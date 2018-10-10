package com.puriarte.convocatoria.core.domain.services;

import javax.persistence.EntityManager;
import com.puriarte.convocatoria.persistence.EntityManagerHelper;

public class Service {
	
	protected String HINT_STORE_MODE_LABEL = "javax.persistence.cache.storeMode";
	protected String HINT_STORE_MODE_LABEL_REFRESH = "REFRESH";
	protected String HINT_STORE_REFRESH_LABEL = "eclipselink.refresh";
	protected String HINT_STORE_REFRESH_CASCADE_LABEL = "eclipselink.refresh.cascade";
	protected String HINT_STORE_REFRESH_CASCADE_ALL_PARTS=  "CascadeAllParts";


	protected static synchronized Object createInstance(Object obj, Object instance){
		if(instance== null)
			instance = obj;
		
		return instance;
	}

	/**
	 * Get EntityManager
	 * @return EntityManager
	 */
	protected EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}
	
	public synchronized void destroy(Object instance){
		if (instance!=null)
			instance= null;
	}

}
