package com.puriarte.convocatoria.core.domain.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.AssignmentStatus;
import com.puriarte.convocatoria.persistence.DocumentType;
import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.Place;

public class AssignmentStatusService {
	static private AssignmentStatusService INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new AssignmentStatusService();
	}

	public static AssignmentStatusService getInstance(){
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

	public AssignmentStatus select(int id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectAssignmentStatus")
					.setParameter("id", id);

			AssignmentStatus a = (AssignmentStatus) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}

	}
	

	public List<AssignmentStatus> selectList() {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectAsignmentStatusList");

			List<AssignmentStatus> a = query.getResultList();

			return a;
		}catch(Exception e){
			return null;
		}
	}

}
