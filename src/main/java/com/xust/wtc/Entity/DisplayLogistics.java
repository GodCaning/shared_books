package com.xust.wtc.Entity;

/**
 * 物流信息的显示
 * Created by Spirit on 2018/1/9.
 */
public class DisplayLogistics {

    private int id;   //物流ID
    private String number; //物流编号
    private String companyName; //物流公司名称
    private String companyNumber; //物流公司编号

    public DisplayLogistics() {
    }

    public DisplayLogistics(int id, String number, String companyName, String companyNumber) {
        this.id = id;
        this.number = number;
        this.companyName = companyName;
        this.companyNumber = companyNumber;
    }

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }
}
