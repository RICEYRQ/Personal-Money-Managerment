package com.example.riceyrq.personalmoneymanagerment.acitvity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.riceyrq.personalmoneymanagerment.R;
import com.example.riceyrq.personalmoneymanagerment.dataBase.DBManager;
import com.example.riceyrq.personalmoneymanagerment.define.User;
import com.example.riceyrq.personalmoneymanagerment.util.ToastUtil;

public class CheckName extends Activity {

    private EditText edUserName;
    private ImageView ivUserNameCancel;
    private Button nextStep;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_name);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        edUserName = (EditText) findViewById(R.id.et_userName_cn);
        ivUserNameCancel = (ImageView) findViewById(R.id.iv_unameClear_cn);
        nextStep = (Button) findViewById(R.id.btn_findPas_cn);

        dbManager = new DBManager(getApplicationContext());

        final Intent intent = getIntent();
        edUserName.setText(intent.getStringExtra("username"));

        if (!edUserName.getText().toString().equals("")){
            ivUserNameCancel.setVisibility(View.VISIBLE);
        }

        edUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edUserName.getText().length() > 0){
                    ivUserNameCancel.setVisibility(View.VISIBLE);
                } else {
                    ivUserNameCancel.setVisibility(View.INVISIBLE);
                }
            }
        });

        ivUserNameCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edUserName.setText("");
                ivUserNameCancel.setVisibility(View.INVISIBLE);
            }
        });

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUserName.getText().toString();
                if (username.equals("")){
                    ToastUtil.showToast(getApplicationContext(), "请输入用户名");
                } else {
                    boolean nameExsist = false;
                    User user = new User();
                    user.setUserName(username);
                    try {
                        nameExsist = dbManager.isNameExsist(user);
                    } catch (Exception e) {
                        ToastUtil.showToast(getApplicationContext(), "检测用户名是否存在失败！");
                        return;
                    }
                    if (nameExsist){
                        User user1 = new User();
                        try {
                            user1 = dbManager.getPasQueAndAns(user);
                        } catch (Exception e) {
                            ToastUtil.showToast(getApplicationContext(), "获取密保问题及答案失败！");
                            return;
                        }
                        Intent intent1 = new Intent();
                        intent1.putExtra("pasque", user1.getPasQuestion());
                        intent1.putExtra("pasaus", user1.getPasAnswer());
                        intent1.putExtra("username", user.getUserName());
                        intent1.setClass(CheckName.this, CheckQueAus.class);
                        startActivity(intent1);
                        CheckName.this.finish();
                    } else {
                        ToastUtil.showToast(getApplicationContext(), "用户名不存在！");
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDB();
    }
}
