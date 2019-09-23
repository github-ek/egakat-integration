package com.egakat.integration.commons.archivos.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.archivos.domain.Llave;
import com.egakat.integration.archivos.dto.LlaveDto;
import com.egakat.integration.archivos.repository.LlaveRepository;
import com.egakat.integration.commons.archivos.service.api.LlaveCrudService;

import lombok.val;

@Service
public class LlaveCrudServiceImpl extends CrudServiceImpl<Llave, LlaveDto, Long> implements LlaveCrudService {

	@Autowired
	private LlaveRepository repository;

	@Override
	protected LlaveRepository getRepository() {
		return repository;
	}

	@Override
	protected LlaveDto asModel(Llave entity) {
		val result = newModel();
		mapModel(entity, result);

		result.setIdTipoArchivo(entity.getIdTipoArchivo());
		result.setCodigo(entity.getCodigo());
		result.setCampos(new ArrayList<>());
		result.setOrdinal(entity.getOrdinal());
		result.setActivo(entity.isActivo());

//		entity.getCampos().size();
//		entity.getCampos().forEach(campo -> {
//			result.getCampos().add(campo.getId());
//		});

		return result;
	}

	@Override
	protected LlaveDto newModel() {
		return new LlaveDto();
	}

	@Override
	protected Llave mergeEntity(LlaveDto model, Llave entity) {
		entity.setIdTipoArchivo(model.getIdTipoArchivo());
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
		List<Llave> entities = getRepository().findAllByIdTipoArchivo(tipoArchivo);
		val result = asModels(entities);
		return result;
	}
}