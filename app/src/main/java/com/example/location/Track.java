package com.example.location;

public class Track {
    String dir;
    double lat;

    public Track() {
    }

    double longi;
    String walkerUserId;
    String idTrack;

    public Track(String dir, double lat, double longi, String walkerUserId, String idTrack) {
        this.dir = dir;
        this.lat = lat;
        this.longi = longi;
        this.walkerUserId = walkerUserId;
        this.idTrack = idTrack;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public String getWalkerUserId() {
        return walkerUserId;
    }

    public void setWalkerUserId(String walkerUserId) {
        this.walkerUserId = walkerUserId;
    }

    public String getIdTrack() {
        return idTrack;
    }

    public void setIdTrack(String idTrack) {
        this.idTrack = idTrack;
    }
}
