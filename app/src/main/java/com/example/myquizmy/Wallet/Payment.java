package com.example.myquizmy.Wallet;

public class Payment{
    private String upiId;
    private String upiNo;
    private String bankName;
    private String bankAcc;
    private String bankIfc;


    public Payment(String upiId, String upiNo) {
        this.upiId = upiId;
        this.upiNo = upiNo;
        this.bankName = null;
        this.bankAcc = null;
        this.bankIfc = null;
    }


    public Payment(String bankName, String bankAcc, String bankIfc) {
        this.upiId = null;
        this.upiNo = null;
        this.bankName = bankName;
        this.bankAcc = bankAcc;
        this.bankIfc = bankIfc;
    }


    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getUpiNo() {
        return upiNo;
    }

    public void setUpiNo(String upiNo) {
        this.upiNo = upiNo;
    }


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAcc() {
        return bankAcc;
    }

    public void setBankAcc(String bankAcc) {
        this.bankAcc = bankAcc;
    }

    public String getBankIfc() {
        return bankIfc;
    }

    public void setBankIfc(String bankIfc) {
        this.bankIfc = bankIfc;
    }
}
