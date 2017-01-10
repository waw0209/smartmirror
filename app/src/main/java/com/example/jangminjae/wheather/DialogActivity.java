package com.example.jangminjae.wheather;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DialogActivity extends Activity implements
        OnClickListener {                                       // 미러 초기 설치 시 기본 값 설정 부분

    private Button mConfirm, mCancel;
    EditText editText,editText1;
    String Key, name;
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
        setting =getSharedPreferences("pref",MODE_PRIVATE);
        setContent();
    }

    private void setContent() {
        mConfirm = (Button) findViewById(R.id.btnConfirm);
        mCancel = (Button) findViewById(R.id.btnCancel1);
        editText = (EditText)findViewById(R.id.editText);
        editText1 = (EditText)findViewById(R.id.editText3);
        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel1:

                Key = editText.getText().toString();
                name = editText1.getText().toString();
                editor = setting.edit();
                editor.putString("name",name);
                editor.putString("key",Key);
                editor.commit();
                this.finish();       //get edittext 한다음에 key 스트링에 넣어준다 + json 맞춰서 코드 변환 하고 + 사진 동영상버튼
                break;

            case R.id.btnConfirm:


                Toast.makeText(this, "초기 설정 키 값이 필요 합니다", Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
    }
}
