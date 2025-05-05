package com.musanlori.dev.crud.api.core.application.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/template")
public class Controller {

    /**
     * ejemplo de endpoint con metodo GET.
     * @return response Entity con mensaje
     * */
    @GetMapping("/example")
    public ResponseEntity<String> exampleController() {
        return new ResponseEntity<>("HOLA MUNDO", HttpStatus.OK);
    }

}
