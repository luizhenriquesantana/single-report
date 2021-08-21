package com.td.singlereport.dispatcher;

import com.td.singlereport.constants.Constants;
import com.td.singlereport.reader.InputFolderReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Component
@Scope( value = Constants.SCOPE.SINGLETON)
public class Dispatcher implements Serializable {

    private final Logger LOG = LoggerFactory.getLogger(Dispatcher.class);

    @Autowired
    private InputFolderReader inputFolderReader;

    private String correlationID = UUID.randomUUID().toString();

    public void run(){
        LOG.info("Entering on method run(correlationID={}) from the Single-Report (CSV merger) application.", getCorrelationID());
        inputFolderReader.setCorrelationID(getCorrelationID());
        inputFolderReader.run();
        LOG.info("Leaving method run(correlationID={}) from the Single-Report (CSV merger) application.", getCorrelationID());
    }

    public String getCorrelationID() {
        return correlationID;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }
}
