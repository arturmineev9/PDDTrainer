package com.example.autoschool11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.autoschool11.db.DayStatisticsDataBaseHelper;
import com.example.autoschool11.db.MistakesDataBaseHelper;
import com.example.autoschool11.db.StatisticsDataBaseHelper;
import com.example.autoschool11.db.TrainingDataBaseHelper;
import com.example.autoschool11.theme_changer.Constant;
import com.example.autoschool11.theme_changer.Methods;
import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPreferences, app_preferences;
    SharedPreferences.Editor editor;
    Button button;
    Methods methods;
    int appTheme;
    int themeColor;
    int appColor;
    Constant constant;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);
        themeColor = appColor;
        Constant.color = appColor;

        if (themeColor == 0) {
            setTheme(Constant.theme);
        } else if (appTheme == 0) {
            setTheme(Constant.theme);
        } else {
            setTheme(appTheme);
        }
        setContentView(R.layout.fragment_settings);


        methods = new Methods();
        button = (Button) findViewById(R.id.button_color);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        colorize();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorChooserDialog dialog = new ColorChooserDialog(SettingsActivity.this);
                dialog.setTitle("Select");
                dialog.setColorListener(new ColorListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        colorize();
                        Constant.color = color;
                        methods.setColorTheme();
                        editor.putInt("color", color);
                        editor.putInt("theme", Constant.theme);
                        editor.commit();
                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

                dialog.show();
            }
        });

        CardView stat_restart = findViewById(R.id.stat_restart_card);
        stat_restart.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void colorize() {
        ShapeDrawable d = new ShapeDrawable(new OvalShape());
        d.setBounds(58, 58, 58, 58);

        d.getPaint().setStyle(Paint.Style.FILL);
        if (String.valueOf(Constant.color).equals("0")) {
            d.getPaint().setColor(0xffF44336);
        } else {
            d.getPaint().setColor(Constant.color);
        }
        button.setBackground(d);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view) {
        StatisticsDataBaseHelper dataBaseHelper = new StatisticsDataBaseHelper(getBaseContext());
        DayStatisticsDataBaseHelper statisticsDataBaseHelper = new DayStatisticsDataBaseHelper(getBaseContext());
        MistakesDataBaseHelper mistakesDataBaseHelper = new MistakesDataBaseHelper(getBaseContext());
        TrainingDataBaseHelper trainingDataBaseHelper = new TrainingDataBaseHelper(getBaseContext());
        trainingDataBaseHelper.restartTrainingData();
        dataBaseHelper.restartStatisticsDB();
        mistakesDataBaseHelper.restartMistakes();
        statisticsDataBaseHelper.restartDayStatisticsDB();
        //DataBaseHelper dataBaseHelper1 = new DataBaseHelper(getBaseContext());
        //dataBaseHelper1.changeExplanation();
        dataBaseHelper.add10();
        trainingDataBaseHelper.addTraining2();
        trainingDataBaseHelper.addTraining3();
        trainingDataBaseHelper.addTraining4();
        statisticsDataBaseHelper.addDay();
        dataBaseHelper.addthemes();
        Toast.makeText(getBaseContext(), "Статистика сброшена!", Toast.LENGTH_SHORT).show();
    }
}
