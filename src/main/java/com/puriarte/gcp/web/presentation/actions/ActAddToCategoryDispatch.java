package com.puriarte.gcp.web.presentation.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.PropertiesConfiguration;
//import org.apache.commons.lang.time.DateUtils;
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
import com.puriarte.convocatoria.persistence.Assignment;
import com.puriarte.convocatoria.persistence.AssignmentStatus;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.DispatchStatus;
import com.puriarte.convocatoria.persistence.Job;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.Place;
import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.utils.date.DateUtils;

public class ActAddToCategoryDispatch extends RestrictionAction {

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
		
		Logger  logger = Logger.getLogger(ActAddToCategoryDispatch.class.getName());
		if(dynaForm.get("accion").equals("load")){
			try{
/*				String[] arPersonIds = dynaForm.get("nroDestino").toString().split(",");
				code = Facade.getInstance().selectNextCode();

				ArrayList<PersonMovil> persons = new ArrayList();
				for(String id : arPersonIds){
					PersonMovil person =Facade.getInstance().selectPersonMovil(Integer.parseInt(id));
					persons.add(person);
				}
				dynaForm.set("colPerson", persons);
				dynaForm.set("prefix", prefix);
				dynaForm.set("code", code);
	*/			dynaForm.set("accion", "addToDispatch");
				
		/*		dynaForm.set("attribute1", attribute1Name);
				dynaForm.set("attribute2", attribute2Name);
			*/	
				try{
					List<Dispatch> dispatches =   new ArrayList<Dispatch>(Facade.getInstance().selectDispatchList(Constants.DISPATCH_STATUS_ACTIVE, "dispatchStatus.name", true, 0, 100));
					dynaForm.set("dispatches",dispatches);
				}catch(Exception e ){

				}
/*
				try{
					List<PersonCategory > categories = new ArrayList<PersonCategory>(Facade.getInstance().selectPersonCategoryList());
					dynaForm.set("categories", categories);
				}catch(Exception e ){
				}
*/
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

					Integer idDispatch = (Integer) dynaForm.get("dispatchId") ;
					Dispatch dispatch = Facade.getInstance().selectDispatch( idDispatch);

					HashMap arPersonCategory = getCategoryRequest(request,"personCategory_");
					String[] arPersonIds = dynaForm.get("nroDestino").toString().split(",");

					// QUITO LAS PERSONAS REPETIDAS
/*					ArrayList<Integer> personas = dispatch.getPersonIds();
					String aux="";
					for (String personId:arPersonIds){
						if (personas.contains(personId)) {
							aux=aux+personId+",";
						}
					}
					arPersonIds = aux.split(",");
	*/				
					
					String xEndDate = "";
					if (dispatch.getScheduledEndDate()!=null){
				 		xEndDate =  " hasta "  + com.puriarte.utils.date.DateUtils.formatDate(dispatch.getScheduledEndDate() , "hh:mm a");
				 	}
				 	
					String xDate = com.puriarte.utils.date.DateUtils.formatDate(dispatch.getScheduledDate() , "EEEE dd MMMM hh:mm a");

					String xPlace="";
					Place place = null;
					xPlace = dispatch.getPlace().getName();
					
				 	String message  = prefix + " " +  dispatch.getCode() + " " +  xDate + xEndDate + " " + xPlace ;

					Facade.getInstance().addToDispatch(dispatch , message, new Date(), arPersonIds, arPersonCategory);
					
					if (dynaForm.get("enviarSMS")!=null){
						Facade.getInstance().sendDispatchSMS(dispatch.getId(), arPersonIds, arPersonCategory );
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
		}else if(dynaForm.get("accion").equals("addToDispatch")){
			try{
				String[] arPersonIds = dynaForm.get("nroDestino").toString().split(",");
				ArrayList<PersonMovil> persons = new ArrayList();
				for(String id : arPersonIds){
					PersonMovil person =Facade.getInstance().selectPersonMovil(Integer.parseInt(id));
					persons.add(person);
				}
				dynaForm.set("colPerson", persons);
		
				Integer idDispatch = (Integer) dynaForm.get("dispatchId") ;
				Dispatch dispatch = Facade.getInstance().selectDispatch( idDispatch);

				String code = (dispatch.getCode()==null)?"":dispatch.getCode();

				List<DispatchStatus > dispatchsStatus = new ArrayList<DispatchStatus>(Facade.getInstance().selectDispatchStatusList());
				
				ArrayList<Assignment> assignments = new ArrayList<Assignment>();
				for(Job job : dispatch.getJobList()){
//					//TODO: En esta versión se está trabajando con una sola asignación por puesto. 							
					assignments.add(job.getAssignmentList().get(0));
				}
 
				dynaForm.set("dispatchId", idDispatch);
				dynaForm.set("dispatch", dispatch);
				dynaForm.set("colAssignment", assignments);
				dynaForm.set("colDispatchStatus", dispatchsStatus);
				
				dynaForm.set("prefix", prefix);
				dynaForm.set("code", code);
				dynaForm.set("accion", "send");
				
				if (dispatch.getScheduledDate()!=null){
					dynaForm.set("eventDate", DateUtils.formatDate(dispatch.getScheduledDate(), Constants.FORMATO_FECHA_HTML5));
					dynaForm.set("eventDateAlt2", DateUtils.formatDate(dispatch.getScheduledDate(), Constants.FORMATO_FECHA_HTML5_ALT2));
					dynaForm.set("eventHour", DateUtils.formatDate(dispatch.getScheduledDate(), Constants.FORMATO_HORA_HTML5));
				}
				
				if (dispatch.getScheduledEndDate()!=null){
					dynaForm.set("eventEndHour", DateUtils.formatDate(dispatch.getScheduledEndDate(), Constants.FORMATO_HORA_HTML5));
				}
				
				try{
					List<PersonCategory > categories = new ArrayList<PersonCategory>(Facade.getInstance().selectPersonCategoryList(false));
					dynaForm.set("categories", categories);
				}catch(Exception e ){

				}
										
				try{
					List<AssignmentStatus > assignmentsStatus = new ArrayList<AssignmentStatus>(Facade.getInstance().selectAssingmentStatusList());
					dynaForm.set("assignmentsStatus", assignmentsStatus);
				}catch(Exception e ){

				}
				
				try{
					List<PersonMovil> personsMovil= new ArrayList<PersonMovil>(Facade.getInstance().selectPersonMovilObjectList("", 0, 100));
					dynaForm.set("personsMovil", personsMovil);
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
