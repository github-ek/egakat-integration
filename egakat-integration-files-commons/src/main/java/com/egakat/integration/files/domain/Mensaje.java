package com.egakat.integration.files.domain;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.egakat.commons.domain.BusinessEntity;
import com.egakat.integration.files.enums.EstadoMensajeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_mensaje"))
abstract public class Mensaje extends BusinessEntity<Long> {

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private EstadoMensajeType estado;

	@Column(name = "fecha_envio")
	private LocalDateTime fechaEnvio;

	@Column(name = "fecha_confirmacion_envio")
	private LocalDateTime fechaConfirmacionEnvio;

	abstract public String correlacion();
	
	// @formatter:off
	public Mensaje(Long id, int version, 
			LocalDateTime FechaCreacion, String createdBy, 
			LocalDateTime FechaModificacion, String modifiedBy, 
			EstadoMensajeType estado, 
			LocalDateTime fechaEnvio, LocalDateTime fechaConfirmacionEnvio) {
		super(id, version, FechaCreacion, createdBy, FechaModificacion, modifiedBy);
		this.estado = estado;
		this.fechaEnvio = fechaEnvio;
		this.fechaConfirmacionEnvio = fechaConfirmacionEnvio;
	}
	// @formatter:on
}