package modelo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * <main>ConexionBD</main>
 * <p>
 * La clase conecta a la base de datos desde por medio de un fichero .properties
 * Si la base de datos no exsiste la crea.</p>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class ConexionBD {

  /**
   * Atributos de la clase con los dos ficheros de conexión posibles
   */
  private static String ficheroCredenciales = "ficheros/credDB.properties";

  private static String ficheroCredencialesSinBd = "ficheros/credDBSinBaseDatos.properties";

  /**
   * Método para conectar a la base de datos con una credenciales pasadas por un
   * fichero ".properties".
   *
   * @param fichero
   * @return tipo Conection con la conexión
   * @throws Exception lanza excepciones si no puede conectarse a la base de
   * datos o no puede leer el fichero de conexión
   */
  public static Connection conexion(String fichero) throws Exception {

    Connection conexion = null;
    try {
      FileInputStream f = new FileInputStream(fichero);
      Properties credenciales = new Properties();
      credenciales.load(f);
      //Una vez leida las credenciales se cargan en variables.
      String url = credenciales.getProperty("url");
      String user = credenciales.getProperty("user");
      String password = credenciales.getProperty("password");
      conexion = DriverManager.getConnection(url, user, password);
    } catch (SQLException | IOException e) {
      throw new Exception("Error conexión base de datos" + e.getMessage());
    }
    return conexion;
  }

  /**
   * Método estático que devuelve una conexion. llama al método conexión()
   *
   * @return tipo Conexcion con una conexión.
   * @throws Exception Lanza excepciones que viene de otro método.
   */
  public static Connection conexion() throws Exception {
    return conexion(ficheroCredenciales);
  }

  /**
   * Método stático que devuelve una conexión. llama al método conexion().
   *
   * @return tipo Conexcion con una conexión.
   * @throws Exception Lanza excepciones que viene de otro método.
   */
  public static Connection conexionDirecta() throws Exception {
    return conexion(ficheroCredencialesSinBd);
  }

  /**
   * Método crea la base de datos si no existe.
   *
   * @throws Exception
   */
  public static void generarBaseDatos() throws Exception {
    //Se carga en una variable la conexión directa al gestor de la base de datos sin entrar en ninguna tabla.
    Connection conexion = conexionDirecta();

    //Se crea una isntancia de un StringBuider para cargar las sentencias de SQL
    StringBuilder ficheroLeido = new StringBuilder();
    BufferedReader buffer = null;
    String linea = "";
    //Declaramos una sentencia
    PreparedStatement statement = null;
    try {
      //Leemos del fichero y lo cargamos en la variable del flujo de datos.
      buffer = new BufferedReader(new FileReader("ficheros/temperaturas2019.sql"));
      //Leemos una linea del fichero
      linea = buffer.readLine();
      //Mientras tenga contenido se genera el bucle.
      while (linea != null) {
        //Cargamos la linea en el StringBuilder
        ficheroLeido.append(linea);
        //Si la linea contiene el ";" es que la sentencia esta completa.
        if (linea.contains(";")) {
          //Cargamos en un String las lineas del fichero asta el ";"
          String sql = ficheroLeido.toString();
          //Preparamos la sentencia a ejecutar 
          statement = conexion.prepareStatement(sql);
          //Ejecutamos la actualización de la base de datos
          statement.executeUpdate();
          //Reseteamos el StringBuilder
          ficheroLeido = new StringBuilder();
        }
        //Volvemos a leer otra línea
        linea = buffer.readLine();
      }//Excepciones
    } catch (SQLException e) {
      throw new SQLException("Error SQL: " + e.getMessage());
    } catch (NullPointerException e) {
      throw new NullPointerException("No se ha podido conectar a la base de datos: " + e.getMessage());
    } catch (Exception e) {
      throw new Exception("Error: " + e.getMessage());
    } finally {//Finalmete cerramos la conexión 
      try {
        if (statement != null) {
          statement.close();
        }
      } catch (SQLException e) {
        throw new SQLException("Error SQL: " + e.getMessage());
      }
    }
  }
}
