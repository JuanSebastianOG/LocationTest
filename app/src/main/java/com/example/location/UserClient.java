package com.example.location;

import java.util.ArrayList;
import java.util.List;

public class UserClient {

    String dir;
    double lat;
    double longi;
    String userId;
    String tipo;

    public UserClient(String dir, double lat, double longi, String userId, String tipo) {
        this.dir = dir;
        this.lat = lat;
        this.longi = longi;
        this.userId = userId;
        this.tipo = tipo;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
