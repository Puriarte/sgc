package com.puriarte.convocatoria.persistence.result;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


public class Report1 {

    private String name;
    private String phone;
	private Long convened;
	private Long  accepted;
	private Long  rejected;
	private Long cancelled;
	
    public Report1(){}
 

	public Report1(String name, String phone, Long convened, Long accepted,
			Long rejected, Long cancelled) {
		super();
		this.name = name;
		this.phone = phone;
		this.convened = convened;
		this.accepted = accepted;
		this.rejected = rejected;
		this.cancelled=cancelled;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Long getConvened() {
		return convened;
	}


	public void setConvened(Long convened) {
		this.convened = convened;
	}


	public Long getAccepted() {
		return accepted;
	}


	public void setAccepted(Long accepted) {
		this.accepted = accepted;
	}


	public Long getRejected() {
		return rejected;
	}

	public Long getPercAccepted() {
		return (accepted*100)/((convened==0)?1:convened);
	}



	public Long getCancelled() {
		return cancelled;
	}


	public void setCancelled(Long cancelled) {
		this.cancelled = cancelled;
	}
 
    

}