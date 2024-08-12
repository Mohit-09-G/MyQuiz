package com.example.myquizmy;

import java.io.Serializable;

public class ModelRank implements Serializable {
    private final String rank;
    private final String price;

    public ModelRank(String rank, String price) {
        this.rank = rank;
        this.price = price;
    }

    public String getRank() {
        return rank;
    }

    public String getPrice() {
        return price;
    }
}
