package com.egakat.integration.commons.archivos.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.egakat.core.dto.AuditableEntityDto;
import com.egakat.integration.commons.archivos.enums.EstadoArchivoType;

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
public class ArchivoDto extends AuditableEntityDto<Long>  {

	private long idTipoArchivo;

	@NotNull
	@Size(max = 300)
	private String nombre;

	private EstadoArchivoType estado;

	@NotNull
	@Size(max = 1024)
	private String ruta;
}