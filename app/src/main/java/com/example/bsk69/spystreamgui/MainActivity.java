package com.example.bsk69.spystreamgui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText input_email, input_pass;
    TextView btnSignup, btnForgotPass;

    RelativeLayout activity_main;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Widok
        btnLogin = (Button)findViewById(R.id.email_sign_in_button);
        input_email = (EditText)findViewById(R.id.email);
        input_pass = (EditText)findViewById(R.id.password);
        btnSignup = (Button)findViewById(R.id.register_btn);
        btnForgotPass = (TextView)findViewById(R.id.forgot_pass_button);
        activity_main = (RelativeLayout)findViewById(R.id.activity_main);

        btnSignup.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        //Inicjacja firebase auth
        auth = FirebaseAuth.getInstance();

        //Sprawdz aktualna sesje
        //if(auth.getCurrentUser() !=null){
        //    startActivity(new Intent(MainActivity.this,DashBoard.class));
        //}
    }

    public void onClick(View view){
        if(view.getId() == R.id.forgot_pass_button){
            startActivity(new Intent(MainActivity.this,ForgotPass.class));
            finish();
        }
        else if(view.getId() == R.id.register_btn){
            startActivity(new Intent(MainActivity.this,SignUp.class));
            finish();
        }
        else if(view.getId() == R.id.email_sign_in_button){
            if(input_email.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Błąd: Nie podałeś maila !",Toast.LENGTH_SHORT).show();
                return;
            }
            else if(input_pass.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Błąd: Nie podałeś hasła !",Toast.LENGTH_SHORT).show();
                return;
            }
            else
                loginUser(input_email.getText().toString(),input_pass.getText().toString());
        }
    }

    private void loginUser(final String email, final String password) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Witaj: "+email,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,DashBoard.class));
                            return;


                    }
                    else{
                            Toast.makeText(getApplicationContext(),"Błąd: Logowanie nieudane !" +task.getException(),Toast.LENGTH_SHORT).show();
                            return;
                    }
            }
        });
    }


}
