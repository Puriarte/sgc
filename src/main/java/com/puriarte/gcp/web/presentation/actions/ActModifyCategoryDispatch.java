package com.puriarte.gcp.web.presentation.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.Assignment;
import com.puriarte.convocatoria.persistence.AssignmentStatus;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.DispatchStatus;
import com.puriarte.convocatoria.persistence.Job;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.Place;
import com.puriarte.utils.date.DateUtils;

public class ActModifyCategoryDispatch extends RestrictionAction {

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
		String attribute1Name = config.getString("dispatch.attribute1");
		String attribute2Name = config.getString("dispatch.attribute2");
		String attribute3Name = config.getString("dispatch.attribute3");

		Logger  logger = Logger.getLogger(ActModifyCategoryDispatch.class.getName());
		if(dynaForm.get("accion").equals("load")){
			try{
				String[] arDispatchIds = dynaForm.get("nroDestino").toString().split(",");
				if ( arDispatchIds.length>0){
					try{
						Integer id = new Integer(arDispatchIds[0]);
						
						Dispatch dispatch = Facade.getInstance().selectDispatch(id);

						String code = (dispatch.getCode()==null)?"":dispatch.getCode();

						List<DispatchStatus > dispatchsStatus = new ArrayList<DispatchStatus>(Facade.getInstance().selectDispatchStatusList());
						
						ArrayList<Assignment> assignments = new ArrayList<Assignment>();
						for(Job job : dispatch.getJobList()){
//							//TODO: En esta versión se está trabajando con una sola asignación por puesto. 							
							assignments.add(job.getAssignmentList().get(0));
						}
//
//						
//						dispatch.getJobList()
//	
//						for(String id : arPersonIds){
//							PersonMovil person =Facade.getInstance().selectPersonMovil(Integer.parseInt(id));
//							persons.add(person);
//						}
						
//						dynaForm.set("eventDate", dispatch.getScheduledDate().getDate());
//						dynaForm.set("eventHour", dispatch.getScheduledDate().getTime());
//	
						dynaForm.set("dispatch", dispatch);
						dynaForm.set("colAssignment", assignments);
						dynaForm.set("colDispatchStatus", dispatchsStatus);
						
						dynaForm.set("prefix", prefix);
						dynaForm.set("code", code);
						dynaForm.set("accion", "send");
						
						dynaForm.set("attribute1", attribute1Name);
						dynaForm.set("attribute2", attribute2Name);
						dynaForm.set("attribute3", attribute3Name);
						
						if ((attribute3Name!=null)&&(!attribute3Name.equals(""))){
							dynaForm.set("attribute3", dispatch.getAttribute3());
						}
						
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
					}catch(Exception e ){
						logger.error(e.getMessage(), null);
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), null);
				errors.add("error", new ActionMessage ("dispatch.error.db.ingresar"));
			}
			if (!errors.isEmpty()) {
				saveMessages(request, errors);
				return mapping.findForward("failure");
			} else {
				return mapping.findForward("load");
			}
		}else if(dynaForm.get("accion").equals("addAssignmentRow")){
			
			if(dynaForm.get("colAssignment")==null)dynaForm.set("colAssignment", new ArrayList<Assignment>());
			Assignment assignment = new Assignment();
			Job job = new Job();
			job.setCategory(new PersonCategory());
			assignment.setJob(job);
			AssignmentStatus as = new  AssignmentStatus();
			assignment.setStatus(as);

			((ArrayList) dynaForm.get("colAssignment")).add(assignment);
			
			dynaForm.set("accion", "send");
			return mapping.findForward("load");

		}else if(dynaForm.get("accion").equals("send")){

			try {
				if (dynaForm.get("nroDestino")!=null){

					HashMap<Integer, Object> arPersonCategory = getCategoryRequest(request,"personCategory_");
					HashMap<Integer, Object> arStatusIds = getCategoryRequest(request,"assignmentStatus_");
					HashMap<Integer, Object> personIds = getCategoryRequest(request,"personMovil_");
					HashMap<Integer, Object> assignmentIds = getCategoryRequest(request,"assignment_");
					HashMap<Integer, Object> forwardIds = getCategoryRequest(request,"forward_");

//					String[] arPersonIds = dynaForm.get("nroDestino").toString().split(",");
//					String[] arAssignmentIds = dynaForm.get("assignment").toString().split(",");
//					String[] arStatusIds = dynaForm.get("assignmentStatus").toString().split(",");
					

					String strName = (String) dynaForm.get("name");
					String strMensaje = (String) dynaForm.get("detalleIn");
					String strDate = (String) dynaForm.get("eventDate") ;
					String strHour = (String) dynaForm.get("eventHour");
					String strEndHour = (String) dynaForm.get("eventEndHour");
					Integer dispatchStatus = 0;
					try{
						dispatchStatus = Integer.parseInt((String) dynaForm.get("dispatchStatus"));
					}catch(Exception e){
						
					}
 					
					String placeId = (String) dynaForm.get("place");
					int id = (int) dynaForm.get("dispatchId");
					Date creationDate = new Date();
					Date scheduledEndDate = null;
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
					String strAttribute1, strAttribute2, strAttribute3;
					String xAttr1="",xAttr2 ="", xAttr3 ="";
					
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
					
					String code = (String) dynaForm.get("code");
				
				 	String message  = prefix + " " + code + " " + xDate + xEndDate + " " + xPlace + " " + xAttr1 + xAttr2 + xAttr3;
				 	String name=  xDate + xEndDate + " " + xPlace + " " + xAttr1 + xAttr2  + xAttr3;
				
					message = message.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
					name= name.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
					
//					strMensaje = strName.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
					Facade.getInstance().updateDispatch(id, message, name, place, creationDate, scheduledDate , dispatchStatus,
							 personIds, arPersonCategory, arStatusIds, assignmentIds, forwardIds);

					HashMap<Integer, Object> arPersonCategory1 = new HashMap<Integer, Object>();
					
					String aux="";
					Iterator it = arPersonCategory.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry entry = (Entry) it.next();
						int categoryKey = (int) entry.getKey();
						int categoryValue = (Integer) entry.getValue();
						int assignmentId= (int) assignmentIds.get(categoryKey);
						if ((forwardIds.get(categoryKey)!=null) && forwardIds.get(categoryKey).equals("on")) {
							Assignment as = Facade.getInstance().getAssignment(assignmentId);
							aux=aux+as.getPersonMovil().getId()+",";
							arPersonCategory1.put(as.getPersonMovil().getId(), categoryValue);
						}
					}
					String[] arPersonIds = aux.split(",");	
					
					// Envio los mensajes 
					Facade.getInstance().sendDispatchSMS(id, arPersonIds, arPersonCategory1 );
				}

			} catch (Exception e) {
				errors.add("error", new ActionMessage ("dispatch.error.db.ingresar"));
			}

			if (!errors.isEmpty()) {
				saveMessages(request, errors);
				return mapping.findForward("failure");
			} else {
				messages.add("msg", new ActionMessage("dispatch.insert.ok"));
				saveMessages(request, messages);
				return mapping.findForward("resultado");
			}
		}else{
			dynaForm.set("accion", "load");
			return mapping.findForward("resultado");
		}
	}


	/**
	 * Carga las categorías seleccionadas en la página web
	 * 
	 * @param request
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private HashMap<Integer, Object> getCategoryRequest(HttpServletRequest request,String data) throws Exception {

  		Enumeration<String> enume = request.getParameterNames();
  		HashMap<Integer, Object> hmValores = new HashMap<Integer, Object>();

  		while(enume.hasMoreElements()) {
  			String paramName = (String)enume.nextElement();
  			if(paramName.startsWith(data)) {
  				try{
  					Integer id= Integer.parseInt(paramName.substring(data.length(), paramName.length()));
  					try{
  						Integer valor= Integer.parseInt(request.getParameter(paramName));
  						if(id != null) hmValores.put(id,valor);
  					}catch(Exception e ){
  						String valor= request.getParameter(paramName);
  						if(id != null) hmValores.put(id,valor);
	  				}
  				}catch(Exception e){
  				}
  			}
  		}
  		return hmValores;
  	}

}
