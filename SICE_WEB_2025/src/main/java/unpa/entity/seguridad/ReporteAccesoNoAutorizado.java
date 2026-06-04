package unpa.entity.seguridad;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reportes_acceso_no_autorizado")
public class ReporteAccesoNoAutorizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Rep", nullable = false)
    private Long id;

    @Column(name = "Id_Alu_FK", length = 8, nullable = false)
    private String matricula;

    @Column(name = "Fecha_Reporte", nullable = false)
    private LocalDateTime fechaReporte;

    @Column(name = "Estatus_Rep", nullable = false)
    private Short estatus;

    @Column(name = "Detalle_Rep", length = 255)
    private String detalle;

    @Column(name = "Bloqueada_Por_Usu_FK", length = 40)
    private String bloqueadaPorUsuario;

    @Column(name = "Fecha_Bloqueo")
    private LocalDateTime fechaBloqueo;

    @Column(name = "Obs_Bloqueo", length = 255)
    private String observacionBloqueo;

    @Column(name = "Reinicio_Por_Usu_FK", length = 40)
    private String reinicioPorUsuario;

    @Column(name = "Fecha_Reinicio")
    private LocalDateTime fechaReinicio;

    @Column(name = "Obs_Reinicio", length = 255)
    private String observacionReinicio;
}
