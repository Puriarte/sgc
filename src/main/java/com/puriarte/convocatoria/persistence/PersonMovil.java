package com.puriarte.convocatoria.persistence;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(name="PersonMovil.SelectPersonMovil",
			query="SELECT pm FROM PersonMovil pm where pm.id= :id "),
	@NamedQuery(name="PersonMovil.SelectPersonMovilList2",
   		query="SELECT pm FROM PersonMovil pm WHERE ((:category = 0) or (pm.person.category.id = :category)) order by pm.person.name  "),
   @NamedQuery(name="PersonMovil.SelectPersonMovils",
   		query="SELECT pm FROM PersonMovil pm WHERE pm.movil.number LIKE :number and ((:status is null) or (pm.movil.movilStatus.id = :status)) order by :order "),
   @NamedQuery(name="PersonMovil.SelectPersonMovilListWithPriority",
       	query="SELECT pm FROM PersonMovil pm WHERE ((:category = 0) or (pm.person.category.id = :category))   and pm.person.priority in :priorities order by pm.person.name "),
   @NamedQuery(name="PersonMovil.SelectPersonMovilList",
	    query="SELECT new com.puriarte.convocatoria.persistence.result.PersonMovilResult(pm.id, pm.movil.number, pm.person.documentNumber ,pm.person.documentType.name, "
	    		+ "pm.person.name, pm.person.nickname , "
	    		+ "pm.person.picture, pm.person.category.name, pm.person.priority ) " +
		 	  " FROM PersonMovil pm  "
		 	  + " where ( (:p IS NULL) or (pm.person.priority IN (:p) )) "),
//		 	  + " and ( (?2 is null) or (pm.person.category.id IN (?2) ))"),
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
