package com.puriarte.convocatoria.test;

import static org.junit.Assert.*;

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

public class SendMail {

	@Test
	public void test() throws ParseException {

		PersonMovil movil = Facade.getInstance().selectPersonMovil("098312914",
				Constants.MOVIL_STATUS_ACTIVE);
		Facade.getInstance().enviarMail(movil, "PRUEBA DE MENSAJE ERRONEO");

	}

}
