package com.td.singlereport.model;

import com.td.singlereport.constants.Constants;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Valuation implements Serializable {

    private Long tradeId;
    private BigDecimal uqlOcMmbMs;
    private BigDecimal uqlOcMmbMsPc;

    public Valuation() {
    }

    public Valuation(Long tradeId, BigDecimal uqlOcMmbMs, BigDecimal uqlOcMmbMsPc) {
        this.tradeId = tradeId;
        this.uqlOcMmbMs = uqlOcMmbMs;
        this.uqlOcMmbMsPc = uqlOcMmbMsPc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Valuation)) return false;
        Valuation valuation = (Valuation) o;
        return tradeId.equals(valuation.tradeId) && uqlOcMmbMs.equals(valuation.uqlOcMmbMs) && uqlOcMmbMsPc.equals(valuation.uqlOcMmbMsPc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId, uqlOcMmbMs, uqlOcMmbMsPc);
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public BigDecimal getUqlOcMmbMs() {
        return uqlOcMmbMs;
    }

    public void setUqlOcMmbMs(BigDecimal uqlOcMmbMs) {
        this.uqlOcMmbMs = uqlOcMmbMs;
    }

    public BigDecimal getUqlOcMmbMsPc() {
        return uqlOcMmbMsPc;
    }

    public void setUqlOcMmbMsPc(BigDecimal uqlOcMmbMsPc) {
        this.uqlOcMmbMsPc = uqlOcMmbMsPc;
    }

    @Override
    public String toString() {
//        return "Valuation{" +
//                "tradeId=" + tradeId +
//                ", uqlOcMmbMs=" + uqlOcMmbMs +
//                ", uqlOcMmbMsPc=" + uqlOcMmbMsPc +
//                '}';

        StringBuilder sb = new StringBuilder();
//        sb.append(tradeId);
//        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(uqlOcMmbMs);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(uqlOcMmbMsPc);

        return sb.toString();
    }
}
