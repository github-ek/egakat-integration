package com.egakat.io.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.egakat.core.data.jpa.domain.SimpleAuditableEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "suscripciones")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class SuscripcionEntity extends SimpleAuditableEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", updatable = false, nullable = false)
	@Setter(value = AccessLevel.PROTECTED)
	private Long id;

	@Column(name = "suscripcion", length = 50, nullable = false)
	@NotNull
	@Size(max = 50)
	private String suscripcion;

	@Column(name = "id_externo", length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String idExterno;

	@Column(name = "estado_suscripcion", length = 50, nullable = false)
	@Size(max = 50)
	@NotNull
	private String estadoSuscripcion;

	@Column(name = "arg0", length = 100, nullable = true)
	@Size(max = 100)
	private String arg0;

	@Column(name = "arg1", length = 100, nullable = true)
	@Size(max = 100)
	private String arg1;

	@Column(name = "arg2", length = 100, nullable = true)
	@Size(max = 100)
	private String arg2;

	@Column(name = "arg3", length = 100, nullable = true)
	@Size(max = 100)
	private String arg3;

	@Column(name = "arg4", length = 100, nullable = true)
	@Size(max = 100)
	private String arg4;

	@Column(name = "arg5", length = 100, nullable = true)
	@Size(max = 100)
	private String arg5;

	@Column(name = "arg6", length = 100, nullable = true)
	@Size(max = 100)
	private String arg6;

	@Column(name = "arg7", length = 100, nullable = true)
	@Size(max = 100)
	private String arg7;

	@Column(name = "arg8", length = 100, nullable = true)
	@Size(max = 100)
	private String arg8;

	@Column(name = "arg9", length = 100, nullable = true)
	@Size(max = 100)
	private String arg9;

}
