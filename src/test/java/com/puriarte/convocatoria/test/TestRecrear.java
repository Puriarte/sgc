package com.puriarte.convocatoria.test;

import java.sql.SQLException;
import java.text.ParseException;

import org.junit.Test;

import com.puriarte.convocatoria.persistence.EntityManagerHelper;

// ESTE m�todo simula la recepci�n de SMS de registro que se har�a mandando
// el SMS ALTA NROCEDULA
//TODO: Hay que hacerlo para otro tipo de documentos
public class TestRecrear {

	@Test
	public void test() throws ParseException, SQLException{
		EntityManagerHelper.getEntityManager();
	}


}
