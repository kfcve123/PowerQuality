package com.cem.powerqualityanalyser.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cem.powerqualityanalyser.R;

import java.util.List;

import serialport.amos.cem.com.libamosserial.Commad;
import serialport.amos.cem.com.libamosserial.DataModel;
import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;
import serialport.amos.cem.com.libamosserial.OnSerialPortDataListener;
import serialport.amos.cem.com.libamosserial.SerialPortHelp;

public class MainTestActivity extends AppCompatActivity {
    private boolean isOpen=false;
    private Button btn_send,btn_open;
    private SerialPortHelp serialHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
        serialHelper=new SerialPortHelp();
        serialHelper.setOnSerialPortDataListener(new OnSerialPortDataListener() {
            @Override
            public void onBATDataReceived(float v, float v1,boolean dcIn) {

            }

            @Override
            public void onDataReceived(byte[] bytes) {
                //Log.e("上层收到的数据为：","长度为："+bytes.length);
            }
            @Override
            public void onDataReceivedModel(ModelAllData list) {
                //Log.e("上层收到的对象为：","长度为："+list.size());
                ModelAllData modelAllData=list;
                Log.e("上层收到的数据HZ的数据为：",modelAllData.getHzData());
                List<ModelLineData> list2=modelAllData.getModelLineData();

                Log.e("上层收到对象中list长度为：",list2.size()+"");
                for ( int n=0;n<list2.size();n++)
                {
                    ModelLineData modelLineData=(ModelLineData)list2.get(n);
                    Log.e("上层收到的数据为",  modelLineData.getLineNumber() +"行"+ "这一行测量的是-------:"+ modelLineData.getLineName());
                }
            }

            @Override
            public void onDataReceivedList(List list) {

            }
        });
        initUIView();
    }

    private void initUIView(){
        btn_send=findViewById(R.id.btn_send);
        btn_open=findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("---------打开","打开串口iiiiiiii");
                isOpen= serialHelper.openSerialPort("/dev/ttyS1",115200);
                if(isOpen)
                {
                    Toast.makeText(MainTestActivity.this,"打开成功",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(MainTestActivity.this,"打开失败",Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen)
                {
                    boolean bb=serialHelper.sendData(Commad.E2_1_NEUTRAL_E_S);
                    if(bb)
                    {
                        Toast.makeText(MainTestActivity.this,"发送数据成功",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
