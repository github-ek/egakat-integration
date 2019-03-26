package com.egakat.integration.core.files.components.writers;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;

public interface Writer<T extends Serializable> {
	String write(final Path input, T data) throws IOException;
}