package unpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoDTO {
    private String matricula;
    private String apMaterno;
    private String apPaterno;
    private String nombre;
    private Boolean esRegular = true;
    private String nombreCarrera;
    private List<MateriaDTO> materias;

    private UsuarioDTO usuario;
}
