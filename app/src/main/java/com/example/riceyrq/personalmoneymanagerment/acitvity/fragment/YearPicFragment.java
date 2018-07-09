package com.example.riceyrq.personalmoneymanagerment.acitvity.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.riceyrq.personalmoneymanagerment.R;
import com.example.riceyrq.personalmoneymanagerment.dataBase.DBManager;
import com.example.riceyrq.personalmoneymanagerment.define.Message;
import com.example.riceyrq.personalmoneymanagerment.util.TimeUtil;
import com.example.riceyrq.personalmoneymanagerment.util.ToastUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointD;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class YearPicFragment extends ToolbarFragment {

    private Spinner spYear;
    private Button btnConfirm;
    private Button btnCancel;
    private LineChart lineChart;
    private TextView tvYearIn;
    private TextView tvYearOut;
    private DBManager dbManager;
    private ArrayAdapter<Integer> yearAdapter;
    private int year;
    private boolean inChange = false;

    public YearPicFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_year_pic, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.yearPic);
        spYear = view.findViewById(R.id.year_fraYear);
        btnConfirm = view.findViewById(R.id.btn_confirm_fraYear);
        btnCancel = view.findViewById(R.id.btn_cancel_fraYear);
        lineChart = view.findViewById(R.id.lchart_fraYear);
        tvYearIn = view.findViewById(R.id.tv_showYearIn_fraYear);
        tvYearOut = view.findViewById(R.id.tv_showYearOut_fraYear);
        dbManager = new DBManager(getActivity());
        initSpinner();
        initButton();
        try {
            refreshChart();
        } catch (ParseException e) {
            ToastUtil.showToast(getActivity(), "读取数据出错！");
        }
    }

    @TargetApi(26)
    private void refreshChart() throws ParseException {
        List<Entry> listIn = new ArrayList<>();
        List<Entry> listOut = new ArrayList<>();
        float in[] = new float[12];
        float out[] = new float[12];
        float inSum = 0;
        float outSum = 0;
        for (int i = 1; i <= 12; i++){
            List<Message> list = new ArrayList<>();
            list = dbManager.getMonthMessage(username, year, i);
            in[i - 1] = 0;
            out[i - 1] = 0;
            for (int j = 0; j < list.size(); j++){
                if (list.get(j).getInOrOut() == 0) {
                    in[i - 1] += list.get(j).getValue();
                } else if (list.get(j).getInOrOut() == 1){
                    out[i - 1] += list.get(j).getValue();
                }
            }
        }
        for (int i = 1; i <= 12; i++){
            listIn.add(new Entry(i, in[i - 1]));
            inSum += in[i - 1];
            listOut.add(new Entry(i, out[i - 1]));
            outSum += out[i - 1];
        }
        tvYearIn.setText(year + " 年度总收入：" + inSum);
        tvYearIn.setTextColor(getActivity().getColor(R.color.colorAccent));
        tvYearOut.setText(year + " 年度总支出：" + outSum);
        tvYearOut.setTextColor(getActivity().getColor(R.color.loveBlue));
        LineDataSet lineDataSetIn = new LineDataSet(listIn, "收入");
        lineDataSetIn.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSetIn.setCircleColor(getActivity().getColor(R.color.colorAccent));
        lineDataSetIn.setColor(getActivity().getColor(R.color.colorAccent));
        LineDataSet lineDataSetOut = new LineDataSet(listOut, "支出");
        lineDataSetOut.setCircleColor(getActivity().getColor(R.color.loveBlue));
        lineDataSetOut.setColor(getActivity().getColor(R.color.loveBlue));
        lineDataSetOut.setAxisDependency(YAxis.AxisDependency.LEFT);
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSetIn);
        dataSets.add(lineDataSetOut);
        final String[] months = new String[]{
                "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"
        };
        final LineData lineData = new LineData(dataSets);
        if (listIn.size() <= 0 && listOut.size() <= 0) {
            lineChart.setData(null);
        } else {
            lineChart.setData(lineData);
        }


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);  //最小轴步骤（间隔）为1
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int i = (int) value;
                return months[i - 1];
            }
        });
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);
        lineChart.getLegend().setEnabled(false);// 不显示图例
        lineChart.getDescription().setEnabled(true);// 显示描述
        lineChart.setScaleEnabled(false);   // 取消缩放
        lineChart.setNoDataText("暂无数据");// 没有数据的时候默认显示的文字
        lineChart.setNoDataTextColor(Color.GRAY);
        lineChart.setBorderColor(Color.BLUE);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        // 如果x轴label文字比较大，可以设置边距
        lineChart.setExtraRightOffset(25f);
        lineChart.setExtraBottomOffset(10f);
        lineChart.setExtraTopOffset(10f);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int month = (int) e.getX();
                float value = e.getY();
                MPPointD p = lineChart.getPixelForValues(month, value, YAxis.AxisDependency.LEFT);
                Log.e("lineChart", "" + month + "月" + value);


            }

            @Override
            public void onNothingSelected() {

            }
        });
        Description description = new Description();
        description.setText(year + "年度收支情况");
        description.setPosition(20, 20);
        description.setTextAlign(Paint.Align.LEFT);
        lineChart.setDescription(description);
        lineChart.invalidate();
    }

    private void initSpinner(){
        yearAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getYears());
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear.setAdapter(yearAdapter);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        spYear.setSelection(year - 2000);
        spYear.setEnabled(false);
    }

    private void initButton(){
        btnConfirm.setText("修改");
        btnCancel.setVisibility(View.INVISIBLE);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inChange){
                    int year1 = spYear.getSelectedItemPosition() + 2000;
                    year = year1;
                    btnConfirm.setText("修改");
                    btnCancel.setVisibility(View.INVISIBLE);
                    inChange = false;
                    spYear.setEnabled(false);
                    try {
                        refreshChart();
                    } catch (ParseException e) {
                        ToastUtil.showToast(getActivity(), "读取数据出错！");

                        return;
                    }
                } else {
                    btnConfirm.setText("确定");
                    btnCancel.setText("取消");
                    btnCancel.setVisibility(View.VISIBLE);
                    spYear.setEnabled(true);
                    inChange = true;
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spYear.setSelection(year - 2000);
                spYear.setEnabled(false);
                btnConfirm.setText("修改");
                btnCancel.setVisibility(View.INVISIBLE);
                inChange = false;
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dbManager.closeDB();
    }
}