package com.puriarte.convocatoria.persistence;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
      @NamedQuery(name="SelectUser",
      query="SELECT u FROM User u WHERE u.name LIKE :name and u.password = :password"),
    })
@Entity
@Table(name="Users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    
    @Id
    @GeneratedValue
    private String id;
    private String name;
    private String password;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
