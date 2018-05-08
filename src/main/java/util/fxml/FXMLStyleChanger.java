package util.fxml;

import javafx.scene.Node;
import javafx.scene.control.Label;

import static util.fxml.FXMLStatus.FAILED;
import static util.fxml.FXMLStatus.SUCCESS;

public class FXMLStyleChanger {
    public static void setBackgroundColor(Node node, FXMLStatus status) {
        switch (status) {
            case SUCCESS:
                node.setStyle("-fx-background-color: #7cfc00");
                break;
            case FAILED:
                node.setStyle("-fx-background-color: #ff6a60");
                break;
            default:
                break;
        }
    }

    public static void checkForEqualityAndSetAppropriateBackgroundColor(int a, int b, Label display) {
        if (a == b)
            FXMLStyleChanger.setBackgroundColor(display, SUCCESS);
        else
            FXMLStyleChanger.setBackgroundColor(display, FAILED);
    }
}
