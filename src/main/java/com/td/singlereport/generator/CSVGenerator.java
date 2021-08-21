package com.td.singlereport.generator;

import com.td.singlereport.config.Config;
import com.td.singlereport.constants.Constants;
import com.td.singlereport.model.RefData;
import com.td.singlereport.model.Trade;
import com.td.singlereport.model.Valuation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Component
@Scope(value = Constants.SCOPE.SINGLETON)
public class CSVGenerator implements Callable<String>, Serializable {

    private static final long serialVersionUID = 3011901089959939524L;

    private Logger LOG = LoggerFactory.getLogger(CSVGenerator.class);

    private String correlationID;

    private List<Trade> tradeList;

    private List<RefData> refDataList;

    private List<Valuation> valuationList;

    private Map<String, Integer> tradeHeadersCsv;

    private Map<String, Integer> refDataHeadersCsv;

    private Map<String, Integer> valuationHeadersCsv;

    @Autowired
    private Config config;

    @Override
    public String call() throws Exception {
        LOG.trace("Entering call(). CorrelationID={}", getCorrelationID());
        return generateFile();
    }

    private String generateFile(){
        LOG.trace("Entering generateFile(). CorrelationID={}", getCorrelationID());
        try {

            String filePath = buidOutputFilePath();
            FileWriter csvWriter = new FileWriter(filePath.toString());
            LOG.debug("File {} was generated properly. To be fulfilled...", filePath.toString());
            String headers = buildHeaders();
            csvWriter.append(headers.toString());



            LOG.debug("Looping tradeList. CorrelationID={}", getCorrelationID());
            tradeList.stream().forEach(t -> {
                StringBuilder tuple = new StringBuilder();
                try{

                    tuple.append(Constants.FILE.NEW_LINE);
                    RefData refData = refDataList.stream().filter(r -> r.getInventory().equalsIgnoreCase(t.getInventory())).findFirst().orElse(null);

                    if(refData != null){
                        LOG.debug("refData='{}' found for inventory='{}'. CorrelationID={}", refData.toString(), refData.getInventory(), getCorrelationID());
                        tuple.append(refData.toString());
                    }else{
                        tuple.append(",,,,,,,,,");
                    }
                    tuple.append(Constants.FILE.COMMA_SEPARATOR);
                    tuple.append(t.toString());
                    tuple.append(Constants.FILE.COMMA_SEPARATOR);
                    Valuation valuation = valuationList.stream().filter(v -> v.getTradeId().equals(t.getTradeId())).findFirst().orElse(null);
                    if(valuation != null){
                        LOG.debug("valuation='{}' found for tradeId='{}'. CorrelationID={}", valuation.toString(), valuation.getTradeId(), getCorrelationID());
                        tuple.append(valuation.toString());


                        //calculate MS-PC
                        BigDecimal mspc = calculateMsPC(tuple, t, valuation);

                        //calculate BreakStatus
                        calculateBreakStatus(tuple, t, mspc);

                        //calculate Term
                        calculateTerm(tuple, t);

                    }else{
                        LOG.debug("Valuation not found for tradeId='{}'. MS-PC and BreakStatus won't be calculated. CorrelationID={}", t.getTradeId(), getCorrelationID());
                        tuple.append(",,,,");
                    }
                    LOG.debug("Writing following tuple={} in the file.", tuple);
                    csvWriter.append(tuple);
                }catch (Exception e){
                    String message = String.format("%s occurred while processing tradeId={}. CorrelationID={}. Details: %s", e.getClass().getSimpleName(), t.getTradeId(), getCorrelationID(), e.getLocalizedMessage());
                    LOG.error(message, e);
                }


            });

            csvWriter.flush();
            csvWriter.close();

            return Constants.EXECUTION_STATUS.SUCCESS;

        }catch (Exception e) {
            String message = String.format("%s occurred while trying to generate the output file. Details: %s", e.getClass().getSimpleName(), e.getLocalizedMessage());
            LOG.error(message, e);
            return Constants.EXECUTION_STATUS.FAILURE;
        }finally {
            LOG.info("{}", "Leaving generateFile()");
        }
    }

    private String buidOutputFilePath() {
        LOG.trace("Entering buildFilePath(). CorrelationID={}", getCorrelationID());
        StringBuilder filePath = new StringBuilder();
        filePath.append(config.getSourceOutputDirectory());
        filePath.append(Constants.FILE.FS_SEPARATOR);
        filePath.append(new Timestamp(System.currentTimeMillis()).getTime());
        filePath.append(Constants.FILE.UNDERSCORE);
        filePath.append(config.getOutputFile());
        LOG.debug("File={} will be generated", filePath.toString());
        LOG.trace("Leaving buildFilePath()={}. CorrelationID={}", filePath.toString(), getCorrelationID());
        return filePath.toString();
    }

    private String buildHeaders() {
        LOG.trace("Entering buildHeaders(). CorrelationID={}", getCorrelationID());
        StringBuilder headers = new StringBuilder();
        //ignoring column inventory as it is already part of the trade.csv
        headers.append(getRefDataHeadersCsv().keySet().stream().filter(s -> !s.equalsIgnoreCase(Constants.JOIN_COLUMNS.INVENTORY)).map(String::valueOf).collect(Collectors.joining(Constants.FILE.COMMA_SEPARATOR)));
        headers.append(Constants.FILE.COMMA_SEPARATOR);
        //adding all the columns from trade.csv
        headers.append(getTradeHeadersCsv().keySet().stream().map(String::valueOf).collect(Collectors.joining(Constants.FILE.COMMA_SEPARATOR)));
        headers.append(Constants.FILE.COMMA_SEPARATOR);
        //ignoring column tradeId as it is already part of the trade.csv
        headers.append(getValuationHeadersCsv().keySet().stream().filter(s -> !s.equalsIgnoreCase(Constants.JOIN_COLUMNS.TRADE_ID)).map(String::valueOf).collect(Collectors.joining(Constants.FILE.COMMA_SEPARATOR)));

        LOG.debug("Adding three new columns ({}, {}, {}) to the trade output file. CorrelationID={}", "MS-PC", "BreakStatus", "Term", getCorrelationID());
        headers.append(Constants.FILE.COMMA_SEPARATOR);
        headers.append("MS-PC");
        headers.append(Constants.FILE.COMMA_SEPARATOR);
        headers.append("BreakStatus");
        headers.append(Constants.FILE.COMMA_SEPARATOR);
        headers.append("Term");
        LOG.info("Following headers({}) created. CorrelationID={}",headers.toString(), getCorrelationID());
        LOG.trace("Leaving buildHeaders()={}. CorrelationID={}", headers.toString(), getCorrelationID());
        return headers.toString();
    }

    private BigDecimal calculateMsPC(StringBuilder tuple, Trade t, Valuation valuation) {
        LOG.trace("Entering calculateMsPC(tuple={}, trade={}, valuation={}. CorrelationID={}", tuple, t.getTradeId(), valuation, getCorrelationID());

        BigDecimal mspc = null;
        if(valuation.getUqlOcMmbMs() != null && valuation.getUqlOcMmbMsPc() != null){
            mspc = (valuation.getUqlOcMmbMs().subtract(valuation.getUqlOcMmbMsPc())).abs();
            LOG.debug("MS-PC={} calculated for tradeId='{}'. CorrelationID={}", mspc, t.getTradeId(), getCorrelationID());

        }
        tuple.append(Constants.FILE.COMMA_SEPARATOR);
        tuple.append(mspc != null ? mspc : "");
        LOG.trace("Leaving calculateMsPC(tuple, trade, valuation)={}. CorrelationID={}", mspc, getCorrelationID());
        return mspc;
    }

    private void calculateBreakStatus(StringBuilder tuple, Trade trade, BigDecimal mspc) {
        LOG.trace("Entering calculateBreakStatus=(tuple={}, trade={}, mspc={}). CorrelationID={}", tuple, trade.getTradeId(), mspc, getCorrelationID());
        String breakStatus = "";
        if(mspc != null){
            if(mspc.compareTo(BigDecimal.ZERO) >= 0 && mspc.compareTo(BigDecimal.valueOf(99)) <= 0){
                breakStatus = Constants.BREAK_STATUS.F0_T99;
            }else if(mspc.compareTo(BigDecimal.valueOf(100)) >= 0 && mspc.compareTo(BigDecimal.valueOf(999)) <= 0){
                breakStatus = Constants.BREAK_STATUS.F100_T999;
            }else if (mspc.compareTo(BigDecimal.valueOf(1000)) >= 0 && mspc.compareTo(BigDecimal.valueOf(9999)) <= 0){
                breakStatus = Constants.BREAK_STATUS.F1000_T9999;
            }else if(mspc.compareTo(BigDecimal.valueOf(10000)) >= 0 && mspc.compareTo(BigDecimal.valueOf(99999)) <= 0){
                breakStatus = Constants.BREAK_STATUS.F10000_T99999;
            }else if(mspc.compareTo(BigDecimal.valueOf(100000)) >= 0){
                breakStatus = Constants.BREAK_STATUS.F100000_PLUS;
            }
            LOG.debug("BreakStatus='{}' calculated for tradeId='{}'. CorrelationID={}", breakStatus, trade.getTradeId(), getCorrelationID());
        }
        tuple.append(Constants.FILE.COMMA_SEPARATOR);
        tuple.append(breakStatus);
        LOG.trace("Leaving calculateBreakStatus=(tuple, trade, mspc)={}. CorrelationID={}", breakStatus, getCorrelationID());
    }

    private void calculateTerm(StringBuilder tuple, Trade trade) {
        LOG.trace("Entering calculateTerm(tuple={}, trade={}. CorrelationID={}", tuple, trade.getTradeId(), getCorrelationID());

        String term = "";
        if(trade.getMaturityDate() != null){
            LocalDate today = LocalDate.now();
            LocalDate maturityDate = trade.getMaturityDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            //the problem doesn't state the criteria to define whether the maturyDate is matured, so I assumed it is <= today.
            if(!today.isAfter(maturityDate)){
                Period period = Period.between(today, maturityDate);
                if(period.getYears() >= 50){
                    term = Constants.TERM.F50Y_PLUS;
                }else if(period.getYears() <= 50 && period.getYears() >= 30){
                    term = Constants.TERM.F30Y_T50Y;
                }else if(period.getYears() <= 30 && period.getYears() >= 10){
                    term = Constants.TERM.F10Y_T30Y;
                }else if(period.getYears() <= 10 && period.getYears() >= 1){
                    term = Constants.TERM.F1Y_T10Y;
                }else if(period.getYears() <= 1 && period.getMonths() >= 6) {
                    term = Constants.TERM.F6M_T1Y;
                }else if(period.getMonths() >= 1 && period.getMonths() <= 6){
                    term = Constants.TERM.F1M_T6M;
                }else if(period.getMonths() == 0 && period.getDays() <= 30){
                    term = Constants.TERM.F0M_T1M;
                }
            }
        }
        tuple.append(Constants.FILE.COMMA_SEPARATOR);
        tuple.append(term);
        LOG.trace("Leaving calculateTerm(tuple, trade)={}. CorrelationID={}", term, getCorrelationID());
    }

    public String getCorrelationID() {
        return correlationID;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }

    public List<Trade> getTradeList() {
        return tradeList;
    }

    public void setTradeList(List<Trade> tradeList) {
        this.tradeList = tradeList;
    }

    public List<RefData> getRefDataList() {
        return refDataList;
    }

    public void setRefDataList(List<RefData> refDataList) {
        this.refDataList = refDataList;
    }

    public List<Valuation> getValuationList() {
        return valuationList;
    }

    public void setValuationList(List<Valuation> valuationList) {
        this.valuationList = valuationList;
    }

    public Map<String, Integer> getTradeHeadersCsv() {
        return tradeHeadersCsv;
    }

    public void setTradeHeadersCsv(Map<String, Integer> tradeHeadersCsv) {
        this.tradeHeadersCsv = tradeHeadersCsv;
    }

    public Map<String, Integer> getRefDataHeadersCsv() {
        return refDataHeadersCsv;
    }

    public void setRefDataHeadersCsv(Map<String, Integer> refDataHeadersCsv) {
        this.refDataHeadersCsv = refDataHeadersCsv;
    }

    public Map<String, Integer> getValuationHeadersCsv() {
        return valuationHeadersCsv;
    }

    public void setValuationHeadersCsv(Map<String, Integer> valuationHeadersCsv) {
        this.valuationHeadersCsv = valuationHeadersCsv;
    }
}