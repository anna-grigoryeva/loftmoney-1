package com.grigorieva.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(getApplicationContext(), SecondActivity.class);
                newActivity.putExtra("COST_ID", 1);
                startActivity(newActivity);
            }
        });

        // TODO: Here we will work with recyclerview
    }
}