package com.puriarte.convocatoria.core.domain.services;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.User;

public class UserService extends Service {

	public static UserService getInstance(){
		if(instance == null) instance = (UserService) createInstance(new UserService(), instance);
		return instance;
	}

	public void destroy() {
		destroy(instance);
	}

	
	public User select(String name, String string) {
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectUser")
				.setParameter("name", name)
				.setParameter("password", string );

		User a = (User) query.getSingleResult();

		return a;
	}

	protected static UserService instance = null;

}
