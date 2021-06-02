package modelo;

/**
 * <main>MediaAnual</main>
 * <p>
 * La clase contine las medias totales anuales de una provincia</p>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public class MediaAnual {

  /**
   * Atributos de una instancia de la clase
   */
  String nombreProvincia;
  double temMinAnual;
  double temMedAnual;
  double temMaxAnual;
  double preciMedAnual;

  /**
   * Constructor inicializa las variables con el cálculo de las media anuales de
   * la porvincia pasada por parámetros
   *
   * @param provincia tipo Provincia
   */
  public MediaAnual(Provincia provincia) {

    this.nombreProvincia = provincia.getNombre();
    this.temMinAnual = provincia.devolverMediaAnualTemMin();
    this.temMedAnual = provincia.devolverMediaAnualTemMed();
    this.temMaxAnual = provincia.devolverMediaAnualTemMax();
    this.preciMedAnual = provincia.devolverMediaAnualPreci();
  }

  /**
   * Getter del atributo nombreProvincia
   *
   * @return tipo String con el valor del atributo
   */
  public String getNombreProvincia() {
    return nombreProvincia;
  }

  /**
   * Getter del atributo temMinAnual
   *
   * @return tipo String con el valor del atributo
   */
  public double getTemMinAnual() {
    return temMinAnual;
  }

  /**
   * Getter del atributo temMedAnual
   *
   * @return tipo String con el valor del atributo
   */
  public double getTemMedAnual() {
    return temMedAnual;
  }

  /**
   * Getter del atributo temMaxAnual
   *
   * @return tipo String con el valor del atributo
   */
  public double getTemMaxAnual() {
    return temMaxAnual;
  }

  /**
   * Getter del atributo preciMedAnual
   *
   * @return tipo String con el valor del atributo
   */
  public double getPreciMedAnual() {
    return preciMedAnual;
  }
}
