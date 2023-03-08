package com.dreamsphere.cashflow;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class GraphsActivity extends AppCompatActivity {
    public static final String TAG ="XYZ GraphsActivity";

    Button button_CombinedChart, button_horizontal_chart, button_LineChart2;

    HorizontalBarChart horizontal_chart_expenses, horizontal_chart_incomes;
    private CombinedChart combined_chart_graph;
    private final int monthsNumber = 12;
    DatabaseHelper databaseHelper;
    Random random;
    ImageButton  button_month_after, button_month_before, imageButtonBack ;
    Calendar myCalendar;
    Integer currentYear;
    TextView id_current_year, id_totals;
    Float total_expenses, total_incomes;
    LinearLayout second_graphs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        id_current_year= findViewById(R.id.id_current_year);
        imageButtonBack= findViewById(R.id.imageButtonBack);
        button_month_before= findViewById(R.id.button_month_before_graph);
        button_month_after= findViewById(R.id.button_month_after_graph);
        id_totals = findViewById(R.id.id_total_exp_inc);

        button_CombinedChart = findViewById(R.id.button_CombinedChart);
        combined_chart_graph = findViewById(R.id.combined_chart);
        horizontal_chart_expenses = findViewById(R.id.horizontal_chart_expenses);
        horizontal_chart_incomes = findViewById(R.id.horizontal_chart_incomes);

        button_horizontal_chart = findViewById(R.id.button_LineChart);
        button_LineChart2 = findViewById(R.id.button_LineChart2);
        second_graphs= findViewById(R.id.second_graphs);


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

        button_month_after();
        button_month_before();




    }

    private void button_month_after() {



        button_month_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_expenses = total_incomes = 0f;
                currentYear++;
                setUpFirstGraph(currentYear);
                id_current_year.setText(currentYear.toString());
            }
        });

    }

    private void button_month_before() {

        button_month_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_expenses = total_incomes = 0f;
                currentYear--;
                setUpFirstGraph(currentYear);
                id_current_year.setText(currentYear.toString());
            }
        });

    }

    private void buttonFirstGraph() {

        button_CombinedChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                combined_chart_graph.setVisibility(View.VISIBLE);
                second_graphs.setVisibility(View.GONE);
                setUpFirstGraph(currentYear);
            }
        });



    }

    private void buttonSecondGraph() {

        button_horizontal_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                combined_chart_graph.setVisibility(View.GONE);
                second_graphs.setVisibility(View.VISIBLE);

                graphExpensesRight();
                graphIncomesLeft();

            }
        });



    }

    private void graphIncomesLeft() {

        ArrayList<TotalCathegory> expensesCathegories = databaseHelper.getAmountForCathegories(currentYear, 1);

        ArrayList<String> labels2 = new ArrayList<>();
        for(int i=0; i<expensesCathegories.size(); i++){
            labels2.add(expensesCathegories.get(i).getNomeCategoria());
        }

        horizontal_chart_incomes.setDrawBarShadow(false);
        horizontal_chart_incomes.setDrawValueAboveBar(true);
        horizontal_chart_incomes.getDescription().setEnabled(false);
        horizontal_chart_incomes.setPinchZoom(false);
        horizontal_chart_incomes.setDrawGridBackground(false);


        XAxis xl = horizontal_chart_incomes.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);


        //CategoryBarChartXaxisFormatter xaxisFormatter = new CategoryBarChartXaxisFormatter(labels);
        //xl.setValueFormatter(xaxisFormatter);
        //xl.setGranularity(1);

        xl.setValueFormatter(new IndexAxisValueFormatter(labels2));
        xl.setGranularity(1f);
        xl.setGranularityEnabled(true);

        //xl.setXOffset(-25.0f);

        YAxis yl = horizontal_chart_incomes.getAxisLeft();
        yl.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yl.setDrawGridLines(false);
        yl.setEnabled(false);
        yl.setAxisMinimum(0f);
        yl.setInverted(false);

        //elementi asse X sotto
        YAxis yr = horizontal_chart_incomes.getAxisRight();
        yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yr.setDrawGridLines(true);
        yr.setGranularity(10f);
        yr.setAxisMinimum(0f);
        yr.setDrawTopYLabelEntry(true);
        yr.setAxisMaximum(0f);


        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < expensesCathegories.size(); i++) {
            yVals1.add(new BarEntry(i, expensesCathegories.get(i).getTotalAmount()));
        }

        BarDataSet set2;
        set2 = new BarDataSet(yVals1, "DataSet 1");
        set2.setColor(getColor(R.color.material_green2));
        set2.setBarBorderColor(getColor(R.color.material_green2));
        set2.setBarShadowColor(getColor(R.color.material_green2));
        set2.setValueTextColor(getColor(R.color.black));

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set2);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(8f);
        data.setBarWidth(.98f);
        horizontal_chart_incomes.setData(data);

        horizontal_chart_incomes.getLegend().setEnabled(false);
    }

    private void graphExpensesRight() {
        ArrayList<TotalCathegory> expensesCathegories = databaseHelper.getAmountForCathegories(currentYear, -1);


        ArrayList<String> labels2 = new ArrayList<>();
        for(int i=0; i<expensesCathegories.size(); i++){
            labels2.add(expensesCathegories.get(i).getNomeCategoria());
        }

        horizontal_chart_expenses.setDrawBarShadow(false);
        horizontal_chart_expenses.setDrawValueAboveBar(true);
        horizontal_chart_expenses.getDescription().setEnabled(false);
        horizontal_chart_expenses.setPinchZoom(false);
        horizontal_chart_expenses.setDrawGridBackground(false);


        XAxis xl = horizontal_chart_expenses.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        //xl.setXOffset(-25f);
        //xl.setYOffset(10f);
        //CategoryBarChartXaxisFormatter xaxisFormatter = new CategoryBarChartXaxisFormatter(labels);
        //xl.setValueFormatter(xaxisFormatter);
        //xl.setGranularity(1);

        xl.setValueFormatter(new IndexAxisValueFormatter(labels2));
        xl.setGranularity(1f);
        xl.setGranularityEnabled(true);
        xl.setPosition(XAxis.XAxisPosition.TOP );

        YAxis yl = horizontal_chart_expenses.getAxisLeft();
        yl.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yl.setDrawGridLines(false);
        yl.setEnabled(false);
        yl.setAxisMinimum(0f);
        yl.setInverted(true);

        YAxis yr = horizontal_chart_expenses.getAxisRight();
        yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yr.setDrawGridLines(true);
        yr.setAxisMinimum(0f);
        yr.setInverted(true);
        yr.setAxisMaximum(0f);


        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < expensesCathegories.size(); i++) {
            yVals1.add(new BarEntry(i, expensesCathegories.get(i).getTotalAmount()));
        }

        BarDataSet set2;
        set2 = new BarDataSet(yVals1, "DataSet 1");
        set2.setColor(getColor(R.color.material_red));
        set2.setBarBorderColor(getColor(R.color.material_red));
        set2.setBarShadowColor(getColor(R.color.material_red));
        set2.setValueTextColor(getColor(R.color.black));

        //set1.setColor(R.color.material_red);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set2);
        BarData data = new BarData(dataSets);
        //data.setValueTextColor(R.color.material_red);

        data.setValueTextSize(9f);

        data.setBarWidth(0.98f);
        horizontal_chart_expenses.setData(data);

        horizontal_chart_expenses.getLegend().setEnabled(false);


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
        if (horizontal_chart_expenses.getData() != null &&
                horizontal_chart_expenses.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) horizontal_chart_expenses.getData().getDataSetByIndex(0);
            set1.setValues(values);

            horizontal_chart_expenses.getData().notifyDataChanged();
            horizontal_chart_expenses.notifyDataSetChanged();
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
            horizontal_chart_expenses.setData(data);
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

        id_totals.setText("Entrate: "+String.format("%.2f", total_incomes)+ "€  Uscite: "+String.format("%.2f", total_expenses) +"€");

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