package com.xust.wtc.Entity.book;

/**
 * Created by Spirit on 2018/2/6.
 */
public class LenderInfo {

    private int lendId;
    private String lenderAddress;
    private String lenderPhone;
    private String lenderName;
    private String number;   //订单编号
    private int companyId;   //公司ID

    public String getLenderAddress() {
        return lenderAddress;
    }

    public void setLenderAddress(String lenderAddress) {
        this.lenderAddress = lenderAddress;
    }

    public String getLenderPhone() {
        return lenderPhone;
    }

    public void setLenderPhone(String lenderPhone) {
        this.lenderPhone = lenderPhone;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
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
