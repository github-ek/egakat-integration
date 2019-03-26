package com.egakat.integration.service.impl.jdbc;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.egakat.integration.service.api.PullService;
import com.egakat.integration.service.impl.PullServiceImpl;

abstract public class JdbcPullServiceImpl<I> extends PullServiceImpl<I> implements PullService {

	abstract protected NamedParameterJdbcTemplate getJdbcTemplate();

	abstract protected String getSql();
}