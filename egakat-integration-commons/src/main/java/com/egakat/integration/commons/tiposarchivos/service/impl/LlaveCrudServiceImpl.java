package com.egakat.integration.commons.tiposarchivos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.commons.tiposarchivo.dto.LlaveDto;
import com.egakat.integration.commons.tiposarchivos.domain.Llave;
import com.egakat.integration.commons.tiposarchivos.repository.LlaveRepository;
import com.egakat.integration.commons.tiposarchivos.repository.TipoArchivoRepository;
import com.egakat.integration.commons.tiposarchivos.service.api.LlaveCrudService;

import lombok.val;

@Service
public class LlaveCrudServiceImpl extends CrudServiceImpl<Llave, LlaveDto, Long> implements LlaveCrudService {

	@Autowired
	private LlaveRepository repository;

	@Autowired
	private TipoArchivoRepository tipoArchivorepository;

	@Override
	protected LlaveRepository getRepository() {
		return repository;
	}

	@Override
	protected LlaveDto asModel(Llave entity) {
		val result = newModel();
		mapModel(entity, result);

		result.setIdTipoArchivo(entity.getTipoArchivo().getId());
		result.setCodigo(entity.getCodigo());

		result.setOrdinal(entity.getOrdinal());
		result.setActivo(entity.isActivo());

		entity.getCampos().size();
		entity.getCampos().forEach(campo -> {
			result.getCampos().add(campo.getId());
		});

		return result;
	}

	@Override
	protected LlaveDto newModel() {
		return new LlaveDto();
	}

	@Override
	protected Llave mergeEntity(LlaveDto model, Llave entity) {
		val tipoArchivo = tipoArchivorepository.getOne(model.getIdTipoArchivo());

		entity.setTipoArchivo(tipoArchivo);
		entity.setCodigo(model.getCodigo());
		entity.setOrdinal(model.getOrdinal());
		entity.setActivo(model.isActivo());

		return entity;
	}

	@Override
	protected Llave newEntity() {
		return new Llave();
	}

	@Override
	public List<LlaveDto> findAllByTipoArchivoId(long tipoArchivo) {
		List<Llave> entities = getRepository().findAllByTipoArchivoId(tipoArchivo);
		val result = asModels(entities);
		return result;
	}
}