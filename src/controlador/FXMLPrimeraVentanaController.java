package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.GestionBDTablaMediciones;
import modelo.LeerExcelMediciones;
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
  @FXML
  private Button botonCargarBaseDatos;
  @FXML
  private Button botonCalculoMedAnual;
  /**
   * Atributo para cargar una instancia del controlador de la primera ventana
   */
  FXMLPrimeraVentanaController controladorPrimeraVentana;
  /**
   * Atributo con la lista de provincias.
   */
  private ArrayList<Provincia> listaProvincias = new ArrayList<>();

  /**
   * Lista Observable para mostrar las mediciones.
   */
  private ObservableList<Medicion> listaObservableMediciones;

  /**
   * Getter de la lista de provincias
   *
   * @return ArrayList de lista de provincias
   */
  public ArrayList<Provincia> getListaProvincias() {
    return listaProvincias;
  }

  /**
   * Método de inicio de la vista.En la clase se carga la lista de Provincias
   * que viene de la clase principal (Sea cargada del fichero excel o de la base
   * de datos.
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {

    //Inicialicamos el atributo del controlador con la instancia actual del controlador.
    controladorPrimeraVentana = this;

    //Cargamos la lista de provincias con la lista de provincias de la clase Principal
    listaProvincias = Principal.listaProvincias;
    //Cargamos los combox con por medio de sus métodos privados
    cargarComboMeses();
    cargarComboProvincias();
    //Requerimos focus enel combo de provincias
    comoBoxProvincias.requestFocus();
    //En principio desabilitamos los botones modificar y mostrar datos
    botonModificar.setDisable(true);
    botonMostrarDatos.setDisable(true);
    //inicializamos el observable de mediciones
    listaObservableMediciones = FXCollections.observableArrayList();
    //Cargamos la tableView con la lista observable de mediciones
    tablaMediciones.setItems(listaObservableMediciones);

    //Cargamos las columnas de la tableView con los diferentes atributos de un Medicion
    columnaTem_min.setCellValueFactory(new PropertyValueFactory("tem_min"));
    columna_Tem_Med.setCellValueFactory(new PropertyValueFactory("tem_med"));
    columna_tem_Max.setCellValueFactory(new PropertyValueFactory("tem_max"));
    columna_Precipitaciones.setCellValueFactory(new PropertyValueFactory("preci_media"));
    columnaMeses.setCellValueFactory(new PropertyValueFactory("mes"));

  }

  /**
   * Evento se produce cuando seleccionas una medición de la tabla, se habilita
   * el boton de modificar
   *
   * @param event
   */
  @FXML
  private void devolverMedicionTabla(MouseEvent event) {
    Medicion medicion = tablaMediciones.getSelectionModel().getSelectedItem();
    if (medicion != null) {
      botonModificar.setDisable(false);
    }
  }

  /**
   * Evento que habilita el botón de mostrar datos cuando el combo de provincias
   * contiene valor
   *
   * @param event
   */
  @FXML
  private void selectcionarComboProvincias(MouseEvent event) {
    botonMostrarDatos.setDisable(false);
  }

  @FXML
  private void selecccionComboMeses(MouseEvent event) {
  }

  /**
   * Evento para modificar una medición. cuando se pulsa el botón modificar se
   * carga una nueva ventana y en esta se muestra la elección de medición para
   * modificar
   *
   * @param event
   */
  @FXML
  private void botonModificarMedicion(ActionEvent event) {
    modificarMedicionSeleccionada();
  }

  /**
   * El evento produce que cuando se pulse el botón de reset se limpien todos
   * los elementos para que el usuario pueda volver a realizar acciones
   *
   * @param event
   */
  @FXML
  private void resetearVentana(ActionEvent event) {
    resetDatos();
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
      GestorAlertas.alertasErrores("Error", e.getMessage());
      //requiere focus al combo de provincias
      comoBoxProvincias.requestFocus();
    }
  }

  /**
   * Método cuando se le pulsa enter llama al método privado para abrir la vista
   * para modificar una medición
   *
   * @param event
   */
  @FXML
  private void selectcionarTeclado(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
      modificarMedicionSeleccionada();
    }
  }

  /**
   * Cuando se pulsa a este boton carga la base de datos por defecto desde el
   * fichero excel.
   *
   * @param event
   */
  @FXML
  private void cargarBaseDatosConFichero(ActionEvent event) {
    //Declaración de boton auxiliar para cargar en el la confirmación de la alerta
    Optional<ButtonType> resultado;
    try {
      //Cargamos la opción elegida por el usuario
      resultado = GestorAlertas.alertasConfiramcion(event, "¿Desea realmente cargar la base de datos con los datos del fichero Excel?");

      //SI la opción elegida es afirmativa se produde la carga de la lista de provincias con los datos del fichero excel
      if (resultado.get() == ButtonType.OK) {
        listaProvincias = LeerExcelMediciones.leerFichero();
        tablaMediciones.refresh();
        //Se trunca la tabla mediciones para que puedan insertarse los nuevo datos
        GestionBDTablaMediciones.truncarTablaMediciones();
        //Se recorre la lista de provincias y se llama al método para insertar mediciones
        for (Provincia provincia : listaProvincias) {
          GestionBDTablaMediciones.insertarMediciones(provincia);
        }
        //Finalmente, si todo ha salido correctamente salta una alerta de mensaje
        GestorAlertas.alertasMensajes("Mensaje", "Base de datos actualizada");
      }
      resetDatos();
      comoBoxProvincias.requestFocus();
    } catch (Exception ex) {//Control de exceciones
      GestorAlertas.alertasErrores("error", ex.getMessage());
    }
  }

  /**
   * El evento llama una tercera ventana que contiene las medias totales anuales
   * de todas las provincias sobre las mediciones
   *
   * @param event
   */
  @FXML
  private void mostrarMediasAnuales(ActionEvent event) {

    try {
      //Cargar la vista de la nueva ventana.
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLVistaTerceraVentana.fxml"));
      //Carga en el padre o root toso los elementos que contendrá la vista.
      Parent root = loader.load();
      //Instancioamos el controlador de la tercera ventana para poder llamar al sus métodos
      FXMLTerceraVentanaController controladorTerVent = (FXMLTerceraVentanaController) loader.getController();
      //llamamos por medido de la instancia a el método para llevarnos la instancia del controlador
      controladorTerVent.recibirlistaProvincias(controladorPrimeraVentana);

      //Instanciamos una nueva scena con los elementos cargados en el root
      Scene scene = new Scene(root);
      Stage stage = new Stage();//Instanciamos un nuevo scenario
      stage.setTitle("Medias anuales");
      //La modalidad del nuevo escenario será aplicación modal (para que no se pueda realizaracciones con la ventana nueva abierta.
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setScene(scene);//Cargamos el escenario con la escena
      stage.setResizable(false);//ponemos para que no se pueda cambiar el tamño de la ventana
      //Este método muestra la ventana y deja el evento en parada para que e pueda realizar otro eventos en la nueva ventana y no pase la ejecución del programa de esta linea esta que se cierra la ventanan nueva.
      stage.showAndWait();
      comoBoxProvincias.requestFocus();
    } catch (IOException ex) {
      GestorAlertas.alertasMensajes("Error", ex.getMessage());
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
  public void recibirMedicionModificadaVenDos(Medicion medicion, double tem_min, double tem_med, double tem_max, double preci) {

    try {
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

      GestionBDTablaMediciones.actualizarMediciones(medicion);
    } catch (Exception ex) {
      GestorAlertas.alertasErrores("error", ex.getMessage());
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

  /**
   * método privado que resetea los combox , el observable, la tabla y
   * desabilita los botones de mostrar datos y modificar
   */
  private void resetDatos() {
    comboMeses.setValue(null);
    comoBoxProvincias.setValue(null);
    comoBoxProvincias.requestFocus();
    listaObservableMediciones.clear();
    tablaMediciones.refresh();
    botonMostrarDatos.setDisable(true);
    botonModificar.setDisable(true);
  }

  /**
   * Método privado que carga una nueva vista y en esta se muestra la selección
   * hecha en la tabla
   */
  private void modificarMedicionSeleccionada() {
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
      stage.setTitle("Modificaciones");
      //La modalidad del nuevo escenario será aplicación modal (para que no se pueda realizaracciones con la ventana nueva abierta.
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setScene(scene);//Cargamos el escenario con la escena
      stage.setResizable(false);//ponemos para que no se pueda cambiar el tamño de la ventana
      //Este método muestra la ventana y deja el evento en parada para que e pueda realizar otro eventos en la nueva ventana y no pase la ejecución del programa de esta linea esta que se cierra la ventanan nueva.
      stage.showAndWait();
      comoBoxProvincias.requestFocus();
      tablaMediciones.refresh();
    } catch (IOException ex) {
      GestorAlertas.alertasErrores("Error", ex.getMessage());
    }
  }
}
