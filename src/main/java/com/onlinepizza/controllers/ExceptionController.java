package com.onlinepizza.controllers;

import org.springframework.http.ResponseEntity;

//todo this is not currently needed, @ResponseStatus annotation on exception class is handling this

//@ControllerAdvice
public class ExceptionController {
    //@ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(){
        return ResponseEntity.notFound().build();
    }
}
