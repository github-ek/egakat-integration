package com.egakat.integration.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import com.egakat.core.web.client.properties.RestProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ConfigurationProperties(prefix = IntegrationConfigRestProperties.CONFIGURATION_PROPERTIES)
@Getter
@Setter
@ToString
@Validated
public class IntegrationConfigRestProperties implements RestProperties {

	static final String CONFIGURATION_PROPERTIES = "endpoint.integration.config.rest";
	
	private String basePath;
	
}
