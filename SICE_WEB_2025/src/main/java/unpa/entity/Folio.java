package unpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "folios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Folio {

    @Id
    @Column(name = "Id_Fol", length = 5, nullable = false)
    private String id;

    @Column(name = "Descripcion_Fol", length = 60, nullable = false)
    private String descripcion;

    @Column(name = "Consecutivo_Fol", nullable = false)
    private int consecutivo;

    @Column(name = "Anio_Fol", nullable = false)
    private int anio;
}

