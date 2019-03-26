package com.anexa.integration.mapas.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.anexa.core.web.client.properties.RestProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@ConfigurationProperties(prefix = IntegrationConfigRestProperties.CONFIGURATION_PROPERTIES)
@Getter
@Setter
@ToString
@Validated
public class IntegrationConfigRestProperties implements RestProperties {

	static final String CONFIGURATION_PROPERTIES = "endpoint.integration.config.rest";
	
	private String basePath;
	
}
