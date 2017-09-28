package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.Place;


public class SrvLstPlace extends RestrictionServlet {


    public void init(ServletConfig config) throws ServletException {
		super.init(config);
    }

    public void _doProcess(HttpServletRequest request, HttpServletResponse response)
                   throws IOException, ServletException {

    	Logger  logger = Logger.getLogger(SrvLstPlace.class.getName());
        PrintWriter out = response.getWriter();

        try {
        	List<Place> listItems = Facade.getInstance().selectPlaceList();
        	String strXml = "<select>";
        	for ( Place place : listItems ) {
        		strXml += "<option value='" + place.getId() +"'>" + place.getName().trim()  + "</option>";
        	}

        	strXml += "</select>";
        //	System.out.println(strXml);
        	out.print(strXml);

        } catch(Exception e) {
        	logger.error(e.getStackTrace());
        } finally {
       	 	out.close();
        }
    }
}