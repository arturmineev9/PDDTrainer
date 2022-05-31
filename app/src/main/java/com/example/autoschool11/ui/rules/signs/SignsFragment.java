package com.example.autoschool11.ui.rules.signs;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.SignsAdapter;


public class SignsFragment extends Fragment implements SignsAdapter.SignsClickListener {
    Context context;

    int[] images = {R.drawable.signs11, R.drawable.signs2, R.drawable.signs31, R.drawable.signs4, R.drawable.signs5, R.drawable.signs6, R.drawable.signs7, R.drawable.signs8, R.drawable.signs9, R.drawable.signs10};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signs, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.signsRV);
        String[] signs = getResources().getStringArray(R.array.road_signs);
        SignsAdapter signsAdapter = new SignsAdapter(signs, images, this);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(signsAdapter);
        return view;
    }


    @Override
    public void onSignClick(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putString("road_signs", "Signs/1. Предупреждающие знаки.pdf");
                break;
            case 1:
                bundle.putString("road_signs", "Signs/2. Знаки приоритета.pdf");
                break;
            case 2:
                bundle.putString("road_signs", "Signs/3. Запрещающие знаки.pdf");
                break;
            case 3:
                bundle.putString("road_signs", "Signs/4. Предписывающие знаки.pdf");
                break;
            case 4:
                bundle.putString("road_signs", "Signs/5. Знаки особых предписаний.pdf");
                break;
            case 5:
                bundle.putString("road_signs", "Signs/6. Информационные знаки.pdf");
                break;
            case 6:
                bundle.putString("road_signs", "Signs/7. Знаки сервиса.pdf");
                break;
            case 7:
                bundle.putString("road_signs", "Signs/8. Знаки дополнительной информации (таблички).pdf");
                break;
            case 8:
                bundle.putString("road_signs", "Signs/9. Горизонтальная разметка.pdf");
                break;
            case 9:
                bundle.putString("road_signs", "Signs/10. Вертикальная разметка.pdf");
                break;

        }
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.signsFragmentVertical, bundle);
    }
    }