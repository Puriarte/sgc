package com.puriarte.convocatoria.persistence;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.puriarte.convocatoria.core.exceptions.MovilException;

@NamedQueries({
	@NamedQuery(name="SelectPersonMovil",
		query="SELECT pm FROM PersonMovil pm where pm.id= :id "),
	@NamedQuery(name="SelectPersonMovilList",
   		query="SELECT pm FROM PersonMovil pm WHERE ((:category = 0) or (pm.person.category.id = :category)) "),
   @NamedQuery(name="SelectPersonMovils",
   		query="SELECT pm FROM PersonMovil pm WHERE pm.movil.number LIKE :number and ((:status is null) or (pm.movil.movilStatus.id = :status)) order by :order "),
   @NamedQuery(name="SelectPersonMovilListWithPriority",
       	query="SELECT pm FROM PersonMovil pm WHERE ((:category = 0) or (pm.person.category.id = :category))   and pm.person.priority in :priorities "),

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