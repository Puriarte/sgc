package com.puriarte.gcp.web.presentation.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.actions.DispatchAction;


public abstract class RestrictionActionDispatch extends DispatchAction  {

	protected void doRestriccion(HttpServletRequest request,SecurityActionMapping mapping) throws Exception  {
	}

  	protected Object getSessionObject(HttpServletRequest req, String attrName) {
	    Object sessionObj = null;
	    HttpSession session = req.getSession(false);
	    if (session != null) {
	       	sessionObj = session.getAttribute(attrName);
    	}
    	return sessionObj;
  	}

  	protected void setSessionObject(HttpServletRequest req, String attrName,Object attrValue) {
	    HttpSession session = req.getSession(false);
	    if (session != null) {
	       	session.setAttribute(attrName,attrValue);
    	}
  	}

	protected void setApplicationObject(String attrName,Object attrValue) {
	    servlet.getServletContext().setAttribute(attrName,attrValue);
  	}

  	// Obtenemos un objeto del contexto de la aplicacion por su nombre.
	protected Object getApplicationObject(String attrName) {
	    return servlet.getServletContext().getAttribute(attrName);
  	}
  	// Verifica si el usuario en sesion ya esta autentificado
  	protected boolean isLoggedIn(HttpServletRequest request) {
  		return true;
  	}

  	// Verifica si el usuario en sesion tiene permisos para efectuar una accion
  	protected boolean isAuthorized(HttpServletRequest request,int idComponente,int idOpMenu) {
  		try {
  			return true;

  		} catch(Exception e){
  		}

  		return false;
  	}

}