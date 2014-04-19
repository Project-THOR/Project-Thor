
package cse360project;

import java.util.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.event.EventHandler;

public class ScreensController extends StackPane
{
    public ScreensController()
    {
        super();
    }
    
     // Holds the screens that are going to be displayed
    public HashMap<String, Node> screens = new HashMap<>(); 
    
    public void addScreen(String name, Node screen) 
    {
        screens.put(name, screen);
    } 
    
    public Node getScreen(String screenName)
    {
        return screens.get(screenName);
    }
    
    public boolean loadScreen(String name, String resource) 
    {
        try 
        {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            TransitionController myScreenController = ((TransitionController) myLoader.getController());
            myScreenController.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        }
        catch(Exception e) 
        {
            System.out.println(e.getMessage());
            return false;
        }
    } 
    
    public boolean setScreen(final String name)
    {
        if (screens.get(name) != null) 
        { //screen loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) 
            { //if there is more than one screen
                Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)), 
                                             new KeyFrame(new Duration(100), 
                                             new EventHandler<ActionEvent>() 
                {
                    @Override
                    public void handle(ActionEvent t) 
                    {
                        getChildren().remove(0);                                                            //remove the displayed screen
                        getChildren().add(0, screens.get(name));                                            //add the screen
                        Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, 
                                                       new KeyValue(opacity, 0.0)),
                                                       new KeyFrame(new Duration(80), 
                                                       new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }
                }, new KeyValue(opacity, 0.0)));
                fade.play();
            } 
            else 
            {
                setOpacity(0.0);
                getChildren().add(screens.get(name));                                                        //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, 
                                               new KeyValue(opacity, 0.0)),
                                               new KeyFrame(new Duration(250), 
                                               new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } 
        else 
        {
            System.out.println("Looks like the screen hasn't been loaded... \n");
            return false;
        }
    }
    
     public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("**SPOILER ALERT** That screen never existed!");
            return false;
        } 
        else 
        {
            return true;
        }
    }
}

