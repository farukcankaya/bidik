package com.farukcankaya.bidik;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Faruk Cankaya on 7/17/16.
 */
public class ApplicationUtil {
    public static boolean isApplicationForeground(Context context) {
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager
                .getRunningTasks(Integer.MAX_VALUE);
        for (ActivityManager.RunningTaskInfo info : services) {
            if (info.topActivity.getPackageName().equalsIgnoreCase(
                    context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
