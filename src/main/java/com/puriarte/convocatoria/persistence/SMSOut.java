package com.puriarte.convocatoria.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "SMS_OUT")
public class SMSOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String recipient;
    private String text;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "WAP_URL")
    private String wapUrl;

    @Column(name = "WAP_EXPIRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wapExpiryDate;

    @Column(name = "WAP_SIGNAL")
    private String wapSignal;
    private String originator;
    private String encoding;

    @Column(name = "STATUS_REPORT")
    private Integer statusReport;

    @Column(name = "FLASH_SMS")
    private Integer flashSMS;

    @Column(name = "SRC_PORT")
    private Integer srcPort;

    @Column(name = "DST_PORT")
    private Integer dstPort;

    @Column(name = "SENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentDate;

    @Column(name = "REF_NO")
    private String refNo;
    private Integer priority;

    private String status;
    private Integer errors;

    @Column(name = "GATEWAY_ID")
    private String gatewayId;

    @Column(name = "IS_SINCHRONIZED")
    private int isSynchronized;

    public SMSOut(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getWapUrl() {
		return wapUrl;
	}

	public void setWapUrl(String wapUrl) {
		this.wapUrl = wapUrl;
	}

	public String getWapSignal() {
		return wapSignal;
	}

	public void setWapSignal(String wapSignal) {
		this.wapSignal = wapSignal;
	}

	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Integer getStatusReport() {
		return statusReport;
	}

	public void setStatusReport(Integer statusReport) {
		this.statusReport = statusReport;
	}

	public Integer getFlashSMS() {
		return flashSMS;
	}

	public void setFlashSMS(Integer flashSMS) {
		this.flashSMS = flashSMS;
	}

	public Integer getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(Integer srcPort) {
		this.srcPort = srcPort;
	}

	public Integer getDstPort() {
		return dstPort;
	}

	public void setDstPort(Integer dstPort) {
		this.dstPort = dstPort;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getErrors() {
		return errors;
	}

	public void setErrors(Integer errors) {
		this.errors = errors;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public Date getWapExpiryDate() {
		return wapExpiryDate;
	}

	public void setWapExpiryDate(Date wapExpiryDate) {
		this.wapExpiryDate = wapExpiryDate;
	}

	public int getIsSynchronized() {
		return isSynchronized;
	}

	public void setIsSynchronized(int isSynchronized) {
		this.isSynchronized = isSynchronized;
	}



}