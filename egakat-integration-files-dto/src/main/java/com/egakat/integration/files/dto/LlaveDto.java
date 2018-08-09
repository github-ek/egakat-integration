package com.egakat.integration.files.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

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
public class LlaveDto extends BusinessEntityDto<Long>  {

	private Long id;

	private long idTipoArchivo;

	@NotNull
	@Size(max = 50)
	private String codigo;

	@NumberFormat
	private int ordinal;

	private boolean activo;

	private List<Long> campos;

	public static class LlaveDtoBuilder {
		private List<Long> campos = new ArrayList<>();
	}

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

	@Builder
	public LlaveDto(Long id, int version, String creadoPor, LocalDateTime fechaCreacion, String modificadoPor,
			LocalDateTime fechaModificacion, Long id2, long idTipoArchivo, @NotNull @Size(max = 50) String codigo,
			int ordinal, boolean activo, List<Long> campos) {
		super(id, version, creadoPor, fechaCreacion, modificadoPor, fechaModificacion);
		id = id2;
		this.idTipoArchivo = idTipoArchivo;
		this.codigo = codigo;
		this.ordinal = ordinal;
		this.activo = activo;
		this.campos = campos;
	}
}
