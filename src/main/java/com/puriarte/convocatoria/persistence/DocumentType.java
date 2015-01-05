package com.puriarte.convocatoria.persistence;

import java.io.Serializable;
import javax.persistence.*;

@Entity

@NamedQueries({
	  @NamedQuery(name="SelectDocumentType",
    query="SELECT dt FROM DocumentType dt WHERE dt.id = :id"),
	})
public class DocumentType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;
	private String name;

	public DocumentType() {
	}

	public DocumentType(int id) {
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}