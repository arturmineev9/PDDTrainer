package com.example.autoschool11.ui.tickets;

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

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.autoschool11.MainActivity;
import com.example.autoschool11.R;
import com.example.autoschool11.adapters.HorizontalButtonAdapter;
import com.example.autoschool11.databinding.FragmentTicketBinding;
import com.example.autoschool11.db.PDD_DataBaseHelper;
import com.example.autoschool11.adapters.AnswersAdapter;
import com.example.autoschool11.db.DataBaseHelper;
import com.example.autoschool11.db.db_classes.DbButtonClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;

// Решение билета
public class Ticket extends Fragment implements AnswersAdapter.DbButtonClickListener, HorizontalButtonAdapter.HorizontalButtonClickListener, View.OnClickListener {
    ArrayList<DbButtonClass> dbButtonClassArrayList;
    static int i;
    int end;
    String ticket_number;
    String img;
    public PDD_DataBaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Context context;
    static int count;
    int ticketstart;
    int countans;
    int question_number = 1;
    int ticket_number_1;
    protected FragmentTicketBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ticketstart = getArguments().getInt("ticketstart");
            ticket_number = getArguments().getString("ticket");
            i = getArguments().getInt("ticketstart");
            end = getArguments().getInt("ticketend");
            ticket_number_1 = getArguments().getInt("ticket_number");
        }
    }

    public static void setI(int i) {
        Ticket.i = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Билет №" + ticket_number_1);
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

        // Верхняя панель навигации
        String[] numbers = new String[20];
        for (int j = 0; j < 20; j++) {
            numbers[j] = Integer.toString(j + 1);
        }

        HorizontalButtonAdapter horizontalButtonAdapter = new HorizontalButtonAdapter(numbers, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.horizontalRV.setLayoutManager(layoutManager);
        binding.horizontalRV.setItemViewCacheSize(20);
        binding.horizontalRV.setAdapter(horizontalButtonAdapter);

        ShowQuestion(i);
        binding.btnnext.setOnClickListener(view1 -> {

            if (i == end + 1) {
                for (int j = 0; j < 20; j++) {
                    if ((chooseans[j]) == 0) {
                        i = j + ticketstart;
                        question_number = j + 1;
                        ShowQuestion(i);
                        break;
                    }
                    if (j == 19) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("countans", countans);
                        bundle.putInt("ticket_number", Integer.parseInt(ticket_number));
                        bundle.putInt("countquestions", 20);
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                        navController.navigate(R.id.ticketEndFragment, bundle);
                    }

                }


                AnswersAdapter.setCountans(0);
            } else {
                if (chooseans[question_number - 1] != 0) {
                    int a = chooseans[question_number - 1];
                    while (a != 0) {
                        if (question_number != 20) {
                            i++;
                            question_number++;
                            a = chooseans[question_number - 1];
                        } else {
                            for (int j = 0; j < 20; j++) {
                                if ((chooseans[j]) == 0) {
                                    i = j + ticketstart;
                                    question_number = j + 1;
                                    binding.horizontalRV.scrollToPosition(i);
                                    ShowQuestion(i);
                                    break;
                                }
                            }
                        }
                    }
                }

                ShowQuestion(i);
            }
        });
        return view;
    }

    public static int getCount() {
        return count;
    }

    int[] chooseans = new int[20];

    @Override
    public void onButtonClick(int position) { // обработка нажатия на вариант ответа
        binding.horizontalRV.scrollToPosition(question_number - 2);
        if (count < 1) {
            onAnswerClick(new Handler(), i, question_number, position);
        }
        count++;


    }

    @Override
    public void onHorizontalButtonClick(int position) { // обработка нажатия на верхнюю панель навигации

        i = position + ticketstart;
        question_number = position + 1;
        ShowQuestion(ticketstart + position);
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

    public void onAnswerClick(final Handler handler, int id, int question_number, int position) { // обработка нажатия на вариант ответа
        handler.post(() -> {
            DataBaseHelper databaseHelper = new DataBaseHelper(getContext());
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
                //
                onAnswerClick(handler, id, question_number, position);
            }
        });
    }


    public void ShowQuestion(int a) { // показ вопроса
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
        binding.questionnumbertxt.setText("Вопрос " + question_number + " / " + 20);
        if (Integer.toString(question_number).length() == 1) {
            img = "pdd_" + ticket_number + "_0" + question_number;
        } else {
            img = "pdd_" + ticket_number + "_" + question_number;
        }
        question_number++;
        try {
            int id = getResources().getIdentifier("com.example.autoschool11:drawable/" + img, null, null);
            Toast toast = Toast.makeText(getContext(), id, Toast.LENGTH_SHORT);
            binding.dbImage.setImageResource(id);
        } catch (Exception e) {
            binding.dbImage.setVisibility(View.GONE);
        }


        dbButtonClassArrayList = mDBHelper.getAnswers(a);
        count = 0;
        mDBHelper.getQuestion(a);
        binding.explanation.setText(PDD_DataBaseHelper.getExplanation());
        binding.dbQuestion.setText(PDD_DataBaseHelper.getQuestion());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        AnswersAdapter answersAdapter = new AnswersAdapter(dbButtonClassArrayList, Ticket.this);
        binding.ansRV.setLayoutManager(linearLayoutManager);
        binding.ansRV.setAdapter(answersAdapter);
        i++;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings_menu, menu);
        for (int i = 0; i < menu.size(); i++)
            menu.getItem(i).setVisible(false);
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