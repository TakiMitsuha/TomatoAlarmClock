package me.takimitsuha.tomatoalarmclock.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;

import me.takimitsuha.tomatoalarmclock.module.time.TimerOnTimeActivity;

public class TimerUtil {

    /**
     * 格式化时间
     *
     * @param hour   小时
     * @param minute 分钟
     * @return 格式化后的时间:[xx:xx]
     */
    public static String formatTime(int hour, int minute) {
        return addZero(hour) + ":" + addZero(minute);
    }

    /**
     * 时间补零
     *
     * @param time 需要补零的时间
     * @return 补零后的时间
     */
    public static String addZero(int time) {
        if (String.valueOf(time).length() == 1) {
            return "0" + time;
        }
        return String.valueOf(time);
    }

    /**
     * 开启倒计时
     *
     * @param context    context
     * @param timeRemain 剩余时间
     */
    public static void startAlarmTimer(Context context, long timeRemain) {
        Intent intent = new Intent(context, TimerOnTimeActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,
                1000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        long countdownTime = timeRemain + SystemClock.elapsedRealtime();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, countdownTime, pi);
        } else {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, countdownTime, pi);
        }
    }

}
