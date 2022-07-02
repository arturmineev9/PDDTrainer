package com.example.autoschool11.ui.theory.examinfo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.adapters.ButtonAdapter;
import com.example.autoschool11.R;
import com.example.autoschool11.databinding.FragmentExamInfoBinding;


public class ExamInfoFragment extends Fragment implements ButtonAdapter.ButtonClickListener {

    Context context;
    private FragmentExamInfoBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExamInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        String[] rules = getResources().getStringArray(R.array.examinfo);
        ButtonAdapter buttonAdapter = new ButtonAdapter(rules, this);
        binding.rulesRV.setLayoutManager(new LinearLayoutManager(context));
        binding.rulesRV.setAdapter(buttonAdapter);
        return view;
    }

    @Override
    public void onButtonClick(int position) { // обработка нажатия на элемент в списке
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}