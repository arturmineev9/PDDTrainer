package com.example.autoschool11.ui.theory.signs;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.SignsAdapter;
import com.example.autoschool11.databinding.FragmentSignsBinding;


public class SignsFragment extends Fragment implements SignsAdapter.SignsClickListener {

    Context context;
    int[] images = {R.drawable.signs11, R.drawable.signs2, R.drawable.signs31, R.drawable.signs4, R.drawable.signs5, R.drawable.signs6, R.drawable.signs7, R.drawable.signs8, R.drawable.signs9, R.drawable.signs10};
    protected FragmentSignsBinding binding;
    protected String roadsigns = "road_signs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        String[] signs = getResources().getStringArray(R.array.road_signs);
        SignsAdapter signsAdapter = new SignsAdapter(signs, images, this);
        binding.signsRV.setLayoutManager(new GridLayoutManager(context, 2));
        binding.signsRV.setAdapter(signsAdapter);
        return view;
    }


    @Override
    public void onSignClick(int position) { // обработка нажатия на элемент в списке
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putString(roadsigns, "Signs/1. Предупреждающие знаки.pdf");
                break;
            case 1:
                bundle.putString(roadsigns, "Signs/2. Знаки приоритета.pdf");
                break;
            case 2:
                bundle.putString(roadsigns, "Signs/3. Запрещающие знаки.pdf");
                break;
            case 3:
                bundle.putString(roadsigns, "Signs/4. Предписывающие знаки.pdf");
                break;
            case 4:
                bundle.putString(roadsigns, "Signs/5. Знаки особых предписаний.pdf");
                break;
            case 5:
                bundle.putString(roadsigns, "Signs/6. Информационные знаки.pdf");
                break;
            case 6:
                bundle.putString(roadsigns, "Signs/7. Знаки сервиса.pdf");
                break;
            case 7:
                bundle.putString(roadsigns, "Signs/8. Знаки дополнительной информации (таблички).pdf");
                break;
            case 8:
                bundle.putString(roadsigns, "Signs/9. Горизонтальная разметка.pdf");
                break;
            case 9:
                bundle.putString(roadsigns, "Signs/10. Вертикальная разметка.pdf");
                break;

        }
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.signsFragmentVertical, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}