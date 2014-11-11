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
//	private SMSService smsService;
	private PersonService personService;
	private MovilService movilService;
	//private DispatchService dispatchService;
	private DocumentTypeService documentTypeService;
	private PersonCategoryService personCategoryService;
	private JobService jobService;
	private AssignmentStatusService assignmentStatusService;
	private PlaceService placeService;
	private PersonMovilService personMovilService;
	private BulkSMSService bulkSMSService;
	
	private Facade(){
		this.userService = UserService.getInstance();
	//	this.smsService = SMSService.getInstance();
		this.personService = PersonService.getInstance();
		this.documentTypeService = DocumentTypeService.getInstance();
		this.movilService = MovilService.getInstance();
	//	this.dispatchService = DispatchService.getInstance();
		this.personCategoryService = PersonCategoryService.getInstance();
		this.jobService= JobService.getInstance();
		this.personMovilService = PersonMovilService.getInstance();
		this.assignmentStatusService = AssignmentStatusService.getInstance();
		this.placeService = PlaceService.getInstance();
		this.bulkSMSService = BulkSMSService.getInstance();
	}
	
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


	public synchronized void stopAll(){
		try {

			UserService.getInstance().destroy();
			Thread.sleep(500L);

			INSTANCE = null;
		} catch (Exception e) {
			this.userService = null;
			e.printStackTrace();
		} catch (Throwable e) {
			this.userService = null;
			e.printStackTrace();
		}
	}
	
	/**
	 * PERSONA
	 */
	public int insertPerson(Person p) throws SQLException{
		return this.personService.insertPerson(p);
	}

	public Person insertPerson(String document) throws SQLException {
		return personService.getInstance().insertPerson(document );
	}



	/**
	 * Borra lógica de Persona
	 * @param id
	 */
	public void removePerson(Person person)throws SQLException{
		this.personService.borrarPerson(person);
	}
	
	/*
	 * MOVIL
	 * */


	/**
	 * Obtener un movil a parteir de su nro.
	 * @param nroDestinoSMS1
	 * @return
	 */
	public PersonMovil selectPersonMovil(String numero, Integer status) {
		return this.personMovilService.select(numero, status);
	}

	/**
	 * Obtener un movil a parteir de su id
	 * @param nroDestinoSMS1
	 * @return
	 */
	public PersonMovil selectPersonMovil(Integer id) {
		return this.personMovilService.select(id);
	}

//	public Movil selectMovilActiveFirst(String numero) {
//		return this.movilService.selectMovilActiveFirst(numero);
//	}

	public Movil selectMovil(String number, int status){
		return movilService.select(number, status);
	}

	/**
	 * Obtener una lista de moviles a parteir de su nro.
	 *
	 * @param nroDestino
	 * @return
	 */
	public List<Movil> selectMovils(String number, int status) {
		return movilService.selectList(number, status);
	}

//	public List<SMS> selectSMSList(Date fechaDesde, Date fechaHasta, Integer estado, Integer convocatoria,  String order,Integer pos, Integer limit) {
//		if ((convocatoria !=null) && (!(convocatoria==0)) ){
//			return smsService.selectListByDispatch(fechaDesde, fechaHasta, estado , convocatoria, order, pos,  limit);
//		}else{
//			return smsService.selectList(fechaDesde, fechaHasta, estado , order, pos,  limit);
//		}
//	}
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

 

}
