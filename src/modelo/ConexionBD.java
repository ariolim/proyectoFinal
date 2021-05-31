package modelo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    Connection conexion = conexionDirecta();

    StringBuilder ficheroLeido = new StringBuilder();
    BufferedReader buffer = null;
    String linea = "";
    try {
      buffer = new BufferedReader(new FileReader("ficheros/temperaturas2019.sql"));

      linea = buffer.readLine();
      while (linea != null) {
        ficheroLeido.append(linea);
        if (linea.contains(";")) {
          try {

            String sql = ficheroLeido.toString();

            PreparedStatement statement;
            statement = conexion.prepareStatement(sql);
            statement.executeUpdate();

          } catch (SQLException ex) {
            throw new Exception("Error Sql " + ex.getSQLState());
          } catch (Exception ex) {
            throw new Exception("Error: " + ex.getMessage());
          }
          ficheroLeido = new StringBuilder();
        }
        linea = buffer.readLine();
      }
    } catch (FileNotFoundException ex) {
      throw new Exception("Error al leer fichero: " + ex.getMessage());
    }
  }
}
