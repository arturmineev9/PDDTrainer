package com.example.autoschool11.ui.theory.examinfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.databinding.FragmentExamInfoPdfBinding;


public class ExamInfoFragmentPDF extends Fragment {

    protected String pdfFile;
    protected FragmentExamInfoPdfBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pdfFile = getArguments().getString("exam_info");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExamInfoPdfBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.pdfviewexaminfo1.fromAsset(pdfFile).load();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}