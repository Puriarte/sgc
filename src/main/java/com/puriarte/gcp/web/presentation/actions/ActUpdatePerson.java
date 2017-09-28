package com.puriarte.gcp.web.presentation.actions;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
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

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.core.exceptions.MovilException;
import com.puriarte.convocatoria.core.exceptions.PersonException;
import com.puriarte.convocatoria.persistence.Category;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.DocumentType;
import com.puriarte.convocatoria.persistence.Movil;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonCategoryAsociation;
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
			
			if(dynaForm.get("accion").equals("load")){
				try{
					// NUEVO
					if (((dynaForm.get("ID")==null)||(dynaForm.get("ID").equals(0)))==true){
						ArrayList<PersonCategory> colCategories =(ArrayList<PersonCategory>)Facade.getInstance().selectPersonCategoryList();
						dynaForm.set("FOTO", Constants.PICTURE_EMPTY_MEDIA);
						dynaForm.set("COLCATEGORIAS", colCategories);
						dynaForm.set("CATEGORIA PREFERIDA", -1);
						dynaForm.set("ORDEN PRELACION", 0);
						// MODIFICAR
					}else{
						PersonMovil person = Facade.getInstance().selectPersonMovilWithCategories(new Integer(dynaForm.get("ID").toString()));
						
			//			Person person = Facade.getInstance().selectPerson(new Integer(dynaForm.get("ID").toString()));
						dynaForm.set("ID", person.getId()); 
						dynaForm.set("SOBRENOMBRE", person.getPerson().getNickname()); 
						dynaForm.set("NOMBRE", person.getPerson().getName());
						dynaForm.set("NRO DOCUMENTO", person.getPerson().getDocumentNumber());
						dynaForm.set("ORDEN PRELACION", person.getPerson().getPriority());
						dynaForm.set("NUMERO", person.getMovil().getNumber());
						dynaForm.set("FOTO", ((person.getPerson().getPicture()==null) || (person.getPerson().getPicture().equals(""))) ?Constants.PICTURE_EMPTY_MEDIA:person.getPerson().getPicture());
						int idPreferedCategory=(person.getPerson().getPreferedCategory()==null)?-1:person.getPerson().getPreferedCategory().getId();
						dynaForm.set("CATEGORIA PREFERIDA",idPreferedCategory );

						ArrayList<PersonCategoryAsociation> categories = new ArrayList<PersonCategoryAsociation>();
						for (PersonCategoryAsociation category: person.getPerson().getCategories()){
							if (category.getIdPersonCategory()!=idPreferedCategory)
								categories.add(category);
						}
						ArrayList<PersonCategory> colCategories =(ArrayList<PersonCategory>)Facade.getInstance().selectPersonCategoryList();
	
						dynaForm.set("CATEGORIAS", categories);
						dynaForm.set("COLCATEGORIAS", colCategories);

					}					
					dynaForm.set("accion", "send");

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
				// MODIFICAR
				if ( (dynaForm.get("ID")!=null) && (!dynaForm.get("ID").equals(0)) &&(!dynaForm.get("ID").toString().trim().equals(""))) {

					int idPerson = new Integer(dynaForm.get("ID").toString());				
					PersonMovil person = Facade.getInstance().selectPersonMovilWithCategories(idPerson );

					Facade.getInstance().celanPersonCategories(person.getPerson().getId());
	
					if (dynaForm.get("SOBRENOMBRE")!=null) person.getPerson().setNickname((String) dynaForm.get("SOBRENOMBRE"));
					if (dynaForm.get("NOMBRE")!=null) person.getPerson().setName((String) dynaForm.get("NOMBRE"));
					if ((dynaForm.get("NRO DOCUMENTO")!=null) && (!dynaForm.get("NRO DOCUMENTO").equals(""))) person.getPerson().setDocumentNumber((String) dynaForm.get("NRO DOCUMENTO"));
					if (dynaForm.get("NUMERO")!=null) movilNumber = (String) dynaForm.get("NUMERO");

					// CATEGORIA PREFERIDA
					if ((!dynaForm.get("CATEGORIA PREFERIDA").equals("")) && (dynaForm.get("CATEGORIA PREFERIDA")!="")) person.getPerson().setPreferedCategory( Facade.getInstance().selectPersonCategory((int) dynaForm.get("CATEGORIA PREFERIDA")));
					person.getPerson().setPriority(((!dynaForm.get("ORDEN PRELACION").equals("")) && (dynaForm.get("ORDEN PRELACION")!=""))? (int) dynaForm.get("ORDEN PRELACION"):0);

					if (person.getPerson().getPreferedCategory()!=null){
						person.getPerson().addPersonCategory(person.getPerson().getPreferedCategory(),person.getPerson().getPriority());
					}

					// CARGO LAS CATEGORIAS
					HashMap<Integer, Object> arPersonCategory = getCategoryRequest(request,"CATEGORIA_");
					HashMap<Integer, Object> arPriority = getCategoryRequest(request,"ORDEN PRELACION_");
					
					Iterator it = arPersonCategory.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry entry = (Entry) it.next();
						int key = (int) entry.getKey();
						Integer idCategory= new Integer((Integer) entry.getValue());
						int priority = 0;
						try { 
							priority=(int) arPriority.get(key);
						}catch(Exception e){
						}
						PersonCategory pc = Facade.getInstance().selectPersonCategory(idCategory);
						person.getPerson().addPersonCategory(pc,priority);
					}
					
			        FormFile foto = (FormFile) dynaForm.get("IMG");
					String filename = cargarFotoDePersona(foto);
					if (filename!=null)
						person.getPerson().setPicture(filename);

					Facade.getInstance().updatePersonMovil(person, movilNumber);
	
				}else{
					// CREAR
					Person person = new Person();
					if (dynaForm.get("SOBRENOMBRE")!=null) person.setNickname((String) dynaForm.get("SOBRENOMBRE"));
					if (dynaForm.get("NOMBRE")!=null) person.setName((String) dynaForm.get("NOMBRE"));
					if ((dynaForm.get("NRO DOCUMENTO")!=null) && (!dynaForm.get("NRO DOCUMENTO").equals(""))) person.setDocumentNumber((String) dynaForm.get("NRO DOCUMENTO"));
					if (dynaForm.get("NUMERO")!=null) movilNumber = (String) dynaForm.get("NUMERO");

					// CATEGORIA PREFERIDA
					if ((!dynaForm.get("CATEGORIA PREFERIDA").equals("")) && (dynaForm.get("CATEGORIA PREFERIDA")!="")) person.setPreferedCategory( Facade.getInstance().selectPersonCategory((int) dynaForm.get("CATEGORIA PREFERIDA")));
					person.setPriority(((!dynaForm.get("ORDEN PRELACION").equals("")) && (dynaForm.get("ORDEN PRELACION")!=""))? (int) dynaForm.get("ORDEN PRELACION"):0);

					if (person.getPreferedCategory()!=null){
						person.addPersonCategory(person.getPreferedCategory(),person.getPriority());
					}
					
					// CARGO LAS CATEGORIAS
					HashMap<Integer, Object> arPersonCategory = getCategoryRequest(request,"CATEGORIA_");
					HashMap<Integer, Object> arPriority = getCategoryRequest(request,"ORDEN PRELACION_");
			
					Iterator it = arPersonCategory.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry entry = (Entry) it.next();
						int key = (int) entry.getKey();
						Integer idCategory= new Integer((Integer) entry.getValue());
						int priority = 0;
						try { 
							priority=(int) arPriority.get(key);
						}catch(Exception e){
						}
						PersonCategory pc = Facade.getInstance().selectPersonCategory(idCategory);
						person.addPersonCategory(pc,priority);
					}
					
					DocumentType dt = Facade.getInstance().selectDocumentType(Constants.PERSON_TYPE_CI);
					person.setDocumentType(dt);
	
			        FormFile foto = (FormFile) dynaForm.get("IMG");
					String filename = cargarFotoDePersona(foto);
					if (filename!=null)
						person.setPicture(filename);

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
	
	private String cargarFotoDePersona(FormFile foto) {
		try{
			if (foto.getFileName().equals("")){
				return null;
			}else{
				String fileName    = RandomStringUtils.randomAlphanumeric(14);
		    	InputStream is = foto.getInputStream();
		    
		        int BUFFER_LENGTH = 4096;
		        
	//	        System.out.println(System.getenv("OPENSHIFT_DATA_DIR") + "faces/"+ fileName);
		        FileOutputStream os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + "faces/"+ fileName);
		        byte[] bytes = new byte[BUFFER_LENGTH];
		        int read = 0;
		        while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
		            os.write(bytes, 0, read);
		        }
		        os.flush();
		        is.close();
		        os.close();
		        return fileName;
			}
		}catch(Exception e){
			return null;
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
