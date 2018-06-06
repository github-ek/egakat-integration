package com.egakat.integration.core.files.components.decorators;

import static com.egakat.integration.core.files.components.Constantes.VALOR_NO_PUEDE_SER_UNA_CADENA_VACIA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.integration.core.files.components.Constantes;
import com.egakat.integration.core.files.exceptions.EtlRuntimeException;
import com.egakat.integration.files.dto.ArchivoErrorDto;
import com.egakat.integration.files.dto.EtlRequestDto;
import com.egakat.integration.files.dto.RegistroDto;

import lombok.val;

public class CheckNumeroDeColumnasDecorator<T extends IdentifiedDomainObject<ID>, ID extends Serializable>
		extends Decorator<T, ID> {

	public CheckNumeroDeColumnasDecorator(Decorator<T, ID> inner) {
		super(inner);
	}

	@Override
	public EtlRequestDto<T, ID> transformar(EtlRequestDto<T, ID> request) {
		val result = super.transformar(request);
		Validate.notNull(result, Constantes.VALOR_NO_PUEDE_SER_NULO + "result");

		String regExpSeparadorCampos = result.getRegExpSeparadorCampos();
		Validate.notEmpty(regExpSeparadorCampos, VALOR_NO_PUEDE_SER_UNA_CADENA_VACIA + "regExpSeparadorCampos");

		val registros = result.getRegistros();
		val errores = new ArrayList<ArchivoErrorDto>();

		if (!registros.isEmpty()) {
			long archivo = result.getArchivo().getId();
			val linea = registros.get(0).getLinea().split(regExpSeparadorCampos, -1);
			int numeroColumnas = linea.length;

			val list = checkNumeroColumnas(archivo, registros, numeroColumnas, regExpSeparadorCampos);
			errores.addAll(list);
			
			if (!errores.isEmpty()) {
				throw new EtlRuntimeException(archivo,"Se detectaron errores en el archivo por diferencias en el numero de columnas entre registros", errores);
			}
		}

		return result;
	}

	private List<ArchivoErrorDto> checkNumeroColumnas(long archivoId, List<RegistroDto<T, ID>> registros,
			int numeroColumnas, String regExpSeparadorCampos) {
		val result = new ArrayList<ArchivoErrorDto>();

		for (val registro : registros) {
			val valores = registro.getLinea().split(regExpSeparadorCampos, -1);
			int n = valores.length;

			if (n != numeroColumnas) {
				val format = "error número de columnas: Se esperaban %d columnas, pero el registro contiene %d";
				val mensaje = String.format(format, numeroColumnas, n);
				val error = ArchivoErrorDto.error(archivoId, mensaje, registro.getNumeroLinea(), registro.getLinea());
				result.add(error);
			}
		}

		return result;
	}
}
