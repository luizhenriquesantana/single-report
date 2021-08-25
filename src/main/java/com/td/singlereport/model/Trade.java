package com.td.singlereport.model;

import com.td.singlereport.constants.Constants;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Trade implements Serializable {

    private static final long serialVersionUID = -1703100557735595690L;

    private String inventory;
    private String book;
    private String system;
    private String legalEntity;
    private Long tradeId;
    private Integer version;
    private String tradeStatus;
    private String productType;
    private String resettingLeg;
    private String productSubType;
    private String tdsProductType;
    private String secCodeSubType;
    private String swapType;
    private String description;
    private Date tradeDate;
    private Date startDate;
    private Date maturityDate;

    public Trade() {
    }

    public Trade(String inventory, String book, String system, String legalEntity, Long tradeId, Integer version, String tradeStatus, String productType, String resettingLeg, String productSubType, String tdsProductType, String secCodeSubType, String swapType, String description, Date tradeDate, Date startDate, Date maturityDate/*, BigDecimal msPc, String breakStatus, String term*/) {
        this.inventory = inventory;
        this.book = book;
        this.system = system;
        this.legalEntity = legalEntity;
        this.tradeId = tradeId;
        this.version = version;
        this.tradeStatus = tradeStatus;
        this.productType = productType;
        this.resettingLeg = resettingLeg;
        this.productSubType = productSubType;
        this.tdsProductType = tdsProductType;
        this.secCodeSubType = secCodeSubType;
        this.swapType = swapType;
        this.description = description;
        this.tradeDate = tradeDate;
        this.startDate = startDate;
        this.maturityDate = maturityDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trade)) return false;
        Trade trade = (Trade) o;
        return getInventory().equals(trade.getInventory()) && Objects.equals(getBook(), trade.getBook()) && Objects.equals(getSystem(), trade.getSystem()) && Objects.equals(getLegalEntity(), trade.getLegalEntity()) && getTradeId().equals(trade.getTradeId()) && Objects.equals(getVersion(), trade.getVersion()) && Objects.equals(getTradeStatus(), trade.getTradeStatus()) && Objects.equals(getProductType(), trade.getProductType()) && Objects.equals(getResettingLeg(), trade.getResettingLeg()) && Objects.equals(getProductSubType(), trade.getProductSubType()) && Objects.equals(getTdsProductType(), trade.getTdsProductType()) && Objects.equals(getSecCodeSubType(), trade.getSecCodeSubType()) && Objects.equals(getSwapType(), trade.getSwapType()) && Objects.equals(getDescription(), trade.getDescription()) && getTradeDate().equals(trade.getTradeDate()) && Objects.equals(getStartDate(), trade.getStartDate()) && Objects.equals(getMaturityDate(), trade.getMaturityDate()) /*&& Objects.equals(getMsPc(), trade.getMsPc()) && Objects.equals(getBreakStatus(), trade.getBreakStatus()) && Objects.equals(getTerm(), trade.getTerm())*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInventory(), getBook(), getSystem(), getLegalEntity(), getTradeId(), getVersion(), getTradeStatus(), getProductType(), getResettingLeg(), getProductSubType(), getTdsProductType(), getSecCodeSubType(), getSwapType(), getDescription(), getTradeDate(), getStartDate(), getMaturityDate()/*, getMsPc(), getBreakStatus(), getTerm()*/);
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getResettingLeg() {
        return resettingLeg;
    }

    public void setResettingLeg(String resettingLeg) {
        this.resettingLeg = resettingLeg;
    }

    public String getProductSubType() {
        return productSubType;
    }

    public void setProductSubType(String productSubType) {
        this.productSubType = productSubType;
    }

    public String getTdsProductType() {
        return tdsProductType;
    }

    public void setTdsProductType(String tdsProductType) {
        this.tdsProductType = tdsProductType;
    }

    public String getSecCodeSubType() {
        return secCodeSubType;
    }

    public void setSecCodeSubType(String secCodeSubType) {
        this.secCodeSubType = secCodeSubType;
    }

    public String getSwapType() {
        return swapType;
    }

    public void setSwapType(String swapType) {
        this.swapType = swapType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    @Override
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        StringBuilder sb = new StringBuilder();
        sb.append(inventory);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(book);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(system);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(legalEntity);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(tradeId);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(version);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(tradeStatus);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(productSubType);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(resettingLeg);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(productSubType);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(tdsProductType);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(secCodeSubType);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(swapType);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(description);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(tradeDate != null ? sdf.format(tradeDate) : null);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(startDate != null ? sdf.format(startDate) : null);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(maturityDate != null ? sdf.format(maturityDate) : null);

        return sb.toString();
    }
}
