package com.example.autoschool11.ui.rules.DPSTalking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.R;
import com.github.barteksc.pdfviewer.PDFView;


public class TalkingFragmentPDF extends Fragment {

    public String pdfFile;

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
        View view = inflater.inflate(R.layout.fragment_talking1, container, false);
        PDFView pdfView = view.findViewById(R.id.pdftalking1);
        pdfView.fromAsset(pdfFile).load();
        return view;
    }
}