package com.puriarte.gcp.web.presentation.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
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
import com.puriarte.convocatoria.persistence.Place;
import com.puriarte.utils.date.DateUtils;


/**
 * Lista de personas
 *
 * @author Pablo Uriarte
 *
 */
public class ActionListPlace extends RestrictionAction {

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

		try{
			HashMap colStatus= new HashMap<Integer, String>();
			colStatus.put(0,"Activo");
			colStatus.put(1,"Todos");

			dynaForm.set("colPlaceStatus", colStatus);
		}catch(Exception e ){

		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} else {
			dynaForm.set("accion", "modificar");
			return mapping.findForward("success");
		}
	}
}