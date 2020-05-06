package com.example.springtest.controller;

import com.example.springtest.model.Coffee;
import com.example.springtest.model.CoffeeOrder;
import com.example.springtest.model.Request.RequestOrder;
import com.example.springtest.service.CoffeeOrderService;
import com.example.springtest.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/order")
public class CoffeeOrderController {

    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeOrderService orderService;

//    @GetMapping("/{id}")
//    @ResponseBody
//    public CoffeeOrder getOrder(@PathVariable("id") Long id) {
//        return orderService.get(id);
//    }
    @ModelAttribute
    public List<Coffee> coffeeList() {
        return coffeeService.getAllCoffee();
    }

    @GetMapping("getall")
    @ResponseBody
    public List<Coffee> getAllCoffees() {
        return coffeeService.getAllCoffee();
    }
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody RequestOrder newOrder) {
        log.info("Receive new Order {}", newOrder);
        Coffee[] coffeeList = coffeeService.getCoffeeByName(newOrder.getItems())
                .toArray(new Coffee[] {});
        return orderService.createOrder(newOrder.getCustomer(), coffeeList);
    }
    @GetMapping("/")
    public ModelAndView showCreateForm() {
        return new ModelAndView("createorder");
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String creatOrder(@Valid RequestOrder order, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            log.warn("Binding Result: {}", result);
            map.addAttribute("message",result.toString());
            return "createorder";
        }
        Coffee[] coffeeList = coffeeService.getCoffeeByName(order.getItems()).toArray(new Coffee[]{});
        CoffeeOrder orders = orderService.createOrder(order.getCustomer(), coffeeList);
        return "redirect:/order/" + orders.getId();
    }
}
