
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Andres Ríos Lima
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

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  

  @FXML
  private void cambioTemperaturaMinima(ActionEvent event) {
  }

  @FXML
  private void cambioTem_Media(ActionEvent event) {
  }

  @FXML
  private void cambioTemMedia(ActionEvent event) {
  }

  @FXML
  private void cambioPrecipitacion(ActionEvent event) {
  }

  @FXML
  private void guardarBaseDatos(ActionEvent event) {
  }
  
}
