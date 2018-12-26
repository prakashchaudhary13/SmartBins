package com.example.alfurquan.googlemapgoogleplaces;

public class Bins {


    String id;
    String lat;
    String label;
    String lon;
    String dist;

    public Bins(String id, String lat, String label, String lon, String dist) {
        this.id = id;
        this.lat = lat;
        this.label = label;
        this.lon = lon;
        this.dist = dist;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
