package com.puriarte.convocatoria.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Transient;

import com.puriarte.convocatoria.persistence.result.Report1;
@SqlResultSetMappings({
	@SqlResultSetMapping(name = "Report1Map", classes = { 
		@ConstructorResult(targetClass = Report1.class, 
			columns = {
		    	@ColumnResult(name = "name"),
		    	@ColumnResult(name = "phone") ,
		    	@ColumnResult(name = "convened") ,
		    	@ColumnResult(name = "accepted") ,
		    	@ColumnResult(name = "rejected") ,
		    	@ColumnResult(name = "cancelled") ,
		    	@ColumnResult(name = "assigned") ,
			}) 
		})
	})
 @NamedQueries({
	  @NamedQuery(name="SelectPerson",
			  query="SELECT p FROM Person p WHERE p.documentType.id = :documentTypeId and p.documentNumber= :document "),
	  @NamedQuery(name="SelectPersonById",
	  	query="SELECT p FROM Person p WHERE p.id = :id "),
	  @NamedQuery(name="Person.SelectPersonPersonCategoriesList",
      	query="SELECT p.categories FROM Person p WHERE (p.id = :idPerson)  "),
      @NamedQuery(name="Person.SelectPersonPersonLocalList",
      	query="SELECT p.categories FROM Person p WHERE (p.id = :idPerson)  "),
      	
	})
@NamedNativeQueries({
	@NamedNativeQuery(name="Report.Report1",
	  	query=" select p.name as name , m.number as phone , " + 
	  			" (select count(*) from assignment a left outer join job j on a.idJob = j.id left outer join dispatch d on j.idDispatch = d.id where idpersonmovil  = pm.id  and assignmentdate between ?1 and ?2  ) as convened, " +
	  			" (select count(*) from assignment a left outer join job j on a.idJob = j.id left outer join dispatch d on j.idDispatch = d.id where idpersonmovil  = pm.id  and assignmentdate between ?1 and ?2  and a.IDASSIGNMENTSTATUS=5 ) as accepted, " +
	  			" (select count(*) from assignment a left outer join job j on a.idJob = j.id left outer join dispatch d on j.idDispatch = d.id where idpersonmovil  = pm.id  and assignmentdate between ?1 and ?2  and a.IDASSIGNMENTSTATUS=6 ) as rejected, " +
	  			" (select count(*) from assignment a left outer join job j on a.idJob = j.id left outer join dispatch d on j.idDispatch = d.id where idpersonmovil  = pm.id  and assignmentdate between ?1 and ?2  and a.IDASSIGNMENTSTATUS=3 ) as cancelled, " +
	  			" (select count(*) from assignment a left outer join job j on a.idJob = j.id left outer join dispatch d on j.idDispatch = d.id where idpersonmovil  = pm.id  and assignmentdate between ?1 and ?2  and a.IDASSIGNMENTSTATUS=2 ) as assigned " +
	  			" from personmovil pm  left outer join person p on pm.idperson=p.id " +
	  			" left outer join movil m on pm.idmovil=m.id "
	  			+ " where p.deleted=0" + 
	  			" order by p.name ", resultSetMapping = "Report1Map"),
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
    private boolean deleted;

	
	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idDocumentType")
	private DocumentType documentType;

	private String documentNumber;

	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idPersonCategory")
	private PersonCategory preferedCategory;
	
	@Transient
	private String selectedMovil;

	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idPersonLocal")
	private PersonLocal preferedLocal;

	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="idPersonSection")
	private PersonSection preferedSection;

	

    @OneToMany(mappedBy = "person" , cascade = CascadeType.PERSIST)
    private List<PersonCategoryAsociation> categories;

    public void addPersonCategory(PersonCategory personCategory, Integer priority){
    	PersonCategoryAsociation association = new PersonCategoryAsociation();
    	 association.setPersonCategory(personCategory);
    	 association.setPerson(this);
    	 association.setIdPersonCategory(personCategory.getId());
    	 if (this.getId()>0)
    		 association.setIdPerson(this.getId());
    	 association.setPriority(priority);
    	 boolean agregar=true;
    	 if(this.categories == null){
    		 this.categories= new ArrayList<>();
    	 }else{
    		 // Me fijo que no exista una asociacion,si la hay tomo la primera
    		 for(PersonCategoryAsociation aux:categories){
    			 if ((aux.getIdPersonCategory()==(association.getIdPersonCategory())) && (aux.getIdPerson()==(association.getIdPerson())) ) 
    				 agregar=false;
    		 }
    	 }
    	 if (agregar) this.categories.add(association);
    }

    
    
	/*
	@ManyToMany
    @JoinTable(name="personpersoncategory",
    	     joinColumns=@JoinColumn(name="idperson", referencedColumnName="ID"),
    	     inverseJoinColumns=@JoinColumn(name="idpersoncategory", referencedColumnName="ID"))
    private List<PersonCategory> categories = new ArrayList<>();
	
	*/

	public List<PersonCategoryAsociation> getCategories() { return categories; }

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


	public void setCategories(List<PersonCategoryAsociation> categories) {
		this.categories = categories;
	}



	public void clearCategories() {
		if ((this.categories!=null) && (this.categories.size()>0)){
			int cant=this.categories.size();
			for(int i=cant-1;i>=0;i--){
				this.categories.remove(i);
			}
		}
	}


	public PersonCategory getPreferedCategory() {
		return preferedCategory;
	}


	public void setPreferedCategory(PersonCategory preferedCategory) {
		this.preferedCategory = preferedCategory;
	}



	public boolean isDeleted() {
		return deleted;
	}



	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}