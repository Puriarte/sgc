package com.puriarte.convocatoria.core.domain.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.persistence.Assignment;
import com.puriarte.convocatoria.persistence.AssignmentStatus;
import com.puriarte.convocatoria.persistence.BulkSMS;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.Job;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.convocatoria.persistence.SmsStatus;

public class BulkSMSService {
	static private BulkSMSService INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new BulkSMSService();
	}

	public static BulkSMSService getInstance(){
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


	public int insert(BulkSMS bulkSMS) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(bulkSMS);
		em.getTransaction().commit();

		return 0;
	}


	public int insert(String message, String name, Date creationDate, Date scheduledDate, String[] personIds, SmsStatus status) {

		// Inicializo la convocatoria
		BulkSMS bulkSMS = new BulkSMS();
		bulkSMS.setName(name);
		bulkSMS.setScheduledDate(scheduledDate);

		for(String idPerson: personIds){
			PersonMovil person = Facade.getInstance().selectPersonMovil(Integer.parseInt(idPerson));
			if (person!=null){
				if ((person.getMovil()!=null)){
					// Creo el SMS
					SMS sms = new SMS();
					sms.setMensaje(message);
					sms.setPersonMovil(person);
					sms.setStatus(status);
					sms.setAction(Constants.SMS_ACTION_OUTCOME);
					sms.setCreationDate(creationDate);
					bulkSMS.addSms(sms);
				}
			}
		}
		return this.insert(bulkSMS);
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
