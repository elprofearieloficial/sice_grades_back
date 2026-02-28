package unpa.controller;


import jakarta.persistence.Id;
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
        //if (request.getMatricula().equals("18080068") && request.getPassword().equals("1234")) {
        System.out.println("Matricula:"+request.getMatricula() + " Password: " +  request.getPassword());
        if (usuarioService.existeUsuario(request.getMatricula(), request.getPassword())){
                AlumnoDetails alumno = new AlumnoDetails(request.getMatricula(), request.getMatricula());
                String token = jwtService.generateToken(alumno);
                return ResponseEntity.ok(new AuthResponse(token));
        } else {
                return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}
