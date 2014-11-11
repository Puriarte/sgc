package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.PersonCategory;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.Place;
import com.puriarte.utils.date.DateUtils;


public class SrvLstGridPlace extends RestrictionServlet {

	private Integer strPage;
	private Integer strRows;
	private String strSort;
	private String strOrder;
	private String orderBy;
	private  Long totalRegistros=null;
	private NumberFormat nF;


	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public  void doGet(HttpServletRequest request, HttpServletResponse  response)
			throws IOException, ServletException {
		try {
			_doProcess(request, response);
		} catch(Exception e) {
			throw new ServletException(e.getMessage());
		}
	}

	public  void doPost(HttpServletRequest request, HttpServletResponse  response)
			throws IOException, ServletException {
		try {
			_doProcess(request, response);
		} catch(Exception e) {
			throw new ServletException(e.getMessage());
		}

	}

	private void cargarParametros(HttpServletRequest request){

		strPage = Integer.parseInt(request.getParameter("page"));
		strRows = Integer.parseInt(request.getParameter("rows"));
		strSort = request.getParameter("sidx");
		strOrder = request.getParameter("sord");
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Logger  logger = Logger.getLogger(this.getServletName());
		PrintWriter out = response.getWriter();
		int i=0;
		String jSonItems="";

		try {
			cargarParametros(request);
			jSonItems=procesar();
			out.print(jSonItems);

		} catch(Exception e) {
			out.print("{\"error\":[{\"errorID\": \""+ i +"\",\"errtext\": \"" + e.getMessage() + "\"}]}");
		} finally {
			out.close();
		}

	}

	private String procesar() throws Exception {
		List<Place> resultados = Facade.getInstance().selectPlaceList();

		String jSonItems="";
		int i=0;

		for(Place item : resultados) {
			try{

			jSonItems += "{\"Id\": \"" +item.getId()+ "\",";
			jSonItems += "\"Pos\": \"" + i++ + "\",";

			if (item.getAddress()!=null)
				jSonItems += "\"Address\": \"" + item.getName() + "\",";
			else
				jSonItems += "\"Address\": \",";

			if (item.getPhone()!=null)
				jSonItems += "\"Phone\": \"" + item.getPhone() + "\",";
			else
				jSonItems += "\"Phone\": \",";

			if (item.getName()!=null)
				jSonItems += "\"Name\": \"" + item.getName() + "\"},";
			else
				jSonItems += "\"Name\": \"},";

			}
			catch(Exception e){}
		}

		jSonItems = jSonItems.replaceAll(System.getProperty("line.separator"), "");

		if (jSonItems.lastIndexOf(",")>0) jSonItems=jSonItems.substring(0,jSonItems.lastIndexOf(","));

		totalRegistros=(long) 100;
		String strXml = "{\"total\": 1," ;
		strXml +="\"page\": " + strPage + ",";
		strXml +="\"records\": " + totalRegistros + ",";
		strXml +="\"total\": " + totalRegistros/strRows + ",";
		strXml +="\"rows\": " +"[" + jSonItems + "]";
		strXml +="}";

		System.out.println(strXml);
		return strXml;
	}


}