package com.magneto.mdetection.controllers;

import com.magneto.mdetection.entities.Human;
import com.magneto.mdetection.services.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/mutant")
public class HumanController {
    @Autowired
    private HumanService humanService;
    @PostMapping
    public ResponseEntity<Boolean> isMutant(@RequestBody Human human){
        return new ResponseEntity<Boolean>(humanService.isMutant(human), HttpStatus.OK);
    }
}
