package unpa.entity.materias;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MateriaId implements Serializable {


    @Id
    @Column(name = "Id_Mat")
    private String idMat;

    @Column(name = "Id_Pla_FK") // nombre exacto en la BD
    private String idPlaFK;


    public MateriaId() {}

    public MateriaId(String idMat, String idPlaFK) {
        this.idMat = idMat;
        this.idPlaFK = idPlaFK;
    }

    // Getters y Setters
    public String getIdMat() {
        return idMat;
    }

    public void setIdMat(String idMat) {
        this.idMat = idMat;
    }

    public String getIdPlaFK() {
        return idPlaFK;
    }

    public void setIdPlaFK(String idPlaFK) {
        this.idPlaFK = idPlaFK;
    }

    // equals y hashCode (muy importante para claves compuestas)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MateriaId)) return false;
        MateriaId that = (MateriaId) o;
        return Objects.equals(idMat, that.idMat) &&
                Objects.equals(idPlaFK, that.idPlaFK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMat, idPlaFK);
    }
}

