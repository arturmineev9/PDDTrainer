package com.example.autoschool11.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoschool11.R;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> {

    String[] rules;
    public  ButtonClickListener mbuttonClickListener;

    public ButtonAdapter(String[] rules, ButtonClickListener mbuttonClickListener) {
        this.rules = rules;
        this.mbuttonClickListener = mbuttonClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.button_row, viewGroup, false);
        return new ViewHolder(view, mbuttonClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.btn.setText(rules[position]);
        holder.btn_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.buttonClickListener.onButtonClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return rules.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView btn;
        CardView btn_card;
        ButtonClickListener buttonClickListener;

        public ViewHolder(View view, ButtonClickListener buttonClickListener) {
            super(view);
            btn = view.findViewById(R.id.recyclerbutton);
            btn_card = view.findViewById(R.id.btn_card);
            this.buttonClickListener = buttonClickListener;
        }
    }

    public interface ButtonClickListener {
        void onButtonClick(int position);
    }
}