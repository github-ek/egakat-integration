package com.egakat.integration.config.mapas.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.NumberFormat;

import com.egakat.core.data.jpa.domain.AuditableEntity;
import com.egakat.core.domain.InactivableObject;
import com.egakat.core.domain.ObjectWithCode;
import com.egakat.core.domain.SortableObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "mapas")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_mapa"))
public class Mapa extends AuditableEntity<Long> implements ObjectWithCode<Long>, SortableObject, InactivableObject {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_grupo_mapa", nullable = false)
	@NotNull
	private GrupoMapa grupoMapa;

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

	@NotNull
	@Size(max = 200)
	@Column(name = "patron_clave")
	private String patronClave;

	@NumberFormat
	@Column(name = "ordinal")
	private int ordinal;

	@Column(name = "activo")
	private boolean activo;

	@ElementCollection
	@CollectionTable(name = "mapas_valores", joinColumns = @JoinColumn(name = "id_mapa", nullable = false))
	private List<MapaValor> valores = new ArrayList<>();

}