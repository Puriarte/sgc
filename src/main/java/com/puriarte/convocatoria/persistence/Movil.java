package com.puriarte.convocatoria.persistence;

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

@NamedQueries({
	  @NamedQuery(name="SelectMovils",
        query="SELECT m FROM Movil m WHERE m.number LIKE :number and ((:status is null) or (m.movilStatus.id = :status)) order by :order "),
      @NamedQuery(name="SelectMovil",
		query="SELECT m FROM Movil m WHERE m.number = :number and m.movilStatus.id = :movilStatus "),
})
@Entity
public class Movil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String number;
	private String description;

	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idMovilStatus")
	private MovilStatus movilStatus;

	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idCompany")
	private Company company;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MovilStatus getMovilStatus() {
		return movilStatus;
	}

	public void setMovilStatus(MovilStatus movilStatus) {
		this.movilStatus = movilStatus;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}


}