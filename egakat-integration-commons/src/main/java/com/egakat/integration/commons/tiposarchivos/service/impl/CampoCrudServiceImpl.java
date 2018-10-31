package com.egakat.integration.commons.tiposarchivos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.commons.tiposarchivo.dto.CampoDto;
import com.egakat.integration.commons.tiposarchivos.domain.Campo;
import com.egakat.integration.commons.tiposarchivos.repository.CampoRepository;
import com.egakat.integration.commons.tiposarchivos.repository.TipoArchivoRepository;
import com.egakat.integration.commons.tiposarchivos.service.api.CampoCrudService;

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
		val result = newModel();
		mapModel(entity, result);
		
		result.setIdTipoArchivo(entity.getTipoArchivo().getId());
		result.setOrdinal(entity.getOrdinal());
		result.setCodigo(entity.getCodigo());
		result.setNombre(entity.getNombre());
		result.setDescripcion(entity.getDescripcion());
		result.setTipoDato(entity.getTipoDato());
		result.setNumeroCaracteres(entity.getNumeroCaracteres());
		result.setTruncarCaracteres(entity.isTruncarCaracteres());
		result.setIgnorar(entity.isIgnorar());
		result.setIncluir(entity.isIncluir());
		result.setObligatorioEstructura(entity.isObligatorioEstructura());
		result.setOrdinalTransformacion(entity.getOrdinalTransformacion());
		result.setObligatorioValidacion(entity.isObligatorioValidacion());
		result.setValorPredeterminado(entity.getValorPredeterminado());
		result.setIdMapa(entity.getIdMapa());
		result.setFormato(entity.getFormato());
		result.setFormatoNumericoSeparadorDecimal(entity.getFormatoNumericoSeparadorDecimal());
		result.setFormatoNumericoSeparadorGrupo(entity.getFormatoNumericoSeparadorGrupo());
		result.setExpresionRegular(entity.getExpresionRegular());
		result.setValoresPermitidos(entity.getValoresPermitidos());
		result.setValorEnteroMin(entity.getValorEnteroMin());
		result.setValorEnteroMax(entity.getValorEnteroMax());
		result.setValorDecimalMin(entity.getValorDecimalMin());
		result.setValorDecimalMax(entity.getValorDecimalMax());
		result.setValorFechaMin(entity.getValorFechaMin());
		result.setValorFechaMax(entity.getValorFechaMax());
		result.setValorHoraMin(entity.getValorHoraMin());
		result.setValorHoraMax(entity.getValorHoraMax());
				
		return result;
	}
	
	@Override
	protected CampoDto newModel() {
		return new CampoDto();
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
