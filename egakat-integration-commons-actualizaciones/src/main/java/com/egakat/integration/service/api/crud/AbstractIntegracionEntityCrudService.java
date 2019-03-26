package com.egakat.integration.service.api.crud;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.egakat.core.services.crud.api.CrudService;
import com.egakat.integration.dto.IntegracionEntityDto;

public interface AbstractIntegracionEntityCrudService<M extends IntegracionEntityDto> extends CrudService<M, Long> {

	@Transactional(readOnly = true)
	boolean exists(String integracion, String idExterno);

	@Transactional(readOnly = true)
	M findOneByIntegracionAndIdExterno(String integracion, String idExterno);

	@Transactional(readOnly = true)
	Optional<M> findByIntegracionAndIdExterno(String integracion, String idExterno);

	@Transactional(readOnly = true)
	List<M> findAllByIntegracionAndIdExternoIn(String integracion, Collection<String> idExterno);
}
