package unpa.entity.reportes;

import unpa.entity.utils.FechaUtils;
import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

// 1. Agregamos 'implements AutoCloseable'
public class Reporte implements AutoCloseable {

    public Connection miConexion;
    private Statement ejecutor;
    private Map parametros;

    public Reporte(DataSource dataSource) {
        try {
            // Aquí se abre la conexión
            miConexion = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        parametros = new HashMap();
    }

    public Map getParametros() {
        return parametros;
    }

    // 2. AGREGAMOS ESTE MÉTODO PARA CERRAR LA CONEXIÓN
    @Override
    public void close() {
        try {
            if (miConexion != null && !miConexion.isClosed()) {
                miConexion.close(); // <--- ¡ESTO ES LO QUE FALTABA!
                System.out.println("✅ Conexión devuelta al pool correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}