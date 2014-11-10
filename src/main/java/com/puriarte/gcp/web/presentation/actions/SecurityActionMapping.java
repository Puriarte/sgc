package com.puriarte.gcp.web.presentation.actions;

import org.apache.struts.action.ActionMapping;

public class SecurityActionMapping extends ActionMapping {

	private static final long serialVersionUID = 2L;
	private int idComponente;
	private int idOpMenu;

	/**
	 * @return  Returns the idComponente.
	 * @uml.property  name="idComponente"
	 */
	public int getIdComponente() {
		return idComponente;
	}
	/**
	 * @param idComponente  The idComponente to set.
	 * @uml.property  name="idComponente"
	 */
	public void setIdComponente(int idComponente) {
		this.idComponente = idComponente;
	}
	/**
	 * @return  Returns the idOpMenu.
	 * @uml.property  name="idOpMenu"
	 */
	public int getIdOpMenu() {
		return idOpMenu;
	}
	/**
	 * @param idOpMenu  The idOpMenu to set.
	 * @uml.property  name="idOpMenu"
	 */
	public void setIdOpMenu(int idOpMenu) {
		this.idOpMenu = idOpMenu;
	}



}