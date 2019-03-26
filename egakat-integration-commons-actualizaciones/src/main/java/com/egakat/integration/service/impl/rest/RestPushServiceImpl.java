package com.egakat.integration.service.impl.rest;

import com.egakat.core.web.client.components.RestClient;
import com.egakat.core.web.client.properties.RestProperties;
import com.egakat.integration.service.impl.PushServiceImpl;

import lombok.val;

abstract public class RestPushServiceImpl<I, O, R> extends PushServiceImpl<I, O, R> {

	protected abstract RestProperties getProperties();

	protected abstract RestClient getRestClient();

	protected String getUrl() {
		val result = getProperties().getBasePath() + getApiEndPoint();
		return result;
	}
	
	protected abstract String getApiEndPoint();
}