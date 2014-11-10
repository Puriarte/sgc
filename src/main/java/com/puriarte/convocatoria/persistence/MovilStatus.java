package com.puriarte.convocatoria.persistence;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@NamedQueries({
	  @NamedQuery(name="SelectMovilStatus",
  query="SELECT ms FROM MovilStatus ms WHERE ms.id = :id"),
	})
public class MovilStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;
	private String name;

	public MovilStatus() {
	}

	public MovilStatus( int id) {
		this.id=id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}