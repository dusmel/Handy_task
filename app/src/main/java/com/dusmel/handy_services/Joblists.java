package com.dusmel.handy_services;

/**
 * Created by abd on 20/09/2017.
 */

public class Joblists {

    private String names;
    private String tel;
    private String mail;
    private String address;
    private String description;
    private String timestamp;

    public Joblists(String names, String tel, String mail, String address, String description, String timestamp) {
        this.names = names;
        this.tel = tel;
        this.mail = mail;
        this.address = address;
        this.description = description;
        this.timestamp = timestamp;
    }


    public String getNames() {
        return names;
    }

    public String getTel() {
        return tel;
    }

    public String getMail() {
        return mail;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
