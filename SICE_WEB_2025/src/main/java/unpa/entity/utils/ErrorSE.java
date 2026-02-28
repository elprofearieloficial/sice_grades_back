package unpa.entity.utils;

import unpa.entity.LoggerSICE;

import javax.swing.JOptionPane;
/**
 * Esta clase permitira guardar los errores de ejecución 
 * la información se guardará en el archivo /logs/sice.log<br> 
 * También tiene métodos para mostrar mensajes 
 * de error e información al usuario<br>
 *
 * Fecha Creación: 11 de junio de 2009 <br> 
 * //se cambia al uso de LOG4J2
 * Fecha ultima Actualización: 28 de septiembre de 2023 <br>
 * 
 * @author Ariel López Rodríguez alopez@unpa.edu.mx <br> * 
 * @update Ariel López Rodríguez alopez@unpa.edu.mx <br>
 * @version BETA <br>
 *
 */


public class ErrorSE {
    /**
     * Permite registrar los  de ejecución
     * @param tipoError el tipo de error que se comete 1= Warning  2=  Información  3=Error
     * @param modulo El módulo o clase donde sucedió el error
     * @param descripcion La descripción detallada del error 
     * @param nombreProgramador Desarrollador del módulo
     * @param error La exception que captura el Error, podemos enviar null si no existe una exception
     */
    public static void guardar(int tipoError,String modulo,String descripcion,String nombreProgramador,Exception error){        
        String trazo = "";
        StackTraceElement[] trackingErrores;
        System.out.println("Modulo:"+modulo);
        System.out.println("Descripcion"+descripcion);
        
        try {
            if (error != null) {               
                trackingErrores=error.getStackTrace();
                for(int varError=0;varError<trackingErrores.length;varError++){
                    trazo+=trackingErrores[varError].toString() + " \n";
                }
                if(trazo.length()>0){
                    trazo=trazo.replace("'", "\\'");                    
                }                
            }  
            System.out.println("trazo:"+trazo);
            LoggerSICE.guardar(tipoError, modulo, "DESCRIPCIÓN:" +descripcion + " TRAZO:" + trazo, nombreProgramador);
        } catch (Exception ex) {
            LoggerSICE.guardar(3, "ErrorSE", "no se pudo guardar", "el profe ariel");
        }        
    }

    /**
     * Permite mostrar en pantalla informacion al usuario
     * @param modulo El modulo o clase donde sucedio el error
     * @param descripcion La descripcion detallada de el error que se captura
     */
    public static  void imprimirInformacion(String modulo,String descripcion){
        JOptionPane.showMessageDialog(null, descripcion, modulo,JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Permite mostrar en pantalla informacion de un error al usuario
     * @param modulo El modulo o clase donde sucedio el error
     * @param descripcion La descripcion detallada de el error que se captura
     */
    public static void imprimirError(String modulo,String descripcion){
        JOptionPane.showMessageDialog(null, descripcion,modulo,JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Permite mostrar en pantalla informacion sobre una advertencia al usuario
     * @param modulo El modulo o clase donde sucedio el error
     * @param descripcion La descripcion detallada de el error que se captura
     */
    public static void imprimirWarning(String modulo,String descripcion){
        JOptionPane.showMessageDialog(null, descripcion,modulo,JOptionPane.WARNING_MESSAGE);
    }    
}
