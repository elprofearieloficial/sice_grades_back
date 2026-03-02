package unpa.controller;

import unpa.config.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unpa.entity.AlumnoDetails;
import unpa.entity.AuthResponse;
import unpa.entity.LoginRequest;
import unpa.service.JwtService;
import unpa.service.users.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200") // angular
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

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
                campusEncontrado = "CAMPUS1";
            } else {
                // 2. Si no está en el 1, buscar en CAMPUS 2 (escolares2)
                TenantContext.setCurrentTenant("CAMPUS2");
                if (usuarioService.existeUsuario(request.getMatricula(), request.getPassword())) {
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
}