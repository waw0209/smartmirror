package com.example.jangminjae.wheather;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static final int THREAD_HANDLER_SUCCESS_INFO = 1;
    TextView tv_WeatherInfo;
    TextView currentday, currentday1;
    ArrayList<ListItem> listItem;
    ArrayList<ListItem1> listItem1;
    IconTextListAdapter adapter, adapter1, adapter2, adapter3;
    ImageView imageView1;
    ImageView imageView;

    ForeCastManager mForeCast;
    Bitmap bitmap;
    String uri = "http://222.112.247.133:80/"; // ip 고정화
    String uriplus = " ";
    String videoPath = " ";
    private final static String keyinit = " ";
    String lon = "126.880373"; // 좌표 설정  경도
    String lat = "37.481223";  // 좌표 설정  위도  가산디지털단지좌표
    String state;

    WebView web;

    ArrayList<ContentValues> mWeatherData;
    ArrayList<WeatherInfo> mWeatherInfomation;
    List<String> point_string;

    private MyTimerTask myTimerTask;
    private Timer timer;
    long now = System.currentTimeMillis();
    ListView list;
    MainActivity mThis;
    VideoView videoView;
    SharedPreferences setting;

    int callvalu = 0;   // 미디어 1 전화번호 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 1000, 60000);

        setting = getSharedPreferences("pref", 0);
        String keyvalu = setting.getString("key", keyinit);
        point_string = new ArrayList<String>();

        if (keyvalu == " ") {                              // 초기 key 값 유무 판별

            Intent intent = null;
            intent = new Intent(getApplicationContext(), DialogActivity.class);
            startActivity(intent);

        }
        Initialize();
    }

    public void Initialize() {                // 초 기 화

        list = (ListView) findViewById(R.id.list);
        tv_WeatherInfo = (TextView) findViewById(R.id.tv_WeatherInfo);
        currentday = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);
        currentday1 = (TextView) findViewById(R.id.textView2);
        imageView1 = (ImageView) findViewById(R.id.imageView2);
        videoView = (VideoView) findViewById(R.id.videoView);
        web = (WebView) findViewById(R.id.web);
        mWeatherInfomation = new ArrayList<>();
        mThis = this;
        mForeCast = new ForeCastManager(lon, lat, mThis);
        mForeCast.run();
        imageView1.setImageBitmap(null);
        web.setVisibility(web.INVISIBLE);
        videoView.setVisibility(videoView.INVISIBLE);

        MediaController controller = new MediaController(MainActivity.this);
        videoView.setMediaController(controller);

    }

    @Override
    protected void onResume() {                                     // 리스트 초기화
        super.onResume();

        adapter = new IconTextListAdapter(this);
        adapter1 = new IconTextListAdapter(this);
        adapter3 = new IconTextListAdapter(this);
        listItem = new ArrayList<ListItem>();
        listItem1 = new ArrayList<ListItem1>();

    }


    public void mainClick(View v) {           // 비활성 화면

        int id = v.getId();

        switch (id) {

            case R.id.act:

                imageView1.setImageBitmap(null);
                Intent intent = null;
                intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
                break;


        }
    }

    private void CheckLoginInfo() {              // json 형식 String 에서 adapter에 매칭

        class checkLoginInfo extends AsyncTask<Void, Void, String> {

            protected void onPostExecute(String str) {

                String imgurl;
                String txt1;
                String txt2;
                String txt3;

                try {

                    JSONObject root = new JSONObject(str);
                    JSONArray ja = root.getJSONArray("results");
                    for (int i = 0; i < ja.length(); i++) {

                        if (callvalu == 1) {
                            JSONObject jo = ja.getJSONObject(i);
                            imgurl = jo.getString("file_path");
                            txt1 = jo.getString("set_name");
                            txt2 = jo.getString("file_type");
                            txt3 = jo.getString("message");

                            listItem.add(new ListItem(imgurl, txt1, txt2, txt3));
                        }
                        if (callvalu == 2) {

                            JSONObject jo = ja.getJSONObject(i);
                            txt1 = jo.getString("name");
                            imgurl = jo.getString("phone");
                            ;
                            listItem1.add(new ListItem1(imgurl, txt1));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (callvalu == 1) {
                    for (int i = 0; i < listItem.size(); i++) {

                        if ("image".equals(listItem.get(i).getData(2)) && "img".equals(state)) {  // 미디어 매체에 따른 리스트 출력 (사진)

                            adapter.addItem(new IconTextItem(listItem.get(i).getData(1), "메세지 = " + listItem.get(i).getData(3), listItem.get(i).getData(2), listItem.get(i).getData(0)));
                            list.setAdapter(adapter);

                        } else if ("video".equals(listItem.get(i).getData(2)) && "video".equals(state)) {  // 미디어 매체에 따른 리스트 출력 (비디오)

                            adapter1.addItem(new IconTextItem(listItem.get(i).getData(1), listItem.get(i).getData(3), listItem.get(i).getData(2), listItem.get(i).getData(0)));
                            list.setAdapter(adapter1);

                        }
                    }
                } else if ("call".equals(state)) {                      // 전화번호 리스트

                    for (int i = 0; i < listItem1.size(); i++) {
                        adapter3.addItem(new IconTextItem(listItem1.get(i).getData(1), listItem1.get(i).getData(0), "--", " "));
                        list.setAdapter(adapter3);
                    }
                }

            }

            @Override
            protected String doInBackground(Void... parms) {     // 미러 보유 key 값에 따른 http 연결

                String result = setting.getString("key", "0");
                CheckInfo checkInfo = new CheckInfo();
                String msg = "";
                msg = checkInfo.checkInfo(result, callvalu);

                return msg;

            }
        }

        checkLoginInfo ci = new checkLoginInfo();
        ci.execute();
    }

    public void phoneListView() {               // 전화번호 메뉴 클릭 이벤트

        web.setVisibility(web.INVISIBLE);
        videoView.setVisibility(videoView.INVISIBLE);
        callvalu = 2;
        onResume();
        state = "call";
        CheckLoginInfo();
        imageView1.setImageBitmap(null);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }

        });

    }


    public void videoListView() {                // 비디오 메뉴 클릭 이벤트
        // 하드웨어적 문제로 인한 딜레이 발생

        web.setVisibility(web.INVISIBLE);
        videoView.setVisibility(videoView.INVISIBLE);
        callvalu = 1;
        onResume();
        state = "video";
        CheckLoginInfo();

        imageView1.setImageBitmap(null);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                IconTextItem curItem = (IconTextItem) adapter1.getItem(position);
                String[] curData = curItem.getData();
                videoPath = uri + curData[3];
                Log.v("Test", videoPath);

                videoView.setVisibility(videoView.VISIBLE);
                videoView.setVideoPath(videoPath);
                videoView.start();

            }

        });
    }


    public void imageListView() {                       // 이미지 메뉴 클릭 이벤트

        web.setVisibility(web.INVISIBLE);
        imageView1.setImageBitmap(null);
        videoView.setVisibility(videoView.INVISIBLE);
        callvalu = 1;
        onResume();
        state = "img";
        CheckLoginInfo();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                IconTextItem curItem = (IconTextItem) adapter.getItem(position);
                String[] curData = curItem.getData();
                uriplus = uri + curData[3];
                Log.v("Test", uriplus);
                URL url = null;
                try {
                    url = new URL(uriplus);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                conn.setDoInput(true);
                try {
                    conn.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream is = null;
                try {
                    is = conn.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final int MIRROR_MAX_TEXTURE_SIZE = 2048;
                /* 디바이스 별 rendering 가능한 텍스쳐 크기의 최대값을 구하는 코드는 아래와 같다.
                int[] max = new int[1]; maxTextureSize = getMaxTextureSize(); // max[1] : 텍스쳐 크기 최대값
                우리의 미러 OS로 테스트한 결과 2048이 나왔고, 위의 코드를 실행시키면
                이미지 로딩 속도가 매우 느려져서 그냥 상수로 선언함. */

                int maxTextureSize = MIRROR_MAX_TEXTURE_SIZE;
                Log.i("openGL info", "Max texture size = " + maxTextureSize);
                final BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 8;
                bitmap = BitmapFactory.decodeStream(is, null, opts);
                imageView1.setImageBitmap(bitmap);

            }

        });
    }

    public void onClickButton(View v) {              // 메인 메뉴 이벤트 매칭

        int id = v.getId();

        switch (id) {

            case R.id.button1:   // 사진

                imageListView();
                break;

            case R.id.button2:  // 동영상

                videoListView();
                break;

            case R.id.button4:    // 홈

                web.setVisibility(web.INVISIBLE);
                list.setAdapter(adapter2);
                imageView1.setImageBitmap(null);
                videoView.setVisibility(videoView.INVISIBLE);
                break;

            case R.id.button: // 주소록

                phoneListView();
                break;

            case R.id.button5: // 실시간 스트리밍

                list.setAdapter(adapter2);
                web.setVisibility(web.VISIBLE);
                web.setBackgroundColor(0);
                web.getSettings().setJavaScriptEnabled(true);

                web.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView v, String s) {
                        return super.shouldOverrideUrlLoading(v, s);
                    }
                });
                web.loadUrl("http://222.112.247.133:8080/stream");
                web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

                break;


            case R.id.button7:         // 캘린더

                CalendarPoint cp = new CalendarPoint();
                cp.execute(setting.getString("key", ""));

                break;


            case R.id.button6:          //설정

                list.setAdapter(adapter2);
                web.setVisibility(web.INVISIBLE);
                imageView1.setImageBitmap(null);
                videoView.setVisibility(videoView.INVISIBLE);

                Intent intent = null;
                intent = new Intent(getApplicationContext(), setting.class);
                startActivity(intent);

                break;

        }
    }

    class CalendarPoint extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... parms) {

            Download D = new Download();
            String msg = D.downloadCalendar(parms[0]);
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.v("TEST달력포인트", s);

            String date;
            try {
                JSONObject root = new JSONObject(s);
                JSONArray ja = root.getJSONArray("results");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    date = jo.getString("date");
                    point_string.add(date);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CalendarAdapter.schedule_string = point_string;
            Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(intent);

        }
    }

    class MyTimerTask extends TimerTask {                   // 메인 화면 시간 구현

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" yyyy-MM-dd  ");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(" a HH:mm ");
            final String strDate = simpleDateFormat.format(calendar.getTime());
            final String strDate1 = simpleDateFormat1.format(calendar.getTime());

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    currentday.setText(strDate);
                    currentday1.setText(strDate1);
                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        Log.i("test", "onDstory()");
        timer.cancel();
        super.onDestroy();
    }

    public String PrintValue() {              // 날씨 데이터 반환

        String mData = "";
        mData = mData + "최고온도  /  최저온도 " + "\n"
                + mWeatherInfomation.get(0).getTemp_Max() + "℃      " + mWeatherInfomation.get(0).getTemp_Min() + "℃";

        return mData;
    }

    public void DataChangedToHangeul() {         // 날씨 API 데이터 한글화

        for (int i = 0; i < mWeatherInfomation.size(); i++) {

            WeatherToHangeul mHangeul = new WeatherToHangeul(mWeatherInfomation.get(i));
            mWeatherInfomation.set(i, mHangeul.getHangeulWeather());

        }
    }

    public void WheatherImage() {                   // 날씨에 따른 이미지 매칭

        if ("맑은 하늘".equals(mWeatherInfomation.get(0).getWeather_Name()) || "바람 없음".equals(mWeatherInfomation.get(0).getWeather_Name())) {
            // png 파일 ImageView 올리기만 하면됨
            Drawable drawable = getResources().getDrawable(R.drawable.clean);
            imageView.setImageDrawable(drawable);

        } else if ("구름 조금".equals(mWeatherInfomation.get(0).getWeather_Name()) || "조각 구름".equals(mWeatherInfomation.get(0).getWeather_Name())) {
            // png 파일 ImageView 올리기만 하면됨
            Drawable drawable = getResources().getDrawable(R.drawable.cloud);
            imageView.setImageDrawable(drawable);

        } else if ("흐림".equals(mWeatherInfomation.get(0).getWeather_Name())) {
            // png 파일 ImageView 올리기만 하면됨
            Drawable drawable = getResources().getDrawable(R.drawable.fade);
            imageView.setImageDrawable(drawable);

        } else if ("번개와 보슬비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "번개와 비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "번개와 집중 호우".equals(mWeatherInfomation.get(0).getWeather_Name()) || "번개와 가벼운 이슬비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "번개와 이슬비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "번개와 집중 호우".equals(mWeatherInfomation.get(0).getWeather_Name())) {
            // png 파일 ImageView 올리기만 하면됨
            Drawable drawable = getResources().getDrawable(R.drawable.lightningrain);
            imageView.setImageDrawable(drawable);

        } else if ("천둥".equals(mWeatherInfomation.get(0).getWeather_Name()) || "천둥 번개".equals(mWeatherInfomation.get(0).getWeather_Name()) || "강한 천둥 번개".equals(mWeatherInfomation.get(0).getWeather_Name()) || "매우 강한 천둥 번개".equals(mWeatherInfomation.get(0).getWeather_Name())) {
            // png 파일 ImageView 올리기만 하면됨
            Drawable drawable = getResources().getDrawable(R.drawable.lightning);
            imageView.setImageDrawable(drawable);

        } else if ("소나기".equals(mWeatherInfomation.get(0).getWeather_Name()) || "강한 소나기".equals(mWeatherInfomation.get(0).getWeather_Name()) || "가벼운 소나기".equals(mWeatherInfomation.get(0).getWeather_Name()) || "매우 강한 소나기".equals(mWeatherInfomation.get(0).getWeather_Name())) {
            // png 파일 ImageView 올리기만 하면됨
            Drawable drawable = getResources().getDrawable(R.drawable.shower);
            imageView.setImageDrawable(drawable);

        } else if ("약한 눈과 비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "눈과 비".equals(mWeatherInfomation.get(0).getWeather_Name())) {
            // png 파일 ImageView 올리기만 하면됨
            Drawable drawable = getResources().getDrawable(R.drawable.snowrain);
            imageView.setImageDrawable(drawable);

        } else if ("센바람".equals(mWeatherInfomation.get(0).getWeather_Name()) || "강풍".equals(mWeatherInfomation.get(0).getWeather_Name()) || "남실 바람".equals(mWeatherInfomation.get(0).getWeather_Name()) || "산들 바람".equals(mWeatherInfomation.get(0).getWeather_Name()) || "건들 바람".equals(mWeatherInfomation.get(0).getWeather_Name()) || "흔들 바람".equals(mWeatherInfomation.get(0).getWeather_Name()) || "된바람".equals(mWeatherInfomation.get(0).getWeather_Name())) {
            // png 파일 ImageView 올리기만 하면됨
            Drawable drawable = getResources().getDrawable(R.drawable.wind);
            imageView.setImageDrawable(drawable);

        } else if ("가벼운 비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "강한 비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "어는 비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "약한 이슬비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "이슬비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "강한 이슬비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "집중 호우".equals(mWeatherInfomation.get(0).getWeather_Name())) {
            // png 파일 ImageView 올리기만 하면됨
            Drawable drawable = getResources().getDrawable(R.drawable.rain);
            imageView.setImageDrawable(drawable);

        } else if ("약한 눈".equals(mWeatherInfomation.get(0).getWeather_Name()) || "눈".equals(mWeatherInfomation.get(0).getWeather_Name()) || "거센 눈".equals(mWeatherInfomation.get(0).getWeather_Name()) || "진눈 깨비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "급 진눈 깨비".equals(mWeatherInfomation.get(0).getWeather_Name()) || "소낙눈".equals(mWeatherInfomation.get(0).getWeather_Name()) || "강한 소낙눈".equals(mWeatherInfomation.get(0).getWeather_Name())) {
            // png 파일 ImageView 올리기만 하면됨
            Drawable drawable = getResources().getDrawable(R.drawable.snow);
            imageView.setImageDrawable(drawable);

        } else if ("안개".equals(mWeatherInfomation.get(0).getWeather_Name()) || "연기".equals(mWeatherInfomation.get(0).getWeather_Name()) || "실안개".equals(mWeatherInfomation.get(0).getWeather_Name())) {
            // png 파일 ImageView 올리기만 하면됨
            Drawable drawable = getResources().getDrawable(R.drawable.fog);
            imageView.setImageDrawable(drawable);

        } else if ("황사 바람".equals(mWeatherInfomation.get(0).getWeather_Name()) || "황사".equals(mWeatherInfomation.get(0).getWeather_Name())) {
            // png 파일 ImageView 올리기만 하면됨
            Drawable drawable = getResources().getDrawable(R.drawable.yellow);
            imageView.setImageDrawable(drawable);

        } else if ("돌풍".equals(mWeatherInfomation.get(0).getWeather_Name()) || "태풍".equals(mWeatherInfomation.get(0).getWeather_Name()) || "극심한 강풍".equals(mWeatherInfomation.get(0).getWeather_Name()) || "폭풍우".equals(mWeatherInfomation.get(0).getWeather_Name()) || "폭풍".equals(mWeatherInfomation.get(0).getWeather_Name()) || "허리케인".equals(mWeatherInfomation.get(0).getWeather_Name())) {
            // png 파일 ImageView 올리기만 하면됨
            Drawable drawable = getResources().getDrawable(R.drawable.storm);
            imageView.setImageDrawable(drawable);

        }


    }

    public void DataToInformation() {
        for (int i = 0; i < mWeatherData.size(); i++) {
            mWeatherInfomation.add(new WeatherInfo(
                    String.valueOf(mWeatherData.get(i).get("weather_Name")), String.valueOf(mWeatherData.get(i).get("weather_Number")), String.valueOf(mWeatherData.get(i).get("weather_Much")),
                    String.valueOf(mWeatherData.get(i).get("weather_Type")), String.valueOf(mWeatherData.get(i).get("wind_Direction")), String.valueOf(mWeatherData.get(i).get("wind_SortNumber")),
                    String.valueOf(mWeatherData.get(i).get("wind_SortCode")), String.valueOf(mWeatherData.get(i).get("wind_Speed")), String.valueOf(mWeatherData.get(i).get("wind_Name")),
                    String.valueOf(mWeatherData.get(i).get("temp_Min")), String.valueOf(mWeatherData.get(i).get("temp_Max")), String.valueOf(mWeatherData.get(i).get("humidity")),
                    String.valueOf(mWeatherData.get(i).get("Clouds_Value")), String.valueOf(mWeatherData.get(i).get("Clouds_Sort")), String.valueOf(mWeatherData.get(i).get("Clouds_Per")), String.valueOf(mWeatherData.get(i).get("day"))
            ));

        }

    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {                // 핸들러 사용을 통한 시간
            super.handleMessage(msg);
            switch (msg.what) {
                case THREAD_HANDLER_SUCCESS_INFO:
                    mForeCast.getmWeather();
                    mWeatherData = mForeCast.getmWeather();

                    if (mWeatherData.size() == 0)
                        tv_WeatherInfo.setText("데이터가 없습니다");

                    DataToInformation(); // 자료 클래스로 저장,

                    String data = "";
                    DataChangedToHangeul();
                    data = data + PrintValue();

                    tv_WeatherInfo.setText(data);
                    WheatherImage();

                    break;
                default:
                    break;
            }
        }
    };
}