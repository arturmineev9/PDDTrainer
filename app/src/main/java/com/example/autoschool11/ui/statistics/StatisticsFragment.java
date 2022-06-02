package com.example.autoschool11.ui.statistics;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.autoschool11.MainActivity;
import com.example.autoschool11.ProgressBarAnimation;
import com.example.autoschool11.R;
import com.example.autoschool11.db.DayStatisticsDataBaseHelper;
import com.example.autoschool11.db.IntensityClass;
import com.example.autoschool11.db.StatisticsDataBaseHelper;
import com.example.autoschool11.db.TrainingDataBaseHelper;
import com.example.autoschool11.theme_changer.Constant;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;


public class StatisticsFragment extends Fragment {

    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> dates;
    ArrayList<IntensityClass> intensityClassArrayList;
    TextView stat_available;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progressbarstat);
        ProgressBar ticketsprogBar = view.findViewById(R.id.progressbarstickets);
        ProgressBar progressBarthemes = view.findViewById(R.id.progressbarsthemes);
        TextView full_themes_count = view.findViewById(R.id.full_themes);
        TextView anscount = view.findViewById(R.id.anscount);
        TextView ticket20count = view.findViewById(R.id.tickets20count);
        BarChart barChart = view.findViewById(R.id.barChart);
        stat_available = view.findViewById(R.id.stat_available);
        ProgressBar circle_pb = view.findViewById(R.id.circle_pg);
        TextView percentage = view.findViewById(R.id.percent_prepare);
        DayStatisticsDataBaseHelper dayStatisticsDataBaseHelper = new DayStatisticsDataBaseHelper(getContext());
        intensityClassArrayList = dayStatisticsDataBaseHelper.getStatisticsData();
        barEntryArrayList = new ArrayList<>();
        dates = new ArrayList<>();
        for (int i = 0; i < intensityClassArrayList.size(); i++) {
            String date = intensityClassArrayList.get(i).getDate();
            int result = intensityClassArrayList.get(i).getResult();
            Log.d("res", String.valueOf(result));
            barEntryArrayList.add(new BarEntry(i, result));
            dates.add(date);
        }

        if (dates.size() != 0){
            stat_available.setVisibility(View.INVISIBLE);
        }

        StatisticsDataBaseHelper dataBaseHelper = new StatisticsDataBaseHelper(getContext());

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Ответы");
        if (String.valueOf(Constant.color).equals("0")) {
            barDataSet.setColors(0xffF44336);
        } else {
            barDataSet.setColors(MainActivity.getThemeColor());
        }
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "" + ((int) value);
            }
        };
        barData.setValueFormatter(formatter);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(1000);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(300);
        barChart.getAxisRight().setEnabled(false);


        YAxis left = barChart.getAxisLeft();
        left.setGranularity(1);

        TrainingDataBaseHelper trainingDataBaseHelper = new TrainingDataBaseHelper(getContext());

        progressBarthemes.setMax(28);
        progressBarthemes.setProgress(dataBaseHelper.getFullThemesCount());
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBarthemes, 0, dataBaseHelper.getFullThemesCount());
        anim.setDuration(1000);
        progressBarthemes.startAnimation(anim);
        full_themes_count.setText(Integer.toString(dataBaseHelper.getFullThemesCount()) + "/28");


        progressBar.setMax(800);
        progressBar.setProgress(trainingDataBaseHelper.getKnowingCount());
        anim = new ProgressBarAnimation(progressBar, 0, trainingDataBaseHelper.getKnowingCount());
        anim.setDuration(1000);
        progressBar.startAnimation(anim);
        anscount.setText(trainingDataBaseHelper.getKnowingCount() + "/800");


        ticketsprogBar.setMax(40);
        ticketsprogBar.setProgress(dataBaseHelper.get20Tickets());
        anim = new ProgressBarAnimation(ticketsprogBar, 0, dataBaseHelper.get20Tickets());
        anim.setDuration(1000);
        ticketsprogBar.startAnimation(anim);
        ticket20count.setText(dataBaseHelper.get20Tickets() + "/40");




        Double d = trainingDataBaseHelper.getKnowingCount() / 800.0 * 100;
        Integer i = d.intValue();
        percentage.setText(i + "%");

        circle_pb.setProgress(i);
        circle_pb.setMax(100);
        anim = new ProgressBarAnimation(circle_pb, 0, i);
        anim.setDuration(1000);
        circle_pb.startAnimation(anim);
        return view;
    }
}