package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.puriarte.convocatoria.persistence.Assignment;
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.convocatoria.persistence.Job;
import com.puriarte.convocatoria.persistence.Person;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.convocatoria.persistence.SMS;
import com.puriarte.gcp.web.Constantes;
import com.puriarte.utils.date.DateUtils;


public class SrvLstDispatch extends HttpServlet {

	private Integer strPage;
	private Integer strRows;
	private String strSort;
	private String strOrder;
	private String orderBy;
	private boolean soloImpagos;
	private Integer category;
	private Integer dispatchStatus;
	private String idRazon;
	private String nroComprobante;
	private Integer nroComprobanteInt;
	private Integer tipoComprobante;
	private int estado;
	private Date  fechaInicio;
	private Date  fechaFin;
	private Date  fechaVencimientoInicio;
	private Date  fechaVencimientoFin;
	private Date  fechaVista;
	private boolean asc;
	private  Long totalRegistros=null;
	private  BigDecimal totalSaldo = new BigDecimal(0);
	private  BigDecimal totalFacturas = new BigDecimal(0);
	private  BigDecimal totalContados = new BigDecimal(0);
	private  BigDecimal totalAFavor = new BigDecimal(0);
	private NumberFormat nF;
	private SimpleDateFormat dTF;
	private String priority;
	private List<String> priorities = new ArrayList<String>();

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


	private void cargarParametros(HttpServletRequest request){

	//	String orderBy = "fMov, ndoc";

		strPage = Integer.parseInt(request.getParameter("page"));
		strRows = Integer.parseInt(request.getParameter("rows"));
		strSort = request.getParameter("sidx");
		strOrder = request.getParameter("sord");

		boolean asc=false;
		if	 ((strOrder!=null)&&(strOrder.equals("asc"))) asc  = true;
		request.getQueryString();
		if ((request.getParameter("soloImpagos")==null ) || (request.getParameter("soloImpagos").toUpperCase().equals("TRUE"))) soloImpagos = true;
		else soloImpagos =false;

		//Armo el criterio en que se quiere ordenar
		if(strSort != null) {
			if(strSort.equals("3"))  orderBy = "cuenta.cliente.nombre";
			else if (strSort.equals("5")) orderBy = "fMov";
			else if (strSort.equals("6")) orderBy = "tipoMov.nombre";
			else if (strSort.equals("7")) orderBy = "ndoc";
			else if (strSort.equals("8")) orderBy = "impMes";
			else if (strSort.equals("9")) orderBy = "p.priority";
			else if (strSort.equals("10")) orderBy = "fVen";
			else if (strSort.equals("11")) orderBy = "impSaldo";
		}

		//	Datos de filtors para la consulta
		category = ((request.getParameter("category")!=null) && NumberUtils.isNumber(request.getParameter("category")))? Integer.parseInt(request.getParameter("category")) : 0;
		dispatchStatus = ((request.getParameter("dispatchStatus")!=null) && NumberUtils.isNumber(request.getParameter("dispatchStatus")))? Integer.parseInt(request.getParameter("dispatchStatus")) : 0;

		priorities =  new ArrayList<String>();

		if (request.getParameter("priority")!=null){
			String[] arPriority = request.getParameter("priority").split(",");
			for(String auxPriority: arPriority){
				if ((auxPriority!=null) && NumberUtils.isNumber(auxPriority)){
					priorities.add(auxPriority);
				}
			}

		}
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Logger  logger = Logger.getLogger(this.getServletName());
		PrintWriter out = response.getWriter();
		int i=0;
		String jSonItems="";

		try {
			cargarParametros(request);
			jSonItems=procesar();
			out.print(jSonItems);

		} catch(Exception e) {
			out.print("{\"error\":[{\"errorID\": \""+ i +"\",\"errtext\": \"" + e.getMessage() + "\"}]}");
		} finally {
			out.close();
		}

	}

	private String procesar() throws Exception {
	 	int pos = (strPage-1) * strRows ;

		List<Dispatch> resultados = Facade.getInstance().selectDispatchList(dispatchStatus, "",  pos,strRows);
		String jSonItems="";
		int i=0;

		for(Dispatch item : resultados) {
			try{
				
				String jsonAssignments = "";
				for (Job job: item.getJobList()){
					Assignment assignment = job.getAssignmentList().get(0);
					jsonAssignments = jsonAssignments + "{\"Id\":\"" + assignment.getId() + 
							"\",\"Movil\":\"" + assignment.getPersonMovil().getMovil().getNumber() + 
							"\",\"Person\":\"" + assignment.getPersonMovil().getPerson().getName() + 
							"\",\"Status\":\"" + assignment.getStatus().getName() + 
							"\",\"AssignmentDate\":\"" + dTF.format(assignment.getAssignmentDate()) + 
							"\",\"PersonCategory\":\"" + assignment.getJob().getCategory().getName() + 
							"\"},";
				}
				if (jsonAssignments.length()>0)
					jsonAssignments = jsonAssignments.substring(0,jsonAssignments.length()-1);

			jSonItems += "{\"Id\": \"" +item.getId()+ "\",";
			jSonItems += "\"Pos\": \"" + i++ + "\",";
			jSonItems += "\"Cliente\": \"" +item.getId()+ "\",";
			jSonItems += "\"IdDoc\": \"" + item.getId() + "\",";
			if (item.getCreationDate()!=null)
				jSonItems += "\"Fecha\": \"" +  dTF.format(item.getCreationDate())  + "\",";
			else
				jSonItems += "\"Fecha\": \"" + "\",";

			if (item.getPlace()!=null)
				jSonItems += "\"Place\": \"" +  item.getPlace().getName() + "\",";
			else
				jSonItems += "\"Place\": \"" + "" + "\",";

			jSonItems += "\"Texto\": \"\",";

			if (item.getScheduledDate()!=null)
				jSonItems += "\"FechaEnvio\": \"" + dTF.format(item.getScheduledDate()) + "\",";
			else
				jSonItems += "\"\": \"" + "\",";

			if (item.getName()!=null)
				jSonItems += "\"Name\": \"" + item.getName() + "\",";
			else
				jSonItems += "\"Name\": \"\",";

			if (item.getDispatchStatus()!=null)
				jSonItems += "\"DispatchStatus\": \"" + item.getDispatchStatus().getName() + "\"";
			else
				jSonItems += "\"DispatchStatus\": \"\"";

			
			jSonItems += ", \"Assignments\" : [" + jsonAssignments + "]},";

			}
			catch(Exception e){}
		}

		jSonItems = jSonItems.replaceAll(System.getProperty("line.separator"), "");

		if (jSonItems.lastIndexOf(",")>0) jSonItems=jSonItems.substring(0,jSonItems.lastIndexOf(","));

		//totalRegistros=resultados.size();
		totalRegistros=(long) 100;
		String strXml = "{\"total\": 1," ;
		strXml +="\"page\": " + strPage + ",";
		strXml +="\"records\": " + totalRegistros + ",";
		strXml +="\"total\": " + totalRegistros/strRows + ",";
		strXml +="\"rows\": " +"[" + jSonItems + "]}";

		System.out.println(strXml);
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