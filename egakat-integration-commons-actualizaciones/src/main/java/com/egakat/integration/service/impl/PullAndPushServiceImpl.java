package com.egakat.integration.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.egakat.integration.dto.ActualizacionDto;
import com.egakat.integration.dto.ErrorIntegracionDto;

import lombok.Getter;
import lombok.val;

public abstract class PullAndPushServiceImpl<P, I, O, R> extends PushServiceImpl<I, O, R> {

	@Getter
	private P input;

	@Override
	final protected List<ActualizacionDto> getPendientes() {
		val correlacion = defaultCorrelacion();
		val errores = new ArrayList<ErrorIntegracionDto>();

		initInput(correlacion, errores);

		val result = enqueue(correlacion, errores);

		if (!errores.isEmpty()) {
			getErroresService().create(errores);
		}

		return result;
	}

	protected String defaultCorrelacion() {
		val result = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString();
		return result;
	}

	protected void initInput(String correlacion, List<ErrorIntegracionDto> errores) {
		this.input = null;

		try {
			this.input = pull(correlacion, errores);
		} catch (RuntimeException e) {
			handlePullException(correlacion, errores, e);
		}
	}

	abstract protected P pull(String correlacion, List<ErrorIntegracionDto> errores);

	protected void handlePullException(String correlacion, List<ErrorIntegracionDto> errores, RuntimeException e) {
		boolean ignorar = isRetryableException(e);
		val error = getErroresService().error(getIntegracion(), correlacion, "", "", ignorar, e);
		errores.add(error);
	}

	protected List<ActualizacionDto> enqueue(String correlacion, List<ErrorIntegracionDto> errores) {
		val result = new ArrayList<ActualizacionDto>();
		val models = asModels(correlacion, this.getInput());

		for (val model : models) {
			try {
				val actualizacion = getActualizacionesService().enqueue(model);
				switch (actualizacion.getEstadoIntegracion()) {
				case NO_PROCESADO:
					result.add(actualizacion);
					break;
				default:
					break;
				}
			} catch (RuntimeException e) {
				handleEnqueueException(correlacion, errores, e);
			}
		}

		return result;
	}

	abstract protected List<ActualizacionDto> asModels(String correlacion, P inputs);

	protected void handleEnqueueException(String correlacion, List<ErrorIntegracionDto> errores, RuntimeException e) {
		val error = getErroresService().error(getIntegracion(), correlacion, "", "", false, e);
		errores.add(error);
	}

}
