package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.FileListFragment;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import serialport.amos.cem.com.libamosserial.ModelAllData;

public class CsvFileActivity extends BaseActivity implements View.OnClickListener {


    protected FragmentTransaction fragmentTransaction;
    protected FragmentManager fragmentManager;
    private FileListFragment fileListFragment;
    private SweetAlertDialog sweetAlertDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setViewShow(0);
    }

    @Override
    public byte[] getMode() {
        return null;
    }

    protected void setViewShow(int index) {
        switch (index){
            case 0:
                if (fileListFragment == null)
                    fileListFragment = new FileListFragment();
                showFragment(fileListFragment,index+"");
                fileListFragment.setShareType(1);
                break;

        }
    }
    protected void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
    }



    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    /**
     * 初始化底部按钮数据
     *
     * @return
     */
    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.Open)));
        data.add(new BaseBottomAdapterObj(1,Res2String(R.string.SaveCSV)));
        data.add(new BaseBottomAdapterObj(2,Res2String(R.string.Send),Res2Stringarr(R.array.share_type)));
        data.add(new BaseBottomAdapterObj(3,Res2String(R.string.SelectAll)));
        data.add(new BaseBottomAdapterObj(4,Res2String(R.string.Delete)));
        return data;
    }

    /**
     * 弹窗菜单点击事件
     *
     * @param obj
     * @param positio
     */
    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {
        fileListFragment.shareFile(positio);
    }

    private void deleteFiles() {
        /*if (hintDialog == null)
            hintDialog = new HintDialog(this,R.style.CustomHintDialog);
        hintDialog.showDialog("", "Do you want delete this file？", Res2String(R.string.Sure), Res2String(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fileListFragment.deletefile();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/


        sweetAlertDialog  = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText(Res2String(R.string.alert_title))
                .setContentText(Res2String(R.string.alert_content))
                .setCancelText(Res2String(R.string.alert_cancel))
                .setConfirmText(Res2String(R.string.alert_delete))
                .showCancelButton(true)
                .setConfirmButton(Res2String(R.string.alert_delete), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        fileListFragment.deletefile();
                        sweetAlertDialog.dismiss();
                    }
                });
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.cancel();
            }
        });
        if(fileListFragment!=null && fileListFragment.getSelectCount()>0) {
            sweetAlertDialog.show();
        }else{
            Toast.makeText(this,R.string.alert_select_file,Toast.LENGTH_SHORT).show();
        }
    }

    private void selectAll() {
        fileListFragment.selectAll();
    }


    /**
     * 底部按钮点击事件
     *
     * @param view
     * @param obj
     */
    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        setViewShow(obj.getId());
        switch (obj.getId()){
            case 0:
                fileListFragment.OpenFile();
                break;
            case 1:
                fileListFragment.newOutCsv();
                break;
            case 2:

                break;
            case 3:
                selectAll();
                break;
            case 4:
                deleteFiles();
                break;
        }
    }



    @Override
    public void onClick(View v) {

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
}
