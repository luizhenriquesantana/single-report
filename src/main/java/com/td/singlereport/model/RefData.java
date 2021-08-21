package com.td.singlereport.model;

import com.td.singlereport.constants.Constants;

import java.io.Serializable;
import java.util.Objects;

public class RefData implements Serializable {

    private String topOfHouse;
    private String segment;
    private String viceChair;
    private String globalBusiness;
    private String policy;
    private String desk;
    private String portfolio;
    private Integer BU;
    private String cline;
    private String inventory;

    public RefData() {
    }

    public RefData(String topOfHouse, String segment, String viceChair, String globalBusiness, String policy, String desk, String portfolio, Integer BU, String cline, String inventory) {
        this.topOfHouse = topOfHouse;
        this.segment = segment;
        this.viceChair = viceChair;
        this.globalBusiness = globalBusiness;
        this.policy = policy;
        this.desk = desk;
        this.portfolio = portfolio;
        this.BU = BU;
        this.cline = cline;
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RefData)) return false;
        RefData refData = (RefData) o;
        return Objects.equals(topOfHouse, refData.topOfHouse) && Objects.equals(segment, refData.segment) && Objects.equals(viceChair, refData.viceChair) && Objects.equals(globalBusiness, refData.globalBusiness) && Objects.equals(policy, refData.policy) && Objects.equals(desk, refData.desk) && Objects.equals(portfolio, refData.portfolio) && Objects.equals(BU, refData.BU) && Objects.equals(cline, refData.cline) && inventory.equals(refData.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topOfHouse, segment, viceChair, globalBusiness, policy, desk, portfolio, BU, cline, inventory);
    }

    public String getTopOfHouse() {
        return topOfHouse;
    }

    public void setTopOfHouse(String topOfHouse) {
        this.topOfHouse = topOfHouse;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getViceChair() {
        return viceChair;
    }

    public void setViceChair(String viceChair) {
        this.viceChair = viceChair;
    }

    public String getGlobalBusiness() {
        return globalBusiness;
    }

    public void setGlobalBusiness(String globalBusiness) {
        this.globalBusiness = globalBusiness;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public Integer getBU() {
        return BU;
    }

    public void setBU(Integer BU) {
        this.BU = BU;
    }

    public String getCline() {
        return cline;
    }

    public void setCline(String cline) {
        this.cline = cline;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
//        return "RefData{" +
//                "topOfHouse='" + topOfHouse + '\'' +
//                ", segment='" + segment + '\'' +
//                ", viceChair='" + viceChair + '\'' +
//                ", globalBusiness='" + globalBusiness + '\'' +
//                ", policy='" + policy + '\'' +
//                ", desk='" + desk + '\'' +
//                ", portfolio='" + portfolio + '\'' +
//                ", BU=" + BU +
//                ", cline='" + cline + '\'' +
//                ", inventory='" + inventory + '\'' +
//                '}';
        StringBuilder sb = new StringBuilder();

        sb.append(topOfHouse);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(segment);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(viceChair);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(globalBusiness);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(policy);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(desk);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(portfolio);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(BU);
        sb.append(Constants.FILE.COMMA_SEPARATOR);
        sb.append(cline);
//        sb.append(Constants.FILE.COMMA_SEPARATOR);
//        sb.append(inventory);

        return sb.toString();
    }
}
