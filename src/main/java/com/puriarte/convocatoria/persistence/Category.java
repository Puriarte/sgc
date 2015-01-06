package com.puriarte.convocatoria.persistence;

import java.io.Serializable;

import javax.persistence.*;

@Entity

@NamedQueries({
	@NamedQuery(name="SelectCategory",
			query="SELECT cat FROM Category cat WHERE cat.id = :id"),
    @NamedQuery(name="SelectCategoryList",
  		query="SELECT cat FROM Category cat "),
	})
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;
	private String name;

	public Category() {
	}

	public Category(int id) {
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