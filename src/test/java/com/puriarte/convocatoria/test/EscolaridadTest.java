package com.puriarte.convocatoria.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

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
