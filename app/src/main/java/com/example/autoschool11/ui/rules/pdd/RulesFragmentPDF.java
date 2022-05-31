package com.example.autoschool11.ui.rules.pdd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.autoschool11.R;
import com.github.barteksc.pdfviewer.PDFView;


public class RulesFragmentPDF extends Fragment {
    public String pdfFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pdfFile = getArguments().getString("rules");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rules1, container, false);
        PDFView pdfView = view.findViewById(R.id.pdfview1);

        pdfView.fromAsset(pdfFile).load();

        return view;
    }

    public String getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(String pdfFile) {
        this.pdfFile = pdfFile;
    }
}