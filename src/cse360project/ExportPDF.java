
package cse360project;

import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.PageSize;
import java.awt.Desktop;
import java.io.IOException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;

public class ExportPDF 
{
    //private static String FILE    = "c:/temp/THOReport.pdf";
    private static String ALTFILE = "THOReport.pdf";
    private static Font titleFont = new Font(Font.FontFamily.HELVETICA, 36, Font.BOLD);
    private static Font redFont   = new Font(Font.FontFamily.HELVETICA, 16, Font.NORMAL);
    private static Font subFont   = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    private static Font smallFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

    public static String username           = "TestUser";
    public static String url                = "jdbc:mysql://168.62.213.183:3306/";
    public static String dbName             = "mydb";
    public static String driver             = "com.mysql.jdbc.Driver";
    public static String databaseUserName   = "CSE360Team";
    public static String databasePassword   = "FitnessTeam#360";
    
    public static List<String> dateList        = new ArrayList<String>();
    public static List<String> stepsList       = new ArrayList<String>();
    public static List<String> glucoseList     = new ArrayList<String>();
    public static List<String> weightList      = new ArrayList<String>();
    public static List<String> heightList      = new ArrayList<String>();
    public static List<String> BMIList         = new ArrayList<String>();
    public static List<String> sleepList       = new ArrayList<String>();
    public static List<String> caloriesList    = new ArrayList<String>();
    public static List<String> activityList    = new ArrayList<String>();
    public static List<String> activityMinList = new ArrayList<String>(); 
    public static List<String> pressureList    = new ArrayList<String>();
    public static List<String> heartList       = new ArrayList<String>();
    
    public static int chartWidth  = 800;
    public static int chartHeight = 600;
     
    public static final String[] IMAGES =
    {
        "ActivityChart.png",
        "HeightChart.png",
        "WeightChart.png",
        "BMIChart.png",
        "CaloriesChart.png",
        "GlucoseChart.png",
        "PressureChart.png",
        "HeartRateChart.png",
        "SleepChart.png",
        "StepsChart.png"
    };
    
  public static void create() 
  {
    try 
    {
        /*Document document = new Document(PageSize.A3.rotate(), 10, 10, 10, 10);
        PdfWriter.getInstance(document, new FileOutputStream(FILE));
        document.open();
        addMetaData(document);
        addTitlePage(document);
        addImages(document);
        document.close();*/
        
        
        
        Document documentAlt = new Document(PageSize.A3.rotate(), 10, 10, 10, 10);
        PdfWriter.getInstance(documentAlt, new FileOutputStream(ALTFILE));
        documentAlt.open();
        addMetaData(documentAlt);
        addTitlePage(documentAlt);
        addImages(documentAlt);
        documentAlt.close();
        
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
        //writeChartToPDF(generateBarChart(), 500, 400);
        //document.close();
        
        
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
  public static void addImages(Document document) throws DocumentException, IOException 
   {
      Image image;
      for (int i = 0; i < IMAGES.length; i++) 
      {
            image = Image.getInstance(String.format(IMAGES[i]));
            
            /*if (image.getScaledWidth() > chartWidth || image.getScaledHeight() > chartHeight) 
            {
                image.scaleToFit(chartWidth, chartHeight);
            }*/
           //document.add(new Paragraph(String.format("%s is an image of type %s", IMAGES[i], image.getClass().getName())));
           document.add(image);  
      }
   }
   public static PdfPTable createTable1() throws DocumentException 
   {          
        PdfPTable table = new PdfPTable(11);

        PdfPCell cell = new PdfPCell(new Phrase("Date"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Weight"));
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
        
        cell = new PdfPCell(new Phrase("Blood Pressure"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
         
        cell = new PdfPCell(new Phrase("Blood Glucose"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Steps"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Heart Rate"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        
        float[] columnWidths = new float[] {25f, 25f, 25f, 25f, 15f, 25f, 25f, 25f, 25f, 25f, 25f};
        //float[] columnWidths = new float[] {20f, 15f, 15f, 15f, 15f, 15f, 15f, 20f, 20f, 15f, 25f};
        table.setWidths(columnWidths);
            
        table.setHeaderRows(11);
        
        for(int i = 0; i < MainScreenController.dateList.size(); i++)
        {
            String date  = MainScreenController.dateList.get(i);
            if(date == null)
            {
                date = "No Data";
            }
            
            String weight = MainScreenController.weightList.get(i);
            if(weight == null)
            {
                weight = "No Data";
            }
            
            String height = MainScreenController.heightList.get(i);
            if(height == null)
            {
                height = "No Data";
            }
           
            String bmi = MainScreenController.BMIList.get(i);
            if(bmi == null)
            {
                bmi = "No Data";
            }   
             
            String sleep = MainScreenController.sleepList.get(i);
            if(sleep == null)
            {
                sleep = "No Data";
            }
            
            String calories = MainScreenController.caloriesList.get(i);
            if(calories == null)
            {
                calories = "No Data";
            }
            
            String activity = MainScreenController.activityList.get(i);
            if(activity == null)
            {
                activity = "No Data";
            }
                
            String pressure = MainScreenController.bloodPressureList.get(i);
            if(pressure == null)
            {
                pressure = "No Data";
            }
            
            String glucose = MainScreenController.glucoseList.get(i);
            if(glucose == null)
            {
                glucose = "No Data";
            }
                       
            String steps = MainScreenController.stepsList.get(i);
            if(steps == null)
            {
                steps = "No Data";
            }
            
            String heart = MainScreenController.heartList.get(i);
            if(heart == null)
            {
                heart = "No Data";
            }
            
            /*String activityMin = MainScreenController.activityMinList.get(i);
            if(activityMin == null)
            {
                activityMin = "No Data";
            }*/
            
            table.addCell(date);
            table.addCell(weight);
            table.addCell(height);
            table.addCell(bmi);
            table.addCell(sleep);
            table.addCell(calories);
            table.addCell(activity);
            table.addCell(pressure);
            table.addCell(glucose);
            table.addCell(steps);
            table.addCell(heart);
        }     
        return table;
    }
 
   private static void addEmptyLine(Paragraph paragraph, int number) 
  {
    for (int i = 0; i < number; i++) {
      paragraph.add(new Paragraph(" "));
    }
  }
  
  public static void start() 
  {
      Stage stage = new Stage();
      GenerateGlucose(stage);
      GenerateSteps(stage);
      GenerateWeight(stage);
      GenerateHeight(stage);
      GenerateBMI(stage);
      GenerateSleep(stage);
      GenerateCalories(stage);
      GenerateActivity(stage);
      GeneratePressure(stage);
      GenerateHeartRate(stage);
   }
  public static void GenerateGlucose(Stage stage)
  {
        dateList.clear();
        glucoseList.clear();
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");
	} 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Where is your MySQL JDBC Driver?");
            System.out.println("Failed stage 1");
            return;
	}
 
	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;
 
	try 
        {
            System.out.println("Attempting Connection...");
            connection = DriverManager.getConnection(url + dbName, databaseUserName, databasePassword);
            if (connection != null) 
            {
                String graphQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ username +  "' ORDER BY date DESC";
                PreparedStatement dataStatement = connection.prepareStatement(graphQuery);
                ResultSet dataResult = dataStatement.executeQuery();
                if(dataResult.next())
                {
                    while(dataResult.next())
                    {
                        if(dataResult.getString(12) != null && dataResult.getString(3) != null)
                        {
                           dateList.add(dataResult.getString(3));
                           glucoseList.add(dataResult.getString(12));
                        }        
                    }   
                }
                         
                else
                {
                   System.out.println("Database error derp!");
                }
              connection.close();  
            }
            else 
            {
                System.out.println("Failed to make connection!");
            }
	} 
        catch (SQLException e) 
        {
            System.out.println("Connection Failed! Check output console");
            System.out.println("Failed stage 3");
            e.printStackTrace();
            return;
	}
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Milligrams per DeciLiter");
        final LineChart<String, Number> glucoseChart = new LineChart<String, Number>(xAxis, yAxis);
        glucoseChart.setAnimated(false);
        glucoseChart.setTitle("Blood Glucose Levels");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Blood Glucose");
        String tempDate;
        int tempSteps;
        
        tempDate = dateList.get(9).substring(5);
        tempSteps = Integer.parseInt(glucoseList.get(9));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(8).substring(5);
        tempSteps = Integer.parseInt(glucoseList.get(8));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(7).substring(5);
        tempSteps = Integer.parseInt(glucoseList.get(7));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(6).substring(5);
        tempSteps = Integer.parseInt(glucoseList.get(6));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(5).substring(5);
        tempSteps = Integer.parseInt(glucoseList.get(5));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));

        tempDate = dateList.get(4).substring(5);
        tempSteps = Integer.parseInt(glucoseList.get(4));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(3).substring(5);
        tempSteps = Integer.parseInt(glucoseList.get(3));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(2).substring(5);
        tempSteps = Integer.parseInt(glucoseList.get(2));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(1).substring(5);
        tempSteps = Integer.parseInt(glucoseList.get(1));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(0).substring(5);
        tempSteps = Integer.parseInt(glucoseList.get(0));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
 
        final Scene scene = new Scene(glucoseChart, 800, 600);
        scene.getStylesheets().add("chart.css");
        try
        {
            scene.getStylesheets().add("chart.css");
        }
        catch (Exception ex)
        { 
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
        glucoseChart.getData().addAll(series1);
        stage.setScene(scene);
        //stage.show();

        Task task = new Task<Void>() 
        {
            @Override
            public Void call() 
            {
                Platform.runLater(new Runnable() 
                {
                    public void run() 
                    {
                        try 
                        {
                            WritableImage wim = new WritableImage(800, 600);
                            scene.snapshot(wim);
                            File file = new File("GlucoseChart.png");
                            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
                        } 
                        catch (Exception s) 
                        {
                            System.out.println("File Write Error!");
                        }
                        System.out.println("|||||||||| Finished Glucose");
                    }});

                return null;
            }
        };
        
        Thread thread1 = new Thread(task);
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread1.start();
        //Platform.exit();
    }
    
    public static void GenerateSteps(Stage stage)
    {
        dateList.clear();
        stepsList.clear();
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");
	} 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Where is your MySQL JDBC Driver?");
            System.out.println("Failed stage 1");
            return;
	}
 
	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;
 
	try 
        {
            System.out.println("Attempting Connection...");
            connection = DriverManager.getConnection(url + dbName, databaseUserName, databasePassword);
            if (connection != null) 
            {
                String graphQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ username +  "' ORDER BY date DESC";
                PreparedStatement dataStatement = connection.prepareStatement(graphQuery);
                ResultSet dataResult = dataStatement.executeQuery();
                if(dataResult.next())
                {
                    while(dataResult.next())
                    {
                        if(dataResult.getString(14) != null && dataResult.getString(3) != null)
                        {
                           dateList.add(dataResult.getString(3));
                           stepsList.add(dataResult.getString(14));
                        }        
                    }   
                }
                         
                else
                {
                   System.out.println("Database error derp!");
                }
              connection.close();  
            }
            else 
            {
                System.out.println("Failed to make connection!");
            }
	} 
        catch (SQLException e) 
        {
            System.out.println("Connection Failed! Check output console");
            System.out.println("Failed stage 3");
            e.printStackTrace();
            return;
	}
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Steps");
        final LineChart<String, Number> stepsChart = new LineChart<String, Number>(xAxis, yAxis);
        stepsChart.setAnimated(false);
        stepsChart.setTitle("Number of Steps");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Steps");
        String tempDate;
        int tempSteps;
        
        tempDate = dateList.get(9).substring(5);
        tempSteps = Integer.parseInt(stepsList.get(9));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(8).substring(5);
        tempSteps = Integer.parseInt(stepsList.get(8));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(7).substring(5);
        tempSteps = Integer.parseInt(stepsList.get(7));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(6).substring(5);
        tempSteps = Integer.parseInt(stepsList.get(6));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(5).substring(5);
        tempSteps = Integer.parseInt(stepsList.get(5));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));

        tempDate = dateList.get(4).substring(5);
        tempSteps = Integer.parseInt(stepsList.get(4));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(3).substring(5);
        tempSteps = Integer.parseInt(stepsList.get(3));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(2).substring(5);
        tempSteps = Integer.parseInt(stepsList.get(2));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(1).substring(5);
        tempSteps = Integer.parseInt(stepsList.get(1));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(0).substring(5);
        tempSteps = Integer.parseInt(stepsList.get(0));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
 
        final Scene scene = new Scene(stepsChart, 800, 600);
        
        try
        {
            scene.getStylesheets().add("chart.css");
        }
        catch (Exception ex)
        { 
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
        stepsChart.getData().addAll(series1);
        stage.setScene(scene);
        //stage.show();

        Task task = new Task<Void>() 
        {
            @Override
            public Void call() 
            {
                Platform.runLater(new Runnable() 
                {
                    public void run() 
                    {
                        try 
                        {
                            WritableImage wim = new WritableImage(800, 600);
                            scene.snapshot(wim);
                            File file = new File("StepsChart.png");
                            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
                        } 
                        catch (Exception s) 
                        {
                            System.out.println("File Write Error!");
                        }
                        System.out.println("|||||||||| Finished Steps");
                    }});

                return null;
            }
        };
        Thread thread2 = new Thread(task);
        thread2.start();
        //Platform.exit();
    }
    
    public static  void GenerateWeight(Stage stage)
    {
        dateList.clear();
        weightList.clear();
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");
	} 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Where is your MySQL JDBC Driver?");
            System.out.println("Failed stage 1");
            return;
	}
 
	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;
 
	try 
        {
            System.out.println("Attempting Connection...");
            connection = DriverManager.getConnection(url + dbName, databaseUserName, databasePassword);
            if (connection != null) 
            {
                String graphQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ username +  "' ORDER BY date DESC";
                PreparedStatement dataStatement = connection.prepareStatement(graphQuery);
                ResultSet dataResult = dataStatement.executeQuery();
                if(dataResult.next())
                {
                    while(dataResult.next())
                    {
                        if(dataResult.getString(4) != null && dataResult.getString(3) != null)
                        {
                           dateList.add(dataResult.getString(3));
                           weightList.add(dataResult.getString(4));
                        }        
                    }   
                }
                         
                else
                {
                   System.out.println("Database error derp!");
                }
              connection.close();  
            }
            else 
            {
                System.out.println("Failed to make connection!");
            }
	} 
        catch (SQLException e) 
        {
            System.out.println("Connection Failed! Check output console");
            System.out.println("Failed stage 3");
            e.printStackTrace();
            return;
	}
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Weight (in lbs)");
        final LineChart<String, Number> weightChart = new LineChart<String, Number>(xAxis, yAxis);
        weightChart.setAnimated(false);
        weightChart.setTitle("Weight");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Weight");
        String tempDate;
        int tempSteps;
        
        tempDate = dateList.get(9).substring(5);
        tempSteps = Integer.parseInt(weightList.get(9));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(8).substring(5);
        tempSteps = Integer.parseInt(weightList.get(8));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(7).substring(5);
        tempSteps = Integer.parseInt(weightList.get(7));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(6).substring(5);
        tempSteps = Integer.parseInt(weightList.get(6));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(5).substring(5);
        tempSteps = Integer.parseInt(weightList.get(5));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));

        tempDate = dateList.get(4).substring(5);
        tempSteps = Integer.parseInt(weightList.get(4));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(3).substring(5);
        tempSteps = Integer.parseInt(weightList.get(3));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(2).substring(5);
        tempSteps = Integer.parseInt(weightList.get(2));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(1).substring(5);
        tempSteps = Integer.parseInt(weightList.get(1));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(0).substring(5);
        tempSteps = Integer.parseInt(weightList.get(0));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
 
        final Scene scene = new Scene(weightChart, 800, 600);
        
        try
        {
            scene.getStylesheets().add("chart.css");
        }
        catch (Exception ex)
        { 
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
        weightChart.getData().addAll(series1);
        stage.setScene(scene);
        //stage.show();

        Task task = new Task<Void>() 
        {
            @Override
            public Void call() 
            {
                Platform.runLater(new Runnable() 
                {
                    public void run() 
                    {
                        try 
                        {
                            WritableImage wim = new WritableImage(800, 600);
                            scene.snapshot(wim);
                            File file = new File("WeightChart.png");
                            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
                        } 
                        catch (Exception s) 
                        {
                            System.out.println("File Write Error!");
                        }
                        System.out.println("|||||||||| Finished Weight");
                    }});

                return null;
            }
        };
        Thread thread3 = new Thread(task);
        thread3.start();
        //Platform.exit();
    }
    
    public static void GenerateHeight(Stage stage)
    {
        dateList.clear();
        heightList.clear();
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");
	} 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Where is your MySQL JDBC Driver?");
            System.out.println("Failed stage 1");
            return;
	}
 
	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;
 
	try 
        {
            System.out.println("Attempting Connection...");
            connection = DriverManager.getConnection(url + dbName, databaseUserName, databasePassword);
            if (connection != null) 
            {
                String graphQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ username +  "' ORDER BY date DESC";
                PreparedStatement dataStatement = connection.prepareStatement(graphQuery);
                ResultSet dataResult = dataStatement.executeQuery();
                if(dataResult.next())
                {
                    while(dataResult.next())
                    {
                        if(dataResult.getString(5) != null && dataResult.getString(3) != null)
                        {
                           dateList.add(dataResult.getString(3));
                           heightList.add(dataResult.getString(5));
                        }        
                    }   
                }
                         
                else
                {
                   System.out.println("Database error derp!");
                }
              connection.close();  
            }
            else 
            {
                System.out.println("Failed to make connection!");
            }
	} 
        catch (SQLException e) 
        {
            System.out.println("Connection Failed! Check output console");
            System.out.println("Failed stage 3");
            e.printStackTrace();
            return;
	}
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Height (in inches)");
        final LineChart<String, Number> heightChart = new LineChart<String, Number>(xAxis, yAxis);
        heightChart.setAnimated(false);
        heightChart.setTitle("Height");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Height");
        String tempDate;
        int tempSteps;
        
        tempDate = dateList.get(9).substring(5);
        tempSteps = Integer.parseInt(heightList.get(9));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(8).substring(5);
        tempSteps = Integer.parseInt(heightList.get(8));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(7).substring(5);
        tempSteps = Integer.parseInt(heightList.get(7));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(6).substring(5);
        tempSteps = Integer.parseInt(heightList.get(6));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(5).substring(5);
        tempSteps = Integer.parseInt(heightList.get(5));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));

        tempDate = dateList.get(4).substring(5);
        tempSteps = Integer.parseInt(heightList.get(4));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(3).substring(5);
        tempSteps = Integer.parseInt(heightList.get(3));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(2).substring(5);
        tempSteps = Integer.parseInt(heightList.get(2));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(1).substring(5);
        tempSteps = Integer.parseInt(heightList.get(1));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(0).substring(5);
        tempSteps = Integer.parseInt(heightList.get(0));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
 
        final Scene scene = new Scene(heightChart, 800, 600);
        
        try
        {
            scene.getStylesheets().add("chart.css");
        }
        catch (Exception ex)
        { 
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
        heightChart.getData().addAll(series1);
        stage.setScene(scene);
        //stage.show();

        Task task = new Task<Void>() 
        {
            @Override
            public Void call() 
            {
                Platform.runLater(new Runnable() 
                {
                    public void run() 
                    {
                        try 
                        {
                            WritableImage wim = new WritableImage(800, 600);
                            scene.snapshot(wim);
                            File file = new File("HeightChart.png");
                            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
                        } 
                        catch (Exception s) 
                        {
                            System.out.println("File Write Error!");
                        }
                        System.out.println("|||||||||| Finished Height");
                    }});

                return null;
            }
        };
        Thread thread4 = new Thread(task);
        thread4.start();
        //Platform.exit();
    }
    
    public static void GenerateBMI(Stage stage)
    {
        dateList.clear();
        BMIList.clear();
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");
	} 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Where is your MySQL JDBC Driver?");
            System.out.println("Failed stage 1");
            return;
	}
 
	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;
 
	try 
        {
            System.out.println("Attempting Connection...");
            connection = DriverManager.getConnection(url + dbName, databaseUserName, databasePassword);
            if (connection != null) 
            {
                String graphQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ username +  "' ORDER BY date DESC";
                PreparedStatement dataStatement = connection.prepareStatement(graphQuery);
                ResultSet dataResult = dataStatement.executeQuery();
                if(dataResult.next())
                {
                    while(dataResult.next())
                    {
                        if(dataResult.getString(6) != null && dataResult.getString(3) != null)
                        {
                           dateList.add(dataResult.getString(3));
                           BMIList.add(dataResult.getString(6));
                        }        
                    }   
                }
                         
                else
                {
                   System.out.println("Database error derp!");
                }
              connection.close();  
            }
            else 
            {
                System.out.println("Failed to make connection!");
            }
	} 
        catch (SQLException e) 
        {
            System.out.println("Connection Failed! Check output console");
            System.out.println("Failed stage 3");
            e.printStackTrace();
            return;
	}
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Body Mass Index");
        final LineChart<String, Number> BMIChart = new LineChart<String, Number>(xAxis, yAxis);
        BMIChart.setAnimated(false);
        BMIChart.setTitle("Body Mass Index");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("BMI");
        String tempDate;
        int tempSteps;
        
        tempDate = dateList.get(9).substring(5);
        tempSteps = Integer.parseInt(BMIList.get(9));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(8).substring(5);
        tempSteps = Integer.parseInt(BMIList.get(8));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(7).substring(5);
        tempSteps = Integer.parseInt(BMIList.get(7));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(6).substring(5);
        tempSteps = Integer.parseInt(BMIList.get(6));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(5).substring(5);
        tempSteps = Integer.parseInt(BMIList.get(5));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));

        tempDate = dateList.get(4).substring(5);
        tempSteps = Integer.parseInt(BMIList.get(4));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(3).substring(5);
        tempSteps = Integer.parseInt(BMIList.get(3));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(2).substring(5);
        tempSteps = Integer.parseInt(BMIList.get(2));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(1).substring(5);
        tempSteps = Integer.parseInt(BMIList.get(1));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(0).substring(5);
        tempSteps = Integer.parseInt(BMIList.get(0));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
 
        final Scene scene = new Scene(BMIChart, 800, 600);
        
        try
        {
            scene.getStylesheets().add("chart.css");
        }
        catch (Exception ex)
        { 
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
        BMIChart.getData().addAll(series1);
        stage.setScene(scene);
        //stage.show();

        Task task = new Task<Void>() 
        {
            @Override
            public Void call() 
            {
                Platform.runLater(new Runnable() 
                {
                    public void run() 
                    {
                        try 
                        {
                            WritableImage wim = new WritableImage(800, 600);
                            scene.snapshot(wim);
                            File file = new File("BMIChart.png");
                            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
                        } 
                        catch (Exception s) 
                        {
                            System.out.println("File Write Error!");
                        }
                        System.out.println("|||||||||| Finished BMI");
                    }});

                return null;
            }
        };
        Thread thread5 = new Thread(task);
        thread5.start();
        //Platform.exit();
    }
    
    public static void GenerateSleep(Stage stage)
    {
        dateList.clear();
        sleepList.clear();
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");
	} 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Where is your MySQL JDBC Driver?");
            System.out.println("Failed stage 1");
            return;
	}
 
	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;
 
	try 
        {
            System.out.println("Attempting Connection...");
            connection = DriverManager.getConnection(url + dbName, databaseUserName, databasePassword);
            if (connection != null) 
            {
                String graphQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ username +  "' ORDER BY date DESC";
                PreparedStatement dataStatement = connection.prepareStatement(graphQuery);
                ResultSet dataResult = dataStatement.executeQuery();
                if(dataResult.next())
                {
                    while(dataResult.next())
                    {
                        if(dataResult.getString(7) != null && dataResult.getString(3) != null)
                        {
                           dateList.add(dataResult.getString(3));
                           sleepList.add(dataResult.getString(7));
                        }        
                    }   
                }
                         
                else
                {
                   System.out.println("Database error derp!");
                }
              connection.close();  
            }
            else 
            {
                System.out.println("Failed to make connection!");
            }
	} 
        catch (SQLException e) 
        {
            System.out.println("Connection Failed! Check output console");
            System.out.println("Failed stage 3");
            e.printStackTrace();
            return;
	}
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Minutes");
        final LineChart<String, Number> SleepChart = new LineChart<String, Number>(xAxis, yAxis);
        SleepChart.setAnimated(false);
        SleepChart.setTitle("Sleep (in minutes)");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Sleep");
        String tempDate;
        int tempSteps;
        
        tempDate = dateList.get(9).substring(5);
        tempSteps = Integer.parseInt(sleepList.get(9));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(8).substring(5);
        tempSteps = Integer.parseInt(sleepList.get(8));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(7).substring(5);
        tempSteps = Integer.parseInt(sleepList.get(7));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(6).substring(5);
        tempSteps = Integer.parseInt(sleepList.get(6));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(5).substring(5);
        tempSteps = Integer.parseInt(sleepList.get(5));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));

        tempDate = dateList.get(4).substring(5);
        tempSteps = Integer.parseInt(sleepList.get(4));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(3).substring(5);
        tempSteps = Integer.parseInt(sleepList.get(3));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(2).substring(5);
        tempSteps = Integer.parseInt(sleepList.get(2));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(1).substring(5);
        tempSteps = Integer.parseInt(sleepList.get(1));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(0).substring(5);
        tempSteps = Integer.parseInt(sleepList.get(0));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
 
        final Scene scene = new Scene(SleepChart, 800, 600);
        
        try
        {
            scene.getStylesheets().add("chart.css");
        }
        catch (Exception ex)
        { 
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
        SleepChart.getData().addAll(series1);
        stage.setScene(scene);
        //stage.show();

        Task task = new Task<Void>() 
        {
            @Override
            public Void call() 
            {
                Platform.runLater(new Runnable() 
                {
                    public void run() 
                    {
                        try 
                        {
                            WritableImage wim = new WritableImage(800, 600);
                            scene.snapshot(wim);
                            File file = new File("SleepChart.png");
                            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
                        } 
                        catch (Exception s) 
                        {
                            System.out.println("File Write Error!");
                        }
                        System.out.println("|||||||||| Finished Sleep");
                    }});

                return null;
            }
        };
        Thread thread6 = new Thread(task);
        thread6.start();
        //Platform.exit();
    }
    
    public static void GenerateCalories(Stage stage)
    {
        dateList.clear();
        caloriesList.clear();
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");
	} 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Where is your MySQL JDBC Driver?");
            System.out.println("Failed stage 1");
            return;
	}
 
	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;
 
	try 
        {
            System.out.println("Attempting Connection...");
            connection = DriverManager.getConnection(url + dbName, databaseUserName, databasePassword);
            if (connection != null) 
            {
                String graphQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ username +  "' ORDER BY date DESC";
                PreparedStatement dataStatement = connection.prepareStatement(graphQuery);
                ResultSet dataResult = dataStatement.executeQuery();
                if(dataResult.next())
                {
                    while(dataResult.next())
                    {
                        if(dataResult.getString(8) != null && dataResult.getString(3) != null)
                        {
                           dateList.add(dataResult.getString(3));
                           caloriesList.add(dataResult.getString(8));
                        }        
                    }   
                }
                         
                else
                {
                   System.out.println("Database error derp!");
                }
              connection.close();  
            }
            else 
            {
                System.out.println("Failed to make connection!");
            }
	} 
        catch (SQLException e) 
        {
            System.out.println("Connection Failed! Check output console");
            System.out.println("Failed stage 3");
            e.printStackTrace();
            return;
	}
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Calories");
        final LineChart<String, Number> CaloriesChart = new LineChart<String, Number>(xAxis, yAxis);
        CaloriesChart.setAnimated(false);
        CaloriesChart.setTitle("Daily Calorie Intake");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Calories");
        String tempDate;
        int tempSteps;
        
        tempDate = dateList.get(9).substring(5);
        tempSteps = Integer.parseInt(caloriesList.get(9));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(8).substring(5);
        tempSteps = Integer.parseInt(caloriesList.get(8));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(7).substring(5);
        tempSteps = Integer.parseInt(caloriesList.get(7));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(6).substring(5);
        tempSteps = Integer.parseInt(caloriesList.get(6));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(5).substring(5);
        tempSteps = Integer.parseInt(caloriesList.get(5));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));

        tempDate = dateList.get(4).substring(5);
        tempSteps = Integer.parseInt(caloriesList.get(4));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(3).substring(5);
        tempSteps = Integer.parseInt(caloriesList.get(3));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(2).substring(5);
        tempSteps = Integer.parseInt(caloriesList.get(2));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(1).substring(5);
        tempSteps = Integer.parseInt(caloriesList.get(1));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(0).substring(5);
        tempSteps = Integer.parseInt(caloriesList.get(0));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
 
        final Scene scene = new Scene(CaloriesChart, 800, 600);
        
        try
        {
            scene.getStylesheets().add("chart.css");
        }
        catch (Exception ex)
        { 
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
        CaloriesChart.getData().addAll(series1);
        stage.setScene(scene);
        //stage.show();

        Task task = new Task<Void>() 
        {
            @Override
            public Void call() 
            {
                Platform.runLater(new Runnable() 
                {
                    public void run() 
                    {
                        try 
                        {
                            WritableImage wim = new WritableImage(800, 600);
                            scene.snapshot(wim);
                            File file = new File("CaloriesChart.png");
                            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
                        } 
                        catch (Exception s) 
                        {
                            System.out.println("File Write Error!");
                        }
                        System.out.println("|||||||||| Finished Calories");
                    }});

                return null;
            }
        };
        Thread thread7 = new Thread(task);
        thread7.start();
        //Platform.exit();
    }
    
    public static void GenerateActivity(Stage stage)
    {
        dateList.clear();
        activityList.clear();
        activityMinList.clear();
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");
	} 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Where is your MySQL JDBC Driver?");
            System.out.println("Failed stage 1");
            return;
	}
 
	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;
 
	try 
        {
            System.out.println("Attempting Connection...");
            connection = DriverManager.getConnection(url + dbName, databaseUserName, databasePassword);
            if (connection != null) 
            {
                String graphQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ username +  "' ORDER BY date DESC";
                PreparedStatement dataStatement = connection.prepareStatement(graphQuery);
                ResultSet dataResult = dataStatement.executeQuery();
                if(dataResult.next())
                {
                    while(dataResult.next())
                    {
                        if(dataResult.getString(9) != null && dataResult.getString(3) != null)
                        {
                           dateList.add(dataResult.getString(3));
                           activityList.add(dataResult.getString(9));
                           activityMinList.add(dataResult.getString(16));
                        }        
                    }   
                }
                         
                else
                {
                   System.out.println("Database error derp!");
                }
              connection.close();  
            }
            else 
            {
                System.out.println("Failed to make connection!");
            }
	} 
        catch (SQLException e) 
        {
            System.out.println("Connection Failed! Check output console");
            System.out.println("Failed stage 3");
            e.printStackTrace();
            return;
	}
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Minutes");
        final LineChart<String, Number> ActivityChart = new LineChart<String, Number>(xAxis, yAxis);
        ActivityChart.setAnimated(false);
        ActivityChart.setTitle("Daily Activity (in minutes)");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Activity1");
        String tempDate;
        int tempSteps;
        
        tempDate = dateList.get(9).substring(5);
        tempSteps = Integer.parseInt(activityMinList.get(9));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(8).substring(5);
        tempSteps = Integer.parseInt(activityMinList.get(8));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(7).substring(5);
        tempSteps = Integer.parseInt(activityMinList.get(7));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(6).substring(5);
        tempSteps = Integer.parseInt(activityMinList.get(6));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(5).substring(5);
        tempSteps = Integer.parseInt(activityMinList.get(5));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));

        tempDate = dateList.get(4).substring(5);
        tempSteps = Integer.parseInt(activityMinList.get(4));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(3).substring(5);
        tempSteps = Integer.parseInt(activityMinList.get(3));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(2).substring(5);
        tempSteps = Integer.parseInt(activityMinList.get(2));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(1).substring(5);
        tempSteps = Integer.parseInt(activityMinList.get(1));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(0).substring(5);
        tempSteps = Integer.parseInt(activityMinList.get(0));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
 
        final Scene scene = new Scene(ActivityChart, 800, 600);
        
        try
        {
            scene.getStylesheets().add("chart.css");
        }
        catch (Exception ex)
        { 
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
        ActivityChart.getData().addAll(series1);
        stage.setScene(scene);
        //stage.show();

        Task task = new Task<Void>() 
        {
            @Override
            public Void call() 
            {
                Platform.runLater(new Runnable() 
                {
                    public void run() 
                    {
                        try 
                        {
                            WritableImage wim = new WritableImage(800, 600);
                            scene.snapshot(wim);
                            File file = new File("ActivityChart.png");
                            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
                        } 
                        catch (Exception s) 
                        {
                            System.out.println("File Write Error!");
                        }
                        System.out.println("|||||||||| Finished BMI");
                    }});

                return null;
            }
        };
        Thread thread8 = new Thread(task);
        thread8.start();
        //Platform.exit();
    }
    
    public static void GeneratePressure(Stage stage)
    {
        dateList.clear();
        pressureList.clear();
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");
	} 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Where is your MySQL JDBC Driver?");
            System.out.println("Failed stage 1");
            return;
	}
 
	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;
 
	try 
        {
            System.out.println("Attempting Connection...");
            connection = DriverManager.getConnection(url + dbName, databaseUserName, databasePassword);
            if (connection != null) 
            {
                String graphQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ username +  "' ORDER BY date DESC";
                PreparedStatement dataStatement = connection.prepareStatement(graphQuery);
                ResultSet dataResult = dataStatement.executeQuery();
                if(dataResult.next())
                {
                    while(dataResult.next())
                    {
                        if(dataResult.getString(11) != null)
                        {
                           dateList.add(dataResult.getString(3));
                           pressureList.add(dataResult.getString(11));
                        }        
                    }   
                }
                         
                else
                {
                   System.out.println("Database error derp!");
                }
              connection.close();  
            }
            else 
            {
                System.out.println("Failed to make connection!");
            }
	} 
        catch (SQLException e) 
        {
            System.out.println("Connection Failed! Check output console");
            System.out.println("Failed stage 3");
            e.printStackTrace();
            return;
	}
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
        final LineChart<String, Number> PressureChart = new LineChart<String, Number>(xAxis, yAxis);
        PressureChart.setAnimated(false);
        PressureChart.setTitle("Blood Pressure (Systolic and Diastolic)");
        XYChart.Series seriesSys = new XYChart.Series();
        XYChart.Series seriesDia = new XYChart.Series();
        seriesSys.setName("Systolic");
        seriesDia.setName("Diastolic");
 
        String tempDate;
        String[] tempBPVals;
        int tempSystolic;
        int tempDiastolic;

        // For some reason it won't let me use a for/foreach loop to add 
        tempDate = dateList.get(9);
        tempBPVals = pressureList.get(9).split("/");
        tempSystolic = Integer.parseInt(tempBPVals[0]);
        tempDiastolic = Integer.parseInt(tempBPVals[1]);
        seriesSys.getData().add(new XYChart.Data(tempDate, tempSystolic));
        seriesDia.getData().add(new XYChart.Data(tempDate, tempDiastolic));
        
        tempDate = dateList.get(8);
        tempBPVals = pressureList.get(8).split("/");
        tempSystolic = Integer.parseInt(tempBPVals[0]);
        tempDiastolic = Integer.parseInt(tempBPVals[1]);
        seriesSys.getData().add(new XYChart.Data(tempDate, tempSystolic));
        seriesDia.getData().add(new XYChart.Data(tempDate, tempDiastolic));
        
        tempDate = dateList.get(7);
        tempBPVals = pressureList.get(7).split("/");
        tempSystolic = Integer.parseInt(tempBPVals[0]);
        tempDiastolic = Integer.parseInt(tempBPVals[1]);
        seriesSys.getData().add(new XYChart.Data(tempDate, tempSystolic));
        seriesDia.getData().add(new XYChart.Data(tempDate, tempDiastolic));
        
        tempDate = dateList.get(6);
        tempBPVals = pressureList.get(6).split("/");
        tempSystolic = Integer.parseInt(tempBPVals[0]);
        tempDiastolic = Integer.parseInt(tempBPVals[1]);
        seriesSys.getData().add(new XYChart.Data(tempDate, tempSystolic));
        seriesDia.getData().add(new XYChart.Data(tempDate, tempDiastolic));
        
        tempDate = dateList.get(5);
        tempBPVals = pressureList.get(5).split("/");
        tempSystolic = Integer.parseInt(tempBPVals[0]);
        tempDiastolic = Integer.parseInt(tempBPVals[1]);
        seriesSys.getData().add(new XYChart.Data(tempDate, tempSystolic));
        seriesDia.getData().add(new XYChart.Data(tempDate, tempDiastolic));
        
        tempDate = dateList.get(4);
        tempBPVals = pressureList.get(4).split("/");
        tempSystolic = Integer.parseInt(tempBPVals[0]);
        tempDiastolic = Integer.parseInt(tempBPVals[1]);
        seriesSys.getData().add(new XYChart.Data(tempDate, tempSystolic));
        seriesDia.getData().add(new XYChart.Data(tempDate, tempDiastolic));
        
        tempDate = dateList.get(3);
        tempBPVals = pressureList.get(3).split("/");
        tempSystolic = Integer.parseInt(tempBPVals[0]);
        tempDiastolic = Integer.parseInt(tempBPVals[1]);
        seriesSys.getData().add(new XYChart.Data(tempDate, tempSystolic));
        seriesDia.getData().add(new XYChart.Data(tempDate, tempDiastolic));
        
        tempDate = dateList.get(2);
        tempBPVals = pressureList.get(2).split("/");
        tempSystolic = Integer.parseInt(tempBPVals[0]);
        tempDiastolic = Integer.parseInt(tempBPVals[1]);
        seriesSys.getData().add(new XYChart.Data(tempDate, tempSystolic));
        seriesDia.getData().add(new XYChart.Data(tempDate, tempDiastolic));
        
        tempDate = dateList.get(1);
        tempBPVals = pressureList.get(1).split("/");
        tempSystolic = Integer.parseInt(tempBPVals[0]);
        tempDiastolic = Integer.parseInt(tempBPVals[1]);
        seriesSys.getData().add(new XYChart.Data(tempDate, tempSystolic));
        seriesDia.getData().add(new XYChart.Data(tempDate, tempDiastolic));
        
        tempDate = dateList.get(0);
        tempBPVals = pressureList.get(0).split("/");
        tempSystolic = Integer.parseInt(tempBPVals[0]);
        tempDiastolic = Integer.parseInt(tempBPVals[1]);
        seriesSys.getData().add(new XYChart.Data(tempDate, tempSystolic));
        seriesDia.getData().add(new XYChart.Data(tempDate, tempDiastolic));
        
        lineChartData.addAll(seriesSys, seriesDia);
        
        PressureChart.setData(lineChartData);
        PressureChart.createSymbolsProperty();
  
        final Scene scene = new Scene(PressureChart, 800, 600);
        
        try
        {
            scene.getStylesheets().add("chart.css");
        }
        catch (Exception ex)
        { 
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
        stage.setScene(scene);
        //stage.show();

        Task task = new Task<Void>() 
        {
            @Override
            public Void call() 
            {
                Platform.runLater(new Runnable() 
                {
                    public void run() 
                    {
                        try 
                        {
                            WritableImage wim = new WritableImage(800, 600);
                            scene.snapshot(wim);
                            File file = new File("PressureChart.png");
                            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
                        } 
                        catch (Exception s) 
                        {
                            System.out.println("File Write Error!");
                        }
                        System.out.println("|||||||||| Finished Pressure");
                    }});

                return null;
            }
        };
        Thread thread9 = new Thread(task);
        thread9.start();
        //Platform.exit();
    }
    
    public static  void GenerateHeartRate(Stage stage)
    {
        dateList.clear();
        heartList.clear();
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");
	} 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Where is your MySQL JDBC Driver?");
            System.out.println("Failed stage 1");
            return;
	}
 
	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;
 
	try 
        {
            System.out.println("Attempting Connection...");
            connection = DriverManager.getConnection(url + dbName, databaseUserName, databasePassword);
            if (connection != null) 
            {
                String graphQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ username +  "' ORDER BY date DESC";
                PreparedStatement dataStatement = connection.prepareStatement(graphQuery);
                ResultSet dataResult = dataStatement.executeQuery();
                if(dataResult.next())
                {
                    while(dataResult.next())
                    {
                        if(dataResult.getString(15) != null && dataResult.getString(3) != null)
                        {
                           dateList.add(dataResult.getString(3));
                           heartList.add(dataResult.getString(15));
                        }        
                    }   
                }
                         
                else
                {
                   System.out.println("Database error derp!");
                }
              connection.close();  
            }
            else 
            {
                System.out.println("Failed to make connection!");
            }
	} 
        catch (SQLException e) 
        {
            System.out.println("Connection Failed! Check output console");
            System.out.println("Failed stage 3");
            e.printStackTrace();
            return;
	}
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Beats per Minute");
        final LineChart<String, Number> HeartRateChart = new LineChart<String, Number>(xAxis, yAxis);
        HeartRateChart.setAnimated(false);
        HeartRateChart.setTitle("Heart Rate (in BPM)");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("BPM");
        String tempDate;
        int tempSteps;
        
        tempDate = dateList.get(9).substring(5);
        tempSteps = Integer.parseInt(heartList.get(9));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(8).substring(5);
        tempSteps = Integer.parseInt(heartList.get(8));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(7).substring(5);
        tempSteps = Integer.parseInt(heartList.get(7));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(6).substring(5);
        tempSteps = Integer.parseInt(heartList.get(6));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(5).substring(5);
        tempSteps = Integer.parseInt(heartList.get(5));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));

        tempDate = dateList.get(4).substring(5);
        tempSteps = Integer.parseInt(heartList.get(4));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(3).substring(5);
        tempSteps = Integer.parseInt(heartList.get(3));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(2).substring(5);
        tempSteps = Integer.parseInt(heartList.get(2));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(1).substring(5);
        tempSteps = Integer.parseInt(heartList.get(1));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
        
        tempDate = dateList.get(0).substring(5);
        tempSteps = Integer.parseInt(heartList.get(0));
        series1.getData().add(new XYChart.Data(tempDate, tempSteps));
 
        final Scene scene = new Scene(HeartRateChart, 800, 600);
        
        try
        {
            scene.getStylesheets().add("chart.css");
        }
        catch (Exception ex)
        { 
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
        HeartRateChart.getData().addAll(series1);
        stage.setScene(scene);
        //stage.show();

        Task task = new Task<Void>() 
        {
            @Override
            public Void call() 
            {
                Platform.runLater(new Runnable() 
                {
                    public void run() 
                    {
                        try 
                        {
                            WritableImage wim = new WritableImage(800, 600);
                            scene.snapshot(wim);
                            File file = new File("HeartRateChart.png");
                            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
                        } 
                        catch (Exception s) 
                        {
                            System.out.println("File Write Error!");
                        }
                        System.out.println("|||||||||| Finished BMI");
                    }});

                return null;
            }
        };
        Thread thread10 = new Thread(task);
        thread10.start();
        //Platform.exit();
    }
} 
