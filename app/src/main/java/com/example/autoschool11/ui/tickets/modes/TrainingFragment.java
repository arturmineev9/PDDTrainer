package com.example.autoschool11.ui.tickets.modes;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.autoschool11.ProgressBarAnimation;
import com.example.autoschool11.R;
import com.example.autoschool11.db.TrainingDataBaseHelper;


public class TrainingFragment extends Fragment implements View.OnClickListener {
    Button training_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training, container, false);
        TrainingDataBaseHelper dataBaseHelper = new TrainingDataBaseHelper(getContext());
        ProgressBar circle_training = view.findViewById(R.id.circle_training);
        CardView not_know_card = view.findViewById(R.id.not_know_card);
        CardView know_card = view.findViewById(R.id.know_card);
        CardView know_good_card = view.findViewById(R.id.know_good_card);
        CardView know_awesome_card = view.findViewById(R.id.know_awesome_card);
        TextView not_know = view.findViewById(R.id.count_not_know);
        TextView know = view.findViewById(R.id.count_know);
        TextView know_good = view.findViewById(R.id.count_know_good);
        TextView know_awesome = view.findViewById(R.id.count_know_awesome);
        TextView awesome_count = view.findViewById(R.id.otlichno_count);
        training_btn = view.findViewById(R.id.training_btn);
        not_know_card.setOnClickListener(this);
        know_card.setOnClickListener(this);
        know_good_card.setOnClickListener(this);
        know_awesome_card.setOnClickListener(this);
        not_know.setText(dataBaseHelper.getCountQuestions(1));
        know.setText(dataBaseHelper.getCountQuestions(2));
        know_good.setText(dataBaseHelper.getCountQuestions(3));
        know_awesome.setText(dataBaseHelper.getCountQuestions(4));

        circle_training.setMax(800);
        circle_training.setProgress(Integer.parseInt(dataBaseHelper.getCountQuestions(4)));
        ProgressBarAnimation anim = new ProgressBarAnimation(circle_training, 0, Integer.parseInt(dataBaseHelper.getCountQuestions(4)));
        anim.setDuration(1000);
        circle_training.startAnimation(anim);
        awesome_count.setText(dataBaseHelper.getCountQuestions(4));
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);

        training_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if (dataBaseHelper.getTrainingTableLength(1) >= 20){
                    bundle.putInt("knowing", 1);
                    TrainingFragmentSolution.setQuestion_number(1);
                    navController.navigate(R.id.trainingFragmentSolution, bundle);
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        switch (view.getId()){
            case R.id.not_know_card:
                bundle.putInt("knowing", 1);
                break;
            case R.id.know_card:
                bundle.putInt("knowing", 2);
                break;
            case R.id.know_good_card:
                bundle.putInt("knowing", 3);
                break;
            case R.id.know_awesome_card:
                bundle.putInt("knowing", 4);
                break;
        }
        TrainingFragmentSolution.setQuestion_number(1);
        navController.navigate(R.id.trainingFragmentSolution, bundle);
    }


}