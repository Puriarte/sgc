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
 
@WebServlet(name = "uploads",urlPatterns = {"/uploads/*"})
@MultipartConfig
public class Uploads extends HttpServlet {
 
 
  private static final long serialVersionUID = 2857847752169838915L;
  int BUFFER_LENGTH = 4096;
 
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
	  try{
		  PrintWriter out = response.getWriter();
		  for (Part part : request.getParts()) {
	        InputStream is = request.getPart(part.getName()).getInputStream();
	        String fileName = getFileName(part);
			  System.out.println(System.getenv("OPENSHIFT_DATA_DIR") + fileName);

	        FileOutputStream os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + fileName);
	        byte[] bytes = new byte[BUFFER_LENGTH];
	        int read = 0;
	        while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
	            os.write(bytes, 0, read);
	        }
	        os.flush();
	        is.close();
	        os.close();
	        out.println(fileName + " was uploaded to " + System.getenv("OPENSHIFT_DATA_DIR"));
		    }
	    }catch(Exception e){
	    }
  }
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  try{
	    String filePath = request.getRequestURI();
		
	    
	    String contextFacesPath;
	    
//	    if (getServletContext().getRealPath("")==null){
	    	contextFacesPath =System.getenv("OPENSHIFT_DATA_DIR") + "faces/" + filePath.substring(filePath.lastIndexOf("/")+1);
	/*	}else{
	        contextFacesPath = getServletContext().getRealPath("")+ "/images/faces/" ;
	        contextFacesPath = getServletContext().getRealPath("")+ "/images/faces/" + filePath.substring(filePath.lastIndexOf("/")+1);
		}
*/
			  System.out.println(System.getenv("OPENSHIFT_DATA_DIR") + "/faces/" + filePath.substring(filePath.lastIndexOf("/")+1));

	   	File file = new File(contextFacesPath );
	    InputStream input = new FileInputStream(file);
	 
	    response.setContentLength((int) file.length());
	    response.setContentType(new MimetypesFileTypeMap().getContentType(file));
	 
	    OutputStream output = response.getOutputStream();
	    byte[] bytes = new byte[BUFFER_LENGTH];
	    int read = 0;
	    while ((read = input.read(bytes, 0, BUFFER_LENGTH)) != -1) {
	        output.write(bytes, 0, read);
	        output.flush();
	    }
	 
	    input.close();
	    output.close();
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