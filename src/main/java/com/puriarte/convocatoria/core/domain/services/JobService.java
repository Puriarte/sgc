package com.puriarte.convocatoria.core.domain.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.Job;
import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.Person;

public class JobService {
	static private JobService INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new JobService();
	}

	public static JobService getInstance(){
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

	 


}
