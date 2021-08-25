package com.td.singlereport.loader;

import com.td.singlereport.config.Config;
import com.td.singlereport.constants.Constants;
import com.td.singlereport.exception.SingleReportException;
import com.td.singlereport.model.RefData;
import com.td.singlereport.model.Trade;
import com.td.singlereport.model.Valuation;
import com.td.singlereport.utils.IOFileUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Component
@Scope(value = Constants.SCOPE.SINGLETON)
public class CSVLoader implements Serializable {
    private static final long serialVersionUID = -6031371821541534230L;

    private static final Logger LOG = LoggerFactory.getLogger(CSVLoader.class);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private Config config;

    public Map<String, Integer> loadTrade(String correlationID, List<Trade> tradeList) throws SingleReportException {
        LOG.trace("Entering loadTrade(correlationID={}, tradeList={})", correlationID, tradeList);
        File tradeFile = null;
        Long tradeId = null;

        Map<String, Integer> headerMap = null;
        try{
            LOG.info("Loading trade file from '{}'. CorrelationID={}.", config.getSourceInputDirectory(), correlationID);

            if (IOFileUtils.isDirectory(config.getSourceInputDirectory())) {
                LOG.info("Accessing the folder '{}' in order to get file '{}'. CorrelationID={}", config.getSourceInputDirectory(), config.getTradeFile(), correlationID);
                StringBuilder tradeFilePath = new StringBuilder();
                tradeFilePath.append(config.getSourceInputDirectory());
                tradeFilePath.append(Constants.FILE.FS_SEPARATOR);
                tradeFilePath.append(config.getTradeFile());
                tradeFile = new File(tradeFilePath.toString());

                Reader reader = new FileReader(tradeFilePath.toString());
                CSVParser csvParser  = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
                for (CSVRecord record : csvParser){
                    Trade trade = new Trade();
                    trade.setInventory(record.get("Inventory"));
                    trade.setBook(record.get("Book"));
                    trade.setSystem(record.get("System"));
                    trade.setLegalEntity(record.get("LegalEntity"));
                    trade.setTradeId(Long.valueOf(record.get("TradeId")));
                    trade.setVersion(Integer.valueOf(record.get("Version")));
                    trade.setTradeStatus(record.get("TradeStatus"));
                    trade.setProductType(record.get("ProductType"));
                    trade.setResettingLeg(record.get("Resetting Leg"));
                    trade.setProductSubType(record.get("ProductSubType"));
                    trade.setTdsProductType(record.get("TDSProductType"));
                    trade.setSecCodeSubType(record.get("SecCodeSubType"));
                    trade.setSwapType(record.get("SwapType"));
                    trade.setDescription(record.get("Description"));
                    trade.setTradeDate(getSimpleDateFormat().parse(record.get("TradeDate")));
                    trade.setStartDate(getSimpleDateFormat().parse(record.get("StartDate")));
                    trade.setMaturityDate(getSimpleDateFormat().parse(record.get("MaturityDate")));

                    tradeList.add(trade);
                }

                headerMap = csvParser.getHeaderMap();

            }


        }catch (Exception e){
            String message = String.format("%s occurred while trying to load tradeFile. Details: %s", e.getClass().getSimpleName(), e.getMessage());
            LOG.warn(message);
            throw new SingleReportException(message, tradeFile, tradeId, correlationID);
        }


        LOG.trace("Leaving loadTrade(correlationID,tradeList)->headerMap={},tradeList={}", headerMap,tradeList);
        return headerMap;
    }

    public Map<String, Integer> loadRefData(String correlationID, List<RefData> refDataList) throws SingleReportException {
        LOG.trace("Entering loadRefData(correlationID={})", correlationID);
        Long tradeId = null;
        File refDataFile = null;
        Map<String, Integer> headerMap = null;
        try{
            LOG.info("Loading trade file from '{}'. CorrelationID={}.", config.getSourceInputDirectory(), correlationID);

            if (IOFileUtils.isDirectory(config.getSourceInputDirectory())) {
                LOG.info("Accessing the folder '{}' in order to get file '{}'. CorrelationID={}", config.getSourceInputDirectory(), config.getTradeFile(), correlationID);
                StringBuilder tradeFilePath = new StringBuilder();
                tradeFilePath.append(config.getSourceInputDirectory());
                tradeFilePath.append(Constants.FILE.FS_SEPARATOR);
                tradeFilePath.append(config.getRefDataFile());
                refDataFile = new File(tradeFilePath.toString());

                Reader reader = new FileReader(tradeFilePath.toString());
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
                for (CSVRecord record : csvParser) {
                    RefData refData = new RefData();
                    refData.setTopOfHouse(record.get("topOfHouse"));
                    refData.setSegment(record.get("segment"));
                    refData.setViceChair(record.get("viceChair"));
                    refData.setGlobalBusiness(record.get("globalBusiness"));
                    refData.setPolicy(record.get("Policy"));
                    refData.setDesk(record.get("desk"));
                    refData.setPortfolio(record.get("portfolio"));
                    refData.setBU(Integer.valueOf(record.get("BU")));
                    refData.setCline(record.get("CLINE"));
                    refData.setInventory(record.get("Inventory"));

                    refDataList.add(refData);
                }

                headerMap = csvParser.getHeaderMap();
            }
        }catch (Exception e){
            String message = String.format("%s occurred while trying to load refDataFile. Details: %s", e.getClass().getSimpleName(), e.getMessage());
            LOG.warn(message);
            throw new SingleReportException(message, refDataFile, tradeId, correlationID);
        }
        LOG.trace("Leaving loadRefData(correlationID)={}", refDataList);
        return headerMap;
    }

    public Map<String, Integer> loadValuation(String correlationID,List<Valuation> valuationList) throws SingleReportException {
        LOG.trace("Entering loadValuation(correlationID={})", correlationID);
        Long tradeId = null;
        File valuationFile = null;
        Map<String, Integer> headerMap = null;
        try{
            LOG.info("Loading trade file from '{}'. CorrelationID={}.", config.getSourceInputDirectory(), correlationID);

            if (IOFileUtils.isDirectory(config.getSourceInputDirectory())) {
                LOG.info("Accessing the folder '{}' in order to get file '{}'. CorrelationID={}", config.getSourceInputDirectory(), config.getTradeFile(), correlationID);
                StringBuilder tradeFilePath = new StringBuilder();
                tradeFilePath.append(config.getSourceInputDirectory());
                tradeFilePath.append(Constants.FILE.FS_SEPARATOR);
                tradeFilePath.append(config.getValuationFile());
                valuationFile = new File(tradeFilePath.toString());

                Reader reader = new FileReader(tradeFilePath.toString());
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
                for (CSVRecord record : csvParser) {
                    Valuation valuation = new Valuation();
                    valuation.setTradeId(Long.valueOf(record.get("TradeId")));
                    valuation.setUqlOcMmbMs(new BigDecimal(record.get("UQL_OC_MMB_MS")));
                    valuation.setUqlOcMmbMsPc(new BigDecimal(record.get("UQL_OC_MMB_MS_PC")));

                    valuationList.add(valuation);
                }

                headerMap = csvParser.getHeaderMap();
            }

        }catch (Exception e){
            String message = String.format("%s occurred while trying to load refDataFile. Details: %s", e.getClass().getSimpleName(), e.getMessage());
            LOG.warn(message);
            throw new SingleReportException(message, valuationFile, tradeId, correlationID);
        }

        LOG.trace("Leaving loadValuation(correlationID)={}", valuationList);
        return headerMap;
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }
}
