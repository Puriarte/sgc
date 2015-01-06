package com.puriarte.gcp.web.presentation.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
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
import com.puriarte.convocatoria.persistence.Place;
import com.puriarte.convocatoria.core.domain.Constants;

public class ActCreateCategoryDispatch extends RestrictionAction {

	public ActionForward _execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		DynaActionForm dynaForm= (DynaActionForm) form;

		Logger  logger = Logger.getLogger(ActCreateCategoryDispatch.class.getName());
		if(dynaForm.get("accion").equals("load")){
			try{
				String[] arPersonIds = dynaForm.get("nroDestino").toString().split(",");

				ArrayList<PersonMovil> persons = new ArrayList();
				for(String id : arPersonIds){
					PersonMovil person =Facade.getInstance().selectPersonMovil(Integer.parseInt(id));
					persons.add(person);
				}
				dynaForm.set("colPerson", persons);
				dynaForm.set("prefix", "ET");
				dynaForm.set("accion", "send");

				try{
					List<Place> places= new ArrayList<Place>(Facade.getInstance().selectPlaceList());
					dynaForm.set("places", places);
				}catch(Exception e ){

				}

				try{
					List<PersonCategory > categories = new ArrayList<PersonCategory>(Facade.getInstance().selectPersonCategoryList());
					dynaForm.set("categories", categories);
				}catch(Exception e ){

				}
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

					HashMap arPersonCategory = getCategoryRequest(request,"personCategory_");
					HashMap arAssignment = getCategoryRequest(request,"assignmentStatus_");

					String[] arPersonIds = dynaForm.get("nroDestino").toString().split(",");

					String strName = (String) dynaForm.get("name");
					String strMensaje = (String) dynaForm.get("detalleIn");
					String strDate = (String) dynaForm.get("eventDate") ;
					String strHour = (String) dynaForm.get("eventHour");
					if ((strHour!=null) && (strHour.trim().toString().length()==5)) strHour += ":00";
					else strHour = "00:00:00";
					
					String placeId = (String) dynaForm.get("place");
					String prefix = (String) dynaForm.get("prefix");

					Date creationDate = new Date();
			 
				 	Date scheduledDate = com.puriarte.utils.date.DateUtils.parseDate(strDate + " " + strHour , Constants.FORMATO_FECHA_HORA_HTML5_REGEX, Constants.FORMATO_FECHA_HORA_HTML5);
				 	
					strName = prefix + " " +  com.puriarte.utils.date.DateUtils.formatDate(scheduledDate , "dd/MM/yyyy hh:mm");
					
					Place place = null;
					try{
						place = Facade.getInstance().selectPlace(Integer.valueOf(placeId));
						strName +=  " " + place.getName() + " " ;
					}finally{}
					
					strMensaje = strName;
					int id = Facade.getInstance().insertDispatch(strMensaje, strName, place, creationDate, scheduledDate , arPersonIds, arPersonCategory);

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


	private HashMap getCategoryRequest(HttpServletRequest request,String data) throws Exception {

  		Enumeration enume = request.getParameterNames();
  		HashMap hmValores = new HashMap();

  		while(enume.hasMoreElements()) {
  			String paramName = (String)enume.nextElement();
  			if(paramName.startsWith(data)) {
  				try{
  					Integer id= Integer.parseInt(paramName.substring(data.length(), paramName.length()));
  					Integer valor= Integer.parseInt(request.getParameter(paramName));
	  				if(id != null) {
	  					hmValores.put(id,valor);
	  				}
  				}catch(Exception e){
  				}
  			}
  		}

  		return hmValores;
  	}

}
