package com.anexa.integration.mapas.client.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.anexa.core.web.client.components.AbstractRestClientImpl;

@Component
public class IntegrationConfigRestClient extends AbstractRestClientImpl {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
}
