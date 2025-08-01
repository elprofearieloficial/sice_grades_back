package unpa.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SinodalesExtraordinariosId implements Serializable {

    private String idExt;
    private String idMatFk;
    private String idPlaFk;
    private String idGpoFk;
    private String idCicFk;
    private Integer idPerFk;
    private String idProFk;

    // Constructor vacío requerido por JPA
    public SinodalesExtraordinariosId() {}

    // Constructor con todos los campos
    public SinodalesExtraordinariosId(String idExt, String idMatFk, String idPlaFk,
                                      String idGpoFk, String idCicFk, Integer idPerFk, String idProFk) {
        this.idExt = idExt;
        this.idMatFk = idMatFk;
        this.idPlaFk = idPlaFk;
        this.idGpoFk = idGpoFk;
        this.idCicFk = idCicFk;
        this.idPerFk = idPerFk;
        this.idProFk = idProFk;
    }

    // Getters y setters
    // ...

    // equals y hashCode obligatorios
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SinodalesExtraordinariosId)) return false;
        SinodalesExtraordinariosId that = (SinodalesExtraordinariosId) o;
        return Objects.equals(idExt, that.idExt) &&
                Objects.equals(idMatFk, that.idMatFk) &&
                Objects.equals(idPlaFk, that.idPlaFk) &&
                Objects.equals(idGpoFk, that.idGpoFk) &&
                Objects.equals(idCicFk, that.idCicFk) &&
                Objects.equals(idPerFk, that.idPerFk) &&
                Objects.equals(idProFk, that.idProFk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExt, idMatFk, idPlaFk, idGpoFk, idCicFk, idPerFk, idProFk);
    }
}
