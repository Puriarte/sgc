package com.puriarte.convocatoria.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@NamedQueries({
	  @NamedQuery(name="SelectDispatchStatusList",
			  query="SELECT dst FROM DispatchStatus dst order by dst.name "),
	  @NamedQuery(name="SelectDispatchStatus",
			  query="SELECT dst FROM DispatchStatus dst WHERE dst.id = :id"),
	})
@Entity
public class DispatchStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;

	public DispatchStatus() {
	}

	public DispatchStatus( int id) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}