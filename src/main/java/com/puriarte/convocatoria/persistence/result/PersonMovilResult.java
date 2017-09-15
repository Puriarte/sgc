package com.puriarte.convocatoria.persistence.result;

import java.util.ArrayList;
import java.util.List;

import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonCategory;

public class PersonMovilResult {

	private int id;
	private String movilNumber;
	private String personDocumentNumber;
	private String documentTypeName;
	private String personName;
	private String personNickName;
	private String personPicture;
	private String categoryName;
	private int priority;
	private ArrayList<PersonCategory> categories;

	public PersonMovilResult(){
	}
	
	
	public PersonMovilResult(int id, String movilNumber,
			String personDocumentNumber, String documentTypeName,
			String personName, String personNickName, String personPicture,
			int priority) {
		super();
		this.id = id;
		this.movilNumber = movilNumber;
		this.personDocumentNumber = personDocumentNumber;
		this.documentTypeName = documentTypeName;
		this.personName = personName;
		this.personNickName = personNickName;
		this.personPicture = personPicture;
		this.priority = priority;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMovilNumber() {
		return movilNumber;
	}

	public void setMovilNumber(String movilNumber) {
		this.movilNumber = movilNumber;
	}

	public String getPersonDocumentNumber() {
		return personDocumentNumber;
	}

	public void setPersonDocumentNumber(String personDocumentNumber) {
		this.personDocumentNumber = personDocumentNumber;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonNickName() {
		return personNickName;
	}

	public void setPersonNickName(String personNickName) {
		this.personNickName = personNickName;
	}

	public String getPersonPicture() {
		return personPicture;
	}

	public void setPersonPicture(String personPicture) {
		this.personPicture = personPicture;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public ArrayList<PersonCategory> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<PersonCategory> categories) {
		this.categories = categories;
	}

	public String getCategoryNames() {
		String value="";
		for(PersonCategory pc: categories){
			value+=pc.getName() + ", ";
		}
		if(value.length()>0) value=value.substring(0,value.length()-2);
		return value;
	}
}
