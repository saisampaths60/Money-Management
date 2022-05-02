package com.daily.usage.moneymanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    EditText amountText, descriptionText;
    Spinner transactionStateSpinner, sourceSpinner;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        transactionStateSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AddTransactionActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.transactionStates));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionStateSpinner.setAdapter(adapter1);

        sourceSpinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AddTransactionActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sources));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(adapter2);

        amountText = (EditText) findViewById(R.id.editText);

        descriptionText = (EditText) findViewById(R.id.editText2);

        submitButton = (Button) findViewById(R.id.button);
        submitButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == submitButton) {
            addTransaction();
        }
    }

    private void addTransaction() {

        final ProgressDialog loading = ProgressDialog.show(this, "Adding Transaction", "Please wait....!");
        final double amount = Double.parseDouble(amountText.getText().toString().trim());
        final String description = descriptionText.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzroBgXf06IBgYgueuBr-JJoMZDGA21jZewMYZcWkTUGLPJXV7zfpe_EC5jFUIbaRTjVA/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(AddTransactionActivity.this, response, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("action","addTransaction");
                params.put("transactionState", transactionStateSpinner.getSelectedItem().toString());
                params.put("source", sourceSpinner.getSelectedItem().toString());
                params.put("amount", "" + amount);
                params.put("description", description);

                return params;
            }
        };

        int socketTimeOut = 50000;

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
