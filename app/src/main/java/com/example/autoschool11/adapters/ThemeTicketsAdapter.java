package com.example.autoschool11.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoschool11.R;

public class ThemeTicketsAdapter extends RecyclerView.Adapter<ThemeTicketsAdapter.ViewHolder> {
    String[] tickets;
    String[] results;
    int[] amount_of_questions;
    public TicketButtonClickListener mticketbuttonClickListener;

    public ThemeTicketsAdapter(String[] tickets, String[] results, int[] amount_of_questions, TicketButtonClickListener mticketbuttonClickListener) {
        this.tickets = tickets;
        this.results = results;
        this.amount_of_questions = amount_of_questions;
        this.mticketbuttonClickListener = mticketbuttonClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.theme_tickets_row, viewGroup, false);
        return new ViewHolder(view, mticketbuttonClickListener);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.btnticket.setText(tickets[position]);
        holder.res.setText(results[position] + "/" + amount_of_questions[position]);
        holder.progressBartheme.setMax(amount_of_questions[position]);
        holder.progressBartheme.setProgress(Integer.parseInt(results[position]));
    }

    @Override
    public int getItemCount() {
        return tickets.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView btnticket;
        TextView res;
        ProgressBar progressBartheme;
        TicketButtonClickListener ticketButtonClickListener;

        public ViewHolder(View view, TicketButtonClickListener ticketButtonClickListener) {
            super(view);
            btnticket = view.findViewById(R.id.ticketbuttonthemes);
            res = view.findViewById(R.id.themeanscount);
            progressBartheme = view.findViewById(R.id.progressbartheme);
            this.ticketButtonClickListener = ticketButtonClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ticketButtonClickListener.onButtonClick(getAdapterPosition());
        }
    }

    public interface TicketButtonClickListener {
        void onButtonClick(int position);
    }
}
