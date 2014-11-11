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

@NamedQueries({
	  @NamedQuery(name="SelectBulkSMS",
		      query="SELECT bs FROM BulkSMS bs WHERE bs.id = :id"),
	  @NamedQuery(name="SelectBulkSMSList",
		      query="SELECT bs FROM BulkSMS bs")
	})
@Entity
public class BulkSMS implements Serializable {
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
	@JoinColumn(name="idBulkSMS")
	private List<SMS> smsList = new ArrayList<SMS>();


	public BulkSMS() {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
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