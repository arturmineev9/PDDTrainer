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
import com.example.autoschool11.db.TrainingDataBaseHelper;
import com.example.autoschool11.ui.tickets.Ticket1;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;


public class TrainingFragmentSolution extends Fragment implements DbButtonAdapter.DbButtonClickListener, HorizontalButtonAdapter.HorizontalButtonClickListener, View.OnClickListener {
    int knowing_id;
    ArrayList<Integer> increase_id;
    ArrayList<Integer> decrease_id;
    ArrayList<DbButtonClass> dbButtonClassArrayList;
    RecyclerView recyclerViewans;
    RecyclerView recyclerViewhorizontal;
    static int i;
    int table_length;
    String img;
    public DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    int[] chooseansthemes;
    static int count;
    int countans;
    TextView question;
    ImageView image_question;
    static int question_number = 1;
    ImageView favourite_img;
    TextView favourite_txt;
    int question_number0 = 0;
    String[] numbers;
    Button btnnext;
    TextView explanation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            knowing_id = getArguments().getInt("knowing");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket1, container, false);
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);
        question = view.findViewById(R.id.db_question);
        image_question = view.findViewById(R.id.db_image);
        mDBHelper = new DataBaseHelper(getContext());
        recyclerViewans = view.findViewById(R.id.ansRV);
        recyclerViewhorizontal = view.findViewById(R.id.horizontalRV);
        increase_id = new ArrayList<>();
        decrease_id = new ArrayList<>();
        favourite_img = view.findViewById(R.id.favourites_image);
        favourite_txt = view.findViewById(R.id.favourites_txt);
        btnnext = view.findViewById(R.id.btnnext);
        CardView favourites = view.findViewById(R.id.favourites_card);
        favourites.setOnClickListener(this);
        explanation = view.findViewById(R.id.explanation);

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
        TrainingDataBaseHelper trainingDataBaseHelper = new TrainingDataBaseHelper(getContext());

        ShowTrainingData(question_number);

        if (trainingDataBaseHelper.getTrainingTableLength(knowing_id) > 21) {
            numbers = new String[20];
            for (int j = 0; j < 20; j++) {
                numbers[j] = Integer.toString(j + 1);
            }
            table_length = 20;
            chooseansthemes = new int[20];
        } else {
            numbers = new String[trainingDataBaseHelper.getTrainingTableLength(knowing_id)];
            for (int j = 0; j < trainingDataBaseHelper.getTrainingTableLength(knowing_id); j++) {
                numbers[j] = Integer.toString(j + 1);
            }
            table_length = trainingDataBaseHelper.getTrainingTableLength(knowing_id);
            chooseansthemes = new int[trainingDataBaseHelper.getTrainingTableLength(knowing_id)];
        }


        HorizontalButtonAdapter horizontalButtonAdapter = new HorizontalButtonAdapter(numbers, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewhorizontal.setLayoutManager(layoutManager);
        recyclerViewhorizontal.setItemViewCacheSize(20);
        recyclerViewhorizontal.setAdapter(horizontalButtonAdapter);


        btnnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (question_number == table_length + 1) {
                    for (int j = 0; j < table_length; j++) {
                        if ((chooseansthemes[j]) == 0) {
                            i = j;
                            ShowTrainingData(i + 1);
                            break;
                        }
                        if (j == table_length - 1) {
                            TrainingDataBaseHelper dataBaseHelper = new TrainingDataBaseHelper(getContext());
                            Bundle bundle = new Bundle();
                            bundle.putInt("countans", countans);
                            bundle.putInt("countquestions", table_length);
                            dataBaseHelper.increaseArrayId(increase_id);
                            dataBaseHelper.decreaseArrayId(decrease_id);
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                            navController.navigate(R.id.ticketEndFragment, bundle);
                        }
                    }


                    countans = 0;
                } else {

                    if (chooseansthemes[question_number - 1] != 0) {
                        int a = chooseansthemes[question_number - 1];
                        while (a != 0) {
                            i++;
                            question_number++;
                            a = chooseansthemes[question_number - 1];
                        }
                    }
                    ShowTrainingData(question_number);
                }
            }
        });
        return view;
    }

    public void ShowTrainingData(int a) {
        image_question.setVisibility(View.VISIBLE);
        explanation.setVisibility(View.GONE);
        btnnext.setVisibility(View.GONE);
        TrainingDataBaseHelper dataBaseHelper = new TrainingDataBaseHelper(getContext());
        question.setText(dataBaseHelper.getTrainingQuestions(knowing_id, dataBaseHelper.getTrainingId(a, knowing_id)));
        mDBHelper.getTicketNumber(dataBaseHelper.getTrainingId(a, knowing_id));
        explanation.setText(DataBaseHelper.getExplanation());
        FavouritesDataBaseHelper favouritesDataBaseHelper = new FavouritesDataBaseHelper(getContext());
        if (favouritesDataBaseHelper.isInFavourites(DataBaseHelper.get_id())) {
            favourite_img.setImageResource(R.drawable.star_pressed);
            favourite_txt.setText("Удалить из избранного");
        } else {
            favourite_img.setImageResource(R.drawable.star_button);
            favourite_txt.setText("Добавить в избранное");
        }
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


        dbButtonClassArrayList = dataBaseHelper.getTrainingAnswers(dataBaseHelper.getTrainingId(question_number, knowing_id));
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
        question_number++;
        i++;
    }

    @Override
    public void onButtonClick(int position) {
        if (count < 1) {
            postAndNotifyHorizontalAdapter(new Handler(), recyclerViewans, question_number, position);
        }
        count++;

    }


    @Override
    public void onHorizontalButtonClick(int position) {
        i = position;
        question_number = position + 1;
        question_number0 = position;
        ShowTrainingData(question_number);
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
                        } else {
                            ansbutton.setCardBackgroundColor(Color.RED);
                            rightbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        }
                    }

                } else {
                    //
                    postAndNotifyAdapter(handler, recyclerView, question_number, position);
                }
            }
        });
    }

    public void postAndNotifyHorizontalAdapter(final Handler handler, final RecyclerView recyclerView, int question_number, int position) {
        TrainingDataBaseHelper trainingDataBaseHelper = new TrainingDataBaseHelper(getContext());
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
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


                    if (Ticket1.getCount() > 1) {
                        ansbutton.setClickable(false);
                    } else {
                        if (position == DataBaseHelper.getCorrectans()) {
                            ansbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                            countans++;
                            increase_id.add(trainingDataBaseHelper.getTrainingId(question_number - 1, knowing_id));
                        } else {
                            ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                            right_button.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                            decrease_id.add(trainingDataBaseHelper.getTrainingId(question_number - 1, knowing_id));
                        }
                        btnnext.setVisibility(View.VISIBLE);
                        explanation.setVisibility(View.VISIBLE);

                        if (position == DataBaseHelper.getCorrectans()) {
                            bt_view.setCardBackgroundColor(Color.GREEN);
                        } else {
                            bt_view.setCardBackgroundColor(Color.RED);
                        }
                        chooseansthemes[question_number - 2] = position + 1;

                    }
                } else {
                    //
                    postAndNotifyHorizontalAdapter(handler, recyclerView, question_number, position);
                }
            }
        });
    }

    public static void setQuestion_number(int question_number) {
        TrainingFragmentSolution.question_number = question_number;
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
}