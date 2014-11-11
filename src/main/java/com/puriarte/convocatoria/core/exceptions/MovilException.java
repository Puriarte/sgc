package com.puriarte.convocatoria.core.exceptions;

public class MovilException extends Exception {

	private static final long serialVersionUID = 1L;
	public static final int DUPLICATED = 0;
	public static final int ALREADY_IN_USE = 1;
	public static final int EMPTY = 2;

	private int idException;

	public MovilException(int idException) {
		this.idException=idException;
	}

	public int getIdException() {
		return idException;
	}

	public void setIdException(int idException) {
		this.idException = idException;
	}

}
