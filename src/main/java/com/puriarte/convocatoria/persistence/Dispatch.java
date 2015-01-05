package com.puriarte.convocatoria.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DISPATCH database table.
 *
 */
@NamedQueries({
	  @NamedQuery(name="SelectDispatch",
		      query="SELECT d FROM Dispatch d WHERE d.id = :id"),
	  @NamedQuery(name="SelectDispatchList",
		      query="SELECT d FROM Dispatch d  WHERE ((:estado = 0) or (d.dispatchStatus.id = :estado)) ")
	})
@Entity
public class Dispatch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date scheduledDate;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date creationDate;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="idDispatch")
	private List<Job> jobList = new ArrayList<Job>();

	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idPlace")
	private Place place;

	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idDispatchStatus")
	private DispatchStatus dispatchStatus;


	public Dispatch() {
	}

	public void setName(String name) {
		this.name = name;
	}

	public java.util.Date getScheduledDate() {
		return this.scheduledDate;
	}

	public void setScheduledDate(java.util.Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}



	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
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

	public List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

	public void addJob(Job job) {
		if (jobList==null)
			jobList = new ArrayList<Job>();
		jobList.add(job);

	}

	public Assignment getAssignment(Long id) {
		if (jobList==null)
			return null;
		
		for(Job job : jobList){
			for(Assignment assignment: job.getAssignmentList()){
				if (assignment.getId().equals(id))
					return assignment;
			}
		}
		return null;
	}

	public DispatchStatus getDispatchStatus() {
		return dispatchStatus;
	}

	public void setDispatchStatus(DispatchStatus dispatchStatus) {
		this.dispatchStatus = dispatchStatus;
	}

}