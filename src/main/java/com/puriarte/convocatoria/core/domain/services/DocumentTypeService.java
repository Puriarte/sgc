package com.puriarte.convocatoria.core.domain.services;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.DocumentType;

public class DocumentTypeService extends Service {

	public static DocumentTypeService getInstance(){
		if(instance == null) instance =(DocumentTypeService) createInstance(new DocumentTypeService(), instance);
		return instance;
	}
	
	public void destroy() {
		destroy(instance);
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

	protected static DocumentTypeService instance = null;
	
}
