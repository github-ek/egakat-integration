package com.egakat.integration.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.egakat.integration.enums.EstadoIntegracionType;
import com.egakat.integration.enums.EstadoNotificacionType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActualizacionDto extends IntegracionEntityDto {

	@NotNull
	private EstadoIntegracionType estadoIntegracion;

	@Size(max = 50)
	@NotNull
	private String subEstadoIntegracion;
	
	@NotNull
	private EstadoNotificacionType estadoNotificacion;

	private int entradasEnCola;
	
	private int reintentos;

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

	private String datos;

	public String getArg0() {
		if (arg0 == null) {
			arg0 = "";
		}
		return arg0;
	}

	public String getArg1() {
		if (arg1 == null) {
			arg1 = "";
		}
		return arg1;
	}

	public String getArg2() {
		if (arg2 == null) {
			arg2 = "";
		}
		return arg2;
	}

	public String getArg3() {
		if (arg3 == null) {
			arg3 = "";
		}
		return arg3;
	}

	public String getArg4() {
		if (arg4 == null) {
			arg4 = "";
		}
		return arg4;
	}

	public String getArg5() {
		if (arg5 == null) {
			arg5 = "";
		}
		return arg5;
	}

	public String getArg6() {
		if (arg6 == null) {
			arg6 = "";
		}
		return arg6;
	}

	public String getArg7() {
		if (arg7 == null) {
			arg7 = "";
		}
		return arg7;
	}

	public String getArg8() {
		if (arg8 == null) {
			arg8 = "";
		}
		return arg8;
	}

	public String getArg9() {
		if (arg9 == null) {
			arg9 = "";
		}
		return arg9;
	}

	public String getDatos() {
		if (datos == null) {
			datos = "";
		}
		return datos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		// @formatter:off
		builder
		.append("{")
		.append("  id : ").append(getId())
		.append(", integracion : '").append(getIntegracion())
		.append("', correlacion : '").append(getCorrelacion())
		.append("', idExterno : '").append(getIdExterno())
		.append("', estadoIntegracion : '").append(estadoIntegracion)
		.append("', subEstadoIntegracion : '").append(subEstadoIntegracion)
		.append("', estadoNotificacion : '").append(estadoNotificacion)
		.append("', entradasEnCola : ").append(entradasEnCola)
		.append(", reintentos : ").append(reintentos)
		.append(", arg0 : '").append(getArg0())
		.append("', arg1 : '").append(getArg1())
		.append("', arg2 : '").append(getArg2())
		.append("', arg3 : '").append(getArg3())
		.append("', arg4 : '").append(getArg4())
		.append("', arg5 : '").append(getArg5())
		.append("', arg6 : '").append(getArg6())
		.append("', arg7 : '").append(getArg7())
		.append("', arg8 : '").append(getArg8())
		.append("', arg9 : '").append(getArg9())
		.append("', datos : '").append(getDatos())
		.append("', fechaCreacion : '").append(getFechaCreacion())
		.append("', fechaModificacion : '").append(getFechaModificacion())
		.append("'}");
		// @formatter:on
		return builder.toString();
	}
}
