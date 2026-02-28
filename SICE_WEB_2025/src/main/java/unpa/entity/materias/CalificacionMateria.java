package unpa.entity.materias;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CalificacionMateria {
    @Id
    private String matricula;
    private String ciclo;
    private String periodo;
    private String nombreMateria;
    private String calificacion;
    private String grupo;


}