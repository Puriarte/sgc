package com.puriarte.convocatoria.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	  @NamedQuery(name="SelectSmsStatus",
    query="SELECT s FROM SmsStatus s WHERE s.id = :id"),
	})
@Entity
public class SmsStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	private String name;

	public SmsStatus() {
	}

	public SmsStatus( int id) {
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