package unpa.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unpa.entity.user.PasswordRequest;
import unpa.service.users.UsuarioService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Value("${app.uploads.dir:./uploads/perfiles}")
    private String uploadDir;

    @Value("${app.uploads.public-base-url:/vm2/fotos-perfil}")
    private String publicBaseUrl;

    @Value("${app.uploads.credencial-dir:./uploads/credenciales}")
    private String credencialUploadDir;

    @Value("${app.uploads.credencial-public-base-url:/vm2/fotos-credencial}")
    private String credencialPublicBaseUrl;

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

    @PostMapping(value = "/{matricula}/foto-perfil", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, String>> subirFotoPerfil(
            @PathVariable("matricula") String matricula,
            @RequestPart("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Archivo vacio"));
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Solo se permiten imagenes"));
        }

        try {
            Path storageDir = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(storageDir);

            String original = Optional.ofNullable(file.getOriginalFilename()).orElse("foto");
            String extension = original.contains(".")
                    ? original.substring(original.lastIndexOf('.'))
                    : ".jpg";
            String safeFileName = matricula + "_" + UUID.randomUUID() + extension;

            Path destination = storageDir.resolve(safeFileName).normalize();
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            String fotoUrl = publicBaseUrl + "/" + safeFileName;
            usuarioService.guardarFotoPerfilUrl(matricula, fotoUrl);
            return ResponseEntity.ok(Map.of("url", fotoUrl));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "No se pudo guardar la imagen"));
        }
    }

    @GetMapping("/{matricula}/foto-perfil")
    public ResponseEntity<Map<String, String>> obtenerFotoPerfil(@PathVariable("matricula") String matricula) {
        return usuarioService.obtenerFotoPerfilUrl(matricula)
                .map(url -> ResponseEntity.ok(Map.of("url", url)))
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of("error", "Foto no encontrada")));
    }

    @PostMapping(value = "/{matricula}/foto-credencial", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, String>> subirFotoCredencial(
            @PathVariable("matricula") String matricula,
            @RequestPart("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Archivo vacio"));
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Solo se permiten imagenes"));
        }

        try {
            Path storageDir = Paths.get(credencialUploadDir).toAbsolutePath().normalize();
            Files.createDirectories(storageDir);

            String original = Optional.ofNullable(file.getOriginalFilename()).orElse("foto");
            String extension = original.contains(".")
                    ? original.substring(original.lastIndexOf('.'))
                    : ".jpg";
            String safeFileName = matricula + "_cred_" + UUID.randomUUID() + extension;

            Path destination = storageDir.resolve(safeFileName).normalize();
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            String fotoUrl = credencialPublicBaseUrl + "/" + safeFileName;
            usuarioService.guardarFotoCredencialUrl(matricula, fotoUrl);
            return ResponseEntity.ok(Map.of("url", fotoUrl));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "No se pudo guardar la imagen"));
        }
    }

    @GetMapping("/{matricula}/foto-credencial")
    public ResponseEntity<Map<String, String>> obtenerFotoCredencial(@PathVariable("matricula") String matricula) {
        return usuarioService.obtenerFotoCredencialUrl(matricula)
                .map(url -> ResponseEntity.ok(Map.of("url", url)))
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of("error", "Foto no encontrada")));
    }

}


