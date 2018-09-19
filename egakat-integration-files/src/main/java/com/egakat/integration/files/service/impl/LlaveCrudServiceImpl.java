package com.egakat.integration.files.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.files.domain.Llave;
import com.egakat.integration.files.dto.LlaveDto;
import com.egakat.integration.files.repository.LlaveRepository;
import com.egakat.integration.files.repository.TipoArchivoRepository;
import com.egakat.integration.files.service.api.LlaveCrudService;

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
		// @formatter:off
		val result = LlaveDto.builder()
				.id(entity.getId())
				
				.idTipoArchivo(entity.getTipoArchivo().getId())
				.codigo(entity.getCodigo())
				
				.ordinal(entity.getOrdinal())
				.activo(entity.isActivo())
				.version(entity.getVersion())
				.creadoPor(entity.getCreadoPor())
				.fechaCreacion(entity.getFechaCreacion())
				.modificadoPor(entity.getModificadoPor())
				.fechaModificacion(entity.getFechaModificacion())
				.build();
		// @formatter:on

		entity.getCampos().size();
		entity.getCampos().forEach(campo -> {
			result.getCampos().add(campo.getId());
		});

		return result;
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