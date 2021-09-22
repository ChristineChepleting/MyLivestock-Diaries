package com.example.mylivestockdiaries.models;

public class DataModel {

    public String tagNumber, breedName, gender, age, dob, weight, joinedOn, stage, source;

    public DataModel() {
    }

    public DataModel(String tagNumber, String breedName, String gender, String age, String dob, String weight, String joinedOn, String stage, String source) {
        this.tagNumber = tagNumber;
        this.breedName = breedName;
        this.gender = gender;
        this.age = age;
        this.dob = dob;
        this.weight = weight;
        this.joinedOn = joinedOn;
        this.stage = stage;
        this.source = source;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(String joinedOn) {
        this.joinedOn = joinedOn;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
