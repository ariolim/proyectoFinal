package modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import modelo.Medicion;
import modelo.Meses;
import modelo.Provincia;
import org.apache.poi.hssf.usermodel.HSSFRow;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * <main>LeerExcelMediciones</main>
 * <p>
 * La clase lee de un fichero excel de 4 hojas llas mediciones del todas las
 * provincias de España del año 2019. La clase crea la 52 provincias y cargas
 * todas sus medicioens en cada lista de mediciones de cada Provincia</p>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class LeerExcelMediciones {

  /**
   * Atributos de la clase
   */
  //private static ArrayList<Provincia> listaAuxProvincias = new ArrayList<>();
  private static String fichero = "ficheros/mediciones2019.xls";

  
  /**
   *
   * @param ficheroExcel
   */
  public static ArrayList<Provincia> leerFichero() throws Exception {

    ArrayList<Provincia> listaAuxProvincias = new ArrayList<>();

    ArrayList<Medicion> listaMediciones = new ArrayList<>();

    Provincia provincia;

    InputStream flujoDatosInputStream = null;

    try {
      flujoDatosInputStream = new FileInputStream(fichero);

      HSSFWorkbook libro = new HSSFWorkbook(flujoDatosInputStream);
      HSSFSheet hoja = null;
      String celdaValor;
      HSSFRow fila = null;
      double parametro = 0;
      Medicion medicion = null;
      Meses mes = null;
      String nombreProvincia = null;
      for (int i = 0; i < 4; i++) {
        hoja = libro.getSheetAt(i);
        for (int j = 1; j < 53; j++) {
          listaMediciones = new ArrayList<>();
          fila = hoja.getRow(j);
          parametro = 0;
          for (int k = 1; k < 14; k++) {
            celdaValor = fila.getCell(k).getStringCellValue();
            if (k != 1) {
              parametro = Double.parseDouble(celdaValor);
              switch (i) {
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
            if ((i == 0)) {
              if (k == 1) {
                nombreProvincia = celdaValor;
              }
              mes = obtenerMes(k, mes);

              if (k == 1) {
                provincia = new Provincia(nombreProvincia);
                provincia.setListaMediciones(listaMediciones);
                listaAuxProvincias.add(provincia);
              } else {
                medicion = new Medicion(nombreProvincia,0, 0, 0, 0, mes);
                listaMediciones.add(medicion);
                listaAuxProvincias.get(j - 1).getListaMediciones().get(k - 2).setTem_min(parametro);
                listaAuxProvincias.get(j - 1).getListaMediciones().get(k - 2).setMes(mes);
              }
            }
          }
        }
      }
      libro.close();
    } catch (FileNotFoundException ex) {
      throw new Exception("Error: no se encuentra el fichero. " + ex.getMessage());
    } catch (IOException ex) {
      throw new Exception("Error: no se puede leer el fichero. " + ex.getMessage());
    }
    return listaAuxProvincias;
  }

  /**
   * Método que devuelve un Més según los parámetros que se le pasen.
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
