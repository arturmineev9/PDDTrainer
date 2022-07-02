package com.example.autoschool11.ui.theory.medicine;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoschool11.adapters.ButtonAdapter;
import com.example.autoschool11.R;
import com.example.autoschool11.databinding.FragmentMedicineBinding;


public class MedicineFragment extends Fragment implements ButtonAdapter.ButtonClickListener {

    Context context;
    private static final String medicine = "medicine";
    protected FragmentMedicineBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMedicineBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        String[] rules = getResources().getStringArray(R.array.medicine);
        ButtonAdapter buttonAdapter = new ButtonAdapter(rules, this);
        binding.rulesRV.setLayoutManager(new LinearLayoutManager(context));
        binding.rulesRV.setAdapter(buttonAdapter);
        return view;
    }

    @Override
    public void onButtonClick(int position) { // обработка нажатия на элемент в списке
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putString(medicine, "Medicine/1. Первая медицинская помощь при ДТП.pdf");
                break;
            case 1:
                bundle.putString(medicine, "Medicine/2. Первоначальные действия на месте ДТП.pdf");
                break;
            case 2:
                bundle.putString(medicine, "Medicine/3. Определение состояния пострадавшего.pdf");
                break;
            case 3:
                bundle.putString(medicine, "Medicine/4. Оказание доврачебной помощи пострадавшему, находящемуся в состоянии комы или клинической смерти.pdf");
                break;
            case 4:
                bundle.putString(medicine, "Medicine/5. Оказание доврачебной помощи при ранах и кровотечениях.pdf");
                break;
            case 5:
                bundle.putString(medicine, "Medicine/6. Оказание доврачебной помощи при ушибах, вывихах и переломах.pdf");
                break;
            case 6:
                bundle.putString(medicine, "Medicine/7. Оказание доврачебной помощи при утоплении, отравлении угарным газом.pdf");
                break;
            case 7:
                bundle.putString(medicine, "Medicine/8. Оказание доврачебной помощи при ожогах.pdf");
                break;
            case 8:
                bundle.putString(medicine, "Medicine/9. Перенос и транспортировка пострадавших.pdf");
                break;
            case 9:
                bundle.putString(medicine, "Medicine/10. Автомобильная медицинская аптечка.pdf");
                break;

        }
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.medicineFragment1, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
