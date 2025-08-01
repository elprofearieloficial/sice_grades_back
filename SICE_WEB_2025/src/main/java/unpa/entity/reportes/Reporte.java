package unpa.entity.reportes;

/**
 * Esta clase es utilizada por otras clases para generar actas de
 * calificaciones, constacias de estudio, entre otros reportes. <br>
 * Fecha de creación: 17 de Marzo del 2009. Última modificación: 22 de Marzo del
 * 2011.
 *
 * @author José Antonio Cervantes A. E-mail: jcervantes@unpa.edu.mx
 * @version 1.0
 */


import unpa.entity.utils.FechaUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Reporte {
    public Connection miConexion;
    private Statement ejecutor;
    private Map parametros;

    public Reporte(DataSource dataSource) {
        try {
            miConexion = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        parametros = new HashMap();
    }

    //public String getFechaTextual(String fecha) //formato simple

    public Map getParametros() {
        return parametros;
    }
}
