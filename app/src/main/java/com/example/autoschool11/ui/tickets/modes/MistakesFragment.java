package com.example.autoschool11.ui.tickets.modes;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.DbButtonAdapter;
import com.example.autoschool11.adapters.HorizontalButtonAdapter;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.db.db_classes.DbButtonClass;
import com.example.autoschool11.db.FavouritesDataBaseHelper;
import com.example.autoschool11.db.MistakesDataBaseHelper;
import com.example.autoschool11.ui.tickets.Ticket;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;

public class MistakesFragment extends Fragment implements DbButtonAdapter.DbButtonClickListener, HorizontalButtonAdapter.HorizontalButtonClickListener, View.OnClickListener {
    int countans;
    ArrayList<DbButtonClass> dbButtonClassArrayList;
    RecyclerView recyclerViewans;
    RecyclerView recyclerViewhorizontal;
    TextView question_number_txt;
    static int i = 1;
    String img;
    TextView explanation;
    public DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    int[] chooseansthemes;
    static int count;
    TextView question;
    ImageView image_question;
    ArrayList<Integer> correctmistakes;
    int question_number = 1;
    int question_number0 = 0;
    Button btnnext;
    ImageView favourite_img;
    TextView favourite_txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        correctmistakes = new ArrayList<>();
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);
        question = view.findViewById(R.id.db_question);
        image_question = view.findViewById(R.id.db_image);
        mDBHelper = new DataBaseHelper(getContext());
        recyclerViewans = view.findViewById(R.id.ansRV);
        recyclerViewhorizontal = view.findViewById(R.id.horizontalRV);
        explanation = view.findViewById(R.id.explanation);
        question_number_txt = view.findViewById(R.id.questionnumbertxt);
        favourite_img = view.findViewById(R.id.favourites_image);
        favourite_txt = view.findViewById(R.id.favourites_txt);
        btnnext = view.findViewById(R.id.btnnext);
        CardView favourites = view.findViewById(R.id.favourites_card);
        favourites.setOnClickListener(this);

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


        MistakesDataBaseHelper dataBaseHelper = new MistakesDataBaseHelper(getContext());

        ShowThemesData(dataBaseHelper.getId(question_number0));

        String[] numbers = new String[dataBaseHelper.getTableLength()];
        for (int j = 0; j < dataBaseHelper.getTableLength(); j++) {
            numbers[j] = Integer.toString(j + 1);
        }
        chooseansthemes = new int[dataBaseHelper.getTableLength()];


        HorizontalButtonAdapter horizontalButtonAdapter = new HorizontalButtonAdapter(numbers, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewhorizontal.setLayoutManager(layoutManager);
        recyclerViewhorizontal.setItemViewCacheSize(dataBaseHelper.getTableLength());
        recyclerViewhorizontal.setAdapter(horizontalButtonAdapter);


        btnnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (question_number == dataBaseHelper.getTableLength() + 1) {
                    for (int j = 0; j < dataBaseHelper.getTableLength(); j++) {
                        if ((chooseansthemes[j]) == 0) {
                            i = dataBaseHelper.getId(j);
                            ShowThemesData(i);
                            break;
                        }
                        if (j == dataBaseHelper.getTableLength() - 1) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("countans", countans);
                            bundle.putInt("countquestions", dataBaseHelper.getTableLength());
                            dataBaseHelper.deleteMistakes(correctmistakes);
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                            navController.navigate(R.id.ticketEndFragment, bundle);
                        }
                    }


                    countans = 0;
                } else {
                    Log.d("question_number", String.valueOf(question_number));
                    if (chooseansthemes[question_number - 1] != 0) {
                        int a = chooseansthemes[question_number - 1];
                        while (a != 0) {
                            i = dataBaseHelper.getId(question_number);
                            question_number++;
                            a = chooseansthemes[question_number - 1];

                        }
                    }
                    Log.d("i", String.valueOf(i));
                    ShowThemesData(i);
                }
            }
        });
        return view;
    }


    public void ShowThemesData(int id) {
        FavouritesDataBaseHelper favouritesDataBaseHelper = new FavouritesDataBaseHelper(getContext());
        if (favouritesDataBaseHelper.isInFavourites(i)) {
            favourite_img.setImageResource(R.drawable.star_pressed);
            favourite_txt.setText("Удалить из избранного");
        } else {
            favourite_img.setImageResource(R.drawable.star_button);
            favourite_txt.setText("Добавить в избранное");
        }
        MistakesDataBaseHelper dataBaseHelper = new MistakesDataBaseHelper(getContext());
        question_number_txt.setText("Вопрос " + Integer.toString(question_number) + "/" + dataBaseHelper.getTableLength());
        image_question.setVisibility(View.VISIBLE);
        btnnext.setVisibility(View.GONE);
        explanation.setVisibility(View.GONE);
        question.setText(dataBaseHelper.getMistakeQuestions(id, getContext()));

        if (Integer.toString(DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd" + "_0" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_0" + (DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(DataBaseHelper.getBilet() + 1).length() != 1 && Integer.toString(DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd_" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_0" + (DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(DataBaseHelper.getNumber() + 1).length() != 1) {
            img = "pdd_0" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_" + (DataBaseHelper.getNumber() + 1);
        } else
            img = "pdd_" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_" + (DataBaseHelper.getNumber() + 1);
        try {
            int idphoto = getResources().getIdentifier("com.example.autoschool11:drawable/" + img, null, null);
            Toast toast = Toast.makeText(getContext(), idphoto, Toast.LENGTH_SHORT);
            image_question.setImageResource(idphoto);
        } catch (Exception e) {
            image_question.setVisibility(View.GONE);
        }
        dbButtonClassArrayList = dataBaseHelper.getMistakesAnswers(id, getContext());
        explanation.setText(MistakesDataBaseHelper.getExplanation());
        count = 0;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        DbButtonAdapter dbButtonAdapter = new DbButtonAdapter(dbButtonClassArrayList, this);
        recyclerViewans.setLayoutManager(linearLayoutManager);
        recyclerViewans.setAdapter(dbButtonAdapter);
        if (question_number < dataBaseHelper.getTableLength()) {
            question_number0++;
            question_number++;
            if (question_number0 != 0) {
                i = dataBaseHelper.getId(question_number0);
            }
        } else {
            question_number++;
        }

    }

    @Override
    public void onButtonClick(int position) {
        Ticket ticket1 = new Ticket();
        if (count < 1) {
            postAndNotifyHorizontalAdapter(new Handler(), recyclerViewans, question_number, position);
        }
        count++;

    }

    @Override
    public void onHorizontalButtonClick(int position) {
        MistakesDataBaseHelper dataBaseHelper = new MistakesDataBaseHelper(getContext());
        i = dataBaseHelper.getPositionId(position);
        question_number = position + 1;
        question_number0 = position;
        ShowThemesData(i);

        postAndNotifyAdapter(new Handler(), recyclerViewans, i, position);
    }

    protected void postAndNotifyAdapter(final Handler handler, final RecyclerView recyclerView, int question_number, int position) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (recyclerView.findViewHolderForAdapterPosition(chooseansthemes[position] - 1) != null) {
                    if (chooseansthemes[position] != 0) {
                        RecyclerView.ViewHolder ans_view = recyclerView.findViewHolderForAdapterPosition(chooseansthemes[position] - 1);
                        RecyclerView.ViewHolder right_ans = recyclerViewans.findViewHolderForAdapterPosition(DataBaseHelper.getCorrectans());
                        CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
                        CardView rightbutton = right_ans.itemView.findViewById(R.id.ans_card);
                        if (chooseansthemes[position] - 1 == DataBaseHelper.getCorrectans()) {
                            ansbutton.setCardBackgroundColor(Color.GREEN);
                            //countans++;
                        } else {
                            ansbutton.setCardBackgroundColor(Color.RED);
                            rightbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        }
                        explanation.setVisibility(View.VISIBLE);
                        btnnext.setVisibility(View.VISIBLE);
                    }

                } else {
                    //
                    postAndNotifyAdapter(handler, recyclerView, question_number, position);
                }
            }
        });
    }

    public static void setI(int i) {
        MistakesFragment.i = i;
    }

    public void postAndNotifyHorizontalAdapter(final Handler handler, final RecyclerView recyclerView, int question_number, int position) {
        MistakesDataBaseHelper dataBaseHelper = new MistakesDataBaseHelper(getContext());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (recyclerViewhorizontal.findViewHolderForAdapterPosition(question_number - 2) != null) {
                    recyclerViewhorizontal.scrollToPosition(question_number - 1);
                    RecyclerView.ViewHolder ans_view = recyclerViewans.findViewHolderForAdapterPosition(position);
                    RecyclerView.ViewHolder right_ans = recyclerViewans.findViewHolderForAdapterPosition(DataBaseHelper.getCorrectans());
                    CardView right_button = right_ans.itemView.findViewById(R.id.ans_card);
                    CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
                    RecyclerView.ViewHolder rv_view = recyclerViewhorizontal.findViewHolderForAdapterPosition(question_number - 2);
                    CardView bt_view = rv_view.itemView.findViewById(R.id.horizontal_card);

                    if (Ticket.getCount() > 1) {
                        ansbutton.setClickable(false);
                    } else {
                        if (position == DataBaseHelper.getCorrectans()) {
                            ansbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                            correctmistakes.add(dataBaseHelper.getId(question_number - 2));
                            countans++;
                        } else {
                            ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                            right_button.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        }
                        explanation.setVisibility(View.VISIBLE);
                        btnnext.setVisibility(View.VISIBLE);

                        if (position == DataBaseHelper.getCorrectans()) {
                            bt_view.setCardBackgroundColor(Color.GREEN);
                        } else {
                            bt_view.setCardBackgroundColor(Color.RED);
                        }
                        chooseansthemes[question_number - 2] = position + 1;


                    }
                } else {

                    postAndNotifyHorizontalAdapter(handler, recyclerView, question_number, position);
                }
            }
        });

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