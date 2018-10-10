package com.puriarte.convocatoria.core.exceptions;

public class PersonCategoryException extends Exception {

	private static final long serialVersionUID = 1L;
	public static final int DUPLICATED = 0;
	public static final int DUPLICATED_MORE_THAN_ONCE = 1;
	public static final int NAME_REQUIRED = 2;
//	public static final int ALREADY_IN_USE = 1;
//	public static final int EMPTY = 2;

	private int idException;

	public PersonCategoryException(int idException) {
		this.idException=idException;
	}

	public int getIdException() {
		return idException;
	}

	public void setIdException(int idException) {
		this.idException = idException;
	}

	@Override
	public String getMessage(){
		if (this.idException==DUPLICATED)
			return "category.error.duplicated";
		else if (this.idException==DUPLICATED_MORE_THAN_ONCE)
			return "category.error.duplicated.more.than.once";
		else if (this.idException==NAME_REQUIRED)
			return "category.error.name.required";
		else
			return "error.default";
	}
}
