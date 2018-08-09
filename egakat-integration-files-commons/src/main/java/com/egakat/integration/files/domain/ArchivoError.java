package com.egakat.integration.files.domain;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.egakat.commons.domain.BusinessEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "archivos_errores")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class ArchivoError extends BusinessEntity<Long> {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_archivo", nullable = false)
	@NotNull
	private Archivo archivo;

	@Column(name = "numero_linea")
	private int numeroLinea;

	@NotNull
	@Size(max = 50)
	private String codigo;

	@NotNull
	@Size(max = 1024)
	private String mensaje;

	@NotNull
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String datos;

	@Builder
	public ArchivoError(Long id, int version, LocalDateTime fechaCreacion, String creadoPor,
			LocalDateTime fechaModificacion, String modificadoPor, @NotNull Archivo archivo, int numeroLinea,
			@NotNull @Size(max = 50) String codigo, @NotNull @Size(max = 1024) String mensaje, @NotNull String datos) {
		super(id, version, fechaCreacion, creadoPor, fechaModificacion, modificadoPor);
		this.archivo = archivo;
		this.numeroLinea = numeroLinea;
		this.codigo = codigo;
		this.mensaje = mensaje;
		this.datos = datos;
	}
}