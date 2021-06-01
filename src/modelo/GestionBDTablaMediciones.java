package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <main>GestionBDTablaMediciones</main>
 * <p>
 * Gestiona todas las operaciones CRUD necesitadas para la gestión de la tabla
 * mediciones</p>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class GestionBDTablaMediciones {

  /**
   * El método recibe de la base de datos los resultados de la tabla mediciones
   * y crea una lista de provincias con sus diferentes mediciones
   *
   * @return Retorna tipo ArrayList con las provincas cargadas de la base de
   * datos
   * @throws Exception lanza excepciones de fallos SQL, o fallos al conectarse a
   * la base de datos.
   */
  public static ArrayList<Provincia> generarListaProvincias() throws Exception {

    // Creemos una lista de provincias y la instanciamos
    ArrayList<Provincia> listaProvincias = new ArrayList<>();

    // Sentencia a ejecutar
    String sql = "select nombre_provincia,mes,tem_med_min,tem_med_med,tem_med_max,prec_media from mediciones";

    /*
     * Declaramos la preparación de la sentencia y la instanciamos
     * Declaramos el resultado
     */
    PreparedStatement sentencia = null;
    ResultSet result;
    try {

      // Cargamos en la variable la sentencia
      sentencia = ConexionBD.conexion().prepareStatement(sql);
      //cargamos el resultado de la consulta
      result = sentencia.executeQuery();
      //Mientras contenga registros carga en variables los datos del la tabla y crea mediciones y provincias
      while (result.next()) {
        String nombre_provincia = result.getString("nombre_provincia");
        Meses mes = Meses.valueOf(result.getString("mes"));
        double tem_min = result.getDouble("tem_med_min");
        double tem_med = result.getDouble("tem_med_med");
        double tem_max = result.getDouble("tem_med_max");
        double precipitacion = result.getDouble("prec_media");
        //Creamos una medición y le insertamos los datos
        Medicion medicion = new Medicion(nombre_provincia, tem_min, tem_med, tem_max, precipitacion, mes);
        //declaramos una variable donde se le carga el índice de la provincia de la lista de provincias
        int indice = devolverIndiceProvincia(nombre_provincia, listaProvincias);
        //Si el índice es -1 es que la provincia no existe 
        if (indice == -1) {
          //Se crea una nueva provincia y se le pasa el nombre que se ha sacado de la tabla
          Provincia provincia = new Provincia(nombre_provincia);
          //Se añade a la lista de provincias
          listaProvincias.add(provincia);
          //Se inserta la medición creada en la provincia concreta, por medio del método privado para devolver el índice y el método para insertar mediciones en provincias
          listaProvincias.get(devolverIndiceProvincia(nombre_provincia, listaProvincias)).insertarMedicion(medicion);
        } else {//Si el índice devuelto no es -1 se inserta la medición en una provincia que ya existia.
          listaProvincias.get(indice).insertarMedicion(medicion);
        }
      }
    } catch (SQLException e) {//Control de las excepciones
      throw new SQLException("Error SQL: " + e.getMessage());
    } catch (NullPointerException e) {
      throw new NullPointerException("No se ha podido conectar a la base de datos: " + e.getMessage());
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
    return listaProvincias;//Retorno de la lista de provincias cargada con sus diferentes mediciones
  }

  /**
   * Método estático que inserta medicioens a la tabla mediciones. Se le pasa
   * una provincia que contiene una lista de mediciones y las introduce en la
   * base de datos.
   *
   * @param provincia tipo Provincia
   * @throws Exception lanza excepciones de fallos SQL, o fallos al conectarse a
   * la base de datos.
   */
  public static void insertarMediciones(Provincia provincia) throws Exception {

    //Sentencia sql para tratar.
    String sql = "INSERT INTO mediciones (nombre_provincia,mes,tem_med_min,tem_med_med,tem_med_max,prec_media) VALUES (?,?,?,?,?,?)";
    //Declaración de una sentencia
    PreparedStatement sentencia = null;
    //Declaramos y instanciamos el nombre de la provincia en una variable
    String nombre_pro = provincia.getNombre();
    try {
      //Cargamos la sentencia sql en la variable y preparamos la consulta
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
    } catch (SQLException e) {//Control de las excepciones
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

  /**
   * Método stático que nos muestra si la tabla medicioens contiene registro o
   * no
   *
   * @return tipo boolean con el resultado .Si es true la tabla esta vacía y si
   * es false la tabla contiene registros
   * @throws Exception lanza excepciones de fallos SQL, o fallos al conectarse a
   * la base de datos.
   */
  public static boolean obtenerFilasTabla() throws Exception {
    boolean vacio = true;

    //Cargamos la consulta en una variable.
    String sql = "select * from mediciones";
    //Declareamos una sentencia
    Statement sentencia = null;
    //Declaramos un ResultSet
    ResultSet result;
    try {
      //cargamos la sentencia con la conexión
      sentencia = ConexionBD.conexion().createStatement();
      //cargamos el resultado de la consulta en el la variable
      result = sentencia.executeQuery(sql);
      //Si el resultado no contiene registros ponemos el booleano a false
      if (result.next()) {
        vacio = false;
      }
    } catch (SQLException e) {//Control de las excepciones
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
    return vacio;
  }

  /**
   * Método estático de la clase para modificar una medicion. se le pasa una
   * medicion con valores nuevos y se modifica en la tabla mediciones.
   *
   * @param medicion tipo Medicion con la medición que se ha de modificar.
   * @throws Exception lanza excepciones de fallos SQL, o fallos al conectarse a
   * la base de datos.
   */
  public static void actualizarMediciones(Medicion medicion) throws Exception {

    //Cargamos la consulta en una variable
    String sql = "update mediciones set tem_med_min = ? ,tem_med_med = ?, tem_med_max=?,prec_media=? where nombre_provincia = ? and mes = ?";
    //Declaración de una sentencia
    PreparedStatement sentencia = null;
    //Cargamos los valores que necesitamos de la medición en variables
    String nombre = medicion.getNombreProvincia();
    Meses mes = medicion.getMes();
    Medicion medicionAux = medicion;
    try {
      //Carga de la sentencia con la conexión y se prepara con la sentencia
      sentencia = ConexionBD.conexion().prepareStatement(sql);

      //Paso de paramatros a la sentencia
      sentencia.setDouble(1, medicionAux.getTem_min());
      sentencia.setDouble(2, medicionAux.getTem_med());
      sentencia.setDouble(3, medicionAux.getTem_max());
      sentencia.setDouble(4, medicionAux.getPreci_media());
      sentencia.setString(5, nombre);
      sentencia.setString(6, mes.toString().toUpperCase());
      //Ejecución de la sentencia
      sentencia.executeUpdate();
    } catch (SQLException e) {//Control de excepciones
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

  /**
   * Método privado estático que nos devuelve la posición de una provincia
   * dentro de un listado de provincias que se le pasa por parámetros.
   *
   * @param nombreString tipo String con el nombre de la provincia a buscar
   * dentro de la lista
   * @param listaProvincias tipo ArrayList de provincias
   * @return tipo entero con la posición de la provincia
   */
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

  /**
   * Método para truncar la tabla mediciones
   *
   * @throws Exception lanza excepciones de fallos SQL, o fallos al conectarse a
   * la base de datos.
   */
  public static void truncarTablaMediciones() throws Exception {

    //Cargamos la consulta en una variable
    String sql = "truncate mediciones";
    //Declaración de una sentencia
    PreparedStatement sentencia = null;

    try {
      //Carga de la sentencia con la conexión y se prepara con la sentencia
      sentencia = ConexionBD.conexion().prepareStatement(sql);
      //Ejecución de la sentencia
      sentencia.execute();
    } catch (SQLException e) {//Control de excepciones
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
}
