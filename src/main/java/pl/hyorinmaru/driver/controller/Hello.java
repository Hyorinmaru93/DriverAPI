package pl.hyorinmaru.driver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class Hello {

    @RequestMapping("/hello")
    @ResponseStatus(OK)
    public String Hello(){
        return "Hello";
    }


    @GetMapping("/hej")
    @ResponseStatus(OK)
    public String hej(){
        return "Hej adminie :)";
    }



}
