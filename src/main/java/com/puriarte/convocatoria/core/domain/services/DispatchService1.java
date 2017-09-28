package com.puriarte.convocatoria.core.domain.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.validator.EmailValidator;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.persistence.Assignment;
import com.puriarte.convocatoria.persistence.AssignmentStatus;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.DispatchStatus;
import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.Job;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.Place;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.convocatoria.persistence.SmsStatus;

public class DispatchService1 {
	static private DispatchService1 INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new DispatchService1();
	}

	public static DispatchService1 getInstance(){
		if(INSTANCE == null) createInstance();
		return INSTANCE;
	}

	public synchronized void destroy(){
		INSTANCE = null;
	}

	protected EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}


	public void crear(SMS sms){
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(sms);
		em.getTransaction().commit();
	}


	public List<Dispatch> selectList(int estado, String order, boolean ascending, Integer pos, Integer limit) {
		final EntityManager em = getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery q = cb.createQuery(Dispatch.class);
		Root<Dispatch> dispatch = q.from(Dispatch.class);
		Join status = dispatch.join("dispatchStatus", JoinType.LEFT);
		Join place= dispatch.join("place", JoinType.LEFT);

		//Armo el criterio en que se quiere ordenar		
		if (ascending==true){
			if(order.equals("place.name"))		q.orderBy(cb.asc(place.get("name")));
			if(order.equals("scheduledDate"))	q.orderBy(cb.asc(dispatch.get("scheduledDate")));
			if(order.equals("name"))			q.orderBy(cb.asc(dispatch.get("name")));
			if(order.equals("dispatchStatus.name"))	q.orderBy(cb.asc(status.get("name")));
		}else{
			if(order.equals("place.name"))		q.orderBy(cb.desc(place.get("name")));
			if(order.equals("scheduledDate"))	q.orderBy(cb.desc(dispatch.get("scheduledDate")));
			if(order.equals("name"))			q.orderBy(cb.desc(dispatch.get("name")));
			if(order.equals("dispatchStatus.name"))	q.orderBy(cb.desc(status.get("name")));
		}
		
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (estado>0) predicateList.add(cb.equal(status.get("id"), estado));
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		q.where(predicates);
		
		Query query = em.createQuery(q);
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setHint("eclipselink.refresh", "true");
		query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");
		if((pos!=null) && (limit!=null)) query.setFirstResult(pos).setMaxResults(limit);
		
		if((pos!=null) && (limit!=null)) query.setFirstResult(pos).setMaxResults(limit);
		List<Dispatch> a = query.getResultList();
	
		return a;
	}


	public List<Dispatch> selectSimpleList(int estado,
			String order, Integer pos, Integer limit) {
		final EntityManager em = getEntityManager();

		if (order==null) order ="";

		Query query = em.createNamedQuery("Dispatch.SelectSimpleDispatchList")
				.setParameter("estado", estado);

		if((pos!=null) && (limit!=null)) query.setFirstResult(pos).setMaxResults(limit);

		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setHint("eclipselink.refresh", "true");
		query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

		List<Object[]> a = query.getResultList();
		List<Dispatch> dispatchList = new ArrayList<Dispatch>(); 	
		for (Object[] aux :a){
			Dispatch aux1 = new Dispatch();
			aux1.setId(Integer.parseInt(aux[0].toString()));
			aux1.setName(aux[1].toString());
			aux1.setCode(aux[2].toString());
			dispatchList.add(aux1);
		}
		return dispatchList;
	}
	
	
	public List<Dispatch> selectSimpleList(int estado, int idPersonMovil,
			String order, boolean ascending, Integer pos, Integer limit) {
		final EntityManager em = getEntityManager();

		if (order==null) order ="";

		// select * from dispatch, person where 
		Query query = em.createNamedQuery("Dispatch.SelectSimpleDispatchList")
				.setParameter("idPersonMovil", idPersonMovil)
				.setParameter("estado", estado);

		if((pos!=null) && (limit!=null)) query.setFirstResult(pos).setMaxResults(limit);

		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setHint("eclipselink.refresh", "true");
		query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

		List<Object[]> a = query.getResultList();
		List<Dispatch> dispatchList = new ArrayList<Dispatch>(); 	
		for (Object[] aux :a){
			Dispatch aux1 = new Dispatch();
			aux1.setId(Integer.parseInt(aux[0].toString()));
			aux1.setName(aux[1].toString());
			aux1.setCode(aux[2].toString());
			dispatchList.add(aux1);
		}
		return dispatchList;
	}
	
	public Dispatch selectDispatch(Integer id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectDispatch")
					.setParameter("id", id);

			Dispatch a = (Dispatch) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}
	}



	public int insert(Dispatch dispatch ) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(dispatch);
		em.getTransaction().commit();

		return dispatch.getId();
	}

	/**
	 * Al crear una convocatoria se debe crear para cada persona incluida un puesto en la convocatoria y una asignaciï¿½n
	 * a ese puesto. Luego si la persona no se va a presentar se puede asignar ese puesto a otro.
	 *
	 * @param message
	 * @param name
	 * @param creationDate
	 * @param scheduledDate
	 * @param personIds			Lista de identificadores de PersonMovil
	 * @param categories
	 * @param status
	 * @return
	 */
	public int insert(String message, String name, String code, Place place, Date creationDate, Date scheduledDate, Date scheduledEndDate,
			String[] personMovilIds, HashMap categories, SmsStatus status) {

		AssignmentStatus assignmentstatus = Facade.getInstance().selectAssingmentStatus(Constants.ASSIGNMENT_STATUS_PENDING);
		DispatchStatus dispatchStatus = Facade.getInstance().selectDispatchStatus(Constants.DISPATCH_STATUS_ACTIVE);

		// Inicializo la convocatoria como ACTIVA
		Dispatch dispatch = new Dispatch();
		dispatch.setName(name);
		dispatch.setCode(code);
		dispatch.setScheduledDate(scheduledDate);
		dispatch.setScheduledEndDate(scheduledEndDate);
		dispatch.setPlace(place);
		dispatch.setDispatchStatus(dispatchStatus);
		
		for(String idPersonMovil: personMovilIds){
			PersonMovil person = Facade.getInstance().selectPersonMovil(Integer.parseInt(idPersonMovil));
			if (person!=null){
				PersonCategory category=new PersonCategory();
				// Obtengo la categoría de la persona para la convocatoria
				try{
					if(categories.containsKey(person.getId())){
						int categoryId = (int) categories.get(person.getId());
						category = Facade.getInstance().selectPersonCategory(categoryId);
					}
				}catch(Exception e){}

				if ((person.getMovil()!=null)){
					// Creo el SMS
					SMS sms = new SMS();
					sms.setMensaje(message.trim());
 					if (category !=null)
						sms.setMensaje(sms.getMensaje().trim() + " " + category.getName().trim());

					sms.setPersonMovil(person);
					sms.setStatus(status);
					sms.setAction(Constants.SMS_ACTION_OUTCOME);
					sms.setCreationDate(creationDate);

					// Agrego el SMS a una nueva asignaciï¿½n de trabajo
					Assignment assignment = new Assignment();
					assignment.setStatus(assignmentstatus);
					assignment.setPersonMovil(person);
					assignment.addSms (sms);
					assignment.setAssignmentDate(creationDate);

					// Creo el puesto de trabajo
					Job job = new Job();
					job.setCategory(category);
					job.addAssignment(assignment);

					dispatch.addJob(job);
				}
			}
		}
		return this.insert(dispatch);
	}

	public void addToDispatch(Dispatch dispatch, String message, Date creationDate, String[] personMovilIds, HashMap categories) {

		SmsStatus status = Facade.getInstance().selectSmsStatus(Constants.SMS_STATUS_EN_ESPERA_CIERRE_DISPATCH); //.SMS_STATUS_PENDIENTE);
		AssignmentStatus assignmentstatus = Facade.getInstance().selectAssingmentStatus(Constants.ASSIGNMENT_STATUS_PENDING);
		DispatchStatus dispatchStatus = Facade.getInstance().selectDispatchStatus(Constants.DISPATCH_STATUS_ACTIVE);
	
		for(String idstPersonMovil: personMovilIds){
			int idPersonMovil=Integer.parseInt(idstPersonMovil);
			PersonMovil person = Facade.getInstance().selectPersonMovil(idPersonMovil);
			if (person!=null){
				PersonCategory category=new PersonCategory();
				// Obtengo la categoría de la persona para la convocatoria
				try{
					if(categories.containsKey(person.getId())){
						int categoryId = (int) categories.get(person.getId());
						category = Facade.getInstance().selectPersonCategory(categoryId);
					}
				}catch(Exception e){}

				if ((person.getMovil()!=null)){
					
					if (!dispatch.containsAssignment(idPersonMovil, category.getId())){
						// Creo el SMS
						SMS sms = new SMS();
						sms.setMensaje(message.trim());
	 					if (category !=null)
							sms.setMensaje(sms.getMensaje().trim() + " " + category.getName().trim());
	
						sms.setPersonMovil(person);
						sms.setStatus(status);
						sms.setAction(Constants.SMS_ACTION_OUTCOME);
						sms.setCreationDate(creationDate);
	
						// Agrego el SMS a una nueva asignaciï¿½n de trabajo
						Assignment assignment = new Assignment();
						assignment.setStatus(assignmentstatus);
						assignment.setPersonMovil(person);
						assignment.addSms (sms);
						assignment.setAssignmentDate(creationDate);
	
						// Creo el puesto de trabajo
						Job job = new Job();
						job.setCategory(category);
						job.addAssignment(assignment);
	
						dispatch.addJob(job);
					}
				}
			}
		}
		this.update(dispatch);
	}

	
	public int update(int id, String message, String name, Place place,
			Date creationDate, Date scheduledDate, Integer dispatchStatusId, HashMap personIds,
			HashMap categories, HashMap arStatus,HashMap arAssignmentIds, HashMap arForwardIds) {

		SmsStatus status = Facade.getInstance().selectSmsStatus(Constants.SMS_STATUS_EN_ESPERA_CIERRE_DISPATCH); 

		AssignmentStatus assignmentstatuspending = Facade.getInstance().selectAssingmentStatus(Constants.ASSIGNMENT_STATUS_PENDING);
		DispatchStatus dispatchStatus = Facade.getInstance().selectDispatchStatus( dispatchStatusId);
		
		// Obtengo la convocatoria
		// Recorro para cada asignación 
		Dispatch dispatch2 = this.selectDispatch(id);
		dispatch2.setDispatchStatus(dispatchStatus);
		
		// Itero en las asignaciones de la convocatoria
		Iterator it = arAssignmentIds.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Entry) it.next();
			int key = (int) entry.getKey();
			long idAssignment = new Long((Integer) entry.getValue());

			int categoryId = 0;
			int idStatus = (int) arStatus.get(key); 
//			boolean forward = false;
	//		if ((arForwardIds.get(key)!=null) && arForwardIds.get(key).equals("on")) 
	//			forward = true;
			
			PersonCategory category=new PersonCategory();
			try{
				if(categories.containsKey(key)){
					categoryId = (int) categories.get(key);
					category = Facade.getInstance().selectPersonCategory(categoryId);
				}
			}catch(Exception e){
			}
		
			if (idAssignment==0){	// CREO UNA NUEVA ASIGNACION
				int idPerson = (int) personIds.get(key);
				PersonMovil person = Facade.getInstance().selectPersonMovil(idPerson);
				if (person!=null){

					if ((person.getMovil()!=null)){
						// Creo el SMS
						SMS sms = new SMS();
						sms.setMensaje(message.trim());
	 					if (category !=null)
							sms.setMensaje(sms.getMensaje().trim() + " " + category.getName().trim());

						sms.setPersonMovil(person);
						sms.setStatus(status);
						sms.setAction(Constants.SMS_ACTION_OUTCOME);
						sms.setCreationDate(creationDate);

						// Agrego el SMS a una nueva asignaciï¿½n de trabajo
						Assignment assignment = new Assignment();
						assignment.setStatus(assignmentstatuspending);
						assignment.setPersonMovil(person);
						assignment.addSms (sms);
						assignment.setAssignmentDate(creationDate);

						// Creo el puesto de trabajo
						Job job = new Job();
						job.setCategory(category);
						job.addAssignment(assignment);

						dispatch2.addJob(job);
						
						this.update(dispatch2);
					}
				}
			}else{				
				Assignment assignment1= dispatch2.getAssignment(idAssignment);
				boolean haveToUpdate = false;

				if (!(assignment1.getJob().getCategory().getId()==categoryId)){
					// Cambio el dato sobre el puesto y mando un SMS avisando
					PersonCategory pc = Facade.getInstance().selectPersonCategory(categoryId);
					assignment1.getJob().setCategory(pc);
				
					// Creo el SMS
					SMS sms = new SMS();
					sms.setMensaje(message.trim());
					sms.setMensaje(sms.getMensaje().trim() + " CAMBIO DE CATEGORIA " + pc.getName().trim());
	
					sms.setPersonMovil(assignment1.getPersonMovil());
					sms.setStatus(status);
					sms.setAction(Constants.SMS_ACTION_OUTCOME);
					sms.setCreationDate(creationDate);
					
					assignment1.setStatus(assignmentstatuspending);
					List smsList = assignment1.getSmsList();
					smsList.add(sms);
					assignment1.setSmsList(smsList);
				}else{
					// Si hay que reenviar el mensaje
/*					if (forward){
						PersonCategory pc = Facade.getInstance().selectPersonCategory(categoryId);

						// Me fijo si hay algun mensaje en espera 
						
						// Creo el SMS
						SMS sms = new SMS();
						sms.setMensaje(message.trim() + " " + pc.getName().trim());
		
						sms.setPersonMovil(assignment1.getPersonMovil());
						sms.setStatus(status);
						sms.setAction(Constants.SMS_ACTION_OUTCOME);
						sms.setCreationDate(creationDate);

						List smsList = assignment1.getSmsList();
						smsList.add(sms);
						assignment1.setSmsList(smsList);
						haveToUpdate  =true;
					}*/
					if  (!(assignment1.getStatus().getId()==idStatus)){
						AssignmentStatus newStatus = Facade.getInstance().selectAssingmentStatus(idStatus);
						assignment1.setStatus(newStatus);
						haveToUpdate  =true;
					}
				}
				if (haveToUpdate) this.update(dispatch2);
			}
		}
		this.update(dispatch2);
		
		return 0;
	}

	public int changeSmsStatus(int id, SmsStatus statusEnEspera,
			SmsStatus statusPendiente) {

		final EntityManager em = getEntityManager();
		// Me quedo con el ultimo mensaje generado para una persona
		int i=0;
		em.getTransaction().begin();
		Dispatch dispatch2 = this.selectDispatch(id);
		for (Job jpb :dispatch2.getJobList()){
			for (Assignment as :jpb.getAssignmentList()){
				for (SMS sms :as.getSmsList()){
					if (sms.getStatus().getId()==statusEnEspera.getId()){
						sms.setStatus(statusPendiente);
						em.persist(sms);
						i=i+1;
					}
				}
			}
		}
		em.getTransaction().commit();
		return i;
	}
	
	
	public void update(Dispatch dispatch) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(dispatch);
		em.getTransaction().commit();


	}

	public void delete(Dispatch item1) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.remove(item1);
		em.getTransaction().commit();
	}

	public String selectNextCode() {
		final EntityManager em = getEntityManager();
		String code = "";
		try{
			int i1=1;
			int i=0;
			while ( (i<100)&&(i1>0) ) {
				code = RandomStringUtils.randomNumeric(4);
			
				i1 = em.createNativeQuery("select count(*) from dispatch where code = '" + code + "'" ).getFirstResult();
				i=i+1;
			}
			return code;
		}catch(Exception e){
			return null;
		}
		
	}
	
	public int sendDispatchSMSByAssignment(int id, String[] arAssignmentIds,  HashMap arPersonCategory) {
		SmsStatus statusEnEspera =  Facade.getInstance().selectSmsStatus(Constants.SMS_STATUS_EN_ESPERA_CIERRE_DISPATCH); 
		SmsStatus statusPendiente =  Facade.getInstance().selectSmsStatus(Constants.SMS_STATUS_PENDIENTE); 
		AssignmentStatus assignmentstatusPending = Facade.getInstance().selectAssingmentStatus(Constants.ASSIGNMENT_STATUS_PENDING);
		AssignmentStatus assignmentstatus = Facade.getInstance().selectAssingmentStatus(Constants.ASSIGNMENT_STATUS_ASSIGNED);

		final EntityManager em = getEntityManager();
		int i=0;
		try{
			Dispatch dispatch = this.selectDispatch(id);
			// Me quedo con el ultimo mensaje generado para una persona
	
			em.getTransaction().begin();
			
			// Obtengo todas las asignaciones del dispatch
			for (Job jpb :dispatch.getJobList()){
				for (Assignment as :jpb.getAssignmentList()){
					// Me fijo si el assgnment corresponde a una persona de la lista
					if (Arrays.asList(arAssignmentIds).indexOf(String.valueOf(as.getId()))>=0){
						// Me fijo que la categoria corresponda a la categoria indicada para la persona en el assignment
						if(arPersonCategory.containsKey(String.valueOf(as.getId()))){
							if (((int)arPersonCategory.get(as.getPersonMovil().getId()))==jpb.getCategory().getId()){
								if (as.getStatus().equals(assignmentstatusPending))
									as.setStatus(assignmentstatus);
								em.persist(as);
								for (SMS sms :as.getSmsList()){
									if (sms.getStatus().getId()==statusEnEspera.getId()){
										sms.setStatus(statusPendiente);
										em.persist(sms);
										i=i+1;
									}
								}
							}
						}
					}
				}
			}
			
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
		}
		
		return i;
	}

	
	public int enviarSmsStatus(int id, String[] arPersonIds,  HashMap arPersonCategory) {
		SmsStatus statusEnEspera =  Facade.getInstance().selectSmsStatus(Constants.SMS_STATUS_EN_ESPERA_CIERRE_DISPATCH); 
		SmsStatus statusPendiente =  Facade.getInstance().selectSmsStatus(Constants.SMS_STATUS_PENDIENTE); 
		AssignmentStatus assignmentstatusPending = Facade.getInstance().selectAssingmentStatus(Constants.ASSIGNMENT_STATUS_PENDING);
		AssignmentStatus assignmentstatus = Facade.getInstance().selectAssingmentStatus(Constants.ASSIGNMENT_STATUS_ASSIGNED);

		final EntityManager em = getEntityManager();
		int i=0;
		try{
			Dispatch dispatch = this.selectDispatch(id);
			// Me quedo con el ultimo mensaje generado para una persona
	
			em.getTransaction().begin();
			
			// Obtengo todas las asignaciones del dispatch
			for (Job jpb :dispatch.getJobList()){
				for (Assignment as :jpb.getAssignmentList()){
					// Me fijo si el assgnment corresponde a una persona de la lista
					if (Arrays.asList(arPersonIds).indexOf(Integer.toString(as.getPersonMovil().getId()))>=0){
						// Me fijo que la categoria corresponda a la categoria indicada para la persona en el assignment
						if(arPersonCategory.containsKey(as.getPersonMovil().getId())){
							if (((int)arPersonCategory.get(as.getPersonMovil().getId()))==jpb.getCategory().getId()){
								if (as.getStatus().equals(assignmentstatusPending))
									as.setStatus(assignmentstatus);
								em.persist(as);
								for (SMS sms :as.getSmsList()){
									if (sms.getStatus().getId()==statusEnEspera.getId()){
										sms.setStatus(statusPendiente);
										em.persist(sms);
										i=i+1;
									}
								}
							}
						}
					}
				}
			}
			
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
		}
		
		return i;
	}
	
	public int enviarSmsStatus(int id) {
		SmsStatus statusEnEspera =  Facade.getInstance().selectSmsStatus(Constants.SMS_STATUS_EN_ESPERA_CIERRE_DISPATCH); 
		SmsStatus statusPendiente =  Facade.getInstance().selectSmsStatus(Constants.SMS_STATUS_PENDIENTE); 
		AssignmentStatus assignmentstatusPending = Facade.getInstance().selectAssingmentStatus(Constants.ASSIGNMENT_STATUS_PENDING);
		AssignmentStatus assignmentstatus = Facade.getInstance().selectAssingmentStatus(Constants.ASSIGNMENT_STATUS_ASSIGNED);

		final EntityManager em = getEntityManager();
		int i=0;
		try{
			Dispatch dispatch = this.selectDispatch(id);
			// Me quedo con el ultimo mensaje generado para una persona
	
			em.getTransaction().begin();
			// Obtengo todas las asignaciones del dispatch
			for (Job jpb :dispatch.getJobList()){
				for (Assignment as :jpb.getAssignmentList()){
					if (as.getStatus().equals(assignmentstatusPending))
						as.setStatus(assignmentstatus);
					em.persist(as);
					for (SMS sms :as.getSmsList()){
						if (sms.getStatus().getId()==statusEnEspera.getId()){
							sms.setStatus(statusPendiente);
							em.persist(sms);
							i=i+1;
						}
					}
				}
			}
			
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
		}
		
		return i;
	}

	public Assignment getAssignment(long assignmentid) {
		final EntityManager em = getEntityManager();
		try{
			Query query = em.createNamedQuery("Assignment.SelectAssignment")
					.setParameter("assignmentid", assignmentid);

			Assignment a = (Assignment) query.getSingleResult();
			
			return a;
		}catch(Exception e){
			return null;
		}
	}


	
}
