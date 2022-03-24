package com.example.archunittest.service;

import org.springframework.beans.factory.annotation.Autowired;

public class HelloService {

    @Autowired
    HelloPersistencePort helloPersistencePort;

    public void hello(){
        this.helloPersistencePort.saveHello();
    }
}
