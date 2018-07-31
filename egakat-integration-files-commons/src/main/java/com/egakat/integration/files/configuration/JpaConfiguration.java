package com.egakat.integration.files.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.val;

@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableLoadTimeWeaving
@EnableJpaRepositories(basePackages = JpaConfiguration.PACKAGES_TO_SCAN_FOR_REPOSITORIES, entityManagerFactoryRef = JpaConfiguration.ENTITY_MANAGER_FACTORY, transactionManagerRef = JpaConfiguration.TRANSACTION_MANAGER)
public abstract class JpaConfiguration extends HikariConfig{

	static final String PACKAGES_TO_SCAN_FOR_REPOSITORIES = "com.egakat.integration.files.repository";

	static final String[] PACKAGES_TO_SCAN_FOR_ENTITIES = { "com.egakat.integration.files.domain",
			"com.egakat.core.data.jpa.converters" };

	static final String DATASOURCE_PROPERTIES_PREFIX = "integration.files.datasource";

	static final String DATA_SOURCE = "integrationFilesDataSource";

	static final String PERSISTENCE_UNIT = "integrationFilesPersistenceUnit";

	static final String ENTITY_MANAGER_FACTORY = "integrationFilesEntityManagerFactory";

	public static final String TRANSACTION_MANAGER = "integrationFilesTransactionManager";

	@Bean(name = DATA_SOURCE)
	@ConfigurationProperties(prefix = DATASOURCE_PROPERTIES_PREFIX)
	public DataSource dataSource() {
		val result = DataSourceBuilder.create().type(HikariDataSource.class).build();
		return result;
	}

	@Bean(name = ENTITY_MANAGER_FACTORY)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier(DATA_SOURCE) DataSource dataSource) {
		val result = builder.dataSource(dataSource).packages(PACKAGES_TO_SCAN_FOR_ENTITIES)
				.persistenceUnit(PERSISTENCE_UNIT).build();
		return result;
	}

	@Bean(name = TRANSACTION_MANAGER)
	public PlatformTransactionManager transactionManager(
			@Qualifier(ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory) {
		val result = new JpaTransactionManager(entityManagerFactory);
		return result;
	}
}
