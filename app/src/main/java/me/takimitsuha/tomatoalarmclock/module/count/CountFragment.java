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
import java.util.Locale;

import me.takimitsuha.tomatoalarmclock.R;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count, container, false);
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

        mPieChart.setCenterText("0");
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

        setData(0, 100);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
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

        // add data
        setData2(7);

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

    protected String[] mParties = new String[]{
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    private void setData(int count, float range) {

        float mult = range;
        ArrayList<Integer> colors = new ArrayList<Integer>();
        if (0 == count) {
            count = 1;
            colors.add(Color.parseColor("#F2F2F2"));
        } else {
            //判断任务类型

        }

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5), mParties[i % mParties.length]));
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

        mPieChart.invalidate();
    }

    private void setData2(int count) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        Integer[] dates = DateUtil.getXData();
        for (int i = 0; i < count; i++) {
            yVals1.add(new Entry(dates[i], 0));
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
}
