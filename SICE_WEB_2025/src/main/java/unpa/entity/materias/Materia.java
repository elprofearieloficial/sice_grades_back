package unpa.entity.materias;

import jakarta.persistence.*;
import lombok.Data;
import unpa.entity.universidad.PlanEstudios;

@Entity
@Table(name = "materias")
@Data
public class Materia {

    @EmbeddedId
    private MateriaId id;

    @Column(name = "Nombre_Mat")
    private String nombre;

    @Column(name = "Numerocred_Mat")
    private Byte numeroCreditos;

    @Column(name = "Semestre_Mat")
    private Byte semestre;

    @Column(name = "Horasdocente_Mat")
    private Integer horasDocente;

    @Column(name = "Horasindependiente_Mat")
    private Integer horasIndependiente;

    @Column(name = "Instalaciones_Mat")
    private String instalaciones;

    @Column(name = "Tipo_Mat")
    private Short tipo;

    @Column(name = "Orden_Mat")
    private Integer orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Pla_FK", insertable = false, updatable = false)
    private PlanEstudios plan;
}

