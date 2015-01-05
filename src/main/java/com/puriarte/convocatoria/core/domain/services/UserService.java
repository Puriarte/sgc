package com.puriarte.convocatoria.core.domain.services;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.User;

public class UserService {

	private static UserService INSTANCE = null;

	private UserService() {
	}

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new UserService();
	}

	public static UserService getInstance(){
		if(INSTANCE == null) createInstance();
		return INSTANCE;
	}

	/**
	 * Get EntityManager
	 * @return EntityManager
	 */
	protected EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	public User select(String name, String string) {
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectUser")
				.setParameter("name", name)
				.setParameter("password", string );

		User a = (User) query.getSingleResult();

		return a;
	}


	public synchronized void destroy(){
		INSTANCE = null;
	}
}
