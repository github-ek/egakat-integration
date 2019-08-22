package com.egakat.integration.archivos.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.NumberFormat;

import com.egakat.core.data.jpa.domain.AuditableEntity;
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

	@Column(name = "id_tipo_archivo", nullable = false)
    private long idTipoArchivo;

	@NotNull
	@Size(max = 50)
	private String codigo;

	@NumberFormat
	private int ordinal;

	private boolean activo;
}
