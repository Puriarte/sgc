package com.puriarte.convocatoria.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;

public class EntityManagerHelper {

	private static final String JPA_TEST = "convocatoria12";
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(JPA_TEST);
	private static final ThreadLocal<EntityManager> threadLocal = new ThreadLocal<EntityManager>();


	public static EntityManager getEntityManager() {
		EntityManager manager = threadLocal.get();
		if (manager == null || manager.isOpen() == false) {
			manager = emf.createEntityManager();
			threadLocal.set(manager);
		}
		return manager;
	}


	public static List<String> getEntityList() {
		List<String> liste = new ArrayList<String>(emf.getMetamodel().getEntities().size());
		for (EntityType<?> entite : emf.getMetamodel().getEntities()) {
			liste.add(entite.getJavaType().getSimpleName().toLowerCase());
		}
		return liste;
	}


	public static void initializeEntityManager() {
		getEntityManager();
	}

	public static void closeEntityManager() {
		final EntityManager em = threadLocal.get();
		threadLocal.set(null);
		closeEm(em);
	}


	public static void commitAndCloseEntityManager() {
		final EntityManager em = threadLocal.get();
		try {
			if (em != null) {
				em.getTransaction().commit();
			}
		} finally {
			threadLocal.set(null);
			closeEm(em);
		}
	}


	public static void beginTransaction() {
		getEntityManager().getTransaction().begin();
	}

	
	public static void commit() {
		getEntityManager().getTransaction().commit();
	}


	public static void rollback() {
		getEntityManager().getTransaction().rollback();
	}

	public static void setRollbackOnly() {
		getEntityManager().getTransaction().setRollbackOnly();
	}

	public static boolean isCloseEntityManager() {
		EntityManager manager = threadLocal.get();
		if (manager == null || manager.isOpen() == false) {
			return true;
		} else {
			return false;
		}
	}

	private static void closeEm(final EntityManager em) {
		try {
			if (em != null) {
				em.close();
			}
		} catch(Exception ex) {
		}
	}
}