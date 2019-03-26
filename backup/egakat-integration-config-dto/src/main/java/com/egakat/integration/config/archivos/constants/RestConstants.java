package com.egakat.integration.config.archivos.constants;

public class RestConstants {

	final public static String base = "/api/archivos";

	final public static String grupoTipoArchivo = base + "/grupo-tipo-archivo";

	final public static String tiposArchivoByGrupoTipoArchivo = "/{id}/tipos-archivo";

	final public static String tipoArchivo = base + "/tipo-archivo";

	final public static String camposByTipoArchivo = "/{id}/campos";

	final public static String llavesByTipoArchivo = "/{id}/llaves";

	final public static String directoriosByTipoArchivo = "/{id}/directorios";
	
	final public static String directoriosObservablesByTipoArchivo = "/{id}/directorios-observables";
}
