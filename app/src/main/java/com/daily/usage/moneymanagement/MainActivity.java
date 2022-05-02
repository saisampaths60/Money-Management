package com.daily.usage.moneymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button addTransactionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTransactionButton = (Button) findViewById(R.id.button);
        addTransactionButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == addTransactionButton) {
            Intent intent = new Intent(getApplicationContext(),AddTransactionActivity.class);
            startActivity(intent);
        }
    }
}
