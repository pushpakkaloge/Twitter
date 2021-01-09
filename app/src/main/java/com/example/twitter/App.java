package com.example.twitter;

import android.app.Application;
import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("u4zEJ2moGkKoSaWSDzPGppPPYTadvMYZPLHYFkpU")
                // if defined
                .clientKey("fgtosAtBpisQSe5Kq4g2cXlte7v5Xe3QGDCtVdIX")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
