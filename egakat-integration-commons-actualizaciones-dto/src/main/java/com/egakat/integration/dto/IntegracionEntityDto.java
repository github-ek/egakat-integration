package com.egakat.integration.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.egakat.core.dto.SimpleAuditableEntityDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
abstract public class IntegracionEntityDto extends SimpleAuditableEntityDto<Long> {

	@NotNull
	@Size(max = 50)
	private String integracion;

	@NotNull
	@Size(max = 100)
	private String correlacion;

	@NotNull
	@Size(max = 100)
	private String idExterno;
}
