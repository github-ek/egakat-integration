package com.egakat.integration.commons.mapas.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.commons.mapas.domain.Mapa;
import com.egakat.integration.commons.mapas.dto.MapaDto;
import com.egakat.integration.commons.mapas.repository.GrupoMapaRepository;
import com.egakat.integration.commons.mapas.repository.MapaRepository;
import com.egakat.integration.commons.mapas.service.api.MapaCrudService;

import lombok.val;

@Service
public class MapaCrudServiceImpl extends CrudServiceImpl<Mapa, MapaDto, Long> implements MapaCrudService {

	@Autowired
	private MapaRepository repository;

	@Autowired
	private GrupoMapaRepository grupoMapaRepository;

	@Override
	protected MapaRepository getRepository() {
		return repository;
	}

	@Override
	protected MapaDto asModel(Mapa entity) {
		val result = newModel();
		mapModel(entity, result);

		result.setIdGrupoMapa(entity.getGrupoMapa().getId());
		result.setCodigo(entity.getCodigo());
		result.setNombre(entity.getNombre());
		result.setDescripcion(entity.getDescripcion());
		result.setPatronClave(entity.getPatronClave());
		result.setOrdinal(entity.getOrdinal());
		result.setActivo(entity.isActivo());

		entity.getValores().forEach(a -> {
			result.getValores().put(a.getClave(), a.getValor());
		});

		// @formatter:on
		return result;
	}

	@Override
	protected MapaDto newModel() {
		return new MapaDto();
	}

	@Override
	protected Mapa mergeEntity(MapaDto model, Mapa entity) {
		val grupoMapa = grupoMapaRepository.getOne(model.getIdGrupoMapa());

		entity.setGrupoMapa(grupoMapa);
		entity.setCodigo(model.getCodigo());
		entity.setNombre(model.getNombre());
		entity.setDescripcion(model.getDescripcion());
		entity.setPatronClave(model.getPatronClave());
		entity.setOrdinal(model.getOrdinal());
		entity.setActivo(model.isActivo());

		return entity;
	}

	@Override
	protected Mapa newEntity() {
		return new Mapa();
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public Optional<MapaDto> findOneByGrupoMapaIdAndCodigo(Long grupoMapa, String codigo) {
		val entity = getRepository().findOneByGrupoMapaIdAndCodigo(grupoMapa, codigo);
		if (entity == null) {
			return Optional.empty();
		}
		val result = asModel(entity);
		return Optional.of(result);
	}

	@Override
	public List<MapaDto> findAllByGrupoMapa(long grupoMapa) {
		val entities = getRepository().findAllByGrupoMapaId(grupoMapa);
		val result = asModels(entities);
		return result;
	}
}