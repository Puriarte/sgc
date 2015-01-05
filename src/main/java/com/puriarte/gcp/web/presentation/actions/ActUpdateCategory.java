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
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.Movil;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.SMS;

public class ActUpdateCategory extends RestrictionAction {

	public ActionForward _execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		DynaActionForm dynaForm= (DynaActionForm) form;

		Logger  logger = Logger.getLogger(ActUpdateCategory.class.getName());

		try {

			if ( (dynaForm.get("ID")!=null) && (!dynaForm.get("ID").toString().trim().equals(""))) {

				PersonCategory category = Facade.getInstance().selectPersonCategory(Integer.parseInt((String) dynaForm.get("ID")));

				if (dynaForm.get("NOMBRE")!=null) category.setName((String) dynaForm.get("NOMBRE"));

				Facade.getInstance().updatePersonCategory(category);
			}else{

				PersonCategory category = new PersonCategory();
				if (dynaForm.get("NOMBRE")!=null) {
					category.setName((String) dynaForm.get("NOMBRE"));
					Facade.getInstance().insertPersonCategory(category);
				}else{
					errors.add("error", new ActionError("person.error.db.ingresar"));
				}
			}

		} catch (Exception e) {
			errors.add("error", new ActionError("person.error.db.ingresar"));
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} else {
//			messages.add("msg", new ActionMessage("person.update.ok"));
//			saveMessages(request, messages);
			return mapping.findForward("success");
		}
	}

}
