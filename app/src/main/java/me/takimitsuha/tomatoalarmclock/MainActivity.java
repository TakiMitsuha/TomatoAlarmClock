package me.takimitsuha.tomatoalarmclock;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import me.takimitsuha.tomatoalarmclock.library.tab.PagerBottomTabStrip;
import me.takimitsuha.tomatoalarmclock.module.Adapter;
import me.takimitsuha.tomatoalarmclock.module.count.CountFragment;
import me.takimitsuha.tomatoalarmclock.module.time.TimeFragment;
import me.takimitsuha.tomatoalarmclock.util.ToastUtil;

public class MainActivity extends FragmentActivity {

    private int[] iconResid = {R.mipmap.ic_launcher,
            R.mipmap.ic_launcher};

    private int[] iconResidClick = {R.mipmap.ic_launcher,
            R.mipmap.ic_launcher};

    private ViewPager mViewPager;
    private PagerBottomTabStrip mPagerBottomTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAdapter();

        mPagerBottomTabStrip = (PagerBottomTabStrip) findViewById(R.id.tab);

        mPagerBottomTabStrip.builder(mViewPager)
                .ColorMode()
                .TabIcon(iconResid)
                .TabClickIcon(iconResidClick)
                .TabClickIconColor(Color.parseColor("#3A5FCD"))
                .TabClickTextColor(Color.parseColor("#3A5FCD"))
                .TabPadding(5)
                .build();
    }

    private void initAdapter() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(new TimeFragment());
        list.add(new CountFragment());
        Adapter adapter = new Adapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(adapter);
    }

    private int count = 0;

    private long lastTime = System.currentTimeMillis();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ++count;
            long off = System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            if (count == 2 && off <= 2000) {
                finish();
            } else {
                count = 1;
                ToastUtil.showShort(getApplicationContext(), "再按一次退出程序");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
