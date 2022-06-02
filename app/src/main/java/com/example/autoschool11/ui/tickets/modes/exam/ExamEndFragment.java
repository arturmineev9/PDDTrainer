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
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ExamEndFragment extends Fragment {
    int type_of_fail;
    int countans;
    int ticket_number;
    int countquestions;
    TextView result_txt;
    TextView result;
    ImageView imageresults;
    Button btnback;

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
        View view = inflater.inflate(R.layout.fragment_exam_end, container, false);
        result_txt = view.findViewById(R.id.resulttxt);
        result = view.findViewById(R.id.results);
        btnback = view.findViewById(R.id.backbuttonexam);
        imageresults = view.findViewById(R.id.imageresultsexam);
        if (type_of_fail == 0){
            result_txt.setText("Вы сдали экзамен!");
            imageresults.setImageResource(R.drawable.success);
        } else if (type_of_fail == 1){
            result_txt.setText("Экзамен не сдан! Вы допустили более 2-ух ошибок.");
            imageresults.setImageResource(R.drawable.fail);
        } else if (type_of_fail == 2) {
            result_txt.setText("Экзамен не сдан! Вы допустили ошибку на дополнительных вопросах.");
            imageresults.setImageResource(R.drawable.fail);
        } else if (type_of_fail == 3){
            result_txt.setText("Время вышло!");
            imageresults.setImageResource(R.drawable.fail);
        }
        result.setText(Integer.toString(countans) + "/" + countquestions);

        btnback.setOnClickListener(new View.OnClickListener() {
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
}