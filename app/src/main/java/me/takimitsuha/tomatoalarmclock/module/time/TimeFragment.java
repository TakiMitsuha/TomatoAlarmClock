package me.takimitsuha.tomatoalarmclock.module.time;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import me.takimitsuha.tomatoalarmclock.R;
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

    private int index = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("番茄闹钟");
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
                break;
            case R.id.rl_work:
                hideViews();
                viewBelowWork.setVisibility(View.VISIBLE);
                index = 0;
                break;
            case R.id.rl_study:
                hideViews();
                viewBelowStudy.setVisibility(View.VISIBLE);
                index = 1;
                break;
            case R.id.rl_think:
                hideViews();
                viewBelowThink.setVisibility(View.VISIBLE);
                index = 2;
                break;
            case R.id.rl_write:
                hideViews();
                viewBelowWrite.setVisibility(View.VISIBLE);
                index = 3;
                break;
            case R.id.rl_sport:
                hideViews();
                viewBelowSport.setVisibility(View.VISIBLE);
                index = 4;
                break;
            case R.id.rl_read:
                hideViews();
                viewBelowRead.setVisibility(View.VISIBLE);
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
    }
}
