package com.puriarte.convocatoria.persistence.result;



public class Report1 {

    private String name;
    private String phone;
	private Long convened;
	private Long  accepted;
	private Long  rejected;
	private Long cancelled;
	private Long assigned;
	
    public Report1(){}
 
	public Report1(String name, String phone, Integer convened, Integer accepted,
			Integer rejected, Integer cancelled, Integer assigned) {
		super();
		this.name = name;
		this.phone = phone;
		this.convened = convened.longValue();
		this.accepted = accepted.longValue();
		this.rejected = rejected.longValue();
		this.cancelled=cancelled.longValue();
		this.assigned=assigned.longValue();
	}

	public Report1(String name, String phone, Long convened, Long accepted,
			Long rejected, Long cancelled, Long assigned) {
		super();
		this.name = name;
		this.phone = phone;
		this.convened = convened;
		this.accepted = accepted;
		this.rejected = rejected;
		this.cancelled=cancelled;
		this.assigned=assigned;
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


	public Long getAssigned() {
		return assigned;
	}


	public void setAssigned(Long assigned) {
		this.assigned = assigned;
	}


	public void setRejected(Long rejected) {
		this.rejected = rejected;
	}
 
    

}