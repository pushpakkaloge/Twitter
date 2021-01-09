package com.example.twitter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class SignUp extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private EditText passwd;
    private Button btn;
    private TextView warning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.user_Name);
        email = findViewById(R.id.e_mail);
        passwd = findViewById(R.id.pwd);
        btn = findViewById(R.id.doneBtn);
        warning=findViewById(R.id.warning);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ParseUser parseUser = new ParseUser();
                parseUser.setUsername(username.getText().toString());
                parseUser.setPassword(passwd.getText().toString());
                parseUser.setEmail(email.getText().toString());

                ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("signing In.");
                progressDialog.show();

                parseUser.signUpInBackground(e -> {
                    if(e == null){
                        Toast.makeText(SignUp.this,"SignUp successful",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        openMainActivity();
                    }else {
                        Toast.makeText(SignUp.this,"Failed to signup",Toast.LENGTH_SHORT).show();
                        warning.setText("Please change username and Try Again!");
                    }
                    progressDialog.dismiss();
                });

            }
        });


    }
    void openMainActivity(){
        Intent intent = new Intent(SignUp.this,MainActivity.class);
        startActivity(intent);
    }

    public void rootLayoutTapped(View view){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
