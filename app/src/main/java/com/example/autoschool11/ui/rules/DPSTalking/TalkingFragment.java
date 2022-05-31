package com.example.autoschool11.ui.rules.DPSTalking;

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


public class TalkingFragment extends Fragment implements ButtonAdapter.ButtonClickListener {
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_talking, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rulesRV);
        String[] dialog = getResources().getStringArray(R.array.dps_talking);
        ButtonAdapter buttonAdapter = new ButtonAdapter(dialog, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(buttonAdapter);
        return view;
    }

    @Override
    public void onButtonClick(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putString("dialog", "Talking/1. Взаимоотношения сотрудников с участниками дорожного движения.pdf");
                break;
            case 1:
                bundle.putString("dialog", "Talking/2. Остановка транспортного средства.pdf");
                break;
            case 2:
                bundle.putString("dialog", "Talking/3. Составление протокола об административном правонарушении.pdf");
                break;
            case 3:
                bundle.putString("dialog", "Talking/4. Отстранение от управления транспортным средством.pdf");
                break;
            case 4:
                bundle.putString("dialog", "Talking/5. Освидетельствование на состояние алкогольного опьянения.pdf");
                break;
            case 5:
                bundle.putString("dialog", "Talking/6. Направление на медицинское освидетельствование на состояние опьянения.pdf");
                break;
            case 6:
                bundle.putString("dialog", "Talking/7. Изъятие водительского удостоверения.pdf");
                break;
            case 7:
                bundle.putString("dialog", "Talking/8. Задержание транспортного средства.pdf");
                break;
            case 8:
                bundle.putString("dialog", "Talking/9. Досмотр транспортного средства.pdf");
                break;
            case 9:
                bundle.putString("dialog", "Talking/10. Правила возврата ВУ после утраты оснований прекращения действия права на управление транспортными средствами.pdf");
                break;
            case 10:
                bundle.putString("dialog", "Talking/11. Изменения.pdf");
                break;
        }
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.talkingFragment1, bundle);
    }
}