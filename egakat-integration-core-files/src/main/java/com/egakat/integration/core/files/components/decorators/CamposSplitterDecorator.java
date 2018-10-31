package com.egakat.integration.core.files.components.decorators;

import static com.egakat.integration.core.files.components.Constantes.COLECCION_NO_PUEDE_ESTAR_VACIA;
import static com.egakat.integration.core.files.components.Constantes.VALOR_NO_PUEDE_SER_UNA_CADENA_VACIA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.egakat.core.domain.IdentifiedDomainObject;
import com.egakat.integration.commons.archivos.dto.ArchivoErrorDto;
import com.egakat.integration.commons.archivos.dto.EtlRequestDto;
import com.egakat.integration.commons.archivos.dto.RegistroDto;
import com.egakat.integration.commons.tiposarchivo.dto.CampoDto;
import com.egakat.integration.core.files.components.Constantes;
import com.egakat.integration.core.files.exceptions.EtlRuntimeException;

import lombok.val;

public class CamposSplitterDecorator<T extends IdentifiedDomainObject<ID>, ID extends Serializable>
		extends Decorator<T, ID> {

	public CamposSplitterDecorator(Decorator<T, ID> inner) {
		super(inner);
	}

	@Override
	public EtlRequestDto<T, ID> transformar(EtlRequestDto<T, ID> request) {
		val result = super.transformar(request);
		Validate.notNull(result, Constantes.VALOR_NO_PUEDE_SER_NULO + "result");

		val campos = result.getCamposNoIncluidos();
		Validate.notEmpty(campos, COLECCION_NO_PUEDE_ESTAR_VACIA + "campos");

		val regExpSeparadorCampos = result.getRegExpSeparadorCampos();
		Validate.notEmpty(regExpSeparadorCampos, VALOR_NO_PUEDE_SER_UNA_CADENA_VACIA + "regExpSeparadorCampos");

		val registros = result.getRegistros();
		val errores = new ArrayList<ArchivoErrorDto>();
		
		if (!registros.isEmpty()) {
			val mapping = getMapping(registros.get(0), campos, regExpSeparadorCampos);

			long archivo = result.getArchivo().getId();

			for (int i = 1; i < registros.size(); i++) {
				val registro = registros.get(i);

				try {
					val list = mapRegistro(archivo, registro, campos, mapping, regExpSeparadorCampos);
					errores.addAll(list);
				} catch (RuntimeException e) {
					val error = ArchivoErrorDto.error(archivo, e, registro.getNumeroLinea(), registro.getLinea());
					errores.add(error);
				}
			}

			if (!errores.isEmpty()) {
				throw new EtlRuntimeException(archivo,"Se detectaron errores al dividir el archivo en columnas", errores);
			}

			registros.remove(0);
		}

		return result;
	}

	private List<ArchivoErrorDto> mapRegistro(long archivoId, RegistroDto<T, ID> registro, List<CampoDto> campos,
			Map<String, Integer> mapping, String regExpSeparadorCampos) {

		val result = new ArrayList<ArchivoErrorDto>();

		try {
			val datos = new HashMap<String, String>();
			val linea = registro.getLinea().split(regExpSeparadorCampos, -1);

			for (val campo : campos) {
				try {
					mapCampo(datos, linea, campo, mapping);
				} catch (RuntimeException e) {

					String format = "Ocurrio el siguiente error al leer el campo %s en la posición %d:";
					String key = campo.getCodigo();

					val sb = new StringBuilder();
					sb.append(String.format(format, key, mapping.get(key)));
					if (e instanceof IndexOutOfBoundsException) {
						sb.append("No se encontró el campo %s en la posición %d.");
					} else {
						sb.append(e.getMessage());
					}
					val error = ArchivoErrorDto.error(archivoId, sb.toString(), registro.getNumeroLinea(),
							registro.getLinea());
					result.add(error);
				}
			}

			registro.getDatos().clear();
			registro.getDatos().putAll(datos);
		} catch (RuntimeException e) {
			val error = ArchivoErrorDto.error(archivoId, e, registro.getNumeroLinea(), registro.getLinea());
			result.add(error);
		}

		return result;
	}

	private void mapCampo(Map<String, String> datos, String[] linea, CampoDto campo, Map<String, Integer> mapping) {
		String key = campo.getCodigo();
		String value = getValorCampo(linea, key, mapping);

		if (value.isEmpty() && !campo.getValorPredeterminado().isEmpty()) {
			value = campo.getValorPredeterminado();
		}

		if (campo.isTruncarCaracteres()) {
			int numeroCaracteres = campo.getNumeroCaracteres();
			if (value.length() > numeroCaracteres) {
				value = StringUtils.left(value, numeroCaracteres);
			}
		}
		datos.put(key, value);
	}

	private String getValorCampo(String[] datos, String key, Map<String, Integer> mapping) {
		return datos[mapping.get(key)].trim();
	}

	private Map<String, Integer> getMapping(RegistroDto<T, ID> registro, List<CampoDto> campos, String separador) {
		Map<String, Integer> result = new HashMap<>();
		List<String> errores = new ArrayList<>();

		String[] datos = registro.getLinea().split(separador, -1);
		for (int i = 0; i < datos.length; i++) {
			datos[i] = datos[i].trim();
		}

		for (CampoDto campo : campos) {
			boolean encontrado = false;
			for (int i = 0; i < datos.length; i++) {
				if (campo.getNombre().equalsIgnoreCase(datos[i])) {
					result.put(campo.getCodigo(), i);
					encontrado = true;
					break;
				}
			}

			if (!encontrado) {
				errores.add(campo.getNombre());
			}
		}

		if (!errores.isEmpty()) {
			String mensaje = "error columnas: La siguientes columnas no se encontraron en el archivo: %s";
			throw new RuntimeException(String.format(mensaje, StringUtils.join(errores, ",")));
		}

		return result;
	}
}
