package com.puriarte.convocatoria.core.domain.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.Job;
import com.puriarte.convocatoria.persistence.Movil;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.result.PersonMovilResult;

public class PersonMovilService {
	static private PersonMovilService INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new PersonMovilService();
	}

	public static PersonMovilService getInstance(){
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


	public int insertPerson(Person p) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();

		return p.getId();
	}

	/**
	 * Inserta una persona en la abse de datos.
	 *
	 * Si ya ubiera una persona con el mismo nro de cedula y tipo de documento se devuelve un error.
	 *
	 * @param document
	 * @param documentType
	 * @return
	 * @throws SQLException
	 */
	public Person insertPerson(String document) throws SQLException {


		Person person = new Person();
//		person.setDocumentType(new DocumentType(documentType));
		person.setDocumentNumber(document);

		insertPerson(person);

		return person;

	}



	public void borrarPerson(Person p) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.remove(p);
		em.getTransaction().commit();
	}

	public void updatePerson(Person person) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(person);
		em.getTransaction().commit();

	}

	public Person selectPerson(String document, int documentType) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectPerson")
					.setParameter("documentTypeId", documentType)
					.setParameter("document", document );

			query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			query.setHint("eclipselink.refresh", "true");
			query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

			Person a = (Person) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}

	}

	public Person selectPerson(int id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectPersonById")
					.setParameter("id", id);

			query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			query.setHint("eclipselink.refresh", "true");
			query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

			Person a = (Person) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}

	}

	
	public List<PersonMovilResult> selectList(List<String> priorities, List<String>  categories ,int estado, String orderByColumn, Integer pos, Integer limit) {
		
		final EntityManager em = getEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		try {
			Query query = em.createNamedQuery("PersonMovil.SelectPersonMovilList" , PersonMovilResult.class);
			
			if ((priorities==null) || (priorities.size()==0))
				priorities =Arrays.asList("-1");
	
			query.setParameter(1, priorities);


			if ((categories==null) || (categories.size()==0))
				categories =Arrays.asList("-1");

			query.setParameter(2, categories);

			//		*/	query.setParameter(2, category);
//			query.setParameter(3, estado);

			query.setHint(QueryHints.BIND_PARAMETERS, HintValues.FALSE);//<--the hint

/*			query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			query.setHint("eclipselink.refresh", "true");
			query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");
	*/		
			return query.getResultList();
		} catch (Exception se) {
			return null;
		}

		
	}
	
	public List<PersonMovil> selectObjectList(List<String> priorities, int category ,int estado, String orderByColumn, Integer pos, Integer limit) {

		final EntityManager em = getEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PersonMovil> criteria = builder.createQuery(PersonMovil.class);
		Root<PersonMovil> personMovil = criteria.from(PersonMovil.class);
		
		Join person= personMovil.join("person", JoinType.LEFT);
		Join documentType= person.join("documentType", JoinType.LEFT);
		Join movil= personMovil.join("movil", JoinType.LEFT);
					

		criteria.select(personMovil);
		if ((orderByColumn!=null) && (!orderByColumn.equals("")) )
			criteria.orderBy(parseOrderBy(orderByColumn, builder, movil,documentType, person, personMovil));
		Query query = em.createQuery(criteria);
		
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setHint("eclipselink.refresh", "true");
		query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

		return query.getResultList();
		    
	}

	public List<Order> parseOrderBy(String orderByColumn, CriteriaBuilder builder, 
			Join movil, Join documentType, Join person, Root<PersonMovil> personMovil){
		List<Order> orders = new  ArrayList<Order>(); 
		String[] orderList = orderByColumn.split(",");
		for (String aux: orderList){
			aux=aux.trim();
			boolean ascending=true;
			String[] aux1 = aux.split("\\ ");
			if (aux1.length>1)	if(aux1[1].equals("desc"))	ascending=false;

			String[] orden = aux1[0].split("\\.");
			javax.persistence.criteria.Order order;
			
			if (orden[0].equals("movil")){
				order = ascending ? builder.asc(movil.get(orden[1])) : builder.desc(movil.get(orden[1]));
			}else if (orden[0].equals("person")){
				if (orden[1].equals("documentType")){
					order = ascending ? builder.asc(documentType.get(orden[2])): builder.desc(documentType.get(orden[2]));
				}if (orden[1].equals("category")){
					order = ascending ? builder.asc(documentType.get(orden[2])): builder.desc(documentType.get(orden[2]));
				}else{
					order = ascending ? builder.asc(person.get(orden[1])): builder.desc(person.get(orden[1]));
				}
			}else{
				order = ascending ? builder.asc(personMovil.get(orden[0])): builder.desc(personMovil.get(orden[0]));
			}		
			orders.add(order);
		}
		
		return orders;
	}

	
	public void insert(PersonMovil pm) {

		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(pm);
		em.getTransaction().commit();

	}


	public PersonMovil select(String numero , int status) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("PersonMovil.SelectPersonMovils")
					.setParameter("status", status)
					.setParameter("number", numero )
					.setParameter("order", "priority");

//			query.setHint("javax.persistence.cache.storeMode", "REFRESH");
//			query.setHint("eclipselink.refresh", "true");
//			query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

			PersonMovil a = (PersonMovil) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}
	}

	public PersonMovil select(Integer id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("PersonMovil.SelectPersonMovil")
					.setParameter("id", id);

			query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			query.setHint("eclipselink.refresh", "true");
			query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

			PersonMovil a = (PersonMovil) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}
	}

	public PersonMovil selectWithCategories(Integer id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("PersonMovil.SelectPersonMovil")
					.setParameter("id", id);

			query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			query.setHint("eclipselink.refresh", "true");
			query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");
			query.setHint("eclipselink.left-join-fetch", "pm.person.categories");

			PersonMovil a = (PersonMovil) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}
	}



	public void updatePersonMovil(PersonMovil person, String movilNumber) {
		
		final EntityManager em = getEntityManager();

		person.getMovil().setNumber(movilNumber);
		
		em.getTransaction().begin();
		em.persist(person);
		em.getTransaction().commit();

		
	}


}
