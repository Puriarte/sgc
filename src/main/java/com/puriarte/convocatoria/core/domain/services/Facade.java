package com.puriarte.convocatoria.core.domain.services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.puriarte.convocatoria.core.exceptions.MovilException;
import com.puriarte.convocatoria.core.exceptions.PersonException;
import com.puriarte.convocatoria.core.exceptions.SMSException;
import com.puriarte.convocatoria.persistence.Assignment;
import com.puriarte.convocatoria.persistence.AssignmentStatus;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.DispatchStatus;
import com.puriarte.convocatoria.persistence.DocumentType;
import com.puriarte.convocatoria.persistence.Job;
import com.puriarte.convocatoria.persistence.Movil;
import com.puriarte.convocatoria.persistence.MovilStatus;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.Place;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.convocatoria.persistence.SMSIn;
import com.puriarte.convocatoria.persistence.SMSOut;
import com.puriarte.convocatoria.persistence.SmsStatus;
import com.puriarte.convocatoria.persistence.User;
import com.puriarte.convocatoria.persistence.result.PersonMovilResult;
import com.puriarte.convocatoria.core.domain.Constants;

public class Facade {

	private static Facade INSTANCE = null;
	private UserService userService;
	private SMSService1 smsService;
	private PersonService personService;
	private MovilService movilService;
	private DispatchService1 dispatchService;
	private DocumentTypeService documentTypeService;
	private PersonCategoryService personCategoryService;
	private JobService jobService;
	private AssignmentStatusService assignmentStatusService;
	private PlaceService placeService;
	private PersonMovilService personMovilService;
	private BulkSMSService bulkSMSService;
	private SMSInService smsInService;
	private SMSOutService smsOutService;
	private DispatchStatusService dispatchStatusService;

	private Facade() {
		this.userService = UserService.getInstance();
		this.smsService = SMSService1.getInstance();
		this.personService = PersonService.getInstance();
		this.documentTypeService = DocumentTypeService.getInstance();
		this.movilService = MovilService.getInstance();
		this.dispatchService = DispatchService1.getInstance();
		this.personCategoryService = PersonCategoryService.getInstance();
		this.jobService = JobService.getInstance();
		this.personMovilService = PersonMovilService.getInstance();
		this.assignmentStatusService = AssignmentStatusService.getInstance();
		this.placeService = PlaceService.getInstance();
		this.bulkSMSService = BulkSMSService.getInstance();
		this.smsInService = SMSInService.getInstance();
		this.smsOutService = SMSOutService.getInstance();
		this.dispatchStatusService = DispatchStatusService.getInstance();
	}

	private static synchronized void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Facade();
			// Loader.loadInitialStructure(false);
		}
	}

	public static Facade getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}

	public synchronized void stopAll() {
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
	public int insertPerson(Person p) throws SQLException {
		return this.personService.insertPerson(p);
	}

	public Person insertPerson(String document) throws SQLException {
		return personService.getInstance().insertPerson(document);
	}

	/**
	 * Borra lgica de Persona
	 * 
	 * @param id
	 */
	public void removePerson(Person person) throws SQLException {
		this.personService.borrarPerson(person);
	}

	/*
	 * MOVIL
	 */

	/**
	 * Obtener un movil a parteir de su nro.
	 * 
	 * @param nroDestinoSMS1
	 * @return
	 */
	public PersonMovil selectPersonMovil(String numero, Integer status) {
		return this.personMovilService.select(numero, status);
	}

	/**
	 * Obtener un movil a parteir de su id
	 * 
	 * @param nroDestinoSMS1
	 * @return
	 */
	public PersonMovil selectPersonMovil(Integer id) {
		return this.personMovilService.select(id);
	}
	
	/**
	 * Obtener un movil a parteir de su id, Trae todas las categorias de la persona ya cargados
	 * 
	 * @param nroDestinoSMS1
	 * @return
	 */
	public PersonMovil selectPersonMovilWithCategories(Integer id) {
		return this.personMovilService.selectWithCategories(id);
	}
	
	

	// public Movil selectMovilActiveFirst(String numero) {
	// return this.movilService.selectMovilActiveFirst(numero);
	// }

	public Movil selectMovil(String number, int status) {
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

	public List<SMS> selectSMSList(Date fechaDesde, Date fechaHasta,
			Integer estado, boolean deleted, Integer convocatoria,
			String order, String orderDirection, Integer pos, Integer limit) {
		if ((convocatoria != null) && (!(convocatoria == 0))) {
			return smsService.selectListByDispatch(fechaDesde, fechaHasta,
					estado, deleted, convocatoria, order, orderDirection, pos,
					limit);
		} else {
			return smsService.selectList(fechaDesde, fechaHasta, estado,
					deleted, order, orderDirection, pos, limit);
		}
	}

	private List<SMS> SelectRelatedSMSList(PersonMovil movil,
			AssignmentStatus assignmentStatus, Date receivedDate, int pos,
			int limit) {
		return smsService.SelectRelatedSMSList(movil, assignmentStatus,
				receivedDate, pos, limit);
	}

	public List<Person> selectPersonList(List<String> priorities, int category,
			int estado, String order, Integer pos, Integer limit) {
		return personService.selectList(priorities, category, estado, order,
				pos, limit);
	}

	public List<PersonMovilResult> selectPersonMovilList(List<String> priorities,
			int category, int estado, String order, Integer pos, Integer limit) {

		
		List<PersonMovilResult> values = personMovilService.selectList(priorities, category, estado,
				order, pos, limit);
		
		for(PersonMovilResult result : values){
			result.setCategories(personCategoryService.selectByPersonList(result.getId()) );
		}
		
		return values;
	}

	public List<PersonMovil> selectPersonMovilObjectList(List<String> priorities,
			int category, int estado, String order, Integer pos, Integer limit) {
		return personMovilService.selectObjectList(priorities, category, estado,
				order, pos, limit);
	}
	
	public List<PersonMovil> selectPersonMovilObjectList(String order, Integer pos,
			Integer limit) {
		return this.selectPersonMovilObjectList(null, 0, 0, "", pos, limit);
	}
	

	// public boolean existSMS(String origen , Date date) {
	// return smsService.exist(origen, date);
	// }

	// / SMS ///
	/**
	 * Este mtodo verifica que (1. el nro Destino est asociado a una persona, 2-
	 * el nro est activo)
	 *
	 * Si no lo est se cambia al estado a SMS rechazado.
	 *
	 * @param nroDestino
	 * @param string
	 * @throws Exception
	 */
	public void insertSMSOutcome(PersonMovil movil, String texto, String uuid)
			throws Exception {
		smsService.getInstance().insert(null, null, null, movil, texto,
				Constants.SMS_ACTION_OUTCOME,
				selectSmsStatus(Constants.SMS_STATUS_PENDIENTE), new Date(),
				uuid);
	}

	/**
	 * Inserta un SMS entrante. Si tiene una citación asociada, siempre que la
	 * citación no sea anterior a la fecha dada lo asocia.
	 * 
	 * @param originator
	 * @param text
	 * @param date
	 * @param uuid
	 * @param positiveStart
	 * @param positiveContains
	 * @param negativeStart
	 * @param negativeContains
	 * @throws Exception
	 */
	public void insertSMSIncome(String originator, String text, Date date,
			String uuid, String[] positiveStart, String[] positiveContains,
			String[] negativeStart, String[] negativeContains) throws Exception {

		boolean enviado = false;
		String word = "NODEFINIDO";

		PersonMovil movil = Facade.getInstance().selectPersonMovil(originator,
				Constants.MOVIL_STATUS_ACTIVE);

		if (movil == null) {
			DocumentType dt = Facade.getInstance().selectDocumentType(
					Constants.PERSON_TYPE_CI);
			movil = Facade.getInstance().insertPersonMovil(originator, "", dt);
		}

		for (String auxWord : positiveStart) {
			if (text.toUpperCase().trim().startsWith(auxWord.trim()))
				word = "SI";
		}

		for (String auxWord : positiveContains) {
			if (text.toUpperCase().trim().contains(auxWord.trim()))
				word = "SI";
		}

		for (String auxWord : negativeStart) {
			if (text.toUpperCase().trim().startsWith(auxWord.trim()))
				word = "NO";
		}

		for (String auxWord : negativeContains) {
			if (text.toUpperCase().trim().contains(auxWord.trim()))
				word = "NO";
		}

		// if (text.toUpperCase().startsWith("SI")) word = "SI";
		// else if (text.toUpperCase().startsWith("NO")) word = "NO";
		// else word ="NODEFINIDO";
		// Me voy a fijar si corresponde a una respuesta de una convocatoria y
		// en tal caso si la respuesta es si o no
		// Tambien veo si el tiempo para responder esta superado o no
		if (movil != null) {
			// if(word.equals("SI") || word.equals("NO")) {
			List<SMS> smsList = Facade
					.getInstance()
					.SelectRelatedSMSList(
							movil,
							selectAssingmentStatus(Constants.ASSIGNMENT_STATUS_ASSIGNED),
							new Date(), 0, 1);
			// List<SMS> smsList =
			// Facade.getInstance().SelectRelatedSMSList(movil,
			// selectSmsStatus(Constants.SMS_STATUS_ENVIADO), 0, 1);
			if ((smsList != null) && (smsList.size() > 0)) {
				int i = 0;
				while ((smsList.size() > i) && (!enviado)) {
					SMS sms = smsList.get(i);
					if (sms.getAssignment() != null) {
						if (sms.getAssignment().getJob() != null) {
							if (sms.getAssignment().getJob().getDispatch() != null) {
								// if(sms.getAssignment().getJob().getDispatch().getScheduledDate().compareTo(new
								// Date())>0){
								Assignment assignment = sms.getAssignment();
								if ((assignment.getStatus().getId() != Constants.ASSIGNMENT_STATUS_CANCELED)
										&& (assignment.getStatus().getId() != Constants.ASSIGNMENT_STATUS_EXPIRED)) {
									if (word.equals("SI"))
										assignment
												.setStatus(Facade
														.getInstance()
														.selectAssingmentStatus(
																Constants.ASSIGNMENT_STATUS_ACCEPTED));
									else if (word.equals("NO"))
										assignment
												.setStatus(Facade
														.getInstance()
														.selectAssingmentStatus(
																Constants.ASSIGNMENT_STATUS_REGECTED));
								}
								smsService
										.getInstance()
										.insert(word,
												assignment,
												sms,
												movil,
												text,
												Constants.SMS_ACTION_INCOME,
												selectSmsStatus(Constants.SMS_STATUS_RECIBIDO),
												date, uuid);
								enviado = true;
								// }
							}
						}
					}
					i++;
				}
			}
			// }
		}

		if (!enviado)
			smsService.getInstance().insert(null, null, null, movil, text,
					Constants.SMS_ACTION_INCOME,
					selectSmsStatus(Constants.SMS_STATUS_RECIBIDO), date, uuid);
	}

	// SI este mensaje
	public void updateSMSAssignment(SMS sms, int dispatchId) {

		boolean enviado = false;
		PersonMovil movil = sms.getPersonMovil();
		if (movil != null) {
			List<SMS> smsList = Facade.getInstance().SelectRelatedSMSList(
					movil, dispatchId, 0, 1);
			if ((smsList != null) && (smsList.size() > 0)) {
				int i = 0;
				while ((smsList.size() > i) && (!enviado)) {
					SMS smsRef = smsList.get(i);
					smsService.getInstance().updateSMSAssignment(sms, smsRef);
					enviado = true;
					i++;
				}
			}

		}

	}

	public void removeSMSAssignment(SMS sms) {

		boolean enviado = false;
		smsService.getInstance().updateSMSAssignment(sms, null);
	}

	private List<SMS> SelectRelatedSMSList(PersonMovil movil, SmsStatus status,
			int dispatchId, int pos, int limit) {
		return smsService.SelectRelatedSMSList(movil, status, dispatchId, pos,
				limit);
	}

	private List<SMS> SelectRelatedSMSList(PersonMovil movil, int dispatchId,
			int pos, int limit) {
		return smsService.SelectRelatedSMSList(movil, dispatchId, pos, limit);
	}

	public int selectCountSMS(Date fechaInicio, Date fechaFin, int estado,
			int convocatoria, boolean deleted) {
		return smsService.selectCountAssignment(fechaInicio, fechaFin, estado,
				convocatoria, deleted);
	}

	public int selectCountAssignment(int personId, int status, Date fechaDesde,
			Date fechaHasta) {
		return smsService.selectCountAssignment(personId, status, fechaDesde,
				fechaHasta);
	}

	public int selectCountAssignment(Date fechaInicio, Date fechaFin,
			int estado, int convocatoria, boolean deleted) {
		return smsService.selectCountAssignment(fechaInicio, fechaFin, estado,
				convocatoria, deleted);
	}

	public int selectCountSentAssignment(int personId, Date fechaDesde,
			Date fechaHasta) {
		return smsService.selectCountSentAssignment(personId, fechaDesde,
				fechaHasta);
	}

	/**
	 * Registro el movil e inserto el SMS
	 *
	 * @param originator
	 * @param text
	 * @param uuid
	 * @throws Exception
	 */
	public void insertSMSIncomeAndRegisterMovil(String originator, String text,
			Date date, String uuid) throws Exception {
		DocumentType dt = Facade.getInstance().selectDocumentType(
				Constants.PERSON_TYPE_CI);
		if (dt == null)
			throw new PersonException(PersonException.DOCUMENT_TYPE_NOT_FOUND);
		else {
			if (!smsService.getInstance().existSms(uuid)) {
				PersonMovil personMovil = Facade.getInstance()
						.insertPersonMovil(originator,
								text.toUpperCase().replace("REGISTRO", ""), dt);
				if (personMovil == null) {
					smsService
							.getInstance()
							.insert("REGISTRO",
									null,
									null,
									personMovil,
									text,
									Constants.SMS_ACTION_INCOME,
									smsService
											.selectSmsStatus(Constants.SMS_STATUS_REGISTRATION_FAILED),
									date, uuid);
				} else {
					smsService
							.getInstance()
							.insert("REGISTRO",
									null,
									null,
									personMovil,
									text,
									Constants.SMS_ACTION_INCOME,
									smsService
											.selectSmsStatus(Constants.SMS_STATUS_RECIBIDO),
									date, uuid);
				}
			}
		}
	}

	/**
	 * Inserto el movil asociado a un documento de cedula de una persona
	 *
	 * @param nroDestino
	 * @param document
	 * @param documentType
	 * @return
	 * @throws SMSException
	 * @throws PersonException
	 * @throws SQLException
	 */
	public PersonMovil insertPersonMovil(String number, String document,
			DocumentType documentType) throws SMSException, PersonException,
			MovilException, SQLException {
		return movilService.getInstance()
				.insert(number, document, documentType);
	}

	public PersonMovil insertPersonMovil(Person person, String movilNumber)
			throws SMSException, PersonException, MovilException, SQLException {
		return movilService.getInstance().insert(person, movilNumber);
	}

	public int insertMovil(Movil m) throws SQLException {
		return this.movilService.insert(m);
	}

	public void updateSMS(SMS sms) {
		this.smsService.getInstance().update(sms);
	}

	/**
	 * Obtiene la lista de SMS pendientes de envio
	 *
	 * @param i
	 * @return
	 */
	public List<SMS> getPendingSMS() {
		return SMSService1.getInstance().selectList(
				Constants.SMS_STATUS_PENDIENTE);
	}

	public void updatePerson(Person person) {
		personService.getInstance().updatePerson(person);
	}

	Person getPerson(String document, int documentType) {
		return personService.getInstance().selectPerson(document, documentType);
	}

	/*
	 * MOVIL
	 */

	/**
	 * Obtener un movil a parteir de su nro.
	 * 
	 * @param nroDestinoSMS1
	 * @return
	 */
	public User selectUser(String name, String string) {
		return this.userService.select(name, string);
	}

	public DocumentType selectDocumentType(int id) {
		return this.documentTypeService.select(id);
	}

	public MovilStatus selectMovilStatus(int id) {
		return this.movilService.selectStatus(id);
	}

	public SmsStatus selectSmsStatus(int status) {
		return smsService.selectSmsStatus(status);
	}

	/* CONVOCATORIA */
	public List<Dispatch> selectDispatchList(int estado, String order,
			boolean ascending, Integer pos, Integer limit) {
		return dispatchService.selectList(estado, order, ascending, pos, limit);
	}

	public Collection<? extends Dispatch> selectSimpleDispatchList(int estado,
			String order, Integer pos, Integer limit) {
		return dispatchService.selectSimpleList(estado, order, pos, limit);
	}

	public List<Dispatch> selectSimpleDispatchByPersonMovilList(int estado,
			int idPersonMovil, String order, boolean ascending, Integer pos,
			Integer limit) {
		return dispatchService.selectSimpleList(estado, idPersonMovil, order,
				ascending, pos, limit);
	}

	public int insertDispatch(Dispatch dispatch) {
		return dispatchService.insert(dispatch);
	}

	public int insertDispatch(String message, String name, Place place,
			Date creationDate, Date scheduledDate, Date scheduledEndDate, String[] personIds,
			HashMap categories) {
		SmsStatus status = selectSmsStatus(Constants.SMS_STATUS_PENDIENTE);
		return dispatchService.insert(message, name, place, creationDate,
				scheduledDate, scheduledEndDate, personIds, categories, status);
	}

	public int updateDispatch(int id, String mensaje, String name, Place place,
			Date creationDate, Date scheduledDate, Integer dispatchStatus,
			HashMap personIds, HashMap categories, HashMap arStatusIds,
			HashMap arAssignmentIds, HashMap arForwardIds) {
		SmsStatus status = selectSmsStatus(Constants.SMS_STATUS_PENDIENTE);
		return dispatchService.update(id, mensaje, name, place, creationDate,
				scheduledDate, dispatchStatus, personIds, categories,
				arStatusIds, arAssignmentIds, arForwardIds, status);
	}

	public int insertBulkSMS(String message, String name, Date creationDate,
			Date scheduledDate, String[] personIds) {
		SmsStatus status = selectSmsStatus(Constants.SMS_STATUS_PENDIENTE);
		return bulkSMSService.insert(message, name, creationDate,
				scheduledDate, personIds, status);
	}

	public Person selectPerson(int id) {
		return personService.selectPerson(id);
	}

	public Dispatch selectDispatch(Integer id) {
		return dispatchService.selectDispatch(id);
	}

	// CATEGORY
	public List<PersonCategory> selectPersonCategoryList() {
		return this.personCategoryService.selectList();
	}

	public PersonCategory selectPersonCategory(int id) {
		return this.personCategoryService.select(id);
	}

	public int insertPersonCategory(PersonCategory cat) throws SQLException {
		return this.personCategoryService.insert(cat);
	}

	public void updatePersonCategory(PersonCategory cat) {
		this.personCategoryService.update(cat);
	}

	public void removePersonCategory(PersonCategory cat) {
		this.personCategoryService.delete(cat);
	}

	// DISPATCH

	public void updateDispatch(Dispatch dispatch) {
		this.dispatchService.update(dispatch);
	}

	public void removeDispatch(Dispatch item1) {
		this.dispatchService.delete(item1);

	}

	// JOB
	public List<Job> selectJobList(int i, String string, int j, int k) {
		return this.jobService.selectList();
	}

	public void insertJob(Job job) throws SQLException {
		this.jobService.insertJob(job);
	}

	public void updateJob(Job job) {
		this.jobService.update(job);

	}

	public void removeJob(Job job) {
		this.jobService.delete(job);

	}

	// PERSONMOVIL

	public void insertPersonMovil(PersonMovil pm) {
		this.personMovilService.insert(pm);
	}

	public void updatePersonMovil(PersonMovil person, String movilNumber) {
		this.personMovilService.updatePersonMovil(person, movilNumber);

	}

	// ASSIGNMENT STATUS
	public AssignmentStatus selectAssingmentStatus(int id) {
		return this.assignmentStatusService.select(id);
	}

	public List<AssignmentStatus> selectAssingmentStatusList() {
		return this.assignmentStatusService.selectList();
	}

	// DISPATCHSTATUS
	public List<DispatchStatus> selectDispatchStatusList() {
		return this.dispatchStatusService.selectList();
	}

	public DispatchStatus selectDispatchStatus(Integer dispatchStatusId) {
		return this.dispatchStatusService.select(dispatchStatusId);
	}

	// PLACES
	public List<Place> selectPlaceList() {
		return this.placeService.selectList();
	}

	public Place selectPlace(int id) {
		return this.placeService.select(id);
	}

	public int insertPlace(Place cat) throws SQLException {
		return this.placeService.insert(cat);
	}

	public void updatePlace(Place cat) {
		this.placeService.update(cat);
	}

	public void removePlace(Place cat) {
		this.placeService.delete(cat);
	}

	public void insertSMSIn(SMSIn sms) {
		this.smsInService.insert(sms);

	}

	public void insertSMSOut(SMSOut sms) {
		this.smsOutService.insert(sms);

	}

	public SMS selectSMS(int idRef) {
		return this.smsService.selectSMS(idRef);
	}

	public void deleteSMS(SMS sms) {

		this.smsService.getInstance().deleteSMS(sms);
	}

}
