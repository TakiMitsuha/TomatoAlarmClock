package me.takimitsuha.tomatoalarmclock.module.time;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.Calendar;

import me.takimitsuha.tomatoalarmclock.R;
import me.takimitsuha.tomatoalarmclock.common.Constants;
import me.takimitsuha.tomatoalarmclock.library.timer.OttoAppConfig;
import me.takimitsuha.tomatoalarmclock.library.timer.TimeModel;
import me.takimitsuha.tomatoalarmclock.library.timer.TimerOnTimeEvent;
import me.takimitsuha.tomatoalarmclock.library.timer.TimerStartEvent;
import me.takimitsuha.tomatoalarmclock.library.timer.TomatoTimer;
import me.takimitsuha.tomatoalarmclock.service.CountDownService;
import me.takimitsuha.tomatoalarmclock.util.TimerUtil;

/**
 * Created by Taki on 2017/2/6.
 */
public class TimerActivity extends FragmentActivity implements View.OnClickListener, TomatoTimer.OnTimeChangeListener, TomatoTimer.OnMinChangListener, CountDownService.TimerUpdateListener {

    private TextView tvTitle;
    private TextView btnStart;
    private TextView btnStop;
    private ViewGroup llStart;

    private TomatoTimer mTimer;

    private boolean isPrepared;
    private boolean isBind;
    private int index = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        OttoAppConfig.getInstance().register(this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        assignViews();
    }

    private void assignViews() {
        llStart = (ViewGroup) findViewById(R.id.ll_start);
        btnStart = (TextView) findViewById(R.id.btn_start);
        btnStop = (TextView) findViewById(R.id.btn_stop);
        TextView resetBtn = (TextView) findViewById(R.id.btn_reset);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        resetBtn.setOnClickListener(this);

        mTimer = (TomatoTimer) findViewById(R.id.timer);
        mTimer.setOnTimeChangeListener(this);
        mTimer.setTimeChangListener(this);
        mTimer.setModel(TimeModel.Timer);
        setTimer();

        isPrepared = true;
    }

    private void setTimer() {
        SharedPreferences preferences = getSharedPreferences(
                Constants.EXTRA_WEAC_SHARE, Activity.MODE_PRIVATE);
        // 倒计时时间
        long countdown = preferences.getLong(Constants.COUNTDOWN_TIME, 0);
        if (countdown != 0) {
            // 剩余时间
            long remainTime;
            boolean isStop = preferences.getBoolean(Constants.IS_STOP, false);
            // 暂停状态
            if (isStop) {
                remainTime = countdown;
                setStart2Visible();
                mTimer.setIsStarted(true);
                // 正在计时状态
            } else {
                long now = SystemClock.elapsedRealtime();
                remainTime = countdown - now;
            }
            // 当剩余时间大于0
            if (remainTime > 0 && (remainTime / 1000 / 60) < 60) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(remainTime);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                mTimer.setStartTime(0, minute, second, isStop, false);
                setStratLlyt2Visible();
            } else {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong(Constants.COUNTDOWN_TIME, 0);
                editor.apply();
                initTimer();
            }
        } else {
            initTimer();
        }
    }

    /**
     * 初始化timer
     */
    private void initTimer() {
        mTimer.showAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                index = intent.getIntExtra("index", 0);
                if (0 == index) {
                    tvTitle.setText("工作");
                } else if (1 == index) {
                    tvTitle.setText("学习");
                } else if (2 == index) {
                    tvTitle.setText("思考");
                } else if (3 == index) {
                    tvTitle.setText("写作");
                } else if (4 == index) {
                    tvTitle.setText("运动");
                } else if (5 == index) {
                    tvTitle.setText("阅读");
                }
                processQuickTimer(0, 25, 0);
            }
        }, 500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startCountDown();
                setStopVisible();
                break;
            case R.id.btn_stop:
                stopAlarmClockTimer();
                mTimer.stop();
                stopCountDown();
                setStart2Visible();
                break;
            case R.id.btn_reset:
                stopAlarmClockTimer();
                mTimer.reset();
                stopCountDown();
                setStratLlytVisible();
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void OnTimerStart(TimerStartEvent event) {
        startCountDownService();
    }

    @Subscribe
    public void onTimerOnTime(TimerOnTimeEvent event) {
        if (mTimer != null) {
            mTimer.clearRemainTime();
            mTimer.setIsStarted(false);
            mTimer.saveRemainTime(0, false);
            setStratLlytVisible();
        }
    }

    @Override
    public void OnUpdateTime() {
        try {
            if (mTimer != null && !isFinishing()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTimer.updateDisplayTime();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopCountDown() {
        try {
            if (isBind) {
                unbindService(mConnection);
                isBind = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startCountDown() {
        if (mTimer.start()) {
            startCountDownService();
        }
    }

    private void startCountDownService() {
        if (!isBind) {
            Intent intent = new Intent(TimerActivity.this, CountDownService.class);
            isBind = bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CountDownService.TimerBinder binder = (CountDownService.TimerBinder) service;
            binder.setTimerUpdateListener(TimerActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private void processQuickTimer(int h, int m, int s) {
        mTimer.setStartTime(h, m, s, false, true);
        setStratLlyt2Visible();
        setStopVisible();
    }

    /**
     * 停止倒计时广播
     */
    private void stopAlarmClockTimer() {
        finish();
        overridePendingTransition(0, android.R.anim.fade_out);
        Intent intent = new Intent(getApplicationContext(), TimerOnTimeActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),
                1000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pi);
    }

    /**
     * 显示开始计时后的开始按钮
     */
    private void setStart2Visible() {
        btnStart.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.GONE);
    }

    /**
     * 显示开始计时后的暂停按钮
     */
    private void setStopVisible() {
        btnStart.setVisibility(View.GONE);
        btnStop.setVisibility(View.VISIBLE);
    }

    /**
     * 显示开始计时前的布局
     */
    private void setStratLlytVisible() {
        llStart.setVisibility(View.GONE);
    }

    /**
     * 显示开始计时后的布局
     */
    private void setStratLlyt2Visible() {
        llStart.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTimerStart(long timeRemain) {
        TimerUtil.startAlarmTimer(getApplicationContext(), timeRemain);
    }

    @Override
    public void onTimeStop(long timeStart, long timeRemain) {
        stopCountDown();
        setStratLlytVisible();
    }

    @Override
    public void onMinChange(int minute) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mTimer != null && mTimer.isStarted()) {
            mTimer.setReset(false);
            setTimer();
            mTimer.setShowAnimation(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopCountDown();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopCountDown();
        OttoAppConfig.getInstance().unregister(this);
    }
}
