package com.puriarte.convocatoria.test;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.convocatoria.persistence.SmsStatus;

public class SendSMStest {

	@Test
	public void test() throws ParseException {
		SmsStatus smsStatusSent  = Facade.getInstance().selectSmsStatus(Constants.SMS_STATUS_ENVIADO);
		List<SMS> smsList = Facade.getInstance().getPendingSMS();
		for (SMS sms:smsList){
			try{
//	    		gsmModem.Sender(sms.getMovil().getNumber(), sms.getMensaje());
	    		sms.setSentDate(new Date());
				sms.setStatus(smsStatusSent);
				Facade.getInstance().updateSMS(sms);

			//	Thread.sleep(10000);
			}catch(Exception ex1){
			}
		}


	}

}
