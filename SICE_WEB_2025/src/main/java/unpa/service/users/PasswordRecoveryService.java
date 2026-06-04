package unpa.service.users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import unpa.config.TenantContext;
import unpa.repository.users.UsuarioRepository;
import unpa.service.EmailService;
import unpa.service.JwtService;

import java.util.Map;
import java.util.Optional;

@Service
public class PasswordRecoveryService {

    private static final Logger log = LoggerFactory.getLogger(PasswordRecoveryService.class);

    private static final String CAMPUS1 = "CAMPUS1";
    private static final String CAMPUS2 = "CAMPUS2";
    private static final String PURPOSE_RESET = "PASSWORD_RESET";

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EmailService emailService;

    @Value("${app.password-reset.frontend-url:http://localhost:4200/restablecer-password}")
    private String resetFrontendUrl;

    @Value("${app.password-reset.servicios-frontend-url:http://localhost:4201/restablecer-password}")
    private String resetServiciosFrontendUrl;

    public void solicitarRecuperacion(String email) {
        String correo = email == null ? "" : email.trim();
        if (correo.isBlank()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }

        Optional<RecoveryTarget> target = buscarUsuarioPorCorreo(correo);
        if (target.isEmpty()) {
            log.info("Solicitud de recuperacion sin coincidencias para correo {}", correo);
            throw new IllegalArgumentException("El correo no esta registrado");
        }

        String token = jwtService.generateTokenWithClaims(
                target.get().matricula(),
                15 * 60 * 1000L,
                Map.of(
                        "purpose", PURPOSE_RESET,
                        "campus", target.get().campus()
                )
        );
        String enlace = resetFrontendUrl + "?token=" + token;
        log.info("Enviando recuperacion para matricula {} en {}", target.get().matricula(), target.get().campus());
        emailService.enviarCorreoRecuperacion(correo, enlace);
    }

    public void solicitarRecuperacionServicios(String email) {
        String correo = email == null ? "" : email.trim();
        if (correo.isBlank()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }

        Optional<RecoveryTarget> target = buscarUsuarioServiciosPorCorreo(correo);
        if (target.isEmpty()) {
            log.info("Solicitud de recuperacion servicios sin coincidencias para correo {}", correo);
            throw new IllegalArgumentException("El correo no esta registrado");
        }

        String token = jwtService.generateTokenWithClaims(
                target.get().matricula(),
                15 * 60 * 1000L,
                Map.of(
                        "purpose", PURPOSE_RESET,
                        "campus", target.get().campus()
                )
        );
        String enlace = resetServiciosFrontendUrl + "?token=" + token;
        log.info("Enviando recuperacion servicios para usuario {} en {}", target.get().matricula(), target.get().campus());
        emailService.enviarCorreoRecuperacion(correo, enlace);
    }

    public void restablecerPassword(String token, String nuevaPassword) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token invalido");
        }
        if (nuevaPassword == null || nuevaPassword.length() < 6) {
            throw new IllegalArgumentException("La nueva contrasena debe tener al menos 6 caracteres");
        }

        Claims claims;
        try {
            claims = jwtService.extractAllClaims(token);
        } catch (JwtException | IllegalArgumentException ex) {
            throw new IllegalArgumentException("Token invalido o expirado");
        }

        String purpose = claims.get("purpose", String.class);
        String campus = claims.get("campus", String.class);
        String matricula = claims.getSubject();

        if (!PURPOSE_RESET.equals(purpose) || matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException("Token invalido");
        }
        if (!CAMPUS1.equals(campus) && !CAMPUS2.equals(campus)) {
            throw new IllegalArgumentException("Token invalido");
        }

        try {
            TenantContext.setCurrentTenant(campus);
            int actualizados = usuarioRepository.actualizarPassword(matricula, nuevaPassword);
            if (actualizados <= 0) {
                throw new IllegalArgumentException("No se pudo restablecer la contrasena");
            }
        } finally {
            TenantContext.clear();
        }
    }

    private Optional<RecoveryTarget> buscarUsuarioPorCorreo(String correo) {
        Optional<String> matriculaCampus1 = buscarAlumnoEnCampus(correo, CAMPUS1);
        if (matriculaCampus1.isPresent()) {
            return Optional.of(new RecoveryTarget(matriculaCampus1.get(), CAMPUS1));
        }
        Optional<String> matriculaCampus2 = buscarAlumnoEnCampus(correo, CAMPUS2);
        return matriculaCampus2.map(m -> new RecoveryTarget(m, CAMPUS2));
    }

    private Optional<RecoveryTarget> buscarUsuarioServiciosPorCorreo(String correo) {
        Optional<String> loginCampus1 = buscarServiciosEnCampus(correo, CAMPUS1);
        if (loginCampus1.isPresent()) {
            return Optional.of(new RecoveryTarget(loginCampus1.get(), CAMPUS1));
        }
        Optional<String> loginCampus2 = buscarServiciosEnCampus(correo, CAMPUS2);
        return loginCampus2.map(login -> new RecoveryTarget(login, CAMPUS2));
    }

    private Optional<String> buscarAlumnoEnCampus(String correo, String campus) {
        try {
            TenantContext.setCurrentTenant(campus);
            return usuarioRepository.findMatriculaByCorreo(correo);
        } finally {
            TenantContext.clear();
        }
    }

    private Optional<String> buscarServiciosEnCampus(String correo, String campus) {
        try {
            TenantContext.setCurrentTenant(campus);
            return usuarioRepository.findLoginServiciosByCorreo(correo);
        } finally {
            TenantContext.clear();
        }
    }

    private record RecoveryTarget(String matricula, String campus) {
    }
}
