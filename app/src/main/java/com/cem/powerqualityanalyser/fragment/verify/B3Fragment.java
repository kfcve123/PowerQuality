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
import com.cem.powerqualityanalyser.libs.MeterDebufB3;
import com.cem.powerqualityanalyser.libs.Utils_Byte;

import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelBaseData;
import serialport.amos.cem.com.libamosserial.SerialPortHelp;


public class B3Fragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextView tv_phaseA_v,tv_phaseB_v,tv_phaseC_v,tv_phaseN_v,
            tv_phaseA_peakV,tv_phaseB_peakV,tv_phaseC_peakV,tv_phaseN_peakV;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_b3,null);
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
        tv_phaseN_v = rootView.findViewById(R.id.tv_phaseN_v);

        tv_phaseA_peakV = rootView.findViewById(R.id.tv_phaseA_peakV);
        tv_phaseB_peakV = rootView.findViewById(R.id.tv_phaseB_peakV);
        tv_phaseC_peakV = rootView.findViewById(R.id.tv_phaseC_peakV);
        tv_phaseN_peakV = rootView.findViewById(R.id.tv_phaseN_peakV);

        rootView.findViewById(R.id.v_phaseA_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseA_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseA_base_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseA_base_sub).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseA_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseA_slope_sub).setOnClickListener(this);

        rootView.findViewById(R.id.v_phaseB_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseB_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseB_base_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseB_base_sub).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseB_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseB_slope_sub).setOnClickListener(this);

        rootView.findViewById(R.id.v_phaseC_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseC_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseC_base_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseC_base_sub).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseC_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseC_slope_sub).setOnClickListener(this);

        rootView.findViewById(R.id.v_phaseN_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseN_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseN_base_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseN_base_sub).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseN_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseN_slope_sub).setOnClickListener(this);


    }

    public void setData2View(MeterDebufB3 meterB357){
        tv_phaseA_v.setText(meterB357.getA_Value().getPhaseA().getShowValue());
        tv_phaseB_v.setText(meterB357.getA_Value().getPhaseB().getShowValue());
        tv_phaseC_v.setText(meterB357.getA_Value().getPhaseC().getShowValue());

        tv_phaseA_peakV.setText(meterB357.getA_Max().getPhaseA().getShowValue());
        tv_phaseB_peakV.setText(meterB357.getA_Max().getPhaseB().getShowValue());
        tv_phaseC_peakV.setText(meterB357.getA_Max().getPhaseC().getShowValue());


    }

    /*public void setData2View(List<ModelBaseData> listB3){
        if(!listB3.isEmpty()) {
            tv_phaseA_v.setText(listB3.get(0).getValue());
            if(listB3.size()>1) {
                tv_phaseB_v.setText(listB3.get(1).getValue());
                if(listB3.size()>2) {
                    tv_phaseC_v.setText(listB3.get(2).getValue());
                    if(listB3.size()>3) {
                        tv_phaseA_peakV.setText(listB3.get(3).getValue());
                        if(listB3.size()>4) {
                            tv_phaseB_peakV.setText(listB3.get(4).getValue());
                            if(listB3.size()>5) {
                                tv_phaseC_peakV.setText(listB3.get(5).getValue());
                            }
                        }
                    }
                }
            }
        }
    }*/


    public void setData2View(List<ModelBaseData> listB3){
        if(!listB3.isEmpty()) {
            tv_phaseA_v.setText(listB3.get(0).getValue());
            if(listB3.size()>1) {
                tv_phaseB_v.setText(listB3.get(1).getValue());
                if(listB3.size()>2) {
                    tv_phaseC_v.setText(listB3.get(2).getValue());
                    if(listB3.size()>3) {
                        tv_phaseN_v.setText(listB3.get(3).getValue());
                        if(listB3.size()>4) {
                            tv_phaseA_peakV.setText(listB3.get(4).getValue());
                            if(listB3.size()>5) {
                                tv_phaseB_peakV.setText(listB3.get(5).getValue());
                                if(listB3.size()>6) {
                                    tv_phaseC_peakV.setText(listB3.get(6).getValue());
                                    if(listB3.size()>7) {
                                        tv_phaseN_peakV.setText(listB3.get(7).getValue());
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
                pro = (byte) 0x92;
                break;
            case R.id.v_phaseA_slope_sub:
                pro = (byte) 0xA2;
                break;
            case R.id.v_phaseA_base_add:
                pro = (byte) 0x93;
                break;
            case R.id.v_phaseA_base_sub:
                pro = (byte) 0xA3;
                break;
            case R.id.peakv_phaseA_slope_add:
                pro = (byte) 0x59;
                break;
            case R.id.peakv_phaseA_slope_sub:
                pro = (byte) 0x69;
                break;

            case R.id.v_phaseB_slope_add:
                pro = (byte) 0x96;
                break;
            case R.id.v_phaseB_slope_sub:
                pro = (byte) 0xA6;
                break;
            case R.id.v_phaseB_base_add:
                pro = (byte) 0x97;
                break;
            case R.id.v_phaseB_base_sub:
                pro = (byte) 0xA7;
                break;
            case R.id.peakv_phaseB_slope_add:
                pro = (byte) 0x5A;
                break;
            case R.id.peakv_phaseB_slope_sub:
                pro = (byte) 0x6A;
                break;
            case R.id.v_phaseC_slope_add:
                pro = (byte) 0x9A;
                break;
            case R.id.v_phaseC_slope_sub:
                pro = (byte) 0xAA;
                break;
            case R.id.v_phaseC_base_add:
                pro = (byte) 0x9B;
                break;
            case R.id.v_phaseC_base_sub:
                pro = (byte) 0xAB;
                break;
            case R.id.peakv_phaseC_slope_add:
                pro = (byte) 0x5B;
                break;
            case R.id.peakv_phaseC_slope_sub:
                pro = (byte) 0x6B;
                break;


            case R.id.v_phaseN_slope_add:
                pro = (byte) 0x70;
                break;
            case R.id.v_phaseN_slope_sub:
                pro = (byte) 0x80;
                break;
            case R.id.v_phaseN_base_add:
                pro = (byte) 0x71;
                break;
            case R.id.v_phaseN_base_sub:
                pro = (byte) 0x81;
                break;
            case R.id.peakv_phaseN_slope_add:
                pro = (byte) 0x72;
                break;
            case R.id.peakv_phaseN_slope_sub:
                pro = (byte) 0x82;
                break;

        }


        Toast.makeText(getActivity(),""+ Utils_Byte.bytesToHexString(pro),Toast.LENGTH_SHORT).show();
        //       CEMThreeLineLib.getInstance().writeByte(pro);
        SerialPortHelp.getInstance().sendData(new byte[]{(byte) 0x00,pro});
    }
}
