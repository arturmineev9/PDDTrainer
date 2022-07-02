package com.example.autoschool11.ui.theory.pdd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.R;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.autoschool11.adapters.ButtonAdapter;
import com.example.autoschool11.databinding.FragmentRulesPDDBinding;

public class RulesFragmentPDD extends Fragment implements ButtonAdapter.ButtonClickListener {

    protected FragmentRulesPDDBinding binding;
    protected String rules = "theory";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRulesPDDBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        String[] array = getResources().getStringArray(R.array.rules_names);
        ButtonAdapter buttonAdapter = new ButtonAdapter(array, this);
        binding.rulesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rulesRV.setAdapter(buttonAdapter);
        return view;
    }

    @Override
    public void onButtonClick(int position) { // обработка нажатия на элемент в списке
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putString(rules, "PDD/1. Общие положения.pdf");
                break;
            case 1:
                bundle.putString(rules, "PDD/2. Общие обязанности водителей.pdf");
                break;
            case 2:
                bundle.putString(rules, "PDD/3. Применение специальных сигналов.pdf");
                break;
            case 3:
                bundle.putString(rules, "PDD/4. Обязанности пешеходов.pdf");
                break;
            case 4:
                bundle.putString(rules, "PDD/5. Обязанности пассажиров.pdf");
                break;
            case 5:
                bundle.putString(rules, "PDD/6. Сигналы светофора и регулировщика.pdf");
                break;
            case 6:
                bundle.putString(rules, "PDD/7. Применение аварийной сигнализации и знака аварийной остановки.pdf");
                break;
            case 7:
                bundle.putString(rules, "PDD/8. Начало движения, маневрирование.pdf");
                break;
            case 8:
                bundle.putString(rules, "PDD/9. Расположение транспортных средств на проезжей части.pdf");
                break;
            case 9:
                bundle.putString(rules, "PDD/10. Скорость движения.pdf");
                break;
            case 10:
                bundle.putString(rules, "PDD/11. Обгон, опережение, встречный разъезд.pdf");
                break;
            case 11:
                bundle.putString(rules, "PDD/12. Остановка и стоянка.pdf");
                break;
            case 12:
                bundle.putString(rules, "PDD/13. Проезд перекрестков.pdf");
                break;
            case 13:
                bundle.putString(rules, "PDD/14. Пешеходные переходы и места остановок маршрутных транспортных средств.pdf");
                break;
            case 14:
                bundle.putString(rules, "PDD/15. Движение через железнодорожные пути.pdf");
                break;
            case 15:
                bundle.putString(rules, "PDD/16. Движение по автомагистралям.pdf");
                break;
            case 16:
                bundle.putString(rules, "PDD/17. Движение в жилых зонах.pdf");
                break;
            case 17:
                bundle.putString(rules, "PDD/18. Приоритет маршрутных транспортных средств.pdf");
                break;
            case 18:
                bundle.putString(rules, "PDD/19. Пользование внешними световыми приборами и звуковыми сигналами.pdf");
                break;
            case 19:
                bundle.putString(rules, "PDD/20. Буксировка механических транспортных средств.pdf");
                break;
            case 20:
                bundle.putString(rules, "PDD/21. Учебная езда.pdf");
                break;
            case 21:
                bundle.putString(rules, "PDD/22. Перевозка людей.pdf");
                break;
            case 22:
                bundle.putString(rules, "PDD/23. Перевозка грузов.pdf");
                break;
            case 23:
                bundle.putString(rules, "PDD/24. Дополнительные требования к движению велосипедистов и водителей мопедов.pdf");
                break;
            case 24:
                bundle.putString(rules, "PDD/25. Дополнительные требования к движению гужевых повозок, а также прогону животных.pdf");
                break;
            case 25:
                bundle.putString(rules, "PDD/Основные положения по допуску транспортных средств к эксплуатации.pdf");
                break;
            case 26:
                bundle.putString(rules, "PDD/Перечень неисправностей и условий, при которых запрещается эксплуатация транспортных средств.pdf");


        }
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.rulesFragment1, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


