package com.example.autoschool11.ui.tickets;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoschool11.R;
import com.example.autoschool11.adapters.AnswersAdapter;
import com.example.autoschool11.adapters.HorizontalButtonAdapter;
import com.example.autoschool11.databinding.FragmentTicketBinding;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.db.PDD_DataBaseHelper;
import com.example.autoschool11.db.db_classes.DbButtonClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;


public class TicketThemes extends Fragment implements AnswersAdapter.DbButtonClickListener, HorizontalButtonAdapter.HorizontalButtonClickListener, View.OnClickListener { // Тренировка по темам
    int category;
    ArrayList<DbButtonClass> dbButtonClassArrayList;
    static int i;
    String img;
    public PDD_DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    int[] chooseansthemes;
    static int count;
    int countans;
    int question_number = 1;
    int question_number0 = 0;
    protected FragmentTicketBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getInt("category");
        }
    }

    public static void setI(int i) {
        TicketThemes.i = i;
    }


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

        ShowThemesData(0);

        String[] numbers = new String[PDD_DataBaseHelper.getCount()];
        for (int j = 0; j < PDD_DataBaseHelper.getCount(); j++) {
            numbers[j] = Integer.toString(j + 1);
        }
        chooseansthemes = new int[PDD_DataBaseHelper.getCount()];


        HorizontalButtonAdapter horizontalButtonAdapter = new HorizontalButtonAdapter(numbers, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.horizontalRV.setLayoutManager(layoutManager);
        binding.horizontalRV.setItemViewCacheSize(PDD_DataBaseHelper.getCount());
        binding.horizontalRV.setAdapter(horizontalButtonAdapter);


        binding.btnnext.setOnClickListener(view1 -> { // нажатие на кнопку "Далее"
            if (question_number == PDD_DataBaseHelper.getTable_length() + 1) {
                for (int j = 0; j < PDD_DataBaseHelper.getTable_length(); j++) {
                    if ((chooseansthemes[j]) == 0) {
                        i = j;
                        ShowThemesData(i);
                        break;
                    }
                    if (j == PDD_DataBaseHelper.getTable_length() - 1) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("countans", countans);
                        bundle.putInt("countquestions", PDD_DataBaseHelper.getTable_length());
                        bundle.putInt("is_themes", 1);
                        bundle.putInt("category", category);
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
                ShowThemesData(i);
            }
        });
        return view;
    }


    public void ShowThemesData(int a) { // создание вопроса

        binding.horizontalRV.scrollToPosition(question_number - 1);
        binding.explanation.setVisibility(View.GONE);
        binding.btnnext.setVisibility(View.GONE);
        binding.dbImage.setVisibility(View.VISIBLE);
        binding.dbQuestion.setText(mDBHelper.getThemesQuestions(category, a));

        if (Integer.toString(PDD_DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(PDD_DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd" + "_0" + (PDD_DataBaseHelper.getBilet() + 1) + "_0" + (PDD_DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(PDD_DataBaseHelper.getBilet() + 1).length() != 1 && Integer.toString(PDD_DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd_" + (PDD_DataBaseHelper.getBilet() + 1) + "_0" + (PDD_DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(PDD_DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(PDD_DataBaseHelper.getNumber() + 1).length() != 1) {
            img = "pdd_0" + (PDD_DataBaseHelper.getBilet() + 1) + "_" + (PDD_DataBaseHelper.getNumber() + 1);
        } else
            img = "pdd_" + (PDD_DataBaseHelper.getBilet() + 1) + "_" + (PDD_DataBaseHelper.getNumber() + 1);
        try {
            int id = getResources().getIdentifier("com.example.autoschool11:drawable/" + img, null, null);
            Toast toast = Toast.makeText(getContext(), id, Toast.LENGTH_SHORT);
            binding.dbImage.setImageResource(id);
        } catch (Exception e) {
            binding.dbImage.setVisibility(View.GONE);
        }


        dbButtonClassArrayList = mDBHelper.getAnswers(PDD_DataBaseHelper.get_id());
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        if (dataBaseHelper.isInFavourites(PDD_DataBaseHelper.get_id())) {
            binding.favouritesImage.setImageResource(R.drawable.star_pressed);
            binding.favouritesTxt.setText("Удалить из избранного");
        } else {
            binding.favouritesImage.setImageResource(R.drawable.star_button);
            binding.favouritesTxt.setText("Добавить в избранное");
        }
        count = 0;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.explanation.setText(PDD_DataBaseHelper.getExplanation());
        AnswersAdapter answersAdapter = new AnswersAdapter(dbButtonClassArrayList, this);
        binding.ansRV.setLayoutManager(linearLayoutManager);
        binding.ansRV.setAdapter(answersAdapter);
        question_number++;
        i++;
    }

    @Override
    public void onButtonClick(int position) { // обработка нажатия на вариант ответа
        if (count < 1) {
            onAnswerClick(new Handler(), question_number, position);
        }
        count++;

    }

    @Override
    public void onHorizontalButtonClick(int position) { // обработка нажатия на верхнюю панель навигации между вопросами
        i = position;
        question_number = position + 1;
        question_number0 = position;
        ShowThemesData(i);
        onHorizontalClick(new Handler(), binding.ansRV, position);
    }

    protected void onHorizontalClick(final Handler handler, final RecyclerView recyclerView, int position) { // обработка нажатия на верхнюю панель навигации между вопросами

        handler.post(() -> {
            if (recyclerView.findViewHolderForAdapterPosition(chooseansthemes[position] - 1) != null) {


                if (chooseansthemes[position] != 0) {
                    RecyclerView.ViewHolder ans_view = recyclerView.findViewHolderForAdapterPosition(chooseansthemes[position] - 1);
                    RecyclerView.ViewHolder right_ans = binding.ansRV.findViewHolderForAdapterPosition(PDD_DataBaseHelper.getCorrectans());
                    CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
                    CardView rightbutton = right_ans.itemView.findViewById(R.id.ans_card);
                    if (chooseansthemes[position] - 1 == PDD_DataBaseHelper.getCorrectans()) {
                        ansbutton.setCardBackgroundColor(Color.GREEN);
                    } else {
                        ansbutton.setCardBackgroundColor(Color.RED);
                        rightbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                    }
                    binding.btnnext.setVisibility(View.VISIBLE);
                    binding.explanation.setVisibility(View.VISIBLE);
                }

            } else {
                onHorizontalClick(handler, recyclerView, position);
            }
        });
    }

    public void onAnswerClick(final Handler handler, int question_number, int position) { // обработка нажатия на вариант ответа
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        handler.post(() -> {
            if (binding.horizontalRV.findViewHolderForAdapterPosition(question_number - 2) != null) {
                binding.horizontalRV.scrollToPosition(question_number - 1);
                RecyclerView.ViewHolder ans_view = binding.ansRV.findViewHolderForAdapterPosition(position);
                RecyclerView.ViewHolder right_ans = binding.ansRV.findViewHolderForAdapterPosition(PDD_DataBaseHelper.getCorrectans());
                CardView right_button = right_ans.itemView.findViewById(R.id.ans_card);
                CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
                RecyclerView.ViewHolder rv_view = binding.horizontalRV.findViewHolderForAdapterPosition(question_number - 2);
                CardView bt_view = rv_view.itemView.findViewById(R.id.horizontal_card);


                if (Ticket.getCount() > 1) {
                    ansbutton.setClickable(false);
                } else {
                    if (position == PDD_DataBaseHelper.getCorrectans()) {
                        ansbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        countans++;
                        dataBaseHelper.increaseKnowingID(mDBHelper.getId(i - 1, category));
                        dataBaseHelper.increaseCorrectAnswers();
                    } else {
                        ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                        right_button.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        dataBaseHelper.decreaseKnowingID(mDBHelper.getId(i - 1, category));
                        dataBaseHelper.increaseIncorrectAnswers();
                    }
                    binding.btnnext.setVisibility(View.VISIBLE);
                    binding.explanation.setVisibility(View.VISIBLE);

                    if (position == PDD_DataBaseHelper.getCorrectans()) {
                        bt_view.setCardBackgroundColor(Color.GREEN);
                    } else {
                        bt_view.setCardBackgroundColor(Color.RED);
                    }
                    chooseansthemes[question_number - 2] = position + 1;

                }
            } else {
                //
                onAnswerClick(handler, question_number, position);
            }
        });
    }

    @Override
    public void onClick(View view) { // обработка нажатия на звезду (Добавление в избранное)
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}