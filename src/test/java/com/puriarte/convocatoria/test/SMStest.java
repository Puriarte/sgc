package com.puriarte.convocatoria.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.SMS;

public class SMStest {

	@Test
	public void test() throws ParseException {

		SimpleDateFormat dTF = new SimpleDateFormat("dd-MM-yyyy", new Locale("ES"));
		Date fecha1 = dTF.parse("01-09-2014");
		Date fecha2 = dTF.parse("30-09-2014");

//		List<SMS> smss= Facade.getInstance().selectSMSList(fecha1, fecha2, 0, 0, "4", 0, 100);
//
//
//		if (smss==null)
//			fail("No hay sms");
//		else
//			System.out.print(smss.size());

	}
	@Test
	public void test2()  {
		try{
			PersonMovil movil = Facade.getInstance().selectPersonMovil("098312914",
					Constants.MOVIL_STATUS_ACTIVE);
			
			List<SMS> smsList = Facade
				.getInstance()
				.SelectRelatedSMSList(
						movil, 
						"",
						Facade.getInstance().selectAssingmentStatus(Constants.ASSIGNMENT_STATUS_ASSIGNED),
						new Date(), 0, 1);
		}catch(Exception e){
		}
	}

}
