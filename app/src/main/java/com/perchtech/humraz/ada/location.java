package com.perchtech.humraz.ada;

/**
 * Created by humra on 8/16/2016.
 */
public class location {
    private String lat;
  private String lng;
    private String yes;

    long stackId;
    public location() {
      /*Blank default constructor essential for Firebase*/
    }
    public location(String a)
    {

    }



    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getYes() {
        return yes;
    }

    public void setYes(String yes) {
        this.yes = yes;
    }
}