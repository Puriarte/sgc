package com.puriarte.convocatoria.core.domain.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.internal.jpa.querydef.CriteriaQueryImpl;

import com.puriarte.convocatoria.persistence.Assignment;
import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.convocatoria.persistence.SmsStatus;

public class SMSService1 {
	static private SMSService1 INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new SMSService1();
	}

	public static SMSService1 getInstance(){
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

	public void insert(SMS sms){
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(sms);
		em.getTransaction().commit();

	}

	public void insert(String word, Assignment assignment, SMS referencedSms,  PersonMovil personMovil, String text, int action, SmsStatus status, Date date, String uuid) throws Exception {

		final EntityManager em = getEntityManager();

		SMS sms = new SMS();

		if (personMovil!=null)
			sms.setPersonMovil(personMovil);
		else if ((assignment!=null) && (assignment.getPersonMovil()!=null)){
			sms.setPersonMovil(assignment.getPersonMovil());
		}

		if (assignment!=null) sms.setAssignment(assignment);
		if (referencedSms!=null) sms.setReferencedSMS(referencedSms);
		if (word!=null )sms.setWord(word);

		sms.setCreationDate(date);
		sms.setSentDate(date); //TODO: Se va a vambiar por un campo transmition date

		sms.setStatus(status);

		sms.setMensaje(text);
		sms.setAction(action);

		sms.setUuid(uuid);
		em.getTransaction().begin();
		em.persist(sms);
		em.getTransaction().commit();

	}


	public void update(SMS sms) {
		final EntityManager em = getEntityManager();
		em.getTransaction().begin();

		em.persist(sms);
		em.getTransaction().commit();
	}

	/**
	 * Busca los SMS filtrando por fecha
	 *
	 * @param from
	 * @param to
	 * @param order
	 * @param pos
	 * @param limit
	 * @return
	 */
	public List<SMS> selectList(Date fromDate, Date toDate, Integer estado,  
			boolean deleted,
			String order, String strOrder, Integer pos, Integer limit){
		final EntityManager em = getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery q = cb.createQuery(SMS.class);
		Root<SMS> sms = q.from(SMS.class);
		Join status = sms.join("status");
		Join assignment= sms.join("assignment", JoinType.LEFT);
		Join personMovil= sms.join("personMovil", JoinType.LEFT);
		Join person= personMovil.join("person", JoinType.LEFT);
		Join movil= personMovil.join("movil", JoinType.LEFT);
		
		Join job= assignment.join("job", JoinType.LEFT);
		Join dispatch= job.join("dispatch", JoinType.LEFT);

		if (strOrder.equals("asc")){
			if(order.equals("number"))	q.orderBy(cb.asc(movil.get("number")));
			if(order.equals("creationDate"))	q.orderBy(cb.asc(sms.get("creationDate")));
			if(order.equals("person"))	q.orderBy(cb.asc(person.get("name")));
			if(order.equals("texto"))	q.orderBy(cb.asc(sms.get("mensaje")));
			if(order.equals("sentDate"))	q.orderBy(cb.asc(sms.get("sentDate")));
			if(order.equals("direction"))	q.orderBy(cb.asc(sms.get("action")));
			if(order.equals("status"))	q.orderBy(cb.asc(status.get("name")));
			if(order.equals("dispatch"))	q.orderBy(cb.asc(dispatch.get("name")));
			if(order.trim().equals("person.name asc, message")==true)
				q.orderBy(cb.asc(person.get("name")));
		}else{
			if(order.equals("number"))	q.orderBy(cb.desc(movil.get("number")));
			if(order.equals("creationDate"))	q.orderBy(cb.desc(sms.get("creationDate")));
			if(order.equals("person"))	q.orderBy(cb.desc(person.get("name")));
			if(order.equals("texto"))	q.orderBy(cb.desc(sms.get("mensaje")));
			if(order.equals("sentDate"))	q.orderBy(cb.desc(sms.get("sentDate")));
			if(order.equals("direction"))	q.orderBy(cb.desc(sms.get("action")));
			if(order.equals("status"))	q.orderBy(cb.desc(status.get("name")));
			if(order.equals("dispatch"))	q.orderBy(cb.desc(dispatch.get("name")));
			if(order.trim().equals("person.name desc, message")==true)
				q.orderBy(cb.desc(person.get("name")));
		}
		
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (fromDate != null) predicateList.add(cb.greaterThanOrEqualTo(sms.<Date>get("creationDate"), fromDate));
		if (toDate != null) predicateList.add(cb.lessThanOrEqualTo(sms.<Date>get("creationDate"), toDate));
		if (estado>0) predicateList.add(cb.equal(status.get("id"), estado));
		predicateList.add(cb.equal(sms.get("deleted"), deleted));
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		q.where(predicates);
		
		Query query = em.createQuery(q);
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setHint("eclipselink.refresh", "true");
		query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");
		if((pos!=null) && (limit!=null)) query.setFirstResult(pos).setMaxResults(limit);
		
		List<SMS> a = query.getResultList();
	
		return a;
	}

	public List<SMS> selectListByDispatch(Date fromDate, Date toDate, Integer estado, boolean deleted,  Integer convocatoria, String order, 
			String orderDirection, 	Integer pos, Integer limit){
		final EntityManager em = getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery q = cb.createQuery(SMS.class);
		Root<SMS> sms = q.from(SMS.class);
		Join status = sms.join("status");
		Join assignment= sms.join("assignment", JoinType.LEFT);
		Join personMovil= sms.join("personMovil", JoinType.LEFT);
		Join person= personMovil.join("person", JoinType.LEFT);
		Join movil= personMovil.join("movil", JoinType.LEFT);		
		Join job= assignment.join("job", JoinType.LEFT);
		Join dispatch= job.join("dispatch", JoinType.LEFT);

		if (orderDirection.equals("asc")){
			if(order.equals("number"))	q.orderBy(cb.asc(movil.get("number")));
			if(order.equals("creationDate"))	q.orderBy(cb.asc(sms.get("creationDate")));
			if(order.equals("person"))	q.orderBy(cb.asc(person.get("name")));
			if(order.equals("texto"))	q.orderBy(cb.asc(sms.get("mensaje")));
			if(order.equals("sentDate"))	q.orderBy(cb.asc(sms.get("sentDate")));
			if(order.equals("direction"))	q.orderBy(cb.asc(sms.get("action")));
			if(order.equals("status"))	q.orderBy(cb.asc(status.get("name")));
			if(order.equals("dispatch"))	q.orderBy(cb.asc(dispatch.get("name")));
			if(order.trim().equals("person.name asc, message")==true)
				q.orderBy(cb.asc(person.get("name")));
		}else{
			if(order.equals("number"))	q.orderBy(cb.desc(movil.get("number")));
			if(order.equals("creationDate"))	q.orderBy(cb.desc(sms.get("creationDate")));
			if(order.equals("person"))	q.orderBy(cb.desc(person.get("name")));
			if(order.equals("texto"))	q.orderBy(cb.desc(sms.get("mensaje")));
			if(order.equals("sentDate"))	q.orderBy(cb.desc(sms.get("sentDate")));
			if(order.equals("direction"))	q.orderBy(cb.desc(sms.get("action")));
			if(order.equals("status"))	q.orderBy(cb.desc(status.get("name")));
			if(order.equals("dispatch"))	q.orderBy(cb.desc(dispatch.get("name")));
			if(order.trim().equals("person.name desc, message")==true)
				q.orderBy(cb.desc(person.get("name")));
		}
		
		
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (fromDate != null) predicateList.add(cb.greaterThanOrEqualTo(sms.<Date>get("creationDate"), fromDate));
		if (toDate != null) predicateList.add(cb.lessThanOrEqualTo(sms.<Date>get("creationDate"), toDate));
		if (estado>0) predicateList.add(cb.equal(status.get("id"), estado));
		if (convocatoria!=null) predicateList.add(cb.equal(dispatch.get("id"), convocatoria));
		predicateList.add(cb.equal(sms.get("deleted"), deleted));
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		q.where(predicates);
		
		Query query = em.createQuery(q);
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setHint("eclipselink.refresh", "true");
		query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");
		if((pos!=null) && (limit!=null)) query.setFirstResult(pos).setMaxResults(limit);
		List<SMS> a = query.getResultList();
		
		return a;
	}

	public int selectCountAssignment(int personId, int status ,Date  dateFrom, Date dateTo){
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectCountAssignmentsByStatus")
				.setParameter("from", dateFrom)
				.setParameter("to", dateTo)
				.setParameter("status", status )
				.setParameter("personId", personId);

		Object a = query.getSingleResult();
		if (a.getClass().getName().equals("java.lang.Long"))
			return Integer.parseInt(a.toString());
		else
			return (Integer)a;
	}

	public int selectCountAssignment(Date fechaInicio, Date fechaFin, int estado,int convocatoria, boolean deleted) {
		final EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery q = cb.createQuery(SMS.class);
		Root<SMS> from = q.from(SMS.class);
		Join status = from.join("status");
		Join assignment= from.join("assignment", JoinType.LEFT);
		Join job= assignment.join("job", JoinType.LEFT);
		Join dispatch= job.join("dispatch", JoinType.LEFT);
		
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (fechaInicio != null) predicateList.add(cb.greaterThanOrEqualTo(from.<Date>get("creationDate"), fechaInicio));
		if (fechaFin != null) predicateList.add(cb.lessThanOrEqualTo(from.<Date>get("creationDate"), fechaFin));
		if (estado>0) predicateList.add(cb.equal(status.get("id"), estado));
		if (convocatoria>0) predicateList.add(cb.equal(dispatch.get("id"), convocatoria));
		predicateList.add(cb.equal(from.get("deleted"), deleted));
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		q.select(cb.count(from));
		q.where(predicates);
		
		Object a = em.createQuery(q).getSingleResult();
		
		if (a.getClass().getName().equals("java.lang.Long"))
			return Integer.parseInt(a.toString());
		else
			return (Integer)a;
	}
	
	

	public int selectCountSentAssignment(int personId, Date  dateFrom, Date dateTo) {
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectCountSentAssignments")
				.setParameter("from", dateFrom)
				.setParameter("to", dateTo)
				.setParameter("personId", personId);

		Object a = query.getSingleResult();
		if (a.getClass().getName().equals("java.lang.Long"))
			return Integer.parseInt(a.toString());
		else
			return (Integer)a;
	}



	public int selectCountSMS(Date fechaInicio, Date fechaFin, int estado,int convocatoria, boolean deleted) {
		final EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery q = cb.createQuery(SMS.class);
		Root<SMS> from = q.from(SMS.class);
		Join status = from.join("status");
		Join assignment= from.join("assignment", JoinType.LEFT);
		Join job= assignment.join("job", JoinType.LEFT);
		Join dispatch= job.join("dispatch", JoinType.LEFT);
		
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (fechaInicio != null) predicateList.add(cb.greaterThanOrEqualTo(from.<Date>get("creationDate"), fechaInicio));
		if (fechaFin != null) predicateList.add(cb.lessThanOrEqualTo(from.<Date>get("creationDate"), fechaFin));
		if (estado>0) predicateList.add(cb.equal(status.get("id"), estado));
		if (convocatoria>0) predicateList.add(cb.equal(dispatch.get("id"), convocatoria));
		predicateList.add(cb.equal(from.get("deleted"), deleted));
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		q.select(cb.count(from));
		q.where(predicates);
		
		Object a = em.createQuery(q).getSingleResult();
		
		if (a.getClass().getName().equals("java.lang.Long"))
			return Integer.parseInt(a.toString());
		else
			return (Integer)a;
	}
	

	/**
	 * Selecciona la lista de SMS or estado
	 *
	 * @param smsStatusPendiente
	 * @return
	 */
	public List<SMS> selectList(int status) {
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectSMSListByStatus")
			.setParameter("status", status );
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setHint("eclipselink.refresh", "true");
		query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

		List<SMS> a = (List<SMS>) query.getResultList();

		return a;
	}

	/**
	 * Trae la lista de los SMS que pueden ser respuesta
	 * 
	 * @param movil
	 * @param status
	 * @param pos
	 * @param limit
	 * @return
	 */
	public List<SMS> SelectRelatedSMSList(PersonMovil movil, SmsStatus status, Integer pos, Integer limit) {

		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectRelatedSMSList")
			.setParameter("movilId", movil.getId())
			.setParameter("status", status.getId());
		
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setHint("eclipselink.refresh", "true");
		query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

		if((pos!=null) && (limit!=null)) query.setFirstResult(pos).setMaxResults(limit);

		List<SMS> a = (List<SMS>) query.getResultList();

		return a;
	}

	public List<SMS> SelectRelatedSMSList(PersonMovil movil, SmsStatus status,
			int dispatchId, Integer pos, Integer limit) {
		
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectRelatedFromDispatchSMSList")
			.setParameter("dispatchId", dispatchId)
			.setParameter("movilId", movil.getId())
			.setParameter("status", status.getId());
		
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setHint("eclipselink.refresh", "true");
		query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

		if((pos!=null) && (limit!=null)) query.setFirstResult(pos).setMaxResults(limit);

		List<SMS> a = (List<SMS>) query.getResultList();

		return a;
	}

	/**
	 * Obtiene el estado de SM
	 *
	 * @param id
	 * @return
	 */
	public SmsStatus selectSmsStatus(Integer id){
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectSmsStatus")
			.setParameter("id", id);
		SmsStatus a = (SmsStatus) query.getSingleResult();
		return a;
	}


	/**
	 * Busca los SMS filtrando por fecha
	 *
	 * @param from
	 * @param to
	 * @param order
	 * @param pos
	 * @param limit
	 * @return
	 */
//	public boolean exist(String movilNumber, Date date){
//		final EntityManager em = getEntityManager();
//
//		Query query = em.createNamedQuery("SelectSMS")
//			.setParameter("movilNumber", movilNumber )
//			.setParameter("creationDate", date);
//		Vector e = (Vector) query.setMaxResults(1).getResultList();
//		if (e.size()>0)
//			return true;
//		else
//			return false;
//
//
//	}

	public boolean existSms( String uuid) {
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectSMS")
				.setParameter("uuid", uuid);
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setHint("eclipselink.refresh", "true");
		query.setHint("eclipselink.refresh.cascade", "CascadeAllParts");

		Vector e = (Vector) query.setMaxResults(1).getResultList();
		if (e.size()>0)
			return true;
		else
			return false;
	}

	public SMS selectSMS(int id) {
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectSMSById")
			.setParameter("id", id);
		SMS a = (SMS) query.getSingleResult();

		return a;
	}

	public void  deleteSMS(SMS sms) {
		final EntityManager em = getEntityManager();
		sms.setDeleted(true);
		em.getTransaction().begin();
		em.persist(sms);
		em.getTransaction().commit();
	}

	public void updateSMSAssignment(SMS sms, SMS smsRef) {
		final EntityManager em = getEntityManager();

		if(smsRef!=null){
			sms.setAssignment(smsRef.getAssignment());
			sms.setReferencedSMS(smsRef);
		}else{
			sms.setAssignment(null);
			sms.setReferencedSMS(null);
		}

		em.getTransaction().begin();
		em.persist(sms);
		em.getTransaction().commit();
		
	}


	

}
