package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.Assignment;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.Job;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.gcp.web.Constantes;
import com.puriarte.utils.date.DateUtils;


public class SrvLstDispatch extends HttpServlet {

	private Integer strPage;
	private Integer strRows;
	private String strSort;
	private String strOrder;
	private String orderBy;

	private Integer dispatchStatus;
	private boolean asc;
	private  Long totalRegistros=null;
	private NumberFormat nF;
	private SimpleDateFormat dTF;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		nF = NumberFormat.getNumberInstance(new Locale("ES"));
		nF.setMinimumFractionDigits(2);
		dTF = new SimpleDateFormat("dd/MM/yy HH:mm", new Locale("ES"));
	
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
		asc=false;

		if	 ((strOrder!=null)&&(strOrder.equals("asc"))) asc  = true;
		request.getQueryString();

		//Armo el criterio en que se quiere ordenar
		if(strSort != null) {
			if(strSort.equals("3"))  orderBy = "place.name";
			else if (strSort.equals("4")) orderBy = "scheduledDate";
			else if (strSort.equals("6")) orderBy = "name";
			else if (strSort.equals("7")) orderBy = "dispatchStatus.name";
			else {
				orderBy = "scheduledDate";
				asc=false;
			}
		}

		//	Datos de filtors para la consulta
		dispatchStatus = ((request.getParameter("dispatchStatus")!=null) && NumberUtils.isNumber(request.getParameter("dispatchStatus")))? Integer.parseInt(request.getParameter("dispatchStatus")) : 0;

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
	 	int pos = (strPage-1) * strRows ;

		System.out.println((new Date()).toString());
		List<Dispatch> resultados = Facade.getInstance().selectDispatchList(dispatchStatus, orderBy, asc, pos, strRows);
		System.out.println((new Date()).toString());
		String jSonItems="";
		int i=0;

		for(Dispatch item : resultados) {
			try{
			
				String jsonAssignments = "";
				for (Job job: item.getJobList()){
					Assignment assignment = job.getAssignmentList().get(0);
					jsonAssignments = jsonAssignments + "{\"Id\":\"" + assignment.getId() + 
							"\",\"Movil\":\"" + assignment.getPersonMovil().getMovil().getNumber() + 
							"\",\"Person\":\"" + assignment.getPersonMovil().getPerson().getName() + 
							"\",\"Status\":\"" + assignment.getStatus().getName() + 
							"\",\"AssignmentDate\":\"" + dTF.format(assignment.getAssignmentDate()) + 
							"\",\"PersonCategory\":\"" + job.getCategory().getName() + 
							"\"},";
				}
				if (jsonAssignments.length()>0)
					jsonAssignments = jsonAssignments.substring(0,jsonAssignments.length()-1);

				jSonItems += "{\"Id\": \"" +item.getId()+ "\",";
				jSonItems += "\"Pos\": \"" + i++ + "\",";
				jSonItems += "\"Cliente\": \"" +item.getId()+ "\",";
				jSonItems += "\"IdDoc\": \"" + item.getId() + "\",";
				if (item.getCreationDate()!=null)
					jSonItems += "\"Fecha\": \"" +  dTF.format(item.getCreationDate())  + "\",";
				else
					jSonItems += "\"Fecha\": \"" + "\",";
	
				if (item.getPlace()!=null)
					jSonItems += "\"Place\": \"" +  item.getPlace().getName() + "\",";
				else
					jSonItems += "\"Place\": \"" + "" + "\",";
	
				jSonItems += "\"Texto\": \"\",";
	
				if (item.getScheduledDate()!=null)
					jSonItems += "\"FechaEnvio\": \"" + dTF.format(item.getScheduledDate()) + "\",";
				else
					jSonItems += "\"\": \"" + "\",";
	
				if (item.getScheduledEndDate()!=null)
					jSonItems += "\"FechaHasta\": \"" + dTF.format(item.getScheduledEndDate()) + "\",";
				else
					jSonItems += "\"\": \"" + "\",";
				
				if (item.getName()!=null)
					jSonItems += "\"Name\": \"" +StringEscapeUtils.escapeHtml( item.getName() )+ "\",";
				else
					jSonItems += "\"Name\": \"\",";
	
				if (item.getName()!=null)
					jSonItems += "\"DispatchCode\": \"" +item.getCode() + "\",";
				else
					jSonItems += "\"DispatchCode\": \"\",";
				
				if (item.getDispatchStatus()!=null)
					jSonItems += "\"DispatchStatus\": \"" + item.getDispatchStatus().getName() + "\"";
				else
					jSonItems += "\"DispatchStatus\": \"\"";
	
				
				jSonItems += ", \"Assignments\" : [" + jsonAssignments + "]},";

			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

		jSonItems = jSonItems.replaceAll(System.getProperty("line.separator"), "");

		if (jSonItems.lastIndexOf(",")>0) jSonItems=jSonItems.substring(0,jSonItems.lastIndexOf(","));

		//totalRegistros=resultados.size();
		totalRegistros=(long) 100;
		String strXml = "{\"total\": 1," ;
		strXml +="\"page\": " + strPage + ",";
		strXml +="\"records\": " + totalRegistros + ",";
		strXml +="\"total\": " + totalRegistros/strRows + ",";
		strXml +="\"rows\": " +"[" + jSonItems + "]}";

	//	System.out.println(strXml);
		return strXml;
	}
 

	// Obtenemos un objeto del contexto de la aplicación por su nombre.
	protected Object getApplicationObject(String attrName) {
		return this.getServletContext().getAttribute(attrName);
	}
}