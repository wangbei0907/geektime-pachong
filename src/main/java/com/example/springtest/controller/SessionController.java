package com.example.springtest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
//@EnableRedisHttpSession
public class SessionController {
    @RequestMapping("hello")
    public String printSession(HttpSession session,String name) {
       String sessionvalue=(String) session.getAttribute("name");
        if(sessionvalue==null)
        {
            session.setAttribute("name",name);
            sessionvalue=name;
        }
        return sessionvalue;
    }
}

