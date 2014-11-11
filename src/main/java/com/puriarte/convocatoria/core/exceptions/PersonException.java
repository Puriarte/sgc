package com.puriarte.convocatoria.core.exceptions;

public class PersonException extends Exception {

	private static final long serialVersionUID = 1L;

	public static final int REPEATED = 0;

	public static final int DOCUMENT_TYPE_NOT_FOUND = 0;
	public static final int DOCUMENT_EMPTY= 1;

	public static final int PERSON_ALREADY_EXISTS=2;


	private int idException;

	public PersonException(int idException) {
		this.idException =idException;

	}

	public int getIdException() {
		return idException;
	}

	public void setIdException(int idException) {
		this.idException = idException;
	}

}
