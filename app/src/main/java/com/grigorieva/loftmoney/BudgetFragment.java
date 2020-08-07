package com.grigorieva.loftmoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import retrofit2.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.grigorieva.loftmoney.cells.money.MoneyAdapter;
import com.grigorieva.loftmoney.cells.money.MoneyCellModel;
import com.grigorieva.loftmoney.remote.AuthResponse;
import com.grigorieva.loftmoney.remote.MoneyApi;
import com.grigorieva.loftmoney.remote.MoneyItem;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;


public class BudgetFragment extends Fragment {

    public static final int REQUEST_CODE = 100;
    private static final String COLOR_ID = "colorId";
    private static final String TYPE = "fragmentType";

    private MoneyAdapter moneyAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MoneyApi moneyApi;

    public static BudgetFragment newInstance(final int colorId, final String type) {
        BudgetFragment budgetFragment = new BudgetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(COLOR_ID, colorId);
        bundle.putString(TYPE, type);
        budgetFragment.setArguments(bundle);
        return budgetFragment;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moneyApi = ((LoftApp)getActivity().getApplication()).getMoneyApi();
        loadItems();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_budget, null);

        RecyclerView recyclerView = view.findViewById(R.id.budget_item_list);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

        moneyAdapter = new MoneyAdapter(getArguments().getInt(COLOR_ID));
        recyclerView.setAdapter(moneyAdapter);

        return view;
    }

    @Override
    public void onActivityResult(
            final int requestCode, final int resultCode, @Nullable final Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
        int value;
        try {
            value = Integer.parseInt(data.getStringExtra("value"));
        } catch (NumberFormatException e) {
            value = 0;
        }
        final int realValue = value;
        final String name = data.getStringExtra("name");

        final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(MainActivity.TOKEN, "");

        Call<AuthResponse> call = moneyApi.addItem(new MoneyItem(name, getArguments().getString(TYPE), value), token);
        call.enqueue(new Callback<AuthResponse>() {

                @Override
                public void onResponse(
                        final Call<AuthResponse> call, final Response<AuthResponse> response
                ) {
                    if (response.body().getStatus().equals("success")) {
                        loadItems();
                    }
                }

                @Override
                public void onFailure(final Call<AuthResponse> call, final Throwable t) {
                    t.printStackTrace();
                }
            });

        }
    }

    public void loadItems() {
        final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(MainActivity.TOKEN, "");

        final Call<List<MoneyCellModel>> moneyCellModels = moneyApi.getItems(getArguments().getString(TYPE), token);
        moneyCellModels.enqueue(new Callback<List<MoneyCellModel>>() {

            @Override
            public void onResponse(
                    final Call<List<MoneyCellModel>> call, final Response<List<MoneyCellModel>> response
            ) {
                moneyAdapter.clearItems();
                swipeRefreshLayout.setRefreshing(false);
                List<MoneyCellModel> moneyCellModels = response.body();
                for (MoneyCellModel moneyCellModel : moneyCellModels) {
                    moneyAdapter.addData(moneyCellModel);
                }
            }

            @Override
            public void onFailure(final Call<List<MoneyCellModel>> call, final Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }}
