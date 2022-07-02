package com.example.autoschool11.ui.theory.fines;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.databinding.FragmentFinesBinding;


public class FinesFragment extends Fragment {

    protected FragmentFinesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFinesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.pdfviewfines.fromAsset("PDD/Штрафы.pdf").load();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}