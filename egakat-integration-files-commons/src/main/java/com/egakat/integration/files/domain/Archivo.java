package com.egakat.integration.files.domain;

import static com.egakat.integration.files.enums.EstadoArchivoType.ERROR_ESTRUCTURA;
import static com.egakat.integration.files.enums.EstadoArchivoType.ERROR_PROCESAMIENTO;
import static com.egakat.integration.files.enums.EstadoArchivoType.ERROR_VALIDACION;
import static com.egakat.integration.files.enums.EstadoArchivoType.ESTRUCTURA_VALIDA;
import static com.egakat.integration.files.enums.EstadoArchivoType.PROCESADO;
import static com.egakat.integration.files.enums.EstadoArchivoType.VALIDADO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.egakat.commons.domain.AuditableEntity;
import com.egakat.integration.files.enums.EstadoArchivoType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "archivos")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true, exclude = { "errores" })
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_archivo"))
public class Archivo extends AuditableEntity<Long> {

	@Column(name = "id_tipo_archivo", nullable = false)
	@NotNull
	private long idTipoArchivo;

	@NotNull
	@Size(max = 300)
	private String nombre;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private EstadoArchivoType estado;

	@NotNull
	@Size(max = 1024)
	private String ruta;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_archivo")
	private List<ArchivoError> errores = new ArrayList<>();

	public void validadoSinErrores() {
		EstadoArchivoType estado;

		switch (getEstado()) {
		case NO_PROCESADO:
		case ERROR_ESTRUCTURA:
			estado = ESTRUCTURA_VALIDA;
			break;
		case ESTRUCTURA_VALIDA:
		case ERROR_VALIDACION:
			estado = VALIDADO;
			break;
		case VALIDADO:
		case ERROR_PROCESAMIENTO:
			estado = PROCESADO;
			break;
		default:
			String msg = "El estado del archivo con id=%d es %s. En este estado no puede tener cambios de estado.";
			msg = String.format(msg, getId(), getEstado().toString());
			throw new RuntimeException(msg);
		}
		setEstado(estado);
	}

	public void validadoConErrores() {
		EstadoArchivoType estado;

		switch (getEstado()) {
		case NO_PROCESADO:
		case ERROR_ESTRUCTURA:
			estado = ERROR_ESTRUCTURA;
			break;
		case ESTRUCTURA_VALIDA:
		case ERROR_VALIDACION:
			estado = ERROR_VALIDACION;
			break;
		case VALIDADO:
		case ERROR_PROCESAMIENTO:
			estado = ERROR_PROCESAMIENTO;
			break;
		default:
			String msg = "El estado del archivo con id=%d es %s. En este estado no se le pueden adicionar errores.";
			msg = String.format(msg, getId(), getEstado().toString());
			throw new RuntimeException(msg);
		}
		setEstado(estado);
	}
}
