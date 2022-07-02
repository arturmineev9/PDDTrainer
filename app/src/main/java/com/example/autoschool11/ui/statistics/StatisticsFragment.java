package com.example.autoschool11.ui.statistics;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.MainActivity;
import com.example.autoschool11.animation.ProgressBarAnimation;
import com.example.autoschool11.databinding.FragmentStatisticsBinding;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.db.db_classes.IntensityClass;
import com.example.autoschool11.theme_changer.ThemeColor;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;


public class StatisticsFragment extends Fragment {

    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> dates;
    ArrayList<IntensityClass> intensityClassArrayList;
    ArrayList<PieEntry> pieEntryArrayList;
    protected FragmentStatisticsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        DataBaseHelper databaseHelper = new DataBaseHelper(getContext());
        intensityClassArrayList = databaseHelper.getStatisticsData();
        barEntryArrayList = new ArrayList<>();
        dates = new ArrayList<>();


        // график интенсивности barChart

        for (int i = 0; i < intensityClassArrayList.size(); i++) {
            String date = intensityClassArrayList.get(i).getDate();
            int result = intensityClassArrayList.get(i).getResult();
            barEntryArrayList.add(new BarEntry(i, result));
            dates.add(date);
        }
        binding.barChart.setVisibility(View.INVISIBLE);
        if (dates.size() != 0) {
            binding.statAvailable.setVisibility(View.INVISIBLE);
            binding.barChart.setVisibility(View.VISIBLE);
        }

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Ответы");
        if (String.valueOf(ThemeColor.color).equals("0")) {
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
        binding.barChart.setFitBars(true);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "font.ttf");
        barData.setValueTypeface(font);
        binding.barChart.setData(barData);
        binding.barChart.getDescription().setText("");
        binding.barChart.animateY(1000);

        XAxis xAxis = binding.barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(300);
        binding.barChart.getAxisRight().setEnabled(false);


        YAxis left = binding.barChart.getAxisLeft();
        left.setGranularity(1);

        //  график успеваемости pieChart

        binding.pieChart.setVisibility(View.VISIBLE);
        binding.statAvailable1.setVisibility(View.INVISIBLE);
        if (dataBaseHelper.isTableEmpty()){
            binding.statAvailable1.setVisibility(View.VISIBLE);
            binding.pieChart.setVisibility(View.INVISIBLE);
        }
        pieEntryArrayList = dataBaseHelper.getPieChartStatistics();
        PieDataSet pieDataSet = new PieDataSet(pieEntryArrayList, null);
        pieDataSet.setColors(Color.GREEN, Color.RED);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTypeface(font);
        pieData.setValueFormatter(formatter);
        pieData.setValueTextSize(20f);
        pieData.setValueTextColor(Color.BLACK);

        binding.pieChart.setHoleRadius(0);
        binding.pieChart.setEntryLabelTypeface(font);
        binding.pieChart.getDescription().setText("");
        binding.pieChart.setRotationEnabled(false);
        binding.pieChart.setTransparentCircleAlpha(0);
        binding.pieChart.setDrawEntryLabels(false);
        binding.pieChart.setData(pieData);
        binding.pieChart.invalidate();

        // ProgressBar по темам
        binding.progressbarsthemes.setMax(28);
        binding.progressbarsthemes.setProgress(dataBaseHelper.getFullThemesCount());
        ProgressBarAnimation anim = new ProgressBarAnimation(binding.progressbarsthemes, 0, dataBaseHelper.getFullThemesCount());
        anim.setDuration(1000);
        binding.progressbarsthemes.startAnimation(anim);
        binding.fullThemes.setText(dataBaseHelper.getFullThemesCount() + "/28");

        // ProgressBar по вопросам
        binding.progressbarstat.setMax(800);
        binding.progressbarstat.setProgress(dataBaseHelper.getKnowingCount());
        anim = new ProgressBarAnimation(binding.progressbarstat, 0, dataBaseHelper.getKnowingCount());
        anim.setDuration(1000);
        binding.progressbarstat.startAnimation(anim);
        binding.anscount.setText(dataBaseHelper.getKnowingCount() + "/800");

        // ProgressBar по билетам
        binding.progressbarstickets.setMax(40);
        binding.progressbarstickets.setProgress(dataBaseHelper.get20Tickets());
        anim = new ProgressBarAnimation(binding.progressbarstickets, 0, dataBaseHelper.get20Tickets());
        anim.setDuration(1000);
        binding.progressbarstickets.startAnimation(anim);
        binding.tickets20count.setText(dataBaseHelper.get20Tickets() + "/40");

        // ProgressBar прогресса
        Double d = dataBaseHelper.getKnowingCount() / 800.0 * 100;
        Integer i = d.intValue();
        binding.percentPrepare.setText(i + "%");

        binding.circlePg.setProgress(i);
        binding.circlePg.setMax(100);
        anim = new ProgressBarAnimation(binding.circlePg, 0, i);
        anim.setDuration(1000);
        binding.circlePg.startAnimation(anim);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}