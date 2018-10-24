package com.dusmel.handy_services;

/**
 * Created by abd on 26/08/2017.
 */

public class ListItem {

    private String image;
    private  String title;
    private String logo;



    public ListItem(String image, String title, String logo) {
        this.image = image;
        this.title = title;
        this.logo = logo;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getLogo() {
        return logo;
    }
}
