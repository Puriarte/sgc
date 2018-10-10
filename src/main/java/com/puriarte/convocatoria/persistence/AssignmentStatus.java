package com.puriarte.convocatoria.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	  @NamedQuery(name="SelectAsignmentStatusList",
			  query="SELECT ast FROM AssignmentStatus ast order by ast.name "),
	  @NamedQuery(name="SelectAssignmentStatus",
			  query="SELECT ast FROM AssignmentStatus ast WHERE ast.id = :id"),
	})
@Entity
public class AssignmentStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	private String name;
	private String description;

	public AssignmentStatus() {
	}

	public AssignmentStatus( int id) {
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