package controlador;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.ConexionBD;
import modelo.GestionBDTablaMediciones;
import modelo.LeerExcelMediciones;
import modelo.Medicion;
import modelo.Meses;
import modelo.Provincia;

/**
 * <main>Principal</main>
 * <p>
 * La clase principal contiene el metodo main y el método start. Extiene de
 * Application</p>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class Principal extends Application {

  /**
   * Lista estática de provincias. en esta lista se obtendrán las provincias que
   * se van a pasar al controlador para su gestión. La lista se puede cargar con
   * los datos del fichero excel, si la base de datos esta vacía o con los datos
   * de la base de datos si esta contiene registros.
   */
  public static ArrayList<Provincia> listaProvincias;

  /**
   * EL método main llama al método de la clase Application.
   *
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * El método llama a generar una base de datos y carga la lista de provincias
   * de la base de datos o del fichero excel
   *
   * @param stage tipo Stage con la escena
   */
  @Override
  public void start(Stage stage) {
    try {
      //llama al método para generar la base de datos
      ConexionBD.generarBaseDatos();
      //llama al método obtenerFilasTabla() para saber si la tabla esta vacía
      if (GestionBDTablaMediciones.obtenerFilasTabla()) {
        //carga de la lista.
        cargarListaFichero();
      } else {//SI la tabla de la base de datos contiene mediciones carga la lista de provincias con las mediciones de la base de datos.
        listaProvincias = GestionBDTablaMediciones.generarListaProvincias();
      }
      //Creación de del escenario y carga de la vista de la ventana principal
      Parent root = FXMLLoader.load(getClass().getResource("/vista/FXMLVistaPrimeraVentana.fxml"));
      Scene scene = new Scene(root);
      stage.setTitle("Temperaturas y precipitaciones");
      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();
    } catch (Exception ex) {
      ClaseAlertas.alertasErrores("Error", ex.getMessage());
    }
  }

  /**
   * Método privado para cargar la lista estática con los datos del fichero
   * Excel
   *
   * @throws Exception lanza exceciones de los métodos que utiliza.
   */
  private void cargarListaFichero() throws Exception {
    //Si está vacía carga la lista de provincias llamando al médoto leerFichero del a clase LeerExcelMediciones.
    listaProvincias = LeerExcelMediciones.leerFichero();
    //Recorre la lista de provincias y inserta las mediciones en la base de datos.
    for (Provincia provincia : listaProvincias) {
      GestionBDTablaMediciones.insertarMediciones(provincia);
    }
  }
}
