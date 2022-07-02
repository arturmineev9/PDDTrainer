package com.example.autoschool11.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoschool11.R;

public class HorizontalButtonAdapter extends RecyclerView.Adapter<HorizontalButtonAdapter.ViewHolder> {
    String[] numbers;
    public HorizontalButtonClickListener mhorizontalButtonClickListener;
    static boolean isCorrect;

    public HorizontalButtonAdapter(String[] numbers, HorizontalButtonClickListener mhorizontalButtonClickListener) {
        this.numbers = numbers;
        this.mhorizontalButtonClickListener = mhorizontalButtonClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontalbutton_row, viewGroup, false);
        return new ViewHolder(view, mhorizontalButtonClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.btnhorizontal.setText(numbers[position]);
        holder.btnhorizontal.setOnClickListener(view -> holder.horizontalButtonClickListener.onHorizontalButtonClick(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return numbers.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView btnhorizontal;
        HorizontalButtonClickListener horizontalButtonClickListener;

        public ViewHolder(View view, HorizontalButtonClickListener horizontalButtonClickListener) {
            super(view);
            btnhorizontal = view.findViewById(R.id.horizontalbutton);
            this.horizontalButtonClickListener = horizontalButtonClickListener;

        }

    }

    public interface HorizontalButtonClickListener {
        void onHorizontalButtonClick(int position);
    }
}

