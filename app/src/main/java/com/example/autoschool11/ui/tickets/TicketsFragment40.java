package com.example.autoschool11.ui.tickets;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.Ticket40Adapter;
import com.example.autoschool11.adapters.AnswersAdapter;
import com.example.autoschool11.databinding.FragmentTickets40Binding;
import com.example.autoschool11.db.DataBaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

// Билеты
public class TicketsFragment40 extends Fragment implements Ticket40Adapter.TicketButtonClickListener {

    String ticket = "ticket";
    Context context;
    protected FragmentTickets40Binding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTickets40Binding.inflate(inflater, container, false);
        View view = binding.getRoot();

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);
        String[] tickets = getResources().getStringArray(R.array.tickets);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        String[] results = dataBaseHelper.getResults();
        Ticket40Adapter buttonAdapter = new Ticket40Adapter(tickets, results, this);
        binding.ticketsRV.setLayoutManager(new GridLayoutManager(context, 2));
        binding.ticketsRV.setAdapter(buttonAdapter);
        return view;
    }

    @Override
    public void onButtonClick(int position) { // обработка нажатия на элемент списка
        Bundle bundle = new Bundle();
        bundle.putInt("ticket_number", position + 1);
        bundle.putInt("ticketstart", position * 20 + 1);
        bundle.putInt("ticketend", (position + 1) * 20);
        bundle.putString(ticket, Integer.toString(position + 1));
        switch (position) {
            case 0:
                bundle.putString(ticket, "01");
                break;
            case 1:
                bundle.putString(ticket, "02");
                break;
            case 2:
                bundle.putString(ticket, "03");
                break;
            case 3:
                bundle.putString(ticket, "04");
                break;
            case 4:
                bundle.putString(ticket, "05");
                break;
            case 5:
                bundle.putString(ticket, "06");
                break;
            case 6:
                bundle.putString(ticket, "07");
                break;
            case 7:
                bundle.putString(ticket, "08");
                break;
            case 8:
                bundle.putString(ticket, "09");
                break;
        }
        AnswersAdapter.setCountans(0);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.ticket1, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}