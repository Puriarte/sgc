package com.puriarte.convocatoria.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity

@NamedQueries({
	@NamedQuery(name="SelectJob",
			query="SELECT j FROM Job j WHERE j.id = :id"),
    @NamedQuery(name="SelectJobList",
  		query="SELECT j FROM Job j "),
	})
public class Job implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;
	private String name;

	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idCategory")
	private PersonCategory category;

	@ManyToOne
	@JoinColumn(name="idDispatch", insertable = true, updatable = true)
	private Dispatch dispatch;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="idJob")
	private List<Assignment> assignmentList = new ArrayList<Assignment>();

	public Job() {
	}

	public Job(int id) {
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

	public PersonCategory getCategory() {
		return category;
	}

	public void setCategory(PersonCategory category) {
		this.category = category;
	}

	public Dispatch getDispatch() {
		return dispatch;
	}

	public void setDispatch(Dispatch dispatch) {
		this.dispatch = dispatch;
	}

	public List<Assignment> getAssignmentList() {
		return assignmentList;
	}

	public void setAssignmentList(List<Assignment> assignmentList) {
		this.assignmentList = assignmentList;
	}

	public void addAssignment(Assignment assignment) {
		if (assignmentList==null)
			assignmentList = new ArrayList<Assignment>();
		assignmentList.add(assignment);

	}


}