package com.example.bsk69.spystreamgui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;


import retrofit2.http.GET;

public class AddTokenTwitch extends AppCompatActivity implements View.OnClickListener {

    private EditText input_token;
    private Button addTokenBtn,backBtn;
   /* FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataRef = database.getReference("tokeny");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_token_twitch);

        input_token = (EditText)findViewById(R.id.insert_token_twitch);
        addTokenBtn = (Button)findViewById(R.id.add_token_button);
        backBtn = (Button)findViewById(R.id.back);

        addTokenBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_token_button) {
            if(input_token.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Błąd: Nie podałeś tokenu do twitcha !",Toast.LENGTH_SHORT ).show();
                return;
            }
            else{
                checkToken(input_token.getText().toString());
            }
        }
        else if(view.getId() == R.id.back){
            Intent intent = new Intent(AddTokenTwitch.this,DashBoard.class);
            startActivity(intent);
            finish();
        }
    }

    private void checkToken(String token) {

        /*TwitchClient twitchClient = TwitchClientBuilder.init()
                .withClientId("Twitch App Id")
                .withClientSecret("Twitch App Secret")
                .withAutoSaveConfiguration(true)
                .withConfigurationDirectory(new File("config"))
                .withCredential(token) // Get your token at: https://twitchapps.com/tmi/
                .connect();
        String id = dataRef.push().getKey();
        UsersPlatform users = new UsersPlatform(id,token);
        dataRef.child(id).setValue(users);*/
    }
}
