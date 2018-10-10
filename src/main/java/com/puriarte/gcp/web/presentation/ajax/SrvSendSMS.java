package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.SMS;

public class SrvSendSMS extends RestrictionServlet {

	private static final long serialVersionUID = -4923357222559910378L;
	private static final Logger logger = Logger.getLogger(SrvSendSMS.class.getName());


	private HashMap<String,Object> cargarParametros(HttpServletRequest request){
		String gatewayId;
		String date;
		String text;
		String refNo;
		String recipient;
		String from;
		String failureCause;
		String messageStatus;
		String originator;
		
		gatewayId = request.getParameter("gatewayId");
		date = request.getParameter("date");
		text = request.getParameter("text");
		refNo = request.getParameter("refNo");
		recipient = request.getParameter("recipient");
		from = request.getParameter("from");
		failureCause = request.getParameter("failureCause");
		messageStatus = request.getParameter("messageStatus");
		originator = request.getParameter("originator");
		
		HashMap paramteros = new HashMap<String,Object>();
		paramteros.put("gatewayId", gatewayId);
		paramteros.put("date", date);
		paramteros.put("text", text);
		paramteros.put("refNo", refNo);
		paramteros.put("recipient", recipient);
		paramteros.put("from", from);
		paramteros.put("failureCause", failureCause);
		paramteros.put("messageStatus", messageStatus);
		paramteros.put("originator", originator);
		
		return paramteros;
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Logger  logger = Logger.getLogger(this.getServletName());

		SimpleDateFormat dTF=new SimpleDateFormat("dd/MM/yy HH:mm", new Locale("ES"));
		dTF = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", new Locale("ES"));

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
				msg.put("createDate", dTF.format(sms.getCreationDate()));
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