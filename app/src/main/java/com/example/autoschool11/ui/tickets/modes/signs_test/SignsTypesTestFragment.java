package com.example.autoschool11.ui.tickets.modes.signs_test;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SignsTypesTestFragment extends Fragment implements SignsAdapter.SignsClickListener { // выбор категроии знаков

    Context context;
    int[] images = {R.drawable.signs11, R.drawable.signs2, R.drawable.signs31, R.drawable.signs4, R.drawable.signs5, R.drawable.signs6, R.drawable.signs7, R.drawable.signs8};
    protected FragmentSignsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);
        String[] signs = getResources().getStringArray(R.array.road_signs_test);
        SignsAdapter signsAdapter = new SignsAdapter(signs, images, this);
        binding.signsRV.setLayoutManager(new GridLayoutManager(context, 2));
        binding.signsRV.setAdapter(signsAdapter);

        return view;
    }

    @Override
    public void onSignClick(int position) { // обработка нажатия на элемент в списке
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putInt("signs_id", position + 1);
        navController.navigate(R.id.signsTestFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}