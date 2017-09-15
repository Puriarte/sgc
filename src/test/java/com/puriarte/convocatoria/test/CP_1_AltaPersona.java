package com.puriarte.convocatoria.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.Person;

// ESTE método simula la recepción de SMS de registro que se haría mandando
// el SMS ALTA NROCEDULA
//TODO: Hay que hacerlo para otro tipo de documentos
public class CP_1_AltaPersona {

	private List<Person> idPersonasCreadas = new ArrayList();
	private String movil1 = "59894440714";
	private String movil2= "59898843084";
	private String movil3= "59899627237";

	private String cedulaPersona1= "";
	private String cedulaPersona2= "33173535";
	private String cedulaPersona3= "34023364";

	
/*	@Test
 * public void pruebaDeMensajesRecibidosParaRegistro() throws ParseException{
		SimpleDateFormat dTF = new SimpleDateFormat("dd-MM-yyyy", new Locale("ES"));
		Date date = dTF.parse("03-11-2014");

//		Facade.getInstance().selectPersonList(null, 0, 1, null, 0, 100);

		registrarPersona("REGISTRO " + cedulaPersona1 , movil1, cedulaPersona1, date);
		registrarPersona("REGISTRO " + cedulaPersona2 , movil2, cedulaPersona2, date);
		registrarPersona("REGISTRO " + cedulaPersona3, movil3, cedulaPersona3, date);
//		registrarPersona("REGISTRO " + cedulaPersona4, movil4, cedulaPersona4, date);
	}
*/
	private void registrarPersona(String text, String movil, String cedula, Date date){
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
//					Facade.getInstance().insertSMSIncome(movil, text, c.getTime());
//				}
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}

/*	@Test
	public void registrarSMSEntrante() {

		try{

			Facade.getInstance().insertSMSIncomeAndRegisterMovil("59898312915", "Saludos", new Date());



			// Creo la persona con la cedula dada.
			// Si la persona ya está creada mando una excepción.
			Facade.getInstance().insertMovil(nroDestinoSMS1, cedulaPersona1 , Constants.PERSON_TYPE_CI);
		} catch (MovilException mexe) {
			if (mexe.getIdException()==MovilException.DUPLICATED)
				System.out.println("El movil " + nroDestinoSMS1 + " ya está asociado a la ceula" + cedulaPersona1);
			else if (mexe.getIdException()==MovilException.ALREADY_IN_USE)
				System.out.println("El movil " + nroDestinoSMS1 + " ya está asociado a otro nro de ceula. Debe pedirle a un administrador que verifique la situación ");
			//else
				//System.out.println("El movil " + nroDestinoSMS1 + " ya está asociado a la ceula" + cedulaPersona1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SMSException e) {
			e.printStackTrace();
		}catch(PersonException pex){
			pex.printStackTrace();
		}

	}
*/

