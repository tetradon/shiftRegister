package util.fxml;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MatrixDisplay {
    public static void displayMatrixToGridPane(GridPane pane, int[][] F) {
        for (int i = 0; i < F.length; i++) {
            for (int j = 0; j < F[0].length; j++) {
                Label field = getNewCellAndSet(F[i][j]);
                GridPane.setRowIndex(field, i);
                GridPane.setColumnIndex(field, j);
                pane.getChildren().add(field);
            }
        }
    }


    public static void displayVectorToGridPane(GridPane pane, int[] Y) {
        pane.getChildren().clear();
        for (int i = 0; i < Y.length; i++) {
            Label field = getNewCellAndSet(Y[i]);
            pane.add(field, i, 0);
        }
    }

    public static void displayEditableVectorToGridPane(GridPane pane, int[] Y) {
        pane.getChildren().clear();
        for (int i = 0; i < Y.length; i++) {
            TextField field = getNewEditableCellAndSet(Y[i]);
            pane.add(field, i, 1);
        }

    }

    private static Label getNewCellAndSet(int i) {
        Label field = new Label();
        field.setPrefHeight(25);
        field.setPrefWidth(25);
        field.setAlignment(Pos.CENTER);
        field.setMouseTransparent(true);
        field.setFocusTraversable(false);
        field.setText(" " + i + " ");
        return field;
    }

    private static TextField getNewEditableCellAndSet(int i) {
        TextField field = new TextField();
        field.setPrefHeight(25);
        field.setPrefWidth(25);
        field.setAlignment(Pos.CENTER);
        field.setText(String.valueOf(i));
        field.setEditable(true);
        field.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 1) {
                return null;
            } else {

                return change;
            }
        }));
        return field;
    }

    public static int getNumberOfColumnsInGridPane(GridPane pane) {
        int cols = 0;
        try {
            Method method = pane.getClass().getDeclaredMethod("getNumberOfColumns");
            method.setAccessible(true);
            cols = (int) method.invoke(pane);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return cols;
    }

    public static boolean isVectorInGridPaneValid(GridPane pane) {
        for (int i = 0; i < getNumberOfColumnsInGridPane(pane); i++) {
            TextField textField = (TextField) pane.getChildren().get(i);
            if (textField.getText().equals(""))
                return false;
        }
        return true;
    }

}
