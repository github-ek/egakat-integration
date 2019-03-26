package com.egakat.integration.config.archivos.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import com.egakat.core.domain.InactivableObject;
import com.egakat.core.domain.ObjectWithCode;
import com.egakat.core.domain.SortableObject;
import com.egakat.core.dto.AuditableEntityDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TipoArchivoDto extends AuditableEntityDto<Long>
		implements ObjectWithCode<Long>, SortableObject, InactivableObject {

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

	@Size(max = 200)
	private String aplicacion;
	
	@NumberFormat
	private int ordinal;

	private boolean activo;
}
