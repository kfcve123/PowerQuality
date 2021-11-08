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
import com.cem.powerqualityanalyser.libs.MeterDebufB2;
import com.cem.powerqualityanalyser.libs.Utils_Byte;

import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelBaseData;
import serialport.amos.cem.com.libamosserial.SerialPortHelp;


public class B2Fragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextView tv_phaseA_v,tv_phaseB_v,tv_phaseC_v,tv_phaseN_v,
            tv_phaseA_peakV,tv_phaseB_peakV,tv_phaseC_peakV,tv_phaseN_peakV,
            tv_hz;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_b2,null);
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
        tv_hz = rootView.findViewById(R.id.tv_hz);

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
        rootView.findViewById(R.id.peakv_phaseN_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.peakv_phaseN_slope_sub).setOnClickListener(this);


        rootView.findViewById(R.id.v_phaseN_slope_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseN_slope_sub).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseN_base_add).setOnClickListener(this);
        rootView.findViewById(R.id.v_phaseN_base_sub).setOnClickListener(this);

        rootView.findViewById(R.id.hz_add).setOnClickListener(this);
        rootView.findViewById(R.id.hz_sub).setOnClickListener(this);

    }

    public void setData2View(MeterDebufB2 meterB246){
        tv_phaseA_v.setText(meterB246.getA_Value().getPhaseA().getShowValue());
        tv_phaseB_v.setText(meterB246.getA_Value().getPhaseB().getShowValue());
        tv_phaseC_v.setText(meterB246.getA_Value().getPhaseC().getShowValue());
        tv_phaseN_v.setText(meterB246.getA_Value().getPhaseN().getShowValue());

        tv_phaseA_peakV.setText(meterB246.getA_Max().getPhaseA().getShowValue());
        tv_phaseB_peakV.setText(meterB246.getA_Max().getPhaseB().getShowValue());
        tv_phaseC_peakV.setText(meterB246.getA_Max().getPhaseC().getShowValue());

        tv_hz.setText(meterB246.getAngle().getShowValue());
    }

    public void setData2View(List<ModelBaseData> listB2) {
        if (!listB2.isEmpty()) {
            tv_phaseA_v.setText(listB2.get(0).getValue());
            if (listB2.size() > 1) {
                tv_phaseB_v.setText(listB2.get(1).getValue());
                if (listB2.size() > 2) {
                    tv_phaseC_v.setText(listB2.get(2).getValue());
                    if (listB2.size() > 3) {
                        tv_phaseN_v.setText(listB2.get(3).getValue());
                        if (listB2.size() > 4) {
                            tv_phaseA_peakV.setText(listB2.get(4).getValue());
                            if (listB2.size() > 5) {
                                tv_phaseB_peakV.setText(listB2.get(5).getValue());
                                if (listB2.size() > 6) {
                                    tv_phaseC_peakV.setText(listB2.get(6).getValue());
                                    if (listB2.size() > 7) {
                                        tv_phaseN_peakV.setText(listB2.get(7).getValue());
                                        if (listB2.size() > 8) {
                                            tv_hz.setText(listB2.get(8).getValue());
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
                pro = (byte) 0x90;
                break;
            case R.id.v_phaseA_slope_sub:
                pro = (byte) 0xA0;
                break;
            case R.id.v_phaseA_base_add:
                pro = (byte) 0x91;
                break;
            case R.id.v_phaseA_base_sub:
                pro = (byte) 0xA1;
                break;
            case R.id.peakv_phaseA_slope_add:
                pro = (byte) 0x56;
                break;
            case R.id.peakv_phaseA_slope_sub:
                pro = (byte) 0x66;
                break;


            case R.id.v_phaseB_slope_add:
                pro = (byte) 0x94;
                break;
            case R.id.v_phaseB_slope_sub:
                pro = (byte) 0xA4;
                break;
            case R.id.v_phaseB_base_add:
                pro = (byte) 0x95;
                break;
            case R.id.v_phaseB_base_sub:
                pro = (byte) 0xA5;
                break;
            case R.id.peakv_phaseB_slope_add:
                pro = (byte) 0x57;
                break;
            case R.id.peakv_phaseB_slope_sub:
                pro = (byte) 0x67;
                break;

            case R.id.v_phaseC_slope_add:
                pro = (byte) 0x98;
                break;
            case R.id.v_phaseC_slope_sub:
                pro = (byte) 0xA8;
                break;
            case R.id.v_phaseC_base_add:
                pro = (byte) 0x99;
                break;
            case R.id.v_phaseC_base_sub:
                pro = (byte) 0xA9;
                break;
            case R.id.peakv_phaseC_slope_add:
                pro = (byte) 0x58;
                break;
            case R.id.peakv_phaseC_slope_sub:
                pro = (byte) 0x68;
                break;


            case R.id.v_phaseN_slope_add:
                pro = (byte) 0x9C;
                break;
            case R.id.v_phaseN_slope_sub:
                pro = (byte) 0xAC;
                break;
            case R.id.v_phaseN_base_add:
                pro = (byte) 0x9D;
                break;
            case R.id.v_phaseN_base_sub:
                pro = (byte) 0xAD;
                break;

            case R.id.peakv_phaseN_slope_add:
                pro = (byte) 0xF9;
                break;
            case R.id.peakv_phaseN_slope_sub:
                pro = (byte) 0xFC;
                break;



            case R.id.hz_add:
                pro = (byte) 0xF2;
                break;
            case R.id.hz_sub:
                pro = (byte) 0xF3;
                break;

        }
//        Toast.makeText(getContext(),Utils_Byte.bytesToHexString(pro),Toast.LENGTH_SHORT).show();
//        CEMThreeLineLib.getInstance().writeByte(pro);
        Toast.makeText(getActivity(),""+ Utils_Byte.bytesToHexString(pro),Toast.LENGTH_SHORT).show();
        //       CEMThreeLineLib.getInstance().writeByte(pro);
        SerialPortHelp.getInstance().sendData(new byte[]{(byte) 0x00,pro});
    }
}
