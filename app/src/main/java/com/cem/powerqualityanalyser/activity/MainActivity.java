package com.cem.powerqualityanalyser.activity;

import android.arch.lifecycle.Observer;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.adapter.MenuAdapter;
import com.cem.powerqualityanalyser.tool.LanguageStore;
import com.cem.powerqualityanalyser.view.PagingItemDecoration;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.Locale;


public class MainActivity extends AllBaseActivity {

    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private PagingItemDecoration pagingItemDecoration = null;

    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuAdapter = new MenuAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.menu_list);
        layoutManager = new GridLayoutManager(this, 5);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pagingItemDecoration = new PagingItemDecoration(this, layoutManager);
        init();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(menuAdapter);
        getLanguageEvent();
        //移除item之间的间隔线条
//        recyclerView.removeItemDecoration(pagingItemDecoration);
//        recyclerView.addItemDecoration(pagingItemDecoration);

        //      PermissionsHelper.with(this).requestCode(200).addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).addPermissionCallback(this).request();
    }


    private void init() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuAdapter.clearData();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        changeAppBrightness(AppConfig.getInstance().getDefault_brightness());
    }


    // 根据亮度值修改当前window亮度
    private void changeAppBrightness( int brightness) {
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }

    private void getLanguageEvent(){
        LiveEventBus
                .get("changeLanguage", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@android.support.annotation.Nullable String s) {
                        changeAppLanguage();
                        recreate();//刷新界面
                    }
                });
    }

    public void changeAppLanguage() {
        String sta = LanguageStore.getLanguageLocal(this);
        if(sta != null && !"".equals(sta)){
            // 本地语言设置
            Locale myLocale = getLocale(sta);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }

    }

    private Locale getLocale(String sta){
        Locale locale ;
        if(sta.equals("zh_rCN")){
            locale =  Locale.SIMPLIFIED_CHINESE;
        }else if(sta.equals("en")){
            locale = Locale.ENGLISH;
        }else{
            locale = Locale.ENGLISH;
        }
        return locale;
    }


}
