package com.egakat.integration.commons.tiposarchivo.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

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
public class LlaveDto extends AuditableEntityDto<Long> {

	private Long id;

	private long idTipoArchivo;

	@NotNull
	@Size(max = 50)
	private String codigo;

	@NumberFormat
	private int ordinal;

	private boolean activo;

	private List<Long> campos;

	public List<String> getCodigosDeCampos(List<CampoDto> campos) {
		val result = new ArrayList<String>();

		this.getCampos().forEach(id -> {
			val c = campos.stream().filter(b -> b.getId().equals(id)).findFirst();
			if (c.isPresent()) {
				result.add(c.get().getCodigo());
			} else {
				result.add("");
			}
		});

		return result;
	}
}