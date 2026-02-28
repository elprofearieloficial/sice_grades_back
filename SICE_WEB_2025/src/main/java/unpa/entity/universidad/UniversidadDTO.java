package unpa.entity.universidad;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UniversidadDTO {
    private String nombre;
    private String clave;
    private String rector;
    private String generoRector;
    private String viceAcademico;
    private String generoAcademico;
    private String viceAdministrativo;
    private String generoAdministrativo;
    private String jefeEscolares;
    private String generoEscolares;

    private Integer idRector;
    private Integer idAcademico;
    private Integer idAdministrativo;
    private Integer idJefeEscolares;
    private String leyenda;

    // Constructor manual usado por Hibernate:
    public UniversidadDTO(String nombre, String clave, String rector, String generoRector,
                          String viceAcademico, String generoAcademico,
                          String viceAdministrativo, String generoAdministrativo,
                          String jefeEscolares, String generoEscolares,
                          Integer idRector, Integer idAcademico,
                          Integer idAdministrativo, Integer idJefeEscolares,
                          String leyenda) {
        this.nombre = nombre;
        this.clave = clave;
        this.rector = rector;
        this.generoRector = generoRector;
        this.viceAcademico = viceAcademico;
        this.generoAcademico = generoAcademico;
        this.viceAdministrativo = viceAdministrativo;
        this.generoAdministrativo = generoAdministrativo;
        this.jefeEscolares = jefeEscolares;
        this.generoEscolares = generoEscolares;
        this.idRector = idRector;
        this.idAcademico = idAcademico;
        this.idAdministrativo = idAdministrativo;
        this.idJefeEscolares = idJefeEscolares;
        this.leyenda = leyenda;
    }
}
