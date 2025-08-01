package unpa.entity;



public interface UniversidadProjection {
    String getNombre();
    String getClave();
    String getRector();
    String getGeneroRector();
    String getViceAcademico();
    String getGeneroAcademico();
    String getViceAdministrativo();
    String getGeneroAdministrativo();
    String getJefeEscolares();
    String getGeneroEscolares();
    Integer getIdRector();
    Integer getIdAcademico();
    Integer getIdAdministrativo();
    Integer getIdJefeEscolares();
    String getLeyenda();
}

