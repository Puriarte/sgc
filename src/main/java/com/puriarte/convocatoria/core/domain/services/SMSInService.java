package com.puriarte.convocatoria.core.domain.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.SMSIn;

public class SMSInService extends Service {

	public static SMSInService getInstance(){
		if(instance == null) instance =(SMSInService)createInstance(new SMSInService(), instance);
		return instance;
	}
	
	public void destroy() {
		destroy(instance);
	}
	
	public void insert(SMSIn sms) {
		final EntityManager em = getEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createNamedQuery("SelectSMSByUUId");
		List<SMSIn> a = (List<SMSIn>) query
				.setParameter("uuid", sms.getUUId())
				.setParameter("originator", sms.getOriginator())
				.setParameter("messageDate", sms.getMessageDate())
				.getResultList();

		if (a.size()==0){					
			em.persist(sms);
			em.getTransaction().commit();
			em.close();
		}else{
			em.close();
		}

	}
	
	
	public void update(SMSIn sms) {
		final EntityManager em = getEntityManager();
		em.getTransaction().begin();

		em.persist(sms);
		em.getTransaction().commit();
	}

	/**
	 * Selecciona la lista de SMS no sincronizados
	 *
	 * @param smsStatusPendiente
	 * @return
	 */
	public List<SMSIn> SelectSMSInNotSincronyzedList() {
		final EntityManager em = getEntityManager();
		Query query = em.createNamedQuery("SelectSMSInNotSincronyzedList");
		List<SMSIn> a = (List<SMSIn>) query.getResultList();

		return a;
	}
	
	protected static SMSInService instance = null;
}
