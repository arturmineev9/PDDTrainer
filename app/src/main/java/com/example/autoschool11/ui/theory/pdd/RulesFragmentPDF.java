package com.example.autoschool11.ui.theory.pdd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.autoschool11.databinding.FragmentRules1Binding;


public class RulesFragmentPDF extends Fragment {

    protected FragmentRules1Binding binding;
    protected String pdfFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pdfFile = getArguments().getString("theory");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRules1Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.pdfview1.fromAsset(pdfFile).load();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}