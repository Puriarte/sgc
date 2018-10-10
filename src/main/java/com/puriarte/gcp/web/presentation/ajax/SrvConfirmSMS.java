package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.convocatoria.persistence.SmsStatus;
import com.puriarte.utils.date.DateUtils;

public class SrvConfirmSMS extends RestrictionServlet {

	private static final long serialVersionUID = 8972986289111689739L;
	private static final Logger logger = Logger.getLogger(SrvConfirmSMS.class.getName());

	
	/**
	 * @param request
	 */
	private String cargarParametros(HttpServletRequest request){
		return request.getParameter("data");
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter out = response.getWriter();

		try {
			String data = cargarParametros(request);
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
							  out.print(Constants.RESULTADO_CONFIRM_SMS_OK);
						  }else{
							  out.print(Constants.RESULTADO_CONFIRM_SMS_FALLA);
						  }
					  }
				  }else
					  out.print(Constants.RESULTADO_CONFIRM_SMS_FALLA);
				  
			}catch(Exception e){
				logger.error(Constants.ERROR_CONFIRM_SMS + e.getMessage());
				out.print(Constants.RESULTADO_CONFIRM_SMS_FALLA);
			}

		} catch(Exception e) {
			logger.error(Constants.ERROR_CONFIRM_SMS + e.getMessage());
			out.print(Constants.RESULTADO_CONFIRM_SMS_FALLA);
		} finally {
			out.close();
		}
	}
	
	private Date parseDate(String date) {
		try{
			return DateUtils.parseDate(date, Constants.FORMATO_FECHA_HORA_HTML5_REGEX,  Constants.FORMATO_FECHA_HORA_HTML5);
		}catch(ParseException ex){
			logger.error(Constants.ERROR_PARSE_DATE + ex.getMessage());
			return null;
		}catch(NullPointerException ex){
			logger.error(Constants.ERROR_PARSE_DATE + ex.getMessage());
			return null;
		}
	}

	// Obtenemos un objeto del contexto de la aplicaci�n por su nombre.
	protected Object getApplicationObject(String attrName) {
		return this.getServletContext().getAttribute(attrName);
	}
}