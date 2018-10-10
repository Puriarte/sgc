package com.puriarte.convocatoria.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.puriarte.convocatoria.core.exceptions.MovilException;
import com.puriarte.convocatoria.core.exceptions.PersonException;
import com.puriarte.convocatoria.core.exceptions.SMSException;
import com.puriarte.convocatoria.persistence.Person;


public class TestSMS {

	private List<Person> idPersonasCreadas = new ArrayList();
	private String nroDestinoSMS1= "098312918";
	private String cedulaPersona1= "36025670";


	@Test
	public void registrarSMSEntrante() throws SMSException, PersonException, MovilException, SQLException {

//		try {
//			Facade.getInstance().insertSMSIncome("59898312914", "Saludos", new Date());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//		}
//		try {
//			Facade.getInstance().insertSMSIncomeAndRegisterMovil("59898312914", "REGISTRO 36025670", new Date());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//		}
//		try {
//			Facade.getInstance().insertSMSIncome("59899607218", "Hola amroso!!!!", new Date());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//		}


	/*	try{
			// Creo la persona 1 para mandarle un SMS. Si la persona ya est� creada va a mandar una excepci�n.
			// La funcionalidad que maneje este m�todo deber�
				Facade.getInstance().insertMovil(nroDestinoSMS1, cedulaPersona1 , Constants.PERSON_TYPE_CI);
		} catch (MovilException mexe) {
			System.out.println("El movil ya est� asociado a la persona");
		} catch (SMSException e) {
		}catch(PersonException pex){
		}
*/
		try{
	/*		if(!Facade.getInstance().existSMS("59898312914", new Date())){
			Movil movil = Facade.getInstance().selectMovil("59898312914", Constants.MOVIL_STATUS_ACTIVE);
			if (movil!=null){
				Facade.getInstance().insertSMSOutcome(movil,"test");
			}
			}
*/
		// Este m�todo debe verificar que
		//	1- el nro Destino est� asociado a una persona.
		//		si no lo est� se cambia al estado a SMS rechazado.
		//  2- el nro est� activo
		//		si no lo est� se cambia al estado a SMS rechazado.
	//	Facade.getInstance().insertSMSIncome(nroDestinoSMS1, "Hola este es un mensaje de prueba ");

		//Movil movil = Facade.getInstance().obtenerMovil(nroDestinoSMS1);

		/*	SMS sms = new SMS();
			sms.setMensaje("PRUEBA de MENSAJE UNITARIO");


			sms.setMovil(movil);
			Facade.getInstance().insertSMS(sms);
			*/
		}catch(Exception sex){

		}
	}

	/*@Test
	public void borrarPersonas() {
		try {
			for(Person person :idPersonasCreadas){
				Facade.getInstance().removePerson(person);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
	}*/
}
