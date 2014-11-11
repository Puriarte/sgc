package com.puriarte.gcp.web.presentation.actions;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.puriarte.gcp.web.Constantes;


public class ActLogout extends ActionBase {
	

	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

		try {
			
			//Limpio la sesion
			this.cleanSession(request,response);
			
			return mapping.findForward("success");

		} catch (Exception e) {
			return mapping.findForward("failure");
		
		}
	}
	
	
	private void cleanSession(HttpServletRequest  request,HttpServletResponse response){
	    HttpSession session = request.getSession();
	    Enumeration e = session.getAttributeNames();  
	    String sName = null;
	  
	    while(e.hasMoreElements()) { 
	      sName = (String)e.nextElement();
	      session.removeAttribute(sName);
	    }
	    
		//Elimino la cookie de usuario logeado
		Cookie[] cookies = request.getCookies();
        boolean foundCookie = false;

        for(int i = 0; i < cookies.length && !foundCookie; i++) { 
            Cookie cookie = cookies[i];
            if (cookie.getName().equals(Constantes.COOKIE_USUARIO_KEY)) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                foundCookie = true;
            }
        }  
	    
	}
	
	
		
		
}
