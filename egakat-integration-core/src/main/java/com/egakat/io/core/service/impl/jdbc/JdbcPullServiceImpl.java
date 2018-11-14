package com.egakat.io.core.service.impl.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.egakat.io.core.service.api.PullService;
import com.egakat.io.core.service.impl.PullServiceImpl;

abstract public class JdbcPullServiceImpl<I> extends PullServiceImpl<I> implements PullService {

	abstract protected NamedParameterJdbcTemplate getJdbcTemplate();

	abstract protected String getSql();
	
	abstract protected RowMapper<I> getRowMapper();

}