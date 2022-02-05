package com.lk.onlinebookshopcustomer.POJO;

public class MapTimeObject {
    public int getTimeInMins() {
        return timeInMins;
    }

    public void setTimeInMins(int timeInMins) {
        this.timeInMins = timeInMins;
    }

    public String getTimeInText() {
        return timeInText;
    }

    public void setTimeInText(String timeInText) {
        this.timeInText = timeInText;
    }

    public MapTimeObject(){}
    public MapTimeObject(int timeInMins, String timeInText) {
        this.timeInMins = timeInMins;
        this.timeInText = timeInText;
    }

    int timeInMins;
    String timeInText;
}
