
package cse360project;

import java.lang.*;
import java.net.URL;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class PhysicalActivityScreenController implements Initializable, TransitionController 
{
    ScreensController myController;

    public static String stepsUserName      = LoginScreenController.userName;
    public static String password           = LoginScreenController.password;
    public static String url                = "jdbc:mysql://168.62.213.183:3306/";
    public static String dbName             = "mydb";
    public static String driver             = "com.mysql.jdbc.Driver";
    public static String databaseUserName   = "CSE360Team";
    public static String databasePassword   = "FitnessTeam#360";
    
    
    public String physicalActivityType, physicalDate, minutes;
    
    

    @FXML
    private Button PhysicalActivitySaveButton;
    @FXML
    private Button PhysicalActivityCancelButton;
    @FXML
    private Label UsernameDisplayLabel;
    @FXML
    private Label PhysicalActivityTypeLabel, physicalDateLabel, minutesLabel;
    @FXML
    private DatePicker physicalDatePicker;
    @FXML
    public BarChart<String, Number> PhysicalActivityGraph;
    @FXML
    private ChoiceBox physicalActivityTypeChoiceBox;
    @FXML
    private TextField minutesTextField;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        UsernameDisplayLabel.setText(LoginScreenController.userName);     
        physicalActivityTypeChoiceBox.setItems(FXCollections.observableArrayList("Cardio","Strength Training"));   
        // Code that displays activies data on the chart
        ObservableList<XYChart.Series<String, Number>> BarChartData = FXCollections.observableArrayList();
        BarChart.Series<String, Number> series = new BarChart.Series<String, Number>();
        series.setName("Activity");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");   
        yAxis.setLabel("Minutes");
        
        // For some reason it won't let me use a for/foreach loop to add 
        String tempDate = MainScreenController.dateList.get(0);
        int tempActivity = Integer.parseInt(MainScreenController.activityList.get(0));
        series.getData().add(new XYChart.Data(tempDate, tempActivity));
        
        tempDate = MainScreenController.dateList.get(1);
        tempActivity = Integer.parseInt(MainScreenController.activityList.get(1));
        series.getData().add(new XYChart.Data(tempDate, tempActivity));
        
        tempDate = MainScreenController.dateList.get(2);
        tempActivity = Integer.parseInt(MainScreenController.activityList.get(2));
        series.getData().add(new XYChart.Data(tempDate, tempActivity));
        
        tempDate = MainScreenController.dateList.get(3);
        tempActivity = Integer.parseInt(MainScreenController.activityList.get(3));
        series.getData().add(new XYChart.Data(tempDate, tempActivity));
        
        tempDate = MainScreenController.dateList.get(4);
        tempActivity = Integer.parseInt(MainScreenController.activityList.get(4));
        series.getData().add(new XYChart.Data(tempDate, tempActivity));
        
        BarChartData.add(series);
        
        PhysicalActivityGraph.setData(BarChartData);
        PhysicalActivityGraph.barGapProperty();
    
    }  
    
    @Override
    public void setScreenParent(ScreensController screenParent)
    {
        myController = screenParent;
    }
    @FXML
    
    
    
    private void goToMainScreen()
    {
        myController.setScreen(ScreensFramework.mainScreenID);
    }
    
    @FXML
    private void saveButtonPressed(ActionEvent event)
    {
       
       physicalActivityType =  physicalActivityTypeChoiceBox.getValue().toString();
       physicalDate     = physicalDatePicker.getValue().toString();
       minutes = minutesTextField.getText();
       
       
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");
	} 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
	}
 
	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;
 
	try 
        {
            // Makes connection to database
            connection = DriverManager.getConnection(url + dbName,databaseUserName, databasePassword);
            if (connection != null) 
            {
                // Checks to see if there is already an entry in the database for the user on the spcified date
                String activityQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ LoginScreenController.userName +"' AND date = '" + physicalDate + "'";
                PreparedStatement checkStatement = connection.prepareStatement(activityQuery);
                ResultSet result = checkStatement.executeQuery();
                // If there is already an entry for that date, the UPDATE statement is used so that it will just update the exiting entry for that 
                // date instead of creating a whole new entry with the same date.  --->NOTE: Spaces are important <----
                if(result.next())
                {
                    String physicalUpdate = "UPDATE mydb.userData SET activity = '" + physicalActivityType +"' WHERE user_name = '" + 
                                                                                LoginScreenController.userName + "' AND date = '" + physicalDate + "'";
                    String physicalUpdate1 = "UPDATE mydb.userData SET activityMin = '" + minutes +"' WHERE user_name = '" + 
                                                                                LoginScreenController.userName + "' AND date = '" + physicalDate + "'";
                    
                    Statement updateStatement = connection.createStatement();
                    updateStatement.executeUpdate(physicalUpdate);
                    updateStatement.executeUpdate(physicalUpdate1);
                    goToMainScreen();
                    connection.close();
                }
                // If there is not an entry already in the database for the specified date, the INSERT statement is used to create a new entry in the database.
                else
                {
                    String physicalInsert = "INSERT INTO mydb.userData (user_name, date, activity, activityMin ) VALUES (\""+ LoginScreenController.userName+ "\",\"" + 
                                                                                                           physicalDate + "\", \"" + physicalActivityType +"\",\"" + minutes+"\")";
                    Statement insertStatement = connection.createStatement();
                    // Executes the statement and writes to the datebase.
                    insertStatement.executeUpdate(physicalInsert);
                    // Returns to the main screen 
                    goToMainScreen();
                    // Closes the connection to the database.
                    connection.close();
                }
            }
            else 
            {
                System.out.println("Failed to make connection!");
            }
	} 
        catch (SQLException e) 
        {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
	}
        // Refreshes all of the scenes so that newly entered data will be reflected  
        ScreensFramework.GlobalRefresh();
    }
    
    @FXML
    private void cancelButtonPressed(ActionEvent event)
    {
        goToMainScreen();
    }
}
