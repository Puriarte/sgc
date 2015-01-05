package com.puriarte.convocatoria.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idAssignmentStatus")
	private AssignmentStatus status;

    @ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idJob")
	private Job job;

    @ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idPersonMovil")
	private PersonMovil personMovil;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date assignmentDate;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="idAssignment")
	private List<SMS> smsList = new ArrayList<SMS>();


    public Assignment(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AssignmentStatus getStatus() {
		return status;
	}

	public void setStatus(AssignmentStatus status) {
		this.status = status;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public PersonMovil getPersonMovil() {
		return personMovil;
	}

	public void setPersonMovil(PersonMovil personMovil) {
		this.personMovil = personMovil;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	

	public Date getAssignmentDate() {
		return assignmentDate;
	}

	public void setAssignmentDate(Date assignmentDate) {
		this.assignmentDate = assignmentDate;
	}

	public List<SMS> getSmsList() {
		return smsList;
	}

	public void setSmsList(List<SMS> smsList) {
		this.smsList = smsList;
	}

	public void addSms(SMS sms) {
		if (smsList==null)
			smsList = new ArrayList<SMS>();
		smsList.add(sms);
	}


}