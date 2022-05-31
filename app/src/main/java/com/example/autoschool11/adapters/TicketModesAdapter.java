package com.example.autoschool11.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoschool11.R;

public class TicketModesAdapter extends RecyclerView.Adapter<TicketModesAdapter.ViewHolder> {
    String[] names;
    int[] images;
    public HomeClickListener mhomeClickListener;

    public TicketModesAdapter(String[] names, int[] images, HomeClickListener mhomeClickListener) {
        this.names = names;
        this.images = images;
        this.mhomeClickListener = mhomeClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ticket_modes_row, viewGroup, false);
        return new ViewHolder(view, mhomeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(names[position]);
        holder.image.setImageResource(images[position]);
    }


    @Override
    public int getItemCount() {
        return names.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageView image;
        HomeClickListener homeClickListener;

        public ViewHolder(View view, HomeClickListener homeClickListener) {
            super(view);
            name = view.findViewById(R.id.imagename);
            image = view.findViewById(R.id.imagehome);
            this.homeClickListener = homeClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            homeClickListener.onHomeClick(getAdapterPosition());
        }
    }

    public interface HomeClickListener {
        void onHomeClick(int position);
    }
}