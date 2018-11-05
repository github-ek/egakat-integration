package com.egakat.integration.core.files.components.decorators;

import static com.egakat.integration.core.files.components.Constantes.COLECCION_NO_PUEDE_ESTAR_VACIA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.integration.commons.archivos.dto.ArchivoErrorDto;
import com.egakat.integration.commons.archivos.dto.RegistroDto;
import com.egakat.integration.config.archivos.dto.CampoDto;
import com.egakat.integration.config.archivos.dto.EtlRequestDto;
import com.egakat.integration.core.files.components.Constantes;
import com.egakat.integration.core.files.components.checkers.ExpresionRegularChecker;
import com.egakat.integration.core.files.components.checkers.NumeroMaximoDeCaracteresChecker;
import com.egakat.integration.core.files.components.checkers.TipoDeDatoChecker;
import com.egakat.integration.core.files.components.checkers.ValorObligatorioChecker;
import com.egakat.integration.core.files.components.checkers.ValoresPermitidosChecker;
import com.egakat.integration.core.files.exceptions.EtlRuntimeException;

import lombok.val;

public class CheckRestriccionesDeCamposDecorator<T extends IdentifiedDomainObject<ID>, ID extends Serializable>
		extends Decorator<T, ID> {

	public CheckRestriccionesDeCamposDecorator(Decorator<T, ID> inner) {
		super(inner);
	}

	@Override
	public EtlRequestDto<T, ID> transformar(EtlRequestDto<T, ID> request) {
		val result = super.transformar(request);
		Validate.notNull(result, Constantes.VALOR_NO_PUEDE_SER_NULO + "result");

		val campos = result.getCampos();
		Validate.notEmpty(campos, COLECCION_NO_PUEDE_ESTAR_VACIA + "result.getCampos()");

		long archivo = result.getArchivo().getId();
		val registros = result.getRegistros();
		val errores = new ArrayList<ArchivoErrorDto>();

		for (val registro : registros) {
			val list = checkRegistro(archivo, registro, campos);
			errores.addAll(list);
		}

		if (!errores.isEmpty()) {
			throw new EtlRuntimeException(archivo,"Se detectaron errores en el archivo por violación de las restricciones de sus campos", errores);
		}

		return result;
	}

	private List<ArchivoErrorDto> checkRegistro(long archivoId, RegistroDto<T, ID> registro, List<CampoDto> campos) {
		val result = new ArrayList<ArchivoErrorDto>();
		val datos = registro.getDatos();
		val numeroLinea = registro.getNumeroLinea();
		val linea = registro.getLinea();

		for (val campo : campos) {
			if (campo.isIgnorar()) {
				continue;
			}
			val valor = datos.get(campo.getCodigo());

			try {
				(new NumeroMaximoDeCaracteresChecker()).check(campo, valor);
			} catch (RuntimeException e) {
				val error = ArchivoErrorDto.error(archivoId, e, numeroLinea, linea);
				result.add(error);
			}

			try {
				(new TipoDeDatoChecker()).check(campo, valor);
			} catch (RuntimeException e) {
				val error = ArchivoErrorDto.error(archivoId, e, numeroLinea, linea);
				result.add(error);
			}

			try {
				(new ValorObligatorioChecker()).check(campo, valor);
			} catch (RuntimeException e) {
				val error = ArchivoErrorDto.error(archivoId, e, numeroLinea, linea);
				result.add(error);
			}

			try {
				(new ExpresionRegularChecker()).check(campo, valor);
			} catch (RuntimeException e) {
				val error = ArchivoErrorDto.error(archivoId, e, numeroLinea, linea);
				result.add(error);
			}

			try {
				(new ValoresPermitidosChecker()).check(campo, valor);
			} catch (RuntimeException e) {
				val error = ArchivoErrorDto.error(archivoId, e, numeroLinea, linea);
				result.add(error);
			}
		}

		return result;
	}
}
