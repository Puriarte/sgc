package com.puriarte.convocatoria.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.SMS;

public class A4_ListSMS {

	@Test
	public void test() throws ParseException {

		SimpleDateFormat dTF = new SimpleDateFormat("dd-MM-yyyy", new Locale("ES"));
		Date fecha1 = dTF.parse("01-09-2014");
		Date fecha2 = dTF.parse("30-11-2014");

		List<SMS> smss= Facade.getInstance().selectSMSList(fecha1, fecha2, 0, 0, "4", 0, 100);


		if (smss==null)
			fail("No hay sms");
		else
			System.out.print(smss.size());

	}

}
