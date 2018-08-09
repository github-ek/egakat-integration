package com.egakat.integration.files.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.egakat.commons.dto.BusinessEntityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class DirectorioDto extends BusinessEntityDto<Long>  {

	private long idTipoArchivo;

	@NotNull
	@Size(max = 300)
	private String subdirectorioTemplate;

	@NotNull
	@Size(max = 300)
	private String subdirectorioEntradas;

	@NotNull
	@Size(max = 300)
	private String subdirectorioTemporal;

	@NotNull
	@Size(max = 300)
	private String subdirectorioDump;

	@NotNull
	@Size(max = 300)
	private String subdirectorioProcesados;

	@NotNull
	@Size(max = 300)
	private String subdirectorioErrores;

	@NotNull
	@Size(max = 300)
	private String subdirectorioSalidas;

	@NotNull
	@Size(max = 50)
	private String regexp;

	@NotNull
	@Size(max = 50)
	private String expresionRegular;

	@NotNull
	@Size(max = 300)
	private String directorioEntradas;

	@NotNull
	@Size(max = 300)
	private String directorioTemporal;

	@NotNull
	@Size(max = 300)
	private String directorioDump;

	@NotNull
	@Size(max = 300)
	private String directorioProcesados;

	@NotNull
	@Size(max = 300)
	private String directorioErrores;

	@NotNull
	@Size(max = 300)
	private String directorioSalidas;

	public boolean isDump() {
		val result = StringUtils.isNotEmpty(this.getSubdirectorioDump());
		return result;
	}

	@Builder
	public DirectorioDto(Long id, int version, String creadoPor, LocalDateTime fechaCreacion, String modificadoPor,
			LocalDateTime fechaModificacion, long idTipoArchivo, @NotNull @Size(max = 300) String subdirectorioTemplate,
			@NotNull @Size(max = 300) String subdirectorioEntradas,
			@NotNull @Size(max = 300) String subdirectorioTemporal, @NotNull @Size(max = 300) String subdirectorioDump,
			@NotNull @Size(max = 300) String subdirectorioProcesados,
			@NotNull @Size(max = 300) String subdirectorioErrores,
			@NotNull @Size(max = 300) String subdirectorioSalidas, @NotNull @Size(max = 50) String regexp,
			@NotNull @Size(max = 50) String expresionRegular, @NotNull @Size(max = 300) String directorioEntradas,
			@NotNull @Size(max = 300) String directorioTemporal, @NotNull @Size(max = 300) String directorioDump,
			@NotNull @Size(max = 300) String directorioProcesados, @NotNull @Size(max = 300) String directorioErrores,
			@NotNull @Size(max = 300) String directorioSalidas) {
		super(id, version, creadoPor, fechaCreacion, modificadoPor, fechaModificacion);
		this.idTipoArchivo = idTipoArchivo;
		this.subdirectorioTemplate = subdirectorioTemplate;
		this.subdirectorioEntradas = subdirectorioEntradas;
		this.subdirectorioTemporal = subdirectorioTemporal;
		this.subdirectorioDump = subdirectorioDump;
		this.subdirectorioProcesados = subdirectorioProcesados;
		this.subdirectorioErrores = subdirectorioErrores;
		this.subdirectorioSalidas = subdirectorioSalidas;
		this.regexp = regexp;
		this.expresionRegular = expresionRegular;
		this.directorioEntradas = directorioEntradas;
		this.directorioTemporal = directorioTemporal;
		this.directorioDump = directorioDump;
		this.directorioProcesados = directorioProcesados;
		this.directorioErrores = directorioErrores;
		this.directorioSalidas = directorioSalidas;
	}
}
