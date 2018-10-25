package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class SrvNotificaciones extends RestrictionServlet {

	private static final long serialVersionUID = -3004459801062075913L;
	private static final Logger logger = Logger.getLogger(SrvNotificaciones.class.getName());


	private void actualizarFechaUltimaNotificacion() {
		getServletContext().setAttribute("fechaUntlimaNotificacion", new Date());
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		SimpleDateFormat dTF;
		dTF = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", new Locale("ES"));

		Date fechaUltimaNotificacion;
		PrintWriter out = response.getWriter();

		try {
			if (getServletContext().getAttribute("fechaUntlimaNotificacion")== null) 
				actualizarFechaUltimaNotificacion();
					
			fechaUltimaNotificacion= (Date) getServletContext().getAttribute("fechaUntlimaNotificacion");

			JSONArray list = new JSONArray();
			
			List<SMS> smsOutList = Facade.getInstance().getIncomingSMS(fechaUltimaNotificacion);

			actualizarFechaUltimaNotificacion();
			
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
			logger.error(e.getMessage());
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