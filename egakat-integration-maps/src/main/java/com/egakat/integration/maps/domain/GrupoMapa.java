package com.egakat.integration.maps.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.NumberFormat;

import com.egakat.commons.domain.BusinessEntity;
import com.egakat.core.domain.InactivableObject;
import com.egakat.core.domain.ObjectWithCode;
import com.egakat.core.domain.SortableObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "grupos_mapa")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_grupo_mapa"))
public class GrupoMapa extends BusinessEntity<Long> implements ObjectWithCode<Long>, SortableObject, InactivableObject {

	@NotNull
	@Size(max = 50)
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Size(max = 100)
	@Column(name = "nombre")
	private String nombre;

	@NotNull
	@Size(max = 200)
	@Column(name = "descripcion")
	private String descripcion;

	@NumberFormat
	@Column(name = "ordinal")
	private int ordinal;

	@Column(name = "activo")
	private boolean activo;
}