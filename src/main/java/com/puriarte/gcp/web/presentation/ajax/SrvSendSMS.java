package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.utils.date.DateUtils;

public class SrvSendSMS extends HttpServlet {

	private String gatewayId;
	private String date;
	private String text;
	private String refNo;
	private String recipient;
	private String from;
	private String failureCause;
	private String messageStatus;
	private String originator;
	
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
		gatewayId = request.getParameter("gatewayId");
		date = request.getParameter("date");
		text = request.getParameter("text");
		refNo = request.getParameter("refNo");
		recipient = request.getParameter("recipient");
		from = request.getParameter("from");
		failureCause = request.getParameter("failureCause");
		messageStatus = request.getParameter("messageStatus");
		originator = request.getParameter("originator");
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Logger  logger = Logger.getLogger(this.getServletName());
		PrintWriter out = response.getWriter();
		int i=0;
		String jSonItems="";

		try {
			cargarParametros(request);
			JSONArray list = new JSONArray();
			
			List<SMS> smsOutList = Facade.getInstance().getPendingSMS();
			for(SMS sms : smsOutList){
				Map msg = new LinkedHashMap();
				msg.put("id", sms.getId());
				msg.put("text", sms.getMensaje());
				msg.put("recipient", sms.getPersonMovil().getMovil().getNumber());
				msg.put("createDate", sms.getCreationDate());
				list.add(msg);				
			}
			
			StringWriter wrt = new StringWriter();
			list.writeJSONString(wrt);
			out.print(wrt.toString());

		} catch(Exception e) {
			out.print("");
		} finally {
			out.close();
		}

	}

	// Obtenemos un objeto del contexto de la aplicaci�n por su nombre.
	protected Object getApplicationObject(String attrName) {
		return this.getServletContext().getAttribute(attrName);
	}
}