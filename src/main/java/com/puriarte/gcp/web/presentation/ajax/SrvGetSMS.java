package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
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
import com.puriarte.convocatoria.persistence.SMSIn;
import com.puriarte.utils.date.DateUtils;

public class SrvGetSMS extends HttpServlet {

	private String pduUserData;
	private String gatewayId;
	private String text;
	private Date messageDate;
	private String originator;
	private char encoding;
	private String type;
	private NumberFormat nF;
	private SimpleDateFormat dTF;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		nF = NumberFormat.getNumberInstance(new Locale("ES"));
		nF.setMinimumFractionDigits(2);
		dTF = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", new Locale("ES"));
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
		try{
			encoding =  request.getParameter("encoding").charAt(0);			
		}catch(Exception e){		
		}
		pduUserData =  request.getParameter("pduUserData");
		gatewayId =  request.getParameter("gatewayId");
		text =  request.getParameter("text");
		try{
			messageDate =  dTF.parse(request.getParameter("messageDate"));			
		}catch(Exception e){
			
		}
		originator =  request.getParameter("originator");
		type = request.getParameter("type");
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Logger  logger = Logger.getLogger(this.getServletName());
		PrintWriter out = response.getWriter();
		int i=0;
		Timestamp receiveDate = new Timestamp(new java.util.Date().getTime());

		try {
			cargarParametros(request);
			SMSIn sms = new SMSIn();
			sms.setEncoding(encoding);
			sms.setGatewayId(gatewayId);
			sms.setMessageDate(messageDate);
			sms.setOriginator(originator);
			sms.setReceiveDate(receiveDate);
			sms.setText(text);
			sms.setType(type);
			Facade.getInstance().insertSMSIn(sms);
			out.print("0");

		} catch(Exception e) {
			out.print("1");
		} finally {
			out.close();
		}

	}

	// Obtenemos un objeto del contexto de la aplicaci�n por su nombre.
	protected Object getApplicationObject(String attrName) {
		return this.getServletContext().getAttribute(attrName);
	}
}