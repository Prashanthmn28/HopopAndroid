package com.hopop.hopop.ply.service;

public class Service {
    String interval, availability, fillingStatus;
    int color,imageId;

    public Service(String availability, String fillingStatus, String interval, int color, int imageId) {
        this.availability = availability;
        this.fillingStatus = fillingStatus;
        this.interval = interval;
        this.color = color;
        this.imageId = imageId;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getFillingStatus() {
        return fillingStatus;
    }

    public void setFillingStatus(String fillingStatus) {
        this.fillingStatus = fillingStatus;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getimageId() {
        return imageId;
    }

    public void setimageId(int imageId) {
        this.imageId = imageId;
    }
}
