package com.example.autoschool11.ui.theory.signs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.databinding.FragmentSignsPdfBinding;


public class SignsFragmentPDF extends Fragment {

    protected String pdfFile;
    protected FragmentSignsPdfBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pdfFile = getArguments().getString("road_signs");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignsPdfBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.pdfviewvertical.fromAsset(pdfFile).load();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}