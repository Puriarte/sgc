package com.puriarte.convocatoria.core.domain.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.AssignmentStatus;

public class AssignmentStatusService extends Service{

	public static AssignmentStatusService getInstance(){
		if(instance == null)  instance =(AssignmentStatusService) createInstance(new AssignmentStatusService(),instance);
		return instance;
	}
	
	public void destroy() {
		destroy(instance);
	}

	public AssignmentStatus select(int id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectAssignmentStatus")
					.setParameter("id", id);

			return (AssignmentStatus) query.getSingleResult();

		}catch(Exception e){
			return null;
		}

	}
	
	public List<AssignmentStatus> selectList() {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectAsignmentStatusList");

			return query.getResultList();
		}catch(Exception e){
			return new ArrayList ();
		}
	}
	

	protected static AssignmentStatusService instance = null;

}
