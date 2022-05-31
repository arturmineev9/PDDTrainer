package com.example.autoschool11.ui.tickets;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.autoschool11.R;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.db.DayStatisticsDataBaseHelper;
import com.example.autoschool11.db.StatisticsDataBaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TicketEndFragment extends Fragment {
    int countans;
    int ticket_number;
    int countquestions;
    int is_themes;
    int category;
    TextView results;
    ImageView imageresults;
    Button btnback;
    TextView successful_txt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            countans = getArguments().getInt("countans");
            countquestions = getArguments().getInt("countquestions");
            ticket_number = getArguments().getInt("ticket_number");
            is_themes = getArguments().getInt("is_themes");
            category = getArguments().getInt("category");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_end, container, false);
        results = view.findViewById(R.id.results);
        imageresults = view.findViewById(R.id.imageresults);
        btnback = view.findViewById(R.id.backbutton);
        successful_txt = view.findViewById(R.id.successfultxt);

        if (countans < countquestions - 2){
            imageresults.setImageResource(R.drawable.fail);
            successful_txt.setText("Билет не сдан.");
        }
        else if (countans == countquestions - 2 || countans == countquestions - 1) {
            successful_txt.setText("Билет пройден успешно!");
            imageresults.setImageResource(R.drawable.success);
        }
        else {
            imageresults.setImageResource(R.drawable.success);
            successful_txt.setText("Билет пройден успешно!");
            results.setText(Integer.toString(countans) + "/" + countquestions);
        }
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_tickets);
                BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
                navBar.setVisibility(View.VISIBLE);
            }
        });
        StatisticsDataBaseHelper dataBaseHelper = new StatisticsDataBaseHelper(getContext());
        DayStatisticsDataBaseHelper dayStatisticsDataBaseHelper = new DayStatisticsDataBaseHelper(getContext());
        dayStatisticsDataBaseHelper.insertDayResults(getDate(), countquestions);

        if (is_themes == 0){
            dataBaseHelper.insertResults(ticket_number, countans);
        } else {
            dataBaseHelper.insertThemeResults(category, countans);
        }

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);
        return view;
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String cd = dateFormat.format(date);
        return cd;
    }
}