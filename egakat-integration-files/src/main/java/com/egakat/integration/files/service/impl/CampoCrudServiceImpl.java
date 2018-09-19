package com.egakat.integration.files.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.files.domain.Campo;
import com.egakat.integration.files.dto.CampoDto;
import com.egakat.integration.files.repository.CampoRepository;
import com.egakat.integration.files.repository.TipoArchivoRepository;
import com.egakat.integration.files.service.api.CampoCrudService;

import lombok.val;

@Service
public class CampoCrudServiceImpl extends CrudServiceImpl<Campo, CampoDto, Long>
		implements CampoCrudService {

	@Autowired
	private CampoRepository repository;

	@Autowired
	private TipoArchivoRepository tipoArchivorepository;

	@Override
	protected CampoRepository getRepository() {
		return repository;
	}

	@Override
	protected CampoDto asModel(Campo entity) {
		// @formatter:off
		val result = CampoDto
				.builder()
				.id(entity.getId())
				
				.idTipoArchivo(entity.getTipoArchivo().getId())
				.ordinal(entity.getOrdinal())
				.codigo(entity.getCodigo())
				.nombre(entity.getNombre())
				.descripcion(entity.getDescripcion())
				.tipoDato(entity.getTipoDato())
				.numeroCaracteres(entity.getNumeroCaracteres())
				.truncarCaracteres(entity.isTruncarCaracteres())
				.ignorar(entity.isIgnorar())
				.incluir(entity.isIncluir())
				.obligatorioEstructura(entity.isObligatorioEstructura())
				.ordinalTransformacion(entity.getOrdinalTransformacion())
				.obligatorioValidacion(entity.isObligatorioValidacion())
				.valorPredeterminado(entity.getValorPredeterminado())
				.idMapa(entity.getIdMapa())
				.formato(entity.getFormato())
				.formatoNumericoSeparadorDecimal(entity.getFormatoNumericoSeparadorDecimal())
				.formatoNumericoSeparadorGrupo(entity.getFormatoNumericoSeparadorGrupo())
				.expresionRegular(entity.getExpresionRegular())
				.valoresPermitidos(entity.getValoresPermitidos())
				.valorEnteroMin(entity.getValorEnteroMin())
				.valorEnteroMax(entity.getValorEnteroMax())
				.valorDecimalMin(entity.getValorDecimalMin())
				.valorDecimalMax(entity.getValorDecimalMax())
				.valorFechaMin(entity.getValorFechaMin())
				.valorFechaMax(entity.getValorFechaMax())
				.valorHoraMin(entity.getValorHoraMin())
				.valorHoraMax(entity.getValorHoraMax())
				
				.activo(entity.isActivo())
				.version(entity.getVersion())
				.creadoPor(entity.getCreadoPor())
				.fechaCreacion(entity.getFechaCreacion())
				.modificadoPor(entity.getModificadoPor())
				.fechaModificacion(entity.getFechaModificacion())
				
				.build();
		// @formatter:on

		return result;
	}

	@Override
	protected Campo mergeEntity(CampoDto model, Campo entity) {
		val tipoArchivo = tipoArchivorepository.getOne(model.getIdTipoArchivo());

		entity.setTipoArchivo(tipoArchivo);
		entity.setOrdinal(model.getOrdinal());
		entity.setCodigo(model.getCodigo());
		entity.setNombre(model.getNombre());
		entity.setDescripcion(model.getDescripcion());
		entity.setTipoDato(model.getTipoDato());
		entity.setNumeroCaracteres(model.getNumeroCaracteres());
		entity.setTruncarCaracteres(model.isTruncarCaracteres());
		entity.setIgnorar(model.isIgnorar());
		entity.setIncluir(model.isIncluir());
		entity.setObligatorioEstructura(model.isObligatorioEstructura());
		entity.setOrdinalTransformacion(model.getOrdinalTransformacion());
		entity.setObligatorioValidacion(model.isObligatorioValidacion());
		entity.setIdMapa(model.getIdMapa());
		entity.setValorPredeterminado(model.getValorPredeterminado());
		entity.setFormato(model.getFormato());
		entity.setFormatoNumericoSeparadorDecimal(model.getFormatoNumericoSeparadorDecimal());
		entity.setFormatoNumericoSeparadorGrupo(model.getFormatoNumericoSeparadorGrupo());
		entity.setExpresionRegular(model.getExpresionRegular());
		entity.setValoresPermitidos(model.getValoresPermitidos());
		entity.setValorEnteroMin(model.getValorEnteroMin());
		entity.setValorEnteroMax(model.getValorEnteroMax());
		entity.setValorDecimalMin(model.getValorDecimalMin());
		entity.setValorDecimalMax(model.getValorDecimalMax());
		entity.setValorFechaMin(model.getValorFechaMin());
		entity.setValorFechaMax(model.getValorFechaMax());
		entity.setValorHoraMin(model.getValorHoraMin());
		entity.setValorHoraMax(model.getValorHoraMax());

		return entity;
	}

	@Override
	protected Campo newEntity() {
		return new Campo();
	}

	@Override
	public List<CampoDto> findAllByTipoArchivoId(long tipoArchivo) {
		val entities = getRepository().findAllByTipoArchivoId(tipoArchivo);
		val result = asModels(entities);
		return result;
	}
}
