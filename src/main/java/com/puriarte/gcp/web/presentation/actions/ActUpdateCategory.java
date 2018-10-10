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

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.core.exceptions.PersonCategoryException;
import com.puriarte.convocatoria.persistence.PersonCategory;

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
			if(dynaForm.get("accion").equals("load")){
				try{
					// NUEVO
					if ((dynaForm.get("ID")==null) || (dynaForm.get("ID").equals(0)==true)|| (dynaForm.get("ID").equals(""))){
						dynaForm.set("NOMBRE", "");
					}else{
						PersonCategory category = Facade.getInstance().selectPersonCategory(Integer.parseInt((String) dynaForm.get("ID")));
						
						dynaForm.set("ID", Integer.toString(category.getId())); 
						dynaForm.set("NOMBRE", category.getName());
						if (category.isDeleted()) dynaForm.set("DELETED", "on");
						
					}					
					dynaForm.set("accion", "send");

				} catch (Exception e) {
					errors.add("error", new ActionError("category.error.db.ingresar"));
				}
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					return mapping.findForward("failure");
				} else {
					return mapping.findForward("load");
				}
				
			}else{
				if ( (dynaForm.get("ID")!=null) && (!dynaForm.get("ID").toString().trim().equals(""))) {

					PersonCategory category = Facade.getInstance().selectPersonCategory(Integer.parseInt((String) dynaForm.get("ID")));

					if ((dynaForm.get("NOMBRE")!=null) && (!dynaForm.get("NOMBRE").equals(""))){
						category.setName((String) dynaForm.get("NOMBRE"));
						category.setDeleted(((String) dynaForm.get("DELETED")).toUpperCase().equals("ON"));

						Facade.getInstance().updatePersonCategory(category);
					}else{
						errors.add("error", new ActionError("category.error.nombre.vacio"));
					}
						
				}else{

					PersonCategory category = new PersonCategory();
					if ((dynaForm.get("NOMBRE")!=null) && (!dynaForm.get("NOMBRE").equals(""))){
						category.setName((String) dynaForm.get("NOMBRE"));
						Facade.getInstance().insertPersonCategory(category);
					}else{
						errors.add("error", new ActionError("category.error.nombre.vacio"));
					}
				}
			}

		} catch (PersonCategoryException pe) {
			errors.add("error", new ActionError(pe.getMessage()));
		} catch (Exception e) {
			errors.add("error", new ActionError("category.error.db.ingresar"));
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} else {
			messages.add("msg", new ActionMessage("category.update.ok"));
			saveMessages(request, messages);
			return mapping.findForward("success");
		}
	}

}
