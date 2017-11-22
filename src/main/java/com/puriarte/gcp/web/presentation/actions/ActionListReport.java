package com.puriarte.gcp.web.presentation.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.utils.date.DateUtils;


/**
 * Lista de personas
 *
 * @author Pablo Uriarte
 *
 */
public class ActionListReport extends RestrictionAction {

	public ActionForward _execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		DynaActionForm dynaForm= (DynaActionForm) form;

		Logger  logger = Logger.getLogger(this.getClass().getName());

		String report = request.getParameter("report");
		
		if ((report!=null)&&(report.equals("1")==true)){
			try{
				Calendar calendar = Calendar.getInstance();
				dynaForm.set("fechaHasta", DateUtils.formatDate(calendar.getTime(),   Constants.FORMATO_FECHA_HTML5));
				calendar.add(Calendar.MONTH, -1);
				dynaForm.set("fechaDesde", DateUtils.formatDate(calendar.getTime(),   Constants.FORMATO_FECHA_HTML5));
				dynaForm.set("report","1"); 
				
				//		List<PersonCategory > categories = new ArrayList<PersonCategory>(Facade.getInstance().selectPersonCategoryList());
//						dynaForm.set("categories", categories);
			}catch(Exception e ){
				
			}
		}else{
			dynaForm.set("report","0"); 
		}	

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}
}