package com.avalon.workbench.services.graphs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.avalon.workbench.beans.concurrntPrograms.CPDetails;
import com.avalon.workbench.repository.exception.WorkbenchDataAccessException;
import com.avalon.workbench.services.concurrentPrograms.ConcurrentProgramsService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class GenerateGraph {
	@Autowired
	@Qualifier("ConcurrentProgramsServiceImpl")
	ConcurrentProgramsService concurrentProgramsService;
	Logger log = Logger.getLogger(GenerateGraph.class);

	public String generatePieGraph(String progName, String from_date,
			String to_date) {
		List<CPDetails> cpDetails = null;
		String pdfFileName=null;
		try {
			log.info("Getting CPdetails");
			cpDetails = concurrentProgramsService.getSpecificCPDetails(
					progName, from_date, to_date);
			DefaultPieDataset dataset = new DefaultPieDataset();
			int count=0;
			for (CPDetails cpd : cpDetails) {

				dataset.setValue(cpd.getRequest_date(),
						Double.parseDouble(cpd.getProcess_time()));
				count++;
				if(count>10){
					break;
				}
			}
			JFreeChart chart = ChartFactory.createPieChart(
					"Performance Of Concurrent Progrm ("+progName+")", // chart title
					dataset, // data
					true, // include legend
					true, false);

			int width = 500; // Width of the image
			int height = 480; // Height of the image
			String imgName="D:/PieChart.jpeg";//Change Location When Deployed in Apps Server
			File pieChart = new File(imgName);

			ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);

			log.info("Chart Generated Succesfully");
			pdfFileName=writeChartToPDF(imgName, pieChart,progName);
		} catch (WorkbenchDataAccessException e) {
			log.error("Error--------------WB" + e);
		} catch (IOException e) {
			log.error("Error-------------- IO" + e);

		}
		return pdfFileName;
	}

	public void generateBarGraph(String progName, String from_date,
			String to_date) {
		
		System.out.println("Under Development");

	}

	public String writeChartToPDF(String fileName, File pieChart,String progName) {

		Document document = new Document();
		String pdfFileName=null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String full_date=dateFormat.format(date);
			pdfFileName="D:/"+progName.replace(" ", "")+"_"+full_date+".pdf";//Change Location When Deployed in Apps Server
			PdfWriter.getInstance(document,
					new FileOutputStream(pdfFileName));
			document.open();

			Image image2 = Image.getInstance(fileName);
			document.add(image2);
			document.close();
			pieChart.delete();
			log.info("File Copied to pdf");
		} catch (Exception e) {
			pieChart.delete();
			log.info("Error" + e);
			e.printStackTrace();
			
		}
		return pdfFileName;
		
	}
	
}
