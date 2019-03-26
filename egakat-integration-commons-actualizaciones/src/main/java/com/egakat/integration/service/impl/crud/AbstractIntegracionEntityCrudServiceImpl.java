package com.egakat.integration.service.impl.crud;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.egakat.core.services.crud.impl.CrudServiceImpl;
import com.egakat.integration.domain.IntegracionEntity;
import com.egakat.integration.dto.IntegracionEntityDto;
import com.egakat.integration.repository.IntegracionEntityRepository;
import com.egakat.integration.service.api.crud.AbstractIntegracionEntityCrudService;
import com.egakat.integration.service.api.crud.ErrorIntegracionCrudService;

import lombok.val;

abstract public class AbstractIntegracionEntityCrudServiceImpl<E extends IntegracionEntity, M extends IntegracionEntityDto>
		extends CrudServiceImpl<E, M, Long> implements AbstractIntegracionEntityCrudService<M> {

	@Override
	abstract protected IntegracionEntityRepository<E> getRepository();

	@Autowired
	private ErrorIntegracionCrudService erroresService;

	protected ErrorIntegracionCrudService getErroresService() {
		return erroresService;
	}

	@Override
	public boolean exists(String integracion, String idExterno) {
		val result = getRepository().existsByIntegracionAndIdExterno(integracion, idExterno);
		return result;
	}

	@Override
	public M findOneByIntegracionAndIdExterno(String integracion, String idExterno) {
		val entities = getRepository().findAllByIntegracionAndIdExterno(integracion, idExterno);
		val entity = entities.findFirst().get();
		val result = asModel(entity);
		return result;
	}

	@Override
	public Optional<M> findByIntegracionAndIdExterno(String integracion, String idExterno) {
		val entities = getRepository().findAllByIntegracionAndIdExterno(integracion, idExterno);
		val optional = entities.findFirst();
		val result = asModel(optional);
		return result;
	}

	@Override
	public List<M> findAllByIntegracionAndIdExternoIn(String integracion, Collection<String> idExterno) {
		val entities = getRepository().findAllByIntegracionAndIdExternoIn(integracion, idExterno);
		val result = asModels(entities);
		return result;
	}
}