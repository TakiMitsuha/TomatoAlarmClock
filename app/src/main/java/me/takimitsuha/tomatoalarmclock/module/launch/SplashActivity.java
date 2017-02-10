package me.takimitsuha.tomatoalarmclock.module.launch;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.TextView;

import me.takimitsuha.tomatoalarmclock.MainActivity;
import me.takimitsuha.tomatoalarmclock.R;

/**
 * Created by Taki on 2017/2/7.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tvSlogan = (TextView) findViewById(R.id.tv_slogan);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tvSlogan,"Alpha",0f,1f).setDuration(1000);
        objectAnimator.start();
        objectAnimator.setStartDelay(300);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 2000);
    }
}
