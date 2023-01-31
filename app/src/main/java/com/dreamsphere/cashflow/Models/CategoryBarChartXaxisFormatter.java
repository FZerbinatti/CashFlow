package com.dreamsphere.cashflow.Models;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class CategoryBarChartXaxisFormatter extends ValueFormatter  {

    ArrayList<String> mValues;

    public CategoryBarChartXaxisFormatter(ArrayList<String> values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        int val = (int) value;
        String label = "VVVV";
        if (val >= 0 && val < mValues.size()) {
            label = mValues.get(val);
        } else {
            label = "AAAA";
        }
        return label;
    }
}