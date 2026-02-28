package unpa.dto;

public interface AlumnoProjection {
    String getMatricula();
    String getNombre();
    String getApPaterno();
    String getApMaterno();
    String getId_Mat_FK();
    String getMateria();
    Integer getSemestre();
    String getId_Pla();
    String getId_Car();
    String getNombre_Car();
    String getId_Pla_FK();
    String getTipocurso_Gra();
    String getCiclo();
    Double getParcial1();
    Double getParcial2();
    Double getParcial3();
    Double getPromedioparcial_Gra();
    Double getOrdinario();
    Double getPFinal();
    String getTipocalificacion_Gra();
    Double getExtra1();
    Double getExtra2();
    Double getEspecial();
}
