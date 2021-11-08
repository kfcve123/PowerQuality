package com.cem.powerqualityanalyser.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.databean.FileImageManager;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class AllBaseActivity extends FragmentActivity {

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        log.e("按键值："+keyCode);
        globalKeDown(keyCode);
        return super.onKeyDown(keyCode, event);

    }
    private void  globalKeDown(int keyCode){
        MeterKeyValue key=MeterKeyValue.valueOf(keyCode);
        String acitvityName=this.getClass().getSimpleName();
  //      log.e("----------Power----------" + key.name());
        switch (key){
            case Scope:
                if (!acitvityName.contains(ScopeActivity.class.getSimpleName())) {
                    Intent intent = new Intent(this, ScopeActivity.class);
                    intent.putExtra(AppConfig.MainPutActivityNameKey,"Scope");
                    startActivity(intent);
                }

                break;
            case Setup:
                if (!acitvityName.contains(SetupActivityTest.class.getSimpleName())) {
                    Intent intent = new Intent(this, SetupActivity.class);
                    intent.putExtra(AppConfig.MainPutActivityNameKey,"Setting");
                    startActivity(intent);
                }

                break;
            case Memory:
                /*if (!acitvityName.contains(HistoryRecordActivity.class.getSimpleName())) {
                    Intent intent = new Intent(this, HistoryRecordActivity.class);
                    intent.putExtra(AppConfig.MainPutActivityNameKey,"History");
                    startActivity(intent);
                }*/

                break;
            case Logger:
                if (!acitvityName.contains(TrendChartRecordActivity.class.getSimpleName())) {
                    Intent intent = new Intent(this, TrendChartRecordActivity.class);
                    intent.putExtra(AppConfig.MainPutActivityNameKey,"Logger");
                    startActivity(intent);
                }
                break;
            case Light:
                setWindowBrightness();
                break;
            case Menu:
                if (!(this instanceof MainActivity)) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                break;

            case Save:
                screenAndSave();
                break;
            case Power:


                break;
        }
    }

    private void screenAndSave(){
        Bitmap bitmap =screenShots();
        if (bitmap != null) {
            try {
                String SAVE_Screenshot_PATH =getExternalCacheDir().getPath() + "/" + FileImageManager.IMAGE_CHILD_PATH;

                createDir(SAVE_Screenshot_PATH);

                File fileDir =new File(SAVE_Screenshot_PATH,new Date().getTime()+".png");
                Log.e("地址:",fileDir.getAbsolutePath());
                Log.e("是否可写入：", String.valueOf(fileDir.canWrite()));
                FileOutputStream os = new FileOutputStream(fileDir);

                Log.e("是否写入成功：", String.valueOf(bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)));
                os.flush();
                os.close();
                Toast.makeText(this, "地址："+fileDir.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("错误：",e.getMessage().toString()+"\r\n"+e.getStackTrace().toString());
                Toast.makeText(this, "截图失败", Toast.LENGTH_SHORT).show();
            }}
    }

    private Bitmap screenShots() {
//获取当前屏幕的大小
        int width = getScreenWidth();
        int height = getScreenHeight(this);
//找到当前页面的跟布局
        View view = this.getWindow().getDecorView();
//设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
//从缓存中获取当前屏幕的图片
        Bitmap temBitmap = view.getDrawingCache();

//生成相同大小的图片
        Bitmap bitmap = Bitmap.createBitmap(temBitmap, 0, 0, width, height);
//        view.destroyDrawingCache();//不清空，第二次截屏则使用的是缓存的同一张。
        return bitmap;
    }

    public void createDir (String dirPath) {

        File dir = new File(dirPath);
        //文件夹是否已经存在
        if (dir.exists()) {
            Log.w(TAG,"The directory [ " + dirPath + " ] has already exists");
            return ;
        }
        if (!dirPath.endsWith(File.separator)) {//不是以 路径分隔符 "/" 结束，则添加路径分隔符 "/"
            dirPath = dirPath + File.separator;
        }
        if (dir.mkdirs()) {
            Log.d(TAG,"create directory [ "+ dirPath + " ] success");
//            return FLAG_SUCCESS;
        }

        Log.e(TAG,"create directory [ "+ dirPath + " ] failed");
    }

    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 设置亮度
     * @param
     * @param
     */
    private void setWindowBrightness() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness += 0.25;
        if (lp.screenBrightness > 1){
            lp.screenBrightness = 0;
        }
        log.e("--------------" + lp.screenBrightness);
        AppConfig.getInstance().setDefault_brightness((int) (255 * lp.screenBrightness));
        window.setAttributes(lp);
    }



}
