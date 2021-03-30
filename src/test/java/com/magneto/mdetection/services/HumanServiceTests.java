package com.magneto.mdetection.services;

import com.magneto.mdetection.entities.Human;
import com.magneto.mdetection.utils.MatrixUtils;
import com.magneto.mdetection.utils.Validations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class HumanServiceTests {

    @MockBean
    MatrixUtils matrixUtils;
    @MockBean
    Validations validations;
    @Autowired
    HumanService humanService;


    @Test
    public void isNotANxNDnaMatrix(){
        Human human = new Human();
        List<String> listDnaMutant = new LinkedList<>();
        listDnaMutant.add("ATGCGGGGA");
        listDnaMutant.add("CAGTGC");
        listDnaMutant.add("TTATGT");
        listDnaMutant.add("AGAAGG");
        human.setDna(listDnaMutant);
        Mockito.when(matrixUtils.isSquareMatrix(human.getDna())).thenReturn(false);
        Assertions.assertThrows(ResponseStatusException.class, () -> humanService.isMutant(human), "expected throw");

    }

    @Test
    public void dnaContainsNonValidNitrogenBases(){
        Human human = new Human();
        List<String> listDnaMutant = new LinkedList<>();
        listDnaMutant.add("ATQCGA");
        listDnaMutant.add("CAGTGC");
        listDnaMutant.add("PTAVGT");
        listDnaMutant.add("AGAAGJ");
        listDnaMutant.add("XCCYTA");
        listDnaMutant.add("TCAZTL");
        human.setDna(listDnaMutant);
        char[][] matrix = new char[listDnaMutant.size()][listDnaMutant.size()];
        for (int i = 0; i < listDnaMutant.size(); i++) {
            for (int j = 0; j < listDnaMutant.size(); j++) {
                matrix[i][j] = listDnaMutant.get(i).charAt(j);
            }
        }
        Mockito.when(matrixUtils.toMatrix(human.getDna())).thenReturn(matrix);
        Mockito.when(matrixUtils.isSquareMatrix(human.getDna())).thenReturn(true);
        Mockito.when(validations.containsValidNitrogenBases(matrix)).thenReturn(false);
        Assertions.assertThrows(ResponseStatusException.class, () -> humanService.isMutant(human), "throw expected");
    }

    @Test
    public void isMutant(){
        Human human = new Human();
        List<String> listDnaMutant = new LinkedList<>();
        listDnaMutant.add("AAAAAA");
        listDnaMutant.add("TTTTTT");
        listDnaMutant.add("TTATGT");
        listDnaMutant.add("AGAAGG");
        listDnaMutant.add("CCCCTA");
        listDnaMutant.add("TCACTG");
        human.setDna(listDnaMutant);
        char[][] matrix = new char[listDnaMutant.size()][listDnaMutant.size()];
        for (int i = 0; i < listDnaMutant.size(); i++) {
            for (int j = 0; j < listDnaMutant.size(); j++) {
                matrix[i][j] = listDnaMutant.get(i).charAt(j);
            }
        }
        Mockito.when(matrixUtils.toMatrix(human.getDna())).thenReturn(matrix);
        Mockito.when(matrixUtils.isSquareMatrix(human.getDna())).thenReturn(true);
        Mockito.when(validations.containsValidNitrogenBases(matrix)).thenReturn(true);
        Mockito.when(matrixUtils.detectMutantSequence(matrix, matrix.length)).thenReturn(1);
        Mockito.when(matrixUtils.identifyNegativeDiagonalMatrix(matrix,1)).thenReturn(matrix);
        Mockito.when(matrixUtils.identifyPositiveDiagonalMatrix(matrix,1)).thenReturn(matrix);
        Mockito.when(matrixUtils.toTranspose(matrix)).thenReturn(matrix);
        boolean isMutant = humanService.isMutant(human);
        Assertions.assertEquals(true, isMutant);
    }

    @Test
    public void isNotMutant(){
        Human human = new Human();
        List<String> listDnaMutant = new LinkedList<>();
        listDnaMutant.add("AGAGAG");
        listDnaMutant.add("CTCTCT");
        listDnaMutant.add("TTATGT");
        listDnaMutant.add("AGAAGG");
        listDnaMutant.add("CCCCTA");
        listDnaMutant.add("TCACTG");
        human.setDna(listDnaMutant);
        char[][] matrix = new char[listDnaMutant.size()][listDnaMutant.size()];
        for (int i = 0; i < listDnaMutant.size(); i++) {
            for (int j = 0; j < listDnaMutant.size(); j++) {
                matrix[i][j] = listDnaMutant.get(i).charAt(j);
            }
        }
        Mockito.when(matrixUtils.toMatrix(human.getDna())).thenReturn(matrix);
        Mockito.when(matrixUtils.isSquareMatrix(human.getDna())).thenReturn(true);
        Mockito.when(validations.containsValidNitrogenBases(matrix)).thenReturn(true);
        Mockito.when(matrixUtils.detectMutantSequence(matrix, matrix.length)).thenReturn(0);
        Mockito.when(matrixUtils.identifyNegativeDiagonalMatrix(matrix,1)).thenReturn(matrix);
        Mockito.when(matrixUtils.identifyPositiveDiagonalMatrix(matrix,1)).thenReturn(matrix);
        Mockito.when(matrixUtils.toTranspose(matrix)).thenReturn(matrix);
        Assertions.assertThrows(ResponseStatusException.class, () -> humanService.isMutant(human), "Is not mutant");
    }
}
