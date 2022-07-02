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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.theme_changer.ThemeColor;
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
    ThemeColor constant;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);
        themeColor = appColor;
        ThemeColor.color = appColor;

        if (themeColor == 0) {
            setTheme(ThemeColor.theme);
        } else if (appTheme == 0) {
            setTheme(ThemeColor.theme);
        } else {
            setTheme(appTheme);
        }
        setContentView(R.layout.fragment_settings);


        methods = new Methods();
        button = findViewById(R.id.button_color);
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
                        if (color == 0xffffffff){
                            Toast.makeText(getBaseContext(), "Тема белого цвета на данный момент недоступна", Toast.LENGTH_SHORT).show();
                        } else {
                            colorize();
                            ThemeColor.color = color;
                            methods.setColorTheme();
                            editor.putInt("color", color);
                            editor.putInt("theme", ThemeColor.theme);
                            editor.commit();
                            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

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
        if (String.valueOf(ThemeColor.color).equals("0")) {
            d.getPaint().setColor(0xffF44336);
        } else {
            d.getPaint().setColor(ThemeColor.color);
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
        DataBaseHelper databaseHelper = new DataBaseHelper(getBaseContext());
        databaseHelper.restartTrainingData();
        databaseHelper.restartStatisticsDB();
        databaseHelper.restartMistakes();
        databaseHelper.restartDayStatisticsDB();
        databaseHelper.restartSuccessTable();

        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Статистика сброшена!", Toast.LENGTH_SHORT).show();
    }
}
