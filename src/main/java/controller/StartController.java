package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import util.db.PolynomialDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class StartController implements Initializable {

    private PolynomialDAO dao = new PolynomialDAO();
    @FXML public Pane rootPane;
    @FXML public ListView listOfN;
    @FXML private Text info;

    @FXML
    public void handleContinueButtonAction(ActionEvent actionEvent) throws IOException {
        if(listOfN.getSelectionModel().getSelectedItem() == null)
            info.setText("Please select n");
        else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/choosePolynomial.fxml"));
            Pane pane = loader.load();
            ChoosePolynomialController choosePolynomialController =
                    loader.getController();
            choosePolynomialController.initData((Integer) listOfN.getSelectionModel().getSelectedItem());
            rootPane.getChildren().setAll(pane);

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Integer> items = listOfN.getItems();
        try {
           items.addAll(dao.getAllN());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
