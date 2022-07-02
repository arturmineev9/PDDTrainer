package com.example.autoschool11.ui.theory.medicine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.databinding.FragmentMedicinePdfBinding;


public class MedicineFragmentPDF extends Fragment {

    protected String pdfFile;
    protected FragmentMedicinePdfBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pdfFile = getArguments().getString("medicine");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMedicinePdfBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.pdfviewmed1.fromAsset(pdfFile).load();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}