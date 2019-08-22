package com.egakat.integration.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

import com.egakat.core.web.client.exception.ReintentableException;
import com.egakat.integration.dto.ActualizacionDto;
import com.egakat.integration.dto.ErrorIntegracionDto;
import com.egakat.integration.dto.IntegracionEntityDto;
import com.egakat.integration.mapas.dto.MapaDto;
import com.egakat.integration.service.api.MapService;
import com.egakat.integration.service.api.crud.ActualizacionCrudService;
import com.egakat.integration.service.api.crud.ErrorIntegracionCrudService;
import com.egakat.integration.service.api.crud.IntegracionEntityCrudService;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract public class MapServiceImpl<I extends IntegracionEntityDto> implements MapService {

	@Autowired
	private ActualizacionCrudService actualizacionesService;

	@Autowired
	private ErrorIntegracionCrudService erroresService;

	protected ActualizacionCrudService getActualizacionesService() {
		return actualizacionesService;
	}

	protected ErrorIntegracionCrudService getErroresService() {
		return erroresService;
	}

	abstract protected IntegracionEntityCrudService<I> getCrudService();

	abstract protected String getIntegracion();

	protected String getOperacion() {
		val result = String.format("MAP %s", getIntegracion());
		return result;
	}

	@Override
	public boolean map() {
		boolean result = true;
		val operacion = getOperacion();

		val actualizaciones = getPendientes();
		int i = 1;
		int n = actualizaciones.size();

		log.info("{}: numero de pendientes={}", operacion, n);

		if (n > 0) {
			init(actualizaciones);

			for (val actualizacion : actualizaciones) {
				val errores = new ArrayList<ErrorIntegracionDto>();
				boolean retry = false;

				try {
					log(actualizacion, i, n);

					map(actualizacion, errores);
				} catch (RuntimeException e) {
					if (retry(actualizacion, e)) {
						retry = true;
						actualizacion.setReintentos(actualizacion.getReintentos() + 1);
					}

					val error = getErroresService().error(actualizacion, "", retry, e);
					errores.add(error);
				}

				if (!errores.isEmpty()) {
					if (retry) {
						result = false;
						onRetry(actualizacion, errores);
						updateOnRetry(actualizacion, errores);
					} else {
						onError(actualizacion, errores);
						updateOnError(actualizacion, errores);
					}
				}

				i++;
			}
		}
		return result;
	}

	protected void init(List<ActualizacionDto> actualizaciones) {

	}

	abstract protected List<ActualizacionDto> getPendientes();

	protected void map(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		I input = getInput(actualizacion, errores);

		if (errores.isEmpty()) {
			map(input, actualizacion, errores);

			if (errores.isEmpty()) {
				val discard = shouldBeDiscarded(input, actualizacion);

				if (!discard) {
					validate(input, actualizacion, errores);

					if (errores.isEmpty()) {
						onSuccess(input, actualizacion);
						updateOnSuccess(input, actualizacion);
					}
				} else {
					onDiscard(input, actualizacion);
					updateOnDiscard(input, actualizacion);
				}
			}

		}
	}

	protected I getInput(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		I result = getCrudService().findOneByIntegracionAndIdExterno(actualizacion.getIntegracion(),
				actualizacion.getIdExterno());
		return result;
	}

	protected abstract void map(I input, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores);

	protected boolean shouldBeDiscarded(I input, ActualizacionDto actualizacion) {
		return false;
	}

	protected abstract void validate(I input, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores);

	protected abstract void onSuccess(I input, ActualizacionDto actualizacion);

	protected abstract void updateOnSuccess(I input, ActualizacionDto actualizacion);

	private void onDiscard(I input, ActualizacionDto actualizacion) {

	}

	private void updateOnDiscard(I input, ActualizacionDto actualizacion) {
		getActualizacionesService().update(actualizacion);
	}

	protected abstract void onError(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores);

	private void updateOnError(ActualizacionDto actualizacion, ArrayList<ErrorIntegracionDto> errores) {
		try {
			getActualizacionesService().update(actualizacion, actualizacion.getEstadoIntegracion(), errores);
		} catch (RuntimeException e) {
			log.error("Exception:", e);
		}
	}

	protected void onRetry(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {

	}

	private void updateOnRetry(ActualizacionDto actualizacion, ArrayList<ErrorIntegracionDto> errores) {
		try {
			getActualizacionesService().update(actualizacion, actualizacion.getEstadoIntegracion(), errores);
		} catch (RuntimeException e) {
			log.error("Exception:", e);
		}
	}

	protected boolean retry(ActualizacionDto actualizacion, RuntimeException e) {
		boolean result = false;

		if (actualizacion.getReintentos() < getNumeroMaximoIntentos()) {
			if (e instanceof ReintentableException) {
				result = true;
			}

			if (e instanceof ResourceAccessException) {
				result = true;
			}
		}

		return result;
	}

	protected int getNumeroMaximoIntentos() {
		return 30;
	}

	protected void log(ActualizacionDto actualizacion, int i, int n) {
		if (actualizacion != null) {
			val format = "{}: Procesando {} de {}: id externo={}, contenido={} ";
			log.debug(format, getOperacion(), i, n, actualizacion.getIntegracion(), actualizacion.getIdExterno(),
					actualizacion);
		}
	}

	protected String defaultKey(String _default) {
		return StringUtils.defaultString(_default).toUpperCase();
	}

	protected String getValueFromMapOrDefault(Long id, String _default) {
		String result = _default;
		if (id != null) {
			val mapa = getMapa(id);
			if (mapa != null) {
				val value = mapa.getValores().get(_default);
				if (value != null) {
					result = value;
				}
			}
		}
		return result;
	}

	protected MapaDto getMapa(Long id) {
		return null;
	}

	protected ErrorIntegracionDto errorAtributoRequeridoNoSuministrado(IntegracionEntityDto input, String codigo,
			String... arg) {
		val mensaje = "Este atributo no admite valores nulos o vacios";
		val result = getErroresService().error(input, codigo, mensaje, arg);
		return result;
	}

	protected ErrorIntegracionDto errorAtributoNoHomologado(IntegracionEntityDto input, String codigo, String valor,
			String... arg) {
		val format = "Este atributo requiere ser homologado. Contiene el valor [%s], pero este valor no pudo ser homologado.";
		val mensaje = String.format(format, valor);
		val result = getErroresService().error(input, codigo, mensaje, arg);
		return result;
	}

	protected ErrorIntegracionDto errorAtributoMapeableNoHomologado(IntegracionEntityDto input, String codigo,
			String valor, long idMapa, String... arg) {
		val format = "Este atributo esta asociado al mapa de homologación con id=%d.Verifique que el valor [%s] exista en dicho mapa.";
		val mensaje = String.format(format, idMapa, valor);
		val result = getErroresService().error(input, codigo, mensaje, arg);
		return result;
	}

	protected ErrorIntegracionDto errorFechaPorDebajoDelMinimo(IntegracionEntityDto input, String codigo,
			LocalDate valor, LocalDate minino) {
		val format = "El valor de la fecha %s esta por debajo del valor minimo %s.";
		val mensaje = String.format(format, valor.toString(), minino.toString());
		val result = getErroresService().error(input, codigo, mensaje);
		return result;
	}

	protected ErrorIntegracionDto errorFechaPorEncimaDelMaximo(IntegracionEntityDto input, String codigo,
			LocalDate valor, LocalDate maximo) {
		val format = "El valor de la fecha %s esta por encima del valor maximo %s.";
		val mensaje = String.format(format, valor.toString(), maximo.toString());
		val result = getErroresService().error(input, codigo, mensaje);
		return result;
	}

	protected ErrorIntegracionDto errorNumeroPorDebajoDelMinimo(IntegracionEntityDto input, String codigo, int valor,
			int minino, String... arg) {
		val format = "El valor %d esta por debajo del valor minimo %d.";
		val mensaje = String.format(format, valor, minino);
		val result = getErroresService().error(input, codigo, mensaje, arg);
		return result;
	}

}