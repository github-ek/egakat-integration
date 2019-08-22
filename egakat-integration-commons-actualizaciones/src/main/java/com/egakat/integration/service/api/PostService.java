package com.egakat.integration.service.api;

import java.util.Optional;

public interface PostService<I, ID> {
	Optional<ID> post(I input);

	Optional<ID> post(I input, String correlacion);
}