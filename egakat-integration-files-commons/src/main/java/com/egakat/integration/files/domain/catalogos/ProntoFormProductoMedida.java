package com.egakat.integration.files.domain.catalogos;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.egakat.integration.files.enums.EstadoRegistroType;
import com.egakat.integration.files.domain.Registro;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(catalog = "wms", name = "pf_productos_medidas")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class ProntoFormProductoMedida extends Registro {

	public static final String CLIENTE_CODIGO = "CLIENTE_CODIGO";
	public static final String PRODUCTO_CODIGO = "PRODUCTO_CODIGO";
	public static final String PRODUCTO_NOMBRE = "PRODUCTO_NOMBRE";
	public static final String BODEGA_CODIGO = "BODEGA_CODIGO";
	public static final String HUELLA_CODIGO = "HUELLA_CODIGO";
	public static final String DEFAULT_FLAG = "DEFAULT_FLAG";
	public static final String CASE_LEVEL = "CASE_LEVEL";

	public static final String UNIDAD_CODIGO_1 = "UNIDAD_CODIGO1";
	public static final String FACTOR_CONVERSION_1 = "FACTOR_CONVERSION1";
	public static final String LARGO_1 = "LARGO1";
	public static final String ANCHO_1 = "ANCHO1";
	public static final String ALTO_1 = "ALTO1";
	public static final String PESO_1 = "PESO1";
	public static final String VOLUMEN_1 = "VOLUMEN1";

	public static final String UNIDAD_CODIGO_2 = "UNIDAD_CODIGO2";
	public static final String FACTOR_CONVERSION_2 = "FACTOR_CONVERSION2";
	public static final String LARGO_2 = "LARGO2";
	public static final String ANCHO_2 = "ANCHO2";
	public static final String ALTO_2 = "ALTO2";
	public static final String PESO_2 = "PESO2";
	public static final String VOLUMEN_2 = "VOLUMEN2";

	public static final String UNIDAD_CODIGO_3 = "UNIDAD_CODIGO3";
	public static final String FACTOR_CONVERSION_3 = "FACTOR_CONVERSION3";
	public static final String LARGO_3 = "LARGO3";
	public static final String ANCHO_3 = "ANCHO3";
	public static final String ALTO_3 = "ALTO3";
	public static final String PESO_3 = "PESO3";
	public static final String VOLUMEN_3 = "VOLUMEN3";

	@Column(name = "cliente_codigo", length = 50)
	@NotEmpty
	private String clienteCodigo;

	@Column(name = "producto_codigo", length = 50)
	@NotEmpty
	private String productoCodigo;

	@Column(name = "producto_nombre", length = 200)
	@NotEmpty
	private String productoNombre;

	@Column(name = "bodega_codigo", length = 50)
	@NotEmpty
	private String bodegaCodigo;

	@Column(name = "huella_codigo", length = 50)
	@NotEmpty
	private String huellaCodigo;

	@Column(name = "case_level")
	private int caseLevel;

	@Column(name = "unidad_codigo1", length = 50)
	@NotEmpty
	private String unidadCodigo1;

	@Column(name = "factor_conversion1")
	private Integer factorConversion1;

	@Column(name = "largo1")
	@NotNull
	private BigDecimal largo1;

	@Column(name = "ancho1")
	@NotNull
	private BigDecimal ancho1;

	@Column(name = "alto1")
	@NotNull
	private BigDecimal alto1;

	@Column(name = "peso1")
	@NotNull
	private BigDecimal peso1;

	@Column(name = "volumen1")
	@NotNull
	private BigDecimal volumen1;

	@Column(name = "unidad_codigo2", length = 50)
	@NotNull
	private String unidadCodigo2;

	@Column(name = "factor_conversion2")
	private Integer factorConversion2;

	@Column(name = "largo2")
	private BigDecimal largo2;

	@Column(name = "ancho2")
	private BigDecimal ancho2;

	@Column(name = "alto2")
	private BigDecimal alto2;

	@Column(name = "peso2")
	private BigDecimal peso2;

	@Column(name = "volumen2")
	private BigDecimal volumen2;

	@Column(name = "unidad_codigo3", length = 50)
	@NotNull
	private String unidadCodigo3;

	@Column(name = "factor_conversion3")
	private Integer factorConversion3;

	@Column(name = "largo3")
	private BigDecimal largo3;

	@Column(name = "ancho3")
	private BigDecimal ancho3;

	@Column(name = "alto3")
	private BigDecimal alto3;

	@Column(name = "peso3")
	private BigDecimal peso3;

	@Column(name = "volumen3")
	private BigDecimal volumen3;

	@Override
	public String getIdCorrelacion() {
		return String.format("[%s][%s]", getClienteCodigo(), getProductoCodigo());
	}

	@Builder
	public ProntoFormProductoMedida(Long idArchivo, EstadoRegistroType estado, int numeroLinea, String clienteCodigo,
			String productoCodigo, String productoNombre, String bodegaCodigo, String huellaCodigo, int caseLevel,
			String unidadCodigo1, Integer factorConversion1, BigDecimal largo1, BigDecimal ancho1, BigDecimal alto1,
			BigDecimal peso1, BigDecimal volumen1, String unidadCodigo2, Integer factorConversion2, BigDecimal largo2,
			BigDecimal ancho2, BigDecimal alto2, BigDecimal peso2, BigDecimal volumen2, String unidadCodigo3,
			Integer factorConversion3, BigDecimal largo3, BigDecimal ancho3, BigDecimal alto3, BigDecimal peso3,
			BigDecimal volumen3) {
		super(idArchivo, estado, numeroLinea);
		this.clienteCodigo = clienteCodigo;
		this.productoCodigo = productoCodigo;
		this.productoNombre = productoNombre;
		this.bodegaCodigo = bodegaCodigo;
		this.huellaCodigo = huellaCodigo;
		this.caseLevel = caseLevel;
		this.unidadCodigo1 = unidadCodigo1;
		this.factorConversion1 = factorConversion1;
		this.largo1 = largo1;
		this.ancho1 = ancho1;
		this.alto1 = alto1;
		this.peso1 = peso1;
		this.volumen1 = volumen1;
		this.unidadCodigo2 = unidadCodigo2;
		this.factorConversion2 = factorConversion2;
		this.largo2 = largo2;
		this.ancho2 = ancho2;
		this.alto2 = alto2;
		this.peso2 = peso2;
		this.volumen2 = volumen2;
		this.unidadCodigo3 = unidadCodigo3;
		this.factorConversion3 = factorConversion3;
		this.largo3 = largo3;
		this.ancho3 = ancho3;
		this.alto3 = alto3;
		this.peso3 = peso3;
		this.volumen3 = volumen3;
	}

	@Override
	public Object getObjectValueFromProperty(String property) {
		switch (property) {
		case CLIENTE_CODIGO:
			return getClienteCodigo();
		case PRODUCTO_CODIGO:
			return getProductoCodigo();
		case PRODUCTO_NOMBRE:
			return getProductoNombre();
		case BODEGA_CODIGO:
			return getBodegaCodigo();
		case HUELLA_CODIGO:
			return getHuellaCodigo();
		case CASE_LEVEL:
			return getCaseLevel();
		case UNIDAD_CODIGO_1:
			return getUnidadCodigo1();
		case FACTOR_CONVERSION_1:
			return getFactorConversion1();
		case LARGO_1:
			return getLargo1();
		case ANCHO_1:
			return getAncho1();
		case ALTO_1:
			return getAlto1();
		case PESO_1:
			return getPeso1();
		case VOLUMEN_1:
			return getVolumen1();

		case UNIDAD_CODIGO_2:
			return getUnidadCodigo2();
		case FACTOR_CONVERSION_2:
			return getFactorConversion2();
		case LARGO_2:
			return getLargo2();
		case ANCHO_2:
			return getAncho2();
		case ALTO_2:
			return getAlto2();
		case PESO_2:
			return getPeso2();
		case VOLUMEN_2:
			return getVolumen2();

		case UNIDAD_CODIGO_3:
			return getUnidadCodigo3();
		case FACTOR_CONVERSION_3:
			return getFactorConversion3();
		case LARGO_3:
			return getLargo3();
		case ANCHO_3:
			return getAncho3();
		case ALTO_3:
			return getAlto3();
		case PESO_3:
			return getPeso3();
		case VOLUMEN_3:
			return getVolumen3();

		default:
			return null;
		}
	}

	@Override
	public boolean isHomologableProperty(String property) {
		return false;
	}

	@Override
	protected String getStringValueFromHomologableProperty(String property) {
		return null;
	}

	@Override
	protected Object getObjectValueFromHomologousProperty(String property) {
		return null;
	}
}
