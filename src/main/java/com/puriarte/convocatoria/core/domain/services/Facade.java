package com.puriarte.convocatoria.core.domain.services;

import com.puriarte.convocatoria.persistence.AssignmentStatus;
import com.puriarte.convocatoria.persistence.Movil;
import com.puriarte.convocatoria.persistence.MovilStatus;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;

public class Facade {

	private static Facade INSTANCE = null;
	private static synchronized void createInstance(){
		if(INSTANCE == null){
			INSTANCE = new Facade();
		//	Loader.loadInitialStructure(false);
		}
	}

	public static Facade getInstance(){
		if(INSTANCE == null) createInstance();
		return INSTANCE;
	}

	public AssignmentStatus selectAssingmentStatus(int assignmentStatusAssigned) {
		// TODO Auto-generated method stub
		return null;
	}

	public PersonMovil selectPersonMovil(int parseInt) {
		// TODO Auto-generated method stub
		return null;
	}

	public PersonCategory selectPersonCategory(int categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	public MovilStatus selectMovilStatus(int movilStatusPending) {
		// TODO Auto-generated method stub
		return null;
	}

	public int insertPerson(Person person) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void insertMovil(Movil movil) {
		// TODO Auto-generated method stub
		
	}

	public void insertPersonMovil(PersonMovil pm) {
		// TODO Auto-generated method stub
		
	}

	public Person getPerson(String document, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object selectMovil(String movilNumber, int movilStatusActive) {
		// TODO Auto-generated method stub
		return null;
	}


}
