package unpa.controller.reportes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import unpa.entity.*;
import unpa.service.reportes.PdfResponseService;
import unpa.service.reportes.RPTConstanciaEstudiosService;
import unpa.service.reportes.RPTDocumentoAsignacionImparticionService;


import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

@RestController
@RequestMapping("/reportes")
public class RptConstanciaImparticionController {

    @Autowired private RPTDocumentoAsignacionImparticionService service;
    @Autowired private PdfResponseService servicePDF;
    @Autowired private RPTConstanciaEstudiosService rptConstanciaEstudiosService;

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
    public ResponseEntity<byte[]> imprimirDocumentoImparticion(
            @RequestParam String matricula

    ) {
        File tempFile = rptConstanciaEstudiosService.generarPDF(matricula,1);
        return servicePDF.buildPdfResponse(tempFile, "constanciaEstudios.pdf");
    }
}
