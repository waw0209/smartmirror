package com.example.jangminjae.wheather;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.GregorianCalendar;

public class ScheduleActivity extends AppCompatActivity {

    Context context = ScheduleActivity.this;

    TextView tv_date, tv_time;
    EditText et_task;
    Button b_save, b_cancel;
    TimePicker timePicker;

    String date, time, task;
    Boolean click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_schedule);

        tv_date = (TextView)findViewById(R.id.date);
        tv_time = (TextView)findViewById(R.id.time);
        et_task =(EditText)findViewById(R.id.task);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        b_save = (Button)findViewById(R.id.save);
        b_cancel = (Button)findViewById(R.id.cancel);

        timePicker.setOnTimeChangedListener(onTimeChangedListener);
        tv_time.setOnClickListener(onClickListener);
        b_save.setOnClickListener(onClickListener);
        b_cancel.setOnClickListener(onClickListener);

        Bundle bundle = getIntent().getExtras();

        date = bundle.getString("Date").replace("-",".");
        tv_date.setText(date);

        GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
        int hour = c.get(GregorianCalendar.HOUR);
        int min = c.get(GregorianCalendar.MINUTE);

        time = hour+":"+min;
        tv_time.setText(time);


        click = false;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.time:
                    if(!click){
                        timePicker.setVisibility(View.VISIBLE);
                        click = true;
                    }
                    else{
                        timePicker.setVisibility(View.GONE);
                        click = false;
                    }
                    break;
                case R.id.save:
                    task = et_task.getText().toString();
                    String date_ym = date.substring(0, date.length()-3);
                    SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
                    String key = prefs.getString("key","");
                    String sender = prefs.getString("name","");

                    CalendarAdapter.schedule_string.add(date);

                    ScheduleInfo s = new ScheduleInfo();
                    s.execute(date, date_ym, time, task, key, sender);
                    break;
                case R.id.cancel:
                    finish();
                    break;
            }
        }
    };

    TimePicker.OnTimeChangedListener onTimeChangedListener = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker timepic, int hourOfDay, int minute) {

            String hour = String.valueOf(hourOfDay);
            String min = String.valueOf(minute);

            time = hour+":"+min;
            tv_time.setText(time);

        }
    };

    class ScheduleInfo extends AsyncTask<String, Void, String> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(context, "Checking Data..", "Please wait..", false, false);
        }

        @Override
        protected String doInBackground(String... parms) {

            Upload U = new Upload();
            String msg = U.uploadSchedule(parms[0], parms[1], parms[2], parms[3], parms[4], parms[5]);
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.v("TEST!!!업로드끝이야", s);
            loading.dismiss();
            finish();
        }
    }

}
