package com.puriarte.convocatoria.core.domain.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.Job;

public class JobService extends Service{

	public static JobService getInstance(){
		if(instance == null)   instance =(JobService)createInstance(new JobService(), instance);
		return instance;
	}
	
	public void destroy() {
		destroy(instance);
	}

	public Job select(int id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectJob")
					.setParameter("id", id);

			Job a = (Job) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}

	}
	

	public List<Job> selectList() {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectJobList");

			List<Job> a = query.getResultList();

			return a;
		}catch(Exception e){
			return null;
		}
	}
	
	public int insert(Job dt) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(dt);
		em.getTransaction().commit();

		return 0;
	}


	public void delete(Job dt) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.remove(dt);
		em.getTransaction().commit();
	}

	public void update(Job dt) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(dt);
		em.getTransaction().commit();

	}

	
	public void insertJob(Job job) {
		final EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		em.persist(job);
		em.getTransaction().commit();
	}

	protected static JobService instance = null;


}
