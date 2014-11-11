package com.puriarte.gcp.web.presentation.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;


abstract public class ActionBase extends Action {

	
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

	protected Object getApplicationObject(String attrName) {
	    return servlet.getServletContext().getAttribute(attrName);
  	}

  	protected boolean isLoggedIn(HttpServletRequest request) {
  		return true;
  	}

  	protected boolean isAuthorized(HttpServletRequest request,int idComponente,int idOpMenu) {
  		try {
  			return true;
  		} catch(Exception e){
  		}

  		return false;
  	}

}
