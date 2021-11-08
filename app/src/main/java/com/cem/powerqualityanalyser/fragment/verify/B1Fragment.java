package com.cem.powerqualityanalyser.fragment.verify;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.libs.MeterDebufB1;
import com.cem.powerqualityanalyser.libs.Utils_Byte;

import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelBaseData;
import serialport.amos.cem.com.libamosserial.SerialPortHelp;


public class B1Fragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextView tv_phaseA_v,tv_phaseB_v,tv_phaseC_v,
            tv_phaseA_peakV,tv_phaseB_peakV,tv_phaseC_peakV,
            tv_phaseA_a,tv_phaseB_a,tv_phaseC_a;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_b1,null);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        tv_phaseA_v = rootView.findViewById(R.id.tv_phaseA_v);
        tv_phaseB_v = rootView.findViewById(R.id.tv_phaseB_v);
        tv_phaseC_v = rootView.findViewById(R.id.tv_phaseC_v);
        tv_phaseA_peakV = rootView.findViewById(R.id.tv_phaseA_peakV);
        tv_phaseB_peakV = rootView.findViewById(R.id.tv_phaseB_peakV);
        tv_phaseC_peakV = rootView.findViewById(R.id.tv_phaseC_peakV);
        tv_phaseA_a = rootView.findViewById(R.id.tv_phaseA_a);
        tv_phaseB_a = rootView.findViewById(R.id.tv_phaseB_a);
        tv_phaseC_a = rootView.findViewById(R.id.tv_phaseC_a);

        rootView.findViewById(R.id.v_phaseA_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseA_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseA_base_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseA_base_sub).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseA_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseA_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseA_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseA_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseA_base_add).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseA_base_sub).setOnClickListener(this);

        rootView.findViewById(R.id.v_phaseB_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseB_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseB_base_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseB_base_sub).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseB_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseB_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseB_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseB_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseB_base_add).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseB_base_sub).setOnClickListener(this);

        rootView.findViewById(R.id.v_phaseC_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseC_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseC_base_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseC_base_sub).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseC_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseC_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseC_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseC_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseC_base_add).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseC_base_sub).setOnClickListener(this);


    }

    public void setData2View(MeterDebufB1 meterB1){
        tv_phaseA_v.setText(meterB1.getV_Value().getPhaseA().getShowValue());
        tv_phaseB_v.setText(meterB1.getV_Value().getPhaseB().getShowValue());
        tv_phaseC_v.setText(meterB1.getV_Value().getPhaseC().getShowValue());

        tv_phaseA_peakV.setText(meterB1.getV_Max().getPhaseA().getShowValue());
        tv_phaseB_peakV.setText(meterB1.getV_Max().getPhaseB().getShowValue());
        tv_phaseC_peakV.setText(meterB1.getV_Max().getPhaseC().getShowValue());

        tv_phaseA_a.setText(meterB1.getA_Value().getPhaseA().getShowValue());
        tv_phaseB_a.setText(meterB1.getA_Value().getPhaseB().getShowValue());
        tv_phaseC_a.setText(meterB1.getA_Value().getPhaseC().getShowValue());
    }

    public void setData2View(List<ModelBaseData> listB1){
        if(!listB1.isEmpty()) {
            tv_phaseA_v.setText(listB1.get(0).getValue());
            if(listB1.size()>1) {
                tv_phaseB_v.setText(listB1.get(1).getValue());
                if (listB1.size() > 2) {
                    tv_phaseC_v.setText(listB1.get(2).getValue());
                    if (listB1.size() > 3) {
                        tv_phaseA_peakV.setText(listB1.get(3).getValue());
                        if (listB1.size() > 4) {
                            tv_phaseB_peakV.setText(listB1.get(4).getValue());
                            if (listB1.size() > 5) {
                                tv_phaseC_peakV.setText(listB1.get(5).getValue());
                                if (listB1.size() > 6) {
                                    tv_phaseA_a.setText(listB1.get(6).getValue());
                                    if (listB1.size() > 7) {
                                        tv_phaseB_a.setText(listB1.get(7).getValue());
                                        if (listB1.size() > 8) {
                                            tv_phaseC_a.setText(listB1.get(8).getValue());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        byte pro = 0;
        switch (v.getId()){
            case R.id.v_phaseA_slope_add:
                pro = (byte) 0x12;
                break;
            case R.id.v_phaseA_slope_sub:
                pro = (byte) 0x22;
                break;
            case R.id.v_phaseA_base_add:
                pro = (byte) 0x13;
                break;
            case R.id.v_phaseA_base_sub:
                pro = (byte) 0x23;
                break;
            case R.id.peakv_phaseA_slope_add:
                pro = (byte) 0x53;
                break;
            case R.id.peakv_phaseA_slope_sub:
                pro = (byte) 0x63;
                break;
            case R.id.a_phaseA_slope_add:
                pro = (byte) 0xC2;
                break;
            case R.id.a_phaseA_slope_sub:
                pro = (byte) 0xD2;
                break;
            case R.id.a_phaseA_base_add:
                pro = (byte) 0xC3;
                break;
            case R.id.a_phaseA_base_sub:
                pro = (byte) 0xD3;
                break;

            case R.id.v_phaseB_slope_add:
                pro = (byte) 0x16;
                break;
            case R.id.v_phaseB_slope_sub:
                pro = (byte) 0x26;
                break;
            case R.id.v_phaseB_base_add:
                pro = (byte) 0x17;
                break;
            case R.id.v_phaseB_base_sub:
                pro = (byte) 0x27;
                break;
            case R.id.peakv_phaseB_slope_add:
                pro = (byte) 0x54;
                break;
            case R.id.peakv_phaseB_slope_sub:
                pro = (byte) 0x64;
                break;
            case R.id.a_phaseB_slope_add:
                pro = (byte) 0xC6;
                break;
            case R.id.a_phaseB_slope_sub:
                pro = (byte) 0xD6;
                break;
            case R.id.a_phaseB_base_add:
                pro = (byte) 0xC7;
                break;
            case R.id.a_phaseB_base_sub:
                pro = (byte) 0xD7;
                break;

            case R.id.v_phaseC_slope_add:
                pro = (byte) 0x1A;
                break;
            case R.id.v_phaseC_slope_sub:
                pro = (byte) 0x2A;
                break;
            case R.id.v_phaseC_base_add:
                pro = (byte) 0x1B;
                break;
            case R.id.v_phaseC_base_sub:
                pro = (byte) 0x2B;
                break;
            case R.id.peakv_phaseC_slope_add:
                pro = (byte) 0x55;
                break;
            case R.id.peakv_phaseC_slope_sub:
                pro = (byte) 0x65;
                break;
            case R.id.a_phaseC_slope_add:
                pro = (byte) 0xCA;
                break;
            case R.id.a_phaseC_slope_sub:
                pro = (byte) 0xDA;
                break;
            case R.id.a_phaseC_base_add:
                pro = (byte) 0xCB;
                break;
            case R.id.a_phaseC_base_sub:
                pro = (byte) 0xDB;
                break;

        }
//        Toast.makeText(getActivity(),Utils_Byte.bytesToHexString(pro),Toast.LENGTH_SHORT).show();
 //       CEMThreeLineLib.getInstance().writeByte(pro);
  //      SerialPortHelp.getInstance().sendData(pro);

        Toast.makeText(getActivity(),""+ Utils_Byte.bytesToHexString(pro),Toast.LENGTH_SHORT).show();
        //       CEMThreeLineLib.getInstance().writeByte(pro);
        SerialPortHelp.getInstance().sendData(new byte[]{(byte) 0x00,pro});

    }
}
