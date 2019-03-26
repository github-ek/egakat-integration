package com.egakat.integration.suscripciones.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.egakat.core.dto.SimpleAuditableEntityDto;

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
public class SuscripcionDto extends SimpleAuditableEntityDto<Long> {

	@NotNull
	@Size(max = 50)
	private String suscripcion;

	@NotNull
	@Size(max = 100)
	private String idExterno;

	@Size(max = 50)
	@NotNull
	private String estadoSuscripcion;

	@Size(max = 100)
	private String arg0;

	@Size(max = 100)
	private String arg1;

	@Size(max = 100)
	private String arg2;

	@Size(max = 100)
	private String arg3;

	@Size(max = 100)
	private String arg4;

	@Size(max = 100)
	private String arg5;

	@Size(max = 100)
	private String arg6;

	@Size(max = 100)
	private String arg7;

	@Size(max = 100)
	private String arg8;

	@Size(max = 100)
	private String arg9;

	public void setArgs(String... args) {
		int n = Math.min(10, args.length);
		for (int i = 0; i < 10; i++) {
			val arg = (i < n && args[i] != null) ? args[i].trim() : "";
			switch (i) {
			case 0:
				arg0 = arg;
				break;
			case 1:
				arg1 = arg;
				break;
			case 2:
				arg2 = arg;
				break;
			case 3:
				arg3 = arg;
				break;
			case 4:
				arg4 = arg;
				break;
			case 5:
				arg5 = arg;
				break;
			case 6:
				arg6 = arg;
				break;
			case 7:
				arg7 = arg;
				break;
			case 8:
				arg8 = arg;
				break;
			case 9:
				arg9 = arg;
				break;
			default:
				break;
			}
		}
	}
}
