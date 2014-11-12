package com.puriarte.convocatoria.core.domain.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.SMSIn;

public class SMSInService {
	static private SMSInService INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new SMSInService();
	}

	public static SMSInService getInstance(){
		if(INSTANCE == null) createInstance();
		return INSTANCE;
	}

	public synchronized void destroy(){
		INSTANCE = null;
	}

	/**
	 * Get EntityManager
	 * @return EntityManager
	 */
	protected EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	

	public void insert(SMSIn sms) {
		final EntityManager em = getEntityManager();
		em.getTransaction().begin();

		em.persist(sms);
		em.getTransaction().commit();
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

}
