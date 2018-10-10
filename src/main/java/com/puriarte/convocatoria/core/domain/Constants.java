package com.puriarte.convocatoria.core.domain;

public final class Constants {

	private Constants() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static final  int PERSON_TYPE_CI = 1;
	public static final  int PERSON_STATUS_ALL = 0;

	//  ESTADOS DE SMS
	public static final int SMS_STATUS_RECIBIDO = 1;			// Activo es recibido o ya enviado
	public static final int SMS_STATUS_NOT_REGISTRED = 2;		// Correspodne a un movil no registrado con documento
	public static final int SMS_STATUS_MULTIPLE_PERSON = 3;		// 
	public static final int SMS_STATUS_PENDIENTE = 4;			// Pendiente de ser enviado
	public static final int SMS_STATUS_ENVIADO= 5;				// Enviado
	public static final int SMS_STATUS_REGISTRATION_FAILED= 6;	// Envio Fallido
	public static final int SMS_STATUS_EN_ESPERA_CIERRE_DISPATCH= 7;	// En espera de que se confirme el dispatch
	
	// DIRECCION DE SMS
	public static final int SMS_ACTION_INCOME= 1;
	public static final int SMS_ACTION_OUTCOME = 2;

	//  ESTADOS DE MOVIL
	public static final int MOVIL_STATUS_ACTIVE = 1;
	public static final int MOVIL_STATUS_PENDING = 2;  // Estado pendiente cuando se agrega un movil para una persona
														// pero ese nro ya tiene otra persona asignada

	public static final String FORMATO_FECHA_HTML5_REGEX = "^[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]";
    public static final String FORMATO_FECHA_HTML5="yyyy-MM-dd";

	public static final String FORMATO_FECHA_HTML5_REGEX_ALT1 = "^[0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9][0-9]";
    public static final String FORMATO_FECHA_HTML5_ALT1="dd-MM-yyyy";

	public static final String FORMATO_FECHA_HTML5_REGEX_ALT2 = "^[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]";
    public static final String FORMATO_FECHA_HTML5_ALT2="dd/MM/yyyy";


    public static final String FORMATO_FECHA_HORA_HTML5_REGEX = "^[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9] [0-2][0-9]:[0-6][0-9]:[0-6][0-9]";
    public static final String FORMATO_FECHA_HORA_HTML5="yyyy-MM-dd hh:mm:ss";

    public static final String FORMATO_HORA_HTML5="hh:mm:ss";


    public static final int ASSIGNMENT_STATUS_PENDING = 1;
    public static final int ASSIGNMENT_STATUS_ASSIGNED = 2;
    public static final int ASSIGNMENT_STATUS_CANCELED= 3;
    public static final int ASSIGNMENT_STATUS_EXPIRED = 4;
    public static final int ASSIGNMENT_STATUS_ACCEPTED = 5;
    public static final int ASSIGNMENT_STATUS_REGECTED = 6;
    public static final int ASSIGNMENT_STATUS_MISSING = 7;
    public static final int ASSIGNMENT_STATUS_CONFIRMED = 8;

	public static final int DISPATCH_STATUS_ACTIVE = 1; 
	public static final int DISPATCH_STATUS_CLOSED = 2;

	public static final String PICTURE_EMPTY_MEDIA = "flag_mediana_.jpg"; 
	public static final String PICTURE_EMPTY_CHICA = "flag_chica_.jpg";

	public static final String PICTURE_PREFIX_MEDIA = "fm_"; 
	public static final String PICTURE_PREFIX_CHICA = "fc_";

	public static final String DEFAULT_IMAGE_NAME = null;

	public static final int PICTURE_WIDTH_CHICA = 30; 
	public static final int PICTURE_HEIGHT_CHICA = 30; 
	
	public static final int PICTURE_WIDTH_MEDIA = 30; 
	public static final int PICTURE_HEIGHT_MEDIA = 30;

	public static final String EXTRA_ATTRIBUTE_NO_LABEL = "NOLABEL";

	
	// Mensajes de error 
	public static final String ERROR_PARSE_DATE  = "Error al parsear la fecha";
	public static final String ERROR_CONFIRM_SMS = "Error confirmando SMS";

	public static final String RESULTADO_CONFIRM_SMS_OK = "0";
	public static final String RESULTADO_CONFIRM_SMS_FALLA = "1";

	
	public static final String ERROR_GET_SMS = "Error enviando SMS";
	
	public static final String RESULTADO_GET_SMS_OK = "0";
	public static final String RESULTADO_GET_SMS_FALLA = "1";

	
}
