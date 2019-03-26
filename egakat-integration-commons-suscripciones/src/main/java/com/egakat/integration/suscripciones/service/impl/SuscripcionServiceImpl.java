package com.egakat.integration.suscripciones.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.egakat.integration.suscripciones.service.api.SuscripcionService;

import lombok.val;

public abstract class SuscripcionServiceImpl<M> implements SuscripcionService<M> {

	protected abstract NamedParameterJdbcTemplate getJdbcTemplate();


	protected abstract String getIdSuscripcionParameterName();

	protected abstract String getSqlFindByIdSuscripcion();

	protected abstract String getSqlFindAllSuscripciones();

	protected abstract RowMapper<M> getRowMapper();

	@Override
	public List<Long> findAllSuscripciones() {
		val sql = getSqlFindAllSuscripciones();
		val result = getJdbcTemplate().queryForList(sql, new MapSqlParameterSource(), Long.class);
		return result;
	}

	@Override
	public List<Long> findAllSuscripciones(Map<String, ?> paramMap) {
		val sql = getSqlFindAllSuscripciones();
		val result = getJdbcTemplate().queryForList(sql, new MapSqlParameterSource(paramMap), Long.class);
		return result;
	}

	@Override
	public Optional<M> findByIdSuscripcion(Long id) {
		val paramMap = new HashMap<String, Long>();
		paramMap.put(getIdSuscripcionParameterName(), id);

		try {
			val sql = getSqlFindByIdSuscripcion();
			val result = getJdbcTemplate().queryForObject(sql, paramMap, getRowMapper());
			return Optional.of(result);
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}
}