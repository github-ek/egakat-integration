package com.egakat.integration.config.archivos.client.service.api;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.egakat.core.web.client.service.api.CacheEvictSupported;
import com.egakat.core.web.client.service.api.LocalQueryService;
import com.egakat.integration.config.archivos.dto.CampoDto;
import com.egakat.integration.config.archivos.dto.DirectorioDto;
import com.egakat.integration.config.archivos.dto.DirectorioObservableDto;
import com.egakat.integration.config.archivos.dto.LlaveDto;
import com.egakat.integration.config.archivos.dto.TipoArchivoDto;

public interface TipoArchivoLocalService extends LocalQueryService<TipoArchivoDto, Long>, CacheEvictSupported {

	@Cacheable(cacheNames = "tipo-archivo-by-id", unless = "#result == null")
	TipoArchivoDto findOneById(Long id);

	@Cacheable(cacheNames = "tipo-archivo-by-codigo", unless = "#result == null")
	TipoArchivoDto findOneByCodigo(String codigo);

	@Cacheable(cacheNames = "tipos-archivo-by-aplicacion", unless = "#result == null")
	List<TipoArchivoDto> findAllByAplicacion(String aplicacion);

	@Cacheable(cacheNames = "campos-by-tipo-archivo", unless = "#result == null")
	List<CampoDto> findAllCamposByTipoArchivo(long id);

	@Cacheable(cacheNames = "llaves-by-tipo-archivo", unless = "#result == null")
	List<LlaveDto> findAllLlavesByTipoArchivo(long id);

	@Cacheable(cacheNames = "directorios-by-tipo-archivo", unless = "#result == null")
	List<DirectorioDto> findAllDirectoriosByTipoArchivo(long id);

	@Cacheable(cacheNames = "directorios-observables-by-tipo-archivo", unless = "#result == null")
	List<DirectorioObservableDto> findAllDirectoriosObservablesByTipoArchivo(long id);

	@CacheEvict(cacheNames = { "tipo-archivo-by-id", "tipo-archivo-by-codigo", "tipos-archivo-by-aplicacion",
			"campos-by-tipo-archivo", "llaves-by-tipo-archivo", "directorios-by-tipo-archivo",
			"directorios-observables-by-tipo-archivo" }, allEntries = true)
	void cacheEvict();

}