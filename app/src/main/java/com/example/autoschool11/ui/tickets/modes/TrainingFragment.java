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
import android.widget.Toast;

import com.example.autoschool11.animation.ProgressBarAnimation;
import com.example.autoschool11.R;
import com.example.autoschool11.databinding.FragmentTrainingBinding;
import com.example.autoschool11.db.DataBaseHelper;

// Умная тренировка
public class TrainingFragment extends Fragment implements View.OnClickListener {

    protected FragmentTrainingBinding binding;
    String knowing = "knowing";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrainingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        binding.notKnowCard.setOnClickListener(this);
        binding.knowCard.setOnClickListener(this);
        binding.knowGoodCard.setOnClickListener(this);
        binding.knowAwesomeCard.setOnClickListener(this);
        binding.countNotKnow.setText(dataBaseHelper.getCountQuestions(1));
        binding.countKnow.setText(dataBaseHelper.getCountQuestions(2));
        binding.countKnowGood.setText(dataBaseHelper.getCountQuestions(3));
        binding.countKnowAwesome.setText(dataBaseHelper.getCountQuestions(4));

        // ProgressBar
        binding.circleTraining.setMax(800);
        binding.circleTraining.setProgress(Integer.parseInt(dataBaseHelper.getCountQuestions(4)));
        ProgressBarAnimation anim = new ProgressBarAnimation(binding.circleTraining, 0, Integer.parseInt(dataBaseHelper.getCountQuestions(4)));
        anim.setDuration(1000);
        binding.circleTraining.startAnimation(anim);
        binding.otlichnoCount.setText(dataBaseHelper.getCountQuestions(4));
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);

        binding.trainingBtn.setOnClickListener(view1 -> { // обработка нажатия кнопки "Начать тренировку"
            Bundle bundle = new Bundle();

            if (dataBaseHelper.getTrainingTableLength(1) != 0)
                bundle.putInt(knowing, 1);
            else if (dataBaseHelper.getTrainingTableLength(2) != 0)
                bundle.putInt(knowing, 2);
            else if (dataBaseHelper.getTrainingTableLength(3) != 0)
                bundle.putInt(knowing, 3);
            else bundle.putInt(knowing, 4);
            TrainingFragmentSolution.setQuestion_number(1);
            navController.navigate(R.id.trainingFragmentSolution, bundle);
        });
        return view;
    }

    @Override
    public void onClick(View view) { // обработка нажатия на категории знаний
        DataBaseHelper trainingDataBaseHelper = new DataBaseHelper(getContext());
        Bundle bundle = new Bundle();
        int knowing_id = 0;
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        switch (view.getId()) {
            case R.id.not_know_card:
                bundle.putInt(knowing, 1);
                knowing_id = 1;
                break;
            case R.id.know_card:
                bundle.putInt(knowing, 2);
                knowing_id = 2;
                break;
            case R.id.know_good_card:
                bundle.putInt(knowing, 3);
                knowing_id = 3;
                break;
            case R.id.know_awesome_card:
                bundle.putInt(knowing, 4);
                knowing_id = 4;
                break;
        }
        if (trainingDataBaseHelper.getTrainingTableLength(knowing_id) != 0) {
            TrainingFragmentSolution.setQuestion_number(1);
            navController.navigate(R.id.trainingFragmentSolution, bundle);
        } else
            Toast.makeText(getContext(), "В этой категории нет вопросов.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}