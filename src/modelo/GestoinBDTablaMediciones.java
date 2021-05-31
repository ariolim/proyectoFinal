package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * <main>GestoinBDTablaMediciones</main>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class GestoinBDTablaMediciones {

  
  /**
   * 
   * @return
   * @throws Exception 
   */
  public static ArrayList<Provincia> generarListaProvincias() throws Exception {

    ArrayList<Provincia> listaProvincias = new ArrayList<>();

    String sql = "select nombre_provincia,mes,tem_med_min,tem_med_med,tem_med_max,prec_media from mediciones";

    PreparedStatement sentencia = null;
    ResultSet result;
    try {
      sentencia = ConexionBD.conexion().prepareStatement(sql);
      result = sentencia.executeQuery();

      while (result.next()) {
        String nombre_provincia = result.getString("nombre_provincia");
        Meses mes = Meses.valueOf(result.getString("mes"));
        double tem_min = result.getDouble("tem_med_min");
        double tem_med = result.getDouble("tem_med_med");
        double tem_max = result.getDouble("tem_med_max");
        double precipitacion = result.getDouble("prec_media");

        Medicion medicion = new Medicion(nombre_provincia, tem_min, tem_med, tem_max, precipitacion, mes);

        int indice = devolverIndiceProvincia(nombre_provincia, listaProvincias);
        if (indice == -1) {
          Provincia provincia = new Provincia(nombre_provincia);
          listaProvincias.add(provincia);
          listaProvincias.get(devolverIndiceProvincia(nombre_provincia, listaProvincias)).insertarMedicion(medicion);
        } else {
          listaProvincias.get(indice).insertarMedicion(medicion);
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error SQL: " + e.getMessage());
    } catch (NullPointerException e) {
      throw new NullPointerException("No se ha podido a la base de datos: " + e.getMessage());
    } catch (Exception e) {
      throw new Exception("Error: " + e.getMessage());
    } finally {
      try {
        if (sentencia != null) {
          sentencia.close();
        }
      } catch (SQLException e) {
        throw new SQLException("Error SQL: " + e.getMessage());
      }
    }
    return listaProvincias;
  }

  /**
   * 
   * @param provincia
   * @throws Exception 
   */
  public static void insertarMediciones(Provincia provincia) throws Exception {

    //Sentencia sql para tratar.
    String sql = "INSERT INTO mediciones (nombre_provincia,mes,tem_med_min,tem_med_med,tem_med_max,prec_media) VALUES (?,?,?,?,?,?)";

    PreparedStatement sentencia = null;
    String nombre_pro = provincia.getNombre();

    try {
      //Cargamos la sentencia sql ela variable y preparamos la consulta
      sentencia = ConexionBD.conexion().prepareStatement(sql);
      //Seteamos los valores en las columnas correspondientes.
      for (Medicion medicion : provincia.getListaMediciones()) {
        sentencia.setString(1, nombre_pro);
        sentencia.setString(2, (medicion.getMes().toString().toUpperCase()));
        sentencia.setDouble(3, medicion.getTem_min());
        sentencia.setDouble(4, medicion.getTem_med());
        sentencia.setDouble(5, medicion.getTem_max());
        sentencia.setDouble(6, medicion.getPreci_media());
        //Ejecutamos la sentencia.
        sentencia.executeUpdate();
      }
    } catch (SQLException e) {
      throw new SQLException("Error SQL: " + e.getMessage());
    } catch (NullPointerException e) {
      throw new NullPointerException("No se ha podido a la base de datos: " + e.getMessage());
    } catch (Exception e) {
      throw new Exception("Error: " + e.getMessage());
    } finally {
      try {
        if (sentencia != null) {
          sentencia.close();
        }
      } catch (SQLException e) {
        throw new SQLException("Error SQL: " + e.getMessage());
      }
    }
  }

  public static boolean obtenerFilasTabla() throws Exception {
    boolean vacio = true;

    Statement statement = null;
    ResultSet result;
    try {
      String sql = "select * from mediciones";

      statement = ConexionBD.conexion().createStatement();
      result = statement.executeQuery(sql);

      if (result.next()) {
        vacio = false;
      }

    } catch (SQLException e) {
      throw new SQLException("Error SQL: " + e.getMessage());
    } catch (NullPointerException e) {
      throw new NullPointerException("No se ha podido a la base de datos: " + e.getMessage());
    } catch (Exception e) {
      throw new Exception("Error: " + e.getMessage());
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
      } catch (SQLException e) {
        throw new SQLException("Error SQL: " + e.getMessage());
      }
    }
    return vacio;
  }

  private static int devolverIndiceProvincia(String nombreString, ArrayList<Provincia> listaProvincias) {
    int indice = -1;
    boolean encontrado = false;
    for (int i = 0; i < listaProvincias.size() && encontrado == false; i++) {
      if (listaProvincias.get(i).getNombre().equals(nombreString)) {
        indice = i;
        encontrado = true;
      }
    }
    return indice;
  }
}
