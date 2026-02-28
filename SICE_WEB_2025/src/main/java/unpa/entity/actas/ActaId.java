package unpa.entity.actas;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ActaId implements Serializable {
    private String idAct;
    private String idGpoFk;
    private String idMatFk;
    private String idCicFk;
    private String idPerFk;
    private String idPlaFk;

    // constructor vacío
    public ActaId() {}

    public ActaId(String idAct, String idGpoFk, String idMatFk, String idCicFk, String idPerFk, String idPlaFk) {
        this.idAct = idAct;
        this.idGpoFk = idGpoFk;
        this.idMatFk = idMatFk;
        this.idCicFk = idCicFk;
        this.idPerFk = idPerFk;
        this.idPlaFk = idPlaFk;
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActaId)) return false;
        ActaId that = (ActaId) o;
        return Objects.equals(idAct, that.idAct) &&
                Objects.equals(idGpoFk, that.idGpoFk) &&
                Objects.equals(idMatFk, that.idMatFk) &&
                Objects.equals(idCicFk, that.idCicFk) &&
                Objects.equals(idPerFk, that.idPerFk) &&
                Objects.equals(idPlaFk, that.idPlaFk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAct, idGpoFk, idMatFk, idCicFk, idPerFk, idPlaFk);
    }

    // getters/setters si no usas Lombok
}
