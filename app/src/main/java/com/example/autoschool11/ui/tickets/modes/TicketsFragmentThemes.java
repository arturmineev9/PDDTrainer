package com.example.autoschool11.ui.tickets.modes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.DbButtonAdapter;
import com.example.autoschool11.adapters.ThemeTicketsAdapter;
import com.example.autoschool11.adapters.Ticket40Adapter;
import com.example.autoschool11.db.StatisticsDataBaseHelper;
import com.example.autoschool11.ui.tickets.TicketThemes;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TicketsFragmentThemes extends Fragment implements ThemeTicketsAdapter.TicketButtonClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tickets_themes, container, false);
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);
        int[] amount = {28, 11, 6, 122, 38, 34, 9, 112, 22, 18, 40, 39, 120, 3, 10, 13, 9, 7, 19, 8, 1, 4, 3, 2, 26, 59, 20, 17};
        RecyclerView recyclerView = view.findViewById(R.id.ticketsthemesRV);
        String[] ticketsthemes = getResources().getStringArray(R.array.themes_names);
        StatisticsDataBaseHelper dataBaseHelper = new StatisticsDataBaseHelper(getContext());
        String[] results = dataBaseHelper.getThemesResults();
        ThemeTicketsAdapter buttonAdapter = new ThemeTicketsAdapter(ticketsthemes, results, amount, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(buttonAdapter);
        return view;
    }

    @Override
    public void onButtonClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("category", position + 1);
        DbButtonAdapter.setCountans(0);
        TicketThemes.setI(0);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.ticketThemes, bundle);
    }
}
