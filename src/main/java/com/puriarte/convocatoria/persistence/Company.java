package com.puriarte.convocatoria.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(name="SelectCompanyFromNumber",
		query="SELECT c FROM Company c where c.id= :id "),
	})
@Entity
public class Company {

  @Id
    @GeneratedValue
	private int id;
	private String name;
	private String description;

	public Company(){
		this.id = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public synchronized boolean equalsSynch(Object obj){
		return this.equals(obj);
	}

	@Override
	public boolean equals(Object obj) {
		Company c = (Company)obj;
		return (this.getName().equalsIgnoreCase(c.getName()));
	}

	@Override
	public String toString() {
		return this.getName();
	}

}