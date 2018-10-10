package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.puriarte.convocatoria.persistence.Dispatch;
import com.puriarte.utils.date.DateUtils;

public class SrvLstAssignment extends RestrictionServlet {

	private static final long serialVersionUID = -1804619602485501036L;
	private static final Logger logger = Logger.getLogger(SrvLstAssignment.class.getName());

	// Literals
	private static final String PAR_SOLO_IMPAGO = "soloImpagos";
	private static final String PAR_SOLO_IMPAGO_VALUE_TRUE = "TRUE";

	private static final String PAR_PAGE = "page";
	private static final String PAR_ROWS= "rows";
	private static final String PAR_SIDX = "sidx";
	private static final String PAR_SORD = "sord";

	private  HashMap<String,Object> cargarParametros(HttpServletRequest request){

		HashMap paramteros = new HashMap<String,Object>();

		List<String> priorities = new ArrayList<String>();
		
		Integer strPage;
		Integer strRows;
		String strSort;
		String strOrder;
		String orderBy="";
		boolean soloImpagos;
		Integer category;
		boolean asc=false;
		Long totalRegistros=null;

		strPage = Integer.parseInt(request.getParameter(PAR_PAGE));
		strRows = Integer.parseInt(request.getParameter(PAR_ROWS));
		strSort = request.getParameter(PAR_SIDX);
		strOrder = request.getParameter(PAR_SORD);

		if	 ((strOrder!=null)&&(strOrder.equals("asc"))) asc  = true;
		request.getQueryString();
		if ((request.getParameter(PAR_SOLO_IMPAGO)==null ) || (request.getParameter(PAR_SOLO_IMPAGO).toUpperCase().equals(PAR_SOLO_IMPAGO_VALUE_TRUE))) soloImpagos = true;
		else soloImpagos =false;

		//Armo el criterio en que se quiere ordenar
		if(strSort != null) {
			if(strSort.equals("3"))  orderBy = "number";
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

		priorities =  new ArrayList<String>();

		if (request.getParameter("priority")!=null){
			String[] arPriority = request.getParameter("priority").split(",");
			for(String auxPriority: arPriority){
				if ((auxPriority!=null) && NumberUtils.isNumber(auxPriority)){
					priorities.add(auxPriority);
				}
			}
		}

		paramteros.put("strPage", strPage);
		paramteros.put("strRows", strRows);
		paramteros.put("strSort", strSort);
		paramteros.put("strOrder", strOrder);
		paramteros.put("orderBy", orderBy);
		paramteros.put("soloImpagos", soloImpagos);
		paramteros.put("category", category);
		paramteros.put("asc", asc);
		paramteros.put("totalRegistros", totalRegistros);
		paramteros.put("priorities", priorities);
		
		return paramteros;
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Logger  logger = Logger.getLogger(this.getServletName());
		PrintWriter out = response.getWriter();
		int i=0;
		String jSonItems="";

		try {
			HashMap<String,Object> paramteros = cargarParametros(request);
			jSonItems=procesar(paramteros);
			out.print(jSonItems);

		} catch(Exception e) {
			out.print("{\"error\":[{\"errorID\": \""+ i +"\",\"errtext\": \"" + e.getMessage() + "\"}]}");
		} finally {
			out.close();
		}

	}

	private String procesar(HashMap<String, Object> paramteros) throws Exception {
	 	int pos = ((int)paramteros.get("strPage")-1)*((int)paramteros.get("strRows"));

	 	NumberFormat nF = NumberFormat.getNumberInstance(new Locale("ES"));
		nF.setMinimumFractionDigits(2);
		
		SimpleDateFormat dTF = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", new Locale("ES"));

	 	
		List<Dispatch> resultados = Facade.getInstance().selectDispatchList(0, "", (boolean)paramteros.get("asc"), pos, (int)paramteros.get("strRows"));
		String jSonItems="";
		int i=0;

		for(Dispatch item : resultados) {
			try{

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
				jSonItems += "\"Name\": \"" + item.getName() + "\"";
			else
				jSonItems += "\"Name\": \"\"";

			jSonItems += "},";

			}
			catch(Exception e){}
		}

		jSonItems = jSonItems.replaceAll(System.getProperty("line.separator"), "");

		if (jSonItems.lastIndexOf(",")>0) jSonItems=jSonItems.substring(0,jSonItems.lastIndexOf(","));

		//totalRegistros=resultados.size();
//		totalRegistros=(long) 100;
//		String strXml = "{\"total\": 1," ;
//		strXml +="\"page\": " + strPage + ",";
//		strXml +="\"records\": " + totalRegistros + ",";
//		strXml +="\"total\": " + totalRegistros/strRows + ",";
//		strXml +="\"rows\": " +"[" + jSonItems + "]}";
		String strXml = "{[" + jSonItems + "]}";

	//	System.out.println(strXml);
		return strXml;
	}

	protected Date getDateRequest(HttpServletRequest request, String parameter) {
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