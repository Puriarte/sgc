package com.puriarte.convocatoria.test;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.Job;
import com.puriarte.convocatoria.persistence.Movil;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonMovil;

public class A4_TestPerson {

//	private String dispatchNombre1 = "CONVOCATORIA 1";
//	private String dispatchNombre2 = "CONVOCATORIA 2";
//
//	private String jobNombre1 = "PUESTO 1";
//	private String jobNombre2 = "PUESTO 2";
//	private String jobNombre3 = "PUESTO 3";
//	private String jobNombre3Modified = "PUESTO 3 MODIFICADO";
//
//	private String categoryNombre1 = "CATEGORIA 1";
//	private String categoryNombre2 = "CATEGORIA 2";
//
//	Category cat1;
//	Category cat2;
//	Dispatch dispatch1;
//	Dispatch dispatch2;

	@Before
	public void setup() throws SQLException{
//		 cat1 = new Category();
//		cat1.setName(categoryNombre1);;
//		Facade.getInstance().insertCategory(cat1);
//
//		 cat2 = new Category();
//		cat2.setName(categoryNombre2);;
//		Facade.getInstance().insertCategory(cat2);
//
//		 dispatch1 = new Dispatch();
//		dispatch1.setName(dispatchNombre1);
//		dispatch1.setScheduledDate(new Date());
//		Facade.getInstance().insertDispatch(dispatch1);
//
//		 dispatch2 = new Dispatch();
//		dispatch2.setName(dispatchNombre2);
//		dispatch2.setScheduledDate(new Date());
//		Facade.getInstance().insertDispatch(dispatch2);

	}

	@After
	public void end() throws SQLException{
//		Facade.getInstance().removeCategory(cat1);
//		Facade.getInstance().removeCategory(cat2);
//		Facade.getInstance().removeDispatch(dispatch1);
//		Facade.getInstance().removeDispatch(dispatch2);
	}

	@Test
	public void TestPerson() throws SQLException{
		listarPersonAlMomento("LISTA DE PERSONAS AL INICIO");
	}

	private void listarPersonAlMomento(String mensaje){
		List<String> priorities = new ArrayList<String>();
		priorities.add("0");

		List<PersonMovil> list = Facade.getInstance().selectPersonMovilList(priorities, 1 ,1,  "", 0, 100);
		/*		System.out.println(mensaje);
		if (list!=null){
			for(PersonMovil aux : list){
				System.out.println(aux.getId() + " " + aux.getMovil().getNumber());
			}
		}
	*/

	}

}