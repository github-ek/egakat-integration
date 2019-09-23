package com.egakat.integration.archivos.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.egakat.core.dto.AuditableEntityDto;

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
public class DirectorioDto extends AuditableEntityDto<Long> {

	private long idTipoArchivo;

	@NotNull
	@Size(max = 300)
	private String subdirectorioTemplate;

	@NotNull
	@Size(max = 50)
	private String regexp;

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
		val result = StringUtils.isNotEmpty(this.getDirectorioDump());
		return result;
	}
}
