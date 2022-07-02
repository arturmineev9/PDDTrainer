package com.example.autoschool11.ui.theory;

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
import com.example.autoschool11.adapters.TheoryAdapter;
import com.example.autoschool11.databinding.FragmentRulesBottomBinding;


public class RulesFragment extends Fragment implements TheoryAdapter.HomeClickListener, View.OnClickListener {

    Context context;
    protected FragmentRulesBottomBinding binding;
    int[] images = {R.drawable.ic_action_fines1, R.drawable.ic_action_signs1, R.drawable.ic_action_medicine, R.drawable.ic_action_police,R.drawable.ic_action_question,  R.drawable.ic_action_plate12};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRulesBottomBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        binding.pddCard.setOnClickListener(this);
        String[] home = getResources().getStringArray(R.array.home);
        binding.homeRV.setEnabled(false);
        TheoryAdapter theoryAdapter = new TheoryAdapter(home, images, this);
        binding.homeRV.setLayoutManager(gridLayoutManager);
        binding.homeRV.setAdapter(theoryAdapter);
        return view;
    }




    @Override
    public void onHomeClick(int position) { // обработка нажатия на элемент в списке
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        switch (position) {
            case 0:
                navController.navigate(R.id.finesFragment);
                break;
            case 1:
                navController.navigate(R.id.signsFragment);
                break;
            case 2:
                navController.navigate(R.id.medicineFragment);
                break;
            case 3:
                navController.navigate(R.id.talkingFragment);
                break;
            case 4:
                navController.navigate(R.id.examRulesFragment);
                break;
            case 5:
                navController.navigate(R.id.regionCodesFragment);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.rulesFragmentPDD2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}