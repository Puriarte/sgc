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
	

//	private List<SMS> SelectRelatedSMSList(PersonMovil movil, SmsStatus status, int pos, int limit) {
//		return smsService.SelectRelatedSMSList(movil, status , pos,  limit);
//	}


	public List<Person> selectPersonList(List<String> priorities, int category ,int estado,  String order,Integer pos, Integer limit) {
		return personService.selectList(priorities, category , estado , order, pos,  limit);
	}

	public List<PersonMovil> selectPersonMovilList(List<String> priorities, int category ,int estado,  String order, Integer pos, Integer limit) {
		return personMovilService.selectList(priorities, category , estado , order, pos,  limit);
	}


	public List<PersonMovil> selectPersonMovilList( String order, Integer pos, Integer limit) {
		return this.selectPersonMovilList(null, 0,0,  "", pos, limit);
	}


//	public boolean existSMS(String origen , Date date) {
//		return smsService.exist(origen, date);
//	}
	

	/// SMS ///
	/**
	 * Este método verifica que
	 * (1. el nro Destino esté asociado a una persona,
	 *  2- el nro esté activo)
	 *
	 *  Si no lo está se cambia al estado a SMS rechazado.
	 *
	 * @param nroDestino
	 * @param string
	 * @throws Exception
	 */
	public void insertSMSOutcome(PersonMovil movil, String texto) throws Exception {
//		smsService.getInstance().insert(null,null, null, movil,texto, Constants.SMS_ACTION_OUTCOME, selectSmsStatus(Constants.SMS_STATUS_PENDIENTE), new Date());
	}

	public void insertSMSIncome(String originator, String text, Date date) throws Exception {
//		PersonMovil movil = Facade.getInstance().selectPersonMovil(originator,Constants.MOVIL_STATUS_ACTIVE );
//		if (movil ==null){
//			DocumentType dt = Facade.getInstance().selectDocumentType(Constants.PERSON_TYPE_CI);
//			movil = Facade.getInstance().insertPersonMovil(originator, "" ,dt);
//		}
//
//		String order="";
//		String word="";
//
//		// Me voy a fijar si corresponde a una respuesta de una convocatoria y en tal caso si la respuesta es si o no
//		// También veré si el tiempo para responder está superado o no
//		List<SMS> smsList = Facade.getInstance().SelectRelatedSMSList(movil, selectSmsStatus(Constants.SMS_STATUS_ENVIADO), 0, 1);
//		if ((smsList!=null) && (smsList.size()>0)){
//			SMS sms = smsList.get(0);
//			if (sms.getAssignment()!=null){
//				if (sms.getAssignment().getJob()!=null){
//					if(sms.getAssignment().getJob().getDispatch()!=null){
//						Assignment assignment = sms.getAssignment();
//						if (text.toUpperCase().startsWith("SI"))
//							word = "SI";
//						else if (text.toUpperCase().startsWith("NO"))
//							word = "NO";
//
//
//		/*				if (text.toUpperCase().startsWith("SI"))
//							smsService.getInstance().insert(idDispatch, movil, text, Constants.SMS_ACTION_INCOME,selectSmsStatus(Constants.SMS_STATUS_RECIBIDO), date);
//						else
//			*/				smsService.getInstance().insert(word, assignment, sms, movil, text, Constants.SMS_ACTION_INCOME,selectSmsStatus(Constants.SMS_STATUS_RECIBIDO), date);
//
//					}
//				}
//
//			}else{
//				smsService.getInstance().insert(null,null,null, movil, text, Constants.SMS_ACTION_INCOME,selectSmsStatus(Constants.SMS_STATUS_RECIBIDO), date);
//			}
//		}else{
//			smsService.getInstance().insert(null,null,null, movil, text, Constants.SMS_ACTION_INCOME,selectSmsStatus(Constants.SMS_STATUS_RECIBIDO), date);
//		}
	}

//	public int selectCountSMS(int personId, String word,Date  fechaDesde, Date fechaHasta){
//		return smsService.selectCountSMS(personId, word, fechaDesde, fechaHasta);
//
//	}
//
//	public int selectCountSentSMS(int personId, Date fechaDesde,Date  fechaHasta){
//		return smsService.selectCountSentSMS(personId, fechaDesde, fechaHasta);
//	}
//
//	public int selectCountExpiredSMS(int personId, Date fechaDesde,Date  fechaHasta){
//		return smsService.selectCountExpiredSMS(personId, fechaDesde, fechaHasta);
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
