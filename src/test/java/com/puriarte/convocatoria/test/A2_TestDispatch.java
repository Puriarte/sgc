package com.puriarte.convocatoria.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.Movil;
import com.puriarte.convocatoria.persistence.Person;

public class A2_TestDispatch {

	private String dispatch1 = "CONVOCATORIA 1";
	private String dispatch2 = "CONVOCATORIA 2";

	@Test
	public void TestDispatch(){
		listarDispatchAlMomento("LISTA DE CONVOCATORIAS AL INICIO");

		Dispatch dispatch = new Dispatch();
		dispatch.setName(dispatch1);
		dispatch.setScheduledDate(new Date());

		System.out.println("AGREGO CONVOCATORIA " + dispatch1);
		Dispatch item = new Dispatch();
		item.setName(dispatch1);;
		Facade.getInstance().insertDispatch(item);

		listarDispatchAlMomento("LISTA DE CONVOCATORIAS AL AGREGAR CONVOCATORIA");

		Dispatch item1 = Facade.getInstance().selectDispatch(item.getId());
		System.out.println("MODIFICO EL NOMBRE DE LA CONVOCATORIA " + dispatch1 + " A " +dispatch2);
		item1.setName(dispatch2);;
		Facade.getInstance().updateDispatch(item1);

		listarDispatchAlMomento("LISTA DE CONVOCATORIAS AL MODIFICAR CONVOCATORIA");

		System.out.println("BORRO DE LA CONVOCATORIAS " +dispatch2);
		Facade.getInstance().removeDispatch(item1);

		listarDispatchAlMomento("LISTA DE CONVOCATORIA AL ELIMINAR CONVOCATORIA"+ dispatch2);


	}
	public void registrarSMSEntrante() {

		try{
			// Traigo las primeras tres personas
			List<String> priorities = new ArrayList<String>();
			priorities.add("1");
			List<Person> list1 =Facade.getInstance().selectPersonList(priorities, 0, 0, "",0, 100);

			ArrayList<Person> list3 = new ArrayList<Person>(list1);
			String nrosDestino="";
			for (Person persona: list3 ){
				nrosDestino=nrosDestino+persona.getId() + ",";
			}

			// Creo una convocatoria con esas tres personas
			String[] arPersonIds = nrosDestino.split(",");
			String strMensaje = "CONVOCATORIA AL EVENTO";
			String strName = "Convocatoria 1";
			Date scheduledDate = new Date();
			Date creationDate = new Date();

			int id = Facade.getInstance().insertDispatch(strMensaje, strName, null, creationDate, scheduledDate , arPersonIds, null);

		}catch(Exception e){
			fail(e.getMessage());
		}
	}




	private void listarDispatchAlMomento(String mensaje){

		List<Dispatch> list = Facade.getInstance().selectDispatchList(0,"",false, 1,100);
		System.out.println(mensaje);
		if (list!=null){
		for(Dispatch aux : list){
			System.out.println(aux.getId() + " " + aux.getName());
		}
		}

	}}
