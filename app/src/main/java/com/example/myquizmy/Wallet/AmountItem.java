package com.example.myquizmy.Wallet;

public class AmountItem {
    public int amount;
    public String s,usd;




    public AmountItem(int amount, String s, String usd) {
        this.amount=amount;
        this.s=s;
        this.usd=usd;

    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
