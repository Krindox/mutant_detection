package com.magneto.mdetection;

import com.magneto.mdetection.entities.Human;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;
import org.springframework.http.ResponseEntity;

public class MutantDetectionHandler extends SpringBootRequestHandler<Human, ResponseEntity<Boolean>> {

}
