package com.td.singlereport.constants;

import java.io.File;

/**
 * Project class grouping constants
 */
public class Constants {

    public interface SCOPE {

        public static final String AUTHORIZATION = "Authorization";

        public static final String APPLICATION = "application";
        public static final String GLOBAL_SESSION = "globalSession";
        public static final String PROTOTYPE = "prototype";
        public static final String REQUEST = "request";
        public static final String SESSION = "session";
        public static final String SINGLETON = "singleton";
    }

    public interface CONFIGURATION {
        public static final String SOURCE_INPUT_DIRECTORY = "${global.config.source.directory}";
        public static final String SOURCE_OUTPUT_DIRECTORY = "${global.config.destination.directory}";
        public static final String NUMBER_OF_THREADS = "${global.config.number.of.threads}";
    }

    public interface FILES {
        public final String TRADE = "${global.config.file.trade}";
        public final String REF_DATA = "${global.config.file.refData}";
        public final String VALUATION = "${global.config.file.valuation}";
        public final String OUTPUT_TRADE = "${global.config.file.outputFile}";
    }

    public interface EXECUTION_STATUS {
        public final static String SUCCESS = "SUCCESS";
        public final static String FAILURE = "FAILURE";
    }

    public interface FILE {
        public static final String NEW_LINE = System.getProperty("line.separator");
        public static final String FS_SEPARATOR = File.separator;
        public static final String COMMA_SEPARATOR = ",";
        public static final String UNDERSCORE = "_";
    }

    public interface JOIN_COLUMNS {
        public static final String INVENTORY = "Inventory";
        public static final String TRADE_ID = "tradeId";
    }

    public interface BREAK_STATUS {
        public static final String F0_T99 = "0-99";
        public static final String F100_T999 = "100-999";
        public static final String F1000_T9999 = "1000-9999";
        public static final String F10000_T99999 = "10000-99999";
        public static final String F100000_PLUS = "100000+";
    }

    public interface TERM {
        public static final String F0M_T1M = "0m-1m";
        public static final String F1M_T6M = "1m-6m";
        public static final String F6M_T1Y = "6m-1yr";
        public static final String F1Y_T10Y = "1yr-10yr";
        public static final String F10Y_T30Y = "10yr-30yr";
        public static final String F30Y_T50Y = "30yr-50yr";
        public static final String F50Y_PLUS = "50yr+";
    }
}
