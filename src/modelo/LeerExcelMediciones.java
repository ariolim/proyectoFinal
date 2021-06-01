package modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFRow;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * <main>LeerExcelMediciones</main>
 * <p>
 * La clase lee de un fichero excel de 4 hojas las mediciones del todas las
 * provincias de España del año 2019. La clase crea la 52 provincias y cargas
 * todas sus medicioens en cada lista de mediciones de cada Provincia</p>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class LeerExcelMediciones {

  /**
   * Atributo de la clase se carga en una variable la ruta al fichero
   */
  private static String fichero = "ficheros/mediciones2019.xls";

  /**
   * El método lee el fichero y devuelve una lista de provincias con sus
   * mediciones por meses
   *
   * @return tipo ArrayList con la lista de provincias cargada con los datos
   * @throws java.lang.Exception lanza excepciones de E/S y fallos de lectura de
   * fichero
   */
  public static ArrayList<Provincia> leerFichero() throws Exception {
    //Declaración e instancia de una lista de provincias
    ArrayList<Provincia> listaAuxProvincias = new ArrayList<>();
    //Declaración de una lista de mediciones
    ArrayList<Medicion> listaMediciones;
    //Declaración de una variable Provincia
    Provincia provincia;
    //Declaración de una variable Medicion
    Medicion medicion = null;
    //Declaración de una variable Meses
    Meses mes = null;
    //Declaración de una variable que contendrá el nombre de las provincias
    String nombreProvincia = null;
    //Declaración de una variable donde se cargará el valor de las celdas de tipo String
    String celdaValor;
    //Declaración de variable donde se cargaran el valor de las celdas de tipo numérico
    double parametro;
    //Declaración de flujo de datos
    InputStream flujoDatosInputStream = null;

    try {
      //Cargamos el flujo con el fichero a leer
      flujoDatosInputStream = new FileInputStream(fichero);
      //Creación de un nuevo libro con todos los datos
      HSSFWorkbook libro = new HSSFWorkbook(flujoDatosInputStream);
      //Declaración de una hoja
      HSSFSheet hoja = null;
      //Declaración de una fila
      HSSFRow fila = null;
      //Primer for recorre las 4 hojas del fichero
      for (int i = 0; i < 4; i++) {
        //Carga de una hoja 
        hoja = libro.getSheetAt(i);
        //Segundo for recorre las filas de las diferentes hojas (se empeiza el for desde 1 en vez de 0)
        for (int j = 1; j < 53; j++) {
          //Nueva instancia de lista de mediciones
          listaMediciones = new ArrayList<>();
          //Cargamos una fila
          fila = hoja.getRow(j);
          //inicializamos la variable parámetro a 0
          parametro = 0;
          //Tercer for recorre las celdas de cada fila de cada hoja
          for (int k = 1; k < 14; k++) {
            //cargamos la variable con el valor de la celda
            celdaValor = fila.getCell(k).getStringCellValue();
            //Si k (celda) es diferente a 1
            if (k != 1) {
              //cargamos el valor de la celda en el parámetro
              parametro = Double.parseDouble(celdaValor);
              //el caso de diferentes hojas (1,2,3)
              switch (i) {
                //Se introduce en la lista de mediciones correspondiente a la provincia que corresponda el valor de parámetro
                case 1:
                  listaAuxProvincias.get(j - 1).getListaMediciones().get(k - 2).setTem_med(parametro);
                  break;
                case 2:
                  listaAuxProvincias.get(j - 1).getListaMediciones().get(k - 2).setTem_max(parametro);
                  break;
                case 3:
                  listaAuxProvincias.get(j - 1).getListaMediciones().get(k - 2).setPreci_media(parametro);
                  break;
                default:
                  break;
              }
            }
            //Si i(hoja) corresponde ha 0 (Primera hoja)
            if ((i == 0)) {
              //Si k (celda) es igual a 1
              if (k == 1) {
                //cargamos la variable nombre provincia con el valor de la celda k==1
                nombreProvincia = celdaValor;
              }
              //Carga de la variable mes con el mes correspondiente a la celda (llama metodo privado que nos devuelve el mes correspondiente)
              mes = obtenerMes(k, mes);
              //Si k (celda) es igual a 1
              if (k == 1) {
                //Se crea una nueva instancia de provincia con su nombre correspondiente y le cargamos la lista de mediciones instanciada en principio
                provincia = new Provincia(nombreProvincia);
                provincia.setListaMediciones(listaMediciones);
                //Se añade la provincia creada a la lista de provincias
                listaAuxProvincias.add(provincia);
              } else {//Si k es diferente a 1 (celda)
                //Se crea una medición con nombre de provincia correspondiente, los parámetros a 0 y el mes que esta cargado en la variable mes
                medicion = new Medicion(nombreProvincia, 0, 0, 0, 0, mes);
                //Se añada a la lista de mediciones instanciada para esta iteración
                listaMediciones.add(medicion);
                //Se carga el parámetro correspondiente a la provincia de la iteración de j-1 (fila) la medicion de la primera hoja de k-2 (celda) y el mes que corresponda
                listaAuxProvincias.get(j - 1).getListaMediciones().get(k - 2).setTem_min(parametro);
                listaAuxProvincias.get(j - 1).getListaMediciones().get(k - 2).setMes(mes);
              }
            }
          }
        }
      }
      //Cerramos el libro al finalizar
      libro.close();
    } catch (FileNotFoundException ex) { //control de excepciones
      throw new Exception("Error: no se encuentra el fichero. " + ex.getMessage());
    } catch (IOException ex) {
      throw new Exception("Error: no se puede leer el fichero. " + ex.getMessage());
    }
    return listaAuxProvincias;
  }

  /**
   * Método que devuelve un mes según los parámetros que se le pasen.
   *
   * @param k tipo in con el número de columna de una hoja excel
   * @param mes tipo Meses donde se le va a cargar el Mes que es.
   * @return El mes correspondiente.
   */
  private static Meses obtenerMes(int k, Meses mes) {
    switch (k) {
      case 2:
        mes = Meses.ENERO;
        break;
      case 3:
        mes = Meses.FEBRERO;
        break;
      case 4:
        mes = Meses.MARZO;
        break;
      case 5:
        mes = Meses.ABRIL;
        break;
      case 6:
        mes = Meses.MAYO;
        break;
      case 7:
        mes = Meses.JUNIO;
        break;
      case 8:
        mes = Meses.JULIO;
        break;
      case 9:
        mes = Meses.AGOSTO;
        break;
      case 10:
        mes = Meses.SEPTIEMBRE;
        break;
      case 11:
        mes = Meses.OCTUBRE;
        break;
      case 12:
        mes = Meses.NOVIEMBRE;
        break;
      case 13:
        mes = Meses.DICIEMBRE;
        break;
    }
    return mes;
  }
}
