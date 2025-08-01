package unpa.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Esta clase permitira registrar los errores de ejecución 
 * Fecha Creación: 29 de septiembre de 2023 <br>  
 * @update Ariel López Rodríguez alopez@unpa.edu.mx <br>
 * @version BETA <br>
 *
 */

/**
     *  Indica el tipo de error que se guarda
     *  1= Warning  2=  Información  3=Error
     */
public class LoggerSICE {
    //SE AGREGA EN SEPTIEMBRE DE 2023, PARA MANEJAR ERRORES
    public static Logger logger= LogManager.getLogger("SICE_2023"); 
    
    public static void guardar(int tipoError,String modulo,String descripcion,String desarrollador){
        switch(tipoError){
            case 1 : 
                logger.warn("Modulo:"+modulo + " Descripción:" + descripcion + " Desarrollador:" + desarrollador);                
                break;
            
            case 2 : 
                logger.info("Modulo:"+modulo + " Descripción:" + descripcion + " Desarrollador:" + desarrollador);                
                break;
            
            case 3 : 
                logger.error("Modulo:"+modulo + " Descripción:" + descripcion + " Desarrollador:" + desarrollador);                
                break;
            
        }
    }
}
