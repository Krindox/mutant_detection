package com.magneto.mdetection.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MatrixUtils {
    @Autowired
    private Validations validations;

    /**
     * This method transforms a list in an array[][], to operate it as a matrix
     * */
    public char[][] toMatrix(List<String> sequences){
        char[][] matrix = new char[sequences.size()][sequences.size()];
        int raw = 0;

        for (String sequence:sequences) {
            for (int i = 0; i < sequence.length(); i++) {
                matrix[raw][i] = sequence.charAt(i);
            }
            raw += 1;
        }
        return matrix;
    }

    /**
     * This method generate a dummy matrix, which is necessary to identify diagonals
     * */
    public char[][] generateDummyMatrix(int dimension){
        char[][] zerosMatrix = new char[dimension][dimension];
        boolean  switcherRow = true;
        for (int i = 0; i < dimension; i++) {
            boolean  switcherCol = true;
            for (int j = 0; j < dimension; j++) {
                if(switcherRow){
                    if(switcherCol){
                        zerosMatrix[i][j] = 'z';
                    }else {
                        zerosMatrix[i][j] = 'x';
                    }
                }else {
                    if(switcherCol){
                        zerosMatrix[i][j] = 'x';
                    }else {
                        zerosMatrix[i][j] = 'z';
                    }
                }
                switcherCol = !switcherCol;
            }
            switcherRow = !switcherRow;
        }
        return zerosMatrix;
    }

    /**
     * This method generates the transpose from a given matrix.
     * It means to transform columns in rows,
     * which is necessary to use the detectMutantSequence method
     * */
    public char[][] toTranspose(char[][] matrix){
        char[][] transpose = new char[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }
        return transpose;
    }

    /**
     * This method transforms diagonals from left to right and from up to down in vertical columns,
     * to identify when a dna has diagonal mutant sequences
     * */
    public char[][] identifyNegativeDiagonalMatrix(char[][] matrix, int iterateFrom){

        char[][] newMatrix = generateDummyMatrix(matrix.length);
        boolean  firstCall = true;
        int      countRow = 0;

        for (int j = iterateFrom; j < matrix.length; j++) {
            if(!firstCall) countRow += 1;
            for (int k = 0; k < matrix[0].length; k++) {
                if(firstCall){
                    newMatrix[j][k] = matrix[j][k];
                }else {
                    if (k + countRow < matrix.length) newMatrix[j][k] = matrix[j][k + countRow];
                }
            }
            firstCall = false;
        }

        return newMatrix;
    }

    /**
     * This method transforms diagonals from left to right and from down to up in vertical columns,
     * to identify when a dna has diagonal mutant sequences
     * */
    public char[][] identifyPositiveDiagonalMatrix(char[][] matrix, int iterateFrom){

        char[][] newMatrix = generateDummyMatrix(matrix.length);
        boolean  firstCall = true;
        int      countRow = 0;

        for (int j = iterateFrom; j >= 0; j--) {
            if(!firstCall) countRow += 1;
            for (int k = matrix.length - 1; k >= 0; k--) {
                if(firstCall){
                    newMatrix[j][k] = matrix[j][k];
                }else {
                    if (k + countRow < matrix.length) newMatrix[j][k] = matrix[j][k + countRow];
                }
            }
            firstCall = false;
        }
        return newMatrix;
    }

    /**
     * This method validates if a given list represents a square matrix
     * */
    public boolean isSquareMatrix(List<String> sequences){
        int rows = sequences.size();
        for (String sequence:sequences) {
            if (sequence.length() != rows) return false;
        }
        return true;
    }

    /**
     * This method is the core of the Magneto's software,
     * it identifies in a horizontal way when a same Nitrogen base
     * is present (validations.NUMBER_SAME_BASE_TO_BE_MUTANT) times in a row.
     * which means it is a mutant sequence
     * */
    public int detectMutantSequence(char[][] matrix, int iterateUntil){
        char letter = '0';
        int  countMutantSequence = 0;

        for (int i = 0; i < iterateUntil; i++) {
            int  countSameLetter = 1;
            for (int j = 0; j < matrix[0].length; j++) {
                if (j == 0){
                    letter = matrix[i][j];
                }else{
                    if (letter == matrix[i][j]){
                        countSameLetter += 1;
                    }else{
                        letter = matrix[i][j];
                        countSameLetter = 1;
                    }
                }

                if(countSameLetter >= validations.NUMBER_SAME_BASE_TO_BE_MUTANT &&
                   countSameLetter % validations.NUMBER_SAME_BASE_TO_BE_MUTANT == 0){
                        countMutantSequence += 1;
                }
            }
        }
        return countMutantSequence;
    }
}
