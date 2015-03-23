package com.puriarte.convocatoria.persistence;

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
import javax.persistence.Transient;

import com.puriarte.convocatoria.core.exceptions.MovilException;

@NamedQueries({
	  @NamedQuery(name="SelectPerson",
			  query="SELECT p FROM Person p WHERE p.documentType.id = :documentTypeId and p.documentNumber= :document "),
	  @NamedQuery(name="SelectPersonById",
	  	query="SELECT p FROM Person p WHERE p.id = :id "),
      @NamedQuery(name="SelectPersonListWithPriority",
      	query="SELECT p FROM Person p WHERE ((:category = 0) or (p.category.id = :category))   and p.priority in :priorities "),
      @NamedQuery(name="SelectPersonList",
      	query="SELECT p FROM Person p WHERE ((:category = 0) or (p.category.id = :category))  ")
	})
@Entity
public class Person {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;
	private String nickname;
	private String picture;
	private int priority;

	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idPersonCategory")
	private PersonCategory category;

	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idDocumentType")
	private DocumentType documentType;

	private String documentNumber;

	@Transient
	private String selectedMovil;

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public Person(){
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

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public synchronized boolean equalsSynch(Object obj){
		return this.equals(obj);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public PersonCategory getCategory() {
		return category;
	}

	public void setCategory(PersonCategory category) {
		this.category = category;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	  
	public String getSelectedMovil() {
		return selectedMovil;
	}

	public void setSelectedMovil(String selectedMovil) {
		this.selectedMovil = selectedMovil;
	}



}