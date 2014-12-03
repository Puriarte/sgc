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
import com.puriarte.utils.date.DateUtils;

public class SrvGetSMS extends HttpServlet {

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

				  SMSIn sms = new SMSIn();
				  if (jsonObj.get("process")!=null)  sms.setProcess(Integer.parseInt(jsonObj.get("process").toString()));
				  if (jsonObj.get("id")!=null)  sms.setId(Long.parseLong(jsonObj.get("id").toString()));
				  if (jsonObj.get("text")!=null)  sms.setText(jsonObj.get("text").toString());
				  if (jsonObj.get("message_date")!=null)  sms.setMessageDate(parseDate((jsonObj.get("message_date").toString().substring(0, 19))));
				  if (jsonObj.get("gateway_id")!=null)  sms.setGatewayId(jsonObj.get("gateway_id").toString());
				  if (jsonObj.get("original_receive_date")!=null)  sms.setOriginalRecibeDate(parseDate(jsonObj.get("original_receive_date").toString().substring(0, 19)));
				  if (jsonObj.get("encoding")!=null)  sms.setEncoding(jsonObj.get("encoding").toString().charAt(0));
				  if (jsonObj.get("type")!=null)  sms.setType(jsonObj.get("type").toString());
				  if (jsonObj.get("receive_date")!=null)  sms.setReceiveDate(parseDate(jsonObj.get("receive_date").toString().substring(0, 19)));
				  if (jsonObj.get("originator")!=null)  sms.setOriginator(jsonObj.get("originator").toString());
				  if (jsonObj.get("original_ref_no")!=null)  sms.setOriginalRefNo(jsonObj.get("original_ref_no").toString());
				  if (jsonObj.get("uu_id")!=null) sms.setUUId(jsonObj.get("uu_id").toString());

				  // Si el SMS se inicia con el texto REGISTRO creo el movil en la base de datos.
				  if (sms.getText().toUpperCase().startsWith("REGISTRO")){
					  Facade.getInstance().insertSMSIncomeAndRegisterMovil(sms.getOriginator(), sms.getText(),sms.getReceiveDate(), jsonObj.get("uu_id").toString());
				  }else{
					  Facade.getInstance().insertSMSIncome(
							  sms.getOriginator(), 
							  sms.getText(), 
							  sms.getReceiveDate(),
							  jsonObj.get("uu_id").toString(),
							  "SI , OK, VOY, CONFIRMADO".split(","),
							  "OK , VOY, AHI ESTARE ".split(","),
							  "NO , YA TENGO , YA TGO, NO PUEDO ".split(","),
							  "YA TENGO , YA TGO, NO PUEDO ".split(","));
				  }
				  out.print("0");
				  
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