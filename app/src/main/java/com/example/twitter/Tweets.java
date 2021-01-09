package com.example.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Tweets extends AppCompatActivity {
    ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);

            listView = findViewById(R.id.tweets_listView);
            arrayList = new ArrayList();
            arrayAdapter = new ArrayAdapter(Tweets.this, android.R.layout.simple_list_item_1,
                    arrayList);


//       listView.setOnItemLongClickListener(userView.this);
            try {
                ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Tweets");
                ParseUser parseUser = ParseUser.getCurrentUser();
                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            if (objects.size() > 0) {
                                for (ParseObject obj : objects) {
                                     for (Object user:parseUser.getList("following")){
                                         Log.i("tag",user.toString());
                                         Log.i("tag",obj.getString("username"));
                                         if(obj.getString("username") == user.toString()){
                                             arrayList.add(obj.getString("Tweet"));
                                         }else {
                                             continue;
                                         }
                                     }

                                }
                                listView.setAdapter(arrayAdapter);

                                listView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(Tweets.this, "No Users", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }

        }
}