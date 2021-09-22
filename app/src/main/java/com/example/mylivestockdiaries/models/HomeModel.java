package com.example.mylivestockdiaries.models;

public class HomeModel {
    private String itemName;
    private  int imageId;

    public HomeModel(String itemName, int imageId) {
        this.itemName = itemName;
        this.imageId = imageId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
