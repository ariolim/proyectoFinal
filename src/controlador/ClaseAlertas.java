

package controlador;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

/**
 * <main>ClaseAlertas</main>
 * 
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class ClaseAlertas {
  
  /**
   * Método static para introducir el título y el mensaje que contendrá las
   * diferentes excepciones que se puedan producir.
   *
   * @param título Tipo String con el título de la ventana de alerta.
   * @param mensaje Tipo String con el mensaje que contendrá la alerta.
   */
  public static void alertasErrores(String título, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.ERROR);//Tipo de alerta.
    alert.setTitle(título);
    alert.setContentText(mensaje);
    alert.setHeaderText(null);//Selecciona a null para que no salga cabecera.
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    //El botón para salir de la alerta. mientras este la alerta en pantalla no se podrá realizar otra acción en la aplicación.
    Optional<ButtonType> result = alert.showAndWait();
  }
  /**
   * Método static para introducir el título y el mensaje que contendrá las
   * diferentes mensajes para el usuario.
   *
   * @param título Tipo String con el título de la ventana de alerta.
   * @param mensaje Tipo String con el mensaje que contendrá la alerta.
   */
  public static void alertasMensajes(String título, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);//Tipo de alerta.
    alert.setTitle(título);
    alert.setContentText(mensaje);
    alert.setHeaderText(null);//Selecciona a null para que no salga cabecera.
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    //El botón para salir de la alerta. mientras este la alerta en pantalla no se podrá realizar otra acción en la aplicación.
    Optional<ButtonType> result = alert.showAndWait();
  }

}
