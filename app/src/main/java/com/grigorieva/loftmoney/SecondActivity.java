package com.grigorieva.loftmoney;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        int costId = getIntent().getIntExtra("COST_ID", 0);
        Log.e("TAG", "Cost Id = " + costId);
    }
}
