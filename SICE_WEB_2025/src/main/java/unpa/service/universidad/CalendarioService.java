package unpa.service.universidad;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class CalendarioService {

    public ResponseEntity<byte[]> getCalendario(String ciclo) {
        try (InputStream inputStream = getClass().getResourceAsStream("/calendarioescolar/2024-2025.pdf")) {
            if (inputStream == null) {
                return ResponseEntity.notFound().build();
            }

            byte[] pdfBytes = inputStream.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "calendario.pdf"); // "attachment" para descargar

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
