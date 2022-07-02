package com.example.autoschool11.ui.tickets;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.TicketModesAdapter;
import com.example.autoschool11.databinding.FragmentTicketsBinding;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.ui.tickets.modes.FinesTestFragment;
import com.example.autoschool11.ui.tickets.modes.HardQuestionFragment;
import com.example.autoschool11.ui.tickets.modes.MarathonFragment;
import com.example.autoschool11.ui.tickets.modes.MistakesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

// Тренировки
public class TicketsFragmentMain extends Fragment implements TicketModesAdapter.HomeClickListener {
    int[] images = {R.drawable.exam, R.drawable.training, R.drawable.hard_questions, R.drawable.favourites, R.drawable.mistakes, R.drawable.marathon, R.drawable.fines_test, R.drawable.speedlimit};
    protected FragmentTicketsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTicketsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);


        binding.buttontickets.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.ticketsFragment40);
        });

        binding.buttontthemestickets.setOnClickListener(view12 -> {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.ticketsFragmentThemes);
        });
        String[] tickets_main = getResources().getStringArray(R.array.ticket_main);
        TicketModesAdapter ticketModesAdapter = new TicketModesAdapter(tickets_main, images, this);
        binding.ticketmainRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.ticketmainRV.setAdapter(ticketModesAdapter);
        return view;
    }

    @Override
    public void onHomeClick(int position) { // обработка нажатия на элемент в списке
        DataBaseHelper databaseHelper = new DataBaseHelper(getContext());
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);

        switch (position) {
            case 0:
                navController.navigate(R.id.examFragment);
                break;
            case 1:
                navController.navigate(R.id.trainingFragment);
                break;
            case 2:
                navController.navigate(R.id.hardQuestionFragment);
                HardQuestionFragment.setI(1);
                break;
            case 3:
                if (databaseHelper.getTableLength() != 0) {
                    navController.navigate(R.id.favouritesFragment);
                } else
                    Toast.makeText(getContext(), "У вас нет избранных вопросов!", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                if (databaseHelper.getMistakesTableLength() != 0) {
                    navController.navigate(R.id.mistakesFragment);
                    MistakesFragment.setI(1);
                } else {
                    Toast.makeText(getContext(), "У вас нет ошибок!", Toast.LENGTH_SHORT).show();
                }
                break;
            case 5:
                navController.navigate(R.id.marathonFragment);
                MarathonFragment.setI(1);
                break;
            case 6:
                FinesTestFragment.setI(0);
                navController.navigate(R.id.finesTestFragment);
                break;
            case 7:
                navController.navigate(R.id.signsTypesTestFragment);
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
