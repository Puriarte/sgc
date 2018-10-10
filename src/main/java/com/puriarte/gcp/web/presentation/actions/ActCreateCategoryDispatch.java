package com.puriarte.gcp.web.presentation.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.PropertiesConfiguration;
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
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.Place;

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

		PropertiesConfiguration config = new PropertiesConfiguration(com.puriarte.gcp.web.Constantes.PATHAPPCONFIG);
		String prefix = config.getString("sms.prefix");
		String code = "";
		String attribute1Name = config.getString("dispatch.attribute1");
		String attribute2Name = config.getString("dispatch.attribute2");
		String attribute3Name = config.getString("dispatch.attribute3");

		Logger  logger = Logger.getLogger(ActCreateCategoryDispatch.class.getName());
		if(dynaForm.get("accion").equals("load")){
			try{
				String[] arPersonIds = dynaForm.get("nroDestino").toString().split(",");
				code = Facade.getInstance().selectNextCode();

				ArrayList<PersonMovil> persons = new ArrayList();
				for(String id : arPersonIds){
					PersonMovil person =Facade.getInstance().selectPersonMovil(Integer.parseInt(id));
					persons.add(person);
				}
				dynaForm.set("colPerson", persons);
				dynaForm.set("prefix", prefix);
				dynaForm.set("code", code);
				dynaForm.set("accion", "send");
				
				dynaForm.set("attribute1", attribute1Name);
				dynaForm.set("attribute2", attribute2Name);
				dynaForm.set("attribute3", attribute3Name);
				
				try{
					List<Place> places= new ArrayList<Place>(Facade.getInstance().selectPlaceList(false));
					dynaForm.set("places", places);
				}catch(Exception e ){

				}

				try{
					List<PersonCategory > categories = new ArrayList<PersonCategory>(Facade.getInstance().selectPersonCategoryList(false));
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

		//			String strName = (String) dynaForm.get("name");
					String strMessage = (String) dynaForm.get("detalleIn");
					String strDate = (String) dynaForm.get("eventDate") ;
					String strHour = (String) dynaForm.get("eventHour");
					String strEndHour = (String) dynaForm.get("eventEndHour");

					code = (String) dynaForm.get("code");

					if ((strHour!=null) && (strHour.trim().toString().length()==5)) strHour += ":00";
					else strHour = "00:00:00";
					
					String placeId = (String) dynaForm.get("place");

					Date creationDate = new Date();
					Date scheduledEndDate =null;
				 	Date scheduledDate = com.puriarte.utils.date.DateUtils.parseDate(strDate + " " + strHour , Constants.FORMATO_FECHA_HORA_HTML5_REGEX, Constants.FORMATO_FECHA_HORA_HTML5);

					String xEndDate = "";
					boolean existEndHour = ( (strEndHour!=null) && (!strEndHour.equals("")) );
					if (existEndHour==true){
						if (strEndHour.trim().toString().length()==5) strEndHour += ":00";
						else strEndHour = "00:00:00";
						scheduledEndDate = com.puriarte.utils.date.DateUtils.parseDate(strDate + " " + strEndHour , Constants.FORMATO_FECHA_HORA_HTML5_REGEX, Constants.FORMATO_FECHA_HORA_HTML5);
				 		if (scheduledDate.compareTo(scheduledEndDate)>0 )
				 			scheduledEndDate=new Date(scheduledEndDate.getTime() + (1000 * 60 * 60 * 24));
				 		xEndDate =  " hasta "  + com.puriarte.utils.date.DateUtils.formatDate(scheduledEndDate , "hh:mm a");
				 	}
				 	
					String xDate = com.puriarte.utils.date.DateUtils.formatDate(scheduledDate , "EEEE dd MMMM hh:mm a");

					String xPlace="";
					Place place = null;
					try{
						place = Facade.getInstance().selectPlace(Integer.valueOf(placeId));
						xPlace = place.getName();
					}finally{}
					
					// Atributos extra
					String strAttribute1, strAttribute2, strAttribute3 = "";
					String xAttr1="",xAttr2 ="",xAttr3 ="";
					
					if ((attribute1Name!=null)&&(!attribute1Name.equals(""))){
						if ( dynaForm.get("attribute1")!=null)  {
							strAttribute1= (String) dynaForm.get("attribute1");
							try{
								xAttr1 =  ", " + attribute1Name + " " + strAttribute1.trim() ;
							}finally{}
						}
					}
					
					if ((attribute2Name!=null)&&(!attribute2Name.equals(""))){
						if ( dynaForm.get("attribute2")!=null)  {
							strAttribute2= (String) dynaForm.get("attribute2");
							try{
								xAttr2 =  ", " + attribute2Name + " " + strAttribute2.trim() ;
							}finally{}
						}
					}
					
					if ((attribute3Name!=null)&&(!attribute3Name.equals(""))){
						if ( dynaForm.get("attribute3")!=null)  {
							strAttribute3= (String) dynaForm.get("attribute3");
							try{
								if (attribute3Name.equals(Constants.EXTRA_ATTRIBUTE_NO_LABEL))
									xAttr3 =  ", " + strAttribute3.trim() ;
								else
									xAttr3 =  ", " + attribute3Name + " " + strAttribute3.trim() ;
									
							}finally{}
						}
					}
						
				 	String message  = prefix + " " +  code + " " +  xDate + xEndDate + " " + xPlace + " " + xAttr1 + xAttr2+ xAttr3;
				 	String name=  xDate + xEndDate + " " + xPlace + " " + xAttr1 + xAttr2+ xAttr3;
					
					message = message.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
					name= name.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
					int id = Facade.getInstance().insertDispatch(message, name, code, place, creationDate, scheduledDate , scheduledEndDate, strAttribute3, arPersonIds, arPersonCategory);
					
					if (dynaForm.get("enviarSMS")!=null){
						Facade.getInstance().sendDispatchSMS(id, null, null);
					}
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
