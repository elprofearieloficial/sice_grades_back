package unpa.entity.Alumnos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "foto_perfil_alumno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotoPerfil {

    @Id
    @Column(name = "Id_Alu_FK", nullable = false, length = 40)
    private String matricula;

    @Column(name = "Url_Foto", nullable = false, length = 512)
    private String url;

    @Column(name = "FechaAct_Foto", nullable = false)
    private LocalDateTime fechaActualizacion;

    @PrePersist
    @PreUpdate
    public void touch() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}
