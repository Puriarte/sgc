package com.puriarte.gcp.web.presentation.actions;

import java.math.BigDecimal;
import java.util.Date;

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

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.core.exceptions.MovilException;
import com.puriarte.convocatoria.core.exceptions.PersonException;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.DocumentType;
import com.puriarte.convocatoria.persistence.Movil;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.Place;
import com.puriarte.convocatoria.persistence.SMS;

public class ActUpdatePerson extends RestrictionAction {

	public ActionForward _execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		DynaActionForm dynaForm= (DynaActionForm) form;

		Logger  logger = Logger.getLogger(ActUpdatePerson.class.getName());

		try {
			String movilNumber= null;

			if ( (dynaForm.get("ID")!=null) && (!dynaForm.get("ID").toString().trim().equals(""))) {
				
				PersonMovil person = Facade.getInstance().selectPersonMovil(new Integer(dynaForm.get("ID").toString()));

	//			Person person = Facade.getInstance().selectPerson(new Integer(dynaForm.get("ID").toString()));
				if (dynaForm.get("SOBRENOMBRE")!=null) person.getPerson().setNickname((String) dynaForm.get("SOBRENOMBRE"));
				if (dynaForm.get("NOMBRE")!=null) person.getPerson().setName((String) dynaForm.get("NOMBRE"));
				if ((dynaForm.get("NRO DOCUMENTO")!=null) && (!dynaForm.get("NRO DOCUMENTO").equals(""))) person.getPerson().setDocumentNumber((String) dynaForm.get("NRO DOCUMENTO"));
				if (dynaForm.get("CATEGORIA")!=null) {
					PersonCategory pc = Facade.getInstance().selectPersonCategory(Integer.parseInt((String) dynaForm.get("CATEGORIA")));
					person.getPerson().setCategory(pc);
				}
				if ((!dynaForm.get("ORDEN PRELACION").equals("")) && (dynaForm.get("ORDEN PRELACION")!="")) person.getPerson().setPriority(Integer.parseInt((String) dynaForm.get("ORDEN PRELACION")));
				if (dynaForm.get("NUMERO")!=null) movilNumber = (String) dynaForm.get("NUMERO");

				Facade.getInstance().updatePersonMovil(person, movilNumber);



			}else{
				Person person = new Person();
				if (dynaForm.get("SOBRENOMBRE")!=null) person.setNickname((String) dynaForm.get("SOBRENOMBRE"));
				if (dynaForm.get("NOMBRE")!=null) person.setName((String) dynaForm.get("NOMBRE"));
				if ((dynaForm.get("NRO DOCUMENTO")!=null) && (!dynaForm.get("NRO DOCUMENTO").equals(""))) person.setDocumentNumber((String) dynaForm.get("NRO DOCUMENTO"));

				if (dynaForm.get("CATEGORIA")!=null) {
					PersonCategory pc = Facade.getInstance().selectPersonCategory(Integer.parseInt((String) dynaForm.get("CATEGORIA")));
					person.setCategory(pc);
				}
				if ((!dynaForm.get("ORDEN PRELACION").equals("")) && (dynaForm.get("ORDEN PRELACION")!="")) person.setPriority(Integer.parseInt((String) dynaForm.get("ORDEN PRELACION")));
				if (dynaForm.get("NUMERO")!=null) movilNumber = (String) dynaForm.get("NUMERO");

				DocumentType dt = Facade.getInstance().selectDocumentType(Constants.PERSON_TYPE_CI);
				person.setDocumentType(dt);

				try{
					Facade.getInstance().insertPersonMovil(person, movilNumber);
				}catch(MovilException me){
					if (me.getIdException()==MovilException.ALREADY_IN_USE)
						errors.add("error", new ActionError("movilperson.error.movil.inuse"));
					else if (me.getIdException()==MovilException.EMPTY)
						errors.add("error", new ActionError("movilperson.error.movil.empty"));
				}catch(PersonException pe){
					if (pe.getIdException()==PersonException.DOCUMENT_EMPTY)
						errors.add("error", new ActionError("movilperson.error.person.document.empty"));
					else if (pe.getIdException()==PersonException.PERSON_ALREADY_EXISTS)
						errors.add("error", new ActionError("movilperson.error.person.alreadyexists"));
				}
			}

		} catch (Exception e) {
			errors.add("error", new ActionError("person.error.db.ingresar"));
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("failure");
		} else {
			messages.add("msg", new ActionMessage("person.update.ok"));
			saveMessages(request, messages);
			return mapping.findForward("success");
		}
	}

}
