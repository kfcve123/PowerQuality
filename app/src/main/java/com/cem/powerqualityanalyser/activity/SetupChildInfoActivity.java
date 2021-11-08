package com.cem.powerqualityanalyser.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.BuildConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildFiveFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildFourFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildOneFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildThreeFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildTwoFragment;
import com.cem.powerqualityanalyser.tool.ButtonListenerTool;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

/**
 * 本机信息 10
 */
public class SetupChildInfoActivity extends BaseActivity implements View.OnClickListener {


    private TextView righttext1,righttext2,righttext3,righttext4,righttext5,righttext6;

    private String apkPath;
    //   public static File filedir = Environment.getExternalStorageDirectory();
    private static String sdcardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String apkSavePath = sdcardRoot+"/1.apk";
    private int INSTALL_PACKAGES_REQUESTCODE = 1;
    private int GET_UNKNOWN_APP_SOURCES = 2;
    public static final int REQUEST_STATUS_GROUP_CODE = 0x003;
    public static String[] PERMISSIONS_GROUP_SORT = {
            // Manifest.permission.ACCESS_COARSE_LOCATION,
            //           Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.CALL_PHONE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_ten);
        righttext1 = findViewById(R.id.righttext1);
        righttext2 = findViewById(R.id.righttext2);
        righttext3 = findViewById(R.id.righttext3);
        righttext4 = findViewById(R.id.righttext4);
        righttext5 = findViewById(R.id.righttext5);
        righttext6 = findViewById(R.id.righttext6);
        apkPath = sdcardRoot + getString(R.string.apkpath);
        readSD();
    }

    private void UpdataApp(){
        if(fileIsExists(apkPath)){
            //          installApk();
            //          installApk(getActivity(),filedir,apkPath);
            checkAndroidOAndInstall(this,apkPath);
        }else{
            Toast.makeText(this,R.string.new_version,Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 判断是否是8.0,8.0需要处理未知应用来源权限问题,否则直接安装
     */
    private void checkAndroidOAndInstall(Context context, String apkPath){
        if(isPermissionsAllGranted(PERMISSIONS_GROUP_SORT,REQUEST_STATUS_GROUP_CODE)) {
            if (Build.VERSION.SDK_INT >= 26) {
                //判断是否可以安装未知来源的应用
                boolean b = context.getPackageManager().canRequestPackageInstalls();
                if (b) {
                    installApk(context, apkPath);
                } else {
                    //先调用一下方法，否则不弹出权限提示框
                    installApk(context, apkPath);
                    //请求安装未知应用来源的权限
                    requestPermissions(new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_PACKAGES_REQUESTCODE);
                }
            } else {
                installApk(context, apkPath);
            }
        }
    }

    private boolean isPermissionsAllGranted(String[] permArray, int questCode){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }
        //获得批量请求但被禁止的权限列表
        List<String> deniedPerms = new ArrayList<String>();
        for(int i=0;permArray!=null&&i<permArray.length;i++){
            if(PackageManager.PERMISSION_GRANTED != checkSelfPermission(permArray[i])){
                deniedPerms.add(permArray[i]);
            }
        }
        //进行批量请求
        int denyPermNum = deniedPerms.size();
        if(denyPermNum != 0){
            requestPermissions(deniedPerms.toArray(new String[denyPermNum]),questCode);
            return false;
        }
        return true;
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    installApk(this,apkPath);
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);
                }
                break;
            case REQUEST_STATUS_GROUP_CODE:
                if (grantResults.length > 0 && grantResults[0]!= PackageManager.PERMISSION_GRANTED) {  //存取
                    popAlterDialog(getResources().getString(R.string.permission_storge),
                            getResources().getString(R.string.permission_storge_content));
                }else{
                    installApk(this,apkPath);
                }

                break;
        }
    }

    /**
     * 安装apk
     *
     * @param context
     * @param apkPath
     */
    /**
     * provider
     * 处理android 7.0 及以上系统安装异常问题
     */
    private void installApk(Context context,String apkPath) {

        try {
            File file = new File(apkPath);
            Intent install = new Intent();
            install.setAction(Intent.ACTION_VIEW);
            install.addCategory(Intent.CATEGORY_DEFAULT);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
                log.e("apkUri=" + apkUri);
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                install.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                install.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }

            context.startActivity(install);
        } catch (Exception e) {
            log.e(e.toString());
            Toast.makeText(context, "文件解析失败", Toast.LENGTH_SHORT).show();
            //         deleteFile(apkPath);
        }
    }


    protected void popAlterDialog(final String msgFlg, String msgInfo) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.warn)
                .setMessage(msgInfo)
                .setNegativeButton(R.string.info_cancel, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.setting,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //前往应用详情界面
                        try {
                            Uri packUri = Uri.parse("package:"+ getPackageName());
                            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packUri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } catch (Exception e) {
                        }
                        dialog.dismiss();
                    }
                }).create().show();
    }


    private boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public byte[] getMode() {
        return null;
    }

    private void readSD(){
        File path= Environment.getExternalStorageDirectory();
        StatFs stat=new StatFs(path.getPath());
        long blockSize=stat.getBlockSize();
        long totalBlacks=stat.getBlockCount();
        long availableBlocks=stat.getAvailableBlocks();

        long totalSize=blockSize*totalBlacks;
        long availSize=blockSize*availableBlocks;

        String free = Math.round(availableBlocks  * 100 / totalBlacks ) + "%";
 //       righttext6.setText(free);

        String totalStr= Formatter.formatFileSize(this, totalSize);
        righttext5.setText(totalStr);

        String availStr=Formatter.formatFileSize(this, availSize);
        righttext6.setText(availStr);

    }



    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();

        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.set_info_button_menu)));
        data.add(new BaseBottomAdapterObj(1,Res2String(R.string.update_apk)));
        data.add(new BaseBottomAdapterObj(2,""));
        data.add(new BaseBottomAdapterObj(3,""));
        data.add(new BaseBottomAdapterObj(4,Res2String(R.string.set_info_button_ok)));
        return  data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()){
            case 1:

                UpdataApp();
                break;
            case 4:
                toVerifyActivity();
                break;
        }

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDataReceived(byte[] bytes) {

    }

    @Override
    public void onDataReceivedModel(ModelAllData list) {

    }

    @Override
    public void onDataReceivedList(List list) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (ButtonListenerTool.getTool().onClick(MeterKeyValue.valueOf(keyCode))){
            Toast.makeText(this,"进入调试模式",Toast.LENGTH_SHORT).show();
            toVerifyActivity();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void toVerifyActivity(){
        Intent intent = new Intent(this,VerifyActivity.class);
        intent.putExtra(AppConfig.MainPutActivityNameKey, "Verify");
        startActivity(intent);
    }


}