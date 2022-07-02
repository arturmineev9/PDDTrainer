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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.concurrent.TimeUnit;


public class ExamFragment extends Fragment implements AnswersAdapter.DbButtonClickListener, HorizontalButtonAdapter.HorizontalButtonClickListener, View.OnClickListener {

    ArrayList<DbButtonClass> dbButtonClassArrayList;
    static int i;
    String img;
    long timer = 1200000;
    public PDD_DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    static int count;
    int amount_of_questions = 20;
    int countans;
    int question_number = 1;
    int[] questions = new int[30];
    int random;
    int number;
    String[] numbers_add;
    ArrayList<Integer> mistakes;
    boolean isAddQuestions = false;
    protected FragmentTicketBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentTicketBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);
        mistakes = new ArrayList<>();
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
        String[] numbers = new String[20];
        for (int j = 0; j < 20; j++) {
            numbers[j] = Integer.toString(j + 1);
        }

        HorizontalButtonAdapter horizontalButtonAdapter = new HorizontalButtonAdapter(numbers, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.horizontalRV.setLayoutManager(layoutManager);
        binding.horizontalRV.setItemViewCacheSize(20);
        binding.horizontalRV.setAdapter(horizontalButtonAdapter);
        for (int j = 0; j < 20; j++) {
            if (j == 0 || j == 5 || j == 10 || j == 15) {
                random = (int) ((Math.random() * 40));
            }
            questions[j] = j + random * 20 + 1;
        }

        ShowQuestion(questions[0]);
        binding.btnnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) { // обработка нажатия на кнопку "Далее"
                if (!isAddQuestions) {
                    if (question_number == 21) { // проверка на последний вопрос в билете
                        for (int j = 0; j < 20; j++) {
                            if ((chooseans[j]) == 0) {
                                question_number = j + 1;
                                ShowQuestion(questions[question_number - 1]);
                                break;
                            }
                            if (j == 19) {
                                if (20 - countans == 1) { // ошибка в 1 вопросе
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
                                    binding.horizontalRV.setLayoutManager(layoutManager);
                                    binding.horizontalRV.setItemViewCacheSize(numbers_add.length);
                                    binding.horizontalRV.setAdapter(horizontalButtonAdapter);
                                    isAddQuestions = true;

                                } else if (20 - countans == 2) { // ошибка в двух вопросах
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
                                    binding.horizontalRV.setLayoutManager(layoutManager);
                                    binding.horizontalRV.setItemViewCacheSize(numbers_add.length);
                                    binding.horizontalRV.setAdapter(horizontalButtonAdapter);
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

                                ShowQuestion(questions[19]);
                                question_number = 21;
                            }

                        }

                        AnswersAdapter.setCountans(0);
                    } else {
                        if (chooseans[question_number - 1] != 0) { // проверка на нерешенные вопросы
                            int a = chooseans[question_number - 1];
                            while (a != 0) {
                                if (question_number != 20) {
                                    question_number++;
                                    a = chooseans[question_number - 1];
                                } else {
                                    for (int j = 0; j < 20; j++) {
                                        if ((chooseans[j]) == 0) {
                                            question_number = j + 1;
                                            binding.horizontalRV.scrollToPosition(i);
                                            ShowQuestion(questions[question_number - 1]);
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
                                bundle.putInt("type_of_fail", 0); // экзамен сдан
                                bundle.putInt("countquestions", 30);
                                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                                navController.navigate(R.id.examEndFragment, bundle);
                            }
                        }
                    }
                }
                ShowQuestion(questions[question_number - 1]);
            }
        });
        return view;
    }

    public static int getCount() {
        return count;
    }

    int[] chooseans = new int[30];

    @Override
    public void onButtonClick(int position) { // обработка нажатия на вариант ответа
        binding.horizontalRV.scrollToPosition(question_number - 2);
        if (isAddQuestions) {
            number = question_number - 20;
        } else number = question_number;
        if (count < 1) {
            onAnswerClick(new Handler(), questions[question_number - 2], number, position);
        }
        count++;


    }

    @Override
    public void onHorizontalButtonClick(int position) { // обработка нажатия на верхнюю панель навигации
        i = position;
        if (isAddQuestions) {
            question_number = position + 21;
        } else question_number = position + 1;
        ShowQuestion(questions[question_number - 1]);
        onHorizontalClick(new Handler(), binding.ansRV, question_number);

    }

    protected void onHorizontalClick(Handler handler, RecyclerView recyclerView, int question_number) { // обработка нажатия на верхнюю панель навигации

        handler.post(() -> {
            if (recyclerView.findViewHolderForLayoutPosition(chooseans[question_number - 2] - 1) != null) {
                if (chooseans[question_number - 2] != 0) {
                    RecyclerView.ViewHolder ans_view = recyclerView.findViewHolderForLayoutPosition(chooseans[question_number - 2] - 1);
                    RecyclerView.ViewHolder right_ans = binding.ansRV.findViewHolderForAdapterPosition(PDD_DataBaseHelper.getCorrectans());
                    CardView ansbutton = ans_view.itemView.findViewById(R.id.ans_card);
                    CardView rightbutton = right_ans.itemView.findViewById(R.id.ans_card);
                    count = 1;
                    if (chooseans[question_number - 2] - 1 == PDD_DataBaseHelper.getCorrectans()) {
                        ansbutton.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                    } else {
                        ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
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

    public void onAnswerClick(Handler handler, int id, int question_number, int position) {  // обработка нажатия на вариант ответа
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
                        databaseHelper.increaseCorrectAnswers();
                    } else {
                        ansbutton.setCardBackgroundColor(Color.argb(255, 255, 0, 0));
                        right_button.setCardBackgroundColor(Color.argb(255, 92, 184, 92));
                        databaseHelper.insertMistake(id - 1);
                        databaseHelper.decreaseKnowingID(id - 1);
                        databaseHelper.increaseIncorrectAnswers();
                        mistakes.add(question_number - 1);
                    }
                    binding.btnnext.setVisibility(View.VISIBLE);
                    binding.explanation.setVisibility(View.VISIBLE);
                    databaseHelper.increaseKnowingID(id - 1);
                }


                if (position == PDD_DataBaseHelper.getCorrectans()) {
                    bt_view.setCardBackgroundColor(Color.GREEN);
                } else {
                    bt_view.setCardBackgroundColor(Color.RED);
                }
                chooseans[question_number - 2] = position + 1;


            } else {
                onAnswerClick(handler, id, question_number, position);
            }
        });
    }


    public void ShowQuestion(int a) { // показ вопроса
        binding.horizontalRV.scrollToPosition(question_number - 2);
        binding.explanation.setVisibility(View.GONE);
        binding.btnnext.setVisibility(View.GONE);
        binding.dbImage.setVisibility(View.VISIBLE);
        mDBHelper.getQuestion(a);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        if (dataBaseHelper.isInFavourites(i)) {
            binding.favouritesImage.setImageResource(R.drawable.star_pressed);
            binding.favouritesTxt.setText("Удалить из избранного");
        } else {
            binding.favouritesImage.setImageResource(R.drawable.star_button);
            binding.favouritesTxt.setText("Добавить в избранное");
        }
        binding.questionnumbertxt.setText("Вопрос " + question_number + " / " + amount_of_questions);
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
        AnswersAdapter answersAdapter = new AnswersAdapter(dbButtonClassArrayList, ExamFragment.this);
        binding.ansRV.setLayoutManager(linearLayoutManager);
        binding.ansRV.setAdapter(answersAdapter);
        question_number++;
        i++;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) { // таймер
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