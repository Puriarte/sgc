package com.puriarte.convocatoria.core.domain.services;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;



import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.exceptions.MovilException;
import com.puriarte.convocatoria.core.exceptions.PersonException;
import com.puriarte.convocatoria.persistence.Company;
import com.puriarte.convocatoria.persistence.DocumentType;
import com.puriarte.convocatoria.persistence.EntityManagerHelper;
import com.puriarte.convocatoria.persistence.Movil;
import com.puriarte.convocatoria.persistence.MovilStatus;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonMovil;

public class MovilService {

	static private MovilService INSTANCE = null;

	private static synchronized void createInstance(){
		if(INSTANCE == null)
			INSTANCE = new MovilService();
	}

	public static MovilService getInstance(){
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


	public void crear(Movil movil){
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(movil);
		em.getTransaction().commit();
		em.close();

	}

	public List<Movil> selectList(String nroDestino , int status) {
		final EntityManager em = getEntityManager();

		Query query = em.createNamedQuery("SelectMovils")
				.setParameter("status", status)
				.setParameter("number", nroDestino )
				.setParameter("order", "movilStatus.id");

		List<Movil> a = (List<Movil>) query.getResultList();

		return a;
	}

	public Movil select(String nroDestino , int status) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectMovils")
					.setParameter("status", status)
					.setParameter("number", nroDestino )
					.setParameter("order", "movilStatus.id");

			Movil a = (Movil) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}
	}

	public Movil selectMovil(String nroDestino, int idStatus) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectMovil")
					.setParameter("number", nroDestino )
					.setParameter("movilStatus", idStatus);

			Movil a = (Movil) query.getSingleResult();

			return a;
		}catch(NoResultException ne){
			return null;
		}catch(Exception e){
			throw e;
		}
	}

	public MovilStatus selectStatus(int id) {
		final EntityManager em = getEntityManager();

		try{
			Query query = em.createNamedQuery("SelectMovilStatus")
					.setParameter("id", id);

			MovilStatus a = (MovilStatus) query.getSingleResult();

			return a;
		}catch(Exception e){
			return null;
		}
	}

	private PersonMovil insertPersonAndMovil( String number, String document, DocumentType documentType) throws SQLException{
		Movil movil = new Movil();
		Person person = new Person();
		MovilStatus ms =null;
		Company company = getMovilCompanyFromNumber(number);

		if ((document!=null) && (!document.trim().equals(""))){
			person.setDocumentType(documentType);
			person.setDocumentNumber(document);
			int idPerson = Facade.getInstance().insertPerson(person );
			person.setId(idPerson);
			ms = Facade.getInstance().selectMovilStatus(Constants.MOVIL_STATUS_ACTIVE);
		}else{
			ms = Facade.getInstance().selectMovilStatus(Constants.MOVIL_STATUS_PENDING);
		}
		movil.setNumber(number);
		movil.setMovilStatus(ms);
		movil.setCompany(company);
		Facade.getInstance().insertMovil(movil);

		PersonMovil pm = new PersonMovil();
		pm.setMovil(movil);
		pm.setPerson(person);

		Facade.getInstance().insertPersonMovil(pm);
		return pm;
	}


	private PersonMovil insertPersonAndMovil( Movil movil, Person person) throws SQLException{

		int idPerson = Facade.getInstance().insertPerson(person );
		person.setId(idPerson);

		Facade.getInstance().insertMovil(movil);

		PersonMovil pm = new PersonMovil();
		pm.setMovil(movil);
		pm.setPerson(person);

		Facade.getInstance().insertPersonMovil(pm);
		return pm;
	}
	private Company getMovilCompanyFromNumber(String number) {
		final EntityManager em = getEntityManager();

		try{
			int id = 1;

			//TODO: Hacerlo dinámico
			if (number.length()<=5)
				return null;
			else if (number.startsWith("59891") ||
					number.startsWith("59892") ||
					number.startsWith("59898") ||
					number.startsWith("59899")
					){
				id = 1;
			}	else if (number.startsWith("59896") ||
					number.startsWith("59897") ){
				id = 2;
			}	else if (number.startsWith("59893") ||
					number.startsWith("59894") ||
					number.startsWith("59895")){
				id = 3;
			}

			Query query = em.createNamedQuery("SelectCompanyFromNumber")
					.setParameter("id", id);

			Company c = (Company) query.getSingleResult();

			return c;
		}catch(NoResultException ne){
			return null;
		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * Inserta un movil
	 *
	 * @param number
	 * @param document
	 * @param documentType
	 * @throws MovilException
	 * @throws SQLException
	 * @throws PersonException
	 */
	public PersonMovil insert(String number, String document, DocumentType documentType) throws MovilException, SQLException, PersonException {
		Person person =null;
		try {
			// Busco la persona
			// Si el documento es vacio la persona no existe. Sino busco a ver si la persona existe
			if ((document!=null) && (!document.trim().equals("")))
				person = Facade.getInstance().getPerson(document, documentType.getId() );

			// Traigo el movil con estado activo
			Movil movil = this.selectMovil(number, Constants.MOVIL_STATUS_ACTIVE);

			if ((movil == null ) && (person ==null)){
				if ((document==null) || (document.trim().equals(""))){
					return null;
				}else{
					return insertPersonAndMovil(number, document, documentType);
				}
			}else {
				return null;
			}
//
//			if (movil == null){
//				movil = new Movil();
//				if (person==null){
//					MovilStatus ms =null;
//					if ((document!=null) && (!document.trim().equals(""))){
//						person = new Person();
//						person.setDocumentType(documentType);
//						person.setDocumentNumber(document);
//						int idPerson = Facade.getInstance().insertPerson(person );
//						person.setId(idPerson);
//						ms = Facade.getInstance().selectMovilStatus(Constants.MOVIL_STATUS_ACTIVE);
//					}else{
//						ms = Facade.getInstance().selectMovilStatus(Constants.MOVIL_STATUS_PENDING);
//					}
//					movil.setNumber(number);
//					movil.setMovilStatus(ms);
//					Facade.getInstance().insertMovil(movil);
//
//					PersonMovil pm = new PersonMovil();
//					pm.setMovil(movil);
//					pm.setPerson(person);
//
//					Facade.getInstance().insertPersonMovil(pm);
//					return pm;
//				}else{
//					PersonMovil pm = new PersonMovil();
//					Facade.getInstance().updatePerson(person);
//					return null;
//				}
//			}else{
//				return null;
//				if (person == null){
//					MovilStatus ms =null;
//					if ((document!=null) && (!document.equals(""))){
//						person = new Person();
//						person.setDocumentType(documentType);
//						person.setDocumentNumber(document);
//						Facade.getInstance().insertPerson(person );
//					}
//
//					// SI el estado del movil es pendiente dejo el actual como activo.
//					if (movil.getMovilStatus().getId()== Constants.MOVIL_STATUS_PENDING)
//						ms= Facade.getInstance().selectMovilStatus(Constants.MOVIL_STATUS_ACTIVE);
//					else
//						ms= Facade.getInstance().selectMovilStatus(Constants.MOVIL_STATUS_PENDING);
//
//					// En este caso el movil está asociado a alguna otra cedula
//					Movil newMovil = new Movil();
//					newMovil.setMovilStatus(ms);
//					newMovil.setNumber(number);
//					newMovil.setPerson(person);
//
//					Facade.getInstance().insertMovil(newMovil);
//
//					return newMovil;
//					//TODO: Hay que agregar una tarea para el administrador ya que aca se genera un
//					//      conflicto por haber dos cedulas para un mismo celular.
//			//		throw new MovilException(MovilException.ALREADY_IN_USE);
//				}else{
//					// Me fijo sI el movil ya está asociado a la cedula dada
//					if ( ( movil.getPerson().getDocumentNumber()== person.getDocumentNumber()) &&
//					   (movil.getPerson().getDocumentType() == person.getDocumentType() )){
//						throw new MovilException(MovilException.DUPLICATED);
//					}else{
//						// En este caso el movil está asociado a alguna otra cedula
//						Movil newMovil = new Movil();
//						newMovil.setMovilStatus(new MovilStatus(Constants.MOVIL_STATUS_PENDING));
//						newMovil.setNumber(number);
//						person.addMovil(newMovil);
//						Facade.getInstance().updatePerson(person);
//						//TODO: Hay que agregar una tarea para el administrador ya que aca se genera un
//						//      conflicto por haber dos cedulas para un mismo celular.
//						throw new MovilException(MovilException.ALREADY_IN_USE);
//					}
//				}
//			}
		}catch(PersistenceException pex){
			throw new PersonException(PersonException.REPEATED);
		}
	}


	public PersonMovil insert(Person person, String movilNumber) throws MovilException, SQLException, PersonException {
		try {
			if (movilNumber == null || movilNumber.trim().equals(""))
				throw new MovilException(MovilException.EMPTY);

			if (Facade.getInstance().selectMovil(movilNumber, Constants.MOVIL_STATUS_ACTIVE )!=null)
				throw new MovilException(MovilException.ALREADY_IN_USE);

			if(person.getDocumentType()==null || person.getDocumentNumber() == null || person.getDocumentNumber().trim().equals(""))
				throw new PersonException(PersonException.DOCUMENT_EMPTY);

			if (Facade.getInstance().getPerson(person.getDocumentNumber(),  person.getDocumentType().getId())!=null)
				throw new PersonException(PersonException.PERSON_ALREADY_EXISTS);

			Movil movil = new Movil();
			MovilStatus ms =null;
			Company company = getMovilCompanyFromNumber(movilNumber);
			ms = Facade.getInstance().selectMovilStatus(Constants.MOVIL_STATUS_ACTIVE);

			movil.setNumber(movilNumber);
			movil.setMovilStatus(ms);
			movil.setCompany(company);

			return insertPersonAndMovil(movil, person);

		}catch(PersistenceException pex){
			throw new PersonException(PersonException.REPEATED);
		}
	}

	public int insert(Movil m) {
		final EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.persist(m);
		em.getTransaction().commit();

		return m.getId();
	}


	/*public List<MovimientoDeudaResult> getMovimientosDeuda(Integer idCuenta, String idRazon,
			Date fechaInicio, Date fechaFin, Date fechaVencimientoInicio, Date fechaVencimientoFin,
			Integer nroComprobante, Integer tipoComprobante, boolean soloImpagos,
			String orden, boolean ascending, Integer pos, Integer limit){

		System.out.println("Inicio (getMovimientosDeudaJPA)  :" + (new Date()).getMinutes() + ":" +(new Date()).getSeconds() );

		if (orden==null) orden ="dmv_fmov";

		Query query = em.createNamedQuery("BuscarMovimientosDeudores")
			.setParameter(1, idCuenta )
			.setParameter(2, idRazon)
			.setParameter(3, tipoComprobante )
			.setParameter(4, nroComprobante)
			.setParameter(5, fechaInicio)
			.setParameter(6, fechaFin)
			.setParameter(7, soloImpagos)
			.setParameter(8, orden);

		if((pos!=null) && (limit!=null)) query.setFirstResult(pos).setMaxResults(limit);

		List<MovimientoDeudaResult> a = (List<MovimientoDeudaResult>) query.getResultList();

		System.out.println("Fin (getMovimientosDeudaJPA): " +(new Date()).getMinutes() + ":" +(new Date()).getSeconds() );

		return a;
	}*/

}
