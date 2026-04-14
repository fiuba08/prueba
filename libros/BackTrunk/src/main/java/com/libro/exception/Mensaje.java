package com.libro.exception;

public final class Mensaje {

	private Mensaje() {
	}

	// MGS Generales
	public static final String MGS_OK = "Data Base H2 in directory./target/BaseDatos/ init OK ";
	public static final String REST_REQUEST_TO_SAVE_LIBRO = "REST request to save Libro : {}";

	// MGS Error
	public static final String REST_REQUEST_TO_GET_ALL_LIBROS = "REST request to get all Libros  cant : ";
	public static final String EXISTE_LIBRO = "Ya existe libro";
	public static final String NO_EXISTE_LIBRO = "NO existe libro";
	public static final String ID_NULL = "Id null";
	public static final String INVALID_ID = "Invalid ID";
	public static final String ID_NOT_FOUND = "Id not found";
	
}
