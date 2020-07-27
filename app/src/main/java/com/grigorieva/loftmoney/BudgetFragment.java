package com.grigorieva.loftmoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.grigorieva.loftmoney.cells.money.MoneyAdapter;
import com.grigorieva.loftmoney.cells.money.MoneyCellModel;

import java.util.List;


public class BudgetFragment extends Fragment {

    RecyclerView recyclerView;
    private static final int REQUEST_CODE = 100;
    private MoneyAdapter moneyAdapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_budget, null);

        Button callAddButton = view.findViewById(R.id.call_add_item_activity);
        callAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivityForResult(new Intent(getActivity(), AddItemActivity.class),
                        REQUEST_CODE);
            }
        });

        recyclerView = view.findViewById(R.id.budget_item_list);
        moneyAdapter = new MoneyAdapter();
        recyclerView.setAdapter(moneyAdapter);

        moneyAdapter.addData(new MoneyCellModel("Молоко", 70));
        moneyAdapter.addData(new MoneyCellModel("Зубная щетка", 70));
        moneyAdapter.addData((new MoneyCellModel("Сковородка с антипригарным покрытием", 1670)));

        return view;
    }

    @Override
    public void onActivityResult(
            final int requestCode, final int resultCode, @Nullable final Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);
        int value;
        try {
            value = Integer.parseInt(data.getStringExtra("value"));
        } catch (NumberFormatException e) {
            value = 0;
        }
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            moneyAdapter.addData(new MoneyCellModel(data.getStringExtra("name"), value));
        }
    }
}
