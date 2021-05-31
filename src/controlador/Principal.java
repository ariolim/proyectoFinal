package controlador;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.ConexionBD;
import modelo.GestoinBDTablaMediciones;
import modelo.LeerExcelMediciones;
import modelo.Provincia;

/**
 * <main>Principal</main>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class Principal extends Application {

  public static ArrayList<Provincia> listaProvincias;

  public static void main(String[] args) {
    launch(args);

  }

  @Override
  public void start(Stage stage) throws Exception {
    try {
      ConexionBD.generarBaseDatos();
      if (GestoinBDTablaMediciones.obtenerFilasTabla()) {
        listaProvincias = LeerExcelMediciones.leerFichero();
        for (Provincia provincia : listaProvincias) {
          GestoinBDTablaMediciones.insertarMediciones(provincia);
        }
      } else {
        listaProvincias = GestoinBDTablaMediciones.generarListaProvincias();
      }

      Parent root = FXMLLoader.load(getClass().getResource("/vista/FXMLVistaVentanaPrincipal.fxml"));
      Scene scene = new Scene(root);
      stage.setTitle("Temperaturas");
      stage.setScene(scene);
      stage.show();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }
}
