package com.mountreachsolution.e_learningapp.HomeWork;

public class POJOHomework {

    public String howework_date;
    public String homework_title;
    public String homework_description;

    public POJOHomework(String howework_date, String homework_title, String homework_description) {
        this.howework_date = howework_date;
        this.homework_title = homework_title;
        this.homework_description = homework_description;
    }

    public String getHowework_date() {
        return howework_date;
    }

    public void setHowework_date(String howework_date) {
        this.howework_date = howework_date;
    }

    public String getHomework_title() {
        return homework_title;
    }

    public void setHomework_title(String homework_title) {
        this.homework_title = homework_title;
    }

    public String getHomework_description() {
        return homework_description;
    }

    public void setHomework_description(String homework_description) {
        this.homework_description = homework_description;
    }
}
