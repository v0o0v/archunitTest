package com.example.archunittest.controller;

import com.example.archunittest.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

public class HelloController {

    @Autowired
    HelloService helloService;

    public void controlHello(){
        System.out.println("Received Hello.");
        this.helloService.hello();
    }
}
