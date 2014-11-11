package com.puriarte.convocatoria.persistence;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@NamedQueries({
	  @NamedQuery(name="SelectPersonCategory",
		  query="SELECT pc FROM PersonCategory pc WHERE pc.id = :id"),
	  @NamedQuery(name="SelectPersonCategoryList",
	  	query="SELECT pc FROM PersonCategory pc "),
	})
public class PersonCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;
	private String name;

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
}