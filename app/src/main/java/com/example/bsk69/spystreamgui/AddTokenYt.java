package com.example.bsk69.spystreamgui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.http.GET;

public class AddTokenYt extends AppCompatActivity implements View.OnClickListener {

    private EditText input_token;
    private Button addTokenBtn,backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_token_yt);

        input_token = (EditText)findViewById(R.id.insert_token_yt);
        addTokenBtn = (Button)findViewById(R.id.add_token_button);
        backBtn = (Button)findViewById(R.id.back);

        addTokenBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.back){
            Intent intent = new Intent(AddTokenYt.this,DashBoard.class);
            startActivity(intent);
            finish();
        }
    }
}
