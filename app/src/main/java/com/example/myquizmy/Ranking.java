package com.example.myquizmy;

import java.util.ArrayList;

public class Ranking {

    public static ArrayList<ModelRank> getRankPrice(){
        ArrayList<ModelRank> rankList
                = new ArrayList<ModelRank>();
        ModelRank rank1 = new ModelRank("1",
                "Rs. 8/-");
        ModelRank rank2 = new ModelRank("2",
                "Rs. 5/-");
        rankList.add(rank1);
        rankList.add(rank2);
        return rankList;
    }
}
