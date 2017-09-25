package com.puriarte.convocatoria.persistence;

import javax.persistence.ConstructorResult ;
import javax.persistence.CascadeType;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;


import com.puriarte.convocatoria.persistence.result.PersonMovilResult;
@SqlResultSetMappings({
	@SqlResultSetMapping(name = "PersonMovilResult", classes = { @ConstructorResult(targetClass = PersonMovilResult.class, columns = {
		@ColumnResult(name = "PERSONMOVIL_ID"),
		@ColumnResult(name = "MOVIL_NUMBER"),
		@ColumnResult(name = "DOCUMENT_NUMBER"),
		@ColumnResult(name = "DOCUMENT_TYPE"),
		@ColumnResult(name = "PERSON_NAME"),
		@ColumnResult(name = "PERSON_NICKNAME"),
		@ColumnResult(name = "PERSON_PICTURE"),
		@ColumnResult(name = "PRIORITY"),
		@ColumnResult(name = "CATEGORY_NAME") }) })
})

@NamedQueries({
	@NamedQuery(name="PersonMovil.SelectPersonMovil",
			query="SELECT pm FROM PersonMovil pm where pm.id= :id "),
/*	@NamedQuery(name="PersonMovil.SelectPersonMovilList2",
   		query="SELECT pm FROM PersonMovil pm WHERE ((:category = 0) or (pm.person.category.id = :category)) order by pm.person.name  "),
  */ @NamedQuery(name="PersonMovil.SelectPersonMovils",
   		query="SELECT pm FROM PersonMovil pm WHERE pm.movil.number LIKE :number and ((:status is null) or (pm.movil.movilStatus.id = :status)) order by :order "),
  /* @NamedQuery(name="PersonMovil.SelectPersonMovilListWithPriority",
       	query="SELECT pm FROM PersonMovil pm WHERE ((:category = 0) or (pm.person.category.id = :category))   and pm.person.priority in :priorities order by pm.person.name "),
   *//*@NamedQuery(name="PersonMovil.SelectPersonMovilList",
	    query="SELECT new com.puriarte.convocatoria.persistence.result.PersonMovilResult(pm.id, pm.movil.number, pm.person.documentNumber ,pm.person.documentType.name,
	     pm.person.name, pm.person.nickname , pm.person.picture,  pm.person.priority, pm.person.preferedCategory.name ) "
	    		+ " FROM PersonMovil pm JOIN pm.person p JOIN p.categories c "
	    		+ " where ( (:p IS NULL) or (pm.person.priority IN (:p) )) and ( (:c IS NULL) or (c.id IN (:c) )) "),
//		 	  + " and ( (?2 is null) or (pm.person.category.id IN (?2) ))"),*/
	})

@NamedNativeQueries({
	@NamedNativeQuery(name = "PersonMovil.SelectPersonMovilList", 
//			query = "SELECT t0.ID as PERSONMOVIL_ID, t1.NUMBER as MOVIL_NUMBER, t2.DOCUMENTNUMBER as DOCUMENT_NUMBER,  t3.NAME as DOCUMENT_TYPE, t2.NAME as PERSON_NAME,  t2.NICKNAME as PERSON_NICKNAME, t2.PICTURE as PERSON_PICTURE, t2.PRIORITY as PRIORITY, T4.NAME AS CATEGORY_NAME  FROM PERSONCATEGORY t4, DOCUMENTTYPE t3, PERSON t2, MOVIL t1, PERSONMOVIL t0  WHERE (t2.ID = t0.idPerson)  AND (t1.ID = t0.idMovil)  AND (t3.ID = t2.idDocumentType)  AND (t4.ID = t2.idPersonCategory) "
			query = "  SELECT * FROM ( "
					+ "SELECT t0.ID as PERSONMOVIL_ID, t1.NUMBER as MOVIL_NUMBER, t2.DOCUMENTNUMBER as DOCUMENT_NUMBER, "
					+ " COALESCE(t3.NAME , '' ) as DOCUMENT_TYPE, COALESCE(t2.NAME, '' ) as PERSON_NAME, " +
					" COALESCE(t2.NICKNAME, '' ) as PERSON_NICKNAME, COALESCE(t2.PICTURE, '' ) as PERSON_PICTURE, COALESCE(t2.PRIORITY , -1) as PRIORITY, COALESCE(T4.NAME, '' ) AS CATEGORY_NAME " +
					" FROM PERSONCATEGORY t4, DOCUMENTTYPE t3, PERSON t2, MOVIL t1, PERSONMOVIL t0 " +
					" WHERE (t2.ID = t0.idPerson) " +
					" AND (t1.ID = t0.idMovil) "+
					" AND (t3.ID = t2.idDocumentType) " + 
					" AND (t4.ID = t2.idPersonCategory) " +
					" AND ((-1 IN ?1) or (t2.PRIORITY IN ?1)) "  + 
					" AND ((-1 IN ?2) or (t0.idPerson IN (select idPerson from personpersoncategory where idPersoncategory in ?2)) ) "
					+ ") AS q"
					+ " order by "
					+ "		CASE WHEN(?3) = 'document_type,document_number' THEN document_number "
					+ "		 WHEN(?3) =  'movil_number' THEN movil_number "
					+ "		 WHEN(?3) =  'document_number' THEN document_number "
					+ "		 WHEN(?3) =  'person_name' THEN person_name "
					+ "		 WHEN(?3) =  'person_nickname' THEN person_nickname "
					+ "		 WHEN(?3) =  'category_name' THEN category_name "
					+ "		 WHEN(?3) =  'priority' THEN CAST (priority AS text)   "
					+ " END "
					, resultSetMapping = "PersonMovilResult"),
})


@Entity
public class PersonMovil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idPerson")
	private Person person;

	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idMovil")
	private Movil movil;

	private int priority;

	public PersonMovil(){
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Movil getMovil() {
		return movil;
	}

	public void setMovil(Movil movil) {
		this.movil = movil;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}



}
