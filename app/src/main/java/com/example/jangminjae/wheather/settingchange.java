package com.example.jangminjae.wheather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class settingchange extends Activity implements
        OnClickListener {

    Button mConfirm1 ,mCancel1;
    String key=" " , name =" ";
    EditText editText,editText1;
    SharedPreferences setting;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settingchange);
        setContent();
    }

    private void setContent() {
        mConfirm1 = (Button) findViewById(R.id.btnConfirm1);
        mCancel1 = (Button) findViewById(R.id.btnCancel1);
        editText=(EditText)findViewById(R.id.editText2);
        editText1=(EditText)findViewById(R.id.editTextname2);
        setting = getSharedPreferences("pref", MODE_PRIVATE);
        mConfirm1.setOnClickListener(this);
        mCancel1.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel1:


                key = editText.getText().toString();
                name = editText1.getText().toString();
                editor =setting.edit();
                editor.putString("key",key);
                editor.putString("name",name);
                editor.commit();

                Intent intent = null;
                intent = new Intent(getApplicationContext(), setting.class);
                startActivity(intent);

                Toast.makeText(getApplication(),"설정 값 변경완료",Toast.LENGTH_LONG).show();
                this.finish();

                break;

            case R.id.btnConfirm1:
                this.finish();

                break;
            default:
                break;
        }
    }
}
