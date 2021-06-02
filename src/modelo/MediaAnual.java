package modelo;

/**
 * <main>MediaAnual</main>
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

  /**
   * Setter del atrubito temMinAnual
   *
   * @param temMinAnual tipo double con el nuevo valor
   */
  public void setTemMinAnual(double temMinAnual) {
    this.temMinAnual = temMinAnual;
  }

  /**
   * Setter del atrubito temMedAnual
   *
   * @param temMedAnual tipo double con el nuevo valor
   */
  public void setTemMedAnual(double temMedAnual) {
    this.temMedAnual = temMedAnual;
  }

  /**
   * Setter del atrubito temMaxAnual
   *
   * @param temMaxAnual tipo double con el nuevo valor
   */
  public void setTemMaxAnual(double temMaxAnual) {
    this.temMaxAnual = temMaxAnual;
  }

  /**
   * Setter del atrubito preciMedAnual
   *
   * @param preciMedAnual tipo double con el nuevo valor
   */
  public void setPreciMedAnual(double preciMedAnual) {
    this.preciMedAnual = preciMedAnual;
  }

  /**
   * Método público que establece nuevos valores a las medias si las medias de
   * las provincias cambian.
   *
   * @param provincia tipo Provincia
   */
  public void establecerValores(Provincia provincia) {
    setTemMinAnual(provincia.devolverMediaAnualTemMin());
    setTemMedAnual(provincia.devolverMediaAnualTemMed());
    setTemMaxAnual(provincia.devolverMediaAnualTemMax());
    setPreciMedAnual(provincia.devolverMediaAnualPreci());
  }

}
