package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.utils.date.DateUtils;


public abstract class RestrictionServlet extends HttpServlet {

	protected Integer idComponente;
	protected Integer idOpMenu;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
    	idComponente = Integer.parseInt(config.getInitParameter("idComponente"));
    	idOpMenu = Integer.parseInt(config.getInitParameter("idOpMenu"));
	}

	public  void doGet(HttpServletRequest request, HttpServletResponse  response)
    	throws IOException, ServletException {
    	try {
    		response.setContentType("text/html; charset=UTF-8");
    		response.setCharacterEncoding("UTF-8");
	    	doRestriccion(request);

	    	_doProcess(request, response);
    	} catch(Exception e) {
    		throw new ServletException(e.getMessage());
    	}
    }

    public  void doPost(HttpServletRequest request, HttpServletResponse  response)
    	throws IOException, ServletException {
    	try {
    		response.setContentType("text/html; charset=UTF-8");
    		response.setCharacterEncoding("UTF-8");
    		doRestriccion(request);

	    	_doProcess(request, response);
    	} catch(Exception e) {
    		throw new ServletException(e.getMessage());
    	}

    }


    public abstract void _doProcess(HttpServletRequest request, HttpServletResponse response)
    	throws IOException, ServletException;


	protected void doRestriccion(HttpServletRequest request) throws Exception  {
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

  	protected void setSessionObject(HttpServletRequest req, String attrName,Object attrValue) {
	    HttpSession session = req.getSession(false);
	    if (session != null) {
	       	session.setAttribute(attrName,attrValue);
    	}
  	}

	protected void setApplicationObject(String attrName,Object attrValue) {
		getServletContext().setAttribute(attrName,attrValue);
  	}

  	// Obtenemos un objeto del contexto de la aplicación por su nombre.
	protected Object getApplicationObject(String attrName) {
	    return getServletContext().getAttribute(attrName);
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

  	protected Date getDateRequest(HttpServletRequest request, String parameter) {
		try{
			return DateUtils.parseDate(request.getParameter(parameter), Constants.FORMATO_FECHA_HTML5_REGEX,  Constants.FORMATO_FECHA_HTML5);
		}catch(ParseException ex){
			try{
				return DateUtils.parseDate(request.getParameter(parameter), Constants.FORMATO_FECHA_HTML5_REGEX_ALT1,  Constants.FORMATO_FECHA_HTML5_ALT1);
			}catch(ParseException ex1){
				try{
					return DateUtils.parseDate(request.getParameter(parameter), Constants.FORMATO_FECHA_HTML5_REGEX_ALT2,  Constants.FORMATO_FECHA_HTML5_ALT2);
				}catch(ParseException ex2){
					return null;
				}
			}
		}catch(NullPointerException ex){
			return null;
		}
	}



}
