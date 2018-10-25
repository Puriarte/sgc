package com.puriarte.convocatoria.core.domain.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.jfree.util.Log;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.exceptions.MovilException;
import com.puriarte.convocatoria.core.exceptions.PersonCategoryException;
import com.puriarte.convocatoria.core.exceptions.PersonException;
import com.puriarte.convocatoria.core.exceptions.PlaceException;
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
import com.puriarte.convocatoria.persistence.result.Report1;
import com.puriarte.gcp.web.presentation.actions.ActDeleteSMS;

public class Facade {

	private static Facade instance = null;
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
	
	private ReportService reportService;
	private Logger logger;
	
	
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
		this.reportService = ReportService.getInstance();
		this.logger=  Logger.getLogger(Facade.class.getName());
	}

	private static synchronized void createInstance() {
		if (instance == null) {
			instance = new Facade();
			// Loader.loadInitialStructure(false);
		}
	}

	public static Facade getInstance() {
		if (instance == null)
			createInstance();
		return instance;
	}

	public synchronized void stopAll() {
		try {

			UserService.getInstance().destroy();
			SMSService1.getInstance().destroy();
			PersonService.getInstance().destroy();
			DocumentTypeService.getInstance().destroy();
			MovilService.getInstance().destroy();
			DispatchService1.getInstance().destroy();
			PersonCategoryService.getInstance().destroy();
			JobService.getInstance().destroy();
			PersonMovilService.getInstance().destroy();
			AssignmentStatusService.getInstance().destroy();
			PlaceService.getInstance().destroy();
			BulkSMSService.getInstance().destroy();
			SMSInService.getInstance().destroy();
			SMSOutService.getInstance().destroy();
			DispatchStatusService.getInstance().destroy();
			ReportService.getInstance().destroy();	

			instance = null;
		} catch (Exception e) {
			this.userService = null;
			logger.error(e.getMessage());
		} catch (Throwable e) {
			this.userService = null;
			logger.error(e.getMessage());
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
//	public void removePerson(Person person) throws SQLException {
	//	this.personService.borrarPerson(person);
//	}


	/**
	 * @param idPerson 0 OK -1 NO ENCONTRADO -2 ERROR AL ACTUALIZAR DATOS
	 */
	public int deletePerson(int idPerson) {
		try {
			Person person = this.personService.getInstance().selectPerson(idPerson);
			if (person==null)
				return -1;
			else {
				this.personService.getInstance().deletePerson(person);
				return 0;
			}
		}catch(Exception e) {
			Log.error(e.getMessage());
			return -2;
		}
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

	public List<SMS> SelectRelatedSMSList(PersonMovil movil, String text, 
			AssignmentStatus assignmentStatus, Date receivedDate, int pos,
			int limit) {
		return smsService.SelectRelatedSMSList(movil, text, assignmentStatus,
				receivedDate, pos, limit);
	}

	public List<Person> selectPersonList(List<String> priorities, int category,
			int estado, String order, Integer pos, Integer limit) {
		return personService.selectList(priorities, category, estado, order,
				pos, limit);
	}

	public List<PersonMovilResult> selectPersonMovilList(List<String> priorities,
			List<String>  categories, Integer estado, String order, String asc, Integer pos, Integer limit) {

		
		List<PersonMovilResult> values = personMovilService.selectList(priorities, categories, estado,
				order, asc, pos, limit);
		
		for(PersonMovilResult result : values){
			result.setCategories(personCategoryService.selectByPersonList(result.getPersonId()) );
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
							text,
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

		if (!enviado){
			smsService.getInstance().insert(null, null, null, movil, text,
					Constants.SMS_ACTION_INCOME,
					selectSmsStatus(Constants.SMS_STATUS_RECIBIDO), date, uuid);
			enviarMail(movil, text);
		}
	}
	
	public void enviarMail(PersonMovil movil, String text){
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.ssl.checkserveridentity", true); 
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("convocatorias983","convocatorias");
					}
				});

			try {
				
				// Leo el template
				PropertiesConfiguration config = new PropertiesConfiguration(com.puriarte.gcp.web.Constantes.PATHAPPCONFIG);

				String path= config.getString("data.folder") + "templates/email_sms.html";
				String[] mailTo = config.getString("mail.nodispatch.to").split(";");

				InternetAddress[] recipientsTo = new InternetAddress[mailTo.length];
				//= {new InternetAddress("joaquin@elenatejeira.com"), new InternetAddress("valonso@elenatejeira.com")}; 
				for(int i=0;i<mailTo.length;i++){
					recipientsTo[i]=new InternetAddress(mailTo[i]);
				}
				
				byte[] encoded = Files.readAllBytes(Paths.get(path));
				String mensajeHtml = new String(encoded, java.nio.charset.Charset.defaultCharset());

				String persona = "";
				String nroMovil = "";
				if ((movil!=null)&&(movil.getPerson()!=null))
					persona=movil.getPerson().getName();

				if ((movil!=null)&&(movil.getMovil()!=null))
					nroMovil=movil.getMovil().getNumber();
				String[] values = {nroMovil , persona, text};
			
						
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("convocatorias983@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, recipientsTo);

				message.setRecipients(Message.RecipientType.BCC,
						InternetAddress.parse("puriarte@gmail.com"));

				message.setSubject("MENSAJE RECIBIDO - GESTION DE CONVOCATORIAS");
				message.setContent(MessageFormat.format(mensajeHtml, values), "text/html; charset=utf-8");
				Transport.send(message);

			} catch (MessagingException | IOException | ConfigurationException e) {
				throw new RuntimeException(e);
			}
		
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
		return SMSService1.getInstance().selectListByStatusAndDispatchDate(
				Constants.SMS_STATUS_PENDIENTE);
	}

	
	/**
	 * Busca los mensajes que se recibieron despues de una hora dada para que
	 * se puedan mostrar al usuario en tiempo real. Los mensajes retornados se
	 * marcan como vistos para que no se muestren más de una vez ( estro podría pasar
	 * si por ejemlo se marca un mensaje con fecha de creado muy posterior a la fecha
	 * del servidor web)
	 */
	public List<SMS> getIncomingSMS(Date fromDate) {
		List<SMS> listaSms = SMSService1.getInstance().selectListByActionAndDate(
				Constants.SMS_ACTION_INCOME, fromDate);
		
		SMSService1.getInstance().updateSMSListAsSeen(listaSms);
		
		return listaSms ;
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
			String order, boolean ascending, Integer pos, Integer limit) {
		return dispatchService.selectSimpleList(estado, order, pos, limit);
	}

	public Collection<? extends Dispatch> selectSimpleListWithAssignments(int estado,
			String order, boolean ascending, Integer pos, Integer limit) {
		return dispatchService.selectSimpleListWithAssignments(estado, order, pos, limit);
	}

	
	
	public List<Dispatch> selectSimpleDispatchByPersonMovilList(int estado,
			int idPersonMovil, String order, boolean ascending, Integer pos,
			Integer limit) {
		return dispatchService.selectSimpleList(estado, idPersonMovil, order,
				ascending, pos, limit);
	}

	/*public int insertDispatch(Dispatch dispatch) {
		return dispatchService.insert(dispatch);
	}*/

	public int insertDispatch(String message, String name, String code, Place place,
			Date creationDate, Date scheduledDate, Date scheduledEndDate, String strAttribute3, String[] personMovilIds,
			HashMap categories) throws ConfigurationException {
		SmsStatus status = selectSmsStatus(Constants.SMS_STATUS_EN_ESPERA_CIERRE_DISPATCH); //.SMS_STATUS_PENDIENTE);
		return dispatchService.insert(message, name, code, place, creationDate,
				scheduledDate, scheduledEndDate, strAttribute3, personMovilIds, categories, status);
	}

	public void addToDispatch(Dispatch dispatch, String message, Date creationDate,  String[] personMovilIds, HashMap categories) throws ConfigurationException {
		dispatchService.addToDispatch(dispatch, message, creationDate,  personMovilIds, categories);
	}

	
	public int updateDispatch(int id, String mensaje, String name, Place place,
			Date creationDate, Date scheduledDate, Integer dispatchStatus,
			HashMap personIds, HashMap categories, HashMap arStatusIds,
			HashMap arAssignmentIds, HashMap arForwardIds) throws ConfigurationException {
		return dispatchService.update(id, mensaje, name, place, creationDate,
				scheduledDate, dispatchStatus, personIds, categories,
				arStatusIds, arAssignmentIds, arForwardIds);
	}
	
	public int sendDispatchSMS(int id, String[] idsPerson, HashMap arPersonCategory){
		if (idsPerson==null)
			return dispatchService.enviarSmsStatus(id);
		else
			return dispatchService.enviarSmsStatus(id, idsPerson,arPersonCategory);
			
	}
	
	public int sendDispatchSMSByAssignment(int id, String[] idsPerson, HashMap arPersonCategory){
		if (idsPerson==null)
			return dispatchService.enviarSmsStatus(id);
		else
			return dispatchService.sendDispatchSMSByAssignment(id, idsPerson,arPersonCategory);
			
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
	public List<PersonCategory> selectPersonCategoryList(boolean includeDeleted) {
		return this.personCategoryService.selectList(includeDeleted);
	}

	public PersonCategory selectPersonCategory(int id) {
		return this.personCategoryService.select(id);
	}

	public int insertPersonCategory(PersonCategory cat) throws SQLException, PersonCategoryException, Exception {
		PersonCategory pc =null;
		try{
			pc = this.personCategoryService.selectByName(cat.getName());
		}catch(NoResultException nrex){
			pc=null;
		}catch(NonUniqueResultException nex ){
			// Si hay mas de una categoría con este nombre aviso 
			throw new PersonCategoryException(PersonCategoryException.DUPLICATED_MORE_THAN_ONCE);
		}
		
		if ((cat==null)  ||(cat.getName()==null))
			throw new PersonCategoryException(PersonCategoryException.NAME_REQUIRED);
		
		if ((pc!=null) && (pc.getName()!=null) && (pc.getName().toLowerCase().trim().equals(cat.getName().toLowerCase().trim())))
			throw new PersonCategoryException(PersonCategoryException.DUPLICATED);
		
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

	public void updatePerson(Person person, String movilNumber) {
		this.personMovilService.updatePerson(person, movilNumber);

	}
	
	public void celanPersonCategories(int idperson) {
		this.personMovilService.celanPersonCategories(idperson);

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

	public String selectNextCode() {
		return this.dispatchService.selectNextCode();
	}

	
	// PLACES
	public List<Place> selectPlaceList(boolean includeDeleted) {
		return this.placeService.selectList(includeDeleted);
	}

	public Place selectPlace(int id) {
		return this.placeService.select(id);
	}

	public int insertPlace(Place cat) throws SQLException, PlaceException {
		return this.placeService.insert(cat);
	}

	public void updatePlace(Place cat) throws SQLException, PlaceException {
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

	public Assignment getAssignment(long idAssignment) {
		return this.dispatchService.getInstance().getAssignment(idAssignment);
	}

	// *-----------REPORTES -----------------**//
	public List<Report1> selectReport1(Date from, Date to, String orderBy) {
		return this.reportService.getInstance().report1(from, to, orderBy);
	}


}
