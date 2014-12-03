package com.puriarte.convocatoria.persistence;

import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@NamedQueries({
	  	@NamedQuery(name="SelectSMSList",
        query="SELECT s FROM SMS s " +
        		" where s.creationDate BETWEEN :from AND :to and ((:estado =0) or (s.status.id = :estado) ) order by :orden "),
        @NamedQuery(name="SelectSMSByDispatchList",
        query="SELECT s FROM SMS s " +
        		" where s.creationDate BETWEEN :from AND :to and ((:estado =0) or (s.status.id = :estado) ) and  (s.assignment.job.dispatch.id = :convocatoria)   "),
   		@NamedQuery(name="SelectSMSById",
        query="SELECT s FROM SMS s where s.id = :id "),
   		@NamedQuery(name="SelectSMS2",
        query="SELECT s FROM SMS s LEFT OUTER JOIN s.personMovil pm LEFT OUTER JOIN pm.movil m where m.number = :movilNumber AND s.creationDate = :creationDate  AND s.uuid = :uuid "),
        @NamedQuery(name="SelectSMS",
        query="SELECT s FROM SMS s WHERE s.uuid = :uuid "),
        @NamedQuery(name="SelectSMSListByStatus",
        query="SELECT s FROM SMS s where s.status.id = :status "),
        @NamedQuery(name="SelectRelatedSMSList",
        query="SELECT s FROM SMS s where s.personMovil.id = :movilId and s.status.id = :status and s.assignment is not null  order by s.sentDate desc"),
        @NamedQuery(name="SelectCountSMSByWord",
        query="SELECT count(s) FROM SMS s where s.personMovil.person.id = :personId and s.word = :word and s.sentDate BETWEEN :from AND :to  "),
        @NamedQuery(name="SelectCountSMSByDate",
        query="SELECT count(s) FROM SMS s where s.personMovil.person.id = :personId and s.word = :word and s.sentDate BETWEEN :from AND :to  "),
        @NamedQuery(name="SelectCountExpiredSMS",
        query="SELECT count(s) FROM SMS s where s.personMovil.person.id = :personId and 1=0  and s.sentDate BETWEEN :from AND :to"),
        @NamedQuery(name="SelectCountSentSMS",
        query="SELECT count(s) FROM SMS s where s.personMovil.person.id = :personId and s.status.id = 5 and s.sentDate BETWEEN :from AND :to"),

	})
@Entity
public class SMS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    private String mensaje;
    @ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idSMSStatus")
	private SmsStatus status;

    @ManyToOne
	@JoinColumn(name="idAssignment", insertable = true, updatable = true)
	private Assignment assignment;

    @ManyToOne
	@JoinColumn(name="idPersonMovil", insertable = true, updatable = true)
	private PersonMovil personMovil;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idReferencedSMS")
    private SMS referencedSMS;

    private int action;

    @Temporal(TemporalType.TIMESTAMP)
    protected java.util.Date sentDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected java.util.Date creationDate;

    protected String uuid;


    public SMS(){}

	public SMS(String mensaje) {
		this.mensaje=mensaje;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public SmsStatus getStatus() {
		return status;
	}

	public void setStatus(SmsStatus status) {
		this.status = status;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public PersonMovil getPersonMovil() {
		return personMovil;
	}

	public void setPersonMovil(PersonMovil personMovil) {
		this.personMovil = personMovil;
	}

	public SMS getReferencedSMS() {
		return referencedSMS;
	}

	public void setReferencedSMS(SMS referencedSMS) {
		this.referencedSMS = referencedSMS;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public java.util.Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(java.util.Date sentDate) {
		this.sentDate = sentDate;
	}

	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Dispatch getDispatch(){
		if ((this.assignment!=null)&& (this.assignment.getJob()!=null)&& (this.assignment.getJob().getDispatch()!=null))
			return this.assignment.getJob().getDispatch();
		else
			return null;
	}
}