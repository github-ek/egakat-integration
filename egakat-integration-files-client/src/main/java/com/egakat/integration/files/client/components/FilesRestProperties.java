package com.egakat.integration.files.client.components;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.egakat.core.web.client.configuration.RestProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@ConfigurationProperties(prefix = "com.egakat.integration.files.rest")
@Getter
@Setter
@ToString
@Validated
public class FilesRestProperties implements RestProperties {

	private String basePath;
}
