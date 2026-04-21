package unpa.dto;

import java.util.List;

public class AlumnoDTO {
    private String matricula;
    private String apMaterno;
    private String apPaterno;
    private String nombre;
    private Boolean esRegular = true;
    private String nombreCarrera;
    private String fotoPerfilUrl;
    private List<MateriaDTO> materias;
    private UsuarioDTO usuario;

    public AlumnoDTO() {
    }

    public AlumnoDTO(String matricula, String apMaterno, String apPaterno, String nombre, Boolean esRegular,
                     String nombreCarrera, String fotoPerfilUrl, List<MateriaDTO> materias, UsuarioDTO usuario) {
        this.matricula = matricula;
        this.apMaterno = apMaterno;
        this.apPaterno = apPaterno;
        this.nombre = nombre;
        this.esRegular = esRegular;
        this.nombreCarrera = nombreCarrera;
        this.fotoPerfilUrl = fotoPerfilUrl;
        this.materias = materias;
        this.usuario = usuario;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEsRegular() {
        return esRegular;
    }

    public void setEsRegular(Boolean esRegular) {
        this.esRegular = esRegular;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public List<MateriaDTO> getMaterias() {
        return materias;
    }

    public void setMaterias(List<MateriaDTO> materias) {
        this.materias = materias;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}
