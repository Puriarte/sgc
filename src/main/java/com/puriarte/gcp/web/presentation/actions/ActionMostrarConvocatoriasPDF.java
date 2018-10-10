package com.puriarte.gcp.web.presentation.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.utils.date.DateUtils;

public class ActionMostrarConvocatoriasPDF  extends HttpServlet {

	private static Charset encodingImp = Charset.forName("UTF-8");
	private static Logger  logger = Logger.getLogger(ActionMostrarConvocatoriasPDF.class.getName());
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActionMostrarConvocatoriasPDF() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			Date fechaDesde= com.puriarte.utils.date.DateUtils.parseDate(request.getParameter("fechaDesde"), Constants.FORMATO_FECHA_HTML5_REGEX, Constants.FORMATO_FECHA_HTML5);
			Date fechaHasta= com.puriarte.utils.date.DateUtils.parseDate(request.getParameter("fechaHasta"), Constants.FORMATO_FECHA_HTML5_REGEX, Constants.FORMATO_FECHA_HTML5);

			devolverClienteComoPDF(response, Integer.parseInt(request.getParameter("idPerson")),fechaDesde,fechaHasta);
		}catch(Exception e){
			try {
				response.sendRedirect("error.html");
			}catch(Exception re) {
				logger.error(re.getMessage());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			Date fechaDesde= com.puriarte.utils.date.DateUtils.parseDate(request.getParameter("fechaDesde"), Constants.FORMATO_FECHA_HTML5_REGEX, Constants.FORMATO_FECHA_HTML5);
			Date fechaHasta= com.puriarte.utils.date.DateUtils.parseDate(request.getParameter("fechaHasta"), Constants.FORMATO_FECHA_HTML5_REGEX, Constants.FORMATO_FECHA_HTML5);

			devolverClienteComoPDF(response, Integer.parseInt(request.getParameter("idPerson")),fechaDesde,fechaHasta);
		}catch(Exception e){
			try {
				response.sendRedirect("error.html");
			}catch(Exception re) {
				logger.error(re.getMessage());
			}
		}
	}

	/**
	 * Devuelve la escolaridad de persona  como PDF
	 *
	 * @param response
	 * @param idPerson
	 * @throws Exception
	 */
	private void devolverClienteComoPDF(HttpServletResponse response,
			int idPerson, Date fechaDesde, Date fechaHasta) throws Exception {

        String contextPath, contextOutputPath, contextFacesPath;
		PropertiesConfiguration config = new PropertiesConfiguration(com.puriarte.gcp.web.Constantes.PATHAPPCONFIG);
		String path= config.getString("data.folder") ;
        String path_repo = config.getString("repo.folder") ;
        
 
        if (getServletContext().getRealPath("")==null){
        	contextPath = path_repo + "src/main/webapp/";
        	contextOutputPath = path  + "reportes/";
        	contextFacesPath = path  + "faces/";
    	}else{
            contextPath = getServletContext().getRealPath("") + "/";//.getRealPath(File.separator);
            contextOutputPath =  getServletContext().getRealPath("")+ "/";
            contextFacesPath = getServletContext().getRealPath("")+ "/images/faces/";
    	}
		
	
		Logger  logger = Logger.getLogger(ActionMostrarEscolaridadPDF.class.getName());
		// Genero el pdf

		SimpleDateFormat df3 = new SimpleDateFormat("yyyyMMdd");
		String fechaActual = df3.format(new Date());

		PersonMovil personMovil = Facade.getInstance().selectPersonMovil(idPerson);
		String picture ="";
		if (personMovil.getPerson().getPicture()!=null)
			picture = personMovil.getPerson().getPicture();
		
		
		Map<String, Object> pars = new HashMap<String,Object>();
		pars.put("REPORT_LOCALE", new java.util.Locale("es","ES"));
		pars.put("P_ID_PERSON", idPerson);
		pars.put("P_PICTURE",  contextFacesPath + "flag_mediana_" + picture + ".jpg");
		pars.put("P_FECHA_DESDE",  DateUtils.formatDate(fechaDesde,   Constants.FORMATO_FECHA_HTML5_ALT2));
		pars.put("P_FECHA_HASTA",  DateUtils.formatDate(fechaHasta,   Constants.FORMATO_FECHA_HTML5_ALT2));

		//		JasperCompileManager.compileReportToFile(
//				contextPath + "/templates/articulos_por_farmacia.jrxml",
//                contextPath + "/templates/33.jasper");

		String jasperFileName = contextPath + "/templates/articulos_por_farmacia.jasper";

		File repoPrePagoFile = new File( jasperFileName);
		JasperReport jasperReport = (JasperReport)JRLoader.loadObject(repoPrePagoFile.getPath());

		Connection connection  = com.puriarte.gcp.resources.ConnectionManager.establishConnection2();
		JasperPrint jasperPrint=JasperFillManager.fillReport (jasperReport, pars, connection);

		//		ArrayList<JasperPrint> list = new  ArrayList<JasperPrint>();
		//		list.add(jasperPrint);// list.add(jasperPrint2);
		//
		String nombreArchivoPDF= idPerson + "_" + fechaActual + ".pdf";
		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(contextPath + "/temp/" + nombreArchivoPDF)); // your output goes here
		//			
		exporter.exportReport();
		//

		// Devuelvo el archivo
		File pdfFile = new File(contextPath + "/temp/" + nombreArchivoPDF);

		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "inline; filename=" + nombreArchivoPDF);
		response.setContentLength((int) pdfFile.length());

		FileInputStream fileInputStream = new FileInputStream(pdfFile);
		try{
			OutputStream responseOutputStream = response.getOutputStream();
			int bytes;
			while ((bytes = fileInputStream.read()) != -1) {
				responseOutputStream.write(bytes);
			}
		}finally{
			fileInputStream.close();
		}

	}

}

