package com.farukcankaya.bidik;

import android.app.Application;
import android.content.Intent;

/**
 * Created by Faruk Cankaya on 7/17/16.
 */
public class Melikus extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(getApplicationContext(), IdikService.class));
    }
}
