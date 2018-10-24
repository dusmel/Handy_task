package com.dusmel.handy_services;

/**
 * Created by abd on 28/08/2017.
 */

public class ListHandyman {
    private int id;
    private String handyFname;
    private String handyLname;
    private String handyField;
    private String handyAdd;
    private String handyPrice;
    private String handySuccess;
    private String handypfl;
    private String field_id;
    private String descr;
    private String dateJ;

    public ListHandyman(int id, String handyFname, String handyLname, String handyField, String handyAdd, String handyPrice,
                        String handySuccess, String handypfl, String field_id, String descr, String dateJ) {

        this.id = id;
        this.handyFname = handyFname;
        this.handyLname = handyLname;
        this.handyField = handyField;
        this.handyAdd = handyAdd;
        this.handyPrice = handyPrice;
        this.handySuccess = handySuccess;
        this.handypfl = handypfl;
        this.field_id = field_id;
        this.descr = descr;
        this.dateJ = dateJ;

    }

    public int getId() {
        return id;
    }

    public String getHandyFname() {
        return handyFname;
    }

    public String getHandyLname() {
        return handyLname;
    }

    public String getHandyField() {
        return handyField;
    }

    public String getHandyAdd() {
        return handyAdd;
    }

    public String getHandyPrice() {
        return handyPrice;
    }

    public String getHandySuccess() {
        return handySuccess;
    }

    public String getHandypfl() {
        return handypfl;
    }

    public String getField_id() {
        return field_id;
    }

    public String getDescr() {
        return descr;
    }

    public String getDateJ() {
        return dateJ;
    }
}
