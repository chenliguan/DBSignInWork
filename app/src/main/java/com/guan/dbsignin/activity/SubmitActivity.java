package com.guan.dbsignin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.guan.dbsignin.R;
import com.guan.dbsignin.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubmitActivity extends AppCompatActivity {

    @Bind(R.id.bt_back)
    Button btBack;
    @Bind(R.id.bt_submit)
    Button btSubmit;
    @Bind(R.id.et_name)
    EditText etName;

    private Intent _intent;
    private SimpleDateFormat sDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        ButterKnife.bind(this);

        sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    }

    @OnClick({R.id.bt_back, R.id.bt_submit})
    public void submit(View view) {

        switch (view.getId()) {
            case R.id.bt_back:
                _intent = new Intent(SubmitActivity.this, MainActivity.class);
                startActivity(_intent);
                this.finish();
                break;

            case R.id.bt_submit:
                User _user = new User();
                _user.setUserName(etName.getText().toString());
                _user.setUserAddress(sDateFormat.format(new java.util.Date()));
                MainActivity.mDBOperation.insertUser(_user);

                _intent = new Intent(SubmitActivity.this, MainActivity.class);
                startActivity(_intent);
                this.finish();
                break;

            default:
                break;
        }
    }
}
