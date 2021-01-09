package com.example.twitter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class userView extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        listView = findViewById(R.id.listView);
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(userView.this, android.R.layout.simple_list_item_checked,
                arrayList);
        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(this);

//       listView.setOnItemLongClickListener(userView.this);
        try {
            ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
            parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            for (ParseUser obj : objects) {
                                arrayList.add(obj.getUsername());
                            }
                            listView.setAdapter(arrayAdapter);

                            for(String user:arrayList){
                                if(ParseUser.getCurrentUser().getList("following") != null){
                                    if(ParseUser.getCurrentUser().getList("following").contains(user)){
                                    listView.setItemChecked(arrayList.indexOf(user),true);
                            }
                            }
                            }

                            listView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(userView.this, "No Users", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

}

 


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.postItemImage){
            Toast.makeText(this,"Camera",Toast.LENGTH_SHORT).show();
//            if(Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(UserView.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},3000);
//            }else {
//                captureImage();
//            }
        }else if(item.getItemId()==R.id.postTweetImage){
                OpensendTweetActivity();
        }else if(item.getItemId()==R.id.LogOutuser){
            ParseUser.getCurrentUser().logOut();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CheckedTextView checkedTextView = (CheckedTextView) view;
        if (checkedTextView.isChecked()) {

            //  Toast.makeText(userView.this, ((CheckedTextView) view).getText()+"Checked", Toast.LENGTH_SHORT).show();
            ParseUser.getCurrentUser().add("following", ((CheckedTextView) view).getText());
        } else {
            //    Toast.makeText(userView.this, "Not Checked", Toast.LENGTH_SHORT).show();
            ParseUser.getCurrentUser().getList("following").remove(((CheckedTextView) view).getText());
            List followingList = ParseUser.getCurrentUser().getList("following");
            ParseUser.getCurrentUser().removeAll("following", arrayList);
            ParseUser.getCurrentUser().put("following", followingList);
        }
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(userView.this, "Followed Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(userView.this, "unfollowed successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
       void OpensendTweetActivity(){
            Intent intent = new Intent(userView.this,sendTweet.class);
            startActivity(intent);
        }

}
