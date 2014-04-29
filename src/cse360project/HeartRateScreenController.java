
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class HeartRateScreenController implements Initializable, TransitionController 
{
    ScreensController myController;

    public static String stepsUserName      = LoginScreenController.userName;
    public static String password           = LoginScreenController.password;
    public static String url                = "jdbc:mysql://168.62.213.183:3306/";
    public static String dbName             = "mydb";
    public static String driver             = "com.mysql.jdbc.Driver";
    public static String databaseUserName   = "CSE360Team";
    public static String databasePassword   = "FitnessTeam#360";
    
    public String heartRate;
    public String heartDate;

    @FXML
    private Button HeartRateSaveButton;
    @FXML
    private Button HeartRateCancelButton;
    @FXML
    private Label UsernameDisplayLabel;
    @FXML
    private DatePicker HeartRateDatePicker;
    @FXML
    private TextField HeartRateTextBox;
    @FXML
    public LineChart<String, Number> HeartRateGraph;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        UsernameDisplayLabel.setText(LoginScreenController.userName);
        
        ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
        LineChart.Series<String, Number> series = new LineChart.Series<String, Number>();
        series.setName("Heart Rate");
 
        String tempDate;
        int tempHeartRate;
        // For some reason it won't let me use a for/foreach loop to add 
        tempDate = MainScreenController.dateList.get(4);
        tempHeartRate = Integer.parseInt(MainScreenController.heartList.get(4));
        series.getData().add(new XYChart.Data(tempDate, tempHeartRate));
        
        tempDate = MainScreenController.dateList.get(3);
        tempHeartRate = Integer.parseInt(MainScreenController.heartList.get(3));
        series.getData().add(new XYChart.Data(tempDate, tempHeartRate));
        
        tempDate = MainScreenController.dateList.get(2);
        tempHeartRate = Integer.parseInt(MainScreenController.heartList.get(2));
        series.getData().add(new XYChart.Data(tempDate, tempHeartRate));
        
        tempDate = MainScreenController.dateList.get(1);
        tempHeartRate = Integer.parseInt(MainScreenController.heartList.get(1));
        series.getData().add(new XYChart.Data(tempDate, tempHeartRate));
        
        tempDate = MainScreenController.dateList.get(0);
        tempHeartRate = Integer.parseInt(MainScreenController.heartList.get(0));
        series.getData().add(new XYChart.Data(tempDate, tempHeartRate));
        
        lineChartData.add(series);
        
        HeartRateGraph.setData(lineChartData);
        HeartRateGraph.createSymbolsProperty();
    }  
    
    @Override
    public void setScreenParent(ScreensController screenParent)
    {
        myController = screenParent;
    }
    
    private void goToMainScreen()
    {
        myController.setScreen(ScreensFramework.mainScreenID);
    }
    
    @FXML
    private void saveButtonPressed(ActionEvent event)
    {
        heartRate = HeartRateTextBox.getText();
        heartDate = HeartRateDatePicker.getValue().toString();
        
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
                String stepsQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ LoginScreenController.userName +"' AND date = '" + heartDate + "'";
                PreparedStatement checkStatement = connection.prepareStatement(stepsQuery);
                ResultSet result = checkStatement.executeQuery();
                // If there is already an entry for that date, the UPDATE statement is used so that it will just update the exiting entry for that 
                // date instead of creating a whole new entry with the same date.  --->NOTE: Spaces are important <----
                if(result.next())
                {
                    String stepsUpdate = "UPDATE mydb.userData SET heartRate = '" + heartRate +"' WHERE user_name = '" + 
                                                                                LoginScreenController.userName + "' AND date = '" + heartDate + "'"; 
                    Statement updateStatement = connection.createStatement();
                    updateStatement.executeUpdate(stepsUpdate);
                    goToMainScreen();
                    connection.close();
                }
                // If there is not an entry already in the database for the specified date, the INSERT statement is used to create a new entry in the database.
                else
                {
                    String stepsInsert = "INSERT INTO mydb.userData (user_name, date, heartRate ) VALUES (\""+ LoginScreenController.userName+ "\",\"" + 
                                                                                                           heartDate + "\", \"" + heartRate +"\")";
                    Statement insertStatement = connection.createStatement();
                    // Executes the statement and writes to the datebase.
                    insertStatement.executeUpdate(stepsInsert);
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
