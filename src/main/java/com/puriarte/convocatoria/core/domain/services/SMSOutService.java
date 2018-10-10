package com.puriarte.convocatoria.core.domain.services;


import javax.persistence.EntityManager;

import com.puriarte.convocatoria.persistence.SMSOut;

public class SMSOutService extends Service{

	public static SMSOutService getInstance(){
		if(instance == null)  instance =(SMSOutService) createInstance(new SMSOutService(), instance);
		return instance;
	}
	
	public void destroy() {
		destroy(instance);
	}
	
	public void insert(SMSOut sms){
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(sms);
		em.getTransaction().commit();

	}

	protected static SMSOutService instance = null;
	
}
