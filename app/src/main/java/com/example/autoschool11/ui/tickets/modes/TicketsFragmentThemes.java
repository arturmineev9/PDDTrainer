package com.example.autoschool11.ui.tickets.modes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.AnswersAdapter;
import com.example.autoschool11.adapters.ThemeTicketsAdapter;
import com.example.autoschool11.databinding.FragmentTicketsThemesBinding;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.ui.tickets.TicketThemes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
// выбор темы
public class TicketsFragmentThemes extends Fragment implements ThemeTicketsAdapter.TicketButtonClickListener {

    protected FragmentTicketsThemesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTicketsThemesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);

        int[] amount = {28, 11, 6, 122, 38, 34, 9, 112, 22, 18, 40, 39, 120, 3, 10, 13, 9, 7, 19, 8, 1, 4, 3, 2, 26, 59, 20, 17};
        String[] ticketsthemes = getResources().getStringArray(R.array.themes_names);
        DataBaseHelper databaseHelper = new DataBaseHelper(getContext());
        String[] results = databaseHelper.getThemesResults();
        ThemeTicketsAdapter buttonAdapter = new ThemeTicketsAdapter(ticketsthemes, results, amount, this);
        binding.ticketsthemesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.ticketsthemesRV.setAdapter(buttonAdapter);
        return view;
    }

    @Override
    public void onButtonClick(int position) { // обработка нажатия на элемент из списка
        Bundle bundle = new Bundle();
        bundle.putInt("category", position + 1);
        AnswersAdapter.setCountans(0);
        TicketThemes.setI(0);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.ticketThemes, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
