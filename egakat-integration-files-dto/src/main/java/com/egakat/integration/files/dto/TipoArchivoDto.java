package com.egakat.integration.files.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import com.egakat.commons.dto.EntityDto;
import com.egakat.core.domain.InactivableObject;
import com.egakat.core.domain.ObjectWithCode;
import com.egakat.core.domain.SortableObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TipoArchivoDto extends EntityDto<Long> implements ObjectWithCode<Long>, SortableObject, InactivableObject {

	private static final long serialVersionUID = 1L;

	private long idGrupoTipoArchivo;

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
	@Size(max = 4)
	private String separadorRegistros;

	@NotNull
	@Size(max = 4)
	private String separadorCampos;

	@NotNull
	@Size(max = 300)
	private String webApiUrlBase;

	@NumberFormat
	private int ordinal;

	private boolean activo;

	@Builder
	public TipoArchivoDto(Long id, int version, String creadoPor, LocalDateTime fechaCreacion, String modificadoPor,
			LocalDateTime fechaModificacion, long idGrupoTipoArchivo, @NotNull @Size(max = 50) String codigo,
			@NotNull @Size(max = 100) String nombre, @NotNull @Size(max = 200) String descripcion,
			@NotNull @Size(max = 4) String separadorRegistros, @NotNull @Size(max = 4) String separadorCampos,
			@NotNull @Size(max = 300) String webApiUrlBase, int ordinal, boolean activo) {
		super(id, version, creadoPor, fechaCreacion, modificadoPor, fechaModificacion);
		this.idGrupoTipoArchivo = idGrupoTipoArchivo;
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.separadorRegistros = separadorRegistros;
		this.separadorCampos = separadorCampos;
		this.webApiUrlBase = webApiUrlBase;
		this.ordinal = ordinal;
		this.activo = activo;
	}
}
