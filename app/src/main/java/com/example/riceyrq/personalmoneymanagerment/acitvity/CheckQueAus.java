package com.example.riceyrq.personalmoneymanagerment.acitvity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.riceyrq.personalmoneymanagerment.R;
import com.example.riceyrq.personalmoneymanagerment.dataBase.DBManager;
import com.example.riceyrq.personalmoneymanagerment.util.ToastUtil;

public class CheckQueAus extends Activity {

    private TextView tvPasQue;
    private EditText edPasAus;
    private ImageView ivPasAusCancel;
    private Button nextStep;
    private DBManager dbManager;
    private String pasQue = "";
    private String pasAus = "";
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_que_aus);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        tvPasQue = (TextView) findViewById(R.id.tv_pasQue);
        edPasAus = (EditText) findViewById(R.id.et_pasAus_cqa);
        ivPasAusCancel = (ImageView) findViewById(R.id.iv_pasAusClear_cqa);
        nextStep = (Button) findViewById(R.id.btn_findPas_cqa);

        dbManager = new DBManager(getApplicationContext());

        final Intent intent = getIntent();
        pasQue = intent.getStringExtra("pasque");
        pasAus = intent.getStringExtra("pasaus");
        username = intent.getStringExtra("username");

        tvPasQue.setText("密保问题：" + pasQue);

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

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edPasAus.getText().toString().equals(pasAus)){
                    Intent intent1 = new Intent();
                    intent1.putExtra("username", username);
                    intent1.setClass(CheckQueAus.this, SetPas.class);
                    startActivity(intent1);
                    CheckQueAus.this.finish();
                } else {
                    ToastUtil.showToast(getApplicationContext(), "密保答案错误！");
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
