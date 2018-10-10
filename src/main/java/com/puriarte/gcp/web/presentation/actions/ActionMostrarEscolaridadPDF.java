package com.puriarte.gcp.web.presentation.actions;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.puriarte.convocatoria.core.domain.Constants;
import com.puriarte.convocatoria.core.domain.services.Facade;
import com.puriarte.convocatoria.persistence.PersonMovil;
import com.puriarte.utils.date.DateUtils;
/**
 * Implementacion del Servlet SrvMostrarFacturaPDF
 *
 */
public class ActionMostrarEscolaridadPDF extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Charset encodingImp = Charset.forName("UTF-8");
	private static Logger  logger = Logger.getLogger(ActionMostrarEscolaridadPDF.class.getName());

	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionMostrarEscolaridadPDF() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			Date fechaDesde= DateUtils.parseDate(request.getParameter("fechaDesde"), Constants.FORMATO_FECHA_HTML5_REGEX, Constants.FORMATO_FECHA_HTML5);
			Date fechaHasta= DateUtils.parseDate(request.getParameter("fechaHasta"), Constants.FORMATO_FECHA_HTML5_REGEX, Constants.FORMATO_FECHA_HTML5);
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
			Date fechaDesde= DateUtils.parseDate(request.getParameter("fechaDesde"), Constants.FORMATO_FECHA_HTML5_REGEX, Constants.FORMATO_FECHA_HTML5);
			Date fechaHasta= DateUtils.parseDate(request.getParameter("fechaHasta"), Constants.FORMATO_FECHA_HTML5_REGEX, Constants.FORMATO_FECHA_HTML5);

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
    	// Genero el pdf
    	try{
	
	    	Object[] parametrosComprobante = new Object[50];
			Arrays.fill(parametrosComprobante, "");
	
			Calendar c = Calendar.getInstance();
			c.setTime( fechaHasta);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
	
			fechaHasta = c.getTime();
	
			PersonMovil personMovil = Facade.getInstance().selectPersonMovil(idPerson);
	
			int countSI = Facade.getInstance().selectCountAssignment(personMovil.getId(), Constants.ASSIGNMENT_STATUS_ACCEPTED , fechaDesde, fechaHasta);
			int countNo = Facade.getInstance().selectCountAssignment(personMovil.getId(), Constants.ASSIGNMENT_STATUS_REGECTED, fechaDesde, fechaHasta);
			int countExpired = Facade.getInstance().selectCountAssignment(personMovil.getId(), Constants.ASSIGNMENT_STATUS_EXPIRED, fechaDesde, fechaHasta);
			int countCancelled = Facade.getInstance().selectCountAssignment(personMovil.getId(), Constants.ASSIGNMENT_STATUS_CANCELED, fechaDesde, fechaHasta);
			int countAssigned= Facade.getInstance().selectCountAssignment(personMovil.getId(), Constants.ASSIGNMENT_STATUS_ASSIGNED, fechaDesde, fechaHasta);
			
	
			int countSent = Facade.getInstance().selectCountSentAssignment(personMovil.getPerson().getId(), fechaDesde, fechaHasta);
		 
			// Cargo los datos del parametro
	    	parametrosComprobante[1] = (personMovil.getPerson().getName()==null? "" :personMovil.getPerson().getName()) ;
	    	parametrosComprobante[2] = (personMovil.getPerson().getNickname()==null? "" : personMovil.getPerson().getNickname());
	/*    	if (personMovil.getPerson().getCategory()!=null)
	    		parametrosComprobante[3] = personMovil.getPerson().getCategory().getName();
	  */  	parametrosComprobante[4] = personMovil.getPerson().getPriority();
	
	    	parametrosComprobante[10] = countSent;
	    	parametrosComprobante[11] = countSI;
	    	parametrosComprobante[12] = countNo;
	    	parametrosComprobante[13] = countExpired;
	    	parametrosComprobante[18] = countCancelled;    	
	    	parametrosComprobante[19] = countAssigned;
	    	
	    	parametrosComprobante[16] = DateUtils.formatDate(fechaDesde,   Constants.FORMATO_FECHA_HTML5_ALT2)  ;
	    	parametrosComprobante[17] =  DateUtils.formatDate(fechaHasta,   Constants.FORMATO_FECHA_HTML5_ALT2) ;
	
	    	if (personMovil.getMovil()!=null){
	    		int i1 = 30;
	//    		for (Movil movil: personMovil.getMovil()){
	        		parametrosComprobante[i1] = personMovil.getMovil().getNumber();
	  //      		i1=i1+1;
	    //		}
	    	}
	
	        MessageFormat mFormatter = null;
	        String nombrePlantilla = "formularioCliente.xml";
	        String nombreArchivoPCL = "fac_" + idPerson + ".fo";
	        String nombreArchivoPDF = "fac_" + idPerson + ".pdf";
	        		
	        String contextPath, contextOutputPath, contextFacesPath;
			PropertiesConfiguration config = new PropertiesConfiguration(com.puriarte.gcp.web.Constantes.PATHAPPCONFIG);
			String path= config.getString("data.folder") ;
			String path_repo = config.getString("repo.folder") ;
	
	        if (getServletContext().getRealPath("")==null){
	        	contextPath = path_repo + "src/main/webapp/";
	        	contextOutputPath = path + "reportes/";
	        	contextFacesPath =path + "faces/";
	    	}else{
	            contextPath = getServletContext().getRealPath("");//.getRealPath(File.separator);
	            contextOutputPath =  getServletContext().getRealPath("");
	            contextFacesPath = getServletContext().getRealPath("")+ "/images/faces/";
	    	}
	
	        File filePlantilla = new File(contextPath +"/templates/" + nombrePlantilla);
	
	    	try{
				if ((countSent>0) &&(countSI+countNo>0)) {
			    	String chartName="fac_char_" + idPerson + ".jpg";
			    	
			    	try(OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(contextOutputPath + chartName)))) {
			    		String picture ="";
			    		if (personMovil.getPerson().getPicture()!=null)
			    			picture = personMovil.getPerson().getPicture();
	    		    	parametrosComprobante[14] = contextOutputPath + chartName;
	    		    	parametrosComprobante[15] = contextFacesPath + "flag_mediana_" + picture + ".jpg";
	    		    	
	    		    	DefaultPieDataset dataset = new DefaultPieDataset();
		    			dataset.setValue("SI", countSI);
		    			dataset.setValue("NO", countNo );
		    			if (countCancelled +countExpired+countAssigned>0)
		    				dataset.setValue("OTR", new Double(countCancelled +countExpired+countAssigned));
	
		    			boolean legend = false;
		    			boolean tooltips = false;
		    			boolean urls = false;
	
		    			JFreeChart chart = ChartFactory.createPieChart("", dataset, legend, tooltips, urls);
		    			chart.setBackgroundPaint(Color.WHITE);
		    			//chart.setBorderPaint(Color.GREEN);
	//	    			chart.setBorderStroke(new BasicStroke(5.0f));
		    			chart.setBorderVisible(false);
		    			int width = 250;
		    			int height = 200;
	
		    			// set the background color for the chart...
		    	        chart.setBackgroundPaint(new Color(222, 222, 255));
	//	    	        final PiePlot plot = (PiePlot) chart.getPlot();
	//	    	        plot.setBackgroundPaint(Color.white);
	//	    	        plot.setCircular(true);
	//	    	        plot.setLabelGenerator((PieSectionLabelGenerator) new PieSectionLabelGenerator("{0} ({2})"));
	////	    	        plot.setNoDataMessage("No data available");
	
		    	        PiePlot plot = (PiePlot)chart.getPlot();
		    	        plot.setSectionPaint("SI", Color.green);
		    	        plot.setSectionPaint("NO", Color.red);
		    	        plot.setSimpleLabels(true);
		    	        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
		    	                "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
		    	            plot.setLabelGenerator(gen);
	
		    			ChartUtilities.writeChartAsJPEG(outputStream, chart, width, height);
			    	}catch(Exception e){
			    		logger.error(e.getMessage());
			    	}
				}
	    	}catch(Exception e){
	    		logger.error(e.getMessage());
	    	}
	
	        String strTemplateComprobante = FileUtils.readFileToString(filePlantilla, encodingImp.name());
	
	        mFormatter = new MessageFormat("");
	  //      strTemplateComprobante=strTemplateComprobante.replace("                      {5}", "{5}");
	        mFormatter.applyPattern(strTemplateComprobante);
	
	    	Writer imp = null;
	        try{
		        imp = new OutputStreamWriter(new FileOutputStream(contextOutputPath  + nombreArchivoPCL,false), encodingImp);
		        imp.write(mFormatter.format(parametrosComprobante));
	        }finally{
	        	if(imp!=null)
	        		imp.close();
	        }
	
	        procesarPCL(MimeConstants.MIME_PDF, contextOutputPath + nombreArchivoPCL,contextOutputPath + nombreArchivoPDF );
	
	        // Devuelvo el archivo
			File pdfFile = new File(contextOutputPath + nombreArchivoPDF);
	
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=" + nombreArchivoPDF);
			response.setContentLength((int) pdfFile.length());
	
			FileInputStream fileInputStream =null;
			try{
				fileInputStream = new FileInputStream(pdfFile);
				OutputStream responseOutputStream = response.getOutputStream();
				int bytes;
				while ((bytes = fileInputStream.read()) != -1) {
					responseOutputStream.write(bytes);
				}
			}finally{
				if (fileInputStream!=null)
					fileInputStream.close();
			}
    	}catch(Exception ex){
			logger.error(ex.getStackTrace());
    	}

	}


    /**
     * Prcocesa un PCL
     *
     * @param mime
     * @param input
     * @param output
     * @throws FOPException
     * @throws TransformerException
     * @throws IOException
     */
    public static void procesarPCL(String mime, String input, String output) throws FOPException, TransformerException, IOException {
    	FopFactory fopFactory = FopFactory.newInstance();
    	OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(output)));

    	try {
		    Fop fop = fopFactory.newFop(mime, out);
		    TransformerFactory factory = TransformerFactory.newInstance();
		    factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		    
		    Transformer transformer =factory.newTransformer();
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    
		    Source src = new StreamSource(new File(input));
		    Result res = new SAXResult(fop.getDefaultHandler());
		    transformer.transform(src, res);
    	}catch(Exception e){
    		logger.error(e.getMessage());
		} finally {
		    out.close();
		}
	}
}
