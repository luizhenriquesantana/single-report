package com.td.singlereport.exception;

import java.io.File;

public class SingleReportException extends Exception{

    private static final long serialVersionUID = -5460476453870766472L;
    
    private File file;

    private Long tradeId;

    private String correlationID;

    public SingleReportException(String message) {
        super(message);
    }

    public SingleReportException(String message, File file, Long tradeId, String correlationID) {
        super(message);
        this.file = file;
        this.tradeId = tradeId;
        this.correlationID = correlationID;
    }

    public SingleReportException(String message, Throwable cause) {
        super(message, cause);
    }

    public SingleReportException(Throwable cause) {
        super(cause);
    }

    public File getFile() {
        return file;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public String getCorrelationID() {
        return correlationID;
    }


}
