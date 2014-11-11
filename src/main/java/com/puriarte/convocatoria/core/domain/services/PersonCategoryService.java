package com.puriarte.convocatoria.core.domain.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.PersonCategory;

public class PersonCategoryService {
	static private PersonCategoryService INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new PersonCategoryService();
	}

	public static PersonCategoryService getInstance(){
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

	public PersonCategory select(int id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectPersonCategory")
					.setParameter("id", id);

			PersonCategory a = (PersonCategory) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}

	}


	public List<PersonCategory> selectList() {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectPersonCategoryList");

			List<PersonCategory> a = query.getResultList();

			return a;
		}catch(Exception e){
			return null;
		}
	}

	public int insert(PersonCategory dt) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(dt);
		em.getTransaction().commit();

		return 0;
	}


	public void delete(PersonCategory dt) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.remove(dt);
		em.getTransaction().commit();
	}

	public void update(PersonCategory dt) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(dt);
		em.getTransaction().commit();

	}

}
