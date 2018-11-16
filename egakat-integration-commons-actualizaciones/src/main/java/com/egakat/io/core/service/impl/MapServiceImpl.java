package com.egakat.io.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import com.egakat.integration.config.mapas.dto.MapaDto;
import com.egakat.io.core.dto.ActualizacionDto;
import com.egakat.io.core.dto.ErrorIntegracionDto;
import com.egakat.io.core.dto.IntegracionEntityDto;
import com.egakat.io.core.enums.EstadoIntegracionType;
import com.egakat.io.core.service.api.MapService;
import com.egakat.io.core.service.api.crud.ActualizacionCrudService;
import com.egakat.io.core.service.api.crud.ErrorIntegracionCrudService;
import com.egakat.io.core.service.api.crud.ExtendedIntegracionEntityCrudService;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract public class MapServiceImpl<M extends IntegracionEntityDto> implements MapService {

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

	abstract protected ExtendedIntegracionEntityCrudService<M> getCrudService();

	abstract protected String getIntegracion();

	@Override
	public void map() {
		val actualizaciones = getPendientes();

		int i = 1;
		int n = actualizaciones.size();
		for (val actualizacion : actualizaciones) {
			log(actualizacion, i, n);

			val errores = new ArrayList<ErrorIntegracionDto>();

			map(actualizacion, errores);

			if (!errores.isEmpty()) {
				if (hasErrors(actualizacion, errores)) {
					onError(actualizacion, errores);
				} else {
					onRetry(actualizacion, errores);
				}

				try {
					getActualizacionesService().update(actualizacion, actualizacion.getEstadoIntegracion(), errores);
				} catch (RuntimeException e) {
					log.error("Exception:", e);
				}
			}

			i++;
		}
	}

	abstract protected List<ActualizacionDto> getPendientes();

	protected void map(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		try {
			val model = getModel(actualizacion, errores);

			if (errores.isEmpty()) {
				map(model, errores);

				if (errores.isEmpty()) {
					validate(model, errores);

					if (errores.isEmpty()) {
						onSuccess(model, actualizacion);
						getCrudService().update(model, actualizacion, actualizacion.getEstadoIntegracion());
					}
				}
			}
		} catch (RuntimeException e) {
			val error = getErroresService().error(actualizacion, "", e);
			errores.add(error);

			if (retry(e, actualizacion, errores)) {
				actualizacion.setReintentos(actualizacion.getReintentos());
			}
		}
	}

	protected M getModel(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		val result = getCrudService().findOneByIntegracionAndIdExterno(actualizacion.getIntegracion(),
				actualizacion.getIdExterno());
		return result;
	}

	abstract protected void map(M model, List<ErrorIntegracionDto> errores);

	abstract protected void validate(M model, List<ErrorIntegracionDto> errores);

	protected boolean retry(RuntimeException e, ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		boolean result = false;

		if (e instanceof HttpStatusCodeException) {
			if (((HttpStatusCodeException) e).getStatusCode() == HttpStatus.GATEWAY_TIMEOUT) {
				result = true;
			}
		}

		return result;
	}

	protected void onSuccess(M model, ActualizacionDto actualizacion) {
		actualizacion.setEstadoIntegracion(EstadoIntegracionType.VALIDADO);
		actualizacion.setReintentos(0);
	}

	protected void onError(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		actualizacion.setEstadoIntegracion(EstadoIntegracionType.ERROR_VALIDACION);
		actualizacion.setReintentos(0);
	}

	protected void onRetry(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {

	}

	protected boolean hasErrors(ActualizacionDto actualizacion, List<ErrorIntegracionDto> errores) {
		boolean result = false;
		if (!errores.isEmpty()) {
			if (actualizacion.getReintentos() == 0 || actualizacion.getReintentos() < getNumeroMaximoIntentos()) {
				result = true;
			}
		}
		return result;
	}

	protected int getNumeroMaximoIntentos() {
		return 30;
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

	protected ErrorIntegracionDto errorAtributoRequeridoNoSuministrado(IntegracionEntityDto model, String codigo,
			String... arg) {
		val mensaje = "Este atributo no admite valores nulos o vacios";
		val result = getErroresService().error(model, codigo, mensaje, arg);
		return result;
	}

	protected ErrorIntegracionDto errorAtributoNoHomologado(IntegracionEntityDto model, String codigo, String valor,
			String... arg) {
		val format = "Este atributo requiere ser homologado. Contiene el valor [%s], pero este valor no pudo ser homologado.";
		val mensaje = String.format(format, valor);
		val result = getErroresService().error(model, codigo, mensaje, arg);
		return result;
	}

	protected ErrorIntegracionDto errorAtributoMapeableNoHomologado(IntegracionEntityDto model, String codigo,
			String valor, long idMapa, String... arg) {
		val format = "Este atributo esta asociado al mapa de homologación con id=%d.Verifique que el valor [%s] exista en dicho mapa.";
		val mensaje = String.format(format, idMapa, valor);
		val result = getErroresService().error(model, codigo, mensaje, arg);
		return result;
	}

	protected void log(ActualizacionDto a, int i, int n) {
		val format = "integracion={}, correlacion={}, id externo={}: {} de {}.";
		log.debug(format, a.getIntegracion(), a.getCorrelacion(), a.getIdExterno(), i, n);
	}
}