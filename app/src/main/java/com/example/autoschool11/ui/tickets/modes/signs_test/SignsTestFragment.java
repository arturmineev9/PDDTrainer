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

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.AnswersAdapter;
import com.example.autoschool11.databinding.FragmentTicketBinding;
import com.example.autoschool11.db.PDD_DataBaseHelper;
import com.example.autoschool11.db.db_classes.DbButtonClass;
import com.example.autoschool11.ui.tickets.Ticket;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;


public class SignsTestFragment extends Fragment implements AnswersAdapter.DbButtonClickListener { // тренажер по знакам
    ArrayList<DbButtonClass> dbButtonClassArrayList;
    static int i;
    String img;
    public PDD_DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    static int count;
    int countans;
    int question_number = 1;
    int signs_id;
    protected FragmentTicketBinding binding;

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
        binding = FragmentTicketBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);

        binding.dbQuestion.setVisibility(View.INVISIBLE);
        mDBHelper = new PDD_DataBaseHelper(getContext());
        binding.favouritesCard.setVisibility(View.GONE);

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

        ShowSignsQuestion(i);

        binding.btnnext.setOnClickListener(view1 -> {

            if (i == signs_id * 10 + 1) {
                Bundle bundle = new Bundle();
                bundle.putInt("countans", countans);
                bundle.putInt("countquestions", 10);
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.ticketEndFragment, bundle);
                AnswersAdapter.setCountans(0);
            } else {
                ShowSignsQuestion(i);
            }
        });
        return view;
    }

    public void ShowSignsQuestion(int a) { // показ вопроса
        binding.explanation.setVisibility(View.GONE);
        binding.btnnext.setVisibility(View.GONE);
        binding.dbImage.setVisibility(View.VISIBLE);
        mDBHelper.getSignsQuestions(a);
        binding.explanation.setText(PDD_DataBaseHelper.getExplanation());
        String img_name = PDD_DataBaseHelper.getSign_number();
        img_name = img_name.replace(".", "");
        img = "sign_" + img_name;
        int id = getResources().getIdentifier("com.example.autoschool11:drawable/" + img, null, null);
        binding.dbImage.setImageResource(id);

        dbButtonClassArrayList = mDBHelper.getSignsAnswers(a);
        count = 0;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        AnswersAdapter answersAdapter = new AnswersAdapter(dbButtonClassArrayList, SignsTestFragment.this);
        binding.ansRV.setLayoutManager(linearLayoutManager);
        binding.ansRV.setAdapter(answersAdapter);
        question_number++;
        i++;
    }

    @Override
    public void onButtonClick(int position) { // обработка нажатия на вариант ответа
        if (count < 1) {
            onAnswerClick(new Handler(), position);
        }
        count++;
    }

    public void onAnswerClick(Handler handler, int position) { // обработка нажатия на вариант ответа
        handler.post(() -> {
            RecyclerView.ViewHolder ans_view = binding.ansRV.findViewHolderForAdapterPosition(position);
            RecyclerView.ViewHolder right_ans = binding.ansRV.findViewHolderForAdapterPosition(PDD_DataBaseHelper.getCorrectans());
            CardView right_button = right_ans.itemView.findViewById(R.id.ans_card);
            CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
            if (Ticket.getCount() > 1) {
                ansbutton.setClickable(false);
            } else {
                if (position == PDD_DataBaseHelper.getCorrectans()) {
                    ansbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                    countans++;
                } else {
                    ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                    right_button.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                }
                binding.btnnext.setVisibility(View.VISIBLE);
                binding.explanation.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}