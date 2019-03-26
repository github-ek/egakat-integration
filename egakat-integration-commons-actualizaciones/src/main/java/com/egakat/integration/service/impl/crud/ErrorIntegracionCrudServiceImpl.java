package com.egakat.integration.service.impl.crud;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.left;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.domain.ErrorIntegracion;
import com.egakat.integration.dto.ErrorIntegracionDto;
import com.egakat.integration.dto.IntegracionEntityDto;
import com.egakat.integration.enums.EstadoNotificacionType;
import com.egakat.integration.repository.ErrorIntegracionRepository;
import com.egakat.integration.service.api.crud.ErrorIntegracionCrudService;

import lombok.val;

@Service
public class ErrorIntegracionCrudServiceImpl extends CrudServiceImpl<ErrorIntegracion, ErrorIntegracionDto, Long>
		implements ErrorIntegracionCrudService {

	@Autowired
	private ErrorIntegracionRepository repository;

	@Override
	protected ErrorIntegracionRepository getRepository() {
		return repository;
	}

	@Override
	protected ErrorIntegracionDto asModel(ErrorIntegracion entity) {
		val model = newModel();

		mapModel(entity, model);
		
		model.setId(entity.getId());
		model.setIntegracion(entity.getIntegracion());
		model.setCorrelacion(entity.getCorrelacion());
		model.setIdExterno(entity.getIdExterno());

		model.setEstadoNotificacion(entity.getEstadoNotificacion());
		model.setFechaNotificacion(entity.getFechaNotificacion());

		model.setCodigo(entity.getCodigo());
		model.setMensaje(entity.getMensaje());

		model.setArg0(entity.getArg0());
		model.setArg1(entity.getArg1());
		model.setArg2(entity.getArg2());
		model.setArg3(entity.getArg3());
		model.setArg4(entity.getArg4());
		model.setArg5(entity.getArg5());
		model.setArg6(entity.getArg6());
		model.setArg7(entity.getArg7());
		model.setArg8(entity.getArg8());
		model.setArg9(entity.getArg9());

		return model;
	}

	@Override
	protected ErrorIntegracion mergeEntity(ErrorIntegracionDto model, ErrorIntegracion entity) {

		entity.setIntegracion(model.getIntegracion());
		entity.setCorrelacion(model.getCorrelacion());
		entity.setIdExterno(model.getIdExterno());

		entity.setEstadoNotificacion(model.getEstadoNotificacion());
		entity.setFechaNotificacion(model.getFechaNotificacion());

		entity.setCodigo(model.getCodigo());
		entity.setMensaje(model.getMensaje());

		entity.setArg0(model.getArg0());
		entity.setArg1(model.getArg1());
		entity.setArg2(model.getArg2());
		entity.setArg3(model.getArg3());
		entity.setArg4(model.getArg4());
		entity.setArg5(model.getArg5());
		entity.setArg6(model.getArg6());
		entity.setArg7(model.getArg7());
		entity.setArg8(model.getArg8());
		entity.setArg9(model.getArg9());

		return entity;
	}

	@Override
	protected ErrorIntegracion newEntity() {
		return new ErrorIntegracion();
	}

	@Override
	public List<ErrorIntegracionDto> findAll(IntegracionEntityDto model) {
		val entities = getRepository().findAllByIntegracionAndIdExternoAndCorrelacion(model.getIntegracion(),
				model.getIdExterno(), model.getCorrelacion());
		val result = asModels(entities);
		return result;
	}

	@Override
	public List<ErrorIntegracionDto> findAllByEstadoNotificacionIn(String integracion,
			EstadoNotificacionType... estados) {
		val entities = getRepository().findAllByIntegracionAndEstadoNotificacionIn(integracion, estados);
		val result = asModels(entities);
		return result;
	}

	@Override
	public List<ErrorIntegracionDto> findAllByEstadoNotificacionIn(EstadoNotificacionType... estados) {
		val entities = getRepository().findAllByEstadoNotificacionIn(estados);
		val result = asModels(entities);
		return result;
	}

	@Override
	public List<ErrorIntegracionDto> findAllByEstadoNotificacionIn(LocalDateTime fechaDesde,
			EstadoNotificacionType... estados) {
		val entities = getRepository().findAllByEstadoNotificacionInAndFechaCreacionGreaterThanEqual(estados,
				fechaDesde);
		val result = asModels(entities);
		return result;
	}

	// -----------------------------------------------------------------------------------------------------
	@Override
	public void create(IntegracionEntityDto model, String codigo, String mensaje, String... arg) {
		create(model, codigo, false, mensaje, arg);
	}

	@Override
	public void create(String integracion, String correlacion, String codigo, String mensaje, String... arg) {
		create(integracion, correlacion, codigo, false, mensaje, arg);
	}

	@Override
	public void create(IntegracionEntityDto model, String codigo, boolean ignorar, String mensaje, String... arg) {
		val error = error(model, codigo, ignorar, mensaje, arg);
		create(error);
	}

	@Override
	public void create(String integracion, String correlacion, String codigo, boolean ignorar, String mensaje,
			String... arg) {
		val error = error(integracion, correlacion, "", codigo, ignorar, mensaje, arg);
		create(error);
	}

	// -----------------------------------------------------------------------------------------------------
	@Override
	public void create(IntegracionEntityDto model, String codigo, Throwable t) {
		create(model, codigo, false, t);
	}

	@Override
	public void create(String integracion, String correlacion, String codigo, Throwable t) {
		create(integracion, correlacion, codigo, false, t);
	}

	@Override
	public void create(IntegracionEntityDto model, String codigo, boolean ignorar, Throwable t) {
		val error = error(model, codigo, ignorar, t);
		create(error);
	}

	@Override
	public void create(String integracion, String correlacion, String codigo, boolean ignorar, Throwable t) {
		val error = error(integracion, correlacion, "", codigo, ignorar, t);
		create(error);
	}

	// -----------------------------------------------------------------------------------------------------
	@Override
	public ErrorIntegracionDto error(IntegracionEntityDto model, String codigo, String mensaje, String... arg) {
		return error(model, codigo, false, mensaje, arg);
	}

	@Override
	public ErrorIntegracionDto error(IntegracionEntityDto model, String codigo, Throwable t) {
		return error(model, codigo, false, t);
	}

	@Override
	public ErrorIntegracionDto error(IntegracionEntityDto model, String codigo, boolean ignorar, String mensaje,
			String... arg) {
		val result = error(model.getIntegracion(), model.getCorrelacion(), model.getIdExterno(), codigo, ignorar,
				mensaje, arg);
		return result;
	}

	@Override
	public ErrorIntegracionDto error(IntegracionEntityDto model, String codigo, boolean ignorar, Throwable t) {
		val result = error(model.getIntegracion(), model.getCorrelacion(), model.getIdExterno(), codigo, ignorar, t);
		return result;
	}

	@Override
	public ErrorIntegracionDto error(String integracion, String correlacion, String idExterno, String codigo,
			boolean ignorar, Throwable t) {
		String c = isEmpty(codigo) ? t.getClass().getName() : codigo;
		String msg = t.getMessage();

		if (t instanceof HttpStatusCodeException) {
			val e = (HttpStatusCodeException) t;
			c = e.getStatusCode().toString();
			msg = e.getResponseBodyAsString();
		}else {
			if (t instanceof ResourceAccessException) {
				val e = (ResourceAccessException) t;
				msg = e.getMessage();
			} else {
			if(t instanceof NestedRuntimeException) {
				val e = (NestedRuntimeException)t;
				if(e.getMostSpecificCause() !=null) {
					msg = e.getMostSpecificCause().getMessage();
				}
			}
		}
		}

		val result = error(integracion, correlacion, idExterno, c, ignorar, msg);
		return result;
	}

	protected ErrorIntegracionDto error(String integracion, String correlacion, String idExterno, String codigo,
			boolean ignorar, String mensaje, String... arg) {
		val argumentos = normalizarArgumentos(arg);

		val result = new ErrorIntegracionDto();
		val estado = (ignorar) ? EstadoNotificacionType.IGNORAR : EstadoNotificacionType.NOTIFICAR;

		result.setIntegracion(integracion);
		result.setCorrelacion(correlacion);
		result.setIdExterno(idExterno);
		result.setEstadoNotificacion(estado);
		result.setCodigo(left(codigo, 100));
		result.setMensaje(StringUtils.defaultString(mensaje));
		result.setArg0(argumentos[0]);
		result.setArg1(argumentos[1]);
		result.setArg2(argumentos[2]);
		result.setArg3(argumentos[3]);
		result.setArg4(argumentos[4]);
		result.setArg5(argumentos[5]);
		result.setArg6(argumentos[6]);
		result.setArg7(argumentos[7]);
		result.setArg8(argumentos[8]);
		result.setArg9(argumentos[9]);

		return result;
	}

	protected static String[] normalizarArgumentos(String... arg) {
		val result = new String[10];

		int n = Math.min(result.length, arg.length);
		for (int i = 0; i < result.length; i++) {
			if (i < n) {
				result[i] = arg[i];
			} else {
				result[i] = "";
			}
		}

		return result;
	}

	@Override
	protected ErrorIntegracionDto newModel() {
		return new ErrorIntegracionDto();
	}
}