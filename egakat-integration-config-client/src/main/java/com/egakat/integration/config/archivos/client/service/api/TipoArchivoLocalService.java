package com.egakat.integration.config.archivos.client.service.api;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.egakat.core.web.client.service.api.LocalQueryService;
import com.egakat.integration.config.archivos.dto.CampoDto;
import com.egakat.integration.config.archivos.dto.DirectorioDto;
import com.egakat.integration.config.archivos.dto.LlaveDto;
import com.egakat.integration.config.archivos.dto.TipoArchivoDto;

public interface TipoArchivoLocalService extends LocalQueryService<TipoArchivoDto, Long> {

	List<TipoArchivoDto> findAllActivos();

	@Cacheable(cacheNames = "tipo-archivo-by-id", sync = true)
	TipoArchivoDto findOneById(Long id);

	@Cacheable(cacheNames = "tipo-archivo-by-codigo", sync = true)
	TipoArchivoDto findOneByCodigo(String codigo);

	@Cacheable(cacheNames = "campos-by-tiposArchivoId", sync = true)
	List<CampoDto> findAllCamposByTipoArchivo(long id);

	@Cacheable(cacheNames = "llaves-by-tiposArchivoId", sync = true)
	List<LlaveDto> findAllLlavesByTipoArchivo(long id);

	@Cacheable(cacheNames = "directorio-by-tiposArchivoId", sync = true)
	DirectorioDto findOneDirectorioByTipoArchivo(long id);
}