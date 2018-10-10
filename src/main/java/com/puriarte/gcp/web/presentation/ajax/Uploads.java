 package com.puriarte.gcp.web.presentation.ajax;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.configuration.PropertiesConfiguration;

import com.puriarte.convocatoria.core.domain.Constants;
 
@WebServlet(name = "uploads",urlPatterns = {"/uploads/*"})
@MultipartConfig
public class Uploads extends HttpServlet {
 
	private static final long serialVersionUID = 2857847752169838915L;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
	  try{
			int BUFFER_LENGTH = 4096;

			PropertiesConfiguration config = new PropertiesConfiguration(com.puriarte.gcp.web.Constantes.PATHAPPCONFIG);
			String path= config.getString("data.folder") ;

			PrintWriter out = response.getWriter();
			for (Part part : request.getParts()) {
				try (InputStream is = request.getPart(part.getName()).getInputStream()){
					
			        String fileName = getFileName(part);
			        try( FileOutputStream os = new FileOutputStream(path  + fileName)){

			        	byte[] bytes = new byte[BUFFER_LENGTH];
				        int read = 0;
				        while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
				            os.write(bytes, 0, read);
				        }
				        os.flush();
				        out.println(fileName + " was uploaded to " + path );
			        }
				}
		    }
	    }catch(Exception e){
	    }
  }
	
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  int BUFFER_LENGTH = 4096;

	  try{
		  PropertiesConfiguration config = new PropertiesConfiguration(com.puriarte.gcp.web.Constantes.PATHAPPCONFIG);
		  String path= config.getString("data.folder") ;

		  String filePath = request.getRequestURI();
		  String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
	    
		  String contextFacesPath;
	    
		  if ((fileName==null) || (fileName.equals("")))
			  fileName=Constants.PICTURE_EMPTY_CHICA;

		  contextFacesPath = path + "faces/" + fileName;

		  File file = new File(contextFacesPath );
		  try(InputStream input = new FileInputStream(file)){
			    response.setContentLength((int) file.length());
			    response.setContentType(new MimetypesFileTypeMap().getContentType(file));
			 
			    try(OutputStream output = response.getOutputStream()){
				    byte[] bytes = new byte[BUFFER_LENGTH];
				    int read = 0;
				    while ((read = input.read(bytes, 0, BUFFER_LENGTH)) != -1) {
				        output.write(bytes, 0, read);
				        output.flush();
				    }
			    }
		  }
	  }catch(Exception e){
		  
	  }
  }
  
 
  private String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
          if (cd.trim().startsWith("filename")) {
            return cd.substring(cd.indexOf('=') + 1).trim()
                    .replace("\"", "");
          }
        }
        return null;
      }
}