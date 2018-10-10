package com.puriarte.convocatoria.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class ReciboRespuestasSMStest {

	private String movil1= "5989000001";
	private String movil2= "5989000002";
	private String movil3= "5989000003";
	private String movil4= "5989000004";
	private String movil5= "5989000005";

	@Test
	public void test() throws ParseException {
		SimpleDateFormat dTF = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", new Locale("ES"));
		Date date = dTF.parse("26-09-2014 03:55:00");
		reciboSMS("NO", movil1, date);
		reciboSMS("SI", movil3, date);
		reciboSMS("SI", movil2, date);
	//	reciboSMS("NO", movil3, date);
	}


	private void reciboSMS(String text, String movil, Date date){
		try{
			// Tengo problemas con los milisegundos así que los dejo en 0
			Calendar c = Calendar.getInstance();
			c.setTime( date);
			c.set(Calendar.MILLISECOND, 0);

			// Me fijo si el mensaje ya existe // ESTO PARA LA DEMO
//			if(!Facade.getInstance().existSMS(movil, c.getTime())){
//				// Si el SMS se inicia con el texto REGISTRO creo el movil en la base de datos.
//				if (text.toUpperCase().startsWith("REGISTRO")){
//					Facade.getInstance().insertSMSIncomeAndRegisterMovil(movil, text,c.getTime());
//				}else{
//					Facade.getInstance().insertSMSIncome(movil, text,  c.getTime());
//				}
//			}
		}catch(Exception e){
		}
	}
}
