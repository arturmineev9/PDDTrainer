package com.example.autoschool11.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoschool11.R;

public class SignsAdapter extends RecyclerView.Adapter<SignsAdapter.ViewHolder> { //адаптер recyclerview для знаков дорожного движения
    String[] signs;
    int[] images;
    public SignsClickListener msignsClickListener;

    public SignsAdapter(String[] signs, int[] images, SignsClickListener msignsClickListener) {
        this.signs = signs;
        this.images = images;
        this.msignsClickListener=  msignsClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.signs_row, viewGroup, false);
        return new ViewHolder(view, msignsClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.sign_type.setText(signs[position]);
        holder.sign_image.setImageResource(images[position]);
    }


    @Override
    public int getItemCount() {
        return signs.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView sign_type;
        ImageView sign_image;
        SignsAdapter.SignsClickListener signsClickListener;

        public ViewHolder(View view, SignsAdapter.SignsClickListener signsClickListener) {
            super(view);
            sign_type = view.findViewById(R.id.sign_type);
            sign_image = view.findViewById(R.id.sign_image);
            this.signsClickListener = signsClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            signsClickListener.onSignClick(getAdapterPosition());
        }
    }

    public interface SignsClickListener {
        void onSignClick(int position);
    }
}

