package com.egakat.integration.core.files.components.decorators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.util.StringUtils;

import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.integration.commons.archivos.dto.ArchivoErrorDto;
import com.egakat.integration.commons.archivos.dto.RegistroDto;
import com.egakat.integration.config.archivos.dto.EtlRequestDto;
import com.egakat.integration.core.files.components.Constantes;
import com.egakat.integration.core.files.exceptions.EtlRuntimeException;

import lombok.val;

public class CheckRegistrosDuplicadosDecorator<T extends IdentifiedDomainObject<ID>, ID extends Serializable>
		extends Decorator<T, ID> {

	public CheckRegistrosDuplicadosDecorator(Decorator<T, ID> inner) {
		super(inner);
	}

	@Override
	public EtlRequestDto<T, ID> transformar(EtlRequestDto<T, ID> request) {
		val result = super.transformar(request);
		Validate.notNull(result, Constantes.VALOR_NO_PUEDE_SER_NULO + "result");

		val llaves = result.getLlaves();

		if (!llaves.isEmpty()) {
			val registros = result.getRegistros();
			val errores = new ArrayList<ArchivoErrorDto>();

			if (!registros.isEmpty()) {
				long archivo = result.getArchivo().getId();

				for (val llave : llaves) {
					val camposUnicos = llave.getCodigosDeCampos(result.getCampos());
					val list = checkValoresDuplicados(archivo, registros, camposUnicos);
					errores.addAll(list);
				}

				if (!errores.isEmpty()) {
					throw new EtlRuntimeException(archivo, "Se detectaron errores en el archivos por datos duplicados",
							errores);
				}
			}
		}

		return result;
	}

	private List<ArchivoErrorDto> checkValoresDuplicados(long archivoId, List<RegistroDto<T, ID>> registros,
			List<String> camposUnicos) {
		val result = new ArrayList<ArchivoErrorDto>();
		val map = new HashMap<String, List<RegistroDto<T, ID>>>();

		if (!camposUnicos.isEmpty()) {
			for (val registro : registros) {
				String key = getKey(registro, camposUnicos);

				if (!map.containsKey(key)) {
					map.put(key, new ArrayList<>());
				}
				map.get(key).add(registro);
			}

			val mensaje = getMensajeValoresDuplicados(camposUnicos);

			// @formatter:off
			map.values().stream()
			.filter(value -> value.size() > 1)
			.forEach(value -> {
				value.forEach(registro ->{
					result.add(ArchivoErrorDto.error(archivoId, mensaje, registro.getNumeroLinea(),registro.getLinea()));	
				});
			});
			// @formatter:on
		}

		return result;
	}

	private String getKey(RegistroDto<T, ID> registro, List<String> campos) {
		val sb = new StringBuilder();
		for (String campo : campos) {
			sb.append(registro.getDatos().get(campo)).append("|");
		}
		val result = sb.toString();
		return result;
	}

	private String getMensajeValoresDuplicados(final List<String> campos) {
		String mensaje;
		mensaje = "error llave duplicada: Los valores de los siguientes campos vienen duplicados en el archivo: %s";
		mensaje = String.format(mensaje, StringUtils.collectionToCommaDelimitedString(campos));
		return mensaje;
	}

}