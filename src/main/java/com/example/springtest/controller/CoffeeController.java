package com.example.springtest.controller;

import com.example.springtest.controller.exception.FormValidationException;
import com.example.springtest.model.Coffee;
import com.example.springtest.model.Request.NewCoffeeRequest;
import com.example.springtest.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("coffee")
public class CoffeeController {
    @Autowired
    private CoffeeService coffeeService;

    @PostMapping(path = "/",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Coffee addCoffee(@Valid NewCoffeeRequest request, BindingResult result){
        if(result.hasErrors()){
           throw new FormValidationException(result);
        }
       return coffeeService.saveCoffee(request.getName(),request.getPrice() );
    }
}
