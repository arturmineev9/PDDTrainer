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
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.db.db_classes.DbButtonClass;
import com.example.autoschool11.ui.tickets.Ticket;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;


public class FinesTestFragment extends Fragment implements DbButtonAdapter.DbButtonClickListener {

    ArrayList<DbButtonClass> dbButtonClassArrayList;
    RecyclerView recyclerViewans;
    RecyclerView recyclerViewhorizontal;
    static int i;
    public DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    static int count;
    int ticketstart;
    TextView question;
    TextView quuestionnumber;
    int countans;
    int question_number = 1;
    Button btnnext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ticketstart = getArguments().getInt("ticketstart");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);
        CardView favourite_card = view.findViewById(R.id.favourites_card);
        favourite_card.setVisibility(View.GONE);
        TextView explanation = view.findViewById(R.id.explanation);
        explanation.setVisibility(View.GONE);
        ImageView image = view.findViewById(R.id.db_image);
        image.setVisibility(View.GONE);
        question = view.findViewById(R.id.db_question);
        mDBHelper = new DataBaseHelper(getContext());
        recyclerViewans = view.findViewById(R.id.ansRV);
        recyclerViewans.setPadding(0,100,0,0);
        recyclerViewhorizontal = view.findViewById(R.id.horizontalRV);
        quuestionnumber = view.findViewById(R.id.questionnumbertxt);
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


        ShowData(i);


        btnnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (i == 10) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("countans", countans);
                    bundle.putInt("countquestions", 10);
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                    navController.navigate(R.id.ticketEndFragment, bundle);


                DbButtonAdapter.setCountans(0);
            } else

            {

                ShowData(i);
            }
        }
    });
        return view;
}

    public static int getCount() {
        return count;
    }

    int[] chooseans = new int[10];

    @Override
    public void onButtonClick(int position) {

        if (count < 1) {
            postAndNotifyHorizontalAdapter(new Handler(), i, question_number, position);
        }
        count++;


    }


    public void postAndNotifyHorizontalAdapter(final Handler handler, int id, int question_number, int position) {
        handler.post(new Runnable() {
            @Override
            public void run() {
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
                }

            }

        });
    }


    public void ShowData(int a) {
        quuestionnumber.setText(Integer.toString(a + 1) + "/10");
        btnnext.setVisibility(View.GONE);
        dbButtonClassArrayList = mDBHelper.getFinesAnswers(a);
        count = 0;
        mDBHelper.getFinesQuestions(a);
        question.setText(DataBaseHelper.getQuestion());
        GridLayoutManager linearLayoutManager = new GridLayoutManager(context,2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        DbButtonAdapter dbButtonAdapter = new DbButtonAdapter(dbButtonClassArrayList, FinesTestFragment.this);
        recyclerViewans.setLayoutManager(linearLayoutManager);
        recyclerViewans.setAdapter(dbButtonAdapter);
        i++;
    }

    public static void setI(int i) {
        FinesTestFragment.i = i;
    }
}