package modelo;

/**
 * <main>Medicion</main>
 * <p>
 * La clase contiene las mediciones de un mes</p>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class Medicion {

  /**
   * Atributos de una instancia de la clase.
   */
  private String nombreProvincia;
  private double tem_min;
  private double tem_med;
  private double tem_max;
  private double preci_media;
  private Meses mes;

  /**
   * Constructor de la clase donde le pasamos por parámetros las diferentes
   * temperaturas, la precipitación y al mes que pertenece
   *
   * @param tem_min tipo double con la tem_min
   * @param tem_med tipo double con la tem_med
   * @param tem_max tipo double con la tem_max
   * @param preci_media tipo double con las precipitaciones
   * @param mes tipo Mes con el mes que corresponde
   */
  public Medicion(String nombreProvincia,double tem_min, double tem_med, double tem_max, double preci_media, Meses mes) {
    this.nombreProvincia = nombreProvincia;
    this.tem_max = tem_max;
    this.tem_med = tem_med;
    this.tem_min = tem_min;
    this.preci_media = preci_media;
    this.mes = mes;
  }

  /**
   * Getter de la precipitacion
   *
   * @return tipo double con la precipitaciones del mes
   */
  public double getPreci_media() {
    return preci_media;
  }

  /**
   * Getter de la temperatura mínima
   *
   * @return tipo double con la temperatura mínima del mes
   */
  public double getTem_min() {
    return this.tem_min;
  }

  /**
   * Getter del mes
   *
   * @return tipo Mes con el mes de la Medicion
   */
  public Meses getMes() {
    return mes;
  }

  /**
   * Getter de la temperatura max
   *
   * @return tipo double con la temperatura max del mes
   */
  public double getTem_max() {
    return this.tem_max;
  }

  /**
   * Getter de la temperatura media
   *
   * @return tipo double con la temperatura media del mes
   */
  public double getTem_med() {
    return this.tem_med;
  }

  /**
   * Setter para cambiar el mes de Medicion
   *
   * @param mes
   */
  public void setMes(Meses mes) {
    this.mes = mes;
  }

  /**
   * Stter para estrablecer la precipitacioens del mes.
   *
   * @param preci_media tipo double con la precipitación
   */
  public void setPreci_media(double preci_media) {
    this.preci_media = preci_media;
  }

  /**
   * Setter para estrablecer la temperatura min del mes.
   *
   * @param preci_media tipo double con tem_minima
   */
  public void setTem_min(double tem_min) {
    this.tem_min = tem_min;
  }

  /**
   * Setter para estrablecer la temperatura med del mes.
   *
   * @param preci_media tipo double con tem_media
   */
  public void setTem_med(double tem_med) {
    this.tem_med = tem_med;
  }

  /**
   * Setter para estrablecer la temperatura max del mes.
   *
   * @param preci_media tipo double con tem_max
   */
  public void setTem_max(double tem_max) {
    this.tem_max = tem_max;
  }
}
