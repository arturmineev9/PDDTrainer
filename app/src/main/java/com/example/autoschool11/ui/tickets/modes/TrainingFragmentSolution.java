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

// Умная тренировка
public class TrainingFragmentSolution extends Fragment implements AnswersAdapter.DbButtonClickListener, HorizontalButtonAdapter.HorizontalButtonClickListener, View.OnClickListener {
    int knowing_id;
    ArrayList<Integer> increase_id;
    ArrayList<Integer> decrease_id;
    ArrayList<DbButtonClass> dbButtonClassArrayList;
    static int i;
    int table_length;
    String img;
    public PDD_DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    int[] chooseansthemes;
    static int count;
    int countans;
    static int question_number = 1;
    int question_number0 = 0;
    String[] numbers;
    protected FragmentTicketBinding binding;

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
        binding = FragmentTicketBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);

        mDBHelper = new PDD_DataBaseHelper(getContext());
        increase_id = new ArrayList<>();
        decrease_id = new ArrayList<>();

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

        ShowTrainingQuestion(question_number);

        // Верхняя панель навигации
        if (databaseHelper.getTrainingTableLength(knowing_id) > 21) {
            numbers = new String[20];
            for (int j = 0; j < 20; j++) {
                numbers[j] = Integer.toString(j + 1);
            }
            table_length = 20;
            chooseansthemes = new int[20];
        } else {
            numbers = new String[databaseHelper.getTrainingTableLength(knowing_id)];
            for (int j = 0; j < databaseHelper.getTrainingTableLength(knowing_id); j++) {
                numbers[j] = Integer.toString(j + 1);
            }
            table_length = databaseHelper.getTrainingTableLength(knowing_id);
            chooseansthemes = new int[databaseHelper.getTrainingTableLength(knowing_id)];
        }


        HorizontalButtonAdapter horizontalButtonAdapter = new HorizontalButtonAdapter(numbers, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.horizontalRV.setLayoutManager(layoutManager);
        binding.horizontalRV.setItemViewCacheSize(20);
        binding.horizontalRV.setAdapter(horizontalButtonAdapter);


        binding.btnnext.setOnClickListener(view1 -> { // обработка нажатия кнопки "Далее"
            if (question_number == table_length + 1) { // проверка на последний вопрос
                for (int j = 0; j < table_length; j++) { // проверка на нерешенные вопросы
                    if ((chooseansthemes[j]) == 0) {
                        i = j;
                        ShowTrainingQuestion(i + 1);
                        break;
                    }
                    if (j == table_length - 1) {
                        DataBaseHelper databaseHelper1 = new DataBaseHelper(getContext());
                        Bundle bundle = new Bundle();
                        bundle.putInt("countans", countans);
                        bundle.putInt("countquestions", table_length);
                        bundle.putInt("isTraining", 1);
                        databaseHelper1.increaseArrayId(increase_id);
                        databaseHelper1.decreaseArrayId(decrease_id);
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
                ShowTrainingQuestion(question_number);
            }
        });
        return view;
    }

    public void ShowTrainingQuestion(int a) { // показ вопроса
        binding.dbImage.setVisibility(View.VISIBLE);
        binding.explanation.setVisibility(View.GONE);
        binding.btnnext.setVisibility(View.GONE);
        DataBaseHelper databaseHelper = new DataBaseHelper(getContext());
        binding.dbQuestion.setText(databaseHelper.getTrainingQuestions(knowing_id, databaseHelper.getTrainingId(a, knowing_id), getContext())); // вопрос
        mDBHelper.getTicketNumber(databaseHelper.getTrainingId(a, knowing_id));
        binding.explanation.setText(PDD_DataBaseHelper.getExplanation()); // объяснение
        DataBaseHelper favouritesDataBaseHelper = new DataBaseHelper(getContext());
        if (favouritesDataBaseHelper.isInFavourites(PDD_DataBaseHelper.get_id())) {
            binding.favouritesImage.setImageResource(R.drawable.star_pressed);
            binding.favouritesTxt.setText("Удалить из избранного");
        } else {
            binding.favouritesImage.setImageResource(R.drawable.star_button);
            binding.favouritesTxt.setText("Добавить в избранное");
        }
        if (Integer.toString(PDD_DataBaseHelper.getBilet() + 1).length() == 1 && Integer.toString(PDD_DataBaseHelper.getNumber() + 1).length() == 1) { // изображение
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


        dbButtonClassArrayList = databaseHelper.getTrainingAnswers(databaseHelper.getTrainingId(question_number, knowing_id), getContext()); // варианты ответа
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
        question_number++;
        i++;
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
        i = position;
        question_number = position + 1;
        question_number0 = position;
        ShowTrainingQuestion(question_number);
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
                }

            } else {
                //
                onHorizontalClick(handler, recyclerView, position);
            }
        });
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
                        countans++;
                        databaseHelper.insertMistake(databaseHelper.getTrainingId(question_number - 1, knowing_id));
                        increase_id.add(databaseHelper.getTrainingId(question_number - 1, knowing_id));
                        databaseHelper.increaseCorrectAnswers();
                    } else {
                        ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                        right_button.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        decrease_id.add(databaseHelper.getTrainingId(question_number - 1, knowing_id));
                        databaseHelper.increaseIncorrectAnswers();
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

    public static void setQuestion_number(int question_number) {
        TrainingFragmentSolution.question_number = question_number;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}