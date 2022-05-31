package com.example.autoschool11.ui.tickets;

import android.app.ActionBar;
import android.content.Context;
import android.database.SQLException;
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
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autoschool11.MainActivity;
import com.example.autoschool11.R;
import com.example.autoschool11.adapters.HorizontalButtonAdapter;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.db.DbAdapter;
import com.example.autoschool11.adapters.DbButtonAdapter;
import com.example.autoschool11.db.DbButtonClass;
import com.example.autoschool11.db.DbModelClass;
import com.example.autoschool11.db.FavouritesDataBaseHelper;
import com.example.autoschool11.db.MistakesDataBaseHelper;
import com.example.autoschool11.db.TrainingDataBaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class Ticket1 extends Fragment implements DbButtonAdapter.DbButtonClickListener, HorizontalButtonAdapter.HorizontalButtonClickListener, View.OnClickListener {
    ArrayList<DbButtonClass> dbButtonClassArrayList;
    RecyclerView recyclerViewans;
    RecyclerView recyclerViewhorizontal;
    static int i;
    int end;
    String ticket_number;
    String img;
    public DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    static int count;
    int ticketstart;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ticketstart = getArguments().getInt("ticketstart");
            ticket_number = getArguments().getString("ticket");
            i = getArguments().getInt("ticketstart");
            end = getArguments().getInt("ticketend");
            ticketa = getArguments().getInt("ticketa");
        }
    }

    public static void setI(int i) {
        Ticket1.i = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Билет №" + ticketa);
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

        String[] numbers = new String[20];
        for (int j = 0; j < 20; j++) {
            numbers[j] = Integer.toString(j + 1);
        }
        HorizontalButtonAdapter horizontalButtonAdapter = new HorizontalButtonAdapter(numbers, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewhorizontal.setLayoutManager(layoutManager);
        recyclerViewhorizontal.setItemViewCacheSize(20);
        recyclerViewhorizontal.setAdapter(horizontalButtonAdapter);

        ShowData(i);
        btnnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (i == end + 1) {
                    for (int j = 0; j < 20; j++) {
                        if ((chooseans[j]) == 0) {
                            i = j + ticketstart;
                            question_number = j + 1;
                            Log.d("i", String.valueOf(i));
                            ShowData(i);
                            break;
                        }
                        if (j == 19) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("countans", countans);
                            bundle.putInt("ticket_number", Integer.parseInt(ticket_number));
                            bundle.putInt("countquestions", 20);
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                            navController.navigate(R.id.ticketEndFragment, bundle);
                        }

                    }


                    DbButtonAdapter.setCountans(0);
                } else {
                    if (chooseans[question_number - 1] != 0) {
                        int a = chooseans[question_number - 1];
                        while (a != 0) {
                            if (question_number != 20) {
                                i++;
                                question_number++;
                                a = chooseans[question_number - 1];
                            }
                            else {
                                for (int j = 0; j < 20; j++) {
                                    if ((chooseans[j]) == 0) {
                                        i = j + ticketstart;
                                        question_number = j + 1;
                                        recyclerViewhorizontal.scrollToPosition(i);
                                        ShowData(i);
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    ShowData(i);
                }
            }
        });
        return view;
    }

    public static int getCount() {
        return count;
    }

    int[] chooseans = new int[20];

    @Override
    public void onButtonClick(int position) {
        recyclerViewhorizontal.scrollToPosition(question_number - 1);
        if (count < 1) {
            postAndNotifyHorizontalAdapter(new Handler(), i, question_number, position);
        }
        count++;


    }

    @Override
    public void onHorizontalButtonClick(int position) {

        i = position + ticketstart;
        question_number = position + 1;
        ShowData(ticketstart + position);
        postAndNotifyAdapter(new Handler(), recyclerViewans, question_number, position);

    }

    protected void postAndNotifyAdapter(final Handler handler, final RecyclerView recyclerView, int question_number, int position) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (recyclerView.findViewHolderForLayoutPosition(chooseans[question_number - 2] - 1) != null) {
                    if (chooseans[question_number - 2] != 0) {
                        RecyclerView.ViewHolder ans_view = recyclerView.findViewHolderForLayoutPosition(chooseans[question_number - 2] - 1);
                        RecyclerView.ViewHolder right_ans = recyclerViewans.findViewHolderForAdapterPosition(DataBaseHelper.getCorrectans());
                        RecyclerView.ViewHolder picked_ans = recyclerViewans.findViewHolderForAdapterPosition(position);
                        CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
                        CardView rightbutton = right_ans.itemView.findViewById(R.id.ans_card);
                        count = 1;
                        if (chooseans[question_number - 2] - 1 == DataBaseHelper.getCorrectans()) {
                            ansbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        } else {
                            ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                            rightbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        }
                        btnnext.setVisibility(View.VISIBLE);
                        explanation.setVisibility(View.VISIBLE);
                    }

                } else {
                    //
                    postAndNotifyAdapter(handler, recyclerView, question_number, position);
                }
            }
        });
    }

    public void postAndNotifyHorizontalAdapter(final Handler handler, int id, int question_number, int position) {
        TrainingDataBaseHelper trainingDataBaseHelper = new TrainingDataBaseHelper(getContext());
        handler.post(new Runnable() {
            @Override
            public void run() {
                MistakesDataBaseHelper dataBaseHelper = new MistakesDataBaseHelper(getContext());
                if (recyclerViewhorizontal.findViewHolderForAdapterPosition(question_number - 2) != null) {
                    recyclerViewhorizontal.scrollToPosition(question_number - 1);
                    RecyclerView.ViewHolder ans_view = recyclerViewans.findViewHolderForAdapterPosition(position);
                    RecyclerView.ViewHolder right_ans = recyclerViewans.findViewHolderForAdapterPosition(DataBaseHelper.getCorrectans());
                    CardView right_button = right_ans.itemView.findViewById(R.id.ans_card);
                    CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
                    RecyclerView.ViewHolder rv_view = recyclerViewhorizontal.findViewHolderForAdapterPosition(question_number - 2);
                    CardView bt_view = rv_view.itemView.findViewById(R.id.horizontal_card);

                    if (Ticket1.getCount() > 1) {
                        ansbutton.setClickable(false);
                    } else {
                        if (position == DataBaseHelper.getCorrectans()) {
                            ansbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                            countans++;


                        } else {
                            ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                            right_button.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                            dataBaseHelper.insertMistake(id - 1);
                            trainingDataBaseHelper.decreaseKnowingID(id - 1);
                        }
                        btnnext.setVisibility(View.VISIBLE);
                        explanation.setVisibility(View.VISIBLE);
                        trainingDataBaseHelper.increaseKnowingID(id - 1);
                    }


                    if (position == DataBaseHelper.getCorrectans()) {
                        bt_view.setCardBackgroundColor(Color.GREEN);
                    } else {
                        bt_view.setCardBackgroundColor(Color.RED);
                    }
                    chooseans[question_number - 2] = position + 1;


                } else {
                    //
                    postAndNotifyHorizontalAdapter(handler, id, question_number, position);
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
        questionnumber.setText("Вопрос " + question_number + " / " + 20);
        if (Integer.toString(question_number).length() == 1) {
            img = "pdd_" + ticket_number + "_0" + question_number;
        } else {
            img = "pdd_" + ticket_number + "_" + question_number;
        }
        question_number++;
        try {
            int id = getResources().getIdentifier("com.example.autoschool11:drawable/" + img, null, null);
            Toast toast = Toast.makeText(getContext(), id, Toast.LENGTH_SHORT);
            image_question.setImageResource(id);
        } catch (Exception e) {
            //image_question.setImageResource(R.drawable.nodrawing);
            image_question.setVisibility(View.GONE);
        }


        dbButtonClassArrayList = mDBHelper.getAnswers(a);
        count = 0;
        mDBHelper.getAllData(a);
        explanation.setText(DataBaseHelper.getExplanation());
        question.setText(DataBaseHelper.getQuestion());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        DbButtonAdapter dbButtonAdapter = new DbButtonAdapter(dbButtonClassArrayList, Ticket1.this);
        recyclerViewans.setLayoutManager(linearLayoutManager);
        recyclerViewans.setAdapter(dbButtonAdapter);
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
}