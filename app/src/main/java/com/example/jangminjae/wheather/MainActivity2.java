package com.example.jangminjae.wheather;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity2 extends AppCompatActivity {                  // 비활성 액티비티


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.unactivit_main);

    }

    public void mainClick(View v){

        int id = v.getId();

        switch (id){

            case R.id.unact:

                Intent intent1 = null;
                intent1 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent1);
                break;

        }
    }

}
