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

public class SrvLstSMS extends HttpServlet {


	private Integer strPage;
	private Integer strRows;
	private String strSort;
	private String strOrder;
	private String orderBy;
	private int estado;
	private int convocatoria;
	private Date  fechaInicio;
	private Date  fechaFin;
	private boolean asc;

	private  Long totalRegistros=null;
	private  BigDecimal totalSaldo = new BigDecimal(0);
	private  BigDecimal totalFacturas = new BigDecimal(0);
	private  BigDecimal totalContados = new BigDecimal(0);
	private  BigDecimal totalAFavor = new BigDecimal(0);
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
		if (strSort.equals("13")) orderBy = "dispatch"; 
		
		fechaInicio = getDateRequest(request, "fechaDesde");
		fechaFin = getDateRequest(request, "fechaHasta");

		if (fechaFin!=null){
			fechaFin.setHours(23);
			fechaFin.setMinutes(59);
		}

		estado = ((request.getParameter("estado")!=null) && NumberUtils.isNumber(request.getParameter("estado")))? Integer.parseInt(request.getParameter("estado")) : 0;
		convocatoria = ((request.getParameter("convocatoria")!=null) && NumberUtils.isNumber(request.getParameter("convocatoria")))? Integer.parseInt(request.getParameter("convocatoria")) : 0;

	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Logger  logger = Logger.getLogger(this.getServletName());
		PrintWriter out = response.getWriter();
		int i=0;
		String jSonItems="";

		try {
			cargarParametros(request);

			int count = Facade.getInstance().selectCountSMS(fechaInicio,fechaFin,estado,convocatoria);
			List<SMS> resultados = Facade.getInstance().selectSMSList(fechaInicio,fechaFin,estado,convocatoria, orderBy, strOrder, (strPage-1)*strRows,strRows);
			jSonItems= procesarItems2(resultados, count);
			out.print(jSonItems);

		} catch(Exception e) {
			out.print("{\"error\":[{\"errorID\": \""+ i +"\",\"errtext\": \"" + e.getMessage() + "\"}]}");
		} finally {
			out.close();
		}
	}

	private String procesarItems2(List<SMS> resultados, int totalRegistros) {
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
				jSonItems += "\"Nombre\": \"" + "\",";


			jSonItems += "\"Texto\": \"" + item.getMensaje()+ "\",";

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

			if (item.getDispatch()!=null)
				jSonItems += "\"Dispatch\": \"" +   item.getDispatch().getName()+ "\"},";
			else
				jSonItems += "\"Dispatch\": \"\"},";
			}
			catch(Exception e){}
		}

		jSonItems = jSonItems.replaceAll(System.getProperty("line.separator"), "");

		if (jSonItems.lastIndexOf(",")>0) jSonItems=jSonItems.substring(0,jSonItems.lastIndexOf(","));


		int totalPaginas=  totalRegistros/strRows;
		if ((totalRegistros % strRows)!=0) totalPaginas++;
			
		String strXml = "{\"total\": " + totalPaginas + ", ";
		strXml +="\"page\": " + strPage + ",";
		strXml +="\"records\": " + totalRegistros + ",";
		strXml +="\"rows\": " +"[" + jSonItems + "]}";

//		System.out.println(strXml);
		return strXml;
	}

	private Date getDateRequest(HttpServletRequest request, String parameter) {
		try{
			return DateUtils.parseDate(request.getParameter(parameter), Constants.FORMATO_FECHA_HTML5_REGEX,  Constants.FORMATO_FECHA_HTML5);
		}catch(ParseException ex){
			return null;
		}catch(NullPointerException ex){
			return null;
		}
	}


	// Obtenemos un objeto del contexto de la aplicación por su nombre.
	protected Object getApplicationObject(String attrName) {
		return this.getServletContext().getAttribute(attrName);
	}
}