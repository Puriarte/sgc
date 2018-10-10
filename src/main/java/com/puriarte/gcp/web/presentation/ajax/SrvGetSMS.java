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
import com.puriarte.convocatoria.persistence.SMSIn;
import com.puriarte.utils.date.DateUtils;
//http://localhost:8083/ROOT/getSMS?data={"id":"88901","encoding":"7","gateway_id":"myModem","message_date":"2017-09-22 17:00:19.665","original_receive_date":null,"original_ref_no":null,"originator":"098312914","process":"0","receive_date":"2017-09-22 17:00:39.805","text":"Si","type":"I","uu_id":"b2c1ffe7-f15c-481e-9b27-387a2961a9a3"}
public class SrvGetSMS extends RestrictionServlet {

	private static final long serialVersionUID = 3958917298766805392L;
	private static final Logger logger = Logger.getLogger(SrvGetSMS.class.getName());

	/**
	 * @param request
	 */
	private String cargarParametros(HttpServletRequest request){
		return  request.getParameter("data");
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter out = response.getWriter();
		
		try {
			String data=cargarParametros(request);
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
				  if (jsonObj.get("originator")!=null)  {
					  if (jsonObj.get("originator").toString().startsWith("598"))
						  sms.setOriginator("0" + jsonObj.get("originator").toString().substring(3));
					  else
						  sms.setOriginator(jsonObj.get("originator").toString());						  
				  }
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

				  out.print(Constants.RESULTADO_GET_SMS_OK);
				  
			}catch(Exception e){
				logger.error(Constants.ERROR_GET_SMS + e.getMessage());
				out.print(Constants.RESULTADO_GET_SMS_FALLA);
			}
		} catch(Exception e) {
			logger.error(Constants.ERROR_GET_SMS + e.getMessage());
			out.print(Constants.RESULTADO_GET_SMS_FALLA);
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