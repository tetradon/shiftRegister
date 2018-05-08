package model;

import util.calculation.Calculation;

import java.util.Arrays;

public class Polynomial {
    private int n;
    private int j;
    private int oct;
    private char letter;

    @Override
    public String toString() {
        return j+" "+oct+letter;

    }

    public Polynomial(int n, int j, int oct, char letter) {
        this.n = n;
        this.j = j;
        this.oct = oct;
        this.letter = letter;
    }

    public int[] getBinaryRepresentation() {
        int decimal = Integer.parseInt(String.valueOf(oct), 8);
        String binaryString = Integer.toBinaryString(decimal);
        int [] binary = new int[binaryString.length()];
        for(int i = 0; i<binaryString.length(); i++)
            binary[i] = Integer.parseInt(String.valueOf(binaryString.charAt(i)));
        return binary;
    }

    public int[][] getCharacteristicMatrix(){
        int [] binaryPolynomial = getBinaryRepresentation();
        binaryPolynomial = Arrays.copyOfRange(binaryPolynomial,1,binaryPolynomial.length);
        int F[][] = new int[binaryPolynomial.length][binaryPolynomial.length];
        for (int i = 0; i < F.length; i++) {
            for (int j = 0; j < F[0].length; j++) {
                if(i==0)
                    F[i][j] = binaryPolynomial[j];
                else if(i-1 == j)
                    F[i][j] = 1;
            }
        }
        return F;
    }

    public int getHammingWeight() {
       return (int)Math.pow(2,n-1);
    }


    public int getPeriod() {
        return (int) ((Math.pow(2,n) - 1) / (Calculation.gcd((int) (Math.pow(2,n) - 1), j)));
    }


    public int getN() {
        return n;
    }

    public int getJ() {
        return j;
    }

    public int getOct() {
        return oct;
    }

    public char getLetter() {
        return letter;
    }
}
