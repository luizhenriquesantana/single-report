package com.td.singlereport.config;

import com.td.singlereport.constants.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
@ConfigurationProperties
public class Config implements Serializable {

    private static final long serialVersionUID = 2236220959749502227L;

    @Value(Constants.CONFIGURATION.SOURCE_INPUT_DIRECTORY)
    private String sourceInputDirectory;

    @Value(Constants.CONFIGURATION.SOURCE_OUTPUT_DIRECTORY)
    private String sourceOutputDirectory;

    @Value(Constants.CONFIGURATION.NUMBER_OF_THREADS)
    private Integer numberOfThreads;

    @Value(Constants.FILES.TRADE)
    private String tradeFile;

    @Value(Constants.FILES.REF_DATA)
    private String refDataFile;

    @Value(Constants.FILES.VALUATION)
    private String valuationFile;

    @Value(Constants.FILES.OUTPUT_TRADE)
    private String outputFile;

    public String getSourceInputDirectory() {
        return sourceInputDirectory;
    }

    public String getSourceOutputDirectory() {
        return sourceOutputDirectory;
    }

    public Integer getNumberOfThreads() {
        return numberOfThreads;
    }

    public String getTradeFile() {
        return tradeFile;
    }

    public String getRefDataFile() {
        return refDataFile;
    }

    public String getValuationFile() {
        return valuationFile;
    }

    public String getOutputFile() {
        return outputFile;
    }
}
