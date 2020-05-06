package com.example.springtest.model.Request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ToString
@Data
public class RequestOrder {
    @NotEmpty
    private String customer;
    @NotEmpty
    private List<String> items;
}
