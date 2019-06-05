package com.example.bsk69.spystreamgui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.text.format.DateFormat;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.util.List;
import java.util.Locale;

public class DashBoard extends AppCompatActivity implements View.OnClickListener {

    private Button tokenYtBtn,tokenTwitchBtn,chatBtn;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        Toolbar toolbar = findViewById(R.id.toolbar);
        tokenYtBtn = (Button)findViewById(R.id.add_token_yt_btn);
        tokenTwitchBtn = (Button)findViewById(R.id.add_token_twitch_btn);
        chatBtn = (Button)findViewById(R.id.chat);

        setSupportActionBar(toolbar);

        tokenYtBtn.setOnClickListener(this);
        tokenTwitchBtn.setOnClickListener(this);
        chatBtn.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dash_board_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Toast.makeText(DashBoard.this, "Zostałeś poprawnie wylogowany !", Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(DashBoard.this,MainActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.settings) {
            Toast.makeText(DashBoard.this, "Tym przyciskiem wejdziesz w ustawienia", Toast.LENGTH_LONG).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_token_yt_btn){
            Intent intent = new Intent(DashBoard.this,AddTokenYt.class);
            startActivity(intent);
            finish();
        }
        else if (view.getId() == R.id.add_token_twitch_btn){
            Intent intent = new Intent(DashBoard.this,AddTokenTwitch.class);
            startActivity(intent);
            finish();
        }
        else if (view.getId()==R.id.chat){
            Intent intent = new Intent(DashBoard.this,ActivityChat.class);
            startActivity(intent);
            finish();
        }
    }



}
