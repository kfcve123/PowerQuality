package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.verify.B0Fragment;
import com.cem.powerqualityanalyser.fragment.verify.B1Fragment;
import com.cem.powerqualityanalyser.fragment.verify.B2Fragment;
import com.cem.powerqualityanalyser.fragment.verify.B3Fragment;
import com.cem.powerqualityanalyser.fragment.verify.B4Fragment;
import com.cem.powerqualityanalyser.fragment.verify.B5Fragment;
import com.cem.powerqualityanalyser.fragment.verify.B6Fragment;
import com.cem.powerqualityanalyser.fragment.verify.B7Fragment;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.libs.MeterCommand;
import com.cem.powerqualityanalyser.libs.MeterDebufB0;
import com.cem.powerqualityanalyser.libs.MeterDebufB1;
import com.cem.powerqualityanalyser.libs.MeterDebufB2;
import com.cem.powerqualityanalyser.libs.MeterDebufB3;
import com.cem.powerqualityanalyser.libs.Utils_Byte;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.Commad;
import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.SerialPortHelp;

/**
 * 校准模式
 */
public class VerifyActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    protected FragmentTransaction fragmentTransaction;
    protected FragmentManager fragmentManager;
//    private int[] protocls = new int[]{0xb0,0xb1,0xb2,0xb3,0xb4,0xb5,0xb6,0xb7};
    private int[] protocls = new int[]{0xb0,0xb2,0xb3,0xb4,0xb5};
    private Spinner spinner;
    private Fragment b0Fragment,b2Fragment,b3Fragment,b4Fragment,b5Fragment,b1Fragment,b6Fragment,b7Fragment;
    private TextView verify_content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
//        CEMThreeLineLib.getInstance().setOnMeterDataCallback(this);
//        CEMThreeLineLib.getInstance().writeByte((byte) 0xEE);
        initView();
        initFragment();
        sendProtocl(protocls[0]);
    }
    private void initView(){
        verify_content = findViewById(R.id.verify_content);
        spinner = findViewById(R.id.verify_spinner);
        String[] arr = getResources().getStringArray(R.array.VerifyArr);
        ArrayAdapter adap = new ArrayAdapter<String>(this, R.layout.item_spinselect, arr);
        adap.setDropDownViewResource(R.layout.item_dialogspinselect);
        spinner.setAdapter(adap);
        spinner.setDropDownVerticalOffset(4);

        spinner.setOnItemSelectedListener(this);
        findViewById(R.id.btn_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //本页校正清零，还有个所有页面清0， 0A 00;
                SerialPortHelp.getInstance().sendData(new byte[]{0x0A,0x01});
            }
        });
    }

    private void sendProtocl(int protocl){
 //       CEMThreeLineLib.getInstance().writeByte((byte) protocl);
        Toast.makeText(this, Utils_Byte.bytesToHexString((byte) protocl),Toast.LENGTH_SHORT).show();
        SerialPortHelp.getInstance().sendData(new byte[]{(byte) 0xEE, (byte) protocl});
    }

    private void  initFragment(){
        selectFragment(0);
    }

    private void selectFragment(int position){
        fragmentManager = this.getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        switch (position){
            case 0:
                if (b0Fragment == null){
                    b0Fragment = new B0Fragment();
                }
                verify_content.setText("电压调整");
                fragmentTransaction.replace(R.id.fragment_layout,b0Fragment);
                fragmentTransaction.commit();
                break;
           /* case 1:
                if (b1Fragment == null){
                    b1Fragment = new B1Fragment();
                }
                fragmentTransaction.replace(R.id.fragment_layout,b1Fragment);
                fragmentTransaction.commit();
                break;*/
            case 1:
                if (b2Fragment == null){
                    b2Fragment = new B2Fragment();
                }
                verify_content.setText("50Hz 3000A电流调整");
                fragmentTransaction.replace(R.id.fragment_layout,b2Fragment);
                fragmentTransaction.commit();
                break;
            case 2:
                if (b3Fragment == null){
                    b3Fragment = new B3Fragment();
                }
                verify_content.setText("50Hz 50A电流调整");
                fragmentTransaction.replace(R.id.fragment_layout,b3Fragment);
                fragmentTransaction.commit();
                break;
            case 3:
                if (b4Fragment == null){
                    b4Fragment = new B4Fragment();
                }
                verify_content.setText("60Hz 3000A电流调整");
                fragmentTransaction.replace(R.id.fragment_layout,b4Fragment);
                fragmentTransaction.commit();
                break;
            case 4:
                if (b5Fragment == null){
                    b5Fragment = new B5Fragment();
                }
                verify_content.setText("60Hz 50A电流调整");
                fragmentTransaction.replace(R.id.fragment_layout,b5Fragment);
                fragmentTransaction.commit();
                break;
            /*case 6:
                if (b6Fragment == null){
                    b6Fragment = new B6Fragment();
                }
                fragmentTransaction.replace(R.id.fragment_layout,b6Fragment);
                fragmentTransaction.commit();
                break;
            case 7:
                if (b7Fragment == null){
                    b7Fragment = new B7Fragment();
                }
                fragmentTransaction.replace(R.id.fragment_layout,b7Fragment);
                fragmentTransaction.commit();
                break;*/
        }
    }


    @Override
    public byte[] getMode() {
        return null;
    }

    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,""));
        data.add(new BaseBottomAdapterObj(1,""));
        data.add(new BaseBottomAdapterObj(2,""));
        data.add(new BaseBottomAdapterObj(3,""));
        data.add(new BaseBottomAdapterObj(4,""));
        return data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {

    }

    /*@Override
    public void onDataChange(final MeterCommand meterCommand, final BaseMeterData baseMeterData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (b0Fragment != null && b0Fragment.isAdded() &&meterCommand.equals(MeterCommand.Debug_B0)){
                    if (b0Fragment.isAdded()){
                        ((B0Fragment) b0Fragment).setData2View((MeterDebufB0) baseMeterData);
                    }
                }else if (b1Fragment != null && b1Fragment.isAdded() && meterCommand.equals(MeterCommand.Debug_B1)){
                    ((B1Fragment) b1Fragment).setData2View((MeterDebufB1) baseMeterData);
                }else if (b2Fragment != null && b2Fragment.isAdded() && meterCommand.equals(MeterCommand.Debug_B2)){
                    ((B2Fragment) b2Fragment).setData2View((MeterDebufB2) baseMeterData);
                }else if (b3Fragment != null && b3Fragment.isAdded() && meterCommand.equals(MeterCommand.Debug_B3)){
                    ((B3Fragment) b3Fragment).setData2View((MeterDebufB3) baseMeterData);
                }else if (b4Fragment != null && b4Fragment.isAdded() && meterCommand.equals(MeterCommand.Debug_B4)){
                    ((B4Fragment) b4Fragment).setData2View((MeterDebufB2) baseMeterData);
                }else if (b5Fragment != null && b5Fragment.isAdded() && meterCommand.equals(MeterCommand.Debug_B5)){
                    ((B5Fragment) b5Fragment).setData2View((MeterDebufB3) baseMeterData);
                }else if (b6Fragment != null && b6Fragment.isAdded() && meterCommand.equals(MeterCommand.Debug_B6)){
                    ((B6Fragment) b6Fragment).setData2View((MeterDebufB2) baseMeterData);
                }else if (b7Fragment != null && b7Fragment.isAdded() && meterCommand.equals(MeterCommand.Debug_B7)){
                    ((B7Fragment) b7Fragment).setData2View((MeterDebufB3) baseMeterData);
                }

            }
        });
    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectFragment(position);
        sendProtocl(protocls[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDataReceived(byte[] bytes) {
        log.e("------------"+Utils_Byte.bytesToHexString(bytes));
    }

    @Override
    public void onDataReceivedModel(ModelAllData list) {

    }

    @Override
    public void onDataReceivedList(final List list) {
        log.e("----------" + list.size());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (b0Fragment != null && b0Fragment.isAdded()){
                    if (b0Fragment.isAdded()){
                        ((B0Fragment) b0Fragment).setData2View(list);
                    }
//                }else if (b1Fragment != null && b1Fragment.isAdded()){
//                    ((B1Fragment) b1Fragment).setData2View(list);
                }else if (b2Fragment != null && b2Fragment.isAdded()){
                    ((B2Fragment) b2Fragment).setData2View(list);
                }else if (b3Fragment != null && b3Fragment.isAdded()){
                    ((B3Fragment) b3Fragment).setData2View(list);
                }else if (b4Fragment != null && b4Fragment.isAdded()){
                    ((B4Fragment) b4Fragment).setData2View(list);
                }else if (b5Fragment != null && b5Fragment.isAdded()){
                    ((B5Fragment) b5Fragment).setData2View(list);
//                }else if (b6Fragment != null && b6Fragment.isAdded()){
//                    ((B6Fragment) b6Fragment).setData2View(list);
//                }else if (b7Fragment != null && b7Fragment.isAdded()){
//                    ((B7Fragment) b7Fragment).setData2View(list);
                }

            }
        });


    }
}
