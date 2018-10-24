package com.dusmel.handy_services;

/**
 * Created by abd on 12/09/2017.
 */

public class HiringListitem {

    private int id;
    private String Names;
    private  String tel;
    private String mail;
    private String field;
    private String address;
    private String description;
    private String handyman_names;
    private String longit;
    private String lat;
    private String timestamp;

    public HiringListitem(int id, String names, String tel, String mail, String field, String address,
                          String description, String handyman_names, String longit, String lat, String timestamp) {
        this.id = id;
        Names = names;
        this.tel = tel;
        this.mail = mail;
        this.field = field;
        this.address = address;
        this.description = description;
        this.handyman_names = handyman_names;
        this.longit = longit;
        this.lat = lat;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getNames() {
        return Names;
    }

    public String getTel() {
        return tel;
    }

    public String getMail() {
        return mail;
    }

    public String getField() {
        return field;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getHandyman_names() {
        return handyman_names;
    }

    public String getLongit() {
        return longit;
    }

    public String getLat() {
        return lat;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
