package com.puriarte.convocatoria.core.domain.services;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.core.exceptions.PlaceException;
import com.puriarte.convocatoria.persistence.DocumentType;
import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.Place;

public class PlaceService {
	static private PlaceService INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new PlaceService();
	}

	public static PlaceService getInstance(){
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

	public Place select(int id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectPlace")
					.setParameter("id", id);

			Place a = (Place) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}

	}


	public List<Place> selectList(boolean includeDeleted) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("Place.SelectPlaceList");

			if (includeDeleted)
				query.setParameter("includeDeleted", 1);
			else
				query.setParameter("includeDeleted", 0);
				
							 
			query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			query.setHint("eclipselink.refresh", "true");
			query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

			List<Place> a = query.getResultList();

			return a;
		}catch(Exception e){
			return null;
		}
	}

	public int insert(Place dt) throws SQLException, PlaceException  {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(dt);
		em.getTransaction().commit();

		return 0;
	}

	public void delete(Place dt) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.remove(dt);
		em.getTransaction().commit();
	}

	public void update(Place dt) throws SQLException, PlaceException  {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(dt);
		em.getTransaction().commit();
	}

}
