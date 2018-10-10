package com.puriarte.convocatoria.test;

import java.sql.SQLException;
import java.text.ParseException;

import org.junit.Test;

import com.puriarte.convocatoria.persistence.EntityManagerHelper;

// ESTE método simula la recepción de SMS de registro que se haría mandando
// el SMS ALTA NROCEDULA
//TODO: Hay que hacerlo para otro tipo de documentos
public class TestRecrear {

	@Test
	public void test() throws ParseException, SQLException{
		EntityManagerHelper.getEntityManager();
	}


}
