package com.egakat.integration.core.transformation.service.api;

import java.util.List;

import com.egakat.core.web.client.service.api.CacheEvictSupported;
import com.egakat.integration.commons.archivos.domain.Registro;


public interface TransformationService<T extends Registro> extends CacheEvictSupported{

	List<Long> getArchivosPendientes();

	void transformar(Long archivoId);
}
