
package com.egakat.integration.maps.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import com.egakat.commons.dto.BusinessEntityDto;
import com.egakat.core.domain.InactivableObject;
import com.egakat.core.domain.ObjectWithCode;
import com.egakat.core.domain.SortableObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MapaDto extends BusinessEntityDto<Long> implements ObjectWithCode<Long>, SortableObject, InactivableObject {

	private long idGrupoMapa;

	@NotNull
	@Size(max = 50)
	private String codigo;

	@NotNull
	@Size(max = 100)
	private String nombre;

	@NotNull
	@Size(max = 200)
	private String descripcion;

	@NotNull
	@Size(max = 200)
	private String patronClave;

	@NumberFormat
	private int ordinal;

	private boolean activo;

	private Map<String,String> valores;
	
	public static class MapaDtoBuilder{
		private Map<String,String> valores = new HashMap<>();
	}

	@Builder
	public MapaDto(Long id, int version, String creadoPor, LocalDateTime fechaCreacion, String modificadoPor,
			LocalDateTime fechaModificacion, long idGrupoMapa, @NotNull @Size(max = 50) String codigo,
			@NotNull @Size(max = 100) String nombre, @NotNull @Size(max = 200) String descripcion,
			@NotNull @Size(max = 200) String patronClave, int ordinal, boolean activo, Map<String, String> valores) {
		super(id, version, creadoPor, fechaCreacion, modificadoPor, fechaModificacion);
		this.idGrupoMapa = idGrupoMapa;
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.patronClave = patronClave;
		this.ordinal = ordinal;
		this.activo = activo;
		this.valores = valores;
	}
}