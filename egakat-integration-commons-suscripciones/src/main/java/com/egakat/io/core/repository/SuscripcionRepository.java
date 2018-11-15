package com.egakat.io.core.repository;

import java.util.stream.Stream;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.io.core.domain.SuscripcionEntity;

public interface SuscripcionRepository<E extends SuscripcionEntity>
		extends IdentifiedDomainObjectRepository<E, Long> {

	boolean existsBySuscripcionAndIdExterno(String suscripcion, String idExterno);

	Stream<E> findAllBySuscripcionAndIdExterno(String suscripcion, String idExterno);
}
