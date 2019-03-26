package com.egakat.integration.service.impl.rest;

import com.egakat.core.web.client.components.RestClient;
import com.egakat.core.web.client.properties.RestProperties;
import com.egakat.integration.service.impl.PullServiceImpl;

import lombok.val;

abstract public class RestPullServiceImpl<I> extends PullServiceImpl<I> {

	abstract protected RestProperties getProperties();

	abstract protected RestClient getRestClient();

	abstract protected String getApiEndPoint();

	protected String getUrl() {
		val result = getProperties().getBasePath() + getApiEndPoint();
		return result;
	}

	abstract protected String getQuery();
}