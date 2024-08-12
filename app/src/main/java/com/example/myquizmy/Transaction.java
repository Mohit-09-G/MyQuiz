package com.example.myquizmy;
public class Transaction {
    private String type;
    private String amount;
    public String date,pay_type,ids;

    public Transaction(String type, String amount, String date,String pay_type,String ids) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.pay_type=pay_type;
        this.ids=ids;

    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

