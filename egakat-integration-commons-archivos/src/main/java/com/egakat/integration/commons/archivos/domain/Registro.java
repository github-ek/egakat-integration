package com.egakat.integration.commons.archivos.domain;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.egakat.core.data.jpa.domain.AuditableEntity;
import com.egakat.integration.commons.archivos.enums.EstadoRegistroType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

@MappedSuperclass
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
abstract public class Registro extends AuditableEntity<Long> {

	@Column(name = "id_archivo")
	private Long idArchivo;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private EstadoRegistroType estado;

	@Column(name = "numero_linea")
	private int numeroLinea;

	abstract public String getIdCorrelacion();

	public boolean hasErrors() {
		switch (getEstado()) {
		case ERROR_ENRIQUECIMIENTO:
		case ERROR_HOMOLOGACION:
		case ERROR_VALIDACION:
		case ERROR_CARGUE:
			return true;
		default:
			return false;
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	final public boolean propertyIsNullOrEmpty(String property) {
		boolean result;
		val value = getObjectValueFromProperty(property);
		
		if (value == null) {
			result = true;
		} else {
			if (value instanceof String) {
				result = "".equals(((String) value).trim());
			} else {
				result = false;
			}
		}
		
		return result;
	}

	abstract public Object getObjectValueFromProperty(String property);

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	final public String getHomologablePropertyValue(String property) {
		if (!isHomologableProperty(property)) {
			throw new IllegalArgumentException("El campo \"" + property + "\", no es homologable.");
		}

		String result = getStringValueFromHomologableProperty(property);
		if (result == null) {
			result = "";
		}
		return result;
	}
	
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	final public boolean propertyHasBeenHomologated(String property) {
		boolean result = false;
		if (isHomologableProperty(property)) {
			val value = getObjectValueFromHomologousProperty(property);
			result = (value != null);
		}
		return result;
	}

	abstract public boolean isHomologableProperty(String property);

	abstract protected String getStringValueFromHomologableProperty(String property);

	abstract protected Object getObjectValueFromHomologousProperty(String property);
}