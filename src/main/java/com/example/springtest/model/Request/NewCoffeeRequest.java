package com.example.springtest.model.Request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class NewCoffeeRequest {
    @NotEmpty
    private String name;
    @NotNull
    private double price;
}
