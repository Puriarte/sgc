package com.puriarte.convocatoria.test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.core.exceptions.PersonCategoryException;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.Job;
import com.puriarte.convocatoria.persistence.PersonCategory;

public class A3_TestJob {

	private String dispatchNombre1 = "CONVOCATORIA 1";
	private String dispatchNombre2 = "CONVOCATORIA 2";

	private String jobNombre1 = "PUESTO 1";
	private String jobNombre2 = "PUESTO 2";
	private String jobNombre3 = "PUESTO 3";
	private String jobNombre3Modified = "PUESTO 3 MODIFICADO";

	private String categoryNombre1 = "CATEGORIA 1";
	private String categoryNombre2 = "CATEGORIA 2";

	PersonCategory cat1;
	PersonCategory cat2;
	Dispatch dispatch1;
	Dispatch dispatch2;

	@Before
	public void setup() throws PersonCategoryException, Exception{
		 cat1 = new PersonCategory();
		cat1.setName(categoryNombre1);;
		Facade.getInstance().insertPersonCategory(cat1);

		 cat2 = new PersonCategory();
		cat2.setName(categoryNombre2);;
		Facade.getInstance().insertPersonCategory(cat2);

		 dispatch1 = new Dispatch();
		dispatch1.setName(dispatchNombre1);
		dispatch1.setScheduledDate(new Date());
//		Facade.getInstance().insertDispatch(dispatch1);

		 dispatch2 = new Dispatch();
		dispatch2.setName(dispatchNombre2);
		dispatch2.setScheduledDate(new Date());
//		Facade.getInstance().insertDispatch(dispatch2);

	}

	@After
	public void end() throws SQLException{
		Facade.getInstance().removePersonCategory(cat1);
		Facade.getInstance().removePersonCategory(cat2);
		Facade.getInstance().removeDispatch(dispatch1);
		Facade.getInstance().removeDispatch(dispatch2);
	}

	@Test
	public void TestJob() throws SQLException{

		listarJobAlMomento("LISTA DE PUESTOS AL INICIO");

		System.out.println("AGREGO PUESTO " + jobNombre1 + " y " + jobNombre2 + " a " + dispatch1.getName());

		Job job1 = new Job();
		job1.setCategory(cat1);
		job1.setDispatch(dispatch1);
		job1.setName(jobNombre1);

		Job job2 = new Job();
		job2.setCategory(cat2);
		job2.setDispatch(dispatch1);
		job2.setName(jobNombre2);

		Facade.getInstance().insertJob(job1);
		Facade.getInstance().insertJob(job2);

		listarJobAlMomento("LISTA DE PUESTOS LUEGO DE INGRESAR " + jobNombre1 + " y " + jobNombre2 );

		System.out.println("AGREGO PUESTO " + jobNombre3 + " a " + dispatch2);

		Job job3 = new Job();
		job3.setCategory(cat2);
		job3.setDispatch(dispatch2);
		job3.setName(jobNombre3);
		Facade.getInstance().insertJob(job3);

		listarJobAlMomento("LISTA DE PUESTOS LUEGO DE INGRESAR " + jobNombre3);

		System.out.println("MODIFICO EL NOMBRE DEL PUESTO " +jobNombre3 );
		job3.setName(jobNombre3Modified);
		Facade.getInstance().updateJob(job3);

		listarJobAlMomento("LISTA DE PUESTOS LUEGO DE MODIFICAR " + jobNombre3Modified);

		System.out.println("BORRO " + job1);
		Facade.getInstance().removeJob(job1);
		listarJobAlMomento("LISTA DE PUESTOS AL ELIMINAR PUESTO " + job1 );
		System.out.println("BORRO " + job2);
		Facade.getInstance().removeJob(job2);
		System.out.println("BORRO " + job3);
		Facade.getInstance().removeJob(job3);

		listarJobAlMomento("LISTA DE PUESTOS AL ELIMINAR PUESTOS");


	}

	private void listarJobAlMomento(String mensaje){
		List<Job> list = Facade.getInstance().selectJobList(0,"",1,100);
		System.out.println(mensaje);
		if (list!=null){
		for(Job aux : list){
			System.out.println(aux.getId() + " " + aux.getName());
		}
	}

}}