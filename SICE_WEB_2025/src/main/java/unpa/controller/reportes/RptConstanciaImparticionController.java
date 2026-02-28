package unpa.controller.reportes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import unpa.entity.*;
import unpa.service.reportes.PdfResponseService;
import unpa.service.reportes.RPTConstanciaEstudiosService;
import unpa.service.reportes.RPTDocumentoAsignacionImparticionService;
import unpa.service.reportes.RPTHistorialAcademicoService;
import unpa.service.universidad.CalendarioService;


import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/reportes")
public class RptConstanciaImparticionController {

    @Autowired private RPTDocumentoAsignacionImparticionService service;
    @Autowired private PdfResponseService servicePDF;
    @Autowired private RPTConstanciaEstudiosService rptConstanciaEstudiosService;
    @Autowired private RPTHistorialAcademicoService rptHistorialAcademicoService;
    @Autowired private CalendarioService calendarioService;


    @GetMapping(value = "/imprimirdoctoasignacion", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> imprimirDocumentoAsignacion(
            @RequestParam String materia,
            @RequestParam String grupo,
            @RequestParam String ciclo,
            @RequestParam String periodoEscolar,
            @RequestParam String cveCarrera,
            @RequestParam String plan

    ) {
        File tempFile = service.generarPDFDoctoAsignacion(materia ,grupo, ciclo,periodoEscolar,cveCarrera,plan);
        return servicePDF.buildPdfResponse(tempFile, "documento_asignacion.pdf");

    }



    @GetMapping(value = "/imprimirdoctoimparticion", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> imprimirDocumentoImparticion(
            @RequestParam String materia,
            @RequestParam String grupo,
            @RequestParam String ciclo,
            @RequestParam String periodoEscolar,
            @RequestParam String cveCarrera,
            @RequestParam String plan

    ) {
        File tempFile = service.generarPDFDoctoImparticion(materia ,grupo, ciclo,periodoEscolar,cveCarrera,plan);
        return servicePDF.buildPdfResponse(tempFile, "documento_imparticion.pdf");
    }


    @GetMapping(value = "/constanciaestudios", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> imprimirConstanciaEstudios(
            @RequestParam("matricula") String matricula

    ) {

        File tempFile = rptConstanciaEstudiosService.generarConstanciaSinHistorial(matricula);
        return servicePDF.buildPdfResponse(tempFile, "constanciaEstudios.pdf");
    }


    @GetMapping(value = "/calendarioescolar", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarCalendarioescolar(
            @RequestParam("ciclo") String ciclo

    ) {
        return  calendarioService.getCalendario(ciclo);
    }

    @GetMapping(value = "/constanciaestudiosconhistorial", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> imprimirConstanciaEstudiosConhistorial(
            @RequestParam("matricula") String matricula

    ) {

        File tempFile = rptConstanciaEstudiosService.generarConstanciaConHistorialAcademico(matricula);
        return servicePDF.buildPdfResponse(tempFile, "constanciaEstudios.pdf");
    }


    @GetMapping(value = "/historialacademico", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> imprimirHistorialAcademico(
            @RequestParam("matricula") String matricula

    ) {

        File tempFile = rptHistorialAcademicoService.generarPDF(matricula);
        return servicePDF.buildPdfResponse(tempFile, "historial.pdf");
    }
}
