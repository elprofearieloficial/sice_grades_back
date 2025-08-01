package unpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "actas")
@Data
public class Acta {

    @EmbeddedId
    private ActaId id;

    @Column(name = "Claveentrega_Act")
    private String claveEntrega;

    @Column(name = "Fechaentrega_Act")
    private LocalDate fechaEntrega;

    @Column(name = "Horaentrega_Act")
    private LocalTime horaEntrega;

    @Column(name = "Fechaaplico_Act")
    private LocalDate fechaAplico;

    @Column(name = "Observaciones_Act")
    private String observaciones;

    // Relación con profesor, opcional
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Pro_FK", insertable = false, updatable = false)
    private Profesor profesor;
    */
}

