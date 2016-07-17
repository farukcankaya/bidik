package com.farukcankaya.bidik;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.farukcankaya.bidik.event.ActivityStartedEvent;
import com.farukcankaya.bidik.event.ActivityStoppedEvent;
import com.farukcankaya.bidik.event.ScreenshotSavedEvent;
import com.farukcankaya.bidik.event.TakeScreenshotEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Faruk Cankaya on 7/17/16.
 */
public class IdikService extends Service {

    private WindowManager windowManager;
    private ImageView chatHead;
    private boolean isActivityRunning = true;

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);

        Log.i("ChatHeadService", "ChatHeadService is started.");

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);
        chatHead.setImageResource(R.mipmap.ic_launcher);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                110,
                110,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.RIGHT;
        params.x = 20;

        windowManager.addView(chatHead, params);

        chatHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EventBus.getDefault().hasSubscriberForEvent(TakeScreenshotEvent.class)) {
                    EventBus.getDefault().post(new TakeScreenshotEvent());
                } else {
                    startActivity(MainActivity.newIntent(getApplicationContext(), true));
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(ScreenshotSavedEvent event) {
        Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
        Log.d("EventBidik", "ScreenshotSavedEvent is gotten");
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onActivityStartedEvent(ActivityStartedEvent event) {
        Log.d("EventBidik", "ActivityStartedEvent is gotten");
        isActivityRunning = true;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onActivityStoppedEvent(ActivityStoppedEvent event) {
        Log.d("EventBidik", "ActivityStoppedEvent is gotten");
        isActivityRunning = false;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        if (chatHead != null) windowManager.removeView(chatHead);
    }
}