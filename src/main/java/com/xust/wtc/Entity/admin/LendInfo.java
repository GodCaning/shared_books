package com.xust.wtc.Entity.admin;

/**
 * Created by Spirit on 2018/5/19.
 */
public class LendInfo {
    private int lendId;
    private String borrowerAddress;
    private String borrowerName;
    private String lenderAddress;
    private String lenderName;
    private String lendStatus;
    private int logisticsId;
    private int logisticsNumber;
    private String logisticsStatus;
    private String logisStatus;
    private String lenderLoginName;
    private String borrowerLoginName;
    private String title;

    public int getLendId() {
        return lendId;
    }

    public void setLendId(int lendId) {
        this.lendId = lendId;
    }

    public String getBorrowerAddress() {
        return borrowerAddress;
    }

    public void setBorrowerAddress(String borrowerAddress) {
        this.borrowerAddress = borrowerAddress;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getLenderAddress() {
        return lenderAddress;
    }

    public void setLenderAddress(String lenderAddress) {
        this.lenderAddress = lenderAddress;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    public String getLendStatus() {
        return lendStatus;
    }

    public void setLendStatus(String lendStatus) {
        this.lendStatus = lendStatus;
    }

    public int getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(int logisticsId) {
        this.logisticsId = logisticsId;
    }

    public int getLogisticsNumber() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(int logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    public String getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public String getLogisStatus() {
        return logisStatus;
    }

    public void setLogisStatus(String logisStatus) {
        this.logisStatus = logisStatus;
    }

    public String getLenderLoginName() {
        return lenderLoginName;
    }

    public void setLenderLoginName(String lenderLoginName) {
        this.lenderLoginName = lenderLoginName;
    }

    public String getBorrowerLoginName() {
        return borrowerLoginName;
    }

    public void setBorrowerLoginName(String borrowerLoginName) {
        this.borrowerLoginName = borrowerLoginName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
