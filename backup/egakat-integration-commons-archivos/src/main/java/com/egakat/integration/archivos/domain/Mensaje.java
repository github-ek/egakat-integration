package com.egakat.integration.archivos.domain;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.egakat.core.data.jpa.domain.AuditableEntity;
import com.egakat.integration.archivos.enums.EstadoMensajeType;

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
abstract public class Mensaje extends AuditableEntity<Long> {

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private EstadoMensajeType estado;

	@Column(name = "fecha_envio")
	private LocalDateTime fechaEnvio;

	@Column(name = "fecha_confirmacion_envio")
	private LocalDateTime fechaConfirmacionEnvio;

	abstract public String correlacion();
	
}