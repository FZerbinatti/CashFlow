package com.dreamsphere.cashflow.Models;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class ValFormatter extends ValueFormatter {


    @Override
    public String getFormattedValue(float value) {
        if(value == Math.round(value)){ // value is an integer?
            return String.valueOf((int) value);
        }else{
            return "abc";
        }
    }
}