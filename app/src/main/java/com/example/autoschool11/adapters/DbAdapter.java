package com.example.autoschool11.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*import com.example.sqliteassethelperapp.R;*/

import com.example.autoschool11.R;
import com.example.autoschool11.db.db_classes.DbModelClass;

import java.util.ArrayList;

public class DbAdapter extends RecyclerView.Adapter<DbAdapter.DbViewHolder> {
    ArrayList<DbModelClass> objDbModelClassArrayList;

    public DbAdapter(ArrayList<DbModelClass> objDbModelClassArrayList) {
        this.objDbModelClassArrayList = objDbModelClassArrayList;
    }

    @NonNull
    @Override
    public DbViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View singleRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_row, viewGroup, false);

        return new DbViewHolder(singleRow);
    }

    @Override
    public void onBindViewHolder(@NonNull DbViewHolder dbViewHolder, int i) {
        DbModelClass objDbModelClass = objDbModelClassArrayList.get(i);
        dbViewHolder.question.setText(objDbModelClass.getQuestion());
        dbViewHolder.image.setImageBitmap(objDbModelClass.getImage());
    }

    @Override
    public int getItemCount() {
        return objDbModelClassArrayList.size();
    }


    public static class DbViewHolder extends RecyclerView.ViewHolder {

        TextView question;
        ImageView image;

        public DbViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.sr_imageDes);
            image = itemView.findViewById(R.id.sr_image);
        }

    }
}
