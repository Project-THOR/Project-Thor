
package cse360project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.*;

public class MainScreenController implements Initializable, TransitionController
{
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
       myController.setScreen(ScreensFramework.stepScreenID);
    }
   
    
    @FXML 
    private void goToPhysicalActivityScreen(ActionEvent event)
    {
        myController.setScreen(ScreensFramework.physicalActivityScreenID);
    }
    
    @FXML 
    private void goToHeartRateScreen(ActionEvent event)
    {
        myController.setScreen(ScreensFramework.heartRateScreenID);
    }
    
    @FXML 
    private void goToBloodGlucoseScreen(ActionEvent event)
    {
        myController.setScreen(ScreensFramework.bloodGlucoseScreenID);
    }
    
    @FXML 
    private void goToBloodPressureScreen(ActionEvent event)
    {
        myController.setScreen(ScreensFramework.bloodPressureScreenID);
    }
    
    @FXML 
    private void goToSleepScreen(ActionEvent event)
    {
        myController.setScreen(ScreensFramework.sleepScreenID);
    }
    
    @FXML 
    private void goToProfileScreen(ActionEvent event)
    {
        myController.setScreen(ScreensFramework.profileScreenID);
    }
    
    @FXML 
    private void goToReportScreen(ActionEvent event)
    {
        myController.setScreen(ScreensFramework.reportScreenID);
    }
    
    @FXML 
    private void goToExportScreen(ActionEvent event)
    {
        myController.setScreen(ScreensFramework.exportScreenID);
    }
}
