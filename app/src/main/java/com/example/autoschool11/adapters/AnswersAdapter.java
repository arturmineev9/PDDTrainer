package com.example.autoschool11.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoschool11.R;
import com.example.autoschool11.db.db_classes.DbButtonClass;

import java.util.ArrayList;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {
    ArrayList<DbButtonClass> dbButtonClassArrayList;
    public DbButtonClickListener mdbbuttonClickListener;
    static int countans;

    public static int getCountans() {
        return countans;
    }

    public static void setCountans(int countans) {
        AnswersAdapter.countans = countans;
    }

    public AnswersAdapter(ArrayList<DbButtonClass> dbButtonClassArrayList, DbButtonClickListener mdbbuttonClickListener) {
        this.dbButtonClassArrayList = dbButtonClassArrayList;
        this.mdbbuttonClickListener = mdbbuttonClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dbbutton_row, viewGroup, false);
        return new ViewHolder(view, mdbbuttonClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DbButtonClass dbButtonClass = dbButtonClassArrayList.get(position);
        holder.btn1.setText(dbButtonClass.getBtn());
        holder.ans_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.dbbuttonClickListener.onButtonClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dbButtonClassArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder/* implements View.OnClickListener*/ {
        TextView btn1;
        CardView ans_card;
        DbButtonClickListener dbbuttonClickListener;

        public ViewHolder(View view, DbButtonClickListener dbbuttonClickListener) {
            super(view);
            btn1 = view.findViewById(R.id.dbbutton);
            ans_card = view.findViewById(R.id.ans_card);
            this.dbbuttonClickListener = dbbuttonClickListener;
        }

    }


    public interface DbButtonClickListener {
        void onButtonClick(int position);
    }

}

