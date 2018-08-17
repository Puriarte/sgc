package com.puriarte.convocatoria.core.domain.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonCategoryAsociation;

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

	public PersonCategory selectByName(String name) throws NonUniqueResultException, Exception{
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("PersonCategory.SelectPersonCategoryByName")
				.setParameter("name", name);

		PersonCategory a = (PersonCategory) query.getSingleResult();
		
		return a;
	}
	
	public List<PersonCategory> selectList(boolean includeDeleted) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("PersonCategory.SelectPersonCategoryList");
			
			if (includeDeleted)
				query.setParameter("includeDeleted", 1);
			else
				query.setParameter("includeDeleted", 0);
			
			query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			query.setHint("eclipselink.refresh", "true");
			query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

			return new ArrayList<PersonCategory>(query.getResultList());
		}catch(Exception e){
			return null;
		}
	}

	public ArrayList<PersonCategoryAsociation> selectByPersonList(int idPerson) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("Person.SelectPersonPersonCategoriesList");
			query.setParameter("idPerson", idPerson);
			
			query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			query.setHint("eclipselink.refresh", "true");
			query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

			ArrayList<PersonCategoryAsociation> a = new ArrayList<PersonCategoryAsociation> (query.getResultList());

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
