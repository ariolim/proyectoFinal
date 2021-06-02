/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.MediaAnual;
import modelo.Provincia;

/**
 * <main>FXMLTerceraVentanaController</main>
 * <p>
 * Controlador de la tercera ventana. Muestra una lista con las medias anuales
 * totales de todas las provincias</p>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class FXMLTerceraVentanaController implements Initializable {

  /**
   * Atributos de uso de la clase controladora
   */
  @FXML
  private TableView<MediaAnual> tableViewMediasAnuales;
  @FXML
  private TableColumn<?, ?> tableColNomProvincia;
  @FXML
  private TableColumn<?, ?> tableColTemMin;
  @FXML
  private TableColumn<?, ?> tableColTemMedia;
  @FXML
  private TableColumn<?, ?> tableColTemMax;
  @FXML
  private TableColumn<?, ?> tableColPreci;

  /**
   * Atributo tipo ObservableList donde se van a cargar una lista de
   * mediasAnuales
   */
  private ObservableList<MediaAnual> listaObservableMediasAnuales;

  /**
   * Método de inicio del controlador instancia el observable, y carga la tabla
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {

    listaObservableMediasAnuales = FXCollections.observableArrayList();

    tableViewMediasAnuales.setItems(listaObservableMediasAnuales);

    tableColNomProvincia.setCellValueFactory(new PropertyValueFactory("nombreProvincia"));
    tableColTemMin.setCellValueFactory(new PropertyValueFactory("temMinAnual"));
    tableColTemMedia.setCellValueFactory(new PropertyValueFactory("temMedAnual"));
    tableColTemMax.setCellValueFactory(new PropertyValueFactory("temMaxAnual"));
    tableColPreci.setCellValueFactory(new PropertyValueFactory("preciMedAnual"));
  }

  /**
   * El método recibe una instancia del primer controlador y este a su ves llama
   * al métodoGetter para tener la lista de provincias. Recorre la lista de
   * provincias y crea una instancia de la clase MediaAnual. Todas las
   * instancias se introducien en una lista de MedicionesAnuales y se carga el
   * ObservableList del tercer controlador con la lista de MediasAnuales para
   * mostrarlas en la tabla
   *
   * @param escena1
   */
  public void recibirlistaProvincias(FXMLPrimeraVentanaController escena1) {

    //Creamos un arrayList y lo inicializamos
    ArrayList<MediaAnual> listaMediaAnuales = new ArrayList<>();
    //Recorremos la lista de provincias
    for (Provincia provincia : escena1.getListaProvincias()) {
      //Creamos una instancia de MediaAnual y le pasamos una provincia
      MediaAnual mediaAnual = new MediaAnual(provincia);
      //Cargamos la lista de MediasAnuales
      listaMediaAnuales.add(mediaAnual);
    }
    //cargamos el observable con la lista de medias anuales y refrescamos la tabla
    listaObservableMediasAnuales.addAll(listaMediaAnuales);
    tableViewMediasAnuales.refresh();
  }
}
