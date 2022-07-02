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
import android.widget.Toast;

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.AnswersAdapter;
import com.example.autoschool11.databinding.FragmentTicketBinding;
import com.example.autoschool11.db.PDD_DataBaseHelper;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.db.db_classes.DbButtonClass;
import com.example.autoschool11.ui.tickets.Ticket;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;

// марафон
public class MarathonFragment extends Fragment implements AnswersAdapter.DbButtonClickListener, View.OnClickListener {
    ArrayList<DbButtonClass> dbButtonClassArrayList;
    static int i = 1;
    String img;
    public PDD_DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    static int count;
    int countans;
    int question_number = 1;
    protected FragmentTicketBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentTicketBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);
        mDBHelper = new PDD_DataBaseHelper(getContext());
        binding.favouritesCard.setOnClickListener(this);

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

        DataBaseHelper databaseHelper = new DataBaseHelper(getContext());
        for (int j = 1; j < 801; j++) {
            int progress = databaseHelper.getMarathonId(j); // проверка прогресса и перемещение на место остановки
            if (progress == 0) {
                i = j;
                question_number = j;
                break;
            }
        }
        ShowMarathonQuestion(i);


        binding.btnnext.setOnClickListener(view1 -> { // обработка нажатия "Далее"

            if (i == 801) {
                Bundle bundle = new Bundle();
                bundle.putInt("countans", countans);
                bundle.putInt("countquestions", 800);
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.ticketEndFragment, bundle);
                AnswersAdapter.setCountans(0);
            } else {
                ShowMarathonQuestion(i);
            }
        });
        return view;
    }


    @Override
    public void onButtonClick(int position) { // обработка нажатия на вариант ответа
        if (count < 1) {
            onAnswerClick(new Handler(), i, question_number, position);
        }
        count++;
    }


    public void onAnswerClick(Handler handler, int id, int question_number, int position) {
        handler.post(() -> {
            DataBaseHelper databaseHelper = new DataBaseHelper(getContext());
            binding.horizontalRV.scrollToPosition(question_number - 1);
            RecyclerView.ViewHolder ans_view = binding.ansRV.findViewHolderForAdapterPosition(position);
            RecyclerView.ViewHolder right_ans = binding.ansRV.findViewHolderForAdapterPosition(PDD_DataBaseHelper.getCorrectans());
            CardView right_button = right_ans.itemView.findViewById(R.id.ans_card);
            CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
            if (Ticket.getCount() > 1) {
                ansbutton.setClickable(false);
            } else {
                if (position == PDD_DataBaseHelper.getCorrectans()) {
                    ansbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                    databaseHelper.setMarathonProgress(id - 1, 1);
                    countans++;
                    databaseHelper.increaseCorrectAnswers();
                } else {
                    ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                    right_button.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                    try {
                        databaseHelper.insertMistake(id - 1);
                    } catch (SQLiteConstraintException e) {
                        Log.d("Exception", "Этот вопрос уже в бд");
                    }
                    databaseHelper.increaseIncorrectAnswers();
                    databaseHelper.setMarathonProgress(id - 1, 2);
                    databaseHelper.decreaseKnowingID(id - 1);
                }
                binding.btnnext.setVisibility(View.VISIBLE);
                binding.explanation.setVisibility(View.VISIBLE);
                databaseHelper.increaseKnowingID(id - 1);
            }

        });
    }


    public void ShowMarathonQuestion(int a) { // показ вопроса
        binding.horizontalRV.scrollToPosition(question_number - 1);
        binding.explanation.setVisibility(View.GONE);
        binding.btnnext.setVisibility(View.GONE);
        binding.dbImage.setVisibility(View.VISIBLE);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        if (dataBaseHelper.isInFavourites(i)) {
            binding.favouritesImage.setImageResource(R.drawable.star_pressed);
            binding.favouritesTxt.setText("Удалить из избранного");
        } else {
            binding.favouritesImage.setImageResource(R.drawable.star_button);
            binding.favouritesTxt.setText("Добавить в избранное");
        }
        binding.questionnumbertxt.setText("Вопрос " + question_number + " / " + 800);
        mDBHelper.getQuestion(a);
        if (Integer.toString(PDD_DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(PDD_DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd" + "_0" + (PDD_DataBaseHelper.getBilet() + 1) + "_0" + (PDD_DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(PDD_DataBaseHelper.getBilet() + 1).length() != 1 && Integer.toString(PDD_DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd_" + (PDD_DataBaseHelper.getBilet() + 1) + "_0" + (PDD_DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(PDD_DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(PDD_DataBaseHelper.getNumber() + 1).length() != 1) {
            img = "pdd_0" + (PDD_DataBaseHelper.getBilet() + 1) + "_" + (PDD_DataBaseHelper.getNumber() + 1);
        } else
            img = "pdd_" + (PDD_DataBaseHelper.getBilet() + 1) + "_" + (PDD_DataBaseHelper.getNumber() + 1);
        try {
            Log.d("img", img);
            int id = getResources().getIdentifier("com.example.autoschool11:drawable/" + img, null, null);
            Toast toast = Toast.makeText(getContext(), id, Toast.LENGTH_SHORT);
            binding.dbImage.setImageResource(id);
        } catch (Exception e) {
            binding.dbImage.setVisibility(View.GONE);
        }


        dbButtonClassArrayList = mDBHelper.getAnswers(a);
        count = 0;

        binding.explanation.setText(PDD_DataBaseHelper.getExplanation());
        binding.dbQuestion.setText(PDD_DataBaseHelper.getQuestion());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        AnswersAdapter answersAdapter = new AnswersAdapter(dbButtonClassArrayList, MarathonFragment.this);
        binding.ansRV.setLayoutManager(linearLayoutManager);
        binding.ansRV.setAdapter(answersAdapter);
        question_number++;
        i++;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings_menu, menu);
        for (int j = 0; j < menu.size(); j++)
            menu.getItem(j).setVisible(false);
    }

    @Override
    public void onClick(View view) { // избранное
        DataBaseHelper favouritesDataBaseHelper = new DataBaseHelper(getContext());
        if (binding.favouritesTxt.getText().equals("Добавить в избранное")) {
            favouritesDataBaseHelper.insertFavourite(i - 1);
            binding.favouritesImage.setImageResource(R.drawable.star_pressed);
            binding.favouritesTxt.setText("Удалить из избранного");
        } else {
            favouritesDataBaseHelper.deleteFavourite(i - 1);
            binding.favouritesImage.setImageResource(R.drawable.star_button);
            binding.favouritesTxt.setText("Добавить в избранное");
        }
    }

    public static void setI(int i) {
        MarathonFragment.i = i;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}