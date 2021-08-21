package com.td.singlereport.reader;

import com.td.singlereport.config.Config;
import com.td.singlereport.constants.Constants;
import com.td.singlereport.exception.SingleReportException;
import com.td.singlereport.generator.CSVGenerator;
import com.td.singlereport.loader.CSVLoader;
import com.td.singlereport.model.RefData;
import com.td.singlereport.model.Trade;
import com.td.singlereport.model.Valuation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Scope(value = Constants.SCOPE.SINGLETON)
public class InputFolderReader implements Runnable, Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(InputFolderReader.class);

    private String correlationID = UUID.randomUUID().toString();

    @Autowired
    private Config config;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CSVLoader csvLoader;

    private List<Callable<String>> callableList;

    private ExecutorService threadPool;

    public String getCorrelationID() {
        return correlationID;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }

    @Override
    public void run() {
        LOG.trace("Entering method run(). CorrelationID={}.", getCorrelationID());
        try {
            processInputFolder();
        }catch (Exception e){
            String message = String.format("%s occurred while trying to consume the input folder. CorrelationID=%s. Detail: %s", e.getClass().getSimpleName(), getCorrelationID(), e.getMessage());
            LOG.error(message);
        }
    }

    private void processInputFolder() throws InterruptedException, IOException, SingleReportException {
        LOG.trace("Entering processSourceFolder() with. CorrelationID={}.", getCorrelationID());

        List<Trade> tradeList = new ArrayList<>();
        Map<String, Integer> tradeHeaderMap = csvLoader.loadTrade(getCorrelationID(), tradeList);
        LOG.info("tradeList={} loaded. CorrelationID={}.", tradeList, getCorrelationID());
        List<RefData> refDataList = new ArrayList<>();
        Map<String, Integer> refDataHeaderMap = csvLoader.loadRefData(getCorrelationID(), refDataList);
        LOG.info("refDataList={} loaded. CorrelationID={}.", refDataList, getCorrelationID());
        List<Valuation> valuationList = new ArrayList<>();
        Map<String, Integer> valuationHeaderMap = csvLoader.loadValuation(getCorrelationID(), valuationList);
        LOG.info("valuationList={} loaded. CorrelationID={}.", valuationList, getCorrelationID());


        if(!CollectionUtils.isEmpty(tradeList)){
            callableList = new ArrayList<>();
            CSVGenerator csvGenerator = applicationContext.getBean(CSVGenerator.class);
            csvGenerator.setCorrelationID(getCorrelationID());
            csvGenerator.setTradeList(tradeList);
            csvGenerator.setTradeHeadersCsv(tradeHeaderMap);

            if(!CollectionUtils.isEmpty(refDataList)){
                csvGenerator.setRefDataList(refDataList);
                csvGenerator.setRefDataHeadersCsv(refDataHeaderMap);
            }
            if(!CollectionUtils.isEmpty(valuationList)){
                csvGenerator.setValuationList(valuationList);
                csvGenerator.setValuationHeadersCsv(valuationHeaderMap);
            }

            callableList.add(csvGenerator);
        }
        if(!CollectionUtils.isEmpty(callableList)){
            threadPool = Executors.newFixedThreadPool(config.getNumberOfThreads());
            threadPool.invokeAll(callableList);
            LOG.info("Source folder started processing. CorrelationID={}", getCorrelationID());
            threadPool.shutdown();
            LOG.info("Source folder finished processing. CorrelationID={}", getCorrelationID());
        }


        LOG.trace("Leaving processSourceFolder(). CorrelationID={}.", getCorrelationID());
    }
}
