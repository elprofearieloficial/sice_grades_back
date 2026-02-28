package unpa.entity.materias;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class ImpartenId implements Serializable {

    private String idMatFk;
    private String idPlaFk;
    private String idCicFk;
    private String idPerFk;
    private String idGpoFk;

    public ImpartenId() {
    }

    public ImpartenId(String idMatFk, String idPlaFk, String idCicFk, String idPerFk, String idGpoFk) {
        this.idMatFk = idMatFk;
        this.idPlaFk = idPlaFk;
        this.idCicFk = idCicFk;
        this.idPerFk = idPerFk;
        this.idGpoFk = idGpoFk;
    }

    // getters y setters

    // equals & hashCode obligatorios
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImpartenId that = (ImpartenId) o;

        if (!Objects.equals(idMatFk, that.idMatFk)) return false;
        if (!Objects.equals(idPlaFk, that.idPlaFk)) return false;
        if (!Objects.equals(idCicFk, that.idCicFk)) return false;
        if (!Objects.equals(idPerFk, that.idPerFk)) return false;
        return Objects.equals(idGpoFk, that.idGpoFk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMatFk, idPlaFk, idCicFk, idPerFk, idGpoFk);
    }
}