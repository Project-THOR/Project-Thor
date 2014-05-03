
package cse360project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import javafx.stage.*;
import javafx.scene.layout.VBox;
import javafx.scene.*;
import javafx.scene.control.Label;

public class LoginScreenController implements Initializable, TransitionController
{
    // Declaring variables that can be accessed throught the entire THOR program
    public static String userName;
    public static String password;
    public static String url                = "jdbc:mysql://168.62.213.183:3306/";
    public static String dbName             = "mydb";
    public static String driver             = "com.mysql.jdbc.Driver";
    public static String databaseUserName   = "CSE360Team";
    public static String databasePassword   = "FitnessTeam#360";
   
    ScreensController myController;
    
    @FXML
    private Button          LoginButton;
    @FXML
    private Button          ForgotPasswordButton;
    @FXML
    private Button          CreateAccountButton;
    @FXML
    private PasswordField   PasswordField;
    @FXML
    private TextField       UsernameField;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    } 
   
    public void setScreenParent(ScreensController screenParent)
    {
        myController = screenParent;
    }
 
    @FXML
    private void goToMainScreen(ActionEvent event)
    {
        userName = UsernameField.getText();
        password = PasswordField.getText();
        
        System.out.println(userName);
        System.out.println(password);
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
            connection = DriverManager.getConnection(url + dbName,databaseUserName, databasePassword);
            if (connection != null) 
            {
                //String loginQuery = "SELECT * FROM mydb.user WHERE userName = '"+ userName+"' AND userPassword = '"+password+"'";
                String loginQuery = "SELECT * FROM mydb.user WHERE userName = '"+ userName+"'";
                PreparedStatement statement = connection.prepareStatement(loginQuery);
                ResultSet result = statement.executeQuery();
                if(result.next())
                {
                    System.out.println(result.getString(4));
                    if(result.getString(4).compareTo(password) == 0)
                    {
                        ScreensFramework.GlobalRefresh();
                        myController.setScreen(ScreensFramework.mainScreenID);
                        connection.close(); 
                    }
                   /* String pwdQuery = "SELECT * FROM mydb.user WHERE userPassword = '"+ password+"'";
                    PreparedStatement pwdStatement = connection.prepareStatement(pwdQuery);
                    ResultSet pwdResult = pwdStatement.executeQuery();
                    if(pwdResult.next())
                    {
                        
                    } */
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
            e.printStackTrace();
            return;
	}
        //StepsScreenController.populateStepsGraph();
        // Refreshes all of the scenes so that newly entered data will be reflected  
        //ScreensFramework.GlobalRefresh();
    }
    
    @FXML 
    private void goToCreateAccount(ActionEvent event)
    {
        myController.setScreen(ScreensFramework.newAccountScreenID);
        //ScreensFramework.GlobalRefresh();
    }
    
    @FXML 
    private void goToForgotPassword(ActionEvent event)
    {
        //ScreensFramework.GlobalRefresh();
        myController.setScreen(ScreensFramework.forgotPasswordScreenID);
        
    }
  
    public static void showLoginError()
    {
        Stage newStage = new Stage();
        VBox comp = new VBox();
        Label loginError = new Label("Username or Password is Invalid");
        comp.getChildren().add(loginError);
        Scene stageScene = new Scene(comp, 300, 300);
        newStage.setScene(stageScene);
        newStage.show();
    }
}
