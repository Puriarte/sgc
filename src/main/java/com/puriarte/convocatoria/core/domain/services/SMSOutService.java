package com.puriarte.convocatoria.core.domain.services;


import javax.persistence.EntityManager;

import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.SMSOut;

public class SMSOutService {
	static private SMSOutService INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new SMSOutService();
	}

	public static SMSOutService getInstance(){
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

	public void insert(SMSOut sms){
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(sms);
		em.getTransaction().commit();

	}


}
