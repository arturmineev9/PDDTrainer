package com.example.autoschool11.ui.tickets;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.Ticket40Adapter;
import com.example.autoschool11.adapters.DbButtonAdapter;
import com.example.autoschool11.db.StatisticsDataBaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class TicketsFragment40 extends Fragment implements Ticket40Adapter.TicketButtonClickListener {
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tickets40, container, false);
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = view.findViewById(R.id.ticketsRV);
        String[] tickets = getResources().getStringArray(R.array.tickets);
        StatisticsDataBaseHelper dataBaseHelper = new StatisticsDataBaseHelper(getContext());
        String[] results = dataBaseHelper.getResults();
        Ticket40Adapter buttonAdapter = new Ticket40Adapter(tickets,results, this);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(buttonAdapter);
        return view;
    }

    @Override
    public void onButtonClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("ticketa", position + 1);
        switch (position) {
            case 0:
                bundle.putString("ticket", "01");
                bundle.putInt("ticketstart", 1);
                bundle.putInt("ticketend", 20);
                break;
            case 1:
                bundle.putString("ticket", "02");
                bundle.putInt("ticketstart", 21);
                bundle.putInt("ticketend", 40);
                break;
            case 2:
                bundle.putString("ticket", "03");
                bundle.putInt("ticketstart", 41);
                bundle.putInt("ticketend", 60);
                break;
            case 3:
                bundle.putString("ticket", "04");
                bundle.putInt("ticketstart", 61);
                bundle.putInt("ticketend", 80);
                break;
            case 4:
                bundle.putString("ticket", "05");
                bundle.putInt("ticketstart", 81);
                bundle.putInt("ticketend", 100);
                break;
            case 5:
                bundle.putString("ticket", "06");
                bundle.putInt("ticketstart", 101);
                bundle.putInt("ticketend", 120);
                break;
            case 6:
                bundle.putString("ticket", "07");
                bundle.putInt("ticketstart", 121);
                bundle.putInt("ticketend", 140);
                break;
            case 7:
                bundle.putString("ticket", "08");
                bundle.putInt("ticketstart", 141);
                bundle.putInt("ticketend", 160);
                break;

            case 8:
                bundle.putString("ticket", "09");
                bundle.putInt("ticketstart", 161);
                bundle.putInt("ticketend", 180);
                break;

            case 9:
                bundle.putString("ticket", "10");
                bundle.putInt("ticketstart", 181);
                bundle.putInt("ticketend", 200);
                break;
            case 10:
                bundle.putString("ticket", "11");
                bundle.putInt("ticketstart", 201);
                bundle.putInt("ticketend", 220);
                break;
            case 11:
                bundle.putString("ticket", "12");
                bundle.putInt("ticketstart", 221);
                bundle.putInt("ticketend", 240);
                break;
            case 12:
                bundle.putString("ticket", "13");
                bundle.putInt("ticketstart", 241);
                bundle.putInt("ticketend", 260);
                break;
            case 13:
                bundle.putString("ticket", "14");
                bundle.putInt("ticketstart", 261);
                bundle.putInt("ticketend", 280);
                break;
            case 14:
                bundle.putString("ticket", "15");
                bundle.putInt("ticketstart", 281);
                bundle.putInt("ticketend", 300);
                break;
            case 15:
                bundle.putString("ticket", "16");
                bundle.putInt("ticketstart", 301);
                bundle.putInt("ticketend", 320);
                break;
            case 16:
                bundle.putString("ticket", "17");
                bundle.putInt("ticketstart", 321);
                bundle.putInt("ticketend", 340);
                break;
            case 17:
                bundle.putString("ticket", "18");
                bundle.putInt("ticketstart", 341);
                bundle.putInt("ticketend", 360);
                break;
            case 18:
                bundle.putString("ticket", "19");
                bundle.putInt("ticketstart", 361);
                bundle.putInt("ticketend", 380);
                break;
            case 19:
                bundle.putString("ticket", "20");
                bundle.putInt("ticketstart", 381);
                bundle.putInt("ticketend", 400);
                break;
            case 20:
                bundle.putString("ticket", "21");
                bundle.putInt("ticketstart", 401);
                bundle.putInt("ticketend", 420);
                break;
            case 21:
                bundle.putString("ticket", "22");
                bundle.putInt("ticketstart", 421);
                bundle.putInt("ticketend", 440);
                break;
            case 22:
                bundle.putString("ticket", "23");
                bundle.putInt("ticketstart", 441);
                bundle.putInt("ticketend", 460);
                break;
            case 23:
                bundle.putString("ticket", "24");
                bundle.putInt("ticketstart", 461);
                bundle.putInt("ticketend", 480);
                break;
            case 24:
                bundle.putString("ticket", "25");
                bundle.putInt("ticketstart", 481);
                bundle.putInt("ticketend", 500);
                break;
            case 25:
                bundle.putString("ticket", "26");
                bundle.putInt("ticketstart", 501);
                bundle.putInt("ticketend", 520);
                break;
            case 26:
                bundle.putString("ticket", "27");
                bundle.putInt("ticketstart", 521);
                bundle.putInt("ticketend", 540);
                break;
            case 27:
                bundle.putString("ticket", "28");
                bundle.putInt("ticketstart", 541);
                bundle.putInt("ticketend", 560);
                break;
            case 28:
                bundle.putString("ticket", "29");
                bundle.putInt("ticketstart", 561);
                bundle.putInt("ticketend", 580);
                break;
            case 29:
                bundle.putString("ticket", "30");
                bundle.putInt("ticketstart", 581);
                bundle.putInt("ticketend", 600);
                break;
            case 30:
                bundle.putString("ticket", "31");
                bundle.putInt("ticketstart", 601);
                bundle.putInt("ticketend", 620);
                break;
            case 31:
                bundle.putString("ticket", "32");
                bundle.putInt("ticketstart", 621);
                bundle.putInt("ticketend", 640);
                break;
            case 32:
                bundle.putString("ticket", "33");
                bundle.putInt("ticketstart", 641);
                bundle.putInt("ticketend", 660);
                break;
            case 33:
                bundle.putString("ticket", "34");
                bundle.putInt("ticketstart", 661);
                bundle.putInt("ticketend", 680);
                break;
            case 34:
                bundle.putString("ticket", "35");
                bundle.putInt("ticketstart", 681);
                bundle.putInt("ticketend", 700);
                break;
            case 35:
                bundle.putString("ticket", "36");
                bundle.putInt("ticketstart", 701);
                bundle.putInt("ticketend", 720);
                break;
            case 36:
                bundle.putString("ticket", "37");
                bundle.putInt("ticketstart", 721);
                bundle.putInt("ticketend", 740);
                break;
            case 37:
                bundle.putString("ticket", "38");
                bundle.putInt("ticketstart", 741);
                bundle.putInt("ticketend", 760);
                break;
            case 38:
                bundle.putString("ticket", "39");
                bundle.putInt("ticketstart", 761);
                bundle.putInt("ticketend", 780);
                break;
            case 39:
                bundle.putString("ticket", "40");
                bundle.putInt("ticketstart", 781);
                bundle.putInt("ticketend", 800);
                break;

        }
        DbButtonAdapter.setCountans(0);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.ticket1, bundle);
    }
}