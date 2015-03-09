package com.puriarte.convocatoria.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;


import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.core.exceptions.MovilException;
import com.puriarte.convocatoria.core.exceptions.PersonException;
import com.puriarte.convocatoria.core.exceptions.SMSException;
import com.puriarte.convocatoria.persistence.DocumentType;
import com.puriarte.convocatoria.persistence.Movil;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.SMS;

// ESTE m�todo simula la recepci�n de SMS de registro que se har�a mandando
// el SMS ALTA NROCEDULA
//TODO: Hay que hacerlo para otro tipo de documentos
public class EscolaridadTest {

	@Test
	public void pruebaDeMensajesRecibidosParaRegistro() throws ParseException{
		SimpleDateFormat dTF = new SimpleDateFormat("dd-MM-yyyy", new Locale("ES"));
		Date fechaInicio = dTF.parse("21-09-2014");
		Date fechaFin = dTF.parse("29-09-2014");
		
//		System.out.println(Facade.getInstance().selectCountSMS(312,1,fechaInicio,fechaFin));
//		System.out.println(Facade.getInstance().selectCountSentSMS(312,fechaInicio,fechaFin));
//		System.out.println(Facade.getInstance().selectCountExpiredSMS(312,fechaInicio,fechaFin));

	}

}
