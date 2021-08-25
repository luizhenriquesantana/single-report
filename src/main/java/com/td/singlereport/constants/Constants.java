package com.td.singlereport.constants;

import java.io.File;

/**
 * Project class grouping constants
 */
public class Constants {

    public interface SCOPE {

        String AUTHORIZATION = "Authorization";

        String APPLICATION = "application";
        String GLOBAL_SESSION = "globalSession";
        String PROTOTYPE = "prototype";
        String REQUEST = "request";
        String SESSION = "session";
        String SINGLETON = "singleton";
    }

    public interface CONFIGURATION {
        String SOURCE_INPUT_DIRECTORY = "${global.config.source.directory}";
        String SOURCE_OUTPUT_DIRECTORY = "${global.config.destination.directory}";
        String NUMBER_OF_THREADS = "${global.config.number.of.threads}";
    }

    public interface FILES {
        String TRADE = "${global.config.file.trade}";
        String REF_DATA = "${global.config.file.refData}";
        String VALUATION = "${global.config.file.valuation}";
        String OUTPUT_TRADE = "${global.config.file.outputFile}";
    }

    public interface EXECUTION_STATUS {
        String SUCCESS = "SUCCESS";
        String FAILURE = "FAILURE";
    }

    public interface FILE {
        String NEW_LINE = System.getProperty("line.separator");
        String FS_SEPARATOR = File.separator;
        String COMMA_SEPARATOR = ",";
        String UNDERSCORE = "_";
    }

    public interface JOIN_COLUMNS {
        String INVENTORY = "Inventory";
        String TRADE_ID = "tradeId";
    }

    public interface BREAK_STATUS {
        String F0_T99 = "0-99";
        String F100_T999 = "100-999";
        String F1000_T9999 = "1000-9999";
        String F10000_T99999 = "10000-99999";
        String F100000_PLUS = "100000+";
    }

    public interface TERM {
        String F0M_T1M = "0m-1m";
        String F1M_T6M = "1m-6m";
        String F6M_T1Y = "6m-1yr";
        String F1Y_T10Y = "1yr-10yr";
        String F10Y_T30Y = "10yr-30yr";
        String F30Y_T50Y = "30yr-50yr";
        String F50Y_PLUS = "50yr+";
    }
}
