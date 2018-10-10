package com.puriarte.gcp.web.presentation.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.Place;

public class SrvLstGridPlace extends RestrictionServlet {

	private static final long serialVersionUID = -4570338988517779347L;
	private static final Logger logger = Logger.getLogger(SrvLstGridPlace.class.getName());

	private static final String ERROR_PLACE_TO_JSON = "Error al pasar el lugar a JSON";
	
	private HashMap<String,Object> cargarParametros(HttpServletRequest request){

		Integer strPage;
		Integer strRows;
		String strSort;
		String strOrder;
		String orderBy="";
		
		Long totalRegistros=null;
		boolean includeDeleted=false;

		strPage = Integer.parseInt(request.getParameter("page"));
		strRows = Integer.parseInt(request.getParameter("rows"));
		strSort = request.getParameter("sidx");
		strOrder = request.getParameter("sord");
		
		//	Datos de filtors para la consulta
		if ((request.getParameter("placeStatus")==null)||(request.getParameter("placeStatus").equals("1")))
			includeDeleted =true;
		else
			includeDeleted =false;

		HashMap<String,Object> paramteros = new HashMap();
		paramteros.put("strPage", strPage);
		paramteros.put("strRows", strRows);
		paramteros.put("strSort", strSort);
		paramteros.put("strOrder", strOrder);
		paramteros.put("orderBy", orderBy);
		paramteros.put("totalRegistros", totalRegistros);
		paramteros.put("includeDeleted", includeDeleted);
		
		return paramteros;
	}

	public void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter out = response.getWriter();
		int i=0;
		String jSonItems="";

		try {
			HashMap<String, Object> parametros =cargarParametros(request);
			jSonItems=procesar(parametros );

			response.setContentType("text/plain");
	        response.setCharacterEncoding("UTF-8");
			out.print(jSonItems);

		} catch(Exception e) {
			logger.error(e.getMessage());
			out.print("{\"error\":[{\"errorID\": \""+ i +"\",\"errtext\": \"" + e.getMessage() + "\"}]}");
		} finally {
			out.close();
		}

	}

	private String procesar(HashMap<String, Object> parametros) throws Exception {
		
		List<Place> resultados = Facade.getInstance().selectPlaceList((boolean) parametros.get("includeDeleted"));

		String jSonItems="";
		int i=0;

		for(Place item : resultados) {
			try{

				jSonItems += "{\"Id\": \"" +item.getId()+ "\",";
				jSonItems += "\"Pos\": \"" + i++ + "\",";
	
				if (item.getAddress()!=null)
					jSonItems += "\"Address\": \"" + item.getAddress()+ "\",";
				else
					jSonItems += "\"Address\": \",";
	
				if (item.getPhone()!=null)
					jSonItems += "\"Phone\": \"" + item.getPhone() + "\",";
				else
					jSonItems += "\"Phone\": \",";
	
				jSonItems += "\"Deleted\": \"" + item.isDeleted() + "\",";
	
				if (item.getName()!=null)
					jSonItems += "\"Name\": \"" + item.getName() + "\"},";
				else
					jSonItems += "\"Name\": \"},";

			}catch(Exception e){
				logger.error(ERROR_PLACE_TO_JSON + e.getMessage());
			}
		}

		jSonItems = jSonItems.replaceAll(System.getProperty("line.separator"), "");
	
		if (jSonItems.lastIndexOf(",")>0) jSonItems=jSonItems.substring(0,jSonItems.lastIndexOf(","));
	
		long totalRegistros=(long) 100;
		
		String strXml = "{\"total\": 1," ;
		strXml +="\"page\": " + (int) parametros.get("strPage") + ",";
		strXml +="\"records\": " + totalRegistros + ",";
		strXml +="\"total\": " + totalRegistros/(int) parametros.get("strRows") + ",";
		strXml +="\"rows\": " +"[" + jSonItems + "]";
		strXml +="}";
	
		return strXml;
	}
}