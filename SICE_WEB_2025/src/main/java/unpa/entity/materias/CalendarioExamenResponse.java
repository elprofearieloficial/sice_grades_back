package unpa.entity.materias;


import java.time.LocalDate;

public class CalendarioExamenResponse {
    private String nombreMateria;
    private String idGrupo;
    private LocalDate fechaParcial1;
    private LocalDate fechaParcial2;
    private LocalDate fechaParcial3;
    private LocalDate fechaFinal;
    private LocalDate fechaExtra1;
    private LocalDate fechaExtra2;
    private LocalDate fechaEspecial;

    // constructor
    public CalendarioExamenResponse(String nombreMateria, String idGrupo,
                                    LocalDate fechaParcial1, LocalDate fechaParcial2, LocalDate fechaParcial3,
                                    LocalDate fechaFinal, LocalDate fechaExtra1, LocalDate fechaExtra2,
                                    LocalDate fechaEspecial) {
        this.nombreMateria = nombreMateria;
        this.idGrupo = idGrupo;
        this.fechaParcial1 = fechaParcial1;
        this.fechaParcial2 = fechaParcial2;
        this.fechaParcial3 = fechaParcial3;
        this.fechaFinal = fechaFinal;
        this.fechaExtra1 = fechaExtra1;
        this.fechaExtra2 = fechaExtra2;
        this.fechaEspecial = fechaEspecial;
    }

    // getters y setters
}

