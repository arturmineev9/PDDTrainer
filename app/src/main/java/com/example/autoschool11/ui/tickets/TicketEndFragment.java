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
import com.example.autoschool11.databinding.FragmentTicketEndBinding;
import com.example.autoschool11.db.DataBaseHelper;
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
    int isTraining;
    protected FragmentTicketEndBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            countans = getArguments().getInt("countans");
            countquestions = getArguments().getInt("countquestions");
            ticket_number = getArguments().getInt("ticket_number");
            is_themes = getArguments().getInt("is_themes");
            category = getArguments().getInt("category");
            isTraining = getArguments().getInt("isTraining");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTicketEndBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        if (isTraining == 1) {
            binding.successfultxt.setText("Хорошая работа, продолжайте тренироваться!");
            binding.imageresults.setImageResource(R.drawable.success);
        } else {
            if (countans < countquestions - 2) {
                binding.imageresults.setImageResource(R.drawable.fail);
                binding.successfultxt.setText("Билет не сдан.");
            } else if (countans == countquestions - 2 || countans == countquestions - 1) {
                binding.successfultxt.setText("Билет пройден успешно!");
                binding.imageresults.setImageResource(R.drawable.success);
            } else {
                binding.imageresults.setImageResource(R.drawable.success);
                binding.successfultxt.setText("Билет пройден успешно!");
                binding.results.setText(countans + "/" + countquestions);
            }}
            binding.backbutton.setOnClickListener(view1 -> {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                if (isTraining == 1){
                    navController.navigate(R.id.trainingFragment);
                } else {
                    navController.navigate(R.id.navigation_tickets);
                }
                BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
                navBar.setVisibility(View.VISIBLE);
            });
            DataBaseHelper databaseHelper = new DataBaseHelper(getContext());
            databaseHelper.insertDayResults(getDate(), countquestions);

            if (is_themes == 0) {
                databaseHelper.insertResults(ticket_number, countans);
            } else {
                databaseHelper.insertThemeResults(category, countans);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}