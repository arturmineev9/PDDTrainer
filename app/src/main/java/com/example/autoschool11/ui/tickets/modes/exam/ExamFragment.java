package com.example.autoschool11.ui.tickets.modes.exam;

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

import android.os.CountDownTimer;
import android.os.Handler;
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

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.DbButtonAdapter;
import com.example.autoschool11.adapters.HorizontalButtonAdapter;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.db.db_classes.DbButtonClass;
import com.example.autoschool11.db.FavouritesDataBaseHelper;
import com.example.autoschool11.db.MistakesDataBaseHelper;
import com.example.autoschool11.db.TrainingDataBaseHelper;
import com.example.autoschool11.ui.tickets.Ticket;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class ExamFragment extends Fragment implements DbButtonAdapter.DbButtonClickListener, HorizontalButtonAdapter.HorizontalButtonClickListener, View.OnClickListener {

    ArrayList<DbButtonClass> dbButtonClassArrayList;
    RecyclerView recyclerViewans;
    RecyclerView recyclerViewhorizontal;
    static int i;
    String img;
    long timer = 1200000;
    public DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    static int count;
    int amount_of_questions = 20;
    TextView question;
    TextView questionnumber;
    TextView timerText;
    int countans;
    ImageView image_question;
    ImageView favourite_img;
    TextView favourite_txt;
    int question_number = 1;
    TextView explanation;
    Button btnnext;
    int[] questions = new int[30];
    int random;
    int number;
    String[] numbers_add;
    ArrayList<Integer> mistakes;
    boolean isAddQuestions = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);
        mistakes = new ArrayList<>();
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
        for (int j = 0; j < 20; j++) {
            if (j == 0 || j == 5 || j == 10 || j == 15) {
                random = (int) ((Math.random() * 40));
            }
            Log.d("questions", String.valueOf(questions[j]));
            questions[j] = j + random * 20 + 1;
        }
        ShowData(questions[0]);
        btnnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!isAddQuestions) {
                    if (question_number == 21) {
                        for (int j = 0; j < 20; j++) {
                            if ((chooseans[j]) == 0) {
                                question_number = j + 1;
                                ShowData(questions[question_number - 1]);
                                break;
                            }
                            if (j == 19) {
                                if (20 - countans == 1) {
                                    numbers_add = new String[5];
                                    amount_of_questions = 25;
                                    Toast.makeText(getContext(), "Вы ошиблись в одном вопросе. Решите еще 5 доп. вопросов", Toast.LENGTH_SHORT).show();
                                    for (int q = 0; q < numbers_add.length; q++) {
                                        numbers_add[q] = Integer.toString(q + 21);
                                    }
                                    for (int a = 20; a < 25; a++) {
                                        random = (int) ((Math.random() * 40));
                                        questions[a] = random * 20 + mistakes.get(0);
                                    }

                                    HorizontalButtonAdapter horizontalButtonAdapter = new HorizontalButtonAdapter(numbers_add, ExamFragment.this);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                                    recyclerViewhorizontal.setLayoutManager(layoutManager);
                                    recyclerViewhorizontal.setItemViewCacheSize(numbers_add.length);
                                    recyclerViewhorizontal.setAdapter(horizontalButtonAdapter);
                                    isAddQuestions = true;

                                } else if (20 - countans == 2) {
                                    numbers_add = new String[10];
                                    amount_of_questions = 30;
                                    Toast.makeText(getContext(), "Вы ошиблись в двух вопросах. Решите еще 10 доп. вопросов", Toast.LENGTH_SHORT).show();
                                    for (int q = 0; q < numbers_add.length; q++) {
                                        numbers_add[q] = Integer.toString(q + 21);
                                    }
                                    int mistake_number = mistakes.get(0);
                                    for (int a = 20; a < 30; a++) {
                                        if (a == 25) {
                                            mistake_number = mistakes.get(1);
                                        }
                                        random = (int) ((Math.random() * 40));
                                        questions[a] = random * 20 + mistake_number;
                                    }

                                    HorizontalButtonAdapter horizontalButtonAdapter = new HorizontalButtonAdapter(numbers_add, ExamFragment.this);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                                    recyclerViewhorizontal.setLayoutManager(layoutManager);
                                    recyclerViewhorizontal.setItemViewCacheSize(numbers_add.length);
                                    recyclerViewhorizontal.setAdapter(horizontalButtonAdapter);
                                    isAddQuestions = true;

                                } else if (countans == 20) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("countans", countans);
                                    bundle.putInt("type_of_fail", 0); //экзамен сдан
                                    bundle.putInt("countquestions", 20);
                                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                                    navController.navigate(R.id.examEndFragment, bundle);
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("countans", countans);
                                    bundle.putInt("type_of_fail", 1); //экзамен не сдан, т.к допущено более 2-ух ошибок
                                    bundle.putInt("countquestions", 20);
                                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                                    navController.navigate(R.id.examEndFragment, bundle);
                                }

                                ShowData(questions[19]);
                                question_number = 21;
                            }

                        }

                        DbButtonAdapter.setCountans(0);
                    } else {
                        if (chooseans[question_number - 1] != 0) {
                            int a = chooseans[question_number - 1];
                            while (a != 0) {
                                if (question_number != 20) {
                                    question_number++;
                                    a = chooseans[question_number - 1];
                                } else {
                                    for (int j = 0; j < 20; j++) {
                                        if ((chooseans[j]) == 0) {
                                            question_number = j + 1;
                                            recyclerViewhorizontal.scrollToPosition(i);
                                            ShowData(questions[question_number - 1]);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (numbers_add.length == 5) {
                        if (question_number == 26) {
                            if (countans != 24) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("countans", countans);
                                bundle.putInt("type_of_fail", 2); //экзамен не сдан, т.к допущена ошибка в доп. вопросах
                                bundle.putInt("countquestions", 20);
                                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                                navController.navigate(R.id.examEndFragment, bundle);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putInt("countans", countans);
                                bundle.putInt("type_of_fail", 0);
                                bundle.putInt("countquestions", 25);
                                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                                navController.navigate(R.id.examEndFragment, bundle);
                            }
                        }
                    } else if (numbers_add.length == 10) {
                        if (question_number == 31) {
                            if (countans != 28) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("countans", countans);
                                bundle.putInt("type_of_fail", 2); //экзамен не сдан, т.к допущена ошибка в доп. вопросах
                                bundle.putInt("countquestions", 30);
                                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                                navController.navigate(R.id.examEndFragment, bundle);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putInt("countans", countans);
                                bundle.putInt("type_of_fail", 0);
                                bundle.putInt("countquestions", 30);
                                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                                navController.navigate(R.id.examEndFragment, bundle);
                            }
                        }
                    }
                }
                ShowData(questions[question_number - 1]);
            }
        });
        return view;
    }

    public static int getCount() {
        return count;
    }

    int[] chooseans = new int[30];

    @Override
    public void onButtonClick(int position) {
        recyclerViewhorizontal.scrollToPosition(question_number - 1);
        if (isAddQuestions) {
            number = question_number - 20;
        } else number = question_number;
        if (count < 1) {
            postAndNotifyHorizontalAdapter(new Handler(), questions[question_number - 2], number, position);
        }
        count++;


    }

    @Override
    public void onHorizontalButtonClick(int position) {
        i = position;
        if (isAddQuestions) {
            question_number = position + 21;
        } else question_number = position + 1;
        ShowData(questions[question_number - 1]);
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
                    Log.d("post_suc", String.valueOf(question_number - 2));
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
                            countans++;


                        } else {
                            ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                            right_button.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                            dataBaseHelper.insertMistake(id - 1);
                            Log.d("id", String.valueOf(id - 1));
                            trainingDataBaseHelper.decreaseKnowingID(id - 1);
                            mistakes.add(question_number - 1);
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
                    Log.d("post", String.valueOf(question_number - 2));
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
        mDBHelper.getAllData(a);
        FavouritesDataBaseHelper dataBaseHelper = new FavouritesDataBaseHelper(getContext());
        if (dataBaseHelper.isInFavourites(i)) {
            favourite_img.setImageResource(R.drawable.star_pressed);
            favourite_txt.setText("Удалить из избранного");
        } else {
            favourite_img.setImageResource(R.drawable.star_button);
            favourite_txt.setText("Добавить в избранное");
        }
        questionnumber.setText("Вопрос " + question_number + " / " + amount_of_questions);
        if (Integer.toString(DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd" + "_0" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_0" + (DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(DataBaseHelper.getBilet() + 1).length() != 1 && Integer.toString(DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd_" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_0" + (DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(DataBaseHelper.getNumber() + 1).length() != 1) {
            img = "pdd_0" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_" + (DataBaseHelper.getNumber() + 1);
        } else
            img = "pdd_" + Integer.toString(DataBaseHelper.getBilet() + 1) + "_" + (DataBaseHelper.getNumber() + 1);
        try {
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
        DbButtonAdapter dbButtonAdapter = new DbButtonAdapter(dbButtonClassArrayList, ExamFragment.this);
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
        inflater.inflate(R.menu.timer_menu, menu);
        final MenuItem counter = menu.findItem(R.id.counter);
        new CountDownTimer(timer, 1000) {

            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                String hms = ((TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))) + ":" + (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));

                counter.setTitle(hms);
                timer = millis;

            }

            public void onFinish() {
                Bundle bundle = new Bundle();
                bundle.putInt("countans", countans);
                bundle.putInt("type_of_fail", 3); //экзамен не сдан, т.к вышло время
                bundle.putInt("countquestions", amount_of_questions);
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.examEndFragment, bundle);
            }
        }.start();

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