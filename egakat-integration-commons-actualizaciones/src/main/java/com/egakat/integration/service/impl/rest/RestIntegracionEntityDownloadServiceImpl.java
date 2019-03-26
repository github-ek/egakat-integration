package com.egakat.integration.service.impl.rest;

import com.egakat.integration.dto.IntegracionEntityDto;
import com.egakat.integration.service.api.crud.IntegracionEntityCrudService;

import lombok.val;

abstract public class RestIntegracionEntityDownloadServiceImpl<I, O extends IntegracionEntityDto, R>
		extends RestPushServiceImpl<I, O, R> {

	@Override
	protected String getOperacion() {
		val result = String.format("DOWNLOAD %s", getIntegracion());
		return result;
	}
	
	protected abstract String getQuery();
	
	protected abstract IntegracionEntityCrudService<O> getCrudService();
}
