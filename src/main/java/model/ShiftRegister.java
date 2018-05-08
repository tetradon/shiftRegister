package model;

import static util.calculation.Calculation.multiplyMatrixByVector;

public class ShiftRegister {
    private Polynomial polynomial;
    private int[] initialState;
    private int[] currentState;

    public ShiftRegister(Polynomial polynomial, int[] initialState) {
        this.polynomial = polynomial;
        this.initialState = initialState;
        currentState = initialState;
    }

    public int[] nextState() {
        currentState = multiplyMatrixByVector(polynomial.getCharacteristicMatrix(), currentState);
        return currentState;
    }

    public int[] getCurrentState() {
        return currentState;
    }

    public Polynomial getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(Polynomial polynomial) {
        this.polynomial = polynomial;
    }

    public int[] getInitialState() {
        return initialState;
    }

    public void setInitialState(int[] initialState) {
        this.initialState = initialState;
    }
}
