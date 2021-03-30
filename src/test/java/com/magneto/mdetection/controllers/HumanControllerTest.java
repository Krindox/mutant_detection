package com.magneto.mdetection.controllers;

import com.magneto.mdetection.entities.Human;
import com.magneto.mdetection.services.HumanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class HumanControllerTest {

    @MockBean
    HumanService humanService;
    @Autowired
    HumanController humanController;

    @Test
    public void isMutantTest(){
        ResponseEntity<Boolean> isMutantExpected = new ResponseEntity<Boolean>(true, HttpStatus.OK);

        Human human = new Human();
        List<String> listDnaMutant = new LinkedList<>();
        listDnaMutant.add("ATGCGA");
        listDnaMutant.add("CAGTGC");
        listDnaMutant.add("TTATGT");
        listDnaMutant.add("AGAAGG");
        listDnaMutant.add("CCCCTA");
        listDnaMutant.add("TCACTG");

        human.setDna(listDnaMutant);
        Mockito.when(humanService.isMutant(human)).thenReturn(true);

        ResponseEntity<Boolean> isMutant = humanController.isMutant(human);

        Assertions.assertEquals(isMutantExpected, isMutant);

    }
}
