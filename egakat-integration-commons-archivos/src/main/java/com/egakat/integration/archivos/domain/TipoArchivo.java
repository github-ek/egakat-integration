package com.egakat.integration.archivos.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.NumberFormat;

import com.egakat.core.data.jpa.domain.AuditableEntity;
import com.egakat.core.domain.InactivableObject;
import com.egakat.core.domain.SortableObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tipos_archivo")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_tipo_archivo"))
public class TipoArchivo extends AuditableEntity<Long> implements SortableObject, InactivableObject {

	@Column(name = "id_grupo_tipo_archivo", nullable = false)
	private long grupoTipoArchivoId;

	@NotNull
	@Column(unique = true)
	@Size(max = 50)
	private String codigo;

	@NotNull
	@Size(max = 100)
	private String nombre;

	@NotNull
	@Size(max = 200)
	private String descripcion;

	@NotNull
	@Size(max = 4)
	@Column(name = "separador_registros")
	private String separadorRegistros;

	@NotNull
	@Size(max = 4)
	@Column(name = "separador_campos")
	private String separadorCampos;

	@NotNull
	@Size(max = 300)
	@Column(name = "subdirectorio_template")
	private String subdirectorioTemplate;

	@NotNull
	@Size(max = 50)
	private String regexp;

	@NotEmpty
	@Size(max = 300)
	@Column(name = "directorio_entradas")
	private String directorioEntradas;

	@NotEmpty
	@Size(max = 300)
	@Column(name = "directorio_temporal")
	private String directorioTemporal;

	@NotNull
	@Size(max = 300)
	@Column(name = "directorio_dump")
	private String directorioDump;

	@NotEmpty
	@Size(max = 300)
	@Column(name = "directorio_procesados")
	private String directorioProcesados;

	@NotEmpty
	@Size(max = 300)
	@Column(name = "directorio_errores")
	private String directorioErrores;

	@NotNull
	@Size(max = 300)
	@Column(name = "directorio_salidas")
	private String directorioSalidas;

	@NumberFormat
	private int ordinal;

	private boolean activo;
}