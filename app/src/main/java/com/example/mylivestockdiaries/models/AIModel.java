package com.example.mylivestockdiaries.models;

public class AIModel {
    public String datefbreeding,tag;
    public AIModel(){
    }

    public AIModel(String datefbreeding, String tag) {
        this.datefbreeding = datefbreeding;
        this.tag = tag;
    }

    public String getDatefbreeding() {
        return datefbreeding;
    }

    public void setDatefbreeding(String datefbreeding) {
        this.datefbreeding = datefbreeding;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
