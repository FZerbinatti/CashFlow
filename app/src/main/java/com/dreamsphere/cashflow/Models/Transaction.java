package com.dreamsphere.cashflow.Models;

public class Transaction {

    Integer type;
    Float amount;
    String cathegory;
    String description;
    Integer day;
    Integer month;
    Integer year;
    long milliseconds;



    public Transaction(Integer type, Float amount, String cathegory, String description, Integer day, Integer month, Integer year, long milliseconds) {
        this.type = type;
        this.amount = amount;
        this.cathegory = cathegory;
        this.description = description;
        this.day = day;
        this.month = month;
        this.year = year;
        this.milliseconds = milliseconds;

    }

    public Transaction() {

    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getCathegory() {
        return cathegory;
    }

    public void setCathegory(String cathegory) {
        this.cathegory = cathegory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(Long milliseconds) {
        this.milliseconds = milliseconds;
    }
}
