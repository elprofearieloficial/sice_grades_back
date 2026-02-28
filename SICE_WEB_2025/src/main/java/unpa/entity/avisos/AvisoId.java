package unpa.entity.avisos;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class AvisoId implements Serializable {
    @Column(name = "Id_Avi", nullable = false)
    private Integer id;
    @Column(name = "Id_Cic_FK", nullable = false, length = 9)
    private String cicloId;
    @Column(name = "Id_Per_FK", nullable = false, length = 1)
    private String periodoId;

    public AvisoId() {}

    public AvisoId(Integer id, String cicloId, String periodoId) {
        this.id = id;
        this.cicloId = cicloId;
        this.periodoId = periodoId;
    }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AvisoId)) return false;
        AvisoId that = (AvisoId) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(cicloId, that.cicloId) &&
                Objects.equals(periodoId, that.periodoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cicloId, periodoId);
    }
}

