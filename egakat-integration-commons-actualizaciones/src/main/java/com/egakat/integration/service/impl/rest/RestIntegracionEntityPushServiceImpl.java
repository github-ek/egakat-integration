package com.egakat.integration.service.impl.rest;

import com.egakat.integration.dto.IntegracionEntityDto;
import com.egakat.integration.service.api.crud.IntegracionEntityCrudService;

abstract public class RestIntegracionEntityPushServiceImpl<I extends IntegracionEntityDto, O, R>
		extends RestPushServiceImpl<I, O, R> {

	protected abstract IntegracionEntityCrudService<I> getCrudService();

}