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

// работа над ошибками
public class MistakesFragment extends Fragment implements AnswersAdapter.DbButtonClickListener, HorizontalButtonAdapter.HorizontalButtonClickListener, View.OnClickListener {
    int countans;
    ArrayList<DbButtonClass> dbButtonClassArrayList;
    static int i = 1;
    String img;
    public PDD_DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    int[] chooseansthemes;
    static int count;
    ArrayList<Integer> correctmistakes;
    int question_number = 1;
    int question_number0 = 0;
    protected FragmentTicketBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTicketBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        correctmistakes = new ArrayList<>();
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

        ShowMistakesQuestion(databaseHelper.getMistakesId(question_number0));

        //верхняя панель навигации
        String[] numbers = new String[databaseHelper.getMistakesTableLength()];
        for (int j = 0; j < databaseHelper.getMistakesTableLength(); j++) {
            numbers[j] = Integer.toString(j + 1);
        }
        chooseansthemes = new int[databaseHelper.getMistakesTableLength()];


        HorizontalButtonAdapter horizontalButtonAdapter = new HorizontalButtonAdapter(numbers, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.horizontalRV.setLayoutManager(layoutManager);
        binding.horizontalRV.setItemViewCacheSize(databaseHelper.getMistakesTableLength());
        binding.horizontalRV.setAdapter(horizontalButtonAdapter);


        binding.btnnext.setOnClickListener(view1 -> { // обработка нажатия "Далее"
            if (question_number == databaseHelper.getMistakesTableLength() + 1) { // проверка на последний вопрос
                for (int j = 0; j < databaseHelper.getMistakesTableLength(); j++) {
                    if ((chooseansthemes[j]) == 0) {
                        i = databaseHelper.getMistakesId(j);
                        ShowMistakesQuestion(i);
                        break;
                    }
                    if (j == databaseHelper.getMistakesTableLength() - 1) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("countans", countans);
                        bundle.putInt("countquestions", databaseHelper.getMistakesTableLength());
                        databaseHelper.deleteMistakes(correctmistakes);
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                        navController.navigate(R.id.ticketEndFragment, bundle);
                    }
                }

                countans = 0;
            } else {
                if (chooseansthemes[question_number - 1] != 0) {
                    int a = chooseansthemes[question_number - 1];
                    while (a != 0) {
                        i = databaseHelper.getMistakesId(question_number);
                        question_number++;
                        a = chooseansthemes[question_number - 1];

                    }
                }
                ShowMistakesQuestion(i);
            }
        });
        return view;
    }


    public void ShowMistakesQuestion(int id) { // показ вопроса
        DataBaseHelper databaseHelper = new DataBaseHelper(getContext());
        if (databaseHelper.isInFavourites(i)) {
            binding.favouritesImage.setImageResource(R.drawable.star_pressed);
            binding.favouritesTxt.setText("Удалить из избранного");
        } else {
            binding.favouritesImage.setImageResource(R.drawable.star_button);
            binding.favouritesTxt.setText("Добавить в избранное");
        }
        binding.questionnumbertxt.setText("Вопрос " + question_number + "/" + databaseHelper.getMistakesTableLength());
        binding.dbImage.setVisibility(View.VISIBLE);
        binding.btnnext.setVisibility(View.GONE);
        binding.explanation.setVisibility(View.GONE);
        binding.dbQuestion.setText(databaseHelper.getMistakeQuestions(id, getContext()));

        if (Integer.toString(PDD_DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(PDD_DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd" + "_0" + (PDD_DataBaseHelper.getBilet() + 1) + "_0" + (PDD_DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(PDD_DataBaseHelper.getBilet() + 1).length() != 1 && Integer.toString(PDD_DataBaseHelper.getNumber() + 1).length() == 1) {
            img = "pdd_" + (PDD_DataBaseHelper.getBilet() + 1) + "_0" + (PDD_DataBaseHelper.getNumber() + 1);
        } else if (Integer.toString(PDD_DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(PDD_DataBaseHelper.getNumber() + 1).length() != 1) {
            img = "pdd_0" + (PDD_DataBaseHelper.getBilet() + 1) + "_" + (PDD_DataBaseHelper.getNumber() + 1);
        } else
            img = "pdd_" + (PDD_DataBaseHelper.getBilet() + 1) + "_" + (PDD_DataBaseHelper.getNumber() + 1);
        try {
            int idphoto = getResources().getIdentifier("com.example.autoschool11:drawable/" + img, null, null);
            Toast toast = Toast.makeText(getContext(), idphoto, Toast.LENGTH_SHORT);
            binding.dbImage.setImageResource(idphoto);
        } catch (Exception e) {
            binding.dbImage.setVisibility(View.GONE);
        }
        dbButtonClassArrayList = databaseHelper.getMistakesAnswers(id, getContext());
        binding.explanation.setText(DataBaseHelper.getExplanation());
        count = 0;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        AnswersAdapter answersAdapter = new AnswersAdapter(dbButtonClassArrayList, this);
        binding.ansRV.setLayoutManager(linearLayoutManager);
        binding.ansRV.setAdapter(answersAdapter);
        if (question_number < databaseHelper.getMistakesTableLength()) {
            question_number0++;
            question_number++;
            if (question_number0 != 0) {
                i = databaseHelper.getMistakesId(question_number0);
            }
        } else {
            question_number++;
        }

    }

    @Override
    public void onButtonClick(int position) { // обработка нажатия на вариант ответа
        binding.horizontalRV.scrollToPosition(question_number - 2);
        if (count < 1) {
            onAnswerClick(new Handler(), question_number, position);
        }
        count++;

    }

    @Override
    public void onHorizontalButtonClick(int position) { // обработка нажатия на верхнюю панель навигации
        DataBaseHelper databaseHelper = new DataBaseHelper(getContext());
        i = databaseHelper.getMistakesPositionId(position);
        question_number = position + 1;
        question_number0 = position;
        ShowMistakesQuestion(i);

        onHorizontalClick(new Handler(), binding.ansRV, position);
    }

    protected void onHorizontalClick(Handler handler, RecyclerView recyclerView, int position) { // обработка нажатия на верхнюю панель навигации

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
                    binding.explanation.setVisibility(View.VISIBLE);
                    binding.btnnext.setVisibility(View.VISIBLE);
                }

            } else {
                //
                onHorizontalClick(handler, recyclerView, position);
            }
        });
    }

    public static void setI(int i) {
        MistakesFragment.i = i;
    }

    public void onAnswerClick(Handler handler, int question_number, int position) { // обработка нажатия на вариант ответа
        DataBaseHelper databaseHelper = new DataBaseHelper(getContext());
        handler.post(() -> {
            if (binding.horizontalRV.findViewHolderForAdapterPosition(question_number - 2) != null) {
                binding.horizontalRV.scrollToPosition(question_number - 2);
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
                        correctmistakes.add(databaseHelper.getMistakesId(question_number - 2));
                        countans++;
                        databaseHelper.increaseCorrectAnswers();
                    } else {
                        ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                        right_button.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        databaseHelper.increaseIncorrectAnswers();
                    }
                    binding.explanation.setVisibility(View.VISIBLE);
                    binding.btnnext.setVisibility(View.VISIBLE);

                    if (position == PDD_DataBaseHelper.getCorrectans()) {
                        bt_view.setCardBackgroundColor(Color.GREEN);
                    } else {
                        bt_view.setCardBackgroundColor(Color.RED);
                    }
                    chooseansthemes[question_number - 2] = position + 1;


                }
            } else {

                onAnswerClick(handler, question_number, position);
            }
        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}