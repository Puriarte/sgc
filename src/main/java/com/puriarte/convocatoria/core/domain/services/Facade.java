package com.puriarte.convocatoria.core.domain.services;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.puriarte.convocatoria.core.exceptions.MovilException;
import com.puriarte.convocatoria.core.exceptions.PersonException;
import com.puriarte.convocatoria.core.exceptions.SMSException;
import com.puriarte.convocatoria.persistence.Assignment;
import com.puriarte.convocatoria.persistence.AssignmentStatus;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.DocumentType;
import com.puriarte.convocatoria.persistence.Job;
import com.puriarte.convocatoria.persistence.Movil;
import com.puriarte.convocatoria.persistence.MovilStatus;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.Place;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.convocatoria.persistence.SmsStatus;
import com.puriarte.convocatoria.persistence.User;
import com.puriarte.convocatoria.core.domain.Constants;

public class Facade {
	
	private static Facade INSTANCE = null;
	private UserService userService;
	private SMSService smsService;
	private PersonService personService;
	private MovilService movilService;
	private DispatchService dispatchService;
	private DocumentTypeService documentTypeService;
	private PersonCategoryService personCategoryService;
	private JobService jobService;
	private AssignmentStatusService assignmentStatusService;
	private PlaceService placeService;
	private PersonMovilService personMovilService;
	private BulkSMSService bulkSMSService;


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
