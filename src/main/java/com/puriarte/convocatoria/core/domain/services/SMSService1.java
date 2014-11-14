package com.puriarte.convocatoria.core.domain.services;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.puriarte.convocatoria.persistence.Assignment;
import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.Movil;
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
	public List<SMS> selectList(Date from, Date to, Integer estado,  String order, Integer pos, Integer limit){
		final EntityManager em = getEntityManager();

		if (order==null) order ="";

		Query query = em.createNamedQuery("SelectSMSList")
			.setParameter("from", from )
			.setParameter("to", to)
			.setParameter("estado", estado);

		query.setHint("javax.persistence.cache.storeMode", "REFRESH");

		if((pos!=null) && (limit!=null)) query.setFirstResult(pos).setMaxResults(limit);

		List<SMS> a = (List<SMS>) query.getResultList();


		return a;
	}

	public List<SMS> selectListByDispatch(Date from, Date to, Integer estado, Integer convocatoria, String order, Integer pos, Integer limit){
		final EntityManager em = getEntityManager();

		if (order==null) order ="";

		Query query = em.createNamedQuery("SelectSMSByDispatchList")
			.setParameter("from", from )
			.setParameter("to", to)
			.setParameter("estado", estado)
			.setParameter("convocatoria", convocatoria);

		query.setHint("javax.persistence.cache.storeMode", "REFRESH");

		if((pos!=null) && (limit!=null)) query.setFirstResult(pos).setMaxResults(limit);

		List<SMS> a = (List<SMS>) query.getResultList();


		return a;
	}

	public int selectCountSMS(int personId, String word,Date  dateFrom, Date dateTo){
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectCountSMSByWord")
				.setParameter("from", dateFrom)
				.setParameter("to", dateTo)
				.setParameter("word", word )
				.setParameter("personId", personId);

		Object a = query.getSingleResult();
		if (a.getClass().getName().equals("java.lang.Long"))
			return Integer.parseInt(a.toString());
		else
			return (Integer)a;
	}


	/**
	 * Cuanta los SMS que expiraron antes de responderse
	 *
	 * @param personId
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 */
	public int selectCountExpiredSMS(int personId, Date  dateFrom, Date dateTo) {
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectCountExpiredSMS")
				.setParameter("from", dateFrom)
				.setParameter("to", dateTo)
				.setParameter("personId", personId);

		Object a = query.getSingleResult();
		if (a.getClass().getName().equals("java.lang.Long"))
			return Integer.parseInt(a.toString());
		else
			return (Integer)a;
	}

	public int selectCountSentSMS(int personId, Date  dateFrom, Date dateTo) {
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectCountSentSMS")
				.setParameter("from", dateFrom)
				.setParameter("to", dateTo)
				.setParameter("personId", personId);

		Object a = query.getSingleResult();
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
		List<SMS> a = (List<SMS>) query.getResultList();

		return a;
	}

	public List<SMS> SelectRelatedSMSList(PersonMovil movil, SmsStatus status, Integer pos, Integer limit) {

		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectRelatedSMSList")
			.setParameter("movilId", movil.getId())
			.setParameter("status", status.getId());

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
		Vector e = (Vector) query.setMaxResults(1).getResultList();
		if (e.size()>0)
			return true;
		else
			return false;
	}

}
