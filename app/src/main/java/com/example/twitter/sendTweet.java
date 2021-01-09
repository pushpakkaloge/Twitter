package com.example.twitter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class sendTweet extends AppCompatActivity {
    EditText inputTweet;
    Button sendTweet;
    TextView viewAlltweets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);
        inputTweet = findViewById(R.id.tweetBox);
        sendTweet = findViewById(R.id.tweetSend);
        viewAlltweets = findViewById(R.id.viewAlltweets);

        viewAlltweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivityTweets();
            }
        });

        sendTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseObject parseObject =  new ParseObject("Tweets");
                parseObject.put("Tweet",inputTweet.getText().toString());
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(sendTweet.this, "Sent", Toast.LENGTH_SHORT).show();
                        }else{
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    void gotoActivityTweets(){
        Intent intent = new Intent(sendTweet.this,Tweets.class);
        startActivity(intent);
    }
}
