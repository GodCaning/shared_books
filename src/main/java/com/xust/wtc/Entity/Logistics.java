package com.xust.wtc.Entity;

/**
 * 物流信息类
 * Created by Spirit on 2018/1/9.
 */
public class Logistics {

    private int id;

    private String number;   //订单编号

    private int companyId;  //公司ID

    private int lendId;   //订单ID

    public Logistics() {
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getLendId() {
        return lendId;
    }

    public void setLendId(int lendId) {
        this.lendId = lendId;
    }
}
