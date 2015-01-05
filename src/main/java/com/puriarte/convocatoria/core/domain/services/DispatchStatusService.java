package com.puriarte.convocatoria.core.domain.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.AssignmentStatus;
import com.puriarte.convocatoria.persistence.DispatchStatus;
import com.puriarte.convocatoria.persistence.DocumentType;
import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.Place;

public class DispatchStatusService {
	static private DispatchStatusService INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new DispatchStatusService();
	}

	public static DispatchStatusService getInstance(){
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

	public DispatchStatus select(int id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectDispatchStatus")
					.setParameter("id", id);

			DispatchStatus a = (DispatchStatus) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}

	}
	

	public List<DispatchStatus> selectList() {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectDispatchStatusList");

			List<DispatchStatus> a = query.getResultList();

			return a;
		}catch(Exception e){
			return null;
		}
	}

}
