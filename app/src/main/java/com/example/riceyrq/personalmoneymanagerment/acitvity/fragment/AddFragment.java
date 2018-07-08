package com.example.riceyrq.personalmoneymanagerment.acitvity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.riceyrq.personalmoneymanagerment.R;
import com.example.riceyrq.personalmoneymanagerment.acitvity.Main;
import com.example.riceyrq.personalmoneymanagerment.dataBase.DBManager;
import com.example.riceyrq.personalmoneymanagerment.define.Data;
import com.example.riceyrq.personalmoneymanagerment.define.Message;
import com.example.riceyrq.personalmoneymanagerment.util.TimeUtil;
import com.example.riceyrq.personalmoneymanagerment.util.ToastUtil;

import java.text.ParseException;
import java.util.Calendar;

public class AddFragment extends ToolbarFragment {
    private  RadioButton rbIn;
    private RadioButton rbOut;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private EditText edValue;
    private ImageView ivValueCancel;
    private EditText edOther;
    private ImageView ivOtherCancel;
    private Spinner spYear;
    private Spinner spMonth;
    private Spinner spDay;
    private Spinner spHour;
    private Spinner spMin;
    private Button btnAdd;
    private DBManager dbManager;
    private ArrayAdapter<Integer> yearAdapter;
    private ArrayAdapter<Integer> monthAdapter;
    private ArrayAdapter<Integer> dayAdapter;
    private ArrayAdapter<Integer> hourAdapter;
    private ArrayAdapter<Integer> minAdapter;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;

    public AddFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.add);
        rbIn = view.findViewById(R.id.rb_in_fraAdd);
        rbOut = view.findViewById(R.id.rb_out_fragAdd);
        rb1 = view.findViewById(R.id.rb1_fraAdd);
        rb2 = view.findViewById(R.id.rb2_fraAdd);
        rb3 = view.findViewById(R.id.rb3_fraAdd);
        rb4 = view.findViewById(R.id.rb4_fraAdd);
        edValue = view.findViewById(R.id.et_value_fraAdd);
        ivValueCancel = view.findViewById(R.id.iv_valueClear_fraAdd);
        edOther = view.findViewById(R.id.et_other_fraAdd);
        ivOtherCancel = view.findViewById(R.id.iv_otherClear_fraAdd);
        spYear = view.findViewById(R.id.year_fraAdd);
        spMonth = view.findViewById(R.id.month_fraAdd);
        spDay = view.findViewById(R.id.day_fraAdd);
        spHour = view.findViewById(R.id.hour_fraAdd);
        spMin = view.findViewById(R.id.minute_fraAdd);
        btnAdd = view.findViewById(R.id.btn_add_fraAdd);
        dbManager = new DBManager(getActivity());
        initButton();
        initSpinner();
        initEditText();
    }

    private void initButton(){
        final RadioButton[] radioButtons = new RadioButton[]{rb1, rb2, rb3, rb4};
        rbIn.setChecked(true);
        rb1.setChecked(true);
        ivValueCancel.setVisibility(View.INVISIBLE);
        ivOtherCancel.setVisibility(View.INVISIBLE);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edValue.getText().length() == 0) {
                    ToastUtil.showToast(getActivity(), "请输入金额！");
                    return;
                } else {
                    Message message = new Message();
                    message.setUserName(username);
                    message.setValue(Float.valueOf(edValue.getText().toString()));
                    if (edOther.getText().length() == 0) {
                        message.setOther("无");
                    } else {
                        message.setOther(edOther.getText().toString());
                    }
                    if (rbIn.isChecked()){
                        message.setInOrOut(0);
                        for (int i = 0; i < radioButtons.length; i++){
                            if (radioButtons[i].isChecked()){
                                message.setKind(Data.ins[i + 1]);
                            }
                        }
                    } else if (rbOut.isChecked()) {
                        message.setInOrOut(1);
                        for (int i = 0; i < radioButtons.length; i++){
                            if (radioButtons[i].isChecked()){
                                message.setKind(Data.outs[i + 1]);
                            }
                        }
                    }
                    year = spYear.getSelectedItemPosition() + 2000;
                    month = spMonth.getSelectedItemPosition() + 1;
                    day = spDay.getSelectedItemPosition() + 1;
                    hour = spHour.getSelectedItemPosition();
                    min = spMin.getSelectedItemPosition();
                    long mill = 0;
                    try {
                        mill = TimeUtil.getDayMill(year, month, day, hour, min);
                    } catch (ParseException e) {
                        ToastUtil.showToast(getActivity(), "读取输入日期出错！");
                        return;
                    }
                    message.setTime(mill);
                    try {
                        dbManager.addMessage(message);
                    } catch (Exception e) {
                        ToastUtil.showToast(getActivity(), "添加数据出错！");
                    }
                    ToastUtil.showToast(getActivity(), "添加数据成功！");
                    Main main = (Main) getActivity();
                    main.displayFragment(new ShowFragment());
                    main.drawer.setSelection(3);

                }

            }
        });
        for (int i = 0; i < radioButtons.length; i++){
            radioButtons[i].setText(Data.ins[i + 1]);
        }
        rbIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbIn.setChecked(true);
                for (int i = 0; i < radioButtons.length; i++){
                    radioButtons[i].setText(Data.ins[i + 1]);

                }
            }
        });
        rbOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbOut.setChecked(true);
                for (int i = 0; i < radioButtons.length; i++){
                    radioButtons[i].setText(Data.outs[i + 1]);

                }
            }
        });
        ivValueCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edValue.setText("");
                ivValueCancel.setVisibility(View.INVISIBLE);
            }
        });
        ivOtherCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edOther.setText("");
                ivOtherCancel.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void initEditText(){
        edValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        edValue.setText(s);
                        edValue.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")){
                    s = "0" + s;
                    edValue.setText(s);
                    edValue.setSelection(s.length());
                }
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        s = s.subSequence(1, s.length());
                        edValue.setText(s);
                        edValue.setSelection(s.length());
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edValue.getText().length() > 0) {
                    ivValueCancel.setVisibility(View.VISIBLE);
                } else {
                    ivValueCancel.setVisibility(View.INVISIBLE);
                }
            }
        });
        edOther.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edOther.getText().length() > 0) {
                    ivOtherCancel.setVisibility(View.VISIBLE);
                } else {
                    ivOtherCancel.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initSpinner(){
        yearAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getYears());
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear.setAdapter(yearAdapter);
        monthAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getMonths());
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonth.setAdapter(monthAdapter);
        hourAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getHours());
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHour.setAdapter(hourAdapter);
        minAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getMins());
        minAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMin.setAdapter(minAdapter);
        Calendar c =  Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DATE);
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        spYear.setSelection(year - 2000);
        spMonth.setSelection(month - 1);
        spDay.setSelection(day - 1);
        spHour.setSelection(hour);
        spMin.setSelection(min);
        openDayChoose();
        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                openDayChoose();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                openDayChoose();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void openDayChoose(){
        if (month == 0 || year == 0){
            spDay.setEnabled(false);
        } else {
            if (TimeUtil.isSpecial(year)){
                dayAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getDaysSpecial(month));
                dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDay.setAdapter(dayAdapter);
                if(day > Data.daysSpecial[month - 1]){
                    day = Data.daysSpecial[month - 1];
                }
            } else {
                dayAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getDaysNormal(month));
                dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDay.setAdapter(dayAdapter);
                if(day > Data.daysNormal[month - 1]){
                    day = Data.daysNormal[month - 1];
                }
            }
            spDay.setEnabled(true);
            spDay.setSelection(day - 1);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dbManager.closeDB();
    }
}