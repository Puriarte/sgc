package com.puriarte.convocatoria.core.domain.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.result.Report1;

public class ReportService extends Service{

	public static ReportService getInstance(){
		if(instance == null) instance = (ReportService)createInstance(new ReportService(), instance);
		return instance;
	}
	
	public void destroy() {
		destroy(instance);
	}
	
	public List<Report1> report1(Date from, Date to, String orderBy) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("Report.Report1")
					.setParameter(1, from)
					.setParameter(2, to);
			List<Report1> a = query.getResultList();

			return a;
		}catch(Exception e){
			return null;
		}
	}
	
	protected static ReportService instance = null;
	
}
