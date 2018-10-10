package com.puriarte.convocatoria.core.domain.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.DispatchStatus;

public class DispatchStatusService extends Service {

	public static DispatchStatusService getInstance(){
		if(instance == null)  instance=(DispatchStatusService)createInstance(new DispatchStatusService(), instance);
		return instance;
	}

	public void destroy() {
		destroy(instance);
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
			return new ArrayList();
		}
	}

	protected static DispatchStatusService instance = null;

}
