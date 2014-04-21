
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
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

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
    
    public String numberOfSteps;
    public String stepsDate;

    @FXML
    private Button PhysicalActivitySaveButton;
    @FXML
    private Button PhysicalActivityCancelButton;
    @FXML
    private Label UsernameDisplayLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        UsernameDisplayLabel.setText(LoginScreenController.userName);
    }  
    
    @Override
    public void setScreenParent(ScreensController screenParent)
    {
        myController = screenParent;
    }
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
        
    }
    
    
    private void goToMainScreen()
    {
        myController.setScreen(ScreensFramework.mainScreenID);
    }
    
    @FXML
    private void saveButtonPressed(ActionEvent event)
    {
        //numberOfSteps = NumberOfStepsField.getText();
        //stepsDate     = StepsDateEntryField.getText();
        
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
                String stepsQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ LoginScreenController.userName +"' AND date = '" + stepsDate + "'";
                PreparedStatement checkStatement = connection.prepareStatement(stepsQuery);
                ResultSet result = checkStatement.executeQuery();
                // If there is already an entry for that date, the UPDATE statement is used so that it will just update the exiting entry for that 
                // date instead of creating a whole new entry with the same date.  --->NOTE: Spaces are important <----
                if(result.next())
                {
                    String stepsUpdate = "UPDATE mydb.userData SET steps = '" + numberOfSteps +"' WHERE user_name = '" + 
                                                                                LoginScreenController.userName + "' AND date = '" + stepsDate + "'"; 
                    Statement updateStatement = connection.createStatement();
                    updateStatement.executeUpdate(stepsUpdate);
                    goToMainScreen();
                    connection.close();
                }
                // If there is not an entry already in the database for the specified date, the INSERT statement is used to create a new entry in the database.
                else
                {
                    String stepsInsert = "INSERT INTO mydb.userData (user_name, date, steps ) VALUES (\""+ LoginScreenController.userName+ "\",\"" + 
                                                                                                           stepsDate + "\", \"" + numberOfSteps +"\")";
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
