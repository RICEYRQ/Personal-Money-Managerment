package com.example.riceyrq.personalmoneymanagerment.acitvity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.riceyrq.personalmoneymanagerment.R;

public class Login extends AppCompatActivity {

    private EditText edUsername;
    private ImageView ivUsernameCancel;
    private EditText edPassword;
    private ImageView ivPasswordCancel;
    private ImageView ivPasswordShow;
    private CheckBox cbRemPas;
    private Button btRegister;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = (EditText) findViewById(R.id.et_userName);
        ivUsernameCancel = (ImageView) findViewById(R.id.iv_unameClear);
        edPassword = (EditText) findViewById(R.id.et_password);
        ivPasswordCancel = (ImageView) findViewById(R.id.iv_pwdClear);
        ivPasswordShow = (ImageView) findViewById(R.id.iv_pwdShow);
        cbRemPas = (CheckBox) findViewById(R.id.cb_checkbox);
        btRegister = (Button) findViewById(R.id.btn_register);
        btLogin = (Button) findViewById(R.id.btn_login);

        edUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edUsername.getText().length() > 0){
                    ivUsernameCancel.setVisibility(View.VISIBLE);
                } else {
                    ivUsernameCancel.setVisibility(View.INVISIBLE);
                }

            }
        });

        ivUsernameCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edUsername.setText("");
                ivUsernameCancel.setVisibility(View.INVISIBLE);
            }
        });

        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edPassword.getText().length() > 0) {
                    ivPasswordCancel.setVisibility(View.VISIBLE);
                } else {
                    ivPasswordCancel.setVisibility(View.INVISIBLE);
                }
            }
        });

        ivPasswordCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edPassword.setText("");
                ivPasswordCancel.setVisibility(View.INVISIBLE);
            }
        });

        ivPasswordShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int inputType = edPassword.getInputType();
                if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){//密码可见状态
                    //更换为密码隐藏状态
                    edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //ivPasswordShow 更换为睁眼
                } else {//密码不可见状态
                    //更换为密码可见状态
                    edPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //ivPasswordShow 更换为闭眼
                }
            }
        });


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Login.this, Register.class);
                startActivity(intent);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
