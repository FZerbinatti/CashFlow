package com.dreamsphere.cashflow;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dreamsphere.cashflow.Models.TotalCathegory;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class GraphsActivity extends AppCompatActivity {
    public static final String TAG ="XYZ GraphsActivity";

    Button button_CombinedChart, button_horizontal_chart, button_LineChart2;

    HorizontalBarChart chart,horizontal_bar_chart;
    private CombinedChart combined_chart_graph;
    private final int monthsNumber = 12;
    DatabaseHelper databaseHelper;
    Random random;
    ImageButton  button_month_after, button_month_before, imageButtonBack ;
    Calendar myCalendar;
    Integer currentYear;
    TextView id_current_year, id_totals;
    Float total_expenses, total_incomes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        id_current_year= findViewById(R.id.id_current_year);
        imageButtonBack= findViewById(R.id.imageButtonBack);
        button_month_before= findViewById(R.id.button_month_before);
        button_month_after= findViewById(R.id.button_month_after);
        id_totals = findViewById(R.id.id_total_exp_inc);

        button_CombinedChart = findViewById(R.id.button_CombinedChart);
        combined_chart_graph = findViewById(R.id.combined_chart);
        chart = findViewById(R.id.horizontal_bar_chart);

        button_horizontal_chart = findViewById(R.id.button_LineChart);
        button_LineChart2 = findViewById(R.id.button_LineChart2);


        total_expenses = total_incomes = 0f;

        databaseHelper = new DatabaseHelper(this);
        myCalendar = Calendar.getInstance();
        currentYear = myCalendar.get(Calendar.YEAR);
        id_current_year.setText(currentYear.toString());

        goBack();
        nextMonth();
        previewsMonth();

        setUpFirstGraph(currentYear);
        buttonSecondGraph();
        buttonFirstGraph();




    }

    private void buttonFirstGraph() {

        button_CombinedChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                combined_chart_graph.setVisibility(View.VISIBLE);
                chart.setVisibility(View.GONE);
                setUpFirstGraph(currentYear);
            }
        });



    }

    private void buttonSecondGraph() {

        button_horizontal_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                combined_chart_graph.setVisibility(View.GONE);
                chart.setVisibility(View.VISIBLE);
                chart.getDescription().setEnabled(false);
                chart.setMaxVisibleValueCount(60);
                chart.setDrawGridBackground(false);
                XAxis xl = chart.getXAxis();
                chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
                xl.setPosition(XAxis.XAxisPosition.BOTTOM);
                //xl.setTypeface(tfLight);
                xl.setDrawAxisLine(true);
                xl.setDrawGridLines(false);
                xl.setGranularity(10f);

                YAxis yl = chart.getAxisLeft();
                //yl.setTypeface(tfLight);
                yl.setDrawAxisLine(true);
                yl.setDrawGridLines(true);
                yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
                yl.setInverted(false);

                YAxis yr = chart.getAxisRight();
                //yr.setTypeface(tfLight);
                yr.setDrawAxisLine(true);
                yr.setDrawGridLines(false);
                yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

                chart.setFitBars(true);
                chart.animateY(1500);


                Legend l = chart.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                l.setDrawInside(false);
                l.setFormSize(8f);
                l.setXEntrySpace(4f);

                setData(databaseHelper.getAmountForCathegories(currentYear, -1));

            }
        });



    }


    private void setData(ArrayList<TotalCathegory> totalCathegories) {

        float barWidth = 9f;
        float spaceForBar = 10f;
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < totalCathegories.size(); i++) {

            values.add(new BarEntry(i * spaceForBar, totalCathegories.get(i).getTotalAmount(), totalCathegories.get(i).getNomeCategoria()));
        }

        BarDataSet set1;
        String[] labels = {" a","b ", "c"};
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);

            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Uscite");

            set1.setDrawIcons(false);


            //set1.setValueFormatter();


            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(tfLight);
            data.setBarWidth(barWidth);
            chart.setData(data);
        }


    }

    private void setUpFirstGraph(Integer year) {


        combined_chart_graph.getDescription().setEnabled(false);
        combined_chart_graph.setBackgroundColor(Color.WHITE);
        combined_chart_graph.setDrawGridBackground(false);
        combined_chart_graph.setDrawBarShadow(false);
        combined_chart_graph.setHighlightFullBarEnabled(false);



        //input Y data (Months Data - 12 Values)
        ArrayList<Double> valuesList = new ArrayList<Double>();
        valuesList.add((double)1800);
        valuesList.add((double)1600);
        valuesList.add((double)500);
        valuesList.add((double)1500);
        valuesList.add((double)900);
        valuesList.add((double)1700);
        valuesList.add((double)400);
        valuesList.add((double)2000);
        valuesList.add((double)2500);
        valuesList.add((double)3500);
        valuesList.add((double)3000);
        valuesList.add((double)1800);

        //prepare Bar Entries
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < valuesList.size(); i++) {
            BarEntry barEntry = new BarEntry(i+1, valuesList.get(i).floatValue()); //start always from x=1 for the first bar
            entries.add(barEntry);
        }

        //initialize x Axis Labels (labels for 13 vertical grid lines)
        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Gen"); //this label will be mapped to the 1st index of the valuesList
        xAxisLabel.add("Feb");
        xAxisLabel.add("Mar");
        xAxisLabel.add("Apr");
        xAxisLabel.add("Mag");
        xAxisLabel.add("Giu");
        xAxisLabel.add("lug");
        xAxisLabel.add("Ago");
        xAxisLabel.add("Set");
        xAxisLabel.add("Ott");
        xAxisLabel.add("Nov");
        xAxisLabel.add("Dic");
        xAxisLabel.add(""); //empty label for the last vertical grid line on Y-Right Axis

        // draw bars behind lines
        combined_chart_graph.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,  CombinedChart.DrawOrder.LINE,
        });

        Legend l = combined_chart_graph.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = combined_chart_graph.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = combined_chart_graph.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        XAxis xAxis = combined_chart_graph.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(10);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAxisMinimum(0 + 0.5f); //to center the bars inside the vertical grid lines we need + 0.5 step
        xAxis.setAxisMaximum(entries.size() + 0.5f); //to center the bars inside the vertical grid lines we need + 0.5 step
        xAxis.setLabelCount(xAxisLabel.size(), true); //draw x labels for 13 vertical grid lines
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setXOffset(0f); //labels x offset in dps
        xAxis.setYOffset(0f); //labels y offset in dps
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xAxisLabel.get((int) value);
            }
        });


        CombinedData data = new CombinedData();

        data.setData(generateLineData(year));
        data.setData(generateBarData(year));

        //data.setValueTypeface(Typeface.createFromAsset(getAssets(), "classicroman2.ttf"));

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        combined_chart_graph.setData(data);
        combined_chart_graph.animate();
        combined_chart_graph.animateY(1400, Easing.EaseInOutQuad);
        combined_chart_graph.invalidate();

        id_totals.setText("Entrate: "+total_incomes.toString() + "€  Uscite: "+total_expenses.toString() +"€");

    }

    private void previewsMonth() {
        button_month_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentYear--;
                id_current_year.setText(currentYear.toString());
                setUpFirstGraph(currentYear);
            }
        });

    }

    private void nextMonth() {
        button_month_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentYear++;
                id_current_year.setText(currentYear.toString());
                setUpFirstGraph(currentYear);
            }
        });

    }

    private void goBack() {
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private LineData generateLineData(Integer year) {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();


        for (int index = 0; index < monthsNumber; index++){

            Float sum = databaseHelper.getMonthSumOfTransactions(index, year, -1);
            entries.add(new Entry(index + 1.0f, sum));
            total_expenses += sum;
        }





        LineDataSet set = new LineDataSet(entries, "Spese");
        set.setColor(getColor(R.color.material_red));
        set.setLineWidth(2.5f);
        set.setCircleColor(getColor(R.color.material_red));
        set.setCircleRadius(5f);
        set.setFillColor(getColor(R.color.material_red));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(getColor(R.color.black));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData(Integer year) {

        float start = 1f;
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int index = 0; index < monthsNumber; index++){
            Float sum = databaseHelper.getMonthSumOfTransactions(index, year, +1);
            values.add(new BarEntry(index + 1.0f, sum));
            total_incomes += sum;
        }

        BarDataSet set1;

        /*f (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            Log.d(TAG, "generateBarData: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+chart.getData().toString());
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            Log.d(TAG, "generateBarData: "+chart.getData().getDataSetByIndex(0));

            set1.setColor(getColor(R.color.material_green2));
            set1.setBarBorderColor(getColor(R.color.material_green2));
            set1.setBarShadowColor(getColor(R.color.material_green2));
            set1.setValues(values);

            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {*/

            set1 = new BarDataSet(values, "Entrate");

            set1.setColor(getColor(R.color.material_green2));
            set1.setBarBorderColor(getColor(R.color.material_green2));
            set1.setBarShadowColor(getColor(R.color.material_green2));

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);


            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            data.setValueTextColor(getColor(R.color.black));

            return data;
       /*     }
        return null;*/
    }

    protected float getRandom(float range, float start) {
        return (float) (Math.random() * range) + start;
    }

    String[] months = new String[] {
            "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobere", "Novembre", "Dicembre"
    };


}