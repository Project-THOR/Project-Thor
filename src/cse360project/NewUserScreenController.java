
package cse360project;

import static cse360project.LoginScreenController.userName;
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
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;

import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewUserScreenController implements Initializable, TransitionController 
{
    ScreensController myController;


    public static String newUserName      = LoginScreenController.userName;
    public static String newPassword      = LoginScreenController.password;
    
    public static String url                = "jdbc:mysql://168.62.213.183:3306/";
    public static String dbName             = "mydb";
    public static String driver             = "com.mysql.jdbc.Driver";
    public static String databaseUserName   = "CSE360Team";
    public static String databasePassword   = "FitnessTeam#360";
    
    public String gender = "no data";
    public String securityQuestion = "no data";
    public String numberOfSteps = "no data";
    public String password1 = "no data";
    public String email = "no data";
    public String birthDate = "no data";
    public String answer = "no data";
    
    @FXML
    private TextField  userNameField;
    @FXML
    private TextField  passwordField1;
    @FXML
    private TextField  emailField;   
    @FXML
    private TextField  answerField;
    @FXML
    private DatePicker BirthDatePicker;
    @FXML
    private ChoiceBox  genderBox;
    @FXML
    private ChoiceBox  securityBox;
    @FXML
    private Button     ProfileCreateButton;
    @FXML
    private Button     ProfileCancelButton;


    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        genderBox.setItems(FXCollections.observableArrayList("Select one", new Separator(),"Male","Female"));
        securityBox.setItems(FXCollections.observableArrayList("Select one", new Separator(),"Name of your first pet: ","Street you grew up on: ", "Mother's maiden name:","Name of first teacher: ", "Childhood nickname: ","City or town of first job: "));
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
    
    private void goToLoginScreen()
    {
        myController.setScreen(ScreensFramework.loginScreenID);
    }
    
    @FXML
    private void saveButtonPressed(ActionEvent event)
    {
        userName         = userNameField.getText();
        password1        = passwordField1.getText();
        if(emailField.getText() != null)
        {
            email        = emailField.getText();
        }
        if(answerField.getText() != null)
        {
            answer       = answerField.getText();
        }
        if(genderBox.getValue().toString() != null)
        {
            gender       = genderBox.getValue().toString();
        }
        if(securityBox.getValue().toString() != null)
        {
            securityQuestion =  securityBox.getValue().toString();
        }
         if(BirthDatePicker.getValue().toString() != null)
        {
            birthDate        = BirthDatePicker.getValue().toString();
        }
        
        
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
                String loginQuery = "SELECT * FROM mydb.user WHERE userName = '"+ userName+"'";
                PreparedStatement statement = connection.prepareStatement(loginQuery);
                ResultSet result = statement.executeQuery();
                if(result.next())
                {
                    showUserNameExists();
                }
                else                                                                                       
                {  
                    String userInsert = "INSERT INTO mydb.user (userName, userPassword, userEmail, userGender, birthDate, userAnswer, userQuestion) "
                            + "VALUES ( '"+userName+" ' ,"+" ' "+password1+"',"+" ' "+email+"',"+" ' "+gender+"',"+" ' "+birthDate+"',"+" ' "+answer+"',"+" ' "+securityQuestion+"')";
                   
                    String dataInsert = "INSERT INTO mydb.user (userName, userPassword, userEmail, userGender, birthDate, userAnswer, userQuestion) "
                            + "VALUES ( '"+userName+" ' ,"+" ' "+password1+"',"+" ' "+email+"',"+" ' "+gender+"',"+" ' "+birthDate+"',"+" ' "+answer+"',"+" ' "+securityQuestion+"')";
                    System.out.println(userInsert);
                    Statement insertStatement = connection.createStatement();
                    insertStatement.executeUpdate(userInsert);
                    goToLoginScreen();
                    connection.close();
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
        ScreensFramework.GlobalRefresh();
    }
    public void showUserNameExists()
    {
        Stage newStage = new Stage();
        VBox comp = new VBox();
        Label loginError = new Label("Username Already Exists");
        comp.getChildren().add(loginError);
        Scene stageScene = new Scene(comp, 300, 300);
        newStage.setScene(stageScene);
        newStage.show();
        myController.setScreen(ScreensFramework.loginScreenID);
       
    }  
   
    @FXML
    private void cancelButtonPressed(ActionEvent event)
    {
        ScreensFramework.GlobalRefresh();
        goToLoginScreen();   
    }
}
