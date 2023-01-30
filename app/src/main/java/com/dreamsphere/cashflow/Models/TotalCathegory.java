package com.dreamsphere.cashflow.Models;

public class TotalCathegory {

    String nomeCategoria;
    Float totalAmount;

    public TotalCathegory(String nomeCategoria, Float totalAmount) {

        this.nomeCategoria = nomeCategoria;
        this.totalAmount = totalAmount;
    }

    public TotalCathegory() {
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
