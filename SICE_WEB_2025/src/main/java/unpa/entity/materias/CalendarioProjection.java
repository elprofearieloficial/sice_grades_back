package unpa.entity.materias;

import java.util.Date;

public interface CalendarioProjection {
    String getNombreMateria();
    String getIdGrupo();
    Date getFechaParcial1();
    Date getFechaParcial2();
    Date getFechaParcial3();
    Date getFechaFinal();
    Date getFechaExtra1();
    Date getFechaExtra2();
    Date getFechaEspecial();
}
