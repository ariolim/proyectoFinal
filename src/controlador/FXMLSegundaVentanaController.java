package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Medicion;

/**
 * <main>FXMLSegundaVentanaController</main>
 * <p>
 * Clase controladora de la segunda ventana. Esta muestra una medición para
 * modificar sus datos</p>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class FXMLSegundaVentanaController implements Initializable {

  @FXML
  private TextField textF_Tem_Min;
  @FXML
  private TextField textF_Tem_Med;
  @FXML
  private TextField textF_Tem_Max;
  @FXML
  private TextField textF_Precipitacion;
  @FXML
  private Button botonGuardar;

  FXMLPrimeraVentanaController ctrl1Ventana;

  private Medicion medicionAux;

  /**
   * Método inicial del controlador
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
  }

  /**
   * Evento que cuando se pulsa enter en el textFied requiere focus a el próximo
   * textFied
   *
   * @param event
   */
  @FXML
  private void cambioTemperaturaMinima(ActionEvent event) {
    textF_Tem_Med.requestFocus();
  }

  /**
   * Evento que cuando se pulsa enter en el textFied requiere focus a el próximo
   * textFied
   *
   * @param event
   */
  @FXML
  private void cambioTem_Media(ActionEvent event) {
    textF_Tem_Max.requestFocus();
  }

  /**
   * Evento que cuando se pulsa enter en el textFied requiere focus a el próximo
   * textFied
   *
   * @param event
   */
  @FXML
  private void cambioTem_Max(ActionEvent event) {
    textF_Precipitacion.requestFocus();
  }

  /**
   * Evento que cuando se pulsa enter en el textFied requiere focus al boton de
   * guardar Y cerrar
   *
   * @param event
   */
  @FXML
  private void cambioPrecipitacion(ActionEvent event) {
    botonGuardar.requestFocus();
  }

  /**
   * El evento guarda en variables los datos nuevos de los textField y, por
   * medio de la variable cargada con la instancia del controlador primera
   * clase, llama a un método público de este controladro que recive por
   * parámetros la medición y los valores de los textField. Seguidamente cierra
   * la ventana
   *
   * @param event
   */
  @FXML
  private void eventoBotonGuardar(ActionEvent event) {
    try {
      //Carga de variables con los datos de los textField
      double tem_min = Double.parseDouble(textF_Tem_Min.getText().replace(",", "."));
      double tem_med = Double.parseDouble(textF_Tem_Med.getText().replace(",", "."));
      double tem_max = Double.parseDouble(textF_Tem_Max.getText().replace(",", "."));
      double prec = Double.parseDouble(textF_Precipitacion.getText().replace(",", "."));

      if (prec < 0) {//Si la precipitación es negativa lanza excepción
        throw new Exception("El valor de precipitación no puede ser negativo.");
      }
      //LLamada al método para recibir la medicion y los parámetros de modificación
      ctrl1Ventana.recibirMedicionModificadaVenDos(medicionAux, tem_min, tem_med, tem_max, prec);
      //Cuando se pulsa el boton cierra la ventana
      Stage stage = (Stage) botonGuardar.getScene().getWindow();
      GestorAlertas.alertasMensajes("Mensaje", "Base de datos actualizada");
      stage.close();
    } catch (NumberFormatException e) {
      GestorAlertas.alertasErrores("Error", "Los valores introducidos no son correctos. ");
    } catch (Exception ex) {
      GestorAlertas.alertasErrores("Error", ex.getMessage());
    }
  }

  /**
   * Método publico de una instancia del contolador de la segunda ventana. Este
   * recibe por parámetros una instancia del controlador de la primera ventana y
   * recibe la medicion seleccionada en la tabla para cargar los textField de la
   * segunda ventana con los valores que tiene la medición.
   *
   * @param escena1
   * @param medicion
   */
  public void recibirMedicionCtrlUno(FXMLPrimeraVentanaController escena1, Medicion medicion) {
    //Se carga la medición pasada por parámetros en una variable de una instancia del controlador de la segunda ventana.
    medicionAux = medicion;
    //Se carga el controlador pasado por parámetros en una variable de instancia del controlador de la segunda ventana.
    ctrl1Ventana = escena1;
    //Carga de los textField con los valores de la medicion.
    textF_Tem_Min.setText(String.valueOf(medicion.getTem_min()));
    textF_Tem_Med.setText(String.valueOf(medicion.getTem_med()));
    textF_Tem_Max.setText(String.valueOf(medicion.getTem_max()));
    textF_Precipitacion.setText(String.valueOf(medicion.getPreci_media()));
  }
}
