package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.PersonCategory;


public class SrvSelectDispatch extends RestrictionServlet {


    public void init(ServletConfig config) throws ServletException {
		super.init(config);
    }

    public void _doProcess(HttpServletRequest request, HttpServletResponse response)
                   throws IOException, ServletException {

    	Logger  logger = Logger.getLogger(SrvSelectDispatch.class.getName());
        PrintWriter out = response.getWriter();

        try {
        	List<Dispatch> listItems = new ArrayList<Dispatch>(Facade.getInstance().selectSimpleDispatchList(0, "", 0, 1000));
        	 	
        	String strXml = "<select>";
        	for ( Dispatch dispatch : listItems ) {
        		strXml += "<option value='" + dispatch.getId() +"'>" + dispatch.getName().trim()  + "</option>";
        	}

 	           strXml += "</select>";
	           System.out.println(strXml);
	           out.print(strXml);


        } catch(Exception e) {
        	logger.error(e.getStackTrace());
        } finally {
       	 	out.close();
        }
    }
}