package com.example.myquizmy;

import android.os.Build;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomeItems implements Serializable {
    private int imageResource;
    private String quizTitle;
    private String dateTime;
    private int joiningFee;
    private int maxEntry;
    private int availableSpots;
    private int prizePool;

    public HomeItems(int imageResource, String quizTitle, String currentDateTime, int joiningFee, int maxEntry, int availableSpots, int prizePool) {
        this.imageResource = imageResource;
        this.quizTitle = quizTitle;
        this.dateTime = getCurrentDateTime();
        this.joiningFee = joiningFee;
        this.maxEntry = maxEntry;
        this.availableSpots = availableSpots;
        this.prizePool = prizePool;
    }

    private String getCurrentDateTime() {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDateTime.now().format(formatter);
        }
        return null;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getJoiningFee() {
        return joiningFee;
    }

    public int getMaxEntry() {
        return maxEntry;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public int getPrizePool() {
        return prizePool;
    }
}
