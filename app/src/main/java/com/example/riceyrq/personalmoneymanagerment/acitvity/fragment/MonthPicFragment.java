package com.example.riceyrq.personalmoneymanagerment.acitvity.fragment;


import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.riceyrq.personalmoneymanagerment.R;
import com.example.riceyrq.personalmoneymanagerment.dataBase.DBManager;
import com.example.riceyrq.personalmoneymanagerment.define.Data;
import com.example.riceyrq.personalmoneymanagerment.define.Message;
import com.example.riceyrq.personalmoneymanagerment.util.TimeUtil;
import com.example.riceyrq.personalmoneymanagerment.util.ToastUtil;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.mikepenz.materialize.color.Material;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthPicFragment extends ToolbarFragment {

    private Spinner spYear;
    private Spinner spMonth;
    private Button btnConfirm;
    private Button btnCancel;
    private PieChart pieInChart;
    private PieChart pieOutChart;
    private TextView tvInAll;
    private TextView tvOutAll;
    private TextView tvIn1;
    private TextView tvIn2;
    private TextView tvIn3;
    private TextView tvIn4;
    private TextView tvOut1;
    private TextView tvOut2;
    private TextView tvOut3;
    private TextView tvOut4;
    private RadioButton rbIn;
    private RadioButton rbOut;
    private LinearLayout llIns;
    private LinearLayout llOuts;
    private DBManager dbManager;
    private int year;
    private int month;
    private boolean inChange = false;
    private ArrayAdapter<Integer> yearAdapter;
    private ArrayAdapter<Integer> monthAdapter;


    public MonthPicFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_month_pic, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.monthPic);
        spYear = view.findViewById(R.id.year_fraMonth);
        spMonth = view.findViewById(R.id.month_fraMonth);
        btnConfirm = view.findViewById(R.id.btn_confirm_fraMonth);
        btnCancel = view.findViewById(R.id.btn_cancel_fraMonth);
        tvInAll = view.findViewById(R.id.tv_showMonthIn_fraMonth);
        tvOutAll = view.findViewById(R.id.tv_showMonthOut_fraMonth);
        tvIn1 = view.findViewById(R.id.tv_showMonthIn1_fraMonth);
        tvIn2 = view.findViewById(R.id.tv_showMonthIn2_fraMonth);
        tvIn3 = view.findViewById(R.id.tv_showMonthIn3_fraMonth);
        tvIn4 = view.findViewById(R.id.tv_showMonthIn4_fraMonth);
        tvOut1 = view.findViewById(R.id.tv_showMonthOut1_fraMonth);
        tvOut2 = view.findViewById(R.id.tv_showMonthOut2_fraMonth);
        tvOut3 = view.findViewById(R.id.tv_showMonthOut3_fraMonth);
        tvOut4 = view.findViewById(R.id.tv_showMonthOut4_fraMonth);
        pieInChart = view.findViewById(R.id.piechart_in_fraMonth);
        pieOutChart = view.findViewById(R.id.piechart_out_fraMonth);
        rbIn = view.findViewById(R.id.rb_in_fraMonth);
        rbOut = view.findViewById(R.id.rb_out_fraMonth);
        llIns = view.findViewById(R.id.ll_ins_fraMonth);
        llOuts = view.findViewById(R.id.ll_outs_fraMonth);
        dbManager = new DBManager(getActivity());
        initSpinner();
        initButton();
        try {
            refreshChart();
        } catch (ParseException e) {
            ToastUtil.showToast(getActivity(), "读取数据出错！");
        }
        showIns();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void refreshChart() throws ParseException {
        List<Message> list = dbManager.getMonthMessage(username, year, month);
        float ins[] = new float[4];
        float outs[] = new float[4];
        float inAll = 0;
        float outAll = 0;
        TextView[] textViewsIn = new TextView[]{tvIn1, tvIn2, tvIn3, tvIn4};
        TextView[] textViewsOut = new TextView[]{tvOut1, tvOut2, tvOut3, tvOut4};
        for (int i = 0; i < 4; i++){
            ins[i] = 0;
            outs[i] = 0;
        }
        for (int i = 0; i < list.size(); i++){
            Message message = list.get(i);
            if (message.getInOrOut() == 0) {
                inAll += message.getValue();
                for (int j = 1; j < Data.ins.length; j++){
                    if (Data.ins[j].equals(message.getKind())){
                        ins[j - 1] += message.getValue();
                    }
                }
            } else if (message.getInOrOut() == 1) {
                outAll += message.getValue();
                for (int j = 1; j < Data.outs.length; j++){
                    if (Data.outs[j].equals(message.getKind())){
                        outs[j - 1] += message.getValue();
                    }
                }
            }
        }
        List<PieEntry> entriesIn = new ArrayList<>();
        List<PieEntry> entriesOut = new ArrayList<>();
        int[] colors = new int[]{Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};
        List<Integer> colorsIn = new ArrayList<>();
        List<Integer> colorsOut = new ArrayList<>();
        for (int i = 0; i < ins.length; i++){
            if (ins[i] > 0) {
                entriesIn.add(new PieEntry(ins[i], Data.ins[i + 1]));
                colorsIn.add(colors[i]);
            }
            if (outs[i] > 0) {
                entriesOut.add(new PieEntry(outs[i], Data.outs[i + 1]));
                colorsOut.add(colors[i]);
            }
        }
        PieDataSet setIn = new PieDataSet(entriesIn, "");
        PieDataSet setOut = new PieDataSet(entriesOut, "");
        setIn.setSliceSpace(1f);           //设置饼状Item之间的间隙
        setIn.setSelectionShift(10f);      //设置饼状Item被选中时变化的距离
        setIn.setColors(colorsIn);
        setOut.setSliceSpace(1f);           //设置饼状Item之间的间隙
        setOut.setSelectionShift(10f);      //设置饼状Item被选中时变化的距离
        setOut.setColors(colorsOut);
        setIn.setValueLinePart1OffsetPercentage(85f);//数据连接线距图形片内部边界的距离，为百分数
        setIn.setValueLinePart1Length(0.4f);
        setIn.setValueLinePart2Length(0.8f);
        setIn.setValueLineColor(Color.BLACK);//设置连接线的颜色
        setIn.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        setOut.setValueLinePart1OffsetPercentage(85f);//数据连接线距图形片内部边界的距离，为百分数
        setOut.setValueLinePart1Length(0.4f);
        setOut.setValueLinePart2Length(0.8f);
        setOut.setValueLineColor(Color.BLACK);//设置连接线的颜色
        setOut.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData dataIn = new PieData(setIn);
        PieData dataOut = new PieData(setOut);
        dataIn.setValueTextSize(11f);
        dataOut.setValueTextSize(11f);
        if (entriesIn.size() > 0) {
            pieInChart.setData(dataIn);
        } else {
            pieInChart.setData(null);
        }
        if (entriesOut.size() > 0) {
            pieOutChart.setData(dataOut);
        } else {
            pieOutChart.setData(null);
        }
        Description descriptionIn = new Description();
        descriptionIn.setText(year + "年" + month + "月收入情况");
        descriptionIn.setPosition(20, 20);
        descriptionIn.setTextAlign(Paint.Align.LEFT);
        pieInChart.setDescription(descriptionIn);
        Description descriptionOut = new Description();
        descriptionOut.setText(year + "年" + month + "月支出情况");
        descriptionOut.setPosition(20, 20);
        descriptionOut.setTextAlign(Paint.Align.LEFT);
        pieOutChart.setDescription(descriptionOut);
        pieInChart.setNoDataText("暂无数据");// 没有数据的时候默认显示的文字
        pieInChart.setNoDataTextColor(Color.GRAY);
        pieOutChart.setNoDataText("暂无数据");// 没有数据的时候默认显示的文字
        pieOutChart.setNoDataTextColor(Color.GRAY);
        //pieInChart.setDrawEntryLabels(true);              //设置pieChart是否只显示饼图上百分比不显示文字（true：下面属性才有效果）
        pieInChart.setEntryLabelColor(Color.WHITE);       //设置pieChart图表文本字体颜色
        pieInChart.setEntryLabelTextSize(15f);
        pieOutChart.setEntryLabelColor(Color.WHITE);       //设置pieChart图表文本字体颜色
        pieOutChart.setEntryLabelTextSize(15f);
        pieInChart.invalidate();
        pieOutChart.invalidate();
        tvInAll.setText(year + " 年" + month + "月总收入：" + inAll);
        tvInAll.setTextColor(getActivity().getColor(R.color.colorAccent));
        tvOutAll.setText(year + " 年" + month + "月度总支出：" + outAll);
        tvOutAll.setTextColor(getActivity().getColor(R.color.loveBlue));
        for (int i = 0; i < textViewsIn.length; i++){
            textViewsIn[i].setText(year + " 年" + month + "月" + Data.ins[i + 1] + "收入：" + ins[i]);
            textViewsIn[i].setTextColor(getActivity().getColor(R.color.colorAccent));
            textViewsOut[i].setText(year + " 年" + month + "月" + Data.outs[i + 1] + "支出：" + outs[i]);
            textViewsOut[i].setTextColor(getActivity().getColor(R.color.loveBlue));
        }
    }

    private void initButton(){
        btnConfirm.setText("修改");
        btnCancel.setVisibility(View.INVISIBLE);
        rbIn.setChecked(true);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inChange) {
                    closeSpinner();
                    year = spYear.getSelectedItemPosition() + 2000;
                    month = spMonth.getSelectedItemPosition() + 1;
                    btnConfirm.setText("修改");
                    btnCancel.setVisibility(View.INVISIBLE);
                    inChange = false;
                    closeSpinner();
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
                    openSpinner();
                    inChange = true;
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spYear.setSelection(year - 2000);
                spMonth.setSelection(month - 1);
                closeSpinner();
                btnConfirm.setText("修改");
                btnCancel.setVisibility(View.INVISIBLE);
                inChange = false;
            }
        });
        rbIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbIn.setChecked(true);
                showIns();
            }
        });
        rbOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbOut.setChecked(true);
                showOuts();
            }
        });
    }

    private void showOuts(){
        pieOutChart.setVisibility(View.VISIBLE);
        pieInChart.setVisibility(View.GONE);
        tvOutAll.setVisibility(View.VISIBLE);
        tvInAll.setVisibility(View.GONE);
        llOuts.setVisibility(View.VISIBLE);
        llIns.setVisibility(View.GONE);
    }

    private void showIns(){
        pieInChart.setVisibility(View.VISIBLE);
        pieOutChart.setVisibility(View.GONE);
        tvInAll.setVisibility(View.VISIBLE);
        tvOutAll.setVisibility(View.GONE);
        llIns.setVisibility(View.VISIBLE);
        llOuts.setVisibility(View.GONE);
    }

    private void initSpinner(){
        yearAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getYears());
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear.setAdapter(yearAdapter);
        monthAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getMonths());
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonth.setAdapter(monthAdapter);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        spYear.setSelection(year - 2000);
        spMonth.setSelection(month - 1);
        closeSpinner();
    }

    private void closeSpinner(){
        spMonth.setEnabled(false);
        spYear.setEnabled(false);
    }

    private void openSpinner(){
        spMonth.setEnabled(true);
        spYear.setEnabled(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dbManager.closeDB();
    }
}