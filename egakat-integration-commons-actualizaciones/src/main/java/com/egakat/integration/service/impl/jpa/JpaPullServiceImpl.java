package com.egakat.integration.service.impl.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egakat.integration.service.impl.PullServiceImpl;

abstract public class JpaPullServiceImpl<I, ID> extends PullServiceImpl<I> {

	abstract protected JpaRepository<I, ID> getRepository();

}