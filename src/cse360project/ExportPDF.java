/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cse360project;

import com.itextpdf.awt.DefaultFontMapper;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ExportPDF 
{

    private static String FILE = "c:/temp/THOReport.pdf";
    private static Font titleFont = new Font(Font.FontFamily.HELVETICA, 36, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.HELVETICA,   16, Font.NORMAL);
    private static Font subFont = new Font(Font.FontFamily.HELVETICA,   16, Font.BOLD);
    private static Font smallFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

  public static void create() 
  {
    try 
    {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(FILE));
        document.open();
        addMetaData(document);
        addTitlePage(document);
        System.out.println("Woot it Worked!");
        document.close();
        
        if (Desktop.isDesktopSupported()) {
        try 
        {
            File myFile = new File("c:/temp/THOReport.pdf");
            Desktop.getDesktop().open(myFile);
        } 
        catch (IOException ex) 
        {
            System.out.println("Failed to open file!");
        }
}
    } 
    catch (Exception e) 
    {
        System.out.println("There has been a PDF output error");
        e.printStackTrace();
    }
  }
  // Creates the PDF's metadata
  private static void addMetaData(Document document) 
  {
    document.addTitle("THOR Report");
    document.addSubject("Using iText");
    document.addKeywords("Java, PDF, iText");
    document.addAuthor(LoginScreenController.userName);
    document.addCreator("THOR");
  }

  private static void addTitlePage(Document document) throws DocumentException 
  {
    Paragraph preface = new Paragraph(Element.ALIGN_CENTER);
    // We add one empty line
    addEmptyLine(preface, 1);
    // Lets write a big header
    preface.add(new Paragraph("THOR - The Health Organizer", titleFont));
    addEmptyLine(preface, 1);
    preface.add(new Paragraph("Fitness report generated for " + LoginScreenController.userName,smallFont));
    addEmptyLine(preface, 2);
    
     
    PdfPTable table = createTable1();
    table.setHorizontalAlignment(Element.ALIGN_CENTER);
    document.add(preface);
    document.add(table);   
  }

   public static PdfPTable createTable1() throws DocumentException 
   {
        PdfPTable table = new PdfPTable(2);
        
        
        PdfPCell cell = new PdfPCell(new Phrase("Date"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        /*cell = new PdfPCell(new Phrase("Weight"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Height"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("BMI"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Sleep"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Calories"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Activity"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Water"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Blood Pressure"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Blood Glucose"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Cholesterol"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);*/
        
        cell = new PdfPCell(new Phrase("Steps"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        //float[] columnWidths = new float[] {20f, 20f};
        //table.setWidths(columnWidths);
            
        table.setHeaderRows(2);
        
        for(int i = 0; i < MainScreenController.dateList.size(); i++)
        {
            String date  = MainScreenController.dateList.get(i);
            String steps = MainScreenController.stepsList.get(i);
            table.addCell(date);
            table.addCell(steps);
        }
        

        // row 1
        /*table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");
        
        //row2
        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");
        
        // row 3
        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");
        
        // row 4 - Test to see if for loop works - it does
        for(int i = 0; i < 10; i++)
        {
            table.addCell("Woot" + i);
        }*/
       
        
        return table;
    }
   
    public static JFreeChart generatePieChart() 
    {
        DefaultPieDataset dataSet = new DefaultPieDataset();
        dataSet.setValue("China", 19.64);
        dataSet.setValue("India", 17.3);
        dataSet.setValue("United States", 4.54);
        dataSet.setValue("Indonesia", 3.4);
        dataSet.setValue("Brazil", 2.83);
        dataSet.setValue("Pakistan", 2.48);
        dataSet.setValue("Bangladesh", 2.38);
 
        JFreeChart chart = ChartFactory.createPieChart("World Population by countries", dataSet, true, true, false);
 
        return chart;
    }
 
    public static JFreeChart generateBarChart() 
    {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        dataSet.setValue(791, "Population", "1750 AD");
        dataSet.setValue(978, "Population", "1800 AD");
        dataSet.setValue(1262, "Population", "1850 AD");
        dataSet.setValue(1650, "Population", "1900 AD");
        dataSet.setValue(2519, "Population", "1950 AD");
        dataSet.setValue(6070, "Population", "2000 AD");
 
        JFreeChart chart = ChartFactory.createBarChart(
                "World Population growth", "Year", "Population in millions", dataSet, PlotOrientation.VERTICAL, false, true, false);
 
        return chart;
    }
    
   public static void writeChartToPDF(JFreeChart chart, int width, int height, String fileName) 
   {
        PdfWriter writer = null;
 
        Document document = new Document();
 
        try 
        {
            writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);
 
            chart.draw(graphics2d, rectangle2d);
         
            graphics2d.dispose();
            contentByte.addTemplate(template, 0, 0);
 
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    document.close();
}
  
  private static void addEmptyLine(Paragraph paragraph, int number) 
  {
    for (int i = 0; i < number; i++) {
      paragraph.add(new Paragraph(" "));
    }
  }
} 
