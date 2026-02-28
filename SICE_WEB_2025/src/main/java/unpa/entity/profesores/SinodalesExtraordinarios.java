package unpa.entity.profesores;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sinodales_extraordinarios")
@Data
public class SinodalesExtraordinarios {

    @EmbeddedId
    private SinodalesExtraordinariosId id;

    @Column(name = "Titular_Ext")
    private Integer titular;  // Usado para ordenar

    // Relaciones opcionales: solo si necesitas navegar entre entidades

    /*
    @ManyToOne
    @JoinColumn(name = "Id_Pro_FK", insertable = false, updatable = false)
    private Trabajador trabajador;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "Id_Mat_FK", referencedColumnName = "Id_Mat", insertable = false, updatable = false),
        @JoinColumn(name = "Id_Pla_FK", referencedColumnName = "Id_Pla_FK", insertable = false, updatable = false)
    })
    private Materia materia;
    */
}
