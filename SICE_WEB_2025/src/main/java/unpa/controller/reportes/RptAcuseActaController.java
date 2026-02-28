package unpa.controller.reportes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unpa.entity.profesores.ProfesorReporteProjection;
import unpa.entity.profesores.SinodalProjection;
import unpa.entity.reportes.AcusedeRecibo;
import unpa.entity.universidad.CampusProjection;
import unpa.entity.universidad.UniversidadProjection;
import unpa.repository.universidad.UniversidadRepository;
import unpa.service.profesores.SinodalService;
import unpa.service.reportes.AcuseService;
import unpa.service.reportes.ReporteProfesorService;
import unpa.service.universidad.CampusService;
import unpa.service.universidad.MateriaService;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/reportes")
public class RptAcuseActaController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    SinodalService sinodalService;

    @Autowired
    MateriaService materiaService;

    @Autowired
    AcuseService acuseService;

    @Autowired
    CampusService campusService;

    @Autowired
    ReporteProfesorService profesorService;

    @Autowired UniversidadRepository universidadRepository;


    @GetMapping("/prueba")
    public ResponseEntity<String> prueba(Authentication authentication) {
        return ResponseEntity.ok("Usuario autenticado: " + authentication.getName());
    }


    @GetMapping(value = "/acuseacta", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarAcuseActa(
            @RequestParam String materia,
            @RequestParam String periodo,
            @RequestParam String plan,
            @RequestParam String grupo,
            @RequestParam String ciclo,
            @RequestParam String periodoEscolar
    ) {
        try {

            /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Desde el controller: ¿Autenticado? " + auth.isAuthenticated());
            System.out.println("Usuario autenticado: " + auth.getName());
*/
            // Ruta al directorio de reportes (equivalente a getServletContext().getRealPath(...))
            String rutaReportes = new File("src/main/resources/reportes").getAbsolutePath();

            InputStream jasperStream = getClass().getResourceAsStream("/reportes/acuseCalificacionesOrd1.jasper");


            // Crear objeto con los parámetros

            AcusedeRecibo acuse = new AcusedeRecibo(grupo, materia, plan, ciclo, periodoEscolar, Integer.parseInt(periodo), dataSource);

            UniversidadProjection universidadDTO = universidadRepository.findDatosUniversidad("20MSU0020Q");

            // Generar el PDF temporalmente
           CampusProjection encabezado=campusService.obtenerCampus("20MSU0020Q","20ESU3001N");

            ProfesorReporteProjection prof=profesorService.obtenerDatosProfesor(materia,plan,grupo,ciclo, periodoEscolar);

            List<SinodalProjection> sinodales= sinodalService.obtenerSinodales(ciclo,materia,plan,grupo,ciclo, Integer.valueOf(periodo));



            File tempFile = acuse.generateTemporaryPDF(jasperStream,encabezado,prof,sinodales,materiaService.obtenerCarreraDesdeMateria(materia,plan), acuseService.obtenerAcuse(materia,plan,grupo,ciclo,periodoEscolar,periodo),universidadDTO);
            byte[] pdfBytes = Files.readAllBytes(tempFile.toPath());

            // Opcional: borrar archivo temporal
            tempFile.delete();

            // Enviar respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.inline().filename("acuse_acta.pdf").build());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
