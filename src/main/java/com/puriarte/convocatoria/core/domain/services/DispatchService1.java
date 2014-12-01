package com.puriarte.convocatoria.core.domain.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.persistence.Assignment;
import com.puriarte.convocatoria.persistence.AssignmentStatus;
import com.puriarte.convocatoria.persistence.Dispatch;
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


	public List<Dispatch> selectList(int estado, String order, Integer pos, Integer limit) {
		final EntityManager em = getEntityManager();

		if (order==null) order ="";

		Query query = em.createNamedQuery("SelectDispatchList");

		if((pos!=null) && (limit!=null)) query.setFirstResult(pos).setMaxResults(limit);

		List<Dispatch> a = (List<Dispatch>) query.getResultList();


		return a;
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

		return 0;
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
	public int insert(String message, String name, Place place, Date creationDate, Date scheduledDate,
			String[] personIds, HashMap categories, SmsStatus status) {

		AssignmentStatus assignmentstatus = Facade.getInstance().selectAssingmentStatus(Constants.ASSIGNMENT_STATUS_ASSIGNED);

		// Inicializo la convocatoria
		Dispatch dispatch = new Dispatch();
		dispatch.setName(name);
		dispatch.setScheduledDate(scheduledDate);
		dispatch.setPlace(place);

		for(String idPerson: personIds){
			PersonMovil person = Facade.getInstance().selectPersonMovil(Integer.parseInt(idPerson));
			if (person!=null){
				PersonCategory category=new PersonCategory();
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


	public int update(int id, String message, String name, Place place,
			Date creationDate, Date scheduledDate, HashMap personIds,
			HashMap categories, HashMap arStatus,HashMap arAssignmentIds, 
			SmsStatus status) {

		AssignmentStatus assignmentstatus = Facade.getInstance().selectAssingmentStatus(Constants.ASSIGNMENT_STATUS_ASSIGNED);
		
		// Obtengo la convocatoria
		// Recorro para cada asignación 
		Dispatch dispatch2 = this.selectDispatch(id);

		Iterator it = arAssignmentIds.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Entry) it.next();
			int key = (int) entry.getKey();
			long idAssignment = new Long((Integer) entry.getValue());
			int categoryId = 0;
			int idStatus = (int) arStatus.get(key); 

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
						assignment.setStatus(assignmentstatus);
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
				if (!(assignment1.getJob().getCategory().getId()==categoryId)){
					// Cambio el dato sobre el puesto y mando un SMS avisando
					PersonCategory pc = Facade.getInstance().selectPersonCategory(categoryId);
					assignment1.getJob().setCategory(pc);
				
	//				Facade.getInstance().updateJob(assignment1.getJob());
					
					// Creo el SMS
					SMS sms = new SMS();
					sms.setMensaje(message.trim());
					sms.setMensaje(sms.getMensaje().trim() + " CAMBIO DE CATEGORIA " + pc.getName().trim());
	
					sms.setPersonMovil(assignment1.getPersonMovil());
					sms.setStatus(status);
					sms.setAction(Constants.SMS_ACTION_OUTCOME);
					sms.setCreationDate(creationDate);
					
					assignment1.setStatus(assignmentstatus);
					List smsList = assignment1.getSmsList();
					smsList.add(sms);
					assignment1.setSmsList(smsList);
					this.update(dispatch2);
					
				}else if  (!(assignment1.getStatus().getId()==idStatus)){
					AssignmentStatus newStatus = Facade.getInstance().selectAssingmentStatus(idStatus);
					assignment1.setStatus(newStatus);
					this.update(dispatch2);
				}
			}
		}

			//		for(int i=0; i<arAssignmentIds.size();i++){
//			long idAssignment = new Long((Integer) arAssignmentIds.get(i+1));
//			if (idAssignment==0){	// CREO UNA NUEVA ASIGNACION
//				int idPerson = (int) personIds.get(i+1);
//				PersonMovil person = Facade.getInstance().selectPersonMovil(idPerson);
//				if (person!=null){
//					PersonCategory category=new PersonCategory();
//					try{
//						if(categories.containsKey(person.getId())){
//							int categoryId = (int) categories.get(person.getId());
//							category = Facade.getInstance().selectPersonCategory(categoryId);
//						}
//					}catch(Exception e){}
//
//					if ((person.getMovil()!=null)){
//						// Creo el SMS
//						SMS sms = new SMS();
//						sms.setMensaje(message.trim());
//	 					if (category !=null)
//							sms.setMensaje(sms.getMensaje().trim() + " " + category.getName().trim());
//
//						sms.setPersonMovil(person);
//						sms.setStatus(status);
//						sms.setAction(Constants.SMS_ACTION_OUTCOME);
//						sms.setCreationDate(creationDate);
//
//						// Agrego el SMS a una nueva asignaciï¿½n de trabajo
//						Assignment assignment = new Assignment();
//						assignment.setStatus(assignmentstatus);
//						assignment.setPersonMovil(person);
//						assignment.addSms (sms);
//						assignment.setAssignmentDate(creationDate);
//
//						// Creo el puesto de trabajo
//						Job job = new Job();
//						job.setCategory(category);
//						job.addAssignment(assignment);
//
//						dispatch2.addJob(job);
//						
//						this.update(dispatch2);
//
//					}
//				}
//				
//				
//			}else{				
//				Assignment assignment1= dispatch2.getAssignment(idAssignment);
//				int idCategory = (int) categories.get(i+1);
//				int idStatus = (int) arStatus.get(i+1); 
//				if (!(assignment1.getJob().getCategory().getId()==idCategory)){
//					// Cambio el dato sobre el puesto y mando un SMS avisando
//					PersonCategory pc = Facade.getInstance().selectPersonCategory(idCategory);
//					assignment1.getJob().setCategory(pc);
//				
//	//				Facade.getInstance().updateJob(assignment1.getJob());
//					
//					// Creo el SMS
//					SMS sms = new SMS();
//					sms.setMensaje(message.trim());
//					sms.setMensaje(sms.getMensaje().trim() + " CAMBIO DE CATEGORIA " + pc.getName().trim());
//	
//					sms.setPersonMovil(assignment1.getPersonMovil());
//					sms.setStatus(status);
//					sms.setAction(Constants.SMS_ACTION_OUTCOME);
//					sms.setCreationDate(creationDate);
//					
//					assignment1.setStatus(assignmentstatus);
//					List smsList = assignment1.getSmsList();
//					smsList.add(sms);
//					assignment1.setSmsList(smsList);
//					this.update(dispatch2);
//					
//				}else if  (!(assignment1.getStatus().getId()==idStatus)){
//					AssignmentStatus newStatus = Facade.getInstance().selectAssingmentStatus(idStatus);
//					assignment1.setStatus(newStatus);
//					this.update(dispatch2);
//				}
//			}
//		}
		return 0;
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


}
