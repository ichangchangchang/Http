package com.ztian.htttp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
//22222222222222222222222222222222222222222222222222222
public class MainActivity extends AppCompatActivity {
     TextView tv;
    Button btn;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            String result=(String) msg.getData().get("result");
            tv.setText(result);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.tv);
        btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this,new  String[]{Manifest.permission.INTERNET},1);

                new Thread(){
                public void run(){
                        getData();
                    }
                }.start();

            }
        });

    }
    public void getData(){
        try {

            String path = "http://101.201.239.28:9080/grid/points/getSmilePoints";
            tv.setText("2");

            tv.setText("3");
            URL url=new URL(path);
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                int responseCode = conn.getResponseCode();
                if(responseCode == 200){
                    InputStream is = conn.getInputStream();
                    BufferedReader bis=new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line=bis.readLine())!=null){
                        if(line!=null){
                            //tv.setText("你好");
                            System.out.println(line);
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("result", line);
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    }

                }else tv.setText("我不好");

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
