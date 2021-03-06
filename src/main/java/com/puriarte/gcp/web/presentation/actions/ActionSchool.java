package com.puriarte.gcp.web.presentation.actions;

import java.util.Calendar;

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
import com.puriarte.utils.date.DateUtils;

/**
 *
 * @author Pablo Uriarte
 *
 */
public class ActionSchool extends RestrictionAction {

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
		dynaForm.set("fechaHasta", DateUtils.formatDate(calendar.getTime(),   Constants.FORMATO_FECHA_HTML5));
		calendar.add(Calendar.DAY_OF_MONTH, -90);
		dynaForm.set("fechaDesde", DateUtils.formatDate(calendar.getTime(),   Constants.FORMATO_FECHA_HTML5));

		dynaForm.set("idPerson", dynaForm.get("idPerson"));

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} else {
			dynaForm.set("accion", "modificar");
			return mapping.findForward("success");
		}
	}
}