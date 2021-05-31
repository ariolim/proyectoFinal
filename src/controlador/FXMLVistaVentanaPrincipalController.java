/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import modelo.Medicion;
import modelo.Meses;
import modelo.Provincia;
import modelo.ProvinciasEnum;

/**
 * <main>FXMLVistaVentanaPrincipalController</main>
 * <p>
 * Clase controladora de la ventana Principal</p>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class FXMLVistaVentanaPrincipalController implements Initializable {

  /**
   * Atributos de uso de la clase controladora
   */
  @FXML
  private TableView<Medicion> tablaMediciones;
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
  private ComboBox comoBoxProvincias;
  @FXML
  private ComboBox comboMeses;
  @FXML
  private Button botonModificar;
  @FXML
  private Button botonReset;
  @FXML
  private Button botonMostrarDatos;

  /**
   * Atributo con la lista de provincias.
   */
  private static ArrayList<Provincia> listaProvincias = new ArrayList<>();

  /**
   * Lista Observable para mostrar las mediciones.
   */
  private ObservableList<Medicion> listaObservableMediciones;
 
  /**
   * Método de inicio de la vista. En la clase se carga la lista de Provincias
   * que viene de la clase principal (Sea cargada del fichero excel o de la base
   * de datos. 
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    listaProvincias = Principal.listaProvincias;
    cargarComboMeses();
    cargarComboProvincias();
    comoBoxProvincias.requestFocus();

    listaObservableMediciones = FXCollections.observableArrayList();

    tablaMediciones.setItems(listaObservableMediciones);

    columnaTem_min.setCellValueFactory(new PropertyValueFactory("tem_min"));
    columna_Tem_Med.setCellValueFactory(new PropertyValueFactory("tem_med"));
    columna_tem_Max.setCellValueFactory(new PropertyValueFactory("tem_max"));
    columna_Precipitaciones.setCellValueFactory(new PropertyValueFactory("preci_media"));
    columnaMeses.setCellValueFactory(new PropertyValueFactory("mes"));

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
    comboMeses.setValue(null);
    comoBoxProvincias.setValue(null);
    comoBoxProvincias.requestFocus();
    listaObservableMediciones.clear();
    tablaMediciones.refresh();
  }

  @FXML
  private void mostrarDatosTabla(ActionEvent event) {

    try {
      if (comoBoxProvincias.getValue() == null) {
        throw new Exception("Selecciones una provincia");
      }

      if (comoBoxProvincias.getValue() != null && comboMeses.getValue() == null) {
        for (Provincia provincia : listaProvincias) {
          if (provincia.getNombre().equalsIgnoreCase(comoBoxProvincias.getValue().toString())) {
            listaObservableMediciones.addAll(provincia.getListaMediciones());
            tablaMediciones.refresh();

          }
        }
      }

      if (comoBoxProvincias.getValue() != null && comboMeses.getValue() != null) {
        for (Provincia provincia : listaProvincias) {
          if (provincia.getNombre().equalsIgnoreCase(comoBoxProvincias.getValue().toString())) {
            listaObservableMediciones.add(provincia.mostrarMedicion((Meses) comboMeses.getValue()));
            tablaMediciones.refresh();
          }
        }
      }
    } catch (Exception e) {
      alertasErrores("Error", e.getMessage());
      comoBoxProvincias.requestFocus();
    }

  }

  private void cargarComboMeses() {
    for (Meses mes : Meses.values()) {
      comboMeses.getItems().add(mes);
    }
  }

  private void cargarComboProvincias() {
    for (ProvinciasEnum provincia : ProvinciasEnum.values()) {
      comoBoxProvincias.getItems().add(provincia.getNombreProvincia());
    }
  }

  /**
   * Método privado para introducir el título y el mensaje que contendrá las
   * diferentes excepciones que se puedan producir.
   *
   * @param título Tipo String con el título de la ventana de alerta.
   * @param mensaje Tipo String con el mensaje que contendrá la alerta.
   */
  private static void alertasErrores(String título, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.ERROR);//Tipo de alerta.
    alert.setTitle(título);
    alert.setContentText(mensaje);
    alert.setHeaderText(null);//Selecciona a null para que no salga cabecera.
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    //El botón para salir de la alerta. mientras este la alerta en pantalla no se podrá realizar otra acción en la aplicación.
    Optional<ButtonType> result = alert.showAndWait();
  }

  private static void alertasMensajes(String título, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);//Tipo de alerta.
    alert.setTitle(título);
    alert.setContentText(mensaje);
    alert.setHeaderText(null);//Selecciona a null para que no salga cabecera.
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    //El botón para salir de la alerta. mientras este la alerta en pantalla no se podrá realizar otra acción en la aplicación.
    Optional<ButtonType> result = alert.showAndWait();
  }

}
