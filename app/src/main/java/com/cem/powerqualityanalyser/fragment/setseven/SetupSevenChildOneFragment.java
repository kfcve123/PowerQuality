package com.cem.powerqualityanalyser.fragment.setseven;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragment;
import com.cem.powerqualityanalyser.tool.log;

import java.lang.reflect.Field;


/**
 * 亮度
 */
public class SetupSevenChildOneFragment extends BaseFragment implements View.OnClickListener {


    private AppConfig config;

    private SeekBar bright_seekbar;


    @Override
    public int setContentView() {
        return R.layout.fragment_set_seven_childone;
    }

    @Override
    public void onInitView() {
        config = AppConfig.getInstance();
        bright_seekbar = (SeekBar) findViewById(R.id.bright_seekbar);
        bright_seekbar.setMax(255);
        bright_seekbar.setProgress(config.getDefault_brightness());

        bright_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {//用户拖拽
                    setWindowBrightness(getActivity(), progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    /**
     * 设置亮度
     * @param activity
     * @param brightness
     */
    private void setWindowBrightness(Activity activity, int brightness) {
        config.setDefault_brightness(brightness);
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);
    }

    public void setLightProgress(int lightProgress){
        if(bright_seekbar!=null) {
            bright_seekbar.setProgress(lightProgress, true);
        }
    }

    @Override
    public void onClick(View view) {

    }





}
