package com.puriarte.convocatoria.core.domain.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.Category;

import javax.persistence.EntityManager;

public class CategoryService extends Service{

	public static CategoryService getInstance(){
		if(instance == null) createInstance(new CategoryService(), instance);
		return  instance;
	}

	public Category select(int id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectCategory")
					.setParameter("id", id);

			return (Category) query.getSingleResult();
		}catch(Exception e){
			return null;
		}

	}
	
	public List<Category> selectList() {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectCategoryList");

			return  query.getResultList();
		}catch(Exception e){
			return new ArrayList ();
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
	
	protected static CategoryService instance = null;


}
