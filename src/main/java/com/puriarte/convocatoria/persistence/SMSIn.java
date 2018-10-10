package com.puriarte.convocatoria.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SMS_IN")
@NamedQueries({
	  @NamedQuery(name="SelectSMSInNotSincronyzedList",
	      query="SELECT si FROM SMSIn si where si.isSynchronized =0  "),
	  @NamedQuery(name="SelectSMSByUUId",
	      query="SELECT si FROM SMSIn si where si.UUId=:uuid and si.originator=:originator and si.messageDate=:messageDate  "),
	})
public class SMSIn {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int process;
    private String originator;
    private String type;
    private char encoding;
    @Column(name = "MESSAGE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date messageDate;
    @Column(name = "RECEIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receiveDate;
    private String text;
    @Column(name = "ORIGINAL_REF_NO")
    private String originalRefNo;
    @Column(name = "ORIGINAL_RECEIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date originalRecibeDate;
    @Column(name = "GATEWAY_ID")
    private String gatewayId;
    @Column(name = "IS_SINCHRONIZED")
    private int isSynchronized;
    
    @Column(name = "UU_ID")
    private String UUId;

    public SMSIn(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getProcess() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
	}

	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public char getEncoding() {
		return encoding;
	}

	public void setEncoding(char encoding) {
		this.encoding = encoding;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getOriginalRefNo() {
		return originalRefNo;
	}

	public void setOriginalRefNo(String originalRefNo) {
		this.originalRefNo = originalRefNo;
	}

	public Date getOriginalRecibeDate() {
		return originalRecibeDate;
	}

	public void setOriginalRecibeDate(Date originalRecibeDate) {
		this.originalRecibeDate = originalRecibeDate;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public int getIsSynchronized() {
		return isSynchronized;
	}

	public void setIsSynchronized(int isSynchronized) {
		this.isSynchronized = isSynchronized;
	}

	public String getUUId() {
		return UUId;
	}

	public void setUUId(String uUId) {
		UUId = uUId;
	}

	


}