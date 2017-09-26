package com.puriarte.convocatoria.persistence.result;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FieldResult;
import javax.persistence.Id;

import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonCategoryAsociation;

public class PersonMovilResult implements javax.persistence.EntityResult{

	private int id;
	private int personId;
	
	private String movilNumber;
	private String personDocumentNumber;
	private String documentTypeName;
	private String personName;
	private String personNickName;
	private String personPicture;
	private String categoryName;
	private String preferedCategoryName;
	private Integer priority;
	private ArrayList<PersonCategoryAsociation> categories;

	public PersonMovilResult(){
	}
	
	public PersonMovilResult(int id) {
		super();
		this.id = id;
	}
	
	public PersonMovilResult(int id, int personId, String movilNumber,
			String personDocumentNumber, String documentTypeName,
			String personName, String personNickName, String personPicture,
			Integer priority, String preferedCategoryName) {
		super();
		try{
				this.id = id;
				this.personId = personId;
				this.movilNumber = movilNumber;
				this.personDocumentNumber = personDocumentNumber;
				this.documentTypeName = documentTypeName;
				this.personName = personName;
				this.personNickName = personNickName;
				this.personPicture = personPicture;
				this.priority = priority;
				this.preferedCategoryName=preferedCategoryName;
		}catch (Exception e){
		e.printStackTrace();	
		}
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	
	public String getPreferedCategoryName() {
		return preferedCategoryName;
	}


	public void setPreferedCategoryName(String preferedCategoryName) {
		this.preferedCategoryName = preferedCategoryName;
	}


	public ArrayList<PersonCategoryAsociation> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<PersonCategoryAsociation> categories) {
		this.categories = categories;
	}

	public String getCategoryNames() {
		String value="";
		for(PersonCategoryAsociation pc: categories){
			if (pc!=null)
				value+=pc.getPersonCategory().getName() + "(" + pc.getPriority() + "), ";
		}
		if(value.length()>0) value=value.substring(0,value.length()-2);
		return value;
	}


	@Override
	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Class entityClass() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public FieldResult[] fields() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String discriminatorColumn() {
		// TODO Auto-generated method stub
		return null;
	}
}
