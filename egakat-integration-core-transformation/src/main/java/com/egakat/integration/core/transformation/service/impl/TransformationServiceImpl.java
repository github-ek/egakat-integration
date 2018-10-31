package com.egakat.integration.core.transformation.service.impl;

import static com.egakat.integration.commons.archivos.enums.EstadoRegistroType.DESCARTADO;
import static com.egakat.integration.commons.archivos.enums.EstadoRegistroType.ERROR_ENRIQUECIMIENTO;
import static com.egakat.integration.commons.archivos.enums.EstadoRegistroType.ERROR_HOMOLOGACION;
import static com.egakat.integration.commons.archivos.enums.EstadoRegistroType.ERROR_VALIDACION;
import static com.egakat.integration.commons.archivos.enums.EstadoRegistroType.HOMOLOGADO;
import static com.egakat.integration.commons.archivos.enums.EstadoRegistroType.PROCESADO;
import static com.egakat.integration.commons.archivos.enums.EstadoRegistroType.VALIDADO;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.egakat.integration.commons.archivos.domain.Registro;
import com.egakat.integration.commons.archivos.dto.ArchivoErrorDto;
import com.egakat.integration.commons.archivos.enums.EstadoRegistroType;
import com.egakat.integration.commons.archivos.repository.RegistroRepository;
import com.egakat.integration.commons.archivos.service.api.ArchivoCrudService;
import com.egakat.integration.commons.mapas.service.api.MapaCrudService;
import com.egakat.integration.commons.tiposarchivo.dto.CampoDto;
import com.egakat.integration.commons.tiposarchivos.service.api.CampoCrudService;
import com.egakat.integration.commons.tiposarchivos.service.api.TipoArchivoCrudService;
import com.egakat.integration.core.transformation.service.api.TransformationService;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
public abstract class TransformationServiceImpl<T extends Registro> implements TransformationService<T> {

	protected List<EstadoRegistroType> ESTADOS_REGISTROS = asList(EstadoRegistroType.ESTRUCTURA_VALIDA, EstadoRegistroType.CORREGIDO);

	@Autowired
	private ArchivoCrudService archivoService;

	protected ArchivoCrudService getArchivoService() {
		return archivoService;
	}

	@Autowired
	private TipoArchivoCrudService tipoArchivoLocalService;

	@Autowired
	private CampoCrudService camposService;

	protected TipoArchivoCrudService getTipoArchivoLocalService() {
		return tipoArchivoLocalService;
	}

	@Autowired
	private MapaCrudService mapasService;

	protected MapaCrudService getMapasService() {
		return mapasService;
	}

	abstract protected RegistroRepository<T> getRepository();

	protected List<EstadoRegistroType> getEstados() {
		return ESTADOS_REGISTROS;
	}

	@Override
	public List<Long> getArchivosPendientes() {
		val result = getRepository().findAllArchivoIdByEstadoIn(getEstados());
		return result;
	}

	@Override
	public void transformar(Long archivoId) {
		val archivo = getArchivoService().findOneById(archivoId);
		val campos = camposService.findAllByTipoArchivoId(archivo.getIdTipoArchivo());
		val errores = new ArrayList<ArchivoErrorDto>();

		try {
			val registros = getRepository().findAllByIdArchivoAndEstadoNotIn(archivoId, asList(PROCESADO));

			log.debug("beforeTranslateRows");
			beforeTranslateRows(registros, errores, campos);
			discard(registros);

			log.debug("translateRows");
			translateRows(registros, errores, campos);
			discard(registros);

			log.debug("beforeValidateRows");
			beforeValidateRows(registros, errores, campos);
			discard(registros);

			log.debug("validateRows");
			validateRows(registros, errores, campos);
			discard(registros);

			log.debug("validateGroups");
			validateGroups(registros, errores, campos);
			discard(registros);

			log.debug("saveRows");
			saveRows(registros);
		} catch (RuntimeException e) {
			errores.add(ArchivoErrorDto.error(archivoId, e));
		} finally {
			getArchivoService().registrarResultadosValidacionNegocio(archivo, errores);
		}
	}

	// -------------------------------------------------------------------------------------
	// BEFORE TRANSLATE RECORDS
	// -------------------------------------------------------------------------------------
	final protected void beforeTranslateRows(List<T> registros, List<ArchivoErrorDto> errores, List<CampoDto> campos) {

		registros.parallelStream().filter(registro -> filter(registro)).forEach(registro -> {
			boolean success = true;

			try {
				success &= beforeTranslateRow(registro, errores, campos);
			} catch (RuntimeException e) {
				error(registro, errores, e);
				success = false;
			}

			if (!success) {
				registro.setEstado(ERROR_ENRIQUECIMIENTO);
			}

		});
	}

	// -------------------------------------------------------------------------------------
	// TRANSLATE RECORDS
	// -------------------------------------------------------------------------------------
	final protected void translateRows(List<T> registros, List<ArchivoErrorDto> errores, List<CampoDto> campos) {
		val camposHomologables = getCamposHomologables(campos);

		registros.parallelStream().filter(registro -> filter(registro)).forEach(registro -> {
			boolean success = true;
			log.debug("linea={}:{}", registro.getNumeroLinea(), registro.toString());
			try {
				success &= translateRow(registro, errores, camposHomologables);
			} catch (RuntimeException e) {
				error(registro, errores, e);
				success = false;
			}

			if (success) {
				registro.setEstado(HOMOLOGADO);
			} else {
				registro.setEstado(ERROR_HOMOLOGACION);
			}
		});
	}

	protected List<CampoDto> getCamposHomologables(List<CampoDto> campos) {
		// @formatter:off
		val result = campos.stream()
				.filter(a -> a.getOrdinalTransformacion() > 0)
				.sorted((a,b) -> a.getOrdinalTransformacion().compareTo(b.getOrdinalTransformacion()))
				.collect(Collectors.toList());
		// @formatter:on

		return result;
	}

	final protected boolean translateRow(T registro, List<ArchivoErrorDto> errores, List<CampoDto> campos) {
		boolean result = true;

		for (val campo : campos) {
			boolean success = true;
			try {
				val key = registro.getHomologablePropertyValue(campo.getCodigo());
				val value = getValueFromMapOrDefault(campo, key);
				translateField(registro, campo, value);
			} catch (RuntimeException e) {
				error(registro, errores, e);
				success = false;
			}

			result &= success;
		}

		return result;
	}

	final protected String getValueFromMapOrDefault(CampoDto campo, String key) {
		String result = key;
		val id = campo.getIdMapa();
		if (id != null) {
			val optional = getMapasService().findById(id);
			if (optional.isPresent()) {
				val value = optional.get().getValores().get(campo.getCodigo());
				if(value != null) {
					result = key;
				}
			}
		}
		return result;
	}

	// -------------------------------------------------------------------------------------
	// BEFORE VALIDATE RECORDS
	// -------------------------------------------------------------------------------------
	final protected void beforeValidateRows(List<T> registros, List<ArchivoErrorDto> errores, List<CampoDto> campos) {

		registros.parallelStream().filter(registro -> filter(registro)).forEach(registro -> {
			boolean success = true;

			try {
				success &= beforeValidateRow(registro, errores, campos);
			} catch (RuntimeException e) {
				error(registro, errores, e);
				success = false;
			}

			if (!success) {
				registro.setEstado(ERROR_ENRIQUECIMIENTO);
			}
		});
	}

	// -------------------------------------------------------------------------------------
	// VALIDATE ROWS
	// -------------------------------------------------------------------------------------
	final protected void validateRows(List<T> registros, List<ArchivoErrorDto> errores, List<CampoDto> campos) {
		val camposRequeridos = campos.stream().filter(a -> !a.isIgnorar() && a.isObligatorioValidacion())
				.collect(toList());

		registros.parallelStream().filter(registro -> filter(registro)).forEach(registro -> {
			boolean success = true;
			try {
				success &= validateRequiredFields(registro, errores, camposRequeridos);
				success &= validateRow(registro, errores, campos);
			} catch (RuntimeException e) {
				error(registro, errores, e);
				success = false;
			}

			if (success) {
				registro.setEstado(VALIDADO);
			} else {
				registro.setEstado(ERROR_VALIDACION);
			}
		});
	}

	final protected boolean validateRequiredFields(T registro, List<ArchivoErrorDto> errores, List<CampoDto> campos) {
		boolean success = true;

		for (val campo : campos) {
			boolean isNullOrEmpty = propertyIsNullOrEmpty(registro, campo);

			if (isNullOrEmpty) {
				errorPropertyIsRequiredButValueIsNullOrEmpty(registro, errores, campo);
				success = false;
			}
		}
		return success;
	}

	protected boolean propertyIsNullOrEmpty(T registro, final CampoDto campo) {
		boolean result;
		if (campo.getIdMapa() == null && campo.getOrdinalTransformacion() == 0) {
			result = registro.propertyIsNullOrEmpty(campo.getCodigo());
		} else {
			boolean propertyHasBeenHomologated = registro.propertyHasBeenHomologated(campo.getCodigo());
			if (propertyHasBeenHomologated) {
				result = false;
			} else {
				result = true;
			}
		}
		return result;
	}

	protected void errorPropertyIsRequiredButValueIsNullOrEmpty(T registro, List<ArchivoErrorDto> errores,
			CampoDto campo) {
		val sb = new StringBuilder();

		val valor = getPropertyValue(registro, campo);

		sb.append(campo.getCodigo()).append(":");

		if (StringUtils.isEmpty(valor)) {
			sb.append("Este campo no admite valores vacíos, pero su valor es [%s].");
		} else {
			if (campo.getOrdinalTransformacion() > 0) {
				if (campo.getIdMapa() != null) {
					val format = "Este campo esta asociado al mapa de homologación con id=%d.Verifique que el valor [%s] exista en dicho mapa.";
					sb.append(String.format(format, campo.getIdMapa(), valor));
				} else {
					val format = "Este campo requiere ser homologado. Contiene el valor [%s], pero este valor no pudo ser homologado.";
					sb.append(String.format(format, valor));
				}
			} else {
				sb.append(String.format("Este campo no admite el valor [%s].", valor));
			}
		}

		val id = registro.getIdArchivo();
		val error = ArchivoErrorDto.error(id, sb.toString(), registro.getNumeroLinea(), registro.toString());
		errores.add(error);
	}

	protected String getPropertyValue(T registro, final CampoDto campo) {
		String result;
		if (campo.getOrdinalTransformacion() == 0) {
			result = String.valueOf(registro.getObjectValueFromProperty(campo.getCodigo()));
		} else {
			result = registro.getHomologablePropertyValue(campo.getCodigo());
		}
		return result;
	}

	// -------------------------------------------------------------------------------------
	// VALIDATE GROUPS
	// -------------------------------------------------------------------------------------
	protected void validateGroups(List<T> registros, List<ArchivoErrorDto> errores, List<CampoDto> campos) {

	}

	// -------------------------------------------------------------------------------------
	// SAVE ROWS
	// -------------------------------------------------------------------------------------
	@Transactional(readOnly = false)
	protected void saveRows(final java.util.List<T> registros) {
		getRepository().saveAll(registros);
//		registros.parallelStream().forEach(registro -> {
//			getRepository().saveAndFlush(registro);
//		});
	}

	// -------------------------------------------------------------------------------------
	//
	// -------------------------------------------------------------------------------------
	protected boolean beforeTranslateRow(T registro, List<ArchivoErrorDto> errores, List<CampoDto> campos) {
		return true;
	}

	abstract protected void translateField(T registro, CampoDto campo, String value);

	protected boolean beforeValidateRow(T registro, List<ArchivoErrorDto> errores, List<CampoDto> campos) {
		return true;
	}

	protected boolean validateRow(T registro, List<ArchivoErrorDto> errores, List<CampoDto> campos) {
		return true;
	}

	// -------------------------------------------------------------------------------------
	//
	// -------------------------------------------------------------------------------------
	protected boolean filter(T registro) {
		switch (registro.getEstado()) {
		case ESTRUCTURA_VALIDA:
		case CORREGIDO:
		case HOMOLOGADO:
		case VALIDADO:
			return true;
		default:
			return false;
		}
	}

	public void discard(List<T> registros) {
		// @formatter:off
		val errores = registros
				.stream()
				.filter(row -> row.hasErrors())
				.map(Registro::getIdCorrelacion)
				.distinct()
				.collect(toList());
		// @formatter:on

		if (errores.size() == 0) {
			return;
		}

		// @formatter:off
		val ok = registros
				.stream()
				.filter(row -> !row.hasErrors())
				.collect(toList());
		// @formatter:on

		ok.forEach(row -> {
			if (errores.stream().anyMatch(a -> a.equals(row.getIdCorrelacion()))) {
				row.setEstado(DESCARTADO);
			}
		});
	}

	protected void error(T registro, List<ArchivoErrorDto> errores, RuntimeException e) {
		val error = ArchivoErrorDto.error(registro.getIdArchivo(), e, registro.getNumeroLinea(), registro.toString());
		errores.add(error);
	}
}