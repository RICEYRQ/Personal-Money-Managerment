package com.example.riceyrq.personalmoneymanagerment.acitvity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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

public class SetPas extends Activity {

    private EditText edPas1;
    private ImageView ivPas1Cancel;
    private ImageView ivPas1Show;
    private EditText edPas2;
    private ImageView ivPas2Cancel;
    private ImageView ivPas2Show;
    private Button btnSetPas;
    private DBManager dbManager;
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pas);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        edPas1 = (EditText) findViewById(R.id.et_password_sp);
        ivPas1Cancel = (ImageView) findViewById(R.id.iv_pwdClear_sp);
        ivPas1Show = (ImageView) findViewById(R.id.iv_pwdShow_sp);
        edPas2 = (EditText) findViewById(R.id.et_password2_sp);
        ivPas2Cancel = (ImageView) findViewById(R.id.iv_pwdClear2_sp);
        ivPas2Show = (ImageView) findViewById(R.id.iv_pwdShow2_sp);
        btnSetPas = (Button) findViewById(R.id.btn_findPas_sp);

        dbManager = new DBManager(getApplicationContext());

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        edPas1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edPas1.getText().length() > 0){
                    ivPas1Show.setVisibility(View.VISIBLE);
                } else {
                    ivPas1Show.setVisibility(View.INVISIBLE);
                }
            }
        });

        ivPas1Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edPas1.setText("");
                ivPas1Cancel.setVisibility(View.INVISIBLE);
            }
        });

        ivPas1Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edPas1.getText().toString().equals("")){
                    return;
                }
                int inputType = edPas1.getInputType();
                int select = edPas1.getSelectionEnd();
                if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){//密码可见状态
                    //更换为密码隐藏状态
                    edPas1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //ivPasswordShow 更换为睁眼
                } else {//密码不可见状态
                    //更换为密码可见状态
                    edPas1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //ivPasswordShow 更换为闭眼
                }
                edPas1.setSelection(select);
            }
        });

        edPas2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edPas2.getText().length() > 0){
                    ivPas2Cancel.setVisibility(View.VISIBLE);
                } else {
                    ivPas2Cancel.setVisibility(View.INVISIBLE);
                }
            }
        });

        ivPas2Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edPas2.setText("");
                ivPas2Cancel.setVisibility(View.INVISIBLE);
            }
        });

        ivPas2Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edPas2.getText().toString().equals("")){
                    return;
                }
                int inputType = edPas2.getInputType();
                int select = edPas2.getSelectionEnd();
                if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){//密码可见状态
                    //更换为密码隐藏状态
                    edPas2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //ivPasswordShow 更换为睁眼
                } else {//密码不可见状态
                    //更换为密码可见状态
                    edPas2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //ivPasswordShow 更换为闭眼
                }
                edPas2.setSelection(select);
            }
        });

        btnSetPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pas1 = edPas1.getText().toString();
                String pas2 = edPas2.getText().toString();
                if (pas1.length() < 6 || pas2.length() < 6){
                    ToastUtil.showToast(getApplicationContext(), "密码不能少于6位！");
                } else if (!pas1.equals(pas2)){
                    ToastUtil.showToast(getApplicationContext(), "两次密码输入不一致！");
                } else {
                    User user = new User();
                    user.setUserName(username);
                    user.setUserPas(pas1);
                    try {
                        dbManager.changePas(user);
                    } catch (Exception e) {
                        ToastUtil.showToast(getApplicationContext(), "修改密码出错！");
                        return;
                    }
                    ToastUtil.showToast(getApplicationContext(), "修改密码成功！");
                    SetPas.this.finish();
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
