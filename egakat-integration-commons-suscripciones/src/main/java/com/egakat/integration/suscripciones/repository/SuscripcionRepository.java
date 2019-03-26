package com.egakat.integration.suscripciones.repository;

import java.util.stream.Stream;

import com.egakat.core.data.jpa.repository.IdentifiedDomainObjectRepository;
import com.egakat.integration.suscripciones.domain.Suscripcion;

public interface SuscripcionRepository<E extends Suscripcion> extends IdentifiedDomainObjectRepository<E, Long> {

	boolean existsBySuscripcionAndIdExterno(String suscripcion, String idExterno);

	Stream<E> findAllBySuscripcionAndIdExterno(String suscripcion, String idExterno);
}
