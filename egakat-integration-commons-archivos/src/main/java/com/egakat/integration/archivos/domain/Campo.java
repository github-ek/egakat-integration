package com.egakat.integration.archivos.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.egakat.core.data.jpa.domain.AuditableEntity;
import com.egakat.core.domain.InactivableObject;
import com.egakat.core.domain.SortableObject;
import com.egakat.integration.config.archivos.enums.DatoType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "campos")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_campo"))
public class Campo extends AuditableEntity<Long> implements SortableObject, InactivableObject {

	@Column(name = "id_tipo_archivo", nullable = false)
	private long idTipoArchivo;

	@NumberFormat
	private int ordinal;

	@NumberFormat
	private Integer llave;
	
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
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_dato")
	private DatoType tipoDato;

	@NumberFormat
	@Column(name = "numero_caracteres")
	private int numeroCaracteres;

	@Column(name = "truncar_caracteres")
	private boolean truncarCaracteres;

	@Column(name = "ignorar")
	private boolean ignorar;

	@Column(name = "incluir")
	private boolean incluir;

	@Column(name = "obligatorio_estructura")
	private boolean obligatorioEstructura;

	@Column(name = "ordinal_transformacion")
	private Integer ordinalTransformacion;

	@Column(name = "obligatorio_validacion")
	private boolean obligatorioValidacion;

	@Column(name = "id_mapa")
	private Long idMapa;

	@NotNull
	@Size(max = 200)
	@Column(name = "valor_predeterminado")
	private String valorPredeterminado;

	@NotNull
	@Size(max = 50)
	@Column(name = "formato")
	private String formato;

	@NotNull
	@Size(max = 1)
	@Column(name = "formato_numerico_separador_decimal")
	private String formatoNumericoSeparadorDecimal;

	@NotNull
	@Size(max = 1)
	@Column(name = "formato_numerico_separador_grupo")
	private String formatoNumericoSeparadorGrupo;

	@NotNull
	@Size(max = 50)
	@Column(name = "expresion_regular")
	private String expresionRegular;

	@NotNull
	@Size(max = 500)
	@Column(name = "valores_permitidos")
	private String valoresPermitidos;

	@NumberFormat
	@Column(name = "valor_entero_min")
	private Long valorEnteroMin;

	@NumberFormat
	@Column(name = "valor_entero_max")
	private Long valorEnteroMax;

	@NumberFormat
	@Column(name = "valor_decimal_min")
	private BigDecimal valorDecimalMin;

	@NumberFormat
	@Column(name = "valor_decimal_max")
	private BigDecimal valorDecimalMax;

	@Column(name = "valor_fecha_min")
	@DateTimeFormat(style = "M-")
	private LocalDate valorFechaMin;

	@Column(name = "valor_fecha_max")
	@DateTimeFormat(style = "M-")
	private LocalDate valorFechaMax;

	@Column(name = "valor_hora_min")
	@DateTimeFormat(style = "M-")
	private LocalTime valorHoraMin;

	@Column(name = "valor_hora_max")
	@DateTimeFormat(style = "M-")
	private LocalTime valorHoraMax;

	private boolean activo;
}
