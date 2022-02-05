package com.lk.onlinebookshopcustomer.POJO;

public class MapDistanceObject{

    public String getDistanceText() {
        return distanceText;
    }

    public void setDistanceText(String distanceText) {
        this.distanceText = distanceText;
    }


    public MapDistanceObject(String distanceText, int distanceValM) {
        this.distanceText = distanceText;
        this.distanceValM = distanceValM;
    }

    String distanceText;

    public int getDistanceValM() {
        return distanceValM;
    }

    public void setDistanceValM(int distanceValM) {
        this.distanceValM = distanceValM;
    }

    int distanceValM;
}
