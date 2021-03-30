package com.magneto.mdetection.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class MatrixUtilsTests {

    @Autowired
    MatrixUtils matrixUtils;

    @Test
    public void toMatrixTest(){
        List<String> listDnaMutant = new LinkedList<>();
        listDnaMutant.add("AAAAAA");
        listDnaMutant.add("TTTTTT");
        listDnaMutant.add("TTATGT");
        listDnaMutant.add("AGAAGG");
        listDnaMutant.add("CCCCTA");
        listDnaMutant.add("TCACTG");
        char[][] expectedMatrix = new char[listDnaMutant.size()][listDnaMutant.size()];
        for (int i = 0; i < listDnaMutant.size(); i++) {
            for (int j = 0; j < listDnaMutant.size(); j++) {
                expectedMatrix[i][j] = listDnaMutant.get(i).charAt(j);
            }
        }
        char[][] matrix = matrixUtils.toMatrix(listDnaMutant);
        Assertions.assertArrayEquals(expectedMatrix, matrix);
    }

    @Test
    public void generateDummyMatrixTest(){
        char[][] expectedMatrix = new char[][]{ {'z','x','z','x'},
                                                {'x','z','x','z'},
                                                {'z','x','z','x'},
                                                {'x','z','x','z'}};
        char[][] dummyMatrix = matrixUtils.generateDummyMatrix(4);
        Assertions.assertArrayEquals(expectedMatrix,dummyMatrix);
    }

    @Test
    public void toTransposeTest(){
        char[][] expectedMatrix = new char[][]{ {'z','z','z','z'},
                                                {'x','x','x','x'},
                                                {'z','z','z','z'},
                                                {'x','x','x','x'}};
        char[][] matrix = new char[][]{ {'z','x','z','x'},
                                        {'z','x','z','x'},
                                        {'z','x','z','x'},
                                        {'z','x','z','x'}};
        Assertions.assertArrayEquals(expectedMatrix, matrixUtils.toTranspose(matrix));
    }

    @Test
    public void isSquareMatrixTest(){
        List<String> listDnaMutant = new LinkedList<>();
        listDnaMutant.add("AAAAAA");
        listDnaMutant.add("TTTTTT");
        listDnaMutant.add("TTATGT");
        listDnaMutant.add("AGAAGG");
        listDnaMutant.add("CCCCTA");
        listDnaMutant.add("TCACTG");

        Assertions.assertTrue(matrixUtils.isSquareMatrix(listDnaMutant));
    }

    @Test
    public void isNotSquareMatrixTest(){
        List<String> listDnaMutant = new LinkedList<>();
        listDnaMutant.add("AAAATTTTTTAA");
        listDnaMutant.add("TTTTTT");
        listDnaMutant.add("TTATGT");

        Assertions.assertFalse(matrixUtils.isSquareMatrix(listDnaMutant));
    }

    @Test
    public void itDoesNotHaveMutantSequenceTest(){
        char[][] matrix = new char[][]{ {'z','x','z','x'},
                                        {'z','x','z','x'},
                                        {'z','x','z','x'},
                                        {'z','x','z','x'}};
        Assertions.assertEquals(0, matrixUtils.detectMutantSequence(matrix, matrix.length));
    }

    @Test
    public void itHasMutantSequenceTest(){
        char[][] matrix = new char[][]{ {'z','z','z','z'},
                                        {'x','x','x','x'},
                                        {'z','x','z','x'},
                                        {'x','z','x','z'}};
        Assertions.assertEquals(2, matrixUtils.detectMutantSequence(matrix, matrix.length));
    }

    @Test
    public void identifyNegativeDiagonalTest(){
        char[][] matrix = new char[][]{ {'A','T','G','C','G','A'},
                                        {'C','A','G','T','G','C'},
                                        {'T','T','A','T','G','T'},
                                        {'A','G','A','A','G','G'},
                                        {'C','C','C','C','T','A'},
                                        {'T','C','A','C','T','G'}};
        char[][] expectedMatrix = new char[][]{ {'A','T','G','C','G','A'},
                                                {'A','G','T','G','C','z'},
                                                {'A','T','G','T','z','x'},
                                                {'A','G','G','z','x','z'},
                                                {'T','A','z','x','z','x'},
                                                {'G','z','x','z','x','z'}};
        Assertions.assertArrayEquals(expectedMatrix, matrixUtils.identifyNegativeDiagonalMatrix(matrix,0));
    }

    @Test
    public void identifyPositiveDiagonalTest(){
        char[][] matrix = new char[][]{ {'A','T','G','C','G','A'},
                                        {'C','C','G','T','A','C'},
                                        {'T','T','A','A','G','T'},
                                        {'A','G','A','G','G','G'},
                                        {'C','C','C','C','T','A'},
                                        {'T','C','A','C','T','G'}};
        char[][] expectedMatrix = new char[][]{ {'A','x','z','x','z','x'},
                                                {'A','C','x','z','x','z'},
                                                {'A','G','T','x','z','x'},
                                                {'A','G','G','G','x','z'},
                                                {'C','C','C','T','A','x'},
                                                {'T','C','A','C','T','G'}};
        Assertions.assertArrayEquals(expectedMatrix, matrixUtils.identifyPositiveDiagonalMatrix(matrix, matrix.length-1));

    }
}
