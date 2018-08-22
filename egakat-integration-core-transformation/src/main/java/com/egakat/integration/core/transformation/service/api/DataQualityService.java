package com.egakat.integration.core.transformation.service.api;

import java.util.List;

import com.egakat.core.web.client.service.api.CacheEvictSupported;
import com.egakat.integration.files.domain.Registro;


public interface DataQualityService<T extends Registro> extends CacheEvictSupported{

	List<Long> getArchivosPendientes();

	void transformar(Long archivoId);
}
