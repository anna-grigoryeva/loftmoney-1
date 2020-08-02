package com.grigorieva.loftmoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.grigorieva.loftmoney.remote.MoneyApi;
import com.grigorieva.loftmoney.remote.MoneyResponse;

public class MainActivity extends AppCompatActivity {

    public static final String EXPENSE = "expense";
    public static final String INCOME = "income";
    private static final String USER_ID = "grigorieva";
    public static final String TOKEN = "token";

    private MoneyApi moneyApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabs);

        final ViewPager viewPager = findViewById(R.id.viewpager);
        final BudgetPagerAdapter adapter = new BudgetPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(adapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                final int activeFragmentIndex = viewPager.getCurrentItem();
                Fragment activeFragment = getSupportFragmentManager().getFragments().get(activeFragmentIndex);
                activeFragment.startActivityForResult(new Intent(MainActivity.this, AddItemActivity.class),
                        BudgetFragment.REQUEST_CODE);
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.expenses);
        tabLayout.getTabAt(1).setText(R.string.income);

        moneyApi = ((LoftApp)getApplication()).getApi();

        final String token = PreferenceManager.getDefaultSharedPreferences(this).getString(TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            Call<MoneyResponse> auth = moneyApi.auth(USER_ID);
            auth.enqueue(new Callback<MoneyResponse>() {

                @Override
                public void onResponse(
                        final Call<MoneyResponse> call, final Response<MoneyResponse> response
                ) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                            MainActivity.this).edit();
                    editor.putString(TOKEN, response.body().getToken());
                    editor.apply();

                    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                        if (fragment instanceof BudgetFragment) {
                            ((BudgetFragment)fragment).loadItems();
                        }
                    }
                }

                @Override
                public void onFailure(final Call<MoneyResponse> call, final Throwable t) {

                }
            });
        }
    }

    static class BudgetPagerAdapter extends FragmentPagerAdapter {

        public BudgetPagerAdapter(@NonNull final FragmentManager fm, final int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(final int position) {
            switch (position) {
                case 0:
                    return BudgetFragment.newInstance(R.color.expenseColor, EXPENSE);
                case 1:
                    return BudgetFragment.newInstance(R.color.incomeColor, INCOME);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}