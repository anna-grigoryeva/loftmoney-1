package com.grigorieva.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText NameEditText;
    private EditText ValueEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        NameEditText = findViewById(R.id.name_edittext);
        ValueEditText = findViewById(R.id.value_edittext);

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                String name = NameEditText.getText().toString();
                String value = ValueEditText.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(value)) {
                    setResult(
                            RESULT_OK,
                            new Intent().putExtra("name", name).putExtra("value", value));
                    finish();
                }
            };
        });};
}