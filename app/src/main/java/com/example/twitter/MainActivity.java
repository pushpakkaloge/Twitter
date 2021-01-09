package com.example.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private Button login;
    private Button signUp;
    private TextView get_data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        login = findViewById(R.id.button);
        signUp = findViewById(R.id.button2);
        get_data =findViewById(R.id.get_data);


//        if(ParseUser.getCurrentUser() != null){
//            ParseUser.getCurrentUser().logOut();
//
//        }
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpActivity();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("signing In.");
                progressDialog.show();
                ParseUser.logInInBackground(name.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null && user != null) {
                            //  Toast.makeText(MainActivity.this,"Login successfully",Toast.LENGTH_SHORT).show();
                            get_data.setText("");
                            progressDialog.dismiss();
                            openUserView();
                        } else {
                            // Toast.makeText(MainActivity.this,"failed to login",Toast.LENGTH_SHORT).show();

                            get_data.setText("Please check Username & Password");
                        }
                        progressDialog.dismiss();
                    }

                });

            }
        });



    }





    void openSignUpActivity(){
        Intent intent = new Intent(MainActivity.this,SignUp.class);
        startActivity(intent);
    }
    void openUserView(){
        Intent intent = new Intent(MainActivity.this,userView.class);
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