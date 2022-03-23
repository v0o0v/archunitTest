package com.example.archunittest.service;

import org.springframework.beans.factory.annotation.Autowired;

public class HelloService {

    @Autowired
    HelloPersistencePort helloPersistencePort;

    public void hello(){
        System.out.println("Service Hello~");
        this.helloPersistencePort.saveHello();
    }
}
