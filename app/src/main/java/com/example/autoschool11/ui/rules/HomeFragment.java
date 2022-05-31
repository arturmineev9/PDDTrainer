package com.example.autoschool11.ui.rules;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.HomeAdapter;


public class HomeFragment extends Fragment implements HomeAdapter.HomeClickListener, View.OnClickListener {
    Context context;
    private CardView pdd_card;

    int[] images = {R.drawable.ic_action_fines1, R.drawable.ic_action_signs1, R.drawable.ic_action_medicine, R.drawable.ic_action_police,R.drawable.ic_action_question,  R.drawable.ic_action_plate12};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rules_bottom, container, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        pdd_card = view.findViewById(R.id.pdd_card);
        pdd_card.setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.homeRV);
        String[] home = getResources().getStringArray(R.array.home);
        recyclerView.setEnabled(false);
        HomeAdapter homeAdapter = new HomeAdapter(home, images, this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(homeAdapter);
        return view;
    }




    @Override
    public void onHomeClick(int position) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
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
}