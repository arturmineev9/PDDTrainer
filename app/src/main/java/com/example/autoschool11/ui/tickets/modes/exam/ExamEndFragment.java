package com.example.autoschool11.ui.tickets.modes.exam;

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
import com.example.autoschool11.databinding.FragmentExamEndBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ExamEndFragment extends Fragment {

    int type_of_fail;
    int countans;
    int countquestions;
    protected FragmentExamEndBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type_of_fail = getArguments().getInt("type_of_fail");
            countans = getArguments().getInt("countans");
            countquestions = getArguments().getInt("countquestions");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExamEndBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        if (type_of_fail == 0) {
            binding.resulttxt.setText("Вы сдали экзамен!");
            binding.imageresultsexam.setImageResource(R.drawable.success);
        } else if (type_of_fail == 1) {
            binding.resulttxt.setText("Экзамен не сдан! Вы допустили более 2-ух ошибок.");
            binding.imageresultsexam.setImageResource(R.drawable.fail);
        } else if (type_of_fail == 2) {
            binding.resulttxt.setText("Экзамен не сдан! Вы допустили ошибку на дополнительных вопросах.");
            binding.imageresultsexam.setImageResource(R.drawable.fail);
        } else if (type_of_fail == 3) {
            binding.resulttxt.setText("Время вышло!");
            binding.imageresultsexam.setImageResource(R.drawable.fail);
        }
        binding.results.setText(countans + "/" + countquestions);

        binding.backbuttonexam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_tickets);
                BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
                navBar.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}