package com.example.jangminjae.wheather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class setting extends Activity implements
        OnClickListener {

Button mConfirm ,mCancel;
    TextView keycheak,name;
    SharedPreferences setting;
    String defValue=" ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        setContent();
    }

    private void setContent() {
        mConfirm = (Button) findViewById(R.id.btnConfirm);
        mCancel = (Button) findViewById(R.id.btnCancel1);
        keycheak = (TextView)findViewById(R.id.keycheak);
        name =(TextView)findViewById(R.id.textViewname);
        setting = getSharedPreferences("pref", MODE_PRIVATE);
        String keyvalu = setting.getString("key", defValue);
        String name2 = setting.getString("name",defValue);
        keycheak.setText(keyvalu);
        name.setText(name2);

        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel1:

                Intent intent = null;
                intent = new Intent(getApplicationContext(), settingchange.class);
                startActivity(intent);
                this.finish();

                break;

            case R.id.btnConfirm:
                this.finish();

                break;
            default:
                break;
        }
    }
}
