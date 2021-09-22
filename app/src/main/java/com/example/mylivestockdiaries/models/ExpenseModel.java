package com.example.mylivestockdiaries.models;

public class ExpenseModel {
    public String expensename,date,howmuch;
    public ExpenseModel(){

    }
    public ExpenseModel(String expensename, String date, String howmuch) {
        this.expensename = expensename;
        this.date = date;
        this.howmuch = howmuch;
    }
    public String getExpensename(){return expensename;}

    public void setExpensename(String expensename) {
        this.expensename = expensename;
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
