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
import android.widget.Toast;

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.AnswersAdapter;
import com.example.autoschool11.adapters.HorizontalButtonAdapter;
import com.example.autoschool11.databinding.FragmentTicketBinding;
import com.example.autoschool11.db.PDD_DataBaseHelper;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.db.db_classes.DbButtonClass;
import com.example.autoschool11.ui.tickets.Ticket;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;

// сложные вопросы
public class HardQuestionFragment extends Fragment implements AnswersAdapter.DbButtonClickListener, HorizontalButtonAdapter.HorizontalButtonClickListener, View.OnClickListener {
    ArrayList<DbButtonClass> dbButtonClassArrayList;
    static int i = 1;
    String ticket_number;
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

        // верхняя панель навигации
        String[] numbers = new String[40];
        for (int j = 0; j < 40; j++) {
            numbers[j] = Integer.toString(j + 1);
        }
        HorizontalButtonAdapter horizontalButtonAdapter = new HorizontalButtonAdapter(numbers, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.horizontalRV.setLayoutManager(layoutManager);
        binding.horizontalRV.setItemViewCacheSize(40);
        binding.horizontalRV.setAdapter(horizontalButtonAdapter);


        ShowHardQuestion(i);

        binding.btnnext.setOnClickListener(view1 -> { // обработка нажатия "Далее"

            if (i == 40) { // проверка на нерешенные вопросы
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


                AnswersAdapter.setCountans(0);
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
        });


        return view;
    }

    int[] chooseans = new int[40];

    @Override
    public void onButtonClick(int position) { // обработка нажатия на вариант ответа
        binding.horizontalRV.scrollToPosition(question_number - 1);
        if (count < 1) {
            onAnswerClick(new Handler(), question_number, position);
        }
        count++;

    }

    @Override
    public void onHorizontalButtonClick(int position) { // обработка нажатия на верхнюю панель навигации
        i = position + 1;
        question_number = position + 1;
        onHorizontalClick(new Handler(), binding.ansRV, question_number);
        ShowHardQuestion(i);
    }

    protected void onHorizontalClick(Handler handler, final RecyclerView recyclerView, int question_number) {

        handler.post(() -> {
            if (recyclerView.findViewHolderForLayoutPosition(chooseans[question_number - 1] - 1) != null) {


                if (chooseans[question_number - 1] != 0) {
                    RecyclerView.ViewHolder ans_view = recyclerView.findViewHolderForLayoutPosition(chooseans[question_number - 1] - 1);
                    RecyclerView.ViewHolder right_ans = binding.ansRV.findViewHolderForAdapterPosition(PDD_DataBaseHelper.getCorrectans());
                    CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
                    CardView rightbutton = right_ans.itemView.findViewById(R.id.ans_card);
                    if (chooseans[question_number - 1] - 1 == PDD_DataBaseHelper.getCorrectans()) {
                        ansbutton.setCardBackgroundColor(Color.GREEN);
                    } else {
                        ansbutton.setCardBackgroundColor(Color.RED);
                        rightbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                    }
                    binding.btnnext.setVisibility(View.VISIBLE);
                    binding.explanation.setVisibility(View.VISIBLE);
                }

            } else {
                //
                onHorizontalClick(handler, recyclerView, question_number);
            }
        });
    }

    public void onAnswerClick(final Handler handler, int question_number, int position) {
        handler.post(() -> {
            DataBaseHelper databaseHelper = new DataBaseHelper(getContext());
            if (binding.horizontalRV.findViewHolderForAdapterPosition(question_number - 1) != null) {
                binding.horizontalRV.scrollToPosition(question_number - 1);
                RecyclerView.ViewHolder ans_view = binding.ansRV.findViewHolderForAdapterPosition(position);
                RecyclerView.ViewHolder right_ans = binding.ansRV.findViewHolderForAdapterPosition(PDD_DataBaseHelper.getCorrectans());
                CardView right_button = right_ans.itemView.findViewById(R.id.ans_card);
                CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
                RecyclerView.ViewHolder rv_view = binding.horizontalRV.findViewHolderForAdapterPosition(question_number - 1);
                CardView bt_view = rv_view.itemView.findViewById(R.id.horizontal_card);

                if (Ticket.getCount() > 1) {
                    ansbutton.setClickable(false);
                } else {
                    if (position == PDD_DataBaseHelper.getCorrectans()) {
                        ansbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        countans++;
                        databaseHelper.increaseCorrectAnswers();
                    } else {
                        ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                        right_button.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        databaseHelper.insertMistake(PDD_DataBaseHelper.get_id());
                        databaseHelper.decreaseKnowingID(PDD_DataBaseHelper.get_id());
                        databaseHelper.increaseIncorrectAnswers();
                    }
                    binding.btnnext.setVisibility(View.VISIBLE);
                    binding.explanation.setVisibility(View.VISIBLE);
                    databaseHelper.increaseKnowingID(PDD_DataBaseHelper.get_id());
                }


                if (position == PDD_DataBaseHelper.getCorrectans()) {
                    bt_view.setCardBackgroundColor(Color.GREEN);
                } else {
                    bt_view.setCardBackgroundColor(Color.RED);
                }
                chooseans[question_number - 1] = position + 1;


            } else {
                //
                onAnswerClick(handler, question_number, position);
            }
        });
    }

    public void ShowHardQuestion(int a) { // показ вопроса
        binding.horizontalRV.scrollToPosition(question_number - 1);
        binding.explanation.setVisibility(View.GONE);
        binding.btnnext.setVisibility(View.GONE);
        binding.dbImage.setVisibility(View.VISIBLE);


        binding.questionnumbertxt.setText("Вопрос " + question_number + " / " + 40);


        dbButtonClassArrayList = mDBHelper.getHardQuestionsAnswers(a);
        count = 0;
        mDBHelper.getHardQuestions(a);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        if (dataBaseHelper.isInFavourites(PDD_DataBaseHelper.get_id())) {
            binding.favouritesImage.setImageResource(R.drawable.star_pressed);
            binding.favouritesTxt.setText("Удалить из избранного");
        } else {
            binding.favouritesImage.setImageResource(R.drawable.star_button);
            binding.favouritesTxt.setText("Добавить в избранное");
        }

        binding.dbQuestion.setText(PDD_DataBaseHelper.getQuestion());
        binding.explanation.setText(PDD_DataBaseHelper.getExplanation());
        if (Integer.toString(PDD_DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(PDD_DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd" + "_0" + (PDD_DataBaseHelper.getBilet() + 1) + "_0" + (PDD_DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(PDD_DataBaseHelper.getBilet() + 1).length() != 1 && Integer.toString(PDD_DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd_" + (PDD_DataBaseHelper.getBilet() + 1) + "_0" + (PDD_DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(PDD_DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(PDD_DataBaseHelper.getNumber() + 1).length() != 1) {
            img = "pdd_0" + (PDD_DataBaseHelper.getBilet() + 1) + "_" + (PDD_DataBaseHelper.getNumber() + 1);
        } else
            img = "pdd_" + (PDD_DataBaseHelper.getBilet() + 1) + "_" + (PDD_DataBaseHelper.getNumber() + 1);
        Log.d("img", img);
        try {
            int id = getResources().getIdentifier("com.example.autoschool11:drawable/" + img, null, null);
            Toast toast = Toast.makeText(getContext(), id, Toast.LENGTH_SHORT);
            binding.dbImage.setImageResource(id);
        } catch (Exception e) {
            binding.dbImage.setVisibility(View.GONE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        AnswersAdapter answersAdapter = new AnswersAdapter(dbButtonClassArrayList, HardQuestionFragment.this);
        binding.ansRV.setLayoutManager(linearLayoutManager);
        binding.ansRV.setAdapter(answersAdapter);
        i++;
    }

    @Override
    public void onClick(View view) { // избранное
        DataBaseHelper favouritesDataBaseHelper = new DataBaseHelper(getContext());
        if (binding.favouritesTxt.getText().equals("Добавить в избранное")) {
            favouritesDataBaseHelper.insertFavourite(PDD_DataBaseHelper.get_id());
            binding.favouritesImage.setImageResource(R.drawable.star_pressed);
            binding.favouritesTxt.setText("Удалить из избранного");
        } else {
            favouritesDataBaseHelper.deleteFavourite(PDD_DataBaseHelper.get_id());
            binding.favouritesImage.setImageResource(R.drawable.star_button);
            binding.favouritesTxt.setText("Добавить в избранное");
        }
    }

    public static void setI(int i) {
        HardQuestionFragment.i = i;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}