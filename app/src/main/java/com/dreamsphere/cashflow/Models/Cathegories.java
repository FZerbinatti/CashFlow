package com.dreamsphere.cashflow.Models;

public class Cathegories {

    private final int iconID;
    private final String iconText;

    public Cathegories(int iconID, String iconText) {
        this.iconID = iconID;
        this.iconText = iconText;
    }


    public int getIconID() {
        return iconID;
    }

    public String getIconText() {
        return iconText;
    }
}
