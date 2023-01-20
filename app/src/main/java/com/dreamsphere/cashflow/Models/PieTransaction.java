package com.dreamsphere.cashflow.Models;

public class PieTransaction {

    Integer value;
    String label;

    public PieTransaction(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public PieTransaction() {

    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
