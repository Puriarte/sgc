package com.puriarte.convocatoria.test;

import java.text.ParseException;

import org.junit.Test;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.PersonMovil;

public class SendMail {

	@Test
	public void test() throws ParseException {

		PersonMovil movil = Facade.getInstance().selectPersonMovil("098312914",
				Constants.MOVIL_STATUS_ACTIVE);
		Facade.getInstance().enviarMail(movil, "PRUEBA DE MENSAJE ERRONEO");

	}

}
