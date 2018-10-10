package com.puriarte.gcp.web.presentation.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.core.exceptions.PlaceException;
import com.puriarte.convocatoria.persistence.Place;


public class ActUpdatePlace extends RestrictionAction {

	public ActionForward _execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		DynaActionForm dynaForm= (DynaActionForm) form;

		Logger  logger = Logger.getLogger(ActUpdatePlace.class.getName());

		try {
			if(dynaForm.get("accion").equals("load")){
				try{
					// NUEVO
					if ((dynaForm.get("ID")==null) || (dynaForm.get("ID").equals(0)==true)|| (dynaForm.get("ID").equals(""))){
						dynaForm.set("NOMBRE", "");
						dynaForm.set("DIRECCION", "");
						dynaForm.set("TELEFONO", "");

					}else{
						Place place = Facade.getInstance().selectPlace(new Integer(dynaForm.get("ID").toString()));
						
						dynaForm.set("ID", place.getId().toString()); 
						dynaForm.set("NOMBRE", place.getName());
						dynaForm.set("DIRECCION", place.getAddress());
						dynaForm.set("TELEFONO", place.getPhone());
						if (place.isDeleted()) dynaForm.set("DELETED", "on");
						
					}					
					dynaForm.set("accion", "send");

				} catch (Exception e) {
					errors.add("error", new ActionMessage("place.error.db.ingresar"));
				}
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					return mapping.findForward("failure");
				} else {
					return mapping.findForward("load");
				}
				
			}else{
				if ( (dynaForm.get("ID")!=null) && (!dynaForm.get("ID").toString().trim().equals(""))) {

					Place place= Facade.getInstance().selectPlace(Integer.parseInt((String) dynaForm.get("ID")));

					if ((dynaForm.get("NOMBRE")!=null) && (!dynaForm.get("NOMBRE").equals(""))){
						place.setName((String) dynaForm.get("NOMBRE"));
						if (dynaForm.get("DIRECCION")!=null) place.setAddress((String) dynaForm.get("DIRECCION"));
						if (dynaForm.get("TELEFONO")!=null) place.setPhone((String) dynaForm.get("TELEFONO"));
						place.setDeleted(((String) dynaForm.get("DELETED")).toUpperCase().equals("ON"));
						
						Facade.getInstance().updatePlace(place);
					}else{
						errors.add("error", new ActionMessage("place.error.name.required"));
					}

				}else{
					Place place = new Place();
					if ((dynaForm.get("NOMBRE")!=null) && (!dynaForm.get("NOMBRE").equals(""))){
						place.setName((String) dynaForm.get("NOMBRE"));
						if (dynaForm.get("DIRECCION")!=null) place.setAddress((String) dynaForm.get("DIRECCION"));
						if (dynaForm.get("TELEFONO")!=null) place.setPhone((String) dynaForm.get("TELEFONO"));
						place.setDeleted(false);
						Facade.getInstance().insertPlace(place);
					}else{
						errors.add("error", new ActionMessage("place.error.name.required"));
					}
				}
			}

		} catch (PlaceException pe) {
			errors.add("error", new ActionMessage(pe.getMessage()));
		} catch (Exception e) {
			errors.add("error", new ActionMessage("place.error.db.ingresar"));
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} else {
			messages.add("msg", new ActionMessage("place.update.ok"));
			saveMessages(request, messages);
			return mapping.findForward("success");
		}
	}

}
