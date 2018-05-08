package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import model.Polynomial;
import model.ShiftRegister;
import util.calculation.Calculation;
import util.report.FileReport;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static util.fxml.FXMLStyleChanger.checkForEqualityAndSetAppropriateBackgroundColor;
import static util.fxml.MatrixDisplay.*;


public class ShiftRegisterController {

    private boolean stopped = true;
    private boolean firstStart = true;
    private int experimentalPeriod = 0;
    private int experimentalHammingWeight = 0;
    private Polynomial polynomial;
    private int[] initialState;
    private ShiftRegister register;
    private FileReport report;
    private Timeline timer;

    @FXML public Label initialStateError;
    @FXML public Label polynomialInfoDisplay;
    @FXML public Label theoreticPeriodDisplay;
    @FXML public GridPane matrixDisplay;
    @FXML public GridPane initialStateDisplay;
    @FXML public GridPane currentStateDisplay;
    @FXML public Slider slider;
    @FXML public Label decimalNumberDisplay;
    @FXML public Label currentPeriodDisplay;
    @FXML public Label theoreticHammingWeightDisplay;
    @FXML public Label currentHammingWeightDisplay;
    @FXML public Button startButton;
    @FXML public Button stopButton;
    @FXML public Button nextStateButton;

    void initData(Polynomial polynomial, File selectedDirectory) throws IOException {
        this.polynomial = polynomial;
        this.report = new FileReport(selectedDirectory);
        String poly = "Polynomial = " + polynomial.toString();
        String period = "Theoretic period (T) = " + polynomial.getPeriod();
        String hamming = "Theoretic Hamming weight = " + polynomial.getHammingWeight();
        polynomialInfoDisplay.setText(poly);
        theoreticPeriodDisplay.setText(period);
        theoreticHammingWeightDisplay.setText(hamming);
        report.writeString(poly);
        report.writeString(period);
        report.writeString(hamming);

        int[][] F = polynomial.getCharacteristicMatrix();
        displayMatrixToGridPane(matrixDisplay, F);

        initialState = new int[F.length];
        initialState[initialState.length - 1] = 1;

        displayEditableVectorToGridPane(initialStateDisplay, initialState);
        displayVectorToGridPane(currentStateDisplay, initialState);

        registerInitialStateListener();
        registerTimeSliderListener();
    }

    private void registerInitialStateListener() {
        int cols = getNumberOfColumnsInGridPane(initialStateDisplay);
        for (int i = 0; i < cols; i++) {
            TextField textField = (TextField) initialStateDisplay.getChildren().get(i);
            int finalI = i;
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!isVectorInGridPaneValid(initialStateDisplay)) {
                    initialStateError.setVisible(true);
                    setAllButtonsDisabled(true);
                } else {
                    initialStateError.setVisible(false);
                    setAllButtonsDisabled(false);
                }
                if (!newValue.matches("[01]")) {
                    textField.setText("");
                } else {
                    initialState[finalI] = Integer.parseInt(textField.getText());
                    displayVectorToGridPane(currentStateDisplay, initialState);
                }
            });
        }
    }

    private void setAllButtonsDisabled(boolean b) {
        stopButton.setDisable(b);
        startButton.setDisable(b);
        nextStateButton.setDisable(b);
        slider.setDisable(b);
    }

    private void registerTimeSliderListener() {
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            slider.setValue(newValue.doubleValue());
            if (!stopped)
                resetTimer(timer, newValue.doubleValue());
        });
    }

    @FXML
    public void nextState() throws IOException {
        if(firstStart)
            doFirstStartSetUp();

        displayVectorToGridPane(currentStateDisplay, register.nextState());

        int decimal = Calculation.convertBinaryArrayToInteger(register.getCurrentState());
        decimalNumberDisplay.setText(String.valueOf(decimal));

        experimentalPeriod++;
        currentPeriodDisplay.setText(String.valueOf(experimentalPeriod));
        report.writeStep(experimentalPeriod, register.getCurrentState(), decimal);

        countHammingWeight();

        if (Arrays.equals(register.getCurrentState(), register.getInitialState()) && !firstStart) {
            stopTimer();
            checkForEqualityAndSetAppropriateBackgroundColor(
                    experimentalPeriod, register.getPolynomial().getPeriod(), currentPeriodDisplay);
            checkForEqualityAndSetAppropriateBackgroundColor(
                    experimentalHammingWeight, polynomial.getHammingWeight(), currentHammingWeightDisplay);
            displayReport();
            setAllButtonsDisabled(true);
        }
        firstStart = false;
    }

    private void doFirstStartSetUp() throws IOException {
            setInitialStateEditable(false);
            report.writeString("Initial state =" + Arrays.toString(initialState) + "\r\n");
            register = new ShiftRegister(polynomial, initialState);
    }

    private void countHammingWeight() {
        if (register.getCurrentState()[0] == 1) {
            experimentalHammingWeight++;
            currentHammingWeightDisplay.setText(String.valueOf(experimentalHammingWeight));
        }
    }

    private void displayReport() throws IOException {
        if (Desktop.isDesktopSupported())
            Desktop.getDesktop().edit(report.getFile());
    }

    private void setInitialStateEditable(Boolean b) {
        int cols = getNumberOfColumnsInGridPane(initialStateDisplay);

        for (int i = 0; i < cols; i++) {
            TextField textField = (TextField) initialStateDisplay.getChildren().get(i);
            initialState[i] = Integer.parseInt(textField.getText());
            textField.setEditable(b);
            textField.setMouseTransparent(!b);
            textField.setFocusTraversable(b);
        }
    }

    private void resetTimer(Timeline timer, final double timerInterval) {
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(timerInterval),
                event -> {
                    try {
                        nextState();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        timer.stop();
        timer.getKeyFrames().setAll(keyFrame);
        if (!stopped)
            timer.play();
    }

    @FXML
    public void startAutoButtonPressed() {
        stopped = false;
        timer = new Timeline(
                new KeyFrame(
                        Duration.millis(slider.getValue()),
                        event -> {
                            try {
                                nextState();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                )
        );
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    @FXML
    public void stopAutoButtonPressed() {
        stopTimer();

    }

    private void stopTimer() {
        if (!stopped)
            timer.stop();
        stopped = true;
    }
}
