package com.puriarte.convocatoria.core.domain.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.Category;
import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.Person;

public class CategoryService {
	static private CategoryService INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new CategoryService();
	}

	public static CategoryService getInstance(){
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

	public Category select(int id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectCategory")
					.setParameter("id", id);

			Category a = (Category) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}

	}
	

	public List<Category> selectList() {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectCategoryList");

			List<Category> a = query.getResultList();

			return a;
		}catch(Exception e){
			return null;
		}
	}
	
	public int insert(Category dt) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(dt);
		em.getTransaction().commit();

		return 0;
	}


	public void delete(Category dt) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.remove(dt);
		em.getTransaction().commit();
	}

	public void update(Category dt) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(dt);
		em.getTransaction().commit();

	}

}
