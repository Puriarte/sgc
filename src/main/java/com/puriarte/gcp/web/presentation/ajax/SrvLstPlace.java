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
import com.puriarte.convocatoria.persistence.Place;

public class SrvLstPlace extends RestrictionServlet {

	private static final long serialVersionUID = 12156990156951396L;
	private static final Logger logger = Logger.getLogger(SrvLstPlace.class.getName());

    public void init(ServletConfig config) throws ServletException {
		super.init(config);
    }

    public void _doProcess(HttpServletRequest request, HttpServletResponse response)
                   throws IOException, ServletException {

        PrintWriter out = response.getWriter();

        try {
        	List<Place> listItems = Facade.getInstance().selectPlaceList(false);
        	String strXml = "<select>";
        	for ( Place place : listItems ) {
        		strXml += "<option value='" + place.getId() +"'>" + place.getName().trim()  + "</option>";
        	}

        	strXml += "</select>";
        	out.print(strXml);

        } catch(Exception e) {
        	logger.error(e.getStackTrace());
        } finally {
       	 	out.close();
        }
    }
}