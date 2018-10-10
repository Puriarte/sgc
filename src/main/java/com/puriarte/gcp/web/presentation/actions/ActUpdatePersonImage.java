package com.puriarte.gcp.web.presentation.actions;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

public class ActUpdatePersonImage extends RestrictionAction {

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
			
			if ( (dynaForm.get("ID")!=null) && (!dynaForm.get("ID").toString().trim().equals(""))) {
				FormFile file = (FormFile) dynaForm.get("IMG");
				String rndNro = dynaForm.get("ID") + (String) dynaForm.get("RNDNAME");
				
			    String contextFacesPath;
				PropertiesConfiguration config = new PropertiesConfiguration(com.puriarte.gcp.web.Constantes.PATHAPPCONFIG);
				String path= config.getString("data.folder") ;
			    
			    if (this.getServlet().getServletContext().getRealPath("")==null){
		        	contextFacesPath = path  + "faces/";
		    	}else{
		            contextFacesPath = this.getServlet().getServletContext().getRealPath("")+ "/images/faces/";
		    	}
 			   
			    //create the upload folder if not exists
			    File folder = new File(contextFacesPath);
			    if(!folder.exists()){
			    	folder.mkdir();
			    }
		 			    
			    String fileName = file.getFileName();
			    fileName="flag_" + rndNro + ".jpg";
			    String fileName_chica="flag_chica_" + rndNro + ".jpg";
			    String fileName_mediana="flag_mediana_" + rndNro + ".jpg";
			    String fileName_grande="flag_grande_" + rndNro + ".jpg";
		 
			    if(!("").equals(fileName)){  
			        File newFile = new File(contextFacesPath, fileName);
		 
			        try (FileOutputStream fos = new FileOutputStream(newFile, false)){
				        fos.write(file.getFileData());
				        fos.flush();
			        }

			        request.setAttribute("uploadedFilePath",newFile.getAbsoluteFile());
			        request.setAttribute("uploadedFileName",newFile.getName());

			        // Genero las diferentes vistas
			        Thumbnails.of(newFile)
			        .size(32, 39)
			        .toFile(new File(contextFacesPath, fileName_chica));

			        Thumbnails.of(newFile)
			        .size(111, 137)
			        .toFile(new File(contextFacesPath, fileName_grande));

			        Thumbnails.of(newFile)
			        .size(67, 83)
			        .toFile(new File(contextFacesPath, fileName_mediana));
			    }
			}
		} catch (Exception e) {
			errors.add("error", new ActionMessage("person.error.db.ingresar"));
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