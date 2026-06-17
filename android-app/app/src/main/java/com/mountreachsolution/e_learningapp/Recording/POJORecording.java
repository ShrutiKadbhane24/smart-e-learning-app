package com.mountreachsolution.e_learningapp.Recording;

public class POJORecording {

    public String recording_date;
    public String recording_title;
    public String recording_description;
    public String recording_url;

    public POJORecording(String recording_date, String recording_title, String recording_description, String recording_url) {
        this.recording_date = recording_date;
        this.recording_title = recording_title;
        this.recording_description = recording_description;
        this.recording_url = recording_url;
    }

    public String getRecording_date() {
        return recording_date;
    }

    public void setRecording_date(String recording_date) {
        this.recording_date = recording_date;
    }

    public String getRecording_title() {
        return recording_title;
    }

    public void setRecording_title(String recording_title) {
        this.recording_title = recording_title;
    }

    public String getRecording_description() {
        return recording_description;
    }

    public void setRecording_description(String recording_description) {
        this.recording_description = recording_description;
    }

    public String getRecording_url() {
        return recording_url;
    }

    public void setRecording_url(String recording_url) {
        this.recording_url = recording_url;
    }
}
