package com.puriarte.gcp.web.presentation.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.Movil;
import com.puriarte.convocatoria.persistence.PersonMovil;

public class ActIngresarSMS extends RestrictionAction {

	public ActionForward _execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		DynaActionForm usuarioForm= (DynaActionForm) form;
		Logger  logger = Logger.getLogger(ActIngresarSMS.class.getName());

		try {
			if (usuarioForm.get("nroDestino")!=null){
				// Obtengo el movil
				PersonMovil movil = Facade.getInstance().selectPersonMovil(usuarioForm.get("nroDestino").toString(), Constants.MOVIL_STATUS_ACTIVE);
				if (movil==null){
					errors.add("error", new ActionError("sms.error.movil.not.active"));
				}else{
					Facade.getInstance().insertSMSOutcome(movil, usuarioForm.get("detalleIn").toString());
				}
			}

		} catch (Exception e) {
			errors.add("error", new ActionError("sms.error.db.ingresar"));
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} else {
			messages.add("msg", new ActionMessage("sms.ingresar.ok"));
			saveMessages(request, messages);
			return mapping.findForward("success");
		}
	}
}