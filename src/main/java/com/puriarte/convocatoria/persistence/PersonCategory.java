package com.puriarte.convocatoria.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({
	  @NamedQuery(name="SelectPersonCategory",
		  query="SELECT pc FROM PersonCategory pc WHERE pc.id = :id"),
	  @NamedQuery(name="PersonCategory.SelectPersonCategoryList",
	  	query="SELECT pc FROM PersonCategory pc order by pc.name " ),
	  @NamedQuery(name="PersonCategory.SelectPersonCategoryByPersonList",
		query="SELECT pc FROM PersonCategory pc where pc.id = :category order by pc.name "),
	})
public class PersonCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	private String name;

	private String prefix;

	@OneToMany(mappedBy = "personCategory" , cascade = CascadeType.PERSIST , orphanRemoval = true )
	private List<PersonCategoryAsociation> persons;
	
	/*
	@ManyToMany(mappedBy = "categories")
	private List<Person> persons = new ArrayList<>();
*/	 
	public PersonCategory() {
	}

	public PersonCategory(int id) {
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PersonCategoryAsociation> getPersons() {
		return persons;
	}

	public void setPersons(List<PersonCategoryAsociation> persons) {
		this.persons = persons;
	}

	public void remove(PersonCategoryAsociation pc) {
		persons.remove(pc);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}