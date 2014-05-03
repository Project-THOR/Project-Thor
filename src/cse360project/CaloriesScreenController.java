
package cse360project;

import java.lang.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

public class CaloriesScreenController implements Initializable, TransitionController 
{
    ScreensController myController;

    public static String stepsUserName      = LoginScreenController.userName;
    public static String password           = LoginScreenController.password;
    public static String url                = "jdbc:mysql://168.62.213.183:3306/";
    public static String dbName             = "mydb";
    public static String driver             = "com.mysql.jdbc.Driver";
    public static String databaseUserName   = "CSE360Team";
    public static String databasePassword   = "FitnessTeam#360";
    
    public String numberOfCalories;
    public String caloriesDate;
 
    @FXML
    private Button CaloriesSaveButton;
    @FXML
    private Button CaloriesCancelButton;
    @FXML
    private TextField CaloriesTextField;
    @FXML
    private DatePicker CaloriesDatePicker;
    @FXML
    private Label UsernameDisplayLabel;
    @FXML
    public LineChart<String, Number> CaloriesGraph;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        UsernameDisplayLabel.setText(LoginScreenController.userName);
        
        ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
        LineChart.Series<String, Number> series = new LineChart.Series<String, Number>();
        series.setName("Calories");
 
        String tempDate;
        int tempCalories;
        // For some reason it won't let me use a for/foreach loop to add 
        tempDate = MainScreenController.dateList.get(4);
        tempCalories = Integer.parseInt(MainScreenController.caloriesList.get(4));
        series.getData().add(new XYChart.Data(tempDate, tempCalories));
        
        tempDate = MainScreenController.dateList.get(3);
        tempCalories = Integer.parseInt(MainScreenController.caloriesList.get(3));
        series.getData().add(new XYChart.Data(tempDate, tempCalories));
        
        tempDate = MainScreenController.dateList.get(2);
        tempCalories = Integer.parseInt(MainScreenController.caloriesList.get(2));
        series.getData().add(new XYChart.Data(tempDate, tempCalories));
        
        tempDate = MainScreenController.dateList.get(1);
        tempCalories = Integer.parseInt(MainScreenController.caloriesList.get(1));
        series.getData().add(new XYChart.Data(tempDate, tempCalories));
        
        tempDate = MainScreenController.dateList.get(0);
        tempCalories= Integer.parseInt(MainScreenController.caloriesList.get(0));
        series.getData().add(new XYChart.Data(tempDate, tempCalories));
        
        lineChartData.add(series);
        
        CaloriesGraph.setData(lineChartData);
        CaloriesGraph.createSymbolsProperty();
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
        numberOfCalories = CaloriesTextField.getText();
        caloriesDate     = CaloriesDatePicker.getValue().toString();
        
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
            // --->NOTE:  Using the local user name variable instead of LoginScreenController.userName<---
            // --->NOTE:  causes a database error so for now we can just use the LoginScreenController version of the variable<---
            connection = DriverManager.getConnection(url + dbName,databaseUserName, databasePassword);
            if (connection != null) 
            {
                // Checks to see if there is already an entry in the database for the user on the spcified date
                String caloriesQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ LoginScreenController.userName +"' AND date = '" + caloriesDate + "'";
                PreparedStatement checkStatement = connection.prepareStatement(caloriesQuery);
                ResultSet result = checkStatement.executeQuery();
                // If there is already an entry for that date, the UPDATE statement is used so that it will just update the exiting entry for that 
                // date instead of creating a whole new entry with the same date.  --->NOTE: Spaces are important <----
                if(result.next())
                {
                    String caloriesUpdate = "UPDATE mydb.userData SET calories = '" + numberOfCalories +"' WHERE user_name = '" + 
                                                                                LoginScreenController.userName + "' AND date = '" + caloriesDate + "'"; 
                    Statement updateStatement = connection.createStatement();
                    updateStatement.executeUpdate(caloriesUpdate);
                    goToMainScreen();
                    connection.close();
                }
                // If there is not an entry already in the database for the specified date, the INSERT statement is used to create a new entry in the database.
                else
                {
                    String caloriesInsert = "INSERT INTO mydb.userData (user_name, date, calories ) VALUES (\""+ LoginScreenController.userName+ "\",\"" + 
                                                                                                           caloriesDate + "\", \"" + numberOfCalories +"\")";
                    Statement insertStatement = connection.createStatement();
                    // Executes the statement and writes to the datebase.
                    insertStatement.executeUpdate(caloriesInsert);
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
