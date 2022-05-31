package com.example.autoschool11.ui.rules.examinfo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.adapters.ButtonAdapter;
import com.example.autoschool11.R;


public class ExamInfoFragment extends Fragment implements ButtonAdapter.ButtonClickListener {
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_info, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rulesRV);
        String[] rules = getResources().getStringArray(R.array.examinfo);
        ButtonAdapter buttonAdapter = new ButtonAdapter(rules, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(buttonAdapter);
        return view;
    }

    @Override
    public void onButtonClick(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putString("exam_info", "ExamInfo/1. Документы для сдачи на права.pdf");
                break;
            case 1:
                bundle.putString("exam_info", "ExamInfo/2. Медосмотр.pdf");
                break;
            case 2:
                bundle.putString("exam_info", "ExamInfo/3. Категории водительских удостоверений.pdf");
                break;
            case 3:
                bundle.putString("exam_info", "ExamInfo/4. Проведение теоретического экзамена.pdf");
                break;
            case 4:
                bundle.putString("exam_info", "ExamInfo/5. Проведение экзамена по первоначальным навыкам управления транспортным средством.pdf");
                break;
            case 5:
                bundle.putString("exam_info", "ExamInfo/6. Проведение экзамена по управлению транспортным средством в условиях дорожного движения.pdf");
                break;
            case 6:
                bundle.putString("exam_info", "ExamInfo/7. Контрольная таблица для экзамена по управлению транспортным средством в условиях дорожного движения.pdf");
                break;
        }
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.examInfoFragment1, bundle);
    }
}