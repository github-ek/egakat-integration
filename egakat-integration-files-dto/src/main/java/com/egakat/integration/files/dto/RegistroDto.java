package com.egakat.integration.files.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.egakat.commons.dto.BusinessEntityDto;
import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.integration.files.enums.EstadoRegistroType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDto<T extends IdentifiedDomainObject<ID>, ID extends Serializable> extends BusinessEntityDto<Long> {

	private Long idArchivo;

	private EstadoRegistroType estado;

	private int numeroLinea;

	private String idCorrelacion;

	@Setter
	@NonNull
	private String linea;

	@NonNull
	private Map<String, String> datos = new HashMap<>();

	@Setter
	private T entidad = null;

	public RegistroDto(int numeroLinea, String linea) {
		super();
		this.numeroLinea = numeroLinea;
		this.linea = linea;
	}
}
