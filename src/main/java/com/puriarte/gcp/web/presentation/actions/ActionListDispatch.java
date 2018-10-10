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
import com.puriarte.convocatoria.persistence.DispatchStatus;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.utils.date.DateUtils;


/**
 * Lista de personas
 *
 * @author Pablo Uriarte
 *
 */
public class ActionListDispatch extends RestrictionAction {

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

		Calendar calendar = Calendar.getInstance();

		dynaForm.set("fechaDesde", DateUtils.formatDate(calendar.getTime(),   Constants.FORMATO_FECHA_HTML5));
		dynaForm.set("fechaHasta", DateUtils.formatDate(calendar.getTime(),   Constants.FORMATO_FECHA_HTML5));

		try{
			List<PersonCategory > categories = new ArrayList<PersonCategory>(Facade.getInstance().selectPersonCategoryList(false));
			dynaForm.set("categories", categories);
		}catch(Exception e ){

		}

		try{
			List<DispatchStatus > colDispatchStatus = new ArrayList<DispatchStatus>(Facade.getInstance().selectDispatchStatusList());
			dynaForm.set("colDispatchStatus", colDispatchStatus);
		}catch(Exception e ){

		}
		
		// Por defecto lista las convocatorias activas
		dynaForm.set("dispatchStatus",Constants.DISPATCH_STATUS_ACTIVE);
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} else {
			dynaForm.set("accion", "modificar");
			return mapping.findForward("success");
		}
	}
}