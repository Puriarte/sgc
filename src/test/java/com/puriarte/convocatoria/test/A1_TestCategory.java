package com.puriarte.convocatoria.test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.Person;

// ESTE método simula la recepción de SMS de registro que se haría mandando
// el SMS ALTA NROCEDULA
//TODO: Hay que hacerlo para otro tipo de documentos
public class A1_TestCategory {

	private List<Person> idPersonasCreadas = new ArrayList();
	private String category1 = "UNO";
	private String category2= "DOS";
	private String category3= "TRES";

	@Test
	public void pruebaDeMensajesRecibidosParaRegistro() throws ParseException, SQLException{
		listarCategoriasAlMomento("LISTA DE CATEGORIAS AL INICIO");
		/*
		System.out.println("AGREGO CATEGORIA " + category1);
		PersonCategory cat = new PersonCategory();
		cat.setName(category1);;
		Facade.getInstance().insertPersonCategory(cat);

		listarCategoriasAlMomento("LISTA DE CATEGORIAS AL AGREGO CATEGORIA");

		PersonCategory cat1 = Facade.getInstance().selectPersonCategory(cat.getId());
		System.out.println("MODIFICO EL NOMBRE DE LA CATEGORIA " + category1 + " A " +category2);
 		cat.setName(category2);;
		Facade.getInstance().updatePersonCategory(cat1);

		listarCategoriasAlMomento("LISTA DE CATEGORIAS AL MODIFICAR CATEGORIA");

		System.out.println("BORRO DE LA CATEGORIA " +category2);
		Facade.getInstance().removePersonCategory(cat1);

		listarCategoriasAlMomento("LISTA DE CATEGORIAS AL ELIMINAR CATEGORIA"+ category2);
*/
	}


	private void listarCategoriasAlMomento(String mensaje){

		ArrayList<PersonCategory> categories = (ArrayList<PersonCategory> )Facade.getInstance().selectPersonCategoryList();
		System.out.println(mensaje);
		if (categories!=null){
		for(PersonCategory aux : categories){
			System.out.println(aux.getId() + " " + aux.getName());
		}
		}

	}
}
