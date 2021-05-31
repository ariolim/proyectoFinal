/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Andres Ríos Lima
 */
public class FXMLVistaVentanaPrincipalController implements Initializable {

  @FXML
  private TableView<?> tablaMediciones;
  @FXML
  private TableColumn<?, ?> columnaMeses;
  @FXML
  private TableColumn<?, ?> columnaTem_min;
  @FXML
  private TableColumn<?, ?> columna_Tem_Med;
  @FXML
  private TableColumn<?, ?> columna_tem_Max;
  @FXML
  private TableColumn<?, ?> columna_Precipitaciones;
  @FXML
  private ComboBox<?> comoBoxProvincias;
  @FXML
  private ComboBox<?> comboMeses;
  @FXML
  private Button botonModificar;
  @FXML
  private Button botonReset;
  @FXML
  private Button botonMostrarDatos;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  

  @FXML
  private void devolverMedicionTabla(MouseEvent event) {
  }

  @FXML
  private void selectcionarComboProvincias(MouseEvent event) {
  }

  @FXML
  private void selecccionComboMeses(MouseEvent event) {
  }

  @FXML
  private void botonModificarMedicion(ActionEvent event) {
  }

  @FXML
  private void resetearVentana(ActionEvent event) {
  }

  @FXML
  private void mostrarDatosTabla(ActionEvent event) {
  }
  
}
