package com.egakat.integration.files.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.egakat.commons.dto.BusinessEntityDto;
import com.egakat.integration.files.enums.EstadoArchivoType;

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
public class ArchivoDto extends BusinessEntityDto<Long>  {

	private long idTipoArchivo;

	@NotNull
	@Size(max = 300)
	private String nombre;

	private EstadoArchivoType estado;

	@NotNull
	@Size(max = 1024)
	private String ruta;

	@Builder
	public ArchivoDto(Long id, int version, String creadoPor, LocalDateTime fechaCreacion, String modificadoPor,
			LocalDateTime fechaModificacion, long idTipoArchivo, @NotNull @Size(max = 300) String nombre,
			EstadoArchivoType estado, @NotNull @Size(max = 1024) String ruta) {
		super(id, version, fechaCreacion, creadoPor, fechaModificacion, modificadoPor);
		this.idTipoArchivo = idTipoArchivo;
		this.nombre = nombre;
		this.estado = estado;
		this.ruta = ruta;
	}
}