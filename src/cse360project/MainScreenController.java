
package cse360project;

import static cse360project.LoginScreenController.showLoginError;
import java.util.*;
import java.net.URL;
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


public class MainScreenController implements Initializable, TransitionController
{
    public static String stepsUserName      = LoginScreenController.userName;
    public static String password           = LoginScreenController.password;
    public static String url                = "jdbc:mysql://168.62.213.183:3306/";
    public static String dbName             = "mydb";
    public static String driver             = "com.mysql.jdbc.Driver";
    public static String databaseUserName   = "CSE360Team";
    public static String databasePassword   = "FitnessTeam#360";
    
    public static List<String> stepsList = new ArrayList<String>();
    public static List<String> dateList = new ArrayList<String>();
    public static List<String> levelList = new ArrayList<String>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        UsernameDisplayLabel.setText(LoginScreenController.userName);
    } 
       
    ScreensController myController;
    
    @FXML
    private Label       UsernameDisplayLabel;
    @FXML
    private Button      StepsButton;
    @FXML
    private Button      PhysicalActivityButton;
    @FXML
    private Button      HeartRateButton;
    @FXML
    private Button      BloodGlucoseButton;
    @FXML
    private Button      BloodPressureButton;
    @FXML
    private Button      SleepButton;
    @FXML
    private Button      ProfileButton;
    @FXML
    private Button      ReportButton;
    @FXML
    private Button      ExportButton;
    @FXML
    private Button      LogoutButton;
   
     
    
    @Override
    public void setScreenParent(ScreensController screenParent)
    {
        myController = screenParent;
    }
      
    @FXML
    private void goToLoginScreen(ActionEvent event)
    {
       myController.setScreen(ScreensFramework.loginScreenID);
    }
    
    @FXML
    private void goToStepScreen(ActionEvent event)
    {
        stepsList.clear();
        dateList.clear();
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
            connection = DriverManager.getConnection(url + dbName,databaseUserName, databasePassword);
            if (connection != null) 
            {
                // mySQL Query: SELECT * FROM(SELECT * FROM mydb.userdata WHERE user_name = 'TestUser' ORDER BY date DESC LIMIT 5) sub ORDER BY date ASC;
                // It selects the userdata of the user that is logged in and gets the 5 latest (DESC) entries by date and then sorts so we get the dates
                // in order of Earliest date to the most recent date
                //String graphQuery = "SELECT * FROM(SELECT * FROM mydb.userData WHERE user_name = '"+ LoginScreenController.userName + 
                                                                                     //   "' ORDER BY date DESC LIMIT 6) sub ORDER BY date ASC";
                String graphQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ LoginScreenController.userName +  "' ORDER BY date DESC";
                PreparedStatement dataStatement = connection.prepareStatement(graphQuery);
                ResultSet dataResult = dataStatement.executeQuery();
                if(dataResult.next())
                {
                    while(dataResult.next())
                    {
                        if(dataResult.getString(14) != null && dataResult.getString(3) != null)
                        {
                            // adds the values that were obtained from the database to a local arraylist
                            dateList.add(dataResult.getString(3));
                            stepsList.add(dataResult.getString(14));
                        }
                        else
                        {
                            // do nothing
                        }
                        /*System.out.println();
                        System.out.print(dataResult.getString(2)+ " \t\t");
                        System.out.print(dataResult.getString(3)+ " \t");
                        System.out.print(dataResult.getString(14));  
                        System.out.println();*/
                    }   
                }
                         
                else
                {
                    showLoginError();
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
         // Refreshes all of the scenes so that newly entered data will be reflected  
        ScreensFramework.GlobalRefresh();
       myController.setScreen(ScreensFramework.stepScreenID);
    }
   
    
    @FXML 
    private void goToPhysicalActivityScreen(ActionEvent event)
    {
        // Refreshes all of the scenes so that newly entered data will be reflected <--- NOTE: Goes right before the screen transition
        ScreensFramework.GlobalRefresh();
        myController.setScreen(ScreensFramework.physicalActivityScreenID);
    }
    
    @FXML 
    private void goToHeartRateScreen(ActionEvent event)
    {
        // Refreshes all of the scenes so that newly entered data will be reflected <--- NOTE: Goes right before the screen transition
        ScreensFramework.GlobalRefresh();
        myController.setScreen(ScreensFramework.heartRateScreenID);
    }
    
    @FXML 
    private void goToBloodGlucoseScreen(ActionEvent event)
    {
        levelList.clear();
        dateList.clear();
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
            connection = DriverManager.getConnection(url + dbName,databaseUserName, databasePassword);
            if (connection != null) 
            {
                // mySQL Query: SELECT * FROM(SELECT * FROM mydb.userdata WHERE user_name = 'TestUser' ORDER BY date DESC LIMIT 5) sub ORDER BY date ASC;
                // It selects the userdata of the user that is logged in and gets the 5 latest (DESC) entries by date and then sorts so we get the dates
                // in order of Earliest date to the most recent date
                String graphQuery = "SELECT * FROM(SELECT * FROM mydb.userData WHERE user_name = '"+ LoginScreenController.userName + 
                                                                                        "' ORDER BY date DESC LIMIT 6) sub ORDER BY date ASC";
                PreparedStatement dataStatement = connection.prepareStatement(graphQuery);
                ResultSet dataResult = dataStatement.executeQuery();
                if(dataResult.next())
                {
                    System.out.print("Username \t\tDate \t\tSteps");
                    while(dataResult.next())
                    {
                        // adds the values that were obtained from the database to a local arraylist
                        dateList.add(dataResult.getString(3));
                        levelList.add(dataResult.getString(12));
                        // Writes data to the console for debugging purposes.
                        System.out.println();
                        System.out.print(dataResult.getString(2)+ " \t\t");
                        System.out.print(dataResult.getString(3)+ " \t");
                        System.out.print(dataResult.getString(12));  
                        System.out.println();
                    }   
                }
                         
                else
                {
                    showLoginError();
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
        
// Refreshes all of the scenes so that newly entered data will be reflected <--- NOTE: Goes right before the screen transition
        ScreensFramework.GlobalRefresh();
        myController.setScreen(ScreensFramework.bloodGlucoseScreenID);
    
    }
    
    @FXML 
    private void goToBloodPressureScreen(ActionEvent event)
    {
        // Refreshes all of the scenes so that newly entered data will be reflected <--- NOTE: Goes right before the screen transition
        ScreensFramework.GlobalRefresh();
        myController.setScreen(ScreensFramework.bloodPressureScreenID);
    }
    
    @FXML 
    private void goToSleepScreen(ActionEvent event)
    {
        // Refreshes all of the scenes so that newly entered data will be reflected <--- NOTE: Goes right before the screen transition
        ScreensFramework.GlobalRefresh();
        myController.setScreen(ScreensFramework.sleepScreenID);
    }
    
    @FXML 
    private void goToProfileScreen(ActionEvent event)
    {
        // Refreshes all of the scenes so that newly entered data will be reflected <--- NOTE: Goes right before the screen transition
        ScreensFramework.GlobalRefresh();
        myController.setScreen(ScreensFramework.profileScreenID);
    }
    
    @FXML 
    private void goToReportScreen(ActionEvent event)
    {
        // Refreshes all of the scenes so that newly entered data will be reflected <--- NOTE: Goes right before the screen transition
        ScreensFramework.GlobalRefresh();
        myController.setScreen(ScreensFramework.reportScreenID);
    }
    
    @FXML 
    private void goToExportScreen(ActionEvent event)
    {
        stepsList.clear();
        dateList.clear();
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
            connection = DriverManager.getConnection(url + dbName,databaseUserName, databasePassword);
            if (connection != null) 
            {
                // mySQL Query: SELECT * FROM(SELECT * FROM mydb.userdata WHERE user_name = 'TestUser' ORDER BY date DESC LIMIT 5) sub ORDER BY date ASC;
                // It selects the userdata of the user that is logged in and gets the 5 latest (DESC) entries by date and then sorts so we get the dates
                // in order of Earliest date to the most recent date
                String graphQuery = "SELECT * FROM(SELECT * FROM mydb.userData WHERE user_name = '"+ LoginScreenController.userName + 
                                                                                        "' ORDER BY date DESC LIMIT 6) sub ORDER BY date ASC";
                PreparedStatement dataStatement = connection.prepareStatement(graphQuery);
                ResultSet dataResult = dataStatement.executeQuery();
                if(dataResult.next())
                {
                    System.out.print("Username \t\tDate \t\tSteps");
                    while(dataResult.next())
                    {
                        // adds the values that were obtained from the database to a local arraylist
                        dateList.add(dataResult.getString(3));
                        stepsList.add(dataResult.getString(14));
                        // Writes data to the console for debugging purposes.
                        System.out.println();
                        System.out.print(dataResult.getString(2)+ " \t\t");
                        System.out.print(dataResult.getString(3)+ " \t");
                        System.out.print(dataResult.getString(13));  
                        System.out.println();
                    }   
                }
                         
                else
                {
                    showLoginError();
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
         // Refreshes all of the scenes so that newly entered data will be reflected  
        ScreensFramework.GlobalRefresh();
       myController.setScreen(ScreensFramework.exportScreenID);
    }
}
