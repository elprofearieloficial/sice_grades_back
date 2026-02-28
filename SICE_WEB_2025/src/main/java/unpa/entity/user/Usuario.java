package unpa.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(name = "Nombre_Usu", length = 40, nullable = false)
    private String nombre;

    @Column(name = "Clave_Usu", length = 40, nullable = false)
    private String clave;

    @Column(name = "Pregunta_Usu", length = 60)
    private String pregunta;

    @Column(name = "Respuesta_Usu", length = 60)
    private String respuesta;

    @Column(name = "Tipo_Usu", nullable = false)
    private Short tipo;

    @Column(name = "Estado_Usu", nullable = false)
    private Short estado;

    @Column(name = "Errorconectar_Usu", nullable = false)
    private Short errorConectar;

    @Column(name = "Fechabloqueo_Usu", nullable = false)
    private String fechaBloqueo;

    // getters y setters
}

