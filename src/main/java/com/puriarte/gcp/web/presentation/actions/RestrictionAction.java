package com.puriarte.gcp.web.presentation.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public abstract class RestrictionAction extends Action  {


	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		doRestriccion(request,(SecurityActionMapping)mapping);

		return _execute(mapping,form,request,response);

	}

	public abstract ActionForward _execute(ActionMapping mapping,
											ActionForm form,
											HttpServletRequest request,
											HttpServletResponse response) throws Exception;

	protected void doRestriccion(HttpServletRequest request,SecurityActionMapping mapping) throws Exception  {
	}



	// Obtenemos un objeto de la sesión por su nombre.
  	protected Object getSessionObject(HttpServletRequest req, String attrName) {
	    Object sessionObj = null;
	    HttpSession session = req.getSession(false);
	    if (session != null) {
	       	sessionObj = session.getAttribute(attrName);
    	}
    	return sessionObj;
  	}

	protected void setApplicationObject(String attrName,Object attrValue) {
	    servlet.getServletContext().setAttribute(attrName,attrValue);
  	}

  	// Obtenemos un objeto del contexto de la aplicación por su nombre.
	protected Object getApplicationObject(String attrName) {
	    return servlet.getServletContext().getAttribute(attrName);
  	}


  	// Verifica si el usuario en sesion ya esta autentificado
  	protected boolean isLoggedIn(HttpServletRequest request) {
  		return true;
  	}


  	// Verifica si el usuario en sesion tiene permisos para efectuar una accion
  	protected boolean isAuthorized(HttpServletRequest request,int idComponente,int idOpMenu) {
  		return true;
  	}

}
