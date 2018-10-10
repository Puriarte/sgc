package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.Assignment;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.Job;


public class SrvLstDispatch extends RestrictionServlet {

	private static final long serialVersionUID = -4327178410685697386L;
	private static final Logger logger = Logger.getLogger(SrvLstDispatch.class.getName());

	private HashMap<String,Object> cargarParametros(HttpServletRequest request){

		Integer strPage;
		Integer strRows;
		String strSort;
		String strOrder;
		String orderBy="";

		Integer dispatchStatus;
		boolean asc;
		Long totalRegistros=null;
		
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
		
		HashMap paramteros = new HashMap<String,Object>();
		paramteros.put("strPage", strPage);
		paramteros.put("strRows", strRows);
		paramteros.put("strSort", strSort);
		paramteros.put("strOrder", strOrder);
		paramteros.put("orderBy", orderBy);
		paramteros.put("dispatchStatus", dispatchStatus);
		paramteros.put("asc", asc);
		paramteros.put("totalRegistros", totalRegistros);
		
		return paramteros;
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Logger  logger = Logger.getLogger(this.getServletName());
		PrintWriter out = response.getWriter();
		int i=0;
		String jSonItems="";

		try {
			HashMap<String,Object> parametros=cargarParametros(request);
			jSonItems=procesar(parametros);
			response.setContentType("text/plain");
	        response.setCharacterEncoding("UTF-8");
			out.print(jSonItems);

		} catch(Exception e) {
			out.print("{\"error\":[{\"errorID\": \""+ i +"\",\"errtext\": \"" + e.getMessage() + "\"}]}");
		} finally {
			out.close();
		}

	}
	
	private String procesar(HashMap<String, Object> parametros) throws Exception {

		Logger logger = Logger.getLogger(this.getServletName());

		NumberFormat nF = NumberFormat.getNumberInstance(new Locale("ES"));
		SimpleDateFormat dTF=new SimpleDateFormat("dd/MM/yy HH:mm", new Locale("ES"));
		nF.setMinimumFractionDigits(2);

		int page= 1;
		int rows= 100;
		
		try {
			page=(int)parametros.get("strPage")-1;
		}catch(Exception e) {
		}

		try {
			rows=(int)parametros.get("strRows");
		}catch(Exception e) {
		}

		int pos = page*rows;
	 	
		Collection<? extends Dispatch> resultados = Facade.getInstance().selectSimpleListWithAssignments((int)parametros.get("dispatchStatus"), (String) parametros.get("orderBy"), 
				(boolean)parametros.get("asc"),pos, rows);

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
				logger.error(e.getMessage());
			}
		}

		jSonItems = jSonItems.replaceAll(System.getProperty("line.separator"), "");

		if (jSonItems.lastIndexOf(",")>0) jSonItems=jSonItems.substring(0,jSonItems.lastIndexOf(","));

		long totalRegistros=(long) 100;
		String strXml = "{\"total\": 1," ;
		strXml +="\"page\": " + (int)parametros.get("strPage")+ ",";
		strXml +="\"records\": " + totalRegistros + ",";
		strXml +="\"total\": " + totalRegistros/(int)parametros.get("strRows") + ",";
		strXml +="\"rows\": " +"[" + jSonItems + "]}";

		return strXml;
	}

	// Obtenemos un objeto del contexto de la aplicación por su nombre.
	protected Object getApplicationObject(String attrName) {
		return this.getServletContext().getAttribute(attrName);
	}
}