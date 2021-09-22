package com.example.mylivestockdiaries.models;

public class MilkModel {

     public String date,tagNumber,totalProduced,consumed,sold;

    public MilkModel() {
    }

    public MilkModel(String date, String tagNumber, String totalProduced, String consumed, String sold) {
        this.date = date;
        this.tagNumber = tagNumber;
        this.totalProduced = totalProduced;
        this.consumed = consumed;
        this.sold = sold;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public String getTotalProduced() {
        return totalProduced;
    }

    public void setTotalProduced(String totalProduced) {
        this.totalProduced = totalProduced;
    }

    public String getConsumed() {
        return consumed;
    }

    public void setConsumed(String consumed) {
        this.consumed = consumed;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }
}
