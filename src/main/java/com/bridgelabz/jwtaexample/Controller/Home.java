package com.bridgelabz.jwtaexample.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @RequestMapping("/welcome")
    public String welcome() {
        String text ="This is Private page";
        text+="This page is only allowed to Authorized person";
        return text;
    }
    @RequestMapping("/getusers")
    public String getUser() {
        return "{\"name\":\"Arun\"}";
    }
}
