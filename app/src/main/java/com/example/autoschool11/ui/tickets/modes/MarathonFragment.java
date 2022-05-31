package com.example.autoschool11.ui.tickets.modes;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autoschool11.MainActivity;
import com.example.autoschool11.R;
import com.example.autoschool11.adapters.DbButtonAdapter;
import com.example.autoschool11.adapters.HorizontalButtonAdapter;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.db.DbButtonClass;
import com.example.autoschool11.db.FavouritesDataBaseHelper;
import com.example.autoschool11.db.MistakesDataBaseHelper;
import com.example.autoschool11.db.TrainingDataBaseHelper;
import com.example.autoschool11.ui.tickets.Ticket1;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;

public class MarathonFragment extends Fragment implements DbButtonAdapter.DbButtonClickListener, View.OnClickListener {
    ArrayList<DbButtonClass> dbButtonClassArrayList;
    RecyclerView recyclerViewans;
    RecyclerView recyclerViewhorizontal;
    static int i = 1;
    String img;
    public DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    static int count;
    TextView question;
    TextView questionnumber;
    int countans;
    ImageView image_question;
    ImageView favourite_img;
    TextView favourite_txt;
    int question_number = 1;
    int ticketa;
    TextView explanation;
    Button btnnext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_ticket1, container, false);
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);
        question = view.findViewById(R.id.db_question);
        explanation = view.findViewById(R.id.explanation);
        image_question = view.findViewById(R.id.db_image);
        mDBHelper = new DataBaseHelper(getContext());
        recyclerViewans = view.findViewById(R.id.ansRV);
        recyclerViewhorizontal = view.findViewById(R.id.horizontalRV);
        favourite_img = view.findViewById(R.id.favourites_image);
        favourite_txt = view.findViewById(R.id.favourites_txt);
        questionnumber = view.findViewById(R.id.questionnumbertxt);
        CardView favourites = view.findViewById(R.id.favourites_card);
        favourites.setOnClickListener(this);
        btnnext = view.findViewById(R.id.btnnext);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }


        /*String[] numbers = new String[800];
        for (int j = 0; j < 800; j++) {
            numbers[j] = Integer.toString(j + 1);
        }
        HorizontalButtonAdapter horizontalButtonAdapter = new HorizontalButtonAdapter(numbers, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewhorizontal.setLayoutManager(layoutManager);
        recyclerViewhorizontal.setItemViewCacheSize(800);
        recyclerViewhorizontal.setAdapter(horizontalButtonAdapter);*/
        TrainingDataBaseHelper trainingDataBaseHelper = new TrainingDataBaseHelper(getContext());
        for (int j = 1; j < 801; j++) {
            int progress = trainingDataBaseHelper.getMarathonId(j);
            if (progress == 0) {
                i = j;
                question_number = j;
                break;
            }
        }
        ShowData(i);


        btnnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (i == 801) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("countans", countans);
                    bundle.putInt("countquestions", 800);
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                    navController.navigate(R.id.ticketEndFragment, bundle);
                    DbButtonAdapter.setCountans(0);
                } else {
                    ShowData(i);
                }
            }
        });
        return view;
    }

    int[] chooseans = new int[800];


    @Override
    public void onButtonClick(int position) {
        recyclerViewhorizontal.scrollToPosition(question_number - 1);
        if (count < 1) {
            postAndNotifyHorizontalAdapter(new Handler(), i, question_number, position);
        }
        count++;
    }




    public void postAndNotifyHorizontalAdapter(final Handler handler, int id, int question_number, int position) {
        TrainingDataBaseHelper trainingDataBaseHelper = new TrainingDataBaseHelper(getContext());
        handler.post(new Runnable() {
            @Override
            public void run() {
                MistakesDataBaseHelper dataBaseHelper = new MistakesDataBaseHelper(getContext());
                recyclerViewhorizontal.scrollToPosition(question_number - 1);
                RecyclerView.ViewHolder ans_view = recyclerViewans.findViewHolderForAdapterPosition(position);
                RecyclerView.ViewHolder right_ans = recyclerViewans.findViewHolderForAdapterPosition(DataBaseHelper.getCorrectans());
                CardView right_button = right_ans.itemView.findViewById(R.id.ans_card);
                CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
                if (Ticket1.getCount() > 1) {
                    ansbutton.setClickable(false);
                } else {
                    if (position == DataBaseHelper.getCorrectans()) {
                        ansbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        Log.d("id", String.valueOf(id - 1));
                        trainingDataBaseHelper.setMarathonProgress(id - 1, 1);
                        countans++;


                    } else {
                        ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                        right_button.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        try {
                            dataBaseHelper.insertMistake(id - 1);
                        } catch (SQLiteConstraintException e) {
                            Log.d("Exception", "Этот вопрос уже в бд");
                        }
                        Log.d("id", String.valueOf(id - 1));
                        trainingDataBaseHelper.setMarathonProgress(id - 1, 2);
                        trainingDataBaseHelper.decreaseKnowingID(id - 1);
                    }
                    btnnext.setVisibility(View.VISIBLE);
                    explanation.setVisibility(View.VISIBLE);
                    trainingDataBaseHelper.increaseKnowingID(id - 1);
                }

            }
        });
    }


    public void ShowData(int a) {
        recyclerViewhorizontal.scrollToPosition(question_number - 1);
        explanation.setVisibility(View.GONE);
        btnnext.setVisibility(View.GONE);
        image_question.setVisibility(View.VISIBLE);
        FavouritesDataBaseHelper dataBaseHelper = new FavouritesDataBaseHelper(getContext());
        if (dataBaseHelper.isInFavourites(i)) {
            favourite_img.setImageResource(R.drawable.star_pressed);
            favourite_txt.setText("Удалить из избранного");
        } else {
            favourite_img.setImageResource(R.drawable.star_button);
            favourite_txt.setText("Добавить в избранное");
        }
        questionnumber.setText("Вопрос " + question_number + " / " + 800);
        mDBHelper.getAllData(a);
        if (Integer.toString(DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd" + "_0" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_0" + (DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(DataBaseHelper.getBilet() + 1).length() != 1 && Integer.toString(DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd_" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_0" + (DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(DataBaseHelper.getNumber() + 1).length() != 1) {
            img = "pdd_0" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_" + (DataBaseHelper.getNumber() + 1);
        } else
            img = "pdd_" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_" + (DataBaseHelper.getNumber() + 1);
        try {
            Log.d("img", img);
            int id = getResources().getIdentifier("com.example.autoschool11:drawable/" + img, null, null);
            Toast toast = Toast.makeText(getContext(), id, Toast.LENGTH_SHORT);
            image_question.setImageResource(id);
        } catch (Exception e) {
            image_question.setVisibility(View.GONE);
        }


        dbButtonClassArrayList = mDBHelper.getAnswers(a);
        count = 0;

        explanation.setText(DataBaseHelper.getExplanation());
        question.setText(DataBaseHelper.getQuestion());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        DbButtonAdapter dbButtonAdapter = new DbButtonAdapter(dbButtonClassArrayList, MarathonFragment.this);
        recyclerViewans.setLayoutManager(linearLayoutManager);
        recyclerViewans.setAdapter(dbButtonAdapter);
        question_number++;
        i++;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings_menu, menu);
        for (int i = 0; i < menu.size(); i++)
            menu.getItem(i).setVisible(false);
    }

    @Override
    public void onClick(View view) {
        ImageView favourite_img = view.findViewById(R.id.favourites_image);
        TextView favourite_txt = view.findViewById(R.id.favourites_txt);
        FavouritesDataBaseHelper favouritesDataBaseHelper = new FavouritesDataBaseHelper(getContext());
        if (favourite_txt.getText().equals("Добавить в избранное")) {
            favouritesDataBaseHelper.insertFavourite(i - 1);
            favourite_img.setImageResource(R.drawable.star_pressed);
            favourite_txt.setText("Удалить из избранного");
        } else {
            favouritesDataBaseHelper.deleteFavourite(i - 1);
            favourite_img.setImageResource(R.drawable.star_button);
            favourite_txt.setText("Добавить в избранное");
        }
    }

    public static void setI(int i) {
        MarathonFragment.i = i;
    }
}