package com.egakat.integration.commons.archivos.dto;

import java.util.HashMap;
import java.util.Map;

import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.core.dto.AuditableEntityDto;
import com.egakat.integration.commons.archivos.enums.EstadoRegistroType;

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
public class RegistroDto<T extends IdentifiedDomainObject<ID>, ID> extends AuditableEntityDto<Long> {

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
