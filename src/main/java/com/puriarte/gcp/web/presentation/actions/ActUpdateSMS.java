package com.puriarte.gcp.web.presentation.actions;

import java.math.BigDecimal;
import java.util.Date;

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
import com.puriarte.convocatoria.core.exceptions.MovilException;
import com.puriarte.convocatoria.core.exceptions.PersonException;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.DocumentType;
import com.puriarte.convocatoria.persistence.Movil;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.Place;
import com.puriarte.convocatoria.persistence.SMS;

public class ActUpdateSMS extends RestrictionAction {

	public ActionForward _execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		DynaActionForm dynaForm= (DynaActionForm) form;

		Logger  logger = Logger.getLogger(ActUpdateSMS.class.getName());

		try {
			String dispatch= null;

			if ( (dynaForm.get("ID")!=null) && (!dynaForm.get("ID").toString().trim().equals(""))) {
				
				SMS sms= Facade.getInstance().selectSMS(new Integer(dynaForm.get("ID").toString()));

				if (dynaForm.get("CONVOCATORIA")!=null) {
					dispatch = (String) dynaForm.get("CONVOCATORIA");				
					Facade.getInstance().updateSMSAssignment(sms, Integer.parseInt(dispatch));
				}else {
					Facade.getInstance().removeSMSAssignment(sms);
				}
			}

		} catch (Exception e) {
			errors.add("error", new ActionError("person.error.db.ingresar"));
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} else {
			messages.add("msg", new ActionMessage("person.update.ok"));
			saveMessages(request, messages);
			return mapping.findForward("success");
		}
	}

}
