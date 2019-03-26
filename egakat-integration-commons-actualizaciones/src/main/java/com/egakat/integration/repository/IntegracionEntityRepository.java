package com.egakat.integration.repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.repository.NoRepositoryBean;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.domain.IntegracionEntity;

@NoRepositoryBean
public interface IntegracionEntityRepository<E extends IntegracionEntity>
		extends IdentifiedDomainObjectRepository<E, Long> {

	boolean existsByIntegracionAndIdExterno(String integracion, String idExterno);

	Stream<E> findAllByIntegracionAndIdExterno(String integracion, String idExterno);
	
	List<E> findAllByIntegracionAndIdExternoIn(String integracion, Collection<String> idExterno);
}
