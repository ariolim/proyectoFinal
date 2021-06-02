package modelo;

import java.util.ArrayList;

/**
 * <main>Provincia</main>
 * <p>
 * La clase Provincia contiene las mediciones del año. Contiene un arrayList de
 * mediciones anuales</p>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class Provincia {

  /**
   * Atributos de una instancia de la clase
   */
  private String nombre;
  private ArrayList<Medicion> listaMediciones;

  /**
   * Constructor con un parámetros donde se la pasa el nombre de la provincia y
   * se crea un arrayList de Medicion vacío.
   *
   * @param nombre tipo String con el nombre de la Provincia
   * @throws Exception
   */
  public Provincia(String nombre) throws Exception {
    this.nombre = nombre;
    listaMediciones = new ArrayList<>();
  }

  /**
   * Constructor sin parámetros de una provincia
   */
  public Provincia() {
  }

  /**
   * Getter para dar el nombre de una instancia de la Provincia
   *
   * @return tipo String con el nombre de la Provincia
   */
  public String getNombre() {
    return this.nombre;
  }

  /**
   * Setter para establecer el nombre a una isntancia de Provincia
   *
   * @param nombre tipo String con el nombre de la provincia.
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Getter para devolver lsa mediciones de una instancia de la Provincia.
   *
   * @return tipo ArrayList de Mediciones de la provincia.
   */
  public ArrayList<Medicion> getListaMediciones() {
    return this.listaMediciones;
  }

  /**
   * Setter de la lista de Mediciones para cargar la lista de mediciones
   *
   * @param listaMediciones tipo ArrayList con las mediciones.
   */
  public void setListaMediciones(ArrayList<Medicion> listaMediciones) {
    this.listaMediciones = listaMediciones;
  }

  /**
   * Método para insertar una medicion dentro de la lista de mediciones de una
   * instancia de una provincia.
   *
   * @param medicion tipo Medicion con la medicion que se va a introducir en la
   * lista de mediciones.
   */
  public void insertarMedicion(Medicion medicion) {
    listaMediciones.add(medicion);
  }

  /**
   * Método para mostrar Medicion exacta de un mes.
   *
   * @param mes tipo Mes con el mes para mostrar la Medicion
   * @return tipo Medicion con la Medicion del mes
   * @throws java.lang.Exception Contempla excepción.
   */
  public Medicion mostrarMedicion(Meses mes) throws Exception {
    return devolverMedicionMesExcacto(mes);
  }

  /**
   * Método privado que busca la Medición dentro de la lista de mediciones de la
   * Provincia y la devuelve
   *
   * @param mes tipo Meses para saber que Medición se va a devolver
   * @return tipo Medición con la medición del mes.
   * @throws Exception lanza excepción si no existe la Medición
   */
  private Medicion devolverMedicionMesExcacto(Meses mes) throws Exception {
    Medicion medicionParaDevolver = null;
    for (Medicion medicion : listaMediciones) {
      if (medicion.getMes() == mes) {
        medicionParaDevolver = medicion;
        break;
      }
    }
    if (medicionParaDevolver == null) {
      throw new Exception("No se ha podido mostrar la medición.");
    }
    return medicionParaDevolver;
  }

  /**
   * Método público que devuelve la media anual de todas las temperaturas
   * mínimas de una provincia
   *
   * @return tipo double con la media anual temperaturas mínimas.
   */
  public double devolverMediaAnualTemMin() {
    return calculoMediasAnuales(1);
  }

  /**
   * Método público que devuelve la media anual de todas las temperaturas medias
   * de una provincia
   *
   * @return tipo double con la media anual temperaturas medias.
   */
  public double devolverMediaAnualTemMed() {
    return calculoMediasAnuales(2);
  }

  /**
   * Método público que devuelve la media anual de todas las temperaturas
   * máximas de una provincia
   *
   * @return tipo double con la media anual temperaturas máximas.
   */
  public double devolverMediaAnualTemMax() {
    return calculoMediasAnuales(3);
  }

  /**
   * Método público que devuelve la media anual de todas las precipitaciones de
   * una provincia
   *
   * @return tipo double con la media anual precipitaciones.
   */
  public double devolverMediaAnualPreci() {
    return calculoMediasAnuales(4);
  }

  /**
   * Método privado que calcula la media de los parámetros de las mediciones de
   * una provincia. (La salida sale formateada a dos decimales)
   *
   * @param tipoParametro tipo entero con el parámatro exacto que vamos a
   * calcular la media.
   * @return tipo double con la media del parámetro elegido.(La salida sale
   * formateada a dos decimales)
   */
  private double calculoMediasAnuales(int tipoParametro) {

    double suma = 0;

    for (Medicion medicion : listaMediciones) {
      switch (tipoParametro) {
        case 1:
          suma += medicion.getTem_min();
          break;
        case 2:
          suma += medicion.getTem_med();
          break;
        case 3:
          suma += medicion.getTem_max();
          break;
        case 4:
          suma += medicion.getPreci_media();
          break;
      }
    }
    return Math.round(suma / listaMediciones.size() * 100.0) / 100.0;
  }
}
