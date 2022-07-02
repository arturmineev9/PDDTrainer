package com.example.autoschool11.ui.theory.region_codes;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.autoschool11.databinding.FragmentRegionCodesBinding;


public class RegionCodesFragment extends Fragment {

    protected FragmentRegionCodesBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegionCodesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.pdfviewregioncodes.fromAsset("Автомобильные коды регионов.pdf").load();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}