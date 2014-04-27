
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
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class BloodGlucoseScreenController implements Initializable, TransitionController 
{
    ScreensController myController;

    public static String stepsUserName      = LoginScreenController.userName;
    public static String password           = LoginScreenController.password;
    public static String url                = "jdbc:mysql://168.62.213.183:3306/";
    public static String dbName             = "mydb";
    public static String driver             = "com.mysql.jdbc.Driver";
    public static String databaseUserName   = "CSE360Team";
    public static String databasePassword   = "FitnessTeam#360";
    
    public String glucoseValue;
    public String dateValue;
    
    @FXML
    public TextField glucoseLevel;
    @FXML
    public  DatePicker glucoseLevelDate;
    @FXML
    public TextField time; 
    @FXML
    public TextArea notes;

    @FXML
    private Button BloodGlucoseSaveButton;
    @FXML
    private Button BloodGlucoseCancelButton;
    @FXML
    private Label UsernameDisplayLabel;
    @FXML
    public LineChart<String, Number> glucoseGraph;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {          
       
        // Displays username in the top right corner of the scene
        UsernameDisplayLabel.setText(LoginScreenController.userName);
        // Code that displays steps data on the chart
        ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
        LineChart.Series<String, Number> series = new LineChart.Series<String, Number>();
        series.setName("Glucose Level");
        
        // For some reason it won't let me use a for/foreach loop to add 
        String tempDate = MainScreenController.dateList.get(0);
        int tempLevel = Integer.parseInt(MainScreenController.levelList.get(0));
        series.getData().add(new XYChart.Data(tempDate, tempLevel));
        
        tempDate = MainScreenController.dateList.get(1);
        tempLevel = Integer.parseInt(MainScreenController.levelList.get(1));
        series.getData().add(new XYChart.Data(tempDate, tempLevel));
        
        tempDate = MainScreenController.dateList.get(2);
        tempLevel = Integer.parseInt(MainScreenController.levelList.get(2));
        series.getData().add(new XYChart.Data(tempDate, tempLevel));
        
        tempDate = MainScreenController.dateList.get(3);
        tempLevel  = Integer.parseInt(MainScreenController.levelList.get(3));
        series.getData().add(new XYChart.Data(tempDate, tempLevel));
        
        tempDate = MainScreenController.dateList.get(4);
        tempLevel  = Integer.parseInt(MainScreenController.levelList.get(4));
        series.getData().add(new XYChart.Data(tempDate, tempLevel));
        
        lineChartData.add(series);
        
        glucoseGraph.setData(lineChartData);
        glucoseGraph.createSymbolsProperty();
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
        glucoseValue = glucoseLevel.getText();
        dateValue = glucoseLevelDate.getValue().toString();
        
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
                String glucoseQuery = "SELECT * FROM mydb.userData WHERE user_name = '"+ LoginScreenController.userName +"' AND date = '" + dateValue + "'";
                PreparedStatement checkStatement = connection.prepareStatement(glucoseQuery);
                ResultSet result = checkStatement.executeQuery();
                // If there is already an entry for that date, the UPDATE statement is used so that it will just update the exiting entry for that 
                // date instead of creating a whole new entry with the same date.  --->NOTE: Spaces are important <----
                if(result.next())
                {
                    String glucoseLevelUpdate = "UPDATE mydb.userData SET bloodGlucose = '" + glucoseValue +"' WHERE user_name = '" + 
                                                                                LoginScreenController.userName + "' AND date = '" + dateValue + "'"; 
                    Statement updateStatement = connection.createStatement();
                    updateStatement.executeUpdate(glucoseLevelUpdate);
                    goToMainScreen();
                    connection.close();
                }
                // If there is not an entry already in the database for the specified date, the INSERT statement is used to create a new entry in the database.
                else
                {
                    String glucoseLevelInsert = "INSERT INTO mydb.userData (user_name, date, bloodGlucose ) VALUES (\""+ LoginScreenController.userName+ "\",\"" + 
                                                                                                           dateValue + "\", \"" + glucoseValue +"\")";
                    Statement insertStatement = connection.createStatement();
                    // Executes the statement and writes to the datebase.
                    insertStatement.executeUpdate(glucoseLevelInsert);
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
    
    public void showEntryError()
    {
        Stage newStage = new Stage();
        VBox comp = new VBox();
        Label loginError = new Label("Please Enter a Number");
        comp.getChildren().add(loginError);
        Scene stageScene = new Scene(comp, 300, 300);
        newStage.setScene(stageScene);
        newStage.show();
    }
    
    @FXML
    private void cancelButtonPressed(ActionEvent event)
    {
        goToMainScreen();
    }
}
