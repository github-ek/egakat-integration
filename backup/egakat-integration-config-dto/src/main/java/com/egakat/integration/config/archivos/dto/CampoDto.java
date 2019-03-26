package com.egakat.integration.config.archivos.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.util.Assert;

import com.egakat.core.domain.InactivableObject;
import com.egakat.core.domain.ObjectWithCode;
import com.egakat.core.domain.SortableObject;
import com.egakat.core.dto.AuditableEntityDto;
import com.egakat.integration.config.archivos.enums.DatoType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CampoDto extends AuditableEntityDto<Long> implements ObjectWithCode<Long>, SortableObject, InactivableObject {

	private long idTipoArchivo;

	@NumberFormat
	private int ordinal;

	@NotNull
	@Size(max = 50)
	private String codigo;

	@NotNull
	@Size(max = 100)
	private String nombre;

	@NotNull
	@Size(max = 200)
	private String descripcion;

	@NotNull
	private DatoType tipoDato;

	@NumberFormat
	private int numeroCaracteres;

	private boolean truncarCaracteres;

	private boolean ignorar;

	private boolean incluir;

	private boolean obligatorioEstructura;

	private Integer ordinalTransformacion;

	private boolean obligatorioValidacion;

	private Long idMapa;

	@NotNull
	@Size(max = 200)
	private String valorPredeterminado;

	@NotNull
	@Size(max = 50)
	private String formato;

	@NotNull
	@Size(max = 1)
	private String formatoNumericoSeparadorDecimal;

	@NotNull
	@Size(max = 1)
	private String formatoNumericoSeparadorGrupo;

	@NotNull
	@Size(max = 50)
	private String expresionRegular;

	@NotNull
	@Size(max = 500)
	private String valoresPermitidos;

	@NumberFormat
	private Long valorEnteroMin;

	@NumberFormat
	private Long valorEnteroMax;

	private BigDecimal valorDecimalMin;

	private BigDecimal valorDecimalMax;

	@DateTimeFormat(style = "M-")
	private LocalDate valorFechaMin;

	@DateTimeFormat(style = "M-")
	private LocalDate valorFechaMax;

	@DateTimeFormat(style = "M-")
	private LocalTime valorHoraMin;

	@DateTimeFormat(style = "M-")
	private LocalTime valorHoraMax;

	private boolean activo;

	@JsonIgnore
	private DateTimeFormatter dateTimeFormatter;

	public DateTimeFormatter getDateTimeFormatter() {
		if (this.dateTimeFormatter == null && !this.isIgnorar()) {
			switch (this.getTipoDato()) {
			case DATETIME:
			case DATE:
			case TIME:
				String mensaje = "El campo %s, es de tipo %s y no tiene un formato definido.";
				mensaje = String.format(mensaje, this.getCodigo(), this.getTipoDato());

				Assert.hasLength(this.getFormato(), mensaje);
				this.dateTimeFormatter = DateTimeFormatter.ofPattern(this.getFormato());
				break;
			default:
				break;
			}
		}
		return dateTimeFormatter;
	}

	@JsonIgnore
	private DecimalFormat decimalFormat;

	/**
	 * https://docs.oracle.com/javase/tutorial/i18n/format/decimalFormat.html#numberpattern
	 * 
	 * @return
	 */
	public DecimalFormat getDecimalFormat() {
		if (this.decimalFormat == null && !this.isIgnorar()) {
			switch (this.getTipoDato()) {
			case DECIMAL:
				String mensaje;
				mensaje = "El campo %s, es de tipo %s y no tiene un formato definido.";
				mensaje = String.format(mensaje, this.getCodigo(), this.getTipoDato());
				Assert.hasLength(this.getFormato(), mensaje);

				mensaje = "El campo %s, es de tipo %s y no tiene un separador decimal definido.";
				mensaje = String.format(mensaje, this.getCodigo(), this.getTipoDato());
				Assert.hasLength(this.getFormatoNumericoSeparadorDecimal(), mensaje);

				mensaje = "El campo %s, es de tipo %s y no tiene un separador de grupo definido.";
				mensaje = String.format(mensaje, this.getCodigo(), this.getTipoDato());
				Assert.hasLength(this.getFormatoNumericoSeparadorGrupo(), mensaje);

				DecimalFormatSymbols symbols = new DecimalFormatSymbols();
				symbols.setDecimalSeparator(this.getFormatoNumericoSeparadorDecimal().charAt(0));
				symbols.setGroupingSeparator(this.getFormatoNumericoSeparadorGrupo().charAt(0));

				this.decimalFormat = new DecimalFormat(this.getFormato(), symbols);
				this.decimalFormat.setParseBigDecimal(true);
				break;
			default:
				break;
			}
		}
		return decimalFormat;
	}

	@JsonIgnore
	private Pattern pattern;

	public Pattern getPattern() {
		if (this.pattern == null && !this.isIgnorar()) {
			if (StringUtils.isNotEmpty(this.getExpresionRegular())) {
				this.pattern = Pattern.compile(this.getExpresionRegular());
			}
		}
		return pattern;
	}

	@JsonIgnore
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	private Set<String> _valoresPermitidos;

	public Set<String> valoresPermitidos() {
		if (this._valoresPermitidos == null && !this.isIgnorar()) {
			if (StringUtils.isNotEmpty(this.getValoresPermitidos())) {
				val values = StringUtils.split(this.getValoresPermitidos(), ',');
				this._valoresPermitidos = Stream.of(values).collect(Collectors.toSet());
			}
		}
		return this._valoresPermitidos;
	}
}