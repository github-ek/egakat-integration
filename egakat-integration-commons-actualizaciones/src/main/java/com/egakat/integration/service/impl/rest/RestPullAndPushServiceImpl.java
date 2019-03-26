package com.egakat.integration.service.impl.rest;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.egakat.core.web.client.components.RestClient;
import com.egakat.core.web.client.exception.ReintentableException;
import com.egakat.core.web.client.properties.RestProperties;
import com.egakat.integration.dto.ErrorIntegracionDto;
import com.egakat.integration.service.impl.PullAndPushServiceImpl;

import lombok.val;

abstract public class RestPullAndPushServiceImpl<P, I, O, R> extends PullAndPushServiceImpl<P, I, O, R> {

	protected abstract RestProperties getProperties();

	protected abstract RestClient getRestClient();

	protected String getUrl() {
		val result = getProperties().getBasePath() + getApiEndPoint();
		return result;
	}

	protected abstract String getApiEndPoint();

	@Override
	protected void handlePullException(String correlacion, List<ErrorIntegracionDto> errores, RuntimeException e) {
		if (onUnauthorized(e)) {
			tokenCacheEvict();
		}

		super.handlePullException(correlacion, errores, e);
	}

	protected boolean onUnauthorized(RuntimeException e) {
		boolean result = false;
		if (e instanceof ReintentableException) {
			if (((ReintentableException) e).getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
				result = true;
			}
		}
		return result;
	}

	abstract protected void tokenCacheEvict();
}
