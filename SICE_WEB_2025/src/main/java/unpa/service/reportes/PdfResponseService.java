package unpa.service.reportes;

import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class PdfResponseService {

    public ResponseEntity<byte[]> buildPdfResponse(File pdfFile, String filename) {
        try {
            if (pdfFile == null || !pdfFile.exists()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());
            pdfFile.delete(); // Limpieza del archivo temporal

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(
                    ContentDisposition.inline().filename(filename).build()
            );

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
