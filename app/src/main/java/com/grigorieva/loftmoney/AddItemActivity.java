package com.grigorieva.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText NameEditText;
    private EditText ValueEditText;
    private Button AddButton;

    private String Name;
    private String Value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        NameEditText = findViewById(R.id.name_edittext);
        NameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Name = editable.toString();
                checkEditTextHasText();
            }
        });

        ValueEditText = findViewById(R.id.value_edittext);
        ValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Value = editable.toString();
                checkEditTextHasText();
            }
        });

        AddButton = findViewById(R.id.add_button);
        AddButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
               if (!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Value)) {
                    setResult(
                            RESULT_OK,
                            new Intent().putExtra("name", Name).putExtra("value", Value));
                    finish();
                }
            }
        });}
        public void checkEditTextHasText() {
            AddButton.setEnabled(!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Value));
        }
}