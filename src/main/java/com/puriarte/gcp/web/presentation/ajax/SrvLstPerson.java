package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.result.PersonMovilResult;
import com.puriarte.utils.date.DateUtils;

public class SrvLstPerson extends RestrictionServlet {

	private static final long serialVersionUID = -4522900707705266638L;
	private static final Logger logger = Logger.getLogger(SrvLstPerson.class.getName());

	private HashMap<String,Object> cargarParametros(HttpServletRequest request){

		Integer strPage;
		Integer strRows;
		String strSort;
		String strOrder;
		String orderBy="";
		boolean soloImpagos;

		List<String> priorities = new ArrayList<String>();
		List<String> categories= new ArrayList<String>();

		strPage = Integer.parseInt(request.getParameter("page"));
		strRows = Integer.parseInt(request.getParameter("rows"));
		strSort = request.getParameter("sidx");
		strOrder = request.getParameter("sord");

		request.getQueryString();
		if ((request.getParameter("soloImpagos")==null ) || (request.getParameter("soloImpagos").toUpperCase().equals("TRUE"))) soloImpagos = true;
		else soloImpagos =false;

		//Armo el criterio en que se quiere ordenar
		if(strSort != null) {
			if(strSort.equals("4"))  orderBy = "movil_number"; // + strOrder;
			else if (strSort.equals("5")) orderBy = "document_type,document_number" ; //+ strOrder;
			else if (strSort.equals("6")) orderBy = "document_number" ; //+ strOrder;
			else if (strSort.equals("7")) orderBy = "person_name" ; //+ strOrder;
			else if (strSort.equals("8")) orderBy = "person_nickname" ; //+ strOrder;
			else if (strSort.equals("10")) orderBy = "category_name" ; //+ strOrder;
			else if (strSort.equals("11")) orderBy = "priority"; // + strOrder;
			else  orderBy = "person_name"; // + strOrder;
		}

		priorities =  new ArrayList<String>();
		if (request.getParameter("priority")!=null){
			String[] arPriority = request.getParameter("priority").split(",");
			for(String auxPriority: arPriority){
				if ((auxPriority!=null) && NumberUtils.isNumber(auxPriority)){
					priorities.add(auxPriority);
				}
			}
		}
		
		categories =  new ArrayList<String>();
		if (request.getParameter("category")!=null){
			String[] arCategories= request.getParameter("category").split(",");
			for(String auxCategory: arCategories){
				if ((auxCategory!=null) && NumberUtils.isNumber(auxCategory)){
					categories.add(auxCategory);
				}
			}

		}
		
		HashMap parametros = new HashMap<String,Object>();
		parametros.put("strPage", strPage);
		parametros.put("strRows", strRows);
		parametros.put("strSort", strSort);
		parametros.put("orderBy", orderBy);
		parametros.put("soloImpagos", soloImpagos);
		parametros.put("priorities", priorities);
		parametros.put("categories", categories);

		return parametros;

	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Logger  logger = Logger.getLogger(this.getServletName());
		PrintWriter out = response.getWriter();
		int i=0;
		String jSonItems="";

		try {
			HashMap<String, Object> parametros =cargarParametros(request);
			jSonItems=procesar(parametros);

			response.setContentType("text/plain");
	        response.setCharacterEncoding("UTF-8");
	        out.print(jSonItems);

		} catch(Exception e) {
			out.print("{\"error\":[{\"errorID\": \""+ i +"\",\"errtext\": \"" + e.getMessage() + "\"}]}");
		} finally {
			out.close();
		}

	}

	private String procesar(HashMap<String, Object> parametros) throws Exception {
		
		String parOrderBy = "";
		
		if (parametros.get("strOrder")!=null) parOrderBy= (String)parametros.get("strOrder");
		
		List<PersonMovilResult> resultados = Facade.getInstance().selectPersonMovilList((List<String>)parametros.get("priorities"), (List<String>)parametros.get("categories") ,
				Constants.PERSON_STATUS_ALL, (String)parametros.get("orderBy"),parOrderBy,   null,null);
		String jSonItems="";
		int i=0;

		for(PersonMovilResult item : resultados) {
			try{
				
				jSonItems += "{\"Id\": \"" +item.getId()+ "\",";
				jSonItems += "\"IdPerson\": \"" + item.getPersonId() + "\",";
				jSonItems += "\"Pos\": \"" + i++ + "\",";
				jSonItems += "\"Cliente\": \"" +item.getId()+ "\",";
				jSonItems += "\"IdDoc\": \"" + item.getId() + "\",";
				jSonItems += "\"Fecha\": \"" + "\",";
				jSonItems += "\"Numero\": \"" +  item.getMovilNumber() + "\",";
				jSonItems += "\"Texto\": \"" + item.getPersonDocumentNumber() + "\",";
				jSonItems += "\"FechaEnvio\": \"" + item.getDocumentTypeName()+ "\",";
				jSonItems += "\"Name\": \"" + item.getPersonName() + "\",";
				jSonItems += "\"Nickname\": \"" + item.getPersonNickName()  + "\",";
				if ((item.getPersonPicture()!=null)&&(!item.getPersonPicture().equals("")))
					jSonItems += "\"Picture\": \"" + Constants.PICTURE_PREFIX_CHICA + item.getPersonPicture()  + "\",";
				else
					jSonItems += "\"Picture\": \"" + Constants.PICTURE_EMPTY_CHICA  + "\",";
					
				jSonItems += "\"Category\": \"" + item.getCategoryNames()  + "\",";
				jSonItems += "\"PreferedCategory\": \"" + item.getPreferedCategoryName()  + "\",";
				jSonItems += "\"Priority\": \"" + ((item.getPriority()<0)?"":item.getPriority().toString()) + "\",";
				jSonItems += "\"Saldo\": \"\"},";
				}
				catch(Exception e){}
		}
		
		jSonItems = jSonItems.replaceAll(System.getProperty("line.separator"), "");

		if (jSonItems.lastIndexOf(",")>0) jSonItems=jSonItems.substring(0,jSonItems.lastIndexOf(","));

		long totalRegistros=(long) resultados.size();

	/*	String strXml = "{\"total\": 1," ;
		strXml +="\"page\": " + strPage + ",";
		strXml +="\"records\": " + totalRegistros + ",";
		strXml +="\"total\": " + totalRegistros/strRows + ",";
		strXml +="\"rows\": " +"[" + jSonItems + "],";
		strXml +="\"footer\": " +"[{\"saldo\":" + totalSaldo + ",\"facturas\":" + totalFacturas + ",\"contados\":" + totalContados + ",\"afavor\":" + totalAFavor + "}]}";
*/
		String strXml = "{";
		strXml +="\"rows\": " +"[" + jSonItems + "]}";

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