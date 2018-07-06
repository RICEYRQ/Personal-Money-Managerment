package com.example.riceyrq.personalmoneymanagerment.acitvity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.riceyrq.personalmoneymanagerment.R;
import com.example.riceyrq.personalmoneymanagerment.dataBase.DBManager;
import com.example.riceyrq.personalmoneymanagerment.define.User;
import com.example.riceyrq.personalmoneymanagerment.util.ToastUtil;

public class Login extends Activity {

    private EditText edUsername;
    private ImageView ivUsernameCancel;
    private EditText edPassword;
    private ImageView ivPasswordCancel;
    private ImageView ivPasswordShow;
    private CheckBox cbRemPas;
    private TextView tvFindPas;
    private Button btRegister;
    private Button btLogin;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        edUsername = (EditText) findViewById(R.id.et_userName);
        ivUsernameCancel = (ImageView) findViewById(R.id.iv_unameClear);
        edPassword = (EditText) findViewById(R.id.et_password);
        ivPasswordCancel = (ImageView) findViewById(R.id.iv_pwdClear);
        ivPasswordShow = (ImageView) findViewById(R.id.iv_pwdShow);
        cbRemPas = (CheckBox) findViewById(R.id.cb_checkbox);
        tvFindPas = (TextView) findViewById(R.id.tv_findPas);
        btRegister = (Button) findViewById(R.id.btn_register);
        btLogin = (Button) findViewById(R.id.btn_login);

        dbManager = new DBManager(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("np", MODE_PRIVATE);
        edUsername.setText(sharedPreferences.getString("name", ""));
        edPassword.setText(sharedPreferences.getString("pas", ""));

        if (!edPassword.getText().toString().equals("")){
            cbRemPas.setChecked(true);
            ivUsernameCancel.setVisibility(View.VISIBLE);
            ivPasswordCancel.setVisibility(View.VISIBLE);
        } else {
            cbRemPas.setChecked(false);
        }



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
                if (edPassword.getText().toString().equals("")){
                    return;
                }
                int inputType = edPassword.getInputType();
                int select = edPassword.getSelectionEnd();
                if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){//密码可见状态
                    //更换为密码隐藏状态
                    edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //ivPasswordShow 更换为睁眼
                } else {//密码不可见状态
                    //更换为密码可见状态
                    edPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //ivPasswordShow 更换为闭眼
                }
                edPassword.setSelection(select);
            }
        });

        tvFindPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edUsername.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("username", name);
                intent.setClass(Login.this, CheckName.class);
                startActivity(intent);
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
                boolean loginOK = false;
                boolean userExsist = false;
                User user = new User();
                user.setUserName(edUsername.getText().toString());
                user.setUserPas(edPassword.getText().toString());
                if (user.getUserName().equals("")){
                    ToastUtil.showToast(getApplicationContext(), "用户名不能为空！");
                    return;
                } else if (user.getUserPas().equals("")){
                    ToastUtil.showToast(getApplicationContext(), "密码不能为空！");
                    return;
                } else{
                    try {
                        userExsist = dbManager.isNameExsist(user);
                    } catch (Exception e) {
                        ToastUtil.showToast(getApplicationContext(), "检测用户名是否存在失败！");
                        return;
                    }
                    if (!userExsist){
                        ToastUtil.showToast(getApplicationContext(), "用户名不存在！");
                    } else {
                        try {
                            loginOK = dbManager.login(user);
                            if (loginOK){
                                boolean isRemPas = cbRemPas.isChecked();
                                SharedPreferences sharedPreferences = getSharedPreferences("np", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                if (isRemPas) {
                                    editor.putString("name", user.getUserName());
                                    editor.putString("pas", user.getUserPas());
                                } else {
                                    editor.putString("name", "");
                                    editor.putString("pas", "");
                                }
                                editor.apply();
                                ToastUtil.showToast(getApplicationContext(), "登录成功！");
                            } else {
                                ToastUtil.showToast(getApplicationContext(), "密码错误！");
                            }
                        } catch (Exception e) {
                            ToastUtil.showToast(getApplicationContext(), "登录失败！");
                        }
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
