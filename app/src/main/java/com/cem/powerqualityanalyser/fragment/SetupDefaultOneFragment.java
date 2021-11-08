package com.cem.powerqualityanalyser.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SetupDefaultOneFragment extends BaseFragment{


    private AppConfig config;
    private Integer[] resours;
    private ImageView check_button1;
    private boolean check1;

    @Override
    public int setContentView() {
        return R.layout.fragment_set_default_first;
    }

    @Override
    public void onInitView() {
        config = AppConfig.getInstance();
        resours = new Integer[]{R.mipmap.conect1,R.mipmap.conect2,R.mipmap.conect3,R.mipmap.conect4};
        check_button1 = (ImageView) findViewById(R.id.check_button1);
        check_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check1) {
                    check_button1.setImageResource(R.mipmap.set_uncheck);
                    check1 = false;
                }else {
                    check1 = true;
                    check_button1.setImageResource(R.mipmap.set_check);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1; //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }


    //在主线程里面处理消息并更新UI界面
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");


                    break;
                default:
                    break;

            }
        }
    };




}
