package com.puriarte.gcp.web.presentation.actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;

import com.puriarte.convocatoria.core.domain.Constants;

public class ActionMostrarConvocatoriasPDF  extends HttpServlet {

	private static Charset encodingImp = Charset.forName("UTF-8");

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
			response.sendRedirect("error.html");
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

		String contextPath = getServletContext().getRealPath("");//.getRealPath(File.separator);
		if (contextPath==null)
			contextPath = System.getenv("OPENSHIFT_REPO_DIR") + "/src/main/webapp/";
		//String contextPath = "/var/lib/openshift/5460b7b1500446bdfa0003f7/app-root/runtime/repo/src/main/webapp";
		Logger  logger = Logger.getLogger(ActionMostrarEscolaridadPDF.class.getName());
		// Genero el pdf

		SimpleDateFormat df3 = new SimpleDateFormat("yyyyMMdd");
		String fechaActual = df3.format(new Date());

		//		response.setHeader ("Content-Disposition", "attachment; filename=" + idPerson + "_" + fechaActual + ".pdf"); //Configurar cabecera http
		//		response.setHeader ("Pragma", "No-cache");
		//		response.setDateHeader ("Expires", 0);

		Map<String, Object> pars = new HashMap<String,Object>();
		pars.put("REPORT_LOCALE", new java.util.Locale("es","ES"));
		pars.put("P_ID_PERSON", idPerson);

		String jasperFileName = contextPath + "/templates/articulos_por_farmacia.jasper";

		File repoPrePagoFile = new File( jasperFileName);
		JasperReport jasperReport = (JasperReport)JRLoader.loadObject(repoPrePagoFile.getPath());

		Connection connection  = com.puriarte.gcp.resources.ConnectionManager.establishConnection();
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

