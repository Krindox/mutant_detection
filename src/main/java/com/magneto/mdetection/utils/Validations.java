package com.magneto.mdetection.utils;

import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class Validations {
    public final int NUMBER_SAME_BASE_TO_BE_MUTANT = 4;
    public final int NUMBER_SEQ_TO_BE_MUTANT = 2;

    /**
     * This method verifies if a given matrix contains valid Nitrogen Bases only
     * */
    public boolean containsValidNitrogenBases(char[][] matrix){

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                boolean isValid = false;
                for(NitrogenBases nitrogenBase: NitrogenBases.values()){
                    if (nitrogenBase.toString().equals(String.valueOf(matrix[i][j]))){
                        isValid = true;
                    }
                }
                if(!isValid){
                    return false;
                }
            }
        }
        return true;
    }
}
