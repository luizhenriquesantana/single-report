package com.td.singlereport.config;

import com.td.singlereport.dispatcher.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class Environment {

    private static final Environment instance = new Environment();

    private ConfigurableApplicationContext applicationContext;

    private SpringApplication application;

    @Autowired
    private Dispatcher dispatcher;

    private Environment(){

    }

    public static Environment context(){
        return instance;
    }

    public synchronized ConfigurableApplicationContext getApplicationContext(){
        return this.applicationContext;
    }

    public synchronized void setApplicationContext(ConfigurableApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    public synchronized SpringApplication getApplication(){
        return this.application;
    }

    public synchronized void setApplication(SpringApplication application){
        this.application = application;
    }

    public synchronized Dispatcher getDispatcher(){
        return this.dispatcher;
    }
}
