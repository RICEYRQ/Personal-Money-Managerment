package com.example.riceyrq.personalmoneymanagerment.acitvity.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.riceyrq.personalmoneymanagerment.R;
import com.example.riceyrq.personalmoneymanagerment.acitvity.Main;
import com.example.riceyrq.personalmoneymanagerment.dataBase.DBManager;
import com.example.riceyrq.personalmoneymanagerment.define.Data;
import com.example.riceyrq.personalmoneymanagerment.define.Message;
import com.example.riceyrq.personalmoneymanagerment.util.TimeUtil;
import com.example.riceyrq.personalmoneymanagerment.util.ToastUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShowFragment extends ToolbarFragment {
    private Spinner spYear1;
    private Spinner spMonth1;
    private Spinner spDay1;
    private Spinner spYear2;
    private Spinner spMonth2;
    private Spinner spDay2;
    private Button btnConfirm;
    private Button btnCancel;
    private RadioButton rbAll;
    private RadioButton rbIn;
    private RadioButton rbOut;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private RecyclerView rcv;
    private ArrayAdapter<Integer> yearAdapter;
    private ArrayAdapter<Integer> monthAdapter;
    private ArrayAdapter<Integer> dayAdapter;
    private int year1 = 0;
    private int month1 = 0;
    private int day1 = 0;
    private int year2 = 0;
    private int month2 = 0;
    private int day2 = 0;
    private boolean inChange = false;
    private DBManager dbManager;
    private List<Message> messagess = new ArrayList<>();
    private List<Message> showMess = new ArrayList<>();
    private MessagesAdapter messagesAdapter;
    private int chooseBtn = 0;


    public ShowFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.show);
        spYear1 = view.findViewById(R.id.year1);
        spMonth1 = view.findViewById(R.id.month1);
        spDay1 = view.findViewById(R.id.day1);
        spYear2 = view.findViewById(R.id.year2);
        spMonth2 = view.findViewById(R.id.month2);
        spDay2 = view.findViewById(R.id.day2);
        btnConfirm = view.findViewById(R.id.btn_confirm_fraShow);
        btnCancel = view.findViewById(R.id.btn_cancel_fraShow);
        rbAll = view.findViewById(R.id.rb_all_fraShow);
        rbIn = view.findViewById(R.id.rb_in_fraShow);
        rbOut = view.findViewById(R.id.rb_out_fragShow);
        btn1 = view.findViewById(R.id.btn1_fraShow);
        btn2 = view.findViewById(R.id.btn2_fraShow);
        btn3 = view.findViewById(R.id.btn3_fraShow);
        btn4 = view.findViewById(R.id.btn4_fraShow);
        btn5 = view.findViewById(R.id.btn5_fraShow);
        rcv = view.findViewById(R.id.rcv_fraShow);
        dbManager = new DBManager(getActivity());
        initSpinner();
        initButton();
        try {
            initRecycleView();
        } catch (ParseException e) {
            ToastUtil.showToast(getActivity(), "数据读取出错！");
        }
        closeAllSpinner();
    }

    private void initRecycleView() throws ParseException {
        messagess = dbManager.getMessage(year1, month1, day1, year2, month2, day2, username);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(linearLayoutManager);
        messagesAdapter = new MessagesAdapter(messagess);
        rcv.setAdapter(messagesAdapter);
        rcv.setHasFixedSize(true);
        messagesAdapter.notifyDataSetChanged();
        showMess = messagess;
    }

    public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>{
        private List<Message> messageList;

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView value;
            TextView other;
            TextView kind;
            TextView time;
            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.iv_item);
                value = itemView.findViewById(R.id.tv_value_item);
                other = itemView.findViewById(R.id.tv_other_item);
                kind = itemView.findViewById(R.id.tv_kind_item);
                time = itemView.findViewById(R.id.tv_time_item);
            }
        }

        public MessagesAdapter(List<Message> messages){
            messageList = messages;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Message message = messageList.get(position);
            holder.value.setText(String.valueOf(message.getValue()));
            holder.kind.setText(message.getKind());
            holder.other.setText(message.getOther());
            holder.time.setText(TimeUtil.stampToDate(message.getTime()));
            if (message.getInOrOut() == 0) {
                holder.imageView.setBackgroundColor(getActivity().getColor(R.color.loveBlue));
            } else if (message.getInOrOut() == 1) {
                holder.imageView.setBackgroundColor(getActivity().getColor(R.color.loveGary));
            }
        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }
    }

    private void initButton(){
        changeBtnsColor();
        btnConfirm.setText("修改");
        btnCancel.setVisibility(View.INVISIBLE);
        final Button[] buttons = new Button[]{btn1, btn2, btn3, btn4, btn5};
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inChange) {
                    //
                    int year11 = spYear1.getSelectedItemPosition() + 2000;
                    int month11 = spMonth1.getSelectedItemPosition() + 1;
                    int day11 = spDay1.getSelectedItemPosition() + 1;
                    int year22 = spYear2.getSelectedItemPosition() + 2000;
                    int month22 = spMonth2.getSelectedItemPosition() + 1;
                    int day22 = spDay2.getSelectedItemPosition() + 1;
                    if (TimeUtil.isOkYMD(year11, month11, day11, year22, month22, day22)){
                        year1 = spYear1.getSelectedItemPosition() + 2000;
                        month1 = spMonth1.getSelectedItemPosition() + 1;
                        day1 = spDay1.getSelectedItemPosition() + 1;
                        year2 = spYear2.getSelectedItemPosition() + 2000;
                        month2 = spMonth2.getSelectedItemPosition() + 1;
                        day2 = spDay2.getSelectedItemPosition() + 1;
                        try {
                            messagess = dbManager.getMessage(year1, month1, day1, year2, month2, day2, username);
                        } catch (ParseException e) {
                            ToastUtil.showToast(getActivity(), "数据读取出错！");
                            return;
                        }
                        refreshMes();
                    } else {
                        ToastUtil.showToast(getActivity(), "不合法的输入！请修改！");
                        return;
                    }
                    btnConfirm.setText("修改");
                    btnCancel.setVisibility(View.INVISIBLE);
                    inChange = false;
                    closeAllSpinner();
                } else {
                    btnConfirm.setText("确定");
                    btnCancel.setText("取消");
                    btnCancel.setVisibility(View.VISIBLE);
                    openAllSpinner();
                    inChange = true;
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spYear1.setSelection(year1 - 2000);
                spYear2.setSelection(year2 - 2000);
                spMonth1.setSelection(month1 - 1);
                spMonth2.setSelection(month2 - 1);
                spDay1.setSelection(day1 - 1);
                spDay2.setSelection(day2 - 1);
                closeAllSpinner();
                btnConfirm.setText("修改");
                btnCancel.setVisibility(View.INVISIBLE);
                inChange = false;
            }
        });
        for (int i = 0; i < buttons.length; i++){
            buttons[i].setText(Data.ins[i]);
            buttons[i].setVisibility(View.GONE);
            final int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    changeBtnsColor();
                    buttons[finalI].setBackgroundColor(getActivity().getColor(R.color.loveBlue));
                    chooseBtn = finalI;
                    refreshMes();
                }
            });
        }
        rbAll.setChecked(true);
        rbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbAll.setChecked(true);
                for (int i = 0; i < buttons.length; i++){
                    buttons[i].setVisibility(View.GONE);
                }
                chooseBtn = -1;
                refreshMes();
            }
        });
        rbIn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                rbIn.setChecked(true);
                for (int i = 0; i < buttons.length; i++){
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setText(Data.ins[i]);
                }
                chooseBtn = 0;
                btn1.setBackgroundColor(getActivity().getColor(R.color.loveBlue));
                refreshMes();
            }
        });
        rbOut.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                rbOut.setChecked(true);
                for (int i = 0; i < buttons.length; i++){
                    buttons[i].setVisibility(View.VISIBLE);
                    buttons[i].setText(Data.outs[i]);
                }
                chooseBtn = 0;
                btn1.setBackgroundColor(getActivity().getColor(R.color.loveBlue));
                refreshMes();
            }
        });
    }

    private void refreshMes(){
        showMess = new ArrayList<>();
        if (rbAll.isChecked()){
            showMess = messagess;
        } else if (rbIn.isChecked()) {
            for (int i = 0; i < messagess.size(); i++){
                Message message = messagess.get(i);
                if (chooseBtn == 0) {
                    if (message.getInOrOut() == 0) {
                        showMess.add(message);
                    }
                } else {
                    if (message.getInOrOut() == 0 && message.getKind().equals(Data.ins[chooseBtn])) {
                        showMess.add(message);
                    }
                }
            }
        } else if (rbOut.isChecked()) {
            for (int i = 0; i < messagess.size(); i++){
                Message message = messagess.get(i);
                if (chooseBtn == 0) {
                    if (message.getInOrOut() == 1) {
                        showMess.add(message);
                    }
                } else {
                    if (message.getInOrOut() == 1 && message.getKind().equals(Data.ins[chooseBtn])) {
                        showMess.add(message);
                    }
                }
            }
        }
        messagesAdapter = new MessagesAdapter(showMess);
        rcv.setAdapter(messagesAdapter);
        rcv.setHasFixedSize(true);
        messagesAdapter.notifyDataSetChanged();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void changeBtnsColor(){
        final Button[] buttons = new Button[]{btn1, btn2, btn3, btn4, btn5};
        for (int i = 0; i < buttons.length; i++){
            buttons[i].setBackgroundColor(getActivity().getColor(R.color.loveGary));
        }
    }

    private void initSpinner(){
        yearAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getYears());
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear1.setAdapter(yearAdapter);
        spYear2.setAdapter(yearAdapter);
        monthAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getMonths());
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonth1.setAdapter(monthAdapter);
        spMonth2.setAdapter(monthAdapter);
        Calendar c =  Calendar.getInstance();
        year1 = c.get(Calendar.YEAR);
        month1 = c.get(Calendar.MONTH) + 1;
        day1 = 1;
        year2 = year1;
        month2 = month1;
        if (TimeUtil.isSpecial(year1)){
            day2 = Data.daysSpecial[month2 - 1];
        } else {
            day2 = Data.daysNormal[month2 - 1];
        }
        spYear1.setSelection(year1 - 2000);
        spYear2.setSelection(year2 - 2000);
        spMonth1.setSelection(month1 - 1);
        spMonth2.setSelection(month2 - 1);
        spDay1.setSelection(day1 - 1);
        spDay2.setSelection(day2 - 1);
        openDayChoose1();
        openDayChoose2();
        spYear1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                openDayChoose1();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spYear2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                openDayChoose2();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spMonth1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                openDayChoose1();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spMonth2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                openDayChoose2();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        closeAllSpinner();
    }

    private void openAllSpinner(){
        spYear1.setEnabled(true);
        spYear2.setEnabled(true);
        spMonth1.setEnabled(true);
        spMonth2.setEnabled(true);
        spDay1.setEnabled(true);
        spDay2.setEnabled(true);
    }

    private void closeAllSpinner(){
        spYear1.setEnabled(false);
        spYear2.setEnabled(false);
        spMonth1.setEnabled(false);
        spMonth2.setEnabled(false);
        spDay1.setEnabled(false);
        spDay2.setEnabled(false);
    }

    private void openDayChoose1(){
        if (month1 == 0 || year1 == 0){
            spDay1.setEnabled(false);
        } else {
            if (TimeUtil.isSpecial(year1)){
                dayAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getDaysSpecial(month1));
                dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDay1.setAdapter(dayAdapter);
                if(day1 > Data.daysSpecial[month1 - 1]){
                    day1 = Data.daysSpecial[month1 - 1];
                }
            } else {
                dayAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getDaysNormal(month1));
                dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDay1.setAdapter(dayAdapter);
                if(day1 > Data.daysNormal[month1 - 1]){
                    day1 = Data.daysNormal[month1 - 1];
                }
            }
            spDay1.setEnabled(true);
            spDay1.setSelection(day1 - 1);
        }
    }

    private void openDayChoose2(){
        if (month2 == 0 || year2 == 0){
            spDay2.setEnabled(false);
        } else {
            if (TimeUtil.isSpecial(year2)){
                dayAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getDaysSpecial(month2));
                dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDay2.setAdapter(dayAdapter);
                if(day2 > Data.daysSpecial[month2 - 1]){
                    day2 = Data.daysSpecial[month2 - 1];
                }
            } else {
                dayAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, TimeUtil.getDaysNormal(month2));
                dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDay2.setAdapter(dayAdapter);
                if(day2 > Data.daysNormal[month2 - 1]){
                    day2 = Data.daysNormal[month2 - 1];
                }
            }
            spDay2.setEnabled(true);
            spDay2.setSelection(day2 - 1);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        dbManager.closeDB();
    }
}