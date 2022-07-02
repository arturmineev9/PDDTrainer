package com.example.autoschool11.ui.theory.DPSTalking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.autoschool11.databinding.FragmentTalkingPdfBinding;


public class TalkingFragmentPDF extends Fragment {

    protected String pdfFile;
    protected FragmentTalkingPdfBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pdfFile = getArguments().getString("dialog");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTalkingPdfBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.pdftalking1.fromAsset(pdfFile).load();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}