package com.puriarte.convocatoria.persistence;

import java.io.Serializable;

import javax.persistence.*;

import com.puriarte.convocatoria.persistence.Pk.PersonAssociationId;

@Entity
@Table(name = "personpersoncategory")
@IdClass(PersonAssociationId.class)
public class PersonCategoryAsociation {

	@Id
	private int idPerson;

	@Id
	private int idPersonCategory;
	
	@Column(name="priority")
	private Integer priority;

	@ManyToOne(  cascade = CascadeType.PERSIST   )
	@PrimaryKeyJoinColumn(name="idPerson", referencedColumnName="id")
	private Person person;
	
	@ManyToOne(  cascade = CascadeType.PERSIST   )
	@PrimaryKeyJoinColumn(name="idPersonCategory", referencedColumnName="id")
	private PersonCategory personCategory;

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public int getIdPersonCategory() {
		return idPersonCategory;
	}

	public void setIdPersonCategory(int idPersonCategory) {
		this.idPersonCategory = idPersonCategory;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public PersonCategory getPersonCategory() {
		return personCategory;
	}

	public void setPersonCategory(PersonCategory personCategory) {
		this.personCategory = personCategory;
	}


	
}
