package controller;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Polynomial;
import util.db.PolynomialDAO;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class ChoosePolynomialController {

    @FXML public Pane rootPane;
    @FXML public Label textAbove;
    @FXML public ListView<Polynomial> listOfPolynomials;
    @FXML public Label info;
    @FXML public Label pathDisplay;

    private PolynomialDAO dao = new PolynomialDAO();
    private DirectoryChooser chooser = new DirectoryChooser();
    private File selectedDirectory;


    public void initData(int n) {
        textAbove.setText("Polynomials for n=" + n);
        ObservableList<Polynomial> items = listOfPolynomials.getItems();
        try {
            items.addAll(dao.getPolynomialsByN(n));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setUpFolderChooser();

    }

    private void setUpFolderChooser() {
        FileSystemView fileSys = FileSystemView.getFileSystemView();
        pathDisplay.setText(String.valueOf(fileSys.getHomeDirectory()));
        selectedDirectory = new File(String.valueOf(fileSys.getHomeDirectory()));
        chooser.setTitle("Choose path for reports");
        chooser.setInitialDirectory(selectedDirectory);
    }

    @FXML
    public void continueButtonPressed(ActionEvent actionEvent) throws IOException {
        if (listOfPolynomials.getSelectionModel().getSelectedItem() == null)
            info.setText("Please select polynomial");
        else if (selectedDirectory == null)
            info.setText("Please select folder");
        else {
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/shiftRegister.fxml"));
            Pane pane = loader.load();

            ShiftRegisterController shiftRegisterController =
                    loader.getController();

            shiftRegisterController.initData(listOfPolynomials.getSelectionModel().getSelectedItem(), selectedDirectory);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();

            info.setText("");
        }
    }

    @FXML
    public void backButtonPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/start.fxml"));
        Pane pane = loader.load();
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    public void browseButtonPressed(ActionEvent actionEvent) {
        selectedDirectory = chooser.showDialog(rootPane.getScene().getWindow());
        pathDisplay.setText(String.valueOf(selectedDirectory));
    }
}
