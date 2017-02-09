package me.takimitsuha.tomatoalarmclock.module.time;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.takimitsuha.tomatoalarmclock.R;
import me.takimitsuha.tomatoalarmclock.common.Constants;
import me.takimitsuha.tomatoalarmclock.db.DBUtil;
import me.takimitsuha.tomatoalarmclock.entity.TomatoAlarmClock;
import me.takimitsuha.tomatoalarmclock.util.ToastUtil;
import me.takimitsuha.tomatoalarmclock.view.StateButton;

/**
 * Created by Taki on 2017/2/6.
 */
public class TimeFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout rlWork;
    private View viewBelowWork;
    private RelativeLayout rlStudy;
    private View viewBelowStudy;
    private RelativeLayout rlThink;
    private View viewBelowThink;
    private View view1;
    private View view2;
    private View view3;
    private RelativeLayout rlWrite;
    private View viewBelowWrite;
    private RelativeLayout rlSport;
    private View viewBelowSport;
    private RelativeLayout rlRead;
    private View viewBelowRead;
    private StateButton btnStart;
    private TextView tvWork;
    private TextView tvStudy;
    private TextView tvThink;
    private TextView tvWrite;
    private TextView tvSport;
    private TextView tvRead;

    private int index = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);


        rlWork = (RelativeLayout) view.findViewById(R.id.rl_work);
        viewBelowWork = view.findViewById(R.id.view_below_work);
        rlStudy = (RelativeLayout) view.findViewById(R.id.rl_study);
        viewBelowStudy = view.findViewById(R.id.view_below_study);
        rlThink = (RelativeLayout) view.findViewById(R.id.rl_think);
        viewBelowThink = view.findViewById(R.id.view_below_think);
        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);
        rlWrite = (RelativeLayout) view.findViewById(R.id.rl_write);
        viewBelowWrite = view.findViewById(R.id.view_below_write);
        rlSport = (RelativeLayout) view.findViewById(R.id.rl_sport);
        viewBelowSport = view.findViewById(R.id.view_below_sport);
        rlRead = (RelativeLayout) view.findViewById(R.id.rl_read);
        viewBelowRead = view.findViewById(R.id.view_below_read);
        btnStart = (StateButton) view.findViewById(R.id.btn_start);
        tvWork = (TextView) view.findViewById(R.id.tv_work);
        tvStudy = (TextView) view.findViewById(R.id.tv_study);
        tvThink = (TextView) view.findViewById(R.id.tv_think);
        tvWrite = (TextView) view.findViewById(R.id.tv_write);
        tvSport = (TextView) view.findViewById(R.id.tv_sport);
        tvRead = (TextView) view.findViewById(R.id.tv_read);
        rlWork.setOnClickListener(this);
        rlStudy.setOnClickListener(this);
        rlThink.setOnClickListener(this);
        rlWrite.setOnClickListener(this);
        rlSport.setOnClickListener(this);
        rlRead.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                if (index < 0) {
                    ToastUtil.showShort(getContext(), "请选择一个任务");
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(getActivity(), TimerActivity.class);
                intent.putExtra("index", index);
                startActivity(intent);
                // 进行数据保存操作
                TomatoAlarmClock tomato = new TomatoAlarmClock(null, System.currentTimeMillis(), null, null, 25, 0, System.currentTimeMillis(), index);
                long id = new DBUtil(getContext()).insert(tomato);
                SharedPreferences preferences = getContext().getSharedPreferences(
                        Constants.EXTRA_WEAC_SHARE, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong(Constants.CURRENT_TASK_ID, id);
                editor.apply();
                break;
            case R.id.rl_work:
                hideViews();
                viewBelowWork.setVisibility(View.VISIBLE);
                viewBelowWork.setBackgroundColor(Color.parseColor("#5DC1FF"));
                view1.setBackgroundColor(Color.parseColor("#5DC1FF"));
                tvWork.setTextColor(Color.parseColor("#333333"));
                index = 0;
                break;
            case R.id.rl_study:
                hideViews();
                viewBelowStudy.setVisibility(View.VISIBLE);
                viewBelowStudy.setBackgroundColor(Color.parseColor("#72D72A"));
                view2.setBackgroundColor(Color.parseColor("#72D72A"));
                tvStudy.setTextColor(Color.parseColor("#333333"));
                index = 1;
                break;
            case R.id.rl_think:
                hideViews();
                viewBelowThink.setVisibility(View.VISIBLE);
                viewBelowThink.setBackgroundColor(Color.parseColor("#FFA517"));
                view3.setBackgroundColor(Color.parseColor("#FFA517"));
                tvThink.setTextColor(Color.parseColor("#333333"));
                index = 2;
                break;
            case R.id.rl_write:
                hideViews();
                viewBelowWrite.setVisibility(View.VISIBLE);
                viewBelowWrite.setBackgroundColor(Color.parseColor("#16DBD8"));
                tvWrite.setTextColor(Color.parseColor("#333333"));
                index = 3;
                break;
            case R.id.rl_sport:
                hideViews();
                viewBelowSport.setVisibility(View.VISIBLE);
                viewBelowSport.setBackgroundColor(Color.parseColor("#968ED4"));
                tvSport.setTextColor(Color.parseColor("#333333"));
                index = 4;
                break;
            case R.id.rl_read:
                hideViews();
                viewBelowRead.setVisibility(View.VISIBLE);
                viewBelowRead.setBackgroundColor(Color.parseColor("#E35252"));
                tvRead.setTextColor(Color.parseColor("#333333"));
                index = 5;
                break;
            default:
                break;
        }
    }

    private void hideViews() {
        viewBelowWork.setVisibility(View.GONE);
        viewBelowStudy.setVisibility(View.GONE);
        viewBelowThink.setVisibility(View.GONE);
        viewBelowWrite.setVisibility(View.GONE);
        viewBelowSport.setVisibility(View.GONE);
        viewBelowRead.setVisibility(View.GONE);
        view1.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.darker_gray));
        view2.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.darker_gray));
        view3.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.darker_gray));
        tvWork.setTextColor(Color.parseColor("#999999"));
        tvStudy.setTextColor(Color.parseColor("#999999"));
        tvThink.setTextColor(Color.parseColor("#999999"));
        tvWrite.setTextColor(Color.parseColor("#999999"));
        tvSport.setTextColor(Color.parseColor("#999999"));
        tvRead.setTextColor(Color.parseColor("#999999"));
    }
}
