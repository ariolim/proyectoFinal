package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Medicion;
import modelo.Meses;
import modelo.Provincia;
import modelo.ProvinciasEnum;

/**
 * <main>FXMLPrimeraVentanaController</main>
 * <p>
 * Clase controladora de la ventana Principal</p>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class FXMLPrimeraVentanaController implements Initializable {

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
   *
   */
  FXMLPrimeraVentanaController controladorPrimeraVentana;

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

    controladorPrimeraVentana = this;

    listaProvincias = Principal.listaProvincias;
    cargarComboMeses();
    cargarComboProvincias();
    comoBoxProvincias.requestFocus();
    botonModificar.setDisable(true);
    botonMostrarDatos.setDisable(true);

    listaObservableMediciones = FXCollections.observableArrayList();

    tablaMediciones.setItems(listaObservableMediciones);

    columnaTem_min.setCellValueFactory(new PropertyValueFactory("tem_min"));
    columna_Tem_Med.setCellValueFactory(new PropertyValueFactory("tem_med"));
    columna_tem_Max.setCellValueFactory(new PropertyValueFactory("tem_max"));
    columna_Precipitaciones.setCellValueFactory(new PropertyValueFactory("preci_media"));
    columnaMeses.setCellValueFactory(new PropertyValueFactory("mes"));

  }

  /**
   * Evento se produce cuando seleccionas una medición de la tabla, se habilita
   * el boton de modificar y se le requiere focus
   *
   * @param event
   */
  @FXML
  private void devolverMedicionTabla(MouseEvent event) {
    botonModificar.setDisable(false);
    botonModificar.requestFocus();

  }

  @FXML
  private void selectcionarComboProvincias(MouseEvent event) {
    botonMostrarDatos.setDisable(false);
  }

  @FXML
  private void selecccionComboMeses(MouseEvent event) {
  }

  @FXML
  private void botonModificarMedicion(ActionEvent event) {
    try {

      //Cargar la vista de la nueva ventana.
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLVistaSegundaVentana.fxml"));
      //Carga en el padre o root toso los elementos que contendrá la vista.
      Parent root = loader.load();
      //Instancioamos el controlador de la segunda ventana para traernos la Medicion
      FXMLSegundaVentanaController controladorSegVent = (FXMLSegundaVentanaController) loader.getController();
      //Llamamos a un método público del controlador de la segunda ventana y le pasamos por parámetros una instancia del primer controlador y la medición elegida en la tabla
      controladorSegVent.recibirMedicionCtrlUno(controladorPrimeraVentana, tablaMediciones.getSelectionModel().getSelectedItem());

      //Instanciamos una nueva scena con los elementos cargados en el root
      Scene scene = new Scene(root);
      Stage stage = new Stage();//Instanciamos un nuevo scenario
      //La modalidad del nuevo escenario será aplicación modal (para que no se pueda realizaracciones con la ventana nueva abierta.
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setScene(scene);//Cargamos el escenario con la escena
      //Este método muestra la ventana y deja el evento en parada para que e pueda realizar otro eventos en la nueva ventana y no pase la ejecución del programa de esta linea esta que se cierra la ventanan nueva.
      stage.showAndWait();
      tablaMediciones.refresh();
    } catch (IOException ex) {
      ClaseAlertas.alertasErrores("Error", ex.getMessage());
    }
  }

  /**
   * Método público del controlador de la primera ventana, se pe pasa por
   * parámetros la medición que se ha utilizado en el controlador de la segunda
   * ventana y los datos para modificarla
   *
   * @param medicion tipo Medición con la medición utilizada
   * @param tem_min tipo double con el nuevo dato de tem_min de la medición
   * @param tem_med tipo double con el nuevo dato de tem_med de la medición
   * @param tem_max tipo double con el nuevo dato de tem_max de la medición
   * @param preci tipo double con el nuevo dato de precipitación de la medición
   */
  @FXML
  public void recibirMedicionModificadaVenDos(Medicion medicion, double tem_min, double tem_med, double tem_max, double preci) {

    /* 
     * Se setean los nuevos datos en la medición (esta medición al cambiar sus valores cambia en las listas
     * de la contenga puesto que lo que se teiene es una referencia a memoria de esta medición, 
     * por eso no hace falta cambiarla o introducir una nueva en los observables ni en los arrayList que se encuentran
     */
    medicion.setTem_min(tem_min);
    medicion.setTem_med(tem_med);
    medicion.setTem_max(tem_max);
    medicion.setPreci_media(preci);
    //Se refresca la tabla para mostrar los datos modificados.
    tablaMediciones.refresh();

  }

  /**
   * El evento produce que cuando se pulse el botón de reset se limpien todos
   * los elementos para que el usuario pueda volver a realizar acciones
   *
   * @param event
   */
  @FXML
  private void resetearVentana(ActionEvent event) {
    comboMeses.setValue(null);
    comoBoxProvincias.setValue(null);
    comoBoxProvincias.requestFocus();
    listaObservableMediciones.clear();
    tablaMediciones.refresh();
    botonMostrarDatos.setDisable(true);
    botonModificar.setDisable(true);
  }

  /**
   * Evento para mostrar los datos en la tabla en relación con las diferentes
   * elecciones de los comboBox.
   *
   * @param event
   */
  @FXML
  private void mostrarDatosTabla(ActionEvent event) {
    //reseteo del observableList
    listaObservableMediciones.clear();
    try {
      //Si el combobox de provincias tiene una provincia seleccionada y el comboBox de meses no tiene valor
      if (comoBoxProvincias.getValue() != null && comboMeses.getValue() == null) {

        //Recorro la lista de provincias y si el nombre de la provincia coincide con el valor del combobox carga el observable con todas las mediciones del año de esa provincia
        for (Provincia provincia : listaProvincias) {
          if (provincia.getNombre().equalsIgnoreCase(comoBoxProvincias.getValue().toString())) {
            listaObservableMediciones.addAll(provincia.getListaMediciones());
            //Refresca la tableView y requiere focus al combo de provincias
            tablaMediciones.refresh();
            comoBoxProvincias.requestFocus();
          }
        }
      }
      //Si el comboBox de provincias y el combo de meses tienen valor
      if (comoBoxProvincias.getValue() != null && comboMeses.getValue() != null) {
        //Recorre la lista de provincias y busca la que coincida con el nombre de la selección del comboBox de provincias
        for (Provincia provincia : listaProvincias) {
          if (provincia.getNombre().equalsIgnoreCase(comoBoxProvincias.getValue().toString())) {
            //Se carga el el observableList con el mes correspondiente al seleccionado en el comboBox de meses
            listaObservableMediciones.add(provincia.mostrarMedicion((Meses) comboMeses.getValue()));
            //Refresca la tableView y requiere focus al combo de provincias
            tablaMediciones.refresh();
            comoBoxProvincias.requestFocus();
          }
        }
      }
    } catch (Exception e) {//Excepciones
      ClaseAlertas.alertasErrores("Error", e.getMessage());
      //requiere focus al combo de provincias
      comoBoxProvincias.requestFocus();
    }

  }

  /**
   * Método privado para cargar el comboBox de meses con la clase Enum de meses.
   */
  private void cargarComboMeses() {
    for (Meses mes : Meses.values()) {
      comboMeses.getItems().add(mes);
    }
  }

  /**
   * Método privado para cargar el comboBox de privincias con la clase Enum de
   * provincias.
   */
  private void cargarComboProvincias() {
    for (ProvinciasEnum provincia : ProvinciasEnum.values()) {
      comoBoxProvincias.getItems().add(provincia.getNombreProvincia());
    }
  }
}
