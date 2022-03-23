package com.example.archunittest.persistence;

import com.example.archunittest.service.HelloPersistencePort;

public class HelloPersistenceAdaptor implements HelloPersistencePort {
    @Override
    public void saveHello() {
        System.out.println("Save Hello To DB.");
    }
}
