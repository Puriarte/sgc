package com.puriarte.convocatoria.core.domain.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.DocumentType;
import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.Place;
import com.puriarte.convocatoria.persistence.result.Report1;

public class ReportService {
	static private ReportService INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new ReportService();
	}

	public static ReportService getInstance(){
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

	public List<Report1> report1(Date from, Date to, String orderBy) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("Report1")
					.setParameter(1, from)
					.setParameter(2, to);
			List<Report1> a = query.getResultList();

			return a;
		}catch(Exception e){
			return null;
		}
	}
}
