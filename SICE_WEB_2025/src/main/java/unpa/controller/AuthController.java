package unpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unpa.config.TenantContext;
import unpa.entity.AlumnoDetails;
import unpa.entity.AuthResponse;
import unpa.entity.LoginRequest;
import unpa.entity.user.ForgotPasswordRequest;
import unpa.entity.user.ResetPasswordRequest;
import unpa.service.JwtService;
import unpa.service.users.PasswordRecoveryService;
import unpa.service.users.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201", "http://127.0.0.1:4201"})
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PasswordRecoveryService passwordRecoveryService;

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("Intentando login - Matricula: " + request.getMatricula());

        String campusEncontrado = null;

        try {
            // 1. Buscar en CAMPUS 1 (escolares)
            TenantContext.setCurrentTenant("CAMPUS1");
            if (usuarioService.existeUsuario(request.getMatricula(), request.getPassword())) {
                if (usuarioService.cuentaBloqueada(request.getMatricula(), request.getPassword())) {
                    return ResponseEntity.status(403).body("Tu cuenta esta bloqueada. Contacta a servicios escolares.");
                }
                campusEncontrado = "CAMPUS1";
            } else {
                // 2. Si no está en el 1, buscar en CAMPUS 2 (escolares2)
                TenantContext.setCurrentTenant("CAMPUS2");
                if (usuarioService.existeUsuario(request.getMatricula(), request.getPassword())) {
                    if (usuarioService.cuentaBloqueada(request.getMatricula(), request.getPassword())) {
                        return ResponseEntity.status(403).body("Tu cuenta esta bloqueada. Contacta a servicios escolares.");
                    }
                    campusEncontrado = "CAMPUS2";
                }
            }

            // 3. Evaluar el resultado
            if (campusEncontrado != null) {
                AlumnoDetails alumno = new AlumnoDetails(request.getMatricula(), request.getMatricula());

                // Generamos el token
                String token = jwtService.generateToken(alumno);

                // IMPORTANTE: Devolvemos el token Y el campus al que pertenece
                return ResponseEntity.ok(new AuthResponse(token, campusEncontrado));
            } else {
                return ResponseEntity.status(401).body("Credenciales inválidas");
            }

        } finally {
            // 4. Limpieza OBLIGATORIA
            // Siempre debemos limpiar el contexto al terminar para no afectar otras peticiones
            TenantContext.clear();
        }
    }

    @PostMapping("/login-servicios")
    public ResponseEntity<?> loginServicios(@RequestBody LoginRequest request) {
        System.out.println("Intentando login servicios - Usuario: " + request.getMatricula());

        String campusEncontrado = null;

        boolean bloqueadaCampus1 = false;
        boolean bloqueadaCampus2 = false;

        try {
            TenantContext.setCurrentTenant("CAMPUS1");
            String resultado = usuarioService.buscarCampusLoginServicios(
                    request.getMatricula(),
                    request.getPassword()
            );
            if ("OK".equals(resultado)) {
                campusEncontrado = "CAMPUS1";
            } else if ("BLOQUEADA".equals(resultado)) {
                bloqueadaCampus1 = true;
            } else if ("NO_ESCOLAR".equals(resultado)) {
                return ResponseEntity.status(401).body("Este usuario no pertenece a servicios escolares.");
            }

            if (campusEncontrado == null) {
                TenantContext.setCurrentTenant("CAMPUS2");
                resultado = usuarioService.buscarCampusLoginServicios(
                        request.getMatricula(),
                        request.getPassword()
                );
                if ("OK".equals(resultado)) {
                    campusEncontrado = "CAMPUS2";
                } else if ("BLOQUEADA".equals(resultado)) {
                    bloqueadaCampus2 = true;
                } else if ("NO_ESCOLAR".equals(resultado)) {
                    return ResponseEntity.status(401).body("Este usuario no pertenece a servicios escolares.");
                }
            }

            if (campusEncontrado == null && (bloqueadaCampus1 || bloqueadaCampus2)) {
                return ResponseEntity.status(403).body("Tu cuenta esta bloqueada.");
            }

            if (campusEncontrado != null) {
                AlumnoDetails usuario = new AlumnoDetails(request.getMatricula(), request.getMatricula());
                String token = jwtService.generateToken(usuario);
                return ResponseEntity.ok(new AuthResponse(token, campusEncontrado));
            }

            return ResponseEntity.status(401).body("Credenciales invalidas");
        } finally {
            TenantContext.clear();
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            passwordRecoveryService.solicitarRecuperacion(request.getEmail());
            return ResponseEntity.ok("Si el correo existe, enviamos un enlace de recuperacion.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo enviar el correo de recuperacion. Revisa configuracion SMTP.");
        }
    }

    @PostMapping("/forgot-password-servicios")
    public ResponseEntity<?> forgotPasswordServicios(@RequestBody ForgotPasswordRequest request) {
        try {
            passwordRecoveryService.solicitarRecuperacionServicios(request.getEmail());
            return ResponseEntity.ok("Si el correo existe, enviamos un enlace de recuperacion.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo enviar el correo de recuperacion. Revisa configuracion SMTP.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            passwordRecoveryService.restablecerPassword(request.getToken(), request.getPasswordNueva());
            return ResponseEntity.ok("Contraseña actualizada correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}