package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.utils.date.DateUtils;

public class SrvLstSMS extends RestrictionServlet {

	private static final long serialVersionUID = 7514109531028703059L;
	private static final Logger logger = Logger.getLogger(SrvLstSMS.class.getName());

	/**
	 * @param request
	 */
	private HashMap<String,Object> cargarParametros(HttpServletRequest request){


		Integer strPage;
		Integer strRows;
		String strSort;
		String strOrder;
		String orderBy="";
		
		int estado;
		int convocatoria;
		Date  fechaInicio;
		Date  fechaFin;
		boolean deleted;

		orderBy = "s.creationDate desc";

		strPage = Integer.parseInt(request.getParameter("page"));
		strRows = Integer.parseInt(request.getParameter("rows"));
		strSort = request.getParameter("sidx");
		strOrder = request.getParameter("sord");
		
		if (strSort.equals("5")) orderBy = "creationDate"; 
		if (strSort.equals("7")) orderBy = "number"; 
		if (strSort.equals("8")) orderBy = "person.name " + strOrder +   ", message "; 
		if (strSort.equals("9")) orderBy = "texto"; 
		if (strSort.equals("10")) orderBy = "sentDate"; 
		if (strSort.equals("11")) orderBy = "direction"; 
		if (strSort.equals("12")) orderBy = "status"; 
		if (strSort.equals("13")) orderBy = "dispatchCode"; 
		if (strSort.equals("14")) orderBy = "dispatch"; 
		
		fechaInicio = getDateRequest(request, "fechaDesde");
		fechaFin = getDateRequest(request, "fechaHasta");

		if (fechaFin!=null){
			fechaFin.setHours(23);
			fechaFin.setMinutes(59);
		}

		estado = ((request.getParameter("estado")!=null) && NumberUtils.isNumber(request.getParameter("estado")))? Integer.parseInt(request.getParameter("estado")) : 0;
		convocatoria = ((request.getParameter("convocatoria")!=null) && NumberUtils.isNumber(request.getParameter("convocatoria")))? Integer.parseInt(request.getParameter("convocatoria")) : 0;

		//TODO: Por ahora solo muestro los SMS que no están borrados. Debería poder ver el tarro de basura.
		deleted = false;
		
		
		HashMap parametros = new HashMap<String,Object>();
		parametros.put("strPage", strPage);
		parametros.put("strRows", strRows);
		parametros.put("strSort", strSort);
		parametros.put("strOrder", strOrder);
		parametros.put("orderBy", orderBy);
		parametros.put("estado", estado);
		parametros.put("convocatoria", convocatoria);
		parametros.put("fechaInicio", fechaInicio);
		parametros.put("fechaFin", fechaFin);
		parametros.put("deleted", deleted);
		
		return parametros;
		
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Logger  logger = Logger.getLogger(this.getServletName());
		PrintWriter out = response.getWriter();
		int i=0;
		String jSonItems="";

		try {
			HashMap<String, Object> parametros =cargarParametros(request);

			int strPage= (int) parametros.get("strPage");
			int strRows= (int) parametros.get("strRows");
			
			int count = Facade.getInstance().selectCountSMS((Date)parametros.get("fechaInicio"),(Date)parametros.get("fechaFin"), (int)parametros.get("estado"), 
					(int)parametros.get("convocatoria"),(boolean)parametros.get("deleted"));
			
			List<SMS> resultados = Facade.getInstance().selectSMSList((Date)parametros.get("fechaInicio"),(Date)parametros.get("fechaFin"), (int)parametros.get("estado"), 
					(boolean)parametros.get("deleted"), (int)parametros.get("convocatoria"),  (String)parametros.get("orderBy"), (String)parametros.get("strOrder"), (strPage-1)*strRows,strRows);

			jSonItems= procesarItems2(resultados, count, parametros);
			response.setContentType("text/plain");
	        response.setCharacterEncoding("UTF-8");
			out.print(jSonItems);

		} catch(Exception e) {
			out.print("{\"error\":[{\"errorID\": \""+ i +"\",\"errtext\": \"" + e.getMessage() + "\"}]}");
		} finally {
			out.close();
		}
	}

	private String procesarItems2(List<SMS> resultados, int totalRegistros, HashMap<String, Object> parametros) {

		NumberFormat nF = NumberFormat.getNumberInstance(new Locale("ES"));
		nF.setMinimumFractionDigits(2);

		SimpleDateFormat dTF=new SimpleDateFormat("dd/MM/yy HH:mm", new Locale("ES"));

		String jSonItems="";
		int i=0;

		for(SMS item : resultados) {
			try{

			jSonItems += "{\"Id\": \"" +item.getId()+ "\",";
			jSonItems += "\"Pos\": \"" + i++ + "\",";
			jSonItems += "\"Cliente\": \"" +item.getId()+ "\",";
			jSonItems += "\"IdDoc\": \"" + item.getId() + "\",";
			if (item.getCreationDate()!=null)
				jSonItems += "\"Fecha\": \"" + dTF.format(item.getCreationDate()) + "\",";
			else
				jSonItems += "\"Fecha\": \"" + "\",";

			if ((item.getPersonMovil()!=null) && (item.getPersonMovil().getMovil()!=null))
				jSonItems += "\"Numero\": \"" +  item.getPersonMovil().getMovil().getNumber() + "\",";
			else
				jSonItems += "\"Numero\": \"" + "\",";

			if ((item.getPersonMovil()!=null) && (item.getPersonMovil().getMovil()!=null) &&(item.getPersonMovil().getPerson()!=null) &&(item.getPersonMovil().getPerson().getName()!=null))
				jSonItems += "\"Nombre\": \"" +  item.getPersonMovil().getPerson().getName() + "\",";
			else
				jSonItems += "\"Nombre\": \"" + "--" + "\",";
				
			if (item.getMensaje().length()>200) 
				jSonItems += "\"Texto\": \"" + StringEscapeUtils.escapeHtml(item.getMensaje()).substring(0,100).replaceAll("\\n","\\\\n")
		                 .replaceAll("\\r","\\\\r")
		                 .replaceAll("\\t","\\\\t")  + "..." + "\",";
			else
				jSonItems += "\"Texto\": \"" + StringEscapeUtils.escapeHtml(item.getMensaje()).replaceAll("\\n","\\\\n")
		                 .replaceAll("\\r","\\\\r")
		                 .replaceAll("\\t","\\\\t")  + "\",";

			if (item.getSentDate()!=null)
				jSonItems += "\"FechaEnvio\": \"" + dTF.format(item.getSentDate()) + "\",";
			else
				jSonItems += "\"\": \"" + "\",";

			if (item.getAction()==Constants.SMS_ACTION_INCOME)
				jSonItems += "\"Action\": \"ENTRANTE\",";
			else
				jSonItems += "\"Action\": \"SALIENTE\",";

			if (item.getStatus()!=null)
				jSonItems += "\"Saldo\": \"" +   item.getStatus().getName() + "\",";
			else
				jSonItems += "\"Saldo\": \"\",";

			if ((item.getDispatch()!=null) &&  (item.getDispatch().getCode()!=null))
				jSonItems += "\"DispatchCode\": \"" +  item.getDispatch().getCode() + "\",";
			else
				jSonItems += "\"DispatchCode\": \"\",";
				
			if (item.getDispatch()!=null)
				jSonItems += "\"Dispatch\": \"" +   StringEscapeUtils.escapeHtml(item.getDispatch().getName()) + "\"},";
			else
				jSonItems += "\"Dispatch\": \"\"},";
			}catch(Exception e){}
		}

		jSonItems = jSonItems.replaceAll(System.getProperty("line.separator"), "");

		if (jSonItems.lastIndexOf(",")>0) jSonItems=jSonItems.substring(0,jSonItems.lastIndexOf(","));

		int strPage= (int) parametros.get("strPage");
		int strRows= (int) parametros.get("strRows");

		int totalPaginas=  totalRegistros/strRows;
		if ((totalRegistros % strRows)!=0) totalPaginas++;
			
		String strXml = "{\"total\": " + totalPaginas + ", ";
		strXml +="\"page\": " + strPage + ",";
		strXml +="\"records\": " + totalRegistros + ",";
		strXml +="\"rows\": " +"[" + jSonItems + "]}";

		return strXml;
	}

	protected Date getDateRequest(HttpServletRequest request, String parameter) {
		try{
			return DateUtils.parseDate(request.getParameter(parameter), Constants.FORMATO_FECHA_HTML5_REGEX,  Constants.FORMATO_FECHA_HTML5);
		}catch(ParseException ex){
			try{
				return DateUtils.parseDate(request.getParameter(parameter), Constants.FORMATO_FECHA_HTML5_REGEX_ALT1,  Constants.FORMATO_FECHA_HTML5_ALT1);
			}catch(ParseException ex1){
				try{
					return DateUtils.parseDate(request.getParameter(parameter), Constants.FORMATO_FECHA_HTML5_REGEX_ALT2,  Constants.FORMATO_FECHA_HTML5_ALT2);
				}catch(ParseException ex2){
					return null;
				}
			}
		}catch(NullPointerException ex){
			return null;
		}
	}

	// Obtenemos un objeto del contexto de la aplicación por su nombre.
	protected Object getApplicationObject(String attrName) {
		return this.getServletContext().getAttribute(attrName);
	}
}