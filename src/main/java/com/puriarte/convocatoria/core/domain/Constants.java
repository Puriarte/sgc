package com.puriarte.convocatoria.core.domain;

public final class Constants {

	public static final  int PERSON_TYPE_CI = 1;

	//  ESTADOS DE SMS
	public static final int SMS_STATUS_RECIBIDO = 1;			// Activo es recibido o ya enviado
	public static final int SMS_STATUS_NOT_REGISTRED = 2;		// Correspodne a un movil no registrado con documento
	public static final int SMS_STATUS_MULTIPLE_PERSON = 3;		//
	public static final int SMS_STATUS_PENDIENTE = 4;			// Pendiente de ser enviado
	public static final int SMS_STATUS_ENVIADO= 5;				// Pendiente de ser enviado
	public static final int SMS_STATUS_REGISTRATION_FAILED= 6;
	// DIRECCION DE SMS
	public static final int SMS_ACTION_INCOME= 1;
	public static final int SMS_ACTION_OUTCOME = 2;

	//  ESTADOS DE MOVIL
	public static final int MOVIL_STATUS_ACTIVE = 1;
	public static final int MOVIL_STATUS_PENDING = 2;  // Estado pendiente cuando se agrega un movil para una persona
														// pero ese nro ya tiene otra persona asignada

	public static final String FORMATO_FECHA_HTML5_REGEX = "^[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]";
    public static final String FORMATO_FECHA_HTML5="yyyy-MM-dd";

    public static final String FORMATO_FECHA_HORA_HTML5_REGEX = "^[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9] [0-2][0-9]:[0-6][0-9]:[0-6][0-9]";
    public static final String FORMATO_FECHA_HORA_HTML5="yyyy-MM-dd hh:mm:ss";

    public static final String FORMATO_HORA_HTML5="hh:mm:ss";


    public static final int ASSIGNMENT_STATUS_PENDING = 1;
    public static final int ASSIGNMENT_STATUS_ASSIGNED = 2;
    public static final int ASSIGNMENT_STATUS_CANCELED= 3;
    public static final int ASSIGNMENT_STATUS_EXPIRED = 4;

}
