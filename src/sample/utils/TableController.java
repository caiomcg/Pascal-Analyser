package sample.utils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import sample.PLA.PSA;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by caiomcg on 27/02/2017.
 */
public class TableController {
    public static ArrayList<TableData> lexicData;
    @FXML
    public TableView table;
    @FXML
    private MenuBar menuBar;

    private PSA analyzer;

    @FXML
    private void onTextSave(ActionEvent event) {
        System.out.println("doing");
    }

    @FXML
    private void onSintaticPress(ActionEvent event) {
        System.out.println("sintatic");
        analyzer = new PSA(lexicData);
        System.out.println("sintatic2");
        analyzer.analyze();
        System.out.println("sintatic3");
    }

    @FXML
    private void onAboutPress(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(menuBar.getScene().getWindow());
        alert.setHeaderText("PLA - Pascal Lexic Analyser");
        alert.setContentText("Developer: Caio Marcelo Campoy Guedes.\n\nFor more information visit: https://github.com/caiomcg/PLA");
        alert.show();
    }
    @FXML
    private void onQuit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(menuBar.getScene().getWindow());
        alert.setHeaderText("You are quitting the application");
        alert.setContentText("Do you want to proceed?");


        Optional<ButtonType> type = alert.showAndWait();

        if (type.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
}