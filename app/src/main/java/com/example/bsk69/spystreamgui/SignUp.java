package com.example.bsk69.spystreamgui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    Button btnSignup;
    EditText input_email,input_pass;
    RelativeLayout activity_sign_up;

    private FirebaseAuth auth;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignup = (Button)findViewById(R.id.registerBtn);
        input_email = (EditText)findViewById(R.id.forgot_email);
        input_pass = (EditText)findViewById(R.id.setpassword);
        activity_sign_up = (RelativeLayout)findViewById(R.id.activity_sign_up);

        btnSignup.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.registerBtn){
            if (input_email.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Błąd: Nie podałeś maila",Toast.LENGTH_SHORT).show();
                return;
            }
            else if (input_pass.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Błąd: Nie podałeś hasła",Toast.LENGTH_SHORT).show();
                return;
            }
            else
                signUpUser(input_email.getText().toString(),input_pass.getText().toString());
        }

    }

    private void signUpUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Błąd: "+ task.getException(),Toast.LENGTH_SHORT).show();
                    //snackbar=Snackbar.make(activity_sign_up,"Bład: " + task.getException(),Snackbar.LENGTH_SHORT);
                    //snackbar.show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Rejestracja udana ",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this,MainActivity.class);
                    startActivity(intent);
                    //snackbar=Snackbar.make(activity_sign_up,"Rejestracja udana: ",Snackbar.LENGTH_SHORT);
                    //snackbar.show();
                }
            }
        });

    }
}
