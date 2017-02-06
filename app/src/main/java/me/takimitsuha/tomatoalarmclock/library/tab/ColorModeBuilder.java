package me.takimitsuha.tomatoalarmclock.library.tab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.LinearLayout.LayoutParams;

import java.util.List;

public class ColorModeBuilder implements IBuilderColor {

    private PagerBottomTabStrip mPagerBottomTabStrip;

    private List<Tabstrip> mTabstrips;

    private int mCount;

    private Context mContext;

    public ColorModeBuilder(PagerBottomTabStrip pagerBottomTabStrip, List<Tabstrip> tabstrips, Context context) {

        mPagerBottomTabStrip = pagerBottomTabStrip;
        mCount = tabstrips.size();
        mTabstrips = tabstrips;
        mContext = context;

    }

    @Override
    public void build() {

        for (int i = 0; i < mCount; i++) {
            Tabstrip tabstrip = mTabstrips.get(i);
            tabstrip.configure(mContext, tabstrip.MODE_COLOR);
            mPagerBottomTabStrip.addView(tabstrip);
            LayoutParams lp = (LayoutParams) tabstrip.getLayoutParams();
            lp.width = 0;
            lp.weight = 1;
            lp.height = LayoutParams.MATCH_PARENT;
            tabstrip.setLayoutParams(lp);
        }
        mTabstrips.get(0).setBitmapAlpha(1.0f);
    }

    @Override
    public IBuilderColor TabTextSize(int textsize) {
        for (int i = 0; i < mCount; i++) {
            mTabstrips.get(i).setTabTextSize(Utils.dp2px(mContext, textsize));
        }
        return this;
    }

    @Override
    public IBuilderColor TabPadding(int padding) {

        for (int i = 0; i < mCount; i++) {
            mTabstrips.get(i).setTabPadding((int) Utils.dp2px(mContext, padding));
        }
        return this;
    }

    @Override
    public IBuilderColor TabTextColor(int textcolor) {
        for (int i = 0; i < mCount; i++) {
            mTabstrips.get(i).setTabTextColor(textcolor);
        }
        return this;
    }

    @Override
    public IBuilderColor TabClickTextColor(int clicktextcolor) {
        for (int i = 0; i < mCount; i++) {
            mTabstrips.get(i).setTabClickTextColor(clicktextcolor);
        }
        return this;
    }

    @Override
    public IBuilderColor TabIconColor(int iconcolor) {
        for (int i = 0; i < mCount; i++) {
            mTabstrips.get(i).setTabIconColor(iconcolor);
        }
        return this;
    }

    @Override
    public IBuilderColor TabClickIconColor(int clickiconcolor) {
        for (int i = 0; i < mCount; i++) {
            mTabstrips.get(i).setTabClickIconColor(clickiconcolor);
        }
        return this;
    }

    @Override
    public IBuilderColor TabMessageBackgroundColor(int backgroundcolor) {
        for (int i = 0; i < mCount; i++) {
            mTabstrips.get(i).setTabMessageBackgroundColor(backgroundcolor);
        }
        return this;
    }

    @Override
    public IBuilderColor TabMessageTextColor(int textcolor) {
        for (int i = 0; i < mCount; i++) {
            mTabstrips.get(i).setTabMessageTextColor(textcolor);
        }
        return this;
    }

    @Override
    public IBuilderColor TabIcon(int[] resids) {
        for (int i = 0; i < resids.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resids[i]);
            mTabstrips.get(i).setTabIcon(bitmap);
        }
        return this;
    }

    @Override
    public IBuilderColor TabClickIcon(int[] resids) {
        for (int i = 0; i < resids.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resids[i]);
            mTabstrips.get(i).setTabClickIcon(bitmap);
        }
        return this;
    }

    @Override
    public IBuilderColor TabBackground(int resid) {
        for (int i = 0; i < mCount; i++) {
            mTabstrips.get(i).setTabBackground(resid);
        }
        return this;
    }

}
