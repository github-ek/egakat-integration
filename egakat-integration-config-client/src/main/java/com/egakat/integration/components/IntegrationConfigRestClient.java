package com.egakat.integration.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.egakat.core.web.client.components.AbstractRestClientImpl;

@Component
public class IntegrationConfigRestClient extends AbstractRestClientImpl {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
}
