package com.xust.wtc.Entity.logistics;

/**
 * Created by Spirit on 2018/2/6.
 */
public class LogisticsAll {

    private int id;   //物流ID
    private String number; //物流编号
    private String companyNumber; //物流公司编号
    private int logisticsStatus; //物流状态
    private int status; //书籍借阅状态

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public int getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(int logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
