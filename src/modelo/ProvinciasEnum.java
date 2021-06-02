package modelo;

/**
 * <main>ProvinciasEnum</main>
 * <p>
 * Contiene las provincias de españa con su nombre tipo String</p>
 *
 * @email al.andres.rios.lima@iesportada.es
 * @author Andres Ríos Lima
 * @version 1.0
 */
public enum ProvinciasEnum {

  A_CORUÑA("A CORUÑA"),
  ALBACETE("ALBACETE"),
  ALICANTEALACANT("ALICANTE/ALACANT"),
  ALMERIA("ALMERIA"),
  ARABAALAVA("ARABA/ALAVA"),
  ASTURIAS("ASTURIAS"),
  AVILA("AVILA"),
  BADAJOZ("ADAJOZ"),
  ILLES_BALEARS("ILLES BALEARS"),
  BARCELONA("BARCELONA"),
  BIZKAIA("BIZKAIA"),
  BURGOS("BURGOS"),
  CACERES("CACERES"),
  CADIZ("CADIZ"),
  CANTABRIA("CANTABRIA"),
  CASTELLONCASTELLÓ("CASTELLON/CASTELLÓ"),
  CEUTA("CEUTA"),
  CIUDADREAL("CIUDAD REAL"),
  CORDOBA("CORDOBA"),
  CUENCA("CUENCA"),
  GIPUZKOA("GIPUZKOA"),
  GIRONA("GIRONA"),
  GRANADA("GRANADA"),
  GUADALAJARA("GUADALAJARA"),
  HUELVA("HUELVA"),
  HUESCA("HUESCA"),
  JAEN("JAEN"),
  LA_RIOJA("LA RIOJA"),
  LAS_PALMAS("LAS PALMAS"),
  LEON("LEON"),
  LLEIDA("LLEIDA"),
  LUGO("LUGO"),
  MADRID("MADRID"),
  MALAGA("MALAGA"),
  MELILLA("MELILLA"),
  MURCIA("MURCIA"),
  NAVARRA("NAVARRA"),
  OURENSE("OURENSE"),
  PALENCIA("PALENCIA"),
  PONTEVEDRA("PONTEVEDRA"),
  SALAMANCA("SALAMANCA"),
  SANTA_CRUZ_DE_TENERIFE("SANTA CRUZ DE TENERIFE"),
  SEGOVIA("SEGOVIA"),
  SEVILLA("SEVILLA"),
  SORIA("SORIA"),
  TARRAGONA("TARRAGONA"),
  TERUEL("TERUEL"),
  TOLEDO("TOLEDO"),
  VALENCIAVALÈNCIA("VALENCIA/VALÈNCIA"),
  VALLADOLID("VALLADOLID"),
  ZAMORA("ZAMORA"),
  ZARAGOZA("ZARAGOZA");

  //Variable con el nombre tipo String de la provincia
  String nombreProvincia;

  /**
   * Constructor del Enum
   * @param nombreProvincia nombre de la provincia
   */
  private ProvinciasEnum(String nombreProvincia) {
    this.nombreProvincia = nombreProvincia;
  }

  /**
   * Getter de para sacar el nombre de la provincia en String
   *
   * @return tipo String con el nombre de la provincia.
   */
  public String getNombreProvincia() {
    return nombreProvincia;
  }
}
