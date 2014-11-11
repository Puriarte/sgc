package com.puriarte.gcp.web.presentation.actions;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.User;
import com.puriarte.gcp.web.Constantes;


/**
 * @version 	1.0
 * @author
 */
public class ActLogin extends ActionBase {

	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		DynaActionForm usuarioForm= (DynaActionForm) form;

		ActionErrors errors = new ActionErrors();
		Logger  logger = Logger.getLogger(ActLogin.class.getName());

		try {

			System.out.print("TRATO DE LOGUEAR");
			
			logger.error("TRATO DE LOG");
			usuarioForm.set("primeraVez", new Boolean(false));

			if ((usuarioForm.get("nombre") == null) || (usuarioForm.get("nombre").toString().length() == 0)) {
				errors.add("error", new ActionError("login.error.nombre.requerido"));
			}
			if ((usuarioForm.get("password") == null) || (usuarioForm.get("password").toString().length() == 0)) {
				errors.add("error", new ActionError("login.error.password.requerido"));
			}

			if(errors.isEmpty()) {
				User usuario = Facade.getInstance().selectUser(usuarioForm.get("nombre").toString(), usuarioForm.get("password").toString());
				if (usuario == null){
					errors.add("error", new ActionError("login.error.usuario.inexistente"));
				} else {

//					//Genero una cookie con los datos del usuario logeado
//					Cookie cookie = new Cookie(Constantes.COOKIE_USUARIO_KEY, usuario.getId() + Constantes.COOKIE_SEPARADOR + usuario.getPassword());
//					//Cuando cierre el explorador
//					//cookie.setMaxAge(24*60*60);
//					cookie.setMaxAge(-1);
//					cookie.setPath("/");
//					response.addCookie(cookie);
				}

			}

		} catch (Exception e) {
			errors.add("error", new ActionError("login.error.db"));
			saveErrors(request, errors);
			logger.error(e.getStackTrace());

		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}
}