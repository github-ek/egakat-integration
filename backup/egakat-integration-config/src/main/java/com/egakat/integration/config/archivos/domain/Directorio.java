package com.egakat.integration.config.archivos.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.egakat.core.data.jpa.domain.AuditableEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "directorios")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_directorio"))
public class Directorio extends AuditableEntity<Long> {
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_tipo_archivo", nullable = false)
	@NotNull
    private TipoArchivo tipoArchivo;

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
}