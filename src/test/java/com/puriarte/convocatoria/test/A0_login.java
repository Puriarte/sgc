package com.puriarte.convocatoria.test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import com.puriarte.convocatoria.persistence.Person;

// ESTE método simula la recepción de SMS de registro que se haría mandando
// el SMS ALTA NROCEDULA
//TODO: Hay que hacerlo para otro tipo de documentos
public class A0_login{

	private List<Person> idPersonasCreadas = new ArrayList();
	private String category1 = "UNO";
	private String category2= "DOS";
	private String category3= "TRES";

	@Test
	public void pruebaDeMensajesRecibidosParaRegistro() throws ParseException, SQLException{

		System.out.println(System.getenv("JAVA_HOME"));
		System.out.println(System.getenv("GCP_DATA_DIR"));
		
		String texto="demo";
		String encript=DigestUtils.sha256Hex(texto);
		System.out.println("shaHex:"+encript);
		
	} 
	
	
}
