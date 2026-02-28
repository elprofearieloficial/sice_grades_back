package unpa.entity.profesores;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "profesores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profesor {
    @Id
    @Column(name = "Id_Pro_FK")
    private String id;

    @Column(name = "Categoria_Pro")
    private String categoria;

    @Column(name = "Nivel_Pro")
    private String nivel;
}

