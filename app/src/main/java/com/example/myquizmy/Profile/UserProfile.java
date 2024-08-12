package com.example.myquizmy.Profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class UserProfile {
    private String phone;
    private String name;
    private String email;
    private String age;
    private String address;
    private String bank = "";
    private String acc = "";
    private String ifsc = "";
    private String upino;
    private String upiid;
    private String referal;
    private String fac;
    private String Whats;
    private byte[] image;
    private double bankBalance;  // New field for bank balance

    // Updated constructor
    public UserProfile(String phone, String name, String email, String age, String address, byte[] image, String bank, String acc, String ifsc, String fac, String Whats, String referal, String upiid, String upino, String bankBalance) {
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.age = age;
        this.address = address;
        this.image = image;
        this.bank = bank;
        this.acc = acc;
        this.ifsc = ifsc;
        this.fac = fac;
        this.Whats = Whats;
        this.upino = upino;
        this.upiid = upiid;
        this.referal = referal;
        this.bankBalance = Double.parseDouble(bankBalance);
    }

    // Getter and Setter for bankBalance
    public double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(double bankBalance) {
        this.bankBalance = bankBalance;
    }

    // Existing getters and setters...

    public String getWhats() {
        return Whats;
    }

    public void setWhats(String Whats) {
        this.Whats = Whats;
    }

    public String getFac() {
        return fac;
    }

    public void setFac(String fac) {
        this.fac = fac;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImageBytes() {
        return image;
    }

    public void setImageBytes(byte[] image) {
        this.image = image;
    }

    public Bitmap getImage() {
        if (image != null && image.length > 0) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        } else {
            return null;
        }
    }

    public void setImage(Bitmap bitmap) {
        if (bitmap != null) {
            this.image = bitmapToByteArray(bitmap);
        }
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public String getUpino() {
        return upino;
    }

    public void setUpino(String upino) {
        this.upino = upino;
    }

    public String getUpiid() {
        return upiid;
    }

    public void setUpiid(String upiid) {
        this.upiid = upiid;
    }

    public String getReferal() {
        return referal;
    }

    public void setReferal(String referal) {
        this.referal = referal;
    }
}
