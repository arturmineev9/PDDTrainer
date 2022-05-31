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
import com.example.autoschool11.db.DbButtonClass;
import com.example.autoschool11.db.FavouritesDataBaseHelper;
import com.example.autoschool11.db.MistakesDataBaseHelper;
import com.example.autoschool11.db.TrainingDataBaseHelper;
import com.example.autoschool11.ui.tickets.Ticket1;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;


public class HardQuestionFragment extends Fragment implements DbButtonAdapter.DbButtonClickListener, HorizontalButtonAdapter.HorizontalButtonClickListener, View.OnClickListener {
    ArrayList<DbButtonClass> dbButtonClassArrayList;
    RecyclerView recyclerViewans;
    RecyclerView recyclerViewhorizontal;
    static int i = 1;
    int end;
    String ticket_number;
    String img;
    public DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    static int count;
    TextView question;
    TextView quuestionnumber;
    ImageView favourite_img;
    TextView favourite_txt;
    int countans;
    ImageView image_question;
    int question_number = 1;
    TextView explanation;
    Button btnnext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket1, container, false);
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);

        question = view.findViewById(R.id.db_question);
        explanation = view.findViewById(R.id.explanation);
        image_question = view.findViewById(R.id.db_image);
        mDBHelper = new DataBaseHelper(getContext());
        favourite_img = view.findViewById(R.id.favourites_image);
        favourite_txt = view.findViewById(R.id.favourites_txt);
        recyclerViewans = view.findViewById(R.id.ansRV);
        recyclerViewhorizontal = view.findViewById(R.id.horizontalRV);
        quuestionnumber = view.findViewById(R.id.questionnumbertxt);
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

        String[] numbers = new String[40];
        for (int j = 0; j < 40; j++) {
            numbers[j] = Integer.toString(j + 1);
        }
        HorizontalButtonAdapter horizontalButtonAdapter = new HorizontalButtonAdapter(numbers, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewhorizontal.setLayoutManager(layoutManager);
        recyclerViewhorizontal.setItemViewCacheSize(40);
        recyclerViewhorizontal.setAdapter(horizontalButtonAdapter);


        ShowHardQuestion(i);

        btnnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (i == 40) {
                    for (int j = 0; j < 40; j++) {
                        if ((chooseans[j]) == 0) {
                            i = j + 1;
                            question_number = j + 1;
                            ShowHardQuestion(i);
                            break;
                        }
                        if (j == 39) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("countans", countans);
                            bundle.putInt("ticket_number", Integer.parseInt(ticket_number));
                            bundle.putInt("countquestions", 40);
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                            navController.navigate(R.id.ticketEndFragment, bundle);
                        }

                    }


                    DbButtonAdapter.setCountans(0);
                } else {
                    if (chooseans[question_number - 1] != 0) {
                        int a = chooseans[question_number - 1];
                        while (a != 0) {
                            i++;
                            question_number++;
                            a = chooseans[question_number - 1];
                        }
                    }

                    ShowHardQuestion(i);
                }
            }
        });


        return view;
    }

    int[] chooseans = new int[40];

    @Override
    public void onButtonClick(int position) {
        if (count < 1) {
            postAndNotifyHorizontalAdapter(new Handler(), i, question_number, position);
        }
        count++;

    }

    @Override
    public void onHorizontalButtonClick(int position) {
        i = position + 1;
        question_number = position + 1;
        postAndNotifyAdapter(new Handler(), recyclerViewans, question_number);
        ShowHardQuestion(i);
    }

    protected void postAndNotifyAdapter(final Handler handler, final RecyclerView recyclerView, int question_number) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (recyclerView.findViewHolderForLayoutPosition(chooseans[question_number - 1] - 1) != null) {


                    if (chooseans[question_number - 1] != 0) {
                        RecyclerView.ViewHolder ans_view = recyclerView.findViewHolderForLayoutPosition(chooseans[question_number - 1] - 1);
                        RecyclerView.ViewHolder right_ans = recyclerViewans.findViewHolderForAdapterPosition(DataBaseHelper.getCorrectans());
                        CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
                        CardView rightbutton = right_ans.itemView.findViewById(R.id.ans_card);
                        if (chooseans[question_number - 1] - 1 == DataBaseHelper.getCorrectans()) {
                            ansbutton.setCardBackgroundColor(Color.GREEN);
                        } else {
                            ansbutton.setCardBackgroundColor(Color.RED);
                            rightbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        }
                        btnnext.setVisibility(View.VISIBLE);
                        explanation.setVisibility(View.VISIBLE);
                    }

                } else {
                    //
                    postAndNotifyAdapter(handler, recyclerView, question_number);
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
                if (recyclerViewhorizontal.findViewHolderForAdapterPosition(question_number - 1) != null) {
                    recyclerViewhorizontal.scrollToPosition(question_number - 1);
                    RecyclerView.ViewHolder ans_view = recyclerViewans.findViewHolderForAdapterPosition(position);
                    RecyclerView.ViewHolder right_ans = recyclerViewans.findViewHolderForAdapterPosition(DataBaseHelper.getCorrectans());
                    CardView right_button = right_ans.itemView.findViewById(R.id.ans_card);
                    CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
                    RecyclerView.ViewHolder rv_view = recyclerViewhorizontal.findViewHolderForAdapterPosition(question_number - 1);
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
                            dataBaseHelper.insertMistake(DataBaseHelper.get_id());
                            trainingDataBaseHelper.decreaseKnowingID(DataBaseHelper.get_id());
                        }
                        btnnext.setVisibility(View.VISIBLE);
                        explanation.setVisibility(View.VISIBLE);
                        trainingDataBaseHelper.increaseKnowingID(DataBaseHelper.get_id());
                    }


                    if (position == DataBaseHelper.getCorrectans()) {
                        bt_view.setCardBackgroundColor(Color.GREEN);
                    } else {
                        bt_view.setCardBackgroundColor(Color.RED);
                    }
                    chooseans[question_number - 1] = position + 1;


                } else {
                    //
                    postAndNotifyHorizontalAdapter(handler, id, question_number, position);
                }
            }
        });
    }

    public void ShowHardQuestion(int a) {
        recyclerViewhorizontal.scrollToPosition(question_number - 1);
        explanation.setVisibility(View.GONE);
        btnnext.setVisibility(View.GONE);
        image_question.setVisibility(View.VISIBLE);


        quuestionnumber.setText("Вопрос " + question_number + " / " + 40);


        dbButtonClassArrayList = mDBHelper.getHardQuestionsAnswers(a);
        count = 0;
        mDBHelper.getHardQuestions(a);
        FavouritesDataBaseHelper dataBaseHelper = new FavouritesDataBaseHelper(getContext());
        if (dataBaseHelper.isInFavourites(DataBaseHelper.get_id())) {
            favourite_img.setImageResource(R.drawable.star_pressed);
            favourite_txt.setText("Удалить из избранного");
        } else {
            favourite_img.setImageResource(R.drawable.star_button);
            favourite_txt.setText("Добавить в избранное");
        }

        question.setText(DataBaseHelper.getQuestion());
        explanation.setText(DataBaseHelper.getExplanation());
        if (Integer.toString(DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd" + "_0" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_0" + (DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(DataBaseHelper.getBilet() + 1).length() != 1 && Integer.toString(DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd_" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_0" + (DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(DataBaseHelper.getNumber() + 1).length() != 1) {
            img = "pdd_0" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_" + (DataBaseHelper.getNumber() + 1);
        } else
            img = "pdd_" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_" + (DataBaseHelper.getNumber() + 1);
        Log.d("img", img);
        try {
            int id = getResources().getIdentifier("com.example.autoschool11:drawable/" + img, null, null);
            Toast toast = Toast.makeText(getContext(), id, Toast.LENGTH_SHORT);
            image_question.setImageResource(id);
        } catch (Exception e) {
            image_question.setVisibility(View.GONE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        DbButtonAdapter dbButtonAdapter = new DbButtonAdapter(dbButtonClassArrayList, HardQuestionFragment.this);
        recyclerViewans.setLayoutManager(linearLayoutManager);
        recyclerViewans.setAdapter(dbButtonAdapter);
        i++;
    }
    @Override
    public void onClick(View view) {
        ImageView favourite_img = view.findViewById(R.id.favourites_image);
        TextView favourite_txt = view.findViewById(R.id.favourites_txt);
        FavouritesDataBaseHelper favouritesDataBaseHelper = new FavouritesDataBaseHelper(getContext());
        if (favourite_txt.getText().equals("Добавить в избранное")) {
            favouritesDataBaseHelper.insertFavourite(DataBaseHelper.get_id());
            favourite_img.setImageResource(R.drawable.star_pressed);
            favourite_txt.setText("Удалить из избранного");
        } else {
            favouritesDataBaseHelper.deleteFavourite(DataBaseHelper.get_id());
            favourite_img.setImageResource(R.drawable.star_button);
            favourite_txt.setText("Добавить в избранное");
        }
    }

    public static void setI(int i) {
        HardQuestionFragment.i = i;
    }
}