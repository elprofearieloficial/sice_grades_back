package unpa.service.seguridad;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unpa.dto.ReporteAccesoNoAutorizadoAccionRequest;
import unpa.dto.ReporteAccesoNoAutorizadoCreateRequest;
import unpa.dto.ReporteAccesoNoAutorizadoResponse;
import unpa.dto.ReporteAccesoSolicitarCodigoRequest;
import unpa.dto.ReporteAccesoValidarCodigoRequest;
import unpa.entity.seguridad.ReporteAccesoNoAutorizado;
import unpa.repository.seguridad.ReporteAccesoNoAutorizadoRepository;
import unpa.repository.users.UsuarioRepository;
import unpa.entity.user.Usuario;
import unpa.service.EmailService;
import unpa.service.users.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.security.SecureRandom;

@Service
public class ReporteAccesoNoAutorizadoService {

    private static final short ESTATUS_PENDIENTE = 1;
    private static final short ESTATUS_BLOQUEADA = 2;
    private static final short ESTATUS_RESUELTA = 3;
    private static final int OTP_LONGITUD = 6;
    private static final int OTP_VIGENCIA_MINUTOS = 10;

    private final ReporteAccesoNoAutorizadoRepository reporteRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final EmailService emailService;
    private final CaptchaService captchaService;
    private final SecureRandom secureRandom = new SecureRandom();
    private final ConcurrentMap<String, OtpData> otpPendientes = new ConcurrentHashMap<>();

    public ReporteAccesoNoAutorizadoService(
            ReporteAccesoNoAutorizadoRepository reporteRepository,
            UsuarioRepository usuarioRepository,
            UsuarioService usuarioService,
            EmailService emailService,
            CaptchaService captchaService
    ) {
        this.reporteRepository = reporteRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.emailService = emailService;
        this.captchaService = captchaService;
    }

    public void solicitarCodigoVerificacion(ReporteAccesoSolicitarCodigoRequest request) {
        String matricula = validarMatricula(request.getMatricula());
        if (!captchaService.validarCaptcha(request.getCaptchaToken())) {
            throw new IllegalArgumentException("No se pudo validar el captcha");
        }
        if (reporteRepository.countAlumnoByMatricula(matricula) <= 0) {
            throw new IllegalArgumentException("No existe un alumno con esa matricula");
        }

        OtpData actual = otpPendientes.get(matricula);
        if (actual != null && actual.expiraEn().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Ya se envio un codigo recientemente. Revisa tu correo.");
        }

        String correo = usuarioRepository.findCorreoByMatricula(matricula)
                .orElseThrow(() -> new IllegalArgumentException("No existe correo registrado para esa matricula"));

        String codigo = generarCodigo();
        otpPendientes.put(matricula, new OtpData(codigo, LocalDateTime.now().plusMinutes(OTP_VIGENCIA_MINUTOS)));
        try {
            emailService.enviarCodigoReporteAcceso(correo, codigo);
        } catch (RuntimeException ex) {
            otpPendientes.remove(matricula);
            throw ex;
        }
    }

    @Transactional
    public ReporteAccesoNoAutorizadoResponse crearReporteConCodigo(ReporteAccesoValidarCodigoRequest request) {
        String matricula = validarMatricula(request.getMatricula());
        String codigo = request.getCodigo() == null ? "" : request.getCodigo().trim();
        if (codigo.length() != OTP_LONGITUD) {
            throw new IllegalArgumentException("Codigo de verificacion invalido");
        }

        OtpData otpData = otpPendientes.get(matricula);
        if (otpData == null) {
            throw new IllegalArgumentException("No existe un codigo pendiente para esta matricula");
        }
        if (otpData.expiraEn().isBefore(LocalDateTime.now())) {
            otpPendientes.remove(matricula);
            throw new IllegalArgumentException("El codigo de verificacion ya expiro");
        }
        if (!otpData.codigo().equals(codigo)) {
            throw new IllegalArgumentException("Codigo de verificacion incorrecto");
        }

        otpPendientes.remove(matricula);
        return crearReporteInterno(matricula, request.getDetalle());
    }

    @Transactional
    public ReporteAccesoNoAutorizadoResponse crearReporte(ReporteAccesoNoAutorizadoCreateRequest request) {
        String matricula = validarMatricula(request.getMatricula());
        return crearReporteInterno(matricula, request.getDetalle());
    }

    public List<ReporteAccesoNoAutorizadoResponse> listarReportes(Short estatus) {
        List<ReporteAccesoNoAutorizado> reportes = estatus == null
                ? reporteRepository.findAllByOrderByFechaReporteDescIdDesc()
                : reporteRepository.findByEstatusOrderByFechaReporteDescIdDesc(estatus);
        return reportes.stream().map(this::toResponse).toList();
    }

    @Transactional
    public ReporteAccesoNoAutorizadoResponse bloquearCuenta(
            Long idReporte,
            String usuarioAccion,
            ReporteAccesoNoAutorizadoAccionRequest request
    ) {
        ReporteAccesoNoAutorizado reporte = obtenerReporte(idReporte);
        validarUsuarioAccion(usuarioAccion);

        int actualizados = usuarioRepository.bloquearCuenta(reporte.getMatricula());
        if (actualizados <= 0) {
            throw new IllegalStateException("No fue posible bloquear la cuenta");
        }

        reporte.setEstatus(ESTATUS_BLOQUEADA);
        reporte.setBloqueadaPorUsuario(obtenerNombreUsuarioBd(usuarioAccion));
        reporte.setFechaBloqueo(LocalDateTime.now());
        reporte.setObservacionBloqueo(normalizarTexto(request.getObservacion()));

        ReporteAccesoNoAutorizado actualizado = reporteRepository.save(reporte);
        return toResponse(actualizado);
    }

    @Transactional
    public ReporteAccesoNoAutorizadoResponse reiniciarPassword(
            Long idReporte,
            String usuarioAccion,
            ReporteAccesoNoAutorizadoAccionRequest request
    ) {
        ReporteAccesoNoAutorizado reporte = obtenerReporte(idReporte);
        validarUsuarioAccion(usuarioAccion);

        int actualizados = usuarioRepository.reiniciarPasswordYDesbloquear(reporte.getMatricula());
        if (actualizados <= 0) {
            throw new IllegalStateException("No fue posible reiniciar la contraseña");
        }

        reporte.setEstatus(ESTATUS_RESUELTA);
        reporte.setReinicioPorUsuario(obtenerNombreUsuarioBd(usuarioAccion));
        reporte.setFechaReinicio(LocalDateTime.now());
        reporte.setObservacionReinicio(normalizarTexto(request.getObservacion()));

        ReporteAccesoNoAutorizado actualizado = reporteRepository.save(reporte);
        return toResponse(actualizado);
    }

    private ReporteAccesoNoAutorizado obtenerReporte(Long idReporte) {
        return reporteRepository.findById(idReporte)
                .orElseThrow(() -> new IllegalArgumentException("No existe el reporte indicado"));
    }

    private void validarUsuarioAccion(String usuarioAccion) {
        String usuario = usuarioAccion == null ? "" : usuarioAccion.trim();
        if (usuario.isBlank()) {
            throw new IllegalArgumentException("No hay usuario autenticado para ejecutar la accion");
        }
        if (usuarioRepository.findByNombreUsuario(usuario).isEmpty()) {
            throw new IllegalArgumentException("El usuario autenticado no existe en el sistema");
        }
        if (!usuarioService.esUsuarioEscolar(usuario)) {
            throw new IllegalArgumentException("Solo personal de servicios escolares puede ejecutar esta accion");
        }
    }

    private String obtenerNombreUsuarioBd(String loginUsuario) {
        return usuarioRepository.findByNombreUsuario(loginUsuario)
                .map(Usuario::getNombre)
                .orElseThrow(() -> new IllegalArgumentException("El usuario autenticado no existe en el sistema"));
    }

    private String normalizarTexto(String valor) {
        if (valor == null) {
            return null;
        }
        String limpio = valor.trim();
        return limpio.isBlank() ? null : limpio;
    }

    private String validarMatricula(String matricula) {
        String valor = matricula == null ? "" : matricula.trim();
        if (valor.isBlank()) {
            throw new IllegalArgumentException("La matricula es obligatoria");
        }
        return valor;
    }

    private String generarCodigo() {
        int numero = secureRandom.nextInt(1_000_000);
        return String.format("%0" + OTP_LONGITUD + "d", numero);
    }

    private ReporteAccesoNoAutorizadoResponse crearReporteInterno(String matricula, String detalle) {
        ReporteAccesoNoAutorizado reporte = new ReporteAccesoNoAutorizado();
        reporte.setMatricula(matricula);
        reporte.setFechaReporte(LocalDateTime.now());
        reporte.setEstatus(ESTATUS_PENDIENTE);
        reporte.setDetalle(normalizarTexto(detalle));

        ReporteAccesoNoAutorizado guardado = reporteRepository.save(reporte);
        return toResponse(guardado);
    }

    private record OtpData(String codigo, LocalDateTime expiraEn) {
    }

    private ReporteAccesoNoAutorizadoResponse toResponse(ReporteAccesoNoAutorizado entity) {
        ReporteAccesoNoAutorizadoResponse response = new ReporteAccesoNoAutorizadoResponse();
        response.setId(entity.getId());
        response.setMatricula(entity.getMatricula());
        response.setFechaReporte(entity.getFechaReporte());
        response.setEstatus(entity.getEstatus());
        response.setDetalle(entity.getDetalle());
        response.setBloqueadaPorUsuario(entity.getBloqueadaPorUsuario());
        response.setFechaBloqueo(entity.getFechaBloqueo());
        response.setObservacionBloqueo(entity.getObservacionBloqueo());
        response.setReinicioPorUsuario(entity.getReinicioPorUsuario());
        response.setFechaReinicio(entity.getFechaReinicio());
        response.setObservacionReinicio(entity.getObservacionReinicio());
        return response;
    }
}
