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
import com.cem.powerqualityanalyser.libs.MeterDebufB0;
import com.cem.powerqualityanalyser.libs.Utils_Byte;
import com.cem.powerqualityanalyser.tool.log;

import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelBaseData;
import serialport.amos.cem.com.libamosserial.SerialPortHelp;


public class B0Fragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextView tv_phaseA_v,tv_phaseB_v,tv_phaseC_v,tv_phaseN_v,
            tv_phaseA_peakV,tv_phaseB_peakV,tv_phaseC_peakV,tv_phaseN_peakV,
            tv_phaseA_a,tv_phaseB_a,tv_phaseC_a,tv_phaseN_a,
            tv_hz;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_b0,null);
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
        tv_phaseA_a = rootView.findViewById(R.id.tv_phaseA_a);
        tv_phaseB_a = rootView.findViewById(R.id.tv_phaseB_a);
        tv_phaseC_a = rootView.findViewById(R.id.tv_phaseC_a);
        tv_phaseN_a = rootView.findViewById(R.id.tv_phaseN_a);
        tv_hz = rootView.findViewById(R.id.tv_hz);

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

        rootView.findViewById(R.id.v_phaseN_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseN_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseN_base_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseN_base_sub).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseN_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseN_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseN_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseN_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseN_base_add).setOnClickListener(this);
        rootView.findViewById(R.id.a_phaseN_base_sub).setOnClickListener(this);

        rootView.findViewById(R.id.hz_add).setOnClickListener(this);
        rootView.findViewById(R.id.hz_sub).setOnClickListener(this);

    }

    public void setData2View(MeterDebufB0 meterB0){
        tv_phaseA_v.setText(meterB0.getV_Value().getPhaseA().getShowValue());
        tv_phaseB_v.setText(meterB0.getV_Value().getPhaseB().getShowValue());
        tv_phaseC_v.setText(meterB0.getV_Value().getPhaseC().getShowValue());
        tv_phaseN_v.setText(meterB0.getV_Value().getPhaseN().getShowValue());

        tv_phaseA_peakV.setText(meterB0.getV_Max().getPhaseA().getShowValue());
        tv_phaseB_peakV.setText(meterB0.getV_Max().getPhaseB().getShowValue());
        tv_phaseC_peakV.setText(meterB0.getV_Max().getPhaseC().getShowValue());

        tv_phaseA_a.setText(meterB0.getA_Value().getPhaseA().getShowValue());
        tv_phaseB_a.setText(meterB0.getA_Value().getPhaseB().getShowValue());
        tv_phaseC_a.setText(meterB0.getA_Value().getPhaseC().getShowValue());
        tv_phaseN_a.setText(meterB0.getA_Value().getPhaseN().getShowValue());

        tv_hz.setText(meterB0.getFrequency().getShowValue());
    }

    public void setData2View(List<ModelBaseData> listB0){
        if(!listB0.isEmpty()) {
            tv_phaseA_v.setText(listB0.get(0).getValue());
            if(listB0.size()>1) {
                tv_phaseB_v.setText(listB0.get(1).getValue());
                if(listB0.size()>2) {
                    tv_phaseC_v.setText(listB0.get(2).getValue());
                    if(listB0.size()>3) {
                        tv_phaseN_v.setText(listB0.get(3).getValue());
                        if(listB0.size()>4) {
                            tv_phaseA_peakV.setText(listB0.get(4).getValue());
                            if(listB0.size()>5) {
                                tv_phaseB_peakV.setText(listB0.get(5).getValue());
                                if (listB0.size() > 6) {
                                    tv_phaseC_peakV.setText(listB0.get(6).getValue());
                                    if (listB0.size() > 7) {
                                        tv_phaseN_peakV.setText(listB0.get(7).getValue());
                                        if (listB0.size() > 8) {
                                            tv_phaseA_a.setText(listB0.get(8).getValue());
                                            if (listB0.size() > 9) {
                                                tv_phaseB_a.setText(listB0.get(9).getValue());
                                                if (listB0.size() > 10) {
                                                    tv_phaseC_a.setText(listB0.get(10).getValue());
                                                    if (listB0.size() > 11) {
                                                        tv_phaseN_a.setText(listB0.get(11).getValue());
                                                        if (listB0.size() > 12) {
                                                            tv_hz.setText(listB0.get(12).getValue());
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
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        byte pro = 0;
        switch (v.getId()){
            case R.id.v_phaseA_slope_add:
                pro = (byte) 0x10;
                break;
            case R.id.v_phaseA_slope_sub:
                pro = (byte) 0x20;
                break;
            case R.id.v_phaseA_base_add:
                pro = (byte) 0x11;
                break;
            case R.id.v_phaseA_base_sub:
                pro = (byte) 0x21;
                break;
            case R.id.peakv_phaseA_slope_add:
                pro = (byte) 0x50;
                break;
            case R.id.peakv_phaseA_slope_sub:
                pro = (byte) 0x60;
                break;
            case R.id.a_phaseA_slope_add:
                pro = (byte) 0xC0;
                break;
            case R.id.a_phaseA_slope_sub:
                pro = (byte) 0xD0;
                break;
            case R.id.a_phaseA_base_add:
                pro = (byte) 0xC1;
                break;
            case R.id.a_phaseA_base_sub:
                pro = (byte) 0xD1;
                break;

            case R.id.v_phaseB_slope_add:
                pro = (byte) 0x14;
                break;
            case R.id.v_phaseB_slope_sub:
                pro = (byte) 0x24;
                break;
            case R.id.v_phaseB_base_add:
                pro = (byte) 0x15;
                break;
            case R.id.v_phaseB_base_sub:
                pro = (byte) 0x25;
                break;
            case R.id.peakv_phaseB_slope_add:
                pro = (byte) 0x51;
                break;
            case R.id.peakv_phaseB_slope_sub:
                pro = (byte) 0x61;
                break;
            case R.id.a_phaseB_slope_add:
                pro = (byte) 0xC4;
                break;
            case R.id.a_phaseB_slope_sub:
                pro = (byte) 0xD4;
                break;
            case R.id.a_phaseB_base_add:
                pro = (byte) 0xC5;
                break;
            case R.id.a_phaseB_base_sub:
                pro = (byte) 0xD5;
                break;

            case R.id.v_phaseC_slope_add:
                pro = (byte) 0x18;
                break;
            case R.id.v_phaseC_slope_sub:
                pro = (byte) 0x28;
                break;
            case R.id.v_phaseC_base_add:
                pro = (byte) 0x19;
                break;
            case R.id.v_phaseC_base_sub:
                pro = (byte) 0x29;
                break;
            case R.id.peakv_phaseC_slope_add:
                pro = (byte) 0x52;
                break;
            case R.id.peakv_phaseC_slope_sub:
                pro = (byte) 0x62;
                break;
            case R.id.a_phaseC_slope_add:
                pro = (byte) 0xC8;
                break;
            case R.id.a_phaseC_slope_sub:
                pro = (byte) 0xD8;
                break;
            case R.id.a_phaseC_base_add:
                pro = (byte) 0xC9;
                break;
            case R.id.a_phaseC_base_sub:
                pro = (byte) 0xD9;
                break;

            case R.id.v_phaseN_slope_add:
                pro = (byte) 0x1C;
                break;
            case R.id.v_phaseN_slope_sub:
                pro = (byte) 0x2C;
                break;
            case R.id.v_phaseN_base_add:
                pro = (byte) 0x1D;
                break;
            case R.id.v_phaseN_base_sub:
                pro = (byte) 0x2D;
                break;
            case R.id.peakv_phaseN_slope_add:
                pro = (byte) 0xF8;
                break;
            case R.id.peakv_phaseN_slope_sub:
                pro = (byte) 0xFB;
                break;
            case R.id.a_phaseN_slope_add:
                pro = (byte) 0xCC;
                break;
            case R.id.a_phaseN_slope_sub:
                pro = (byte) 0xDC;
                break;
            case R.id.a_phaseN_base_add:
                pro = (byte) 0xCD;
                break;
            case R.id.a_phaseN_base_sub:
                pro = (byte) 0xDD;
                break;

            case R.id.hz_add:
                pro = (byte) 0xF0;
                break;
            case R.id.hz_sub:
                pro = (byte) 0xF1;
                break;

        }
        Toast.makeText(getActivity(),""+ Utils_Byte.bytesToHexString(pro),Toast.LENGTH_SHORT).show();
 //       CEMThreeLineLib.getInstance().writeByte(pro);
        SerialPortHelp.getInstance().sendData(new byte[]{(byte) 0x00,pro});
    }
}
