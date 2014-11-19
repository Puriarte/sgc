package com.puriarte.gcp.web.presentation.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;

public class ActCreateDispatch extends RestrictionAction {

	public ActionForward _execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		DynaActionForm dynaForm= (DynaActionForm) form;

		Logger  logger = Logger.getLogger(ActCreateDispatch.class.getName());
		if(dynaForm.get("accion").equals("load")){
			try{
				String[] arPersonIds = dynaForm.get("nroDestino").toString().split(",");
				ArrayList<PersonMovil> persons = new ArrayList();
				for(String id : arPersonIds){
					PersonMovil personMovil =Facade.getInstance().selectPersonMovil(Integer.parseInt(id));
					persons.add(personMovil);
				}
				dynaForm.set("colPerson", persons);
				dynaForm.set("accion", "send");

			} catch (Exception e) {
				errors.add("error", new ActionError("dispatch.error.db.ingresar"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward("failure");
			} else {
				return mapping.findForward("load");
			}

		}else if(dynaForm.get("accion").equals("send")){

			try {
				if (dynaForm.get("nroDestino")!=null){

					String[] arPersonIds = dynaForm.get("nroDestino").toString().split(",");

					String strName = (String) dynaForm.get("name");
					String strMensaje = (String) dynaForm.get("detalleIn");

					Date creationDate = new Date();
					Date scheduledDate = new Date();
				//	Date scheduledDate = DateUtils.parseDateTime(strDate, Constants.FORMATO_FECHA_HORA_HTML5_REGEX, Constants.FORMATO_FECHA_HORA_HTML5)
					int id = Facade.getInstance().insertBulkSMS(strMensaje, strName, creationDate, scheduledDate , arPersonIds);

				}

			} catch (Exception e) {
				errors.add("error", new ActionError("dispatch.error.db.ingresar"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward("failure");
			} else {
				messages.add("msg", new ActionMessage("dispatch.insert.ok"));
				saveMessages(request, messages);
				return mapping.findForward("success");
			}

		}else{
			dynaForm.set("accion", "load");
			return mapping.findForward("success");
		}
	}

}
