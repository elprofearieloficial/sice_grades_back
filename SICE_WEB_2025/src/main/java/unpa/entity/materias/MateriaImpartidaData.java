package unpa.entity.materias;

import java.sql.Date;


public interface MateriaImpartidaData {
    String getNombreMat();
    Integer getSemestreMat();
    String getNombreTra();
    String getApellidopTra();
    String getApellidomTra();
    String getSiglasgradomaxTra();
    String getIdCiclo();
    String getIdGrupo();
    String getIdMateria();
    String getIdPeriodo();
    String getIdPlan();
    Integer getFechaClases();
    Date getFechae1();
    Date getFechae2();
    Date getFechaesp();
    Date getFechaf();
    Date getFechap1();
    Date getFechap2();
    Date getFechap3();
    String getIdCarrera();
    String getIdProfesor();
}

