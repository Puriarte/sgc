package com.puriarte.convocatoria.core.domain.services;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.DocumentType;
import com.puriarte.convocatoria.persistence.EntityManagerHelper;

public class DocumentTypeService {
	static private DocumentTypeService INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new DocumentTypeService();
	}

	public static DocumentTypeService getInstance(){
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

	public DocumentType select(int id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectDocumentType")
					.setParameter("id", id);

			DocumentType a = (DocumentType) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}

	}

}
