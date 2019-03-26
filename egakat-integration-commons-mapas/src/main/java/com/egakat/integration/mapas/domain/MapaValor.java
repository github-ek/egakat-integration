package com.egakat.integration.mapas.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapaValor implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 200)
	@Column(name = "clave")
	private String clave;

	@NotNull
	@Size(max = 200)
	@Column(name = "valor")
	private String valor;
}