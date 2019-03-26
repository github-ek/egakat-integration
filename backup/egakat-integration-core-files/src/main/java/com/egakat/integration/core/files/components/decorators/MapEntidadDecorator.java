package com.egakat.integration.core.files.components.decorators;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.integration.commons.archivos.dto.ArchivoErrorDto;
import com.egakat.integration.commons.archivos.dto.EtlRequestDto;
import com.egakat.integration.commons.archivos.dto.RegistroDto;
import com.egakat.integration.config.archivos.dto.CampoDto;
import com.egakat.integration.config.archivos.enums.DatoType;
import com.egakat.integration.core.files.components.Constantes;
import com.egakat.integration.core.files.exceptions.EtlRuntimeException;

import lombok.val;

public abstract class MapEntidadDecorator<T extends IdentifiedDomainObject<ID>, ID extends Serializable>
		extends Decorator<T, ID> {

	public MapEntidadDecorator(Decorator<T, ID> inner) {
		super(inner);
	}

	@Override
	public EtlRequestDto<T, ID> transformar(EtlRequestDto<T, ID> request) {
		val result = super.transformar(request);
		Validate.notNull(result, Constantes.VALOR_NO_PUEDE_SER_NULO + "result");

		long archivo = result.getArchivo().getId();
		val registros = result.getRegistros();
		val errores = new ArrayList<ArchivoErrorDto>();

		for (val registro : registros) {
			try {
				val entidad = map(result, registro);
				registro.setEntidad(entidad);
			} catch (RuntimeException e) {
				val error = ArchivoErrorDto.error(archivo, e, registro.getNumeroLinea(), registro.getLinea());
				errores.add(error);
			}
		}

		if (!errores.isEmpty()) {
			throw new EtlRuntimeException(archivo,
					"Se detectaron errores en el archivo durante el mapeo de las entidades", errores);
		}

		return result;
	}

	protected abstract T map(EtlRequestDto<T, ID> archivo, RegistroDto<T, ID> registro);

	protected String getString(EtlRequestDto<T, ID> archivo, final RegistroDto<T, ID> registro, final String campo) {
		String result = null;
		val optional = getCampo(archivo, campo);
		if (optional.isPresent()) {
			result = registro.getDatos().get(optional.get().getCodigo());
		}
		return result;
	}

	protected Integer getInteger(EtlRequestDto<T, ID> archivo, final RegistroDto<T, ID> registro, final String campo) {
		Integer result = null;
		val valor = getString(archivo, registro, campo);
		if (!StringUtils.isEmpty(valor)) {
			
			val optional = getCampo(archivo, campo);
			if (optional.isPresent()) {
				if (optional.get().getTipoDato() == DatoType.DECIMAL) {
					val decimal = getBigDecimal(archivo, registro, campo);
					result = decimal.intValue();
				}
			}

			if (result == null) {
				result = Integer.parseInt(valor);
			}
		}
		return result;
	}

	protected Long getLong(EtlRequestDto<T, ID> archivo, final RegistroDto<T, ID> registro, final String campo) {
		Long result = null;
		val valor = getString(archivo, registro, campo);
		if (!StringUtils.isEmpty(valor)) {

			val optional = getCampo(archivo, campo);
			if (optional.isPresent()) {
				if (optional.get().getTipoDato() == DatoType.DECIMAL) {
					val decimal = getBigDecimal(archivo, registro, campo);
					result = decimal.longValue();
				}
			}

			if (result == null) {
				result = Long.parseLong(valor);
			}
		}
		return result;
	}

	protected BigDecimal getBigDecimal(EtlRequestDto<T, ID> archivo, final RegistroDto<T, ID> registro,
			final String campo) {
		BigDecimal result = null;
		val optional = getCampo(archivo, campo);
		if (optional.isPresent()) {
			val valor = registro.getDatos().get(optional.get().getCodigo());
			val format = optional.get().getDecimalFormat();

			if (!StringUtils.isEmpty(valor)) {
				try {
					result = (BigDecimal) format.parse(valor);
				} catch (ParseException e) {
					String mensaje = "Ocurrio un error al intentar hacer la conversión del dato %s";
					mensaje = String.format(mensaje, valor);
					throw new RuntimeException(mensaje, e);
				}
			}
		}
		return result;
	}

	protected LocalDateTime getLocalDateTime(EtlRequestDto<T, ID> archivo, final RegistroDto<T, ID> registro,
			final String campo) {
		LocalDateTime result = null;
		val optional = getCampo(archivo, campo);
		if (optional.isPresent()) {
			val valor = registro.getDatos().get(optional.get().getCodigo());
			val formatter = optional.get().getDateTimeFormatter();

			if (!StringUtils.isEmpty(valor)) {
				val temporalAccessor = formatter.parseBest(valor, LocalDateTime::from, LocalDate::from);

				if (temporalAccessor instanceof LocalDateTime) {
					result = (LocalDateTime) temporalAccessor;
				} else {
					result = ((LocalDate) temporalAccessor).atStartOfDay();
				}
			}
		}
		return result;
	}

	protected LocalDate getLocalDate(EtlRequestDto<T, ID> archivo, final RegistroDto<T, ID> registro,
			final String campo) {
		LocalDate result = null;
		val optional = getCampo(archivo, campo);
		if (optional.isPresent()) {
			val valor = registro.getDatos().get(optional.get().getCodigo());
			val format = optional.get().getDateTimeFormatter();

			if (!StringUtils.isEmpty(valor)) {
				result = LocalDate.parse(valor, format);
			}
		}
		return result;
	}

	protected LocalTime getLocalTime(EtlRequestDto<T, ID> archivo, final RegistroDto<T, ID> registro,
			final String campo) {
		LocalTime result = null;
		val optional = getCampo(archivo, campo);
		if (optional.isPresent()) {
			val valor = registro.getDatos().get(optional.get().getCodigo());
			val format = optional.get().getDateTimeFormatter();

			if (!StringUtils.isEmpty(valor)) {
				result = LocalTime.parse(valor, format);
			}
		}
		return result;
	}

	protected LocalTime getLocalTimeFromBigDecimal(EtlRequestDto<T, ID> archivo, final RegistroDto<T, ID> registro,
			final String campo) {
		LocalTime result = null;
		BigDecimal valor = this.getBigDecimal(archivo, registro, campo);

		if (valor != null) {
			Float floatValue = valor.floatValue();
			if (floatValue >= 1.0) {
				String mensaje = "La hora suministrada en el campo %s supera las 24 horas";
				mensaje = String.format(mensaje, campo);
				throw new RuntimeException(mensaje);
			}

			result = LocalTime.ofSecondOfDay((long) ((24L * 60L * 60L * 1L) * floatValue));
		}
		return result;
	}

	private Optional<CampoDto> getCampo(EtlRequestDto<T, ID> archivo, final String campo) {
		return archivo.getCampo(campo);
	}

}