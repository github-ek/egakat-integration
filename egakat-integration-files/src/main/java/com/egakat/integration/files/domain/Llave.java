package com.egakat.integration.files.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.NumberFormat;

import com.egakat.commons.domain.AuditableEntity;
import com.egakat.core.domain.InactivableObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "llaves")
@DynamicUpdate
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "id_llave"))
public class Llave extends AuditableEntity<Long> implements InactivableObject {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_tipo_archivo", nullable = false)
	@NotNull
	private TipoArchivo tipoArchivo;

	@NotNull
	@Size(max = 50)
	private String codigo;

	@NumberFormat
	private int ordinal;

	private boolean activo;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "llaves_campos", joinColumns = @JoinColumn(name = "id_llave", referencedColumnName = "id_llave"), inverseJoinColumns = @JoinColumn(name = "id_campo", referencedColumnName = "id_campo"))
	private List<Campo> campos = new ArrayList<>();

}
