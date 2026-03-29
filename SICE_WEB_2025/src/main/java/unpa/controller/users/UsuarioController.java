package unpa.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unpa.entity.user.PasswordRequest;
import unpa.service.users.UsuarioService;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.PUT, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PutMapping("cambiarpassword/{matricula}/")
    public ResponseEntity<Map<String, String>> cambiarPassword(
            @PathVariable("matricula") String matricula,
            @RequestBody PasswordRequest request) {

        boolean actualizado = usuarioService.cambiarPassword(
                matricula,
                request.getPasswordActual(),
                request.getPasswordNueva()
        );

        Map<String, String> response = new HashMap<>();

        if (actualizado) {
            response.put("message", "Contraseña actualizada correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "La contraseña actual no es correcta");
            return ResponseEntity.status(400).body(response);
        }
    }
}


