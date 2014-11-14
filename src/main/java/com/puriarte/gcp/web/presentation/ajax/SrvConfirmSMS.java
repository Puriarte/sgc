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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.convocatoria.persistence.SMSIn;
import com.puriarte.convocatoria.persistence.SmsStatus;
import com.puriarte.utils.date.DateUtils;

public class SrvConfirmSMS extends HttpServlet {

	private String data;
	private SimpleDateFormat dTF;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
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
		data =  request.getParameter("data");
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Logger  logger = Logger.getLogger(this.getServletName());
		PrintWriter out = response.getWriter();
		int i=0;
		String jSonItems="";
		try {
			cargarParametros(request);
			try{
				  Object obj=JSONValue.parse(data);
				  JSONObject jsonObj=(JSONObject)obj;

				  if (jsonObj.get("remote_id")!=null)  {
					  int idRef = Integer.parseInt(jsonObj.get("remote_id").toString());
					  SMS sms = Facade.getInstance().selectSMS(idRef);
					  if (jsonObj.get("status").toString().equals("S")){
						  SmsStatus status = Facade.getInstance().selectSmsStatus(Constants.SMS_STATUS_ENVIADO);
						  if (sms!=null){
							  if (jsonObj.get("status")!=null)  sms.setStatus(status);
							  if (jsonObj.get("sent_date")!=null)  sms.setSentDate(parseDate(jsonObj.get("sent_date").toString()));
	//						  if (jsonObj.get("gateway_id")!=null)  sms.set.setGatewayId(jsonObj.get("gateway_id").toString());
							  Facade.getInstance().updateSMS(sms);
							  out.print("0");
						  }else{
							  out.print("1");
						  }
					  }
				  }else
					  out.print("1");
				  
			}catch(Exception e){
				out.print("1");
			}

		} catch(Exception e) {
			out.print("1");
		} finally {
			out.close();
		}

	}
	
	private Date parseDate(String date) {
		try{
			return DateUtils.parseDate(date, Constants.FORMATO_FECHA_HORA_HTML5_REGEX,  Constants.FORMATO_FECHA_HORA_HTML5);
		}catch(ParseException ex){
			return null;
		}catch(NullPointerException ex){
			return null;
		}
	}


	// Obtenemos un objeto del contexto de la aplicaci�n por su nombre.
	protected Object getApplicationObject(String attrName) {
		return this.getServletContext().getAttribute(attrName);
	}
}