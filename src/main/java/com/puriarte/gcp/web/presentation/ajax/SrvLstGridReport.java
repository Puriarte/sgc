package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.result.Report1;

public class SrvLstGridReport extends RestrictionServlet {

	private static final long serialVersionUID = -6958034743845189419L;
	private static final Logger logger = Logger.getLogger(SrvLstGridReport.class.getName());


	private HashMap<String,Object> cargarParametros(HttpServletRequest request){

		Integer strPage;
		Integer strRows;
		String strSort;
		String strOrder;
		String orderBy="";

		Date from;
		Date to;
		String strReport;

		strPage = Integer.parseInt(request.getParameter("page"));
		strRows = Integer.parseInt(request.getParameter("rows"));
		strSort = request.getParameter("sidx");
		strOrder = request.getParameter("sord");
		strReport= request.getParameter("report");

		from = getDateRequest(request, "fechaDesde");
		to= getDateRequest(request, "fechaHasta");

		if (to!=null){
			to.setHours(23);
			to.setMinutes(59);
		}
		
		if (strSort.equals("name"))
			orderBy="p.name";

		HashMap paramteros = new HashMap<String,Object>();
		paramteros.put("strPage", strPage);
		paramteros.put("strRows", strRows);
		paramteros.put("strSort", strSort);
		paramteros.put("strOrder", strOrder);
		paramteros.put("strReport", strReport);
		
		paramteros.put("from", from);
		paramteros.put("to", to);
		paramteros.put("orderBy", orderBy);
		
		return paramteros;
		
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Logger  logger = Logger.getLogger(this.getServletName());
		PrintWriter out = response.getWriter();
		int i=0;
		String jSonItems="";

		try {
			HashMap<String, Object> parametros =cargarParametros(request);
			if (parametros.get("strReport").equals("1"))
				jSonItems=procesarReport1(parametros);

			response.setContentType("text/plain");
	        response.setCharacterEncoding("UTF-8");
	        out.print(jSonItems);

		} catch(Exception e) {
			out.print("{\"error\":[{\"errorID\": \""+ i +"\",\"errtext\": \"" + e.getMessage() + "\"}]}");
		} finally {
			out.close();
		}

	}

	private String procesarReport1(HashMap<String, Object> parametros) throws Exception{

		Date from = (Date)parametros.get("from");
		Date to = (Date)parametros.get("to");
		String orderBy= (String)parametros.get("orderBy");
		
		List<Report1> resultados = Facade.getInstance().selectReport1(from, to, orderBy);

		String jSonItems="";
		int i=0;

		for(Report1 item : resultados) {
			try{
				jSonItems += "{\"Name\": \"" +item.getName()+ "\",";
				jSonItems += "\"Pos\": \"" + i++ + "\",";
				jSonItems += "\"Convoc\": \"" + item.getConvened() + "\",";
				jSonItems += "\"Aceptada\": \"" + item.getAccepted() + "\",";
				jSonItems += "\"Rechazada\": \"" + item.getRejected() + "\",";
				jSonItems += "\"Cancelada\": \"" + item.getCancelled() + "\",";
				jSonItems += "\"SinResp\": \"" + item.getAssigned() + "\",";
				jSonItems += "\"Aceptacion\": \"" + item.getPercAccepted() + "\",";
				jSonItems += "\"Phone\": \"" +item.getPhone()+ "\"},";		
				
			}
			catch(Exception e){}
		}

		jSonItems = jSonItems.replaceAll(System.getProperty("line.separator"), "");

		if (jSonItems.lastIndexOf(",")>0) jSonItems=jSonItems.substring(0,jSonItems.lastIndexOf(","));

		long totalRegistros=(long) 100;
		String strXml = "{\"total\": 1," ;
		strXml +="\"page\": " + (int)parametros.get("strPage") + ",";
		strXml +="\"records\": " + totalRegistros + ",";
		strXml +="\"total\": " + totalRegistros/(int)parametros.get("strRows") + ",";
		strXml +="\"rows\": " +"[" + jSonItems + "]";
		strXml +="}";

		return strXml;
	}

}