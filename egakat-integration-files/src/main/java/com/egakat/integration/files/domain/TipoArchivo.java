package com.egakat.integration.files.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "tipos_archivo")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_tipo_archivo"))
public class TipoArchivo extends BusinessEntity<Long>
		implements ObjectWithCode<Long>, SortableObject, InactivableObject {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_grupo_tipo_archivo", nullable = false)
	@NotNull
	private GrupoTipoArchivo grupoTipoArchivo;

	@NotNull
	@Column(unique = true)
	@Size(max = 50)
	private String codigo;

	@NotNull
	@Size(max = 100)
	private String nombre;

	@NotNull
	@Size(max = 200)
	private String descripcion;

	@NotNull
	@Size(max = 4)
	@Column(name = "separador_registros")
	private String separadorRegistros;

	@NotNull
	@Size(max = 4)
	@Column(name = "separador_campos")
	private String separadorCampos;

	@NumberFormat
	private int ordinal;

	private boolean activo;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "tipoArchivo")
	private Set<Campo> campos = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "tipoArchivo")
	private Set<Directorio> directorios = new HashSet<Directorio>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "tipoArchivo")
	private Set<Llave> llaves = new HashSet<>();

}