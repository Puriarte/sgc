package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.utils.date.DateUtils;

public class SrvGetSMS extends HttpServlet {

	private String pduUserData;
	private String gatewayId;
	private String text;
	private String date;
	private String originator;
	
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

	/**
	 * @param request
	 */
	private void cargarParametros(HttpServletRequest request){
		pduUserData =  request.getParameter("pduUserData");
		gatewayId =  request.getParameter("gatewayId");
		text =  request.getParameter("text");
		date =  request.getParameter("date");
		originator =  request.getParameter("originator");
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Logger  logger = Logger.getLogger(this.getServletName());
		PrintWriter out = response.getWriter();
		int i=0;
		String jSonItems="";
		try {
			cargarParametros(request);
			out.print("0");

		} catch(Exception e) {
			out.print("{\"error\":[{\"errorID\": \""+ i +"\",\"errtext\": \"" + e.getMessage() + "\"}]}");
		} finally {
			out.close();
		}

	}

//	private String procesar() throws Exception {
//		List<SMS> resultados = Facade.getInstance().selectSMSList(fechaInicio,fechaFin,estado,convocatoria,orderBy, null,null);
//		return procesarItems2(resultados);
//	}
//
//
//	private String procesarItems2(List<SMS> resultados) {
//		String jSonItems="";
//		int i=0;
//
//		for(SMS item : resultados) {
//			try{
//
//			jSonItems += "{\"Id\": \"" +item.getId()+ "\",";
//			jSonItems += "\"Pos\": \"" + i++ + "\",";
//			jSonItems += "\"Cliente\": \"" +item.getId()+ "\",";
//			jSonItems += "\"IdDoc\": \"" + item.getId() + "\",";
//			if (item.getCreationDate()!=null)
//				jSonItems += "\"Fecha\": \"" + dTF.format(item.getCreationDate()) + "\",";
//			else
//				jSonItems += "\"Fecha\": \"" + "\",";
//
//			if ((item.getPersonMovil()!=null) && (item.getPersonMovil().getMovil()!=null))
//				jSonItems += "\"Numero\": \"" +  item.getPersonMovil().getMovil().getNumber() + "\",";
//			else
//				jSonItems += "\"Numero\": \"" + "\",";
//
//			if ((item.getPersonMovil()!=null) && (item.getPersonMovil().getMovil()!=null) &&(item.getPersonMovil().getPerson()!=null) &&(item.getPersonMovil().getPerson().getName()!=null))
//				jSonItems += "\"Nombre\": \"" +  item.getPersonMovil().getPerson().getName() + "\",";
//			else
//				jSonItems += "\"Nombre\": \"" + "\",";
//
//
//			jSonItems += "\"Texto\": \"" + item.getMensaje()+ "\",";
//
//			if (item.getSentDate()!=null)
//				jSonItems += "\"FechaEnvio\": \"" + dTF.format(item.getSentDate()) + "\",";
//			else
//				jSonItems += "\"\": \"" + "\",";
//
//			if (item.getAction()==Constants.SMS_ACTION_INCOME)
//				jSonItems += "\"Action\": \"ENTRANTE\",";
//			else
//				jSonItems += "\"Action\": \"SALIENTE\",";
//
//			if (item.getStatus()!=null)
//				jSonItems += "\"Saldo\": \"" +   item.getStatus().getName() + "\",";
//			else
//				jSonItems += "\"Saldo\": \"\",";
//
//			if (item.getDispatch()!=null)
//				jSonItems += "\"Dispatch\": \"" +   item.getDispatch().getName()+ "\"},";
//			else
//				jSonItems += "\"Dispatch\": \"\"},";
//			}
//			catch(Exception e){}
//		}
//
//		jSonItems = jSonItems.replaceAll(System.getProperty("line.separator"), "");
//
//		if (jSonItems.lastIndexOf(",")>0) jSonItems=jSonItems.substring(0,jSonItems.lastIndexOf(","));
//
//		//totalRegistros=resultados.size();
//		totalRegistros=(long) 100;
//		String strXml = "{\"total\": 1," ;
//		strXml +="\"page\": " + strPage + ",";
//		strXml +="\"records\": " + totalRegistros + ",";
//		strXml +="\"total\": " + totalRegistros/strRows + ",";
//		strXml +="\"rows\": " +"[" + jSonItems + "],";
//		strXml +="\"footer\": " +"[{\"saldo\":" + totalSaldo + ",\"facturas\":" + totalFacturas + ",\"contados\":" + totalContados + ",\"afavor\":" + totalAFavor + "}]}";
//
//		System.out.println(strXml);
//		return strXml;
//	}
//
//	private Date getDateRequest(HttpServletRequest request, String parameter) {
//		try{
//			return DateUtils.parseDate(request.getParameter(parameter), Constants.FORMATO_FECHA_HTML5_REGEX,  Constants.FORMATO_FECHA_HTML5);
//		}catch(ParseException ex){
//			return null;
//		}catch(NullPointerException ex){
//			return null;
//		}
//	}
//

	// Obtenemos un objeto del contexto de la aplicaci�n por su nombre.
	protected Object getApplicationObject(String attrName) {
		return this.getServletContext().getAttribute(attrName);
	}
}