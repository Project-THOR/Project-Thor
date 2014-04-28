/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cse360project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Risingdamp220
 */
public class ExportScreenController implements Initializable {
    @FXML
    private Label UsernameDisplayLabel;
    @FXML
    private Button CaloriesCancelButton;
    @FXML
    private Button CaloriesSaveButton;
    @FXML
    private LineChart<?, ?> CaloriesGraph;
    @FXML
    private DatePicker CaloriesDatePicker;
    @FXML
    private TextField CaloriesTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelButtonPressed(ActionEvent event) {
    }

    @FXML
    private void saveButtonPressed(ActionEvent event) {
    }
    
}
