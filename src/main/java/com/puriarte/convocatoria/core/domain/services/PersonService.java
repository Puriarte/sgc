package com.puriarte.convocatoria.core.domain.services;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.SMS;

public class PersonService extends Service{
 

	public static PersonService getInstance(){
		if(instance == null) instance =(PersonService)createInstance(new PersonService(), instance);
		return instance;
	}
	
	public void destroy() {
		destroy(instance);
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

	public void updatePerson(Person person) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(person);
		em.getTransaction().commit();

	}

	public void deletePerson(Person person) {
		final EntityManager em = getEntityManager();
		person.setDeleted(true);
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

	public List<Person> selectList(List<String> priorities, int category ,int estado, String order, Integer pos, Integer limit) {
		final EntityManager em = getEntityManager();
		Query query ;
		if (order==null) order ="";
		if ((priorities==null) || (priorities.size()==0)){
			query = em.createNamedQuery("SelectPersonList"	)
			.setParameter("category", category);
		}else{
			query = em.createNamedQuery("SelectPersonListWithPriority"	)
			.setParameter("category", category)
			.setParameter("priorities", priorities );
		}

		if((pos!=null) && (limit!=null)) query.setFirstResult(pos).setMaxResults(limit);

		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setHint("eclipselink.refresh", "true");
		query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

		List<Person> a = (List<Person>) query.getResultList();
		return a;
	}

	protected static PersonService instance = null;

	
}
