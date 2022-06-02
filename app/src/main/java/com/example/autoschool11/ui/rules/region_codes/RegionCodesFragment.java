package com.example.autoschool11.ui.rules.region_codes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.R;
import com.github.barteksc.pdfviewer.PDFView;


public class RegionCodesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_region_codes, container, false);
        PDFView pdfView = view.findViewById(R.id.pdfviewregioncodes);
        pdfView.fromAsset("Автомобильные коды регионов.pdf").load();
        return view;
    }
}