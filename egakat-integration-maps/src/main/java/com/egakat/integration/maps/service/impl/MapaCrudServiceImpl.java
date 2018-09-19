package com.egakat.integration.maps.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.maps.domain.Mapa;
import com.egakat.integration.maps.dto.MapaDto;
import com.egakat.integration.maps.repository.GrupoMapaRepository;
import com.egakat.integration.maps.repository.MapaRepository;
import com.egakat.integration.maps.service.api.MapaCrudService;

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
		// @formatter:off
		val result = MapaDto
				.builder()
				.id(entity.getId())
				.idGrupoMapa(entity.getGrupoMapa().getId())
				.codigo(entity.getCodigo())
				.nombre(entity.getNombre())
				.descripcion(entity.getDescripcion())
				.patronClave(entity.getPatronClave())
				.ordinal(entity.getOrdinal())
				.activo(entity.isActivo())
				.version(entity.getVersion())
				.creadoPor(entity.getCreadoPor())
				.fechaCreacion(entity.getFechaCreacion())
				.modificadoPor(entity.getModificadoPor())
				.fechaModificacion(entity.getFechaModificacion())
				.build();

		entity.getValores().forEach(a -> {
			result.getValores().put(a.getClave(), a.getValor());
		});
		
		// @formatter:on
		return result;
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