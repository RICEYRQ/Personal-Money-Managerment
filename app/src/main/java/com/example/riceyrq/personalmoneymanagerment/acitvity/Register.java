package com.example.riceyrq.personalmoneymanagerment.acitvity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.riceyrq.personalmoneymanagerment.R;
import com.example.riceyrq.personalmoneymanagerment.dataBase.DBManager;
import com.example.riceyrq.personalmoneymanagerment.define.User;
import com.example.riceyrq.personalmoneymanagerment.util.BitUtil;
import com.example.riceyrq.personalmoneymanagerment.util.ToastUtil;

import static com.example.riceyrq.personalmoneymanagerment.R.id.btn_register_rg;

public class Register extends Activity {
    private EditText edUserName;
    private ImageView ivUserNameCancel;
    private EditText edPassword;
    private ImageView ivPasswordCancel;
    private ImageView ivPasswordShow;
    private EditText edPassword2;
    private ImageView ivPassword2Cancel;
    private ImageView ivPassword2Show;
    private EditText edPasQue;
    private ImageView ivPasQueCancel;
    private EditText edPasAus;
    private ImageView ivPasAusCancel;
    private Button register;
    private ImageView back;
    private DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registeer);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        edUserName = (EditText) findViewById(R.id.et_userName_rg);
        ivUserNameCancel = (ImageView) findViewById(R.id.iv_unameClear_rg);
        edPassword = (EditText) findViewById(R.id.et_password_rg);
        ivPasswordCancel = (ImageView) findViewById(R.id.iv_pwdClear_rg);
        ivPasswordShow = (ImageView) findViewById(R.id.iv_pwdShow_rg);
        edPassword2 = (EditText) findViewById(R.id.et_password2_rg);
        ivPassword2Cancel = (ImageView) findViewById(R.id.iv_pwdClear2_rg);
        ivPassword2Show = (ImageView) findViewById(R.id.iv_pwdShow2_rg);
        edPasQue = (EditText) findViewById(R.id.et_pasQue_rg);
        ivPasQueCancel = (ImageView) findViewById(R.id.iv_pasQueClear_rg);
        edPasAus = (EditText) findViewById(R.id.et_pasAus_rg);
        ivPasAusCancel = (ImageView) findViewById(R.id.iv_pasAusClear_rg);
        register = (Button) findViewById(btn_register_rg);
        back = (ImageView) findViewById(R.id.back_register);

        dbManager = new DBManager(getApplicationContext());

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

        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edPassword.getText().length() > 0){
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
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
                    ivPasswordShow.setImageDrawable(new BitmapDrawable(BitUtil.getBit(getApplicationContext(), R.drawable.eye_gray)));
                } else {//密码不可见状态
                    //更换为密码可见状态
                    edPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //ivPasswordShow 更换为闭眼
                    ivPasswordShow.setImageDrawable(new BitmapDrawable(BitUtil.getBit(getApplicationContext(), R.drawable.eye_blue)));
                }
                edPassword.setSelection(select);
            }
        });

        edPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edPassword2.getText().length() > 0){
                    ivPassword2Cancel.setVisibility(View.VISIBLE);
                } else {
                    ivPassword2Cancel.setVisibility(View.INVISIBLE);
                }
            }
        });

        ivPassword2Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edPassword2.setText("");
                ivPassword2Cancel.setVisibility(View.INVISIBLE);
            }
        });

        ivPassword2Show.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                if (edPassword2.getText().toString().equals("")){
                    return;
                }
                int inputType = edPassword2.getInputType();
                int select = edPassword2.getSelectionEnd();
                if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){//密码可见状态
                    //更换为密码隐藏状态
                    edPassword2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //ivPasswordShow 更换为睁眼
                    ivPassword2Show.setImageDrawable(new BitmapDrawable(BitUtil.getBit(getApplicationContext(), R.drawable.eye_gray)));
                } else {//密码不可见状态
                    //更换为密码可见状态
                    edPassword2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //ivPasswordShow 更换为闭眼
                    ivPassword2Show.setImageDrawable(new BitmapDrawable(BitUtil.getBit(getApplicationContext(), R.drawable.eye_blue)));
                }
                edPassword2.setSelection(select);
            }
        });

        edPasQue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edPasQue.getText().length() > 0){
                    ivPasQueCancel.setVisibility(View.VISIBLE);
                } else {
                    ivPasQueCancel.setVisibility(View.INVISIBLE);
                }
            }
        });

        ivPasQueCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edPasQue.setText("");
                ivPasQueCancel.setVisibility(View.INVISIBLE);
            }
        });

        edPasAus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edPasAus.getText().length() > 0){
                    ivPasAusCancel.setVisibility(View.VISIBLE);
                } else {
                    ivPasAusCancel.setVisibility(View.INVISIBLE);
                }
            }
        });

        ivPasAusCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edPasAus.setText("");
                ivPasAusCancel.setVisibility(View.INVISIBLE);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUserName.getText().toString();
                String pas = edPassword.getText().toString();
                String pas2 = edPassword2.getText().toString();
                String pasQue = edPasQue.getText().toString();
                String pasAus = edPasAus.getText().toString();
                if (username.equals("")){
                    ToastUtil.showToast(getApplicationContext(), "用户名不能为空！");
                } else if (pas.length() < 6 || pas2.length() < 6) {
                    ToastUtil.showToast(getApplicationContext(), "密码不能少于6位！");
                } else if (!pas.equals(pas2)){
                    ToastUtil.showToast(getApplicationContext(), "两次密码输入不一致！");
                } else if (pasQue.equals("")){
                    ToastUtil.showToast(getApplicationContext(), "密保问题不能为空！");
                } else if (pasAus.equals("")){
                    ToastUtil.showToast(getApplicationContext(), "密保答案不能为空！");
                } else {
                    boolean usernameExsist = false;
                    User user = new User();
                    user.setUserName(username);
                    user.setUserPas(pas);
                    user.setPasQuestion(pasQue);
                    user.setPasAnswer(pasAus);
                    try {
                        usernameExsist = dbManager.isNameExsist(user);
                    } catch (Exception e) {
                        ToastUtil.showToast(getApplicationContext(), "检测用户名是否存在失败！");
                        return;
                    }
                    if (usernameExsist) {
                        ToastUtil.showToast(getApplicationContext(), "当前用户名已被注册！");
                    } else {
                        try {
                            dbManager.register(user);
                        } catch (Exception e) {
                            ToastUtil.showToast(getApplicationContext(), "注册失败！");
                            return;
                        }
                        Register.this.finish();
                    }
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register.this.finish();
            }
        });




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDB();
    }
}
