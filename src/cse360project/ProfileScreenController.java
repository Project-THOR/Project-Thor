
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileScreenController implements Initializable, TransitionController 
{
    ScreensController myController;

    public static String stepsUserName      = LoginScreenController.userName;
    public static String password           = LoginScreenController.password;
    public static String url                = "jdbc:mysql://168.62.213.183:3306/";
    public static String dbName             = "mydb";
    public static String driver             = "com.mysql.jdbc.Driver";
    public static String databaseUserName   = "CSE360Team";
    public static String databasePassword   = "FitnessTeam#360";
    
    public String date;
    public String height;
    public String weight;
    public String feet;
    public String inches;
    public String bmi;
    public String restingPulse;
    
    private TextField weightField;
    private TextField feetField;
    private TextField inchesField;
    private TextField bmiField;
    private TextField restingPulseField;

    @FXML
    private Button ProfileSaveButton;
    @FXML
    private Button ProfileCancelButton;
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
        weight       = weightField.getText();
        feet         = feetField.getText();
        inches       = inchesField.getText();
        restingPulse = restingPulseField.getText();
        
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
                String stepsQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ LoginScreenController.userName +"' AND date = '" + date + "'";
                PreparedStatement checkStatement = connection.prepareStatement(stepsQuery);
                ResultSet result = checkStatement.executeQuery();
                // If there is already an entry for that date, the UPDATE statement is used so that it will just update the exiting entry for that 
                // date instead of creating a whole new entry with the same date.  --->NOTE: Spaces are important <----
                if(result.next())
                {
                    /* Here we used the same variables (profileUpdate and updateStatement) since it will be overwritten in java, but we have correctly
                    used a different variable when we use SET (for example all the lines are the same, but after 'SET' we have weight and height and bmi)*/
                    String profileUpdate = "UPDATE mydb.userData SET weight = '" + weight +"' WHERE user_name = '" + 
                                                                                LoginScreenController.userName + "' AND date = '" + date + "'"; 
                    
                    Statement updateStatement = connection.createStatement();
                    updateStatement.executeUpdate(profileUpdate);
                    
                    profileUpdate = "UPDATE mydb.userData SET height = '" + height +"' WHERE user_name = '" + 
                                                                                LoginScreenController.userName + "' AND date = '" + date + "'"; 
                    
                    updateStatement = connection.createStatement();
                    updateStatement.executeUpdate(profileUpdate);
                    
                    profileUpdate = "UPDATE mydb.userData SET bmi = '" + bmi +"' WHERE user_name = '" + 
                                                                                LoginScreenController.userName + "' AND date = '" + date + "'"; 
                    
                    updateStatement = connection.createStatement();
                    updateStatement.executeUpdate(profileUpdate);
                    
                    profileUpdate = "UPDATE mydb.userData SET restingPulse = '" + restingPulse +"' WHERE user_name = '" + 
                                                                                LoginScreenController.userName + "' AND date = '" + date + "'"; 
                    
                    updateStatement = connection.createStatement();
                    updateStatement.executeUpdate(profileUpdate);
                    
                    myController.setScreen(ScreensFramework.mainScreenID);
                    connection.close();
                }
                // If there is not an entry already in the database for the specified date, the INSERT statement is used to create a new entry in the database.
                else
                {
                    String profileInsert = "INSERT INTO mydb.userData (user_name, date, weight, height, bmi, restingPulse ) VALUES "
                            + "(\""+ LoginScreenController.userName+ "\",\"" + 
                            date + "\", \"" + weight +"\", \"" + height +"\", \"" + bmi +"\", \"" + restingPulse +"\")";
                    Statement insertStatement = connection.createStatement();
                    // Executes the statement and writes to the datebase.
                    insertStatement.executeUpdate(profileInsert);
                    // Returns to the main screen 
                    myController.setScreen(ScreensFramework.mainScreenID);
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
        myController.setScreen(ScreensFramework.mainScreenID);
    }
}
