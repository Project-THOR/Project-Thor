
package cse360project;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class ScreensFramework extends Application 
{
    public static String loginScreenID              = "loginScreen";
    public static String loginScreenFile            = "LoginScreen.fxml";
    
    public static String mainScreenID               = "mainScreen";
    public static String mainScreenFile             = "MainScreen.fxml";
    
    public static String stepScreenID               = "stepScreen";
    public static String stepScreenFile             = "StepScreen.fxml";
    
    public static String bloodGlucoseScreenID       = "bloodGlucoseScreen";
    public static String bloodGlucoseScreenFile     = "BloodGlucoseScreen.fxml";
    
    public static String bloodPressureScreenID      = "bloodPressureScreen";
    public static String bloodPressureScreenFile    = "BloodPressureScreen.fxml";
    
    public static String caloriesScreenID           = "caloriesScreen";
    public static String caloriesScreenFile         = "CaloriesScreen.fxml";
   
    public static String heartRateScreenID          = "heartRateScreen";
    public static String heartRateScreenFile        = "HeartRateScreen.fxml";
    
    public static String physicalActivityScreenID   = "physicalActivityScreen";
    public static String physicalActivityScreenFile = "PhysicalActivityScreen.fxml";
    
    public static String profileScreenID            = "profileScreen";
    public static String profileScreenFile          = "ProfileScreen.fxml";
    
    public static String reportScreenID             = "reportScreen";
    public static String reportScreenFile           = "ReportScreen.fxml";
    
    public static String sleepScreenID              = "sleepScreen";
    public static String sleepScreenFile            = "SleepScreen.fxml";
    
    public static String newAccountScreenID         = "newAccount";
    public static String newAccountScreenFile       = "NewAccountScreen.fxml";
            
    public static String forgotPasswordScreenID     = "forgotPasswordScreen";
    public static String forgotPasswordScreenFile   = "ForgotPasswordScreen.fxml";
 
    public static ScreensController mainContainer = new ScreensController();
   
    @Override
    //  This is what passes for "main" in this case.  It essentially sets up all
    //  of the scenes once the program starts by placing them into an array so 
    //  at any given moment there is only 1 scene taking up the 
    public void start(Stage primaryStage) 
    {   
        //ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(ScreensFramework.loginScreenID, ScreensFramework.loginScreenFile);
        mainContainer.loadScreen(ScreensFramework.mainScreenID,  ScreensFramework.mainScreenFile);
        mainContainer.loadScreen(ScreensFramework.stepScreenID,  ScreensFramework.stepScreenFile);
        mainContainer.loadScreen(ScreensFramework.bloodGlucoseScreenID, ScreensFramework.bloodGlucoseScreenFile);
        mainContainer.loadScreen(ScreensFramework.bloodPressureScreenID, ScreensFramework.bloodPressureScreenFile);
        mainContainer.loadScreen(ScreensFramework.caloriesScreenID, ScreensFramework.caloriesScreenFile);
        mainContainer.loadScreen(ScreensFramework.heartRateScreenID, ScreensFramework.heartRateScreenFile);
        mainContainer.loadScreen(ScreensFramework.physicalActivityScreenID, ScreensFramework.physicalActivityScreenFile);
        mainContainer.loadScreen(ScreensFramework.profileScreenID, ScreensFramework.profileScreenFile);
        mainContainer.loadScreen(ScreensFramework.reportScreenID, ScreensFramework.reportScreenFile);
        mainContainer.loadScreen(ScreensFramework.sleepScreenID, ScreensFramework.sleepScreenFile);
        mainContainer.loadScreen(ScreensFramework.newAccountScreenID, ScreensFramework.newAccountScreenFile);
        mainContainer.loadScreen(ScreensFramework.forgotPasswordScreenID, ScreensFramework.forgotPasswordScreenFile);
        mainContainer.setScreen(ScreensFramework.loginScreenID);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        try
        {
            scene.getStylesheets().add("chart.css");
        }
        catch (Exception ex)
        { 
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
        primaryStage.setTitle("THOR - The Health Organizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void GlobalRefresh()
    {
        mainContainer.unloadScreen(loginScreenID);
        mainContainer.unloadScreen(mainScreenID);
        mainContainer.unloadScreen(stepScreenID);
        mainContainer.unloadScreen(bloodGlucoseScreenID);
        mainContainer.unloadScreen(bloodPressureScreenID);
        mainContainer.unloadScreen(caloriesScreenID);
        mainContainer.unloadScreen(heartRateScreenID);
        mainContainer.unloadScreen(physicalActivityScreenID);
        mainContainer.unloadScreen(profileScreenID);
        mainContainer.unloadScreen(reportScreenID);
        mainContainer.unloadScreen(sleepScreenID);
        mainContainer.unloadScreen(newAccountScreenID);
        mainContainer.unloadScreen(forgotPasswordScreenID);
        
        mainContainer.loadScreen(ScreensFramework.loginScreenID, ScreensFramework.loginScreenFile);
        mainContainer.loadScreen(ScreensFramework.mainScreenID,  ScreensFramework.mainScreenFile);
        mainContainer.loadScreen(ScreensFramework.stepScreenID,  ScreensFramework.stepScreenFile);
        mainContainer.loadScreen(ScreensFramework.bloodGlucoseScreenID, ScreensFramework.bloodGlucoseScreenFile);
        mainContainer.loadScreen(ScreensFramework.bloodPressureScreenID, ScreensFramework.bloodPressureScreenFile);
        mainContainer.loadScreen(ScreensFramework.caloriesScreenID, ScreensFramework.caloriesScreenFile);
        mainContainer.loadScreen(ScreensFramework.heartRateScreenID, ScreensFramework.heartRateScreenFile);
        mainContainer.loadScreen(ScreensFramework.physicalActivityScreenID, ScreensFramework.physicalActivityScreenFile);
        mainContainer.loadScreen(ScreensFramework.profileScreenID, ScreensFramework.profileScreenFile);
        mainContainer.loadScreen(ScreensFramework.reportScreenID, ScreensFramework.reportScreenFile);
        mainContainer.loadScreen(ScreensFramework.sleepScreenID, ScreensFramework.sleepScreenFile); 
        mainContainer.loadScreen(ScreensFramework.newAccountScreenID, ScreensFramework.newAccountScreenFile);  
        mainContainer.loadScreen(ScreensFramework.forgotPasswordScreenID, ScreensFramework.forgotPasswordScreenFile);  
    }
    // In this case the main method is ignored this is just a fallback
    public static void main(String[] args) 
    {
        launch(args);
    }
}
