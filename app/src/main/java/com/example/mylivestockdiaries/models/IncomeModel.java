package com.example.mylivestockdiaries.models;

public class IncomeModel {
    public String incomename,date,howmuch;

    public IncomeModel() {

    }


    public IncomeModel(String incomename, String date, String howmuch) {
        this.incomename = incomename;
        this.date = date;
        this.howmuch = howmuch;
    }

    public String getIncomename() {
        return incomename;
    }

    public void setIncomename(String incomename) {
        this.incomename = incomename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHowmuch() {
        return howmuch;
    }

    public void setHowmuch(String howmuch) {
        this.howmuch = howmuch;
    }
}
