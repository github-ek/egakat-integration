package com.egakat.integration.files.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.val;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = JpaConfiguration.PACKAGES_TO_SCAN_FOR_REPOSITORIES)
public class JpaConfiguration extends HikariConfig {

	static final String PACKAGES_TO_SCAN_FOR_REPOSITORIES = "com.egakat.integration.files.repository";

	static final String[] PACKAGES_TO_SCAN_FOR_ENTITIES = { "com.egakat.integration.files.domain",
			"com.egakat.core.data.jpa.converters" };

	static final String DATASOURCE_PROPERTIES_PREFIX = "spring.datasource";

	@Bean
	@ConfigurationProperties(prefix = DATASOURCE_PROPERTIES_PREFIX)
	public DataSource dataSource() {
		val result = DataSourceBuilder.create().type(HikariDataSource.class).build();
		return result;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			DataSource dataSource) {
		val result = builder.dataSource(dataSource).packages(PACKAGES_TO_SCAN_FOR_ENTITIES).build();
		return result;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		val result = new JpaTransactionManager(entityManagerFactory);
		return result;
	}
}