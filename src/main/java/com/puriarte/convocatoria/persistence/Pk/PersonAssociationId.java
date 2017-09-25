package com.puriarte.convocatoria.persistence.Pk;

import java.io.Serializable;

public class PersonAssociationId implements Serializable {
	
    private int idPerson;
    private int idPersonCategory;

    public int hashCode() {
		return (int)(idPerson + idPersonCategory);
	}

	public boolean equals(Object object) {
		if (object instanceof PersonAssociationId) {
			PersonAssociationId otherId = (PersonAssociationId) object;
			return (otherId.idPerson == this.idPerson) && (otherId.idPersonCategory == this.idPersonCategory);
		}
		return false;
	}
    
}
