package com.cem.powerqualityanalyser.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.tool.log;


public class BatteryView extends LinearLayout {
    private ImageView imgBatteryLevel;
    private TextView battery_percentvalue;

    public BatteryView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.battery_view,this);
        //显示电量等级
        imgBatteryLevel = (ImageView) linearLayout.findViewById(R.id.battery_level);
        //显示当前的电池电量
        battery_percentvalue=(TextView) linearLayout.findViewById(R.id.battery_percentvalue);
    }

	/*private Handler mHandler=new Handler(){
		@Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
	};*/

    @Override
    protected void onAttachedToWindow() {
        // TODO Auto-generated method stub
        super.onAttachedToWindow();
        IntentFilter mFilter=new IntentFilter();
 //       mFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        mFilter.addAction("BATTERY_CHANGED");
        getContext().registerReceiver(mIntentReceiver, mFilter);
    }

    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(mIntentReceiver);
    }

    private int status = 0;
    private int health = 0;
    private boolean present = false;
    private int level = 0;
    private int scale = 0;
    private int icon_small = 0;
    private int plugged = 0;
    private int voltage = 0;
    private int temperature = 0;
    private String technology = "";
    private String statusString = "";
    private int powerleverl = 0;//电池百分比
    private float powerstatus = 0f;//保留2位小数的电池百分比
    private boolean powerDc; //是否在充电
    private float voltageleverl;//电压

    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // ///////////////

            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                //lockScrrenTimes();
                AppConfig.getInstance().setLockScreen(false);
                // Log.d("", 3333+"screen on");
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                AppConfig.getInstance().setLockScreen( true);
                // Log.d("", 3333+"screen off");
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                AppConfig.getInstance().setLockScreen(true);
                // Log.d("", 3333+"screen unlock");
            } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent
                    .getAction())) {
                // Log.i("",
                // 3333+" receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS");
            }

            // ////////////
            // if(true)return;cat
            if (intent.hasExtra("status"))
                status = intent.getIntExtra("status", 0);
            if (intent.hasExtra("health"))
                health = intent.getIntExtra("health", 0);
            if (intent.hasExtra("present"))
                present = intent.getBooleanExtra("present", false);
            if (intent.hasExtra("level"))
                level = intent.getIntExtra("level", 0);
            if (intent.hasExtra("scale"))
                scale = intent.getIntExtra("scale", 0);
            if (intent.hasExtra("icon-small"))
                icon_small = intent.getIntExtra("icon-small", 0);
            if (intent.hasExtra("plugged"))
                plugged = intent.getIntExtra("plugged", 0);
            if (intent.hasExtra("voltage"))
                voltage = intent.getIntExtra("voltage", 0);
            if (intent.hasExtra("temperature"))
                temperature = intent.getIntExtra("temperature", 0);
            if (intent.hasExtra("technology"))
                technology = intent.getStringExtra("technology");
            statusString = "";
            if(String.valueOf(level)!=null && level<=100){
                battery_percentvalue.setText(level+"%"+"");
            }

            if ("BATTERY_CHANGED".equals(action)) {
                if(intent.hasExtra("voltage"))
                    voltageleverl = intent.getFloatExtra("voltage",0f);
                if (intent.hasExtra("powerlevel"))
                    powerleverl = intent.getIntExtra("powerlevel", 0);
                if(intent.hasExtra("powerdc"))
                    powerDc = intent.getBooleanExtra("powerdc",false);

                if(intent.hasExtra("powerstatus")) {
                    powerstatus = intent.getFloatExtra("powerstatus", 0f);
//                    Toast.makeText(context,powerstatus + "",Toast.LENGTH_SHORT).show();
//                    log.e("------电压------" + powerstatus);
//                    log.e("------电量------" + DataFormatUtil.formatValue(powerleverl,2));
//                    log.e("------状态------" + powerDc);
                    if(powerDc){
                        statusString = "charging"; // 充电
                        // Toast.makeText(getApplicationContext(), "充电!",
                        // Toast.LENGTH_SHORT).show();
                        imgBatteryLevel.setImageResource(R.mipmap.battery_in);
                        imgBatteryLevel.getDrawable().setLevel(powerleverl);
                    }else{
                        int rid1 = R.mipmap.top_battery1;
                        if (powerleverl >= 40 && powerleverl <= 70) {
                            rid1 = R.mipmap.top_battery2;
                        } else if (powerleverl > 10 && powerleverl <= 40) {
                            rid1 = R.mipmap.top_battery3;
                        } else if (powerleverl < 10) {
                            rid1 = R.mipmap.top_battery4;
                        }
                        statusString = "not charging";
                        imgBatteryLevel.setImageResource(rid1);
                        imgBatteryLevel.getDrawable().setLevel(powerleverl);
                    }
                }



                if(String.valueOf(powerleverl)!=null && powerleverl<=100){
                    battery_percentvalue.setText(powerleverl+"%"+"");
                }

                if(powerleverl == 100) {
                    statusString = "full";
                    imgBatteryLevel.setImageResource(R.mipmap.battery_full);
                    imgBatteryLevel.getDrawable().setLevel(powerleverl);
                }
            }

            //////////////////////////////////////////////
            /*switch (status) {
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    statusString = "unknown";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    statusString = "charging"; // 充电
                    // Toast.makeText(getApplicationContext(), "充电!",
                    // Toast.LENGTH_SHORT).show();
                    imgBatteryLevel.setImageResource(R.mipmap.battery_in);
                    imgBatteryLevel.getDrawable().setLevel(level);
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    statusString = "discharging"; // Toast.makeText(getApplicationContext(),
                    // "放电!",
                    // Toast.LENGTH_SHORT).show();
//                    int rid1 = R.mipmap.battery_full;
//                    if (level >= 70 && level <= 90) {
//                        rid1 = R.mipmap.battery_4;
//                    } else if (level > 40 && level <= 60) {
//                        rid1 = R.mipmap.battery_3;
//                    } else if (level >= 20 && level <= 40) {
//                        rid1 = R.mipmap.battery_2;
//                    } else if (level >= 10 && level <= 20) {
//                        rid1 = R.mipmap.battery_1;
//                    } else if (level < 10) {
//                        rid1 = R.mipmap.battery_none;
//                    }

                    int rid1 = R.mipmap.top_battery1;
                    if (level >= 40 && level <= 70) {
                        rid1 = R.mipmap.top_battery2;
                    } else if (level > 10 && level <= 40) {
                        rid1 = R.mipmap.top_battery3;
                    } else if (level < 10) {
                        rid1 = R.mipmap.top_battery4;
                    }


                    statusString = "not charging"; // Toast.makeText(getApplicationContext(),
                    // "不充电!"+level+" 电量最大值"+scale+" 电量百分比",
                    // Toast.LENGTH_SHORT).show();

                    imgBatteryLevel.setImageResource(rid1);
                    imgBatteryLevel.getDrawable().setLevel(level);
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    int rid = R.mipmap.top_battery1;
                    if (level >= 40 && level <= 70) {
                        rid = R.mipmap.top_battery2;
                    } else if (level > 10 && level <= 40) {
                        rid = R.mipmap.top_battery3;
                    } else if (level < 10) {
                        rid = R.mipmap.top_battery4;
                    }
                    statusString = "not charging"; // Toast.makeText(getApplicationContext(),
                    // "不充电!"+level+" 电量最大值"+scale+" 电量百分比",
                    // Toast.LENGTH_SHORT).show();

                    imgBatteryLevel.setImageResource(rid);
                    imgBatteryLevel.getDrawable().setLevel(level);
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    if(powerleverl == 100) {
                        statusString = "full";
                        imgBatteryLevel.setImageResource(R.mipmap.battery_full);
                        imgBatteryLevel.getDrawable().setLevel(level);
                    }
                    break;
            }*/
            String healthString = "";
            switch (health) {
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    healthString = "unknown";
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    healthString = "good";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    healthString = "overheat";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    healthString = "dead";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    healthString = "voltage";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    healthString = "unspecified failure";
                    break;
            }
            String acString = "";
            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    acString = "plugged ac";
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    acString = "plugged usb";
                    break;
            }
        }
    };
}
