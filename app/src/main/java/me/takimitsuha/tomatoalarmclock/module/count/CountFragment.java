package me.takimitsuha.tomatoalarmclock.module.count;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.takimitsuha.tomatoalarmclock.R;
import me.takimitsuha.tomatoalarmclock.db.DBManager;
import me.takimitsuha.tomatoalarmclock.db.DBUtil;
import me.takimitsuha.tomatoalarmclock.entity.TomatoAlarmClock;
import me.takimitsuha.tomatoalarmclock.util.DateUtil;

/**
 * Created by Taki on 2017/2/6.
 */
public class CountFragment extends Fragment implements OnChartValueSelectedListener {

    private TextView tvTodayTotalTomato;
    private TextView tvTomatoCount;
    private TextView tvToday;
    private TextView tvTodayFinishTomato;
    private TextView tvAverageEveryday;
    private TextView tvAverageEveryWeek;
    private PieChart mPieChart;
    private LineChart mLineChart;

    private DBManager mDBManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count, container, false);

        mDBManager = DBManager.getInstance();
        mDBManager.init(getContext());

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        tvTodayTotalTomato = (TextView) view.findViewById(R.id.tv_today_total_tomato);
        tvTomatoCount = (TextView) view.findViewById(R.id.tv_tomato_count);
        tvToday = (TextView) view.findViewById(R.id.tv_today);
        tvTodayFinishTomato = (TextView) view.findViewById(R.id.tv_today_finish_tomato);
        tvAverageEveryday = (TextView) view.findViewById(R.id.tv_average_everyday);
        tvAverageEveryWeek = (TextView) view.findViewById(R.id.tv_average_every_week);

        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        tvToday.setText(df.format(new Date()));
        mPieChart = (PieChart) view.findViewById(R.id.piechart);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);

        mPieChart.setCenterTextSize(28);

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        // add a selection listener
        mPieChart.setOnChartValueSelectedListener(this);


        mPieChart.animateY(2400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);
        Legend l = mPieChart.getLegend();
        l.setEnabled(false);

        // entry label styling
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(0);


        mLineChart = (LineChart) view.findViewById(R.id.linechart);
        mLineChart.setOnChartValueSelectedListener(this);

        // no description text
        mLineChart.getDescription().setEnabled(false);

        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        mLineChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(false);
        mLineChart.setDrawGridBackground(true);
        mLineChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(true);

        mLineChart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l2 = mLineChart.getLegend();
        l2.setEnabled(false);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.parseColor("#666666"));
        xAxis.setDrawGridLines(false);
//        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#666666"));
        leftAxis.setAxisMaximum(60f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setEnabled(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void setPieChartData(List<TomatoAlarmClock> result) {
        Map<String, Integer> cacheMap = new HashMap<>();
        for (TomatoAlarmClock data : result) {
            if (cacheMap.isEmpty()) {
                cacheMap.put(data.getTaskType() + "", 1);
            } else {
                if (cacheMap.containsKey(data.getTaskType() + "")) {
                    Integer value = cacheMap.get(data.getTaskType() + "");
                    value++;
                    cacheMap.put(data.getTaskType() + "", value);
                } else {
                    cacheMap.put(data.getTaskType() + "", 1);
                }
            }
        }
        ArrayList<Integer> colors = new ArrayList<Integer>();
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        if (0 == result.size()) {
            colors.add(Color.parseColor("#F2F2F2"));
            entries.add(new PieEntry((float) ((Math.random() * 100) + 100 / 5), "0"));
        } else {
            //判断任务类型
            //工作 #5DC1FF 学习 #72D72A 思考 #FFA517 写作 #16DBD8 运动 #968ED4 阅读 #E35252
            for (String dataKey : cacheMap.keySet()) {
                switch (dataKey) {
                    case "0":
                        colors.add(Color.parseColor("#5DC1FF"));
                        break;
                    case "1":
                        colors.add(Color.parseColor("#72D72A"));
                        break;
                    case "2":
                        colors.add(Color.parseColor("#FFA517"));
                        break;
                    case "3":
                        colors.add(Color.parseColor("#16DBD8"));
                        break;
                    case "4":
                        colors.add(Color.parseColor("#968ED4"));
                        break;
                    case "5":
                        colors.add(Color.parseColor("#E35252"));
                        break;

                    default:
                        break;
                }
            }
            for (Map.Entry<String, Integer> entry : cacheMap.entrySet()) {
                entries.add(new PieEntry((float) (entry.getValue()), entry.getKey()));
            }
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(colors);
        dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(0);//去掉百分号
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);

        // undo all highlights
        mPieChart.highlightValues(null);
        mPieChart.setCenterText(result.size() + "");

        mPieChart.invalidate();
    }

    private void setLineChartData(List<Integer> result) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        Integer[] dates = DateUtil.getXData();
        for (int i = 0; i < 7; i++) {
            yVals1.add(new Entry(dates[i], result.get(i)));
        }

        LineDataSet set;

        if (mLineChart.getData() != null &&
                mLineChart.getData().getDataSetCount() > 0) {
            set = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
            set.setValues(yVals1);
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set = new LineDataSet(yVals1, "");

            set.setAxisDependency(YAxis.AxisDependency.LEFT);
            set.setColor(Color.BLUE);
//            set.setCircleColor(Color.WHITE);
            set.setLineWidth(2f);
            set.setCircleRadius(3f);
            set.setFillAlpha(65);
            set.setFillColor(ColorTemplate.getHoloBlue());
            set.setHighLightColor(Color.rgb(244, 117, 117));
            set.setDrawCircleHole(false);
            //set.setFillFormatter(new MyFillFormatter(0f));
            //set.setDrawHorizontalHighlightIndicator(false);
//            set.setVisible(false);
            set.setCircleColor(Color.parseColor("#5DC1FF"));

            // create a data object with the datasets
            LineData data = new LineData(set);
//            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(0);

            // set data
            mLineChart.setData(data);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED", "Value: " + e.getY() + ", index: " + h.getX() + ", DataSet index: " + h.getDataSetIndex());
        mLineChart.centerViewToAnimated(e.getX(), e.getY(), mLineChart.getData().getDataSetByIndex(h.getDataSetIndex()).getAxisDependency(), 500);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    private void initData() {
        getTodayTomato();
        getAllTomatoes();
        getTodayFinishTomato();
    }

    private void getTodayTomato() {
        List<TomatoAlarmClock> list = mDBManager.getDaoSession().queryRaw(TomatoAlarmClock.class, "WHERE create_time >= ? AND create_time <= ?", new String[]{DateUtil.getStartTime() + "", DateUtil.getEndTime() + ""});
        tvTodayTotalTomato.setText(list == null ? "0" : list.size() + "");
        setDataChart(list);
    }

    private void getAllTomatoes() {
        List<TomatoAlarmClock> list = new DBUtil(getContext()).loadAll();
        tvTomatoCount.setText(list == null ? "0" : list.size() + "");
    }

    private void getTodayFinishTomato() {
        List<TomatoAlarmClock> list = mDBManager.getDaoSession().queryRaw(TomatoAlarmClock.class, "WHERE create_time >= ? AND create_time <= ? AND finish = ?", new String[]{DateUtil.getStartTime() + "", DateUtil.getEndTime() + "", "1"});
        tvTodayFinishTomato.setText(list == null ? "0个番茄" : list.size() + "个番茄");

    }

    private void setDataChart(List<TomatoAlarmClock> list) {
        // add data
        setPieChartData(list);
        //计算出最近7天，包括今天的番茄
        //7天总共番茄数
        List<TomatoAlarmClock> list2 = mDBManager.getDaoSession().queryRaw(TomatoAlarmClock.class, "WHERE create_time >= ? AND create_time <= ? AND finish = ?", new String[]{DateUtil.getStartTimeByParam(6) + "", DateUtil.getEndTime() + "", "1"});
        tvAverageEveryWeek.setText(list2 == null ? "0个番茄" : list2.size() + "个番茄");
        //平均每天番茄数
        if (0 == (list2 == null ? 0 : list2.size())) {
            tvAverageEveryday.setText("0个番茄");
        } else {
            String everyday = new Formatter().format("%.2f", (float) (list2 == null ? 0 : (list2.size() / 7))).toString();
            tvAverageEveryday.setText(everyday + "个番茄");
        }
        //算出最近7天每天的番茄数
        List<TomatoAlarmClock> listOne = mDBManager.getDaoSession().queryRaw(TomatoAlarmClock.class, "WHERE create_time >= ? AND create_time <= ? AND finish = ?", new String[]{DateUtil.getStartTimeByParam(6) + "", DateUtil.getEndTimeByParam(6) + "", "1"});
        List<TomatoAlarmClock> listTwo = mDBManager.getDaoSession().queryRaw(TomatoAlarmClock.class, "WHERE create_time >= ? AND create_time <= ? AND finish = ?", new String[]{DateUtil.getStartTimeByParam(5) + "", DateUtil.getEndTimeByParam(5) + "", "1"});
        List<TomatoAlarmClock> listThree = mDBManager.getDaoSession().queryRaw(TomatoAlarmClock.class, "WHERE create_time >= ? AND create_time <= ? AND finish = ?", new String[]{DateUtil.getStartTimeByParam(4) + "", DateUtil.getEndTimeByParam(4) + "", "1"});
        List<TomatoAlarmClock> listFour = mDBManager.getDaoSession().queryRaw(TomatoAlarmClock.class, "WHERE create_time >= ? AND create_time <= ? AND finish = ?", new String[]{DateUtil.getStartTimeByParam(3) + "", DateUtil.getEndTimeByParam(3) + "", "1"});
        List<TomatoAlarmClock> listFive = mDBManager.getDaoSession().queryRaw(TomatoAlarmClock.class, "WHERE create_time >= ? AND create_time <= ? AND finish = ?", new String[]{DateUtil.getStartTimeByParam(2) + "", DateUtil.getEndTimeByParam(2) + "", "1"});
        List<TomatoAlarmClock> listSix = mDBManager.getDaoSession().queryRaw(TomatoAlarmClock.class, "WHERE create_time >= ? AND create_time <= ? AND finish = ?", new String[]{DateUtil.getStartTimeByParam(1) + "", DateUtil.getEndTimeByParam(1) + "", "1"});
        List<TomatoAlarmClock> listSeven = mDBManager.getDaoSession().queryRaw(TomatoAlarmClock.class, "WHERE create_time >= ? AND create_time <= ? AND finish = ?", new String[]{DateUtil.getStartTime() + "", DateUtil.getEndTime() + "", "1"});
        List<Integer> result = new ArrayList<>();
        result.add(listOne == null ? 0 : listOne.size());
        result.add(listTwo == null ? 0 : listTwo.size());
        result.add(listThree == null ? 0 : listThree.size());
        result.add(listFour == null ? 0 : listFour.size());
        result.add(listFive == null ? 0 : listFive.size());
        result.add(listSix == null ? 0 : listSix.size());
        result.add(listSeven == null ? 0 : listSeven.size());
        setLineChartData(result);
    }
}
