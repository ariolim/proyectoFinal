package modelo;

import java.util.ArrayList;

/**
 * <main>Proyecto_temperaturas</main>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class Proyecto_temperaturas {

  public static ArrayList<Provincia> listaProvincias;

  public static void main(String[] args) {

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

      for (Provincia provincia : listaProvincias) {
        System.out.println(provincia.getNombre());
      }

    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }
}
