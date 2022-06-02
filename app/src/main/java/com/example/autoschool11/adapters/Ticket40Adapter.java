package com.example.autoschool11.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoschool11.R;

public class Ticket40Adapter extends RecyclerView.Adapter<Ticket40Adapter.ViewHolder> {
    String[] tickets;
    String[] results;
    public TicketButtonClickListener mticketbuttonClickListener;

    public Ticket40Adapter(String[] tickets, String[] results, TicketButtonClickListener mticketbuttonClickListener) {
        this.tickets = tickets;
        this.results = results;
        this.mticketbuttonClickListener = mticketbuttonClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ticket40_row, viewGroup, false);
        return new ViewHolder(view, mticketbuttonClickListener);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.btnticket.setText(tickets[position]);
        holder.results.setText(results[position] + "/20");
        holder.progressBar.setMax(20);
        holder.progressBar.setProgress(Integer.parseInt(results[position]));
    }

    @Override
    public int getItemCount() {
        return tickets.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView btnticket;
        TextView results;
        ProgressBar progressBar;
        TicketButtonClickListener ticketButtonClickListener;

        public ViewHolder(View view, TicketButtonClickListener ticketButtonClickListener) {
            super(view);
            btnticket = view.findViewById(R.id.ticketbutton);
            results = view.findViewById(R.id.results1);
            progressBar = view.findViewById(R.id.progressbar);
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
