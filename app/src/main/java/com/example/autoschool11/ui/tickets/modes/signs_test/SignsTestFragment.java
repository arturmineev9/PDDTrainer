package com.example.autoschool11.ui.tickets.modes.signs_test;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.DbButtonAdapter;
import com.example.autoschool11.adapters.HorizontalButtonAdapter;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.db.db_classes.DbButtonClass;
import com.example.autoschool11.db.MistakesDataBaseHelper;
import com.example.autoschool11.db.TrainingDataBaseHelper;
import com.example.autoschool11.ui.tickets.Ticket;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;


public class SignsTestFragment extends Fragment implements DbButtonAdapter.DbButtonClickListener, HorizontalButtonAdapter.HorizontalButtonClickListener, View.OnClickListener{
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
    int signs_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            signs_id = getArguments().getInt("signs_id");
            i = 10 * (signs_id - 1) + 1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);
        question = view.findViewById(R.id.db_question);
        question.setVisibility(View.INVISIBLE);
        explanation = view.findViewById(R.id.explanation);
        image_question = view.findViewById(R.id.db_image);
        mDBHelper = new DataBaseHelper(getContext());
        recyclerViewans = view.findViewById(R.id.ansRV);
        questionnumber = view.findViewById(R.id.questionnumbertxt);
        CardView favourites = view.findViewById(R.id.favourites_card);
        favourites.setVisibility(View.GONE);
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

        ShowSignsData(i);

        btnnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (i == signs_id * 10 + 1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("countans", countans);
                    bundle.putInt("countquestions", 10);
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                    navController.navigate(R.id.ticketEndFragment, bundle);
                    DbButtonAdapter.setCountans(0);
                } else {
                    ShowSignsData(i);
                }
            }
        });
        return view;
    }

    public void ShowSignsData(int a) {
        explanation.setVisibility(View.GONE);
        btnnext.setVisibility(View.GONE);
        image_question.setVisibility(View.VISIBLE);
        mDBHelper.getSignsQuestions(a);
        explanation.setText(DataBaseHelper.getExplanation());
        String img_name = DataBaseHelper.getSign_number();
        img_name = img_name.replace(".", "");
        img = "sign_" + img_name;
        int id = getResources().getIdentifier("com.example.autoschool11:drawable/" + img, null, null);
        image_question.setImageResource(id);

        dbButtonClassArrayList = mDBHelper.getSignsAnswers(a);
        count = 0;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        DbButtonAdapter dbButtonAdapter = new DbButtonAdapter(dbButtonClassArrayList, SignsTestFragment.this);
        recyclerViewans.setLayoutManager(linearLayoutManager);
        recyclerViewans.setAdapter(dbButtonAdapter);
        question_number++;
        i++;
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onButtonClick(int position) {
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
                    RecyclerView.ViewHolder ans_view = recyclerViewans.findViewHolderForAdapterPosition(position);
                    RecyclerView.ViewHolder right_ans = recyclerViewans.findViewHolderForAdapterPosition(DataBaseHelper.getCorrectans());
                    CardView right_button = right_ans.itemView.findViewById(R.id.ans_card);
                    CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
                    if (Ticket.getCount() > 1) {
                        ansbutton.setClickable(false);
                    } else {
                        if (position == DataBaseHelper.getCorrectans()) {
                            ansbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                            countans++;
                        } else {
                            ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                            right_button.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        }
                        btnnext.setVisibility(View.VISIBLE);
                        explanation.setVisibility(View.VISIBLE);
                    }

                }
        });
    }

    @Override
    public void onHorizontalButtonClick(int position) {

    }
}