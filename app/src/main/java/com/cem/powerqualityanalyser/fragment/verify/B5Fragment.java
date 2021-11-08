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


public class B5Fragment extends Fragment implements View.OnClickListener {
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
        tv_phaseA_peakV = rootView.findViewById(R.id.tv_phaseA_peakV);
        tv_phaseB_peakV = rootView.findViewById(R.id.tv_phaseB_peakV);
        tv_phaseC_peakV = rootView.findViewById(R.id.tv_phaseC_peakV);

        tv_phaseN_v = rootView.findViewById(R.id.tv_phaseN_v);
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

    /*public void setData2View(List<ModelBaseData> listB5){
        if(!listB5.isEmpty()){
            tv_phaseA_v.setText(listB5.get(0).getValue());
            if(listB5.size()>1) {
                tv_phaseB_v.setText(listB5.get(1).getValue());
                if (listB5.size() > 2) {
                    tv_phaseC_v.setText(listB5.get(2).getValue());
                    if (listB5.size() > 3) {
                        tv_phaseA_peakV.setText(listB5.get(3).getValue());
                        if (listB5.size() > 4) {
                            tv_phaseB_peakV.setText(listB5.get(4).getValue());
                            if (listB5.size() > 5) {
                                tv_phaseC_peakV.setText(listB5.get(5).getValue());
                            }
                        }
                    }
                }
            }
        }
    }*/

    public void setData2View(List<ModelBaseData> listB5){
        if(!listB5.isEmpty()) {
            tv_phaseA_v.setText(listB5.get(0).getValue());
            if(listB5.size()>1) {
                tv_phaseB_v.setText(listB5.get(1).getValue());
                if(listB5.size()>2) {
                    tv_phaseC_v.setText(listB5.get(2).getValue());
                    if(listB5.size()>3) {
                        tv_phaseN_v.setText(listB5.get(3).getValue());
                        if(listB5.size()>4) {
                            tv_phaseA_peakV.setText(listB5.get(4).getValue());
                            if(listB5.size()>5) {
                                tv_phaseB_peakV.setText(listB5.get(5).getValue());
                                if(listB5.size()>6) {
                                    tv_phaseC_peakV.setText(listB5.get(6).getValue());
                                    if(listB5.size()>7) {
                                        tv_phaseN_peakV.setText(listB5.get(7).getValue());
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
                pro = (byte) 0x32;
                break;
            case R.id.v_phaseA_slope_sub:
                pro = (byte) 0x42;
                break;
            case R.id.v_phaseA_base_add:
                pro = (byte) 0x33;
                break;
            case R.id.v_phaseA_base_sub:
                pro = (byte) 0x43;
                break;
            case R.id.peakv_phaseA_slope_add:
                pro = (byte) 0x5F;
                break;
            case R.id.peakv_phaseA_slope_sub:
                pro = (byte) 0x6F;
                break;

            case R.id.v_phaseB_slope_add:
                pro = (byte) 0x36;
                break;
            case R.id.v_phaseB_slope_sub:
                pro = (byte) 0x46;
                break;
            case R.id.v_phaseB_base_add:
                pro = (byte) 0x37;
                break;
            case R.id.v_phaseB_base_sub:
                pro = (byte) 0x47;
                break;
            case R.id.peakv_phaseB_slope_add:
                pro = (byte) 0x7E;
                break;
            case R.id.peakv_phaseB_slope_sub:
                pro = (byte) 0x8E;
                break;

            case R.id.v_phaseC_slope_add:
                pro = (byte) 0x3A;
                break;
            case R.id.v_phaseC_slope_sub:
                pro = (byte) 0x4A;
                break;
            case R.id.v_phaseC_base_add:
                pro = (byte) 0x3B;
                break;
            case R.id.v_phaseC_base_sub:
                pro = (byte) 0x4B;
                break;
            case R.id.peakv_phaseC_slope_add:
                pro = (byte) 0x7F;
                break;
            case R.id.peakv_phaseC_slope_sub:
                pro = (byte) 0x8F;
                break;

            case R.id.v_phaseN_slope_add:
                pro = (byte) 0x73;
                break;
            case R.id.v_phaseN_slope_sub:
                pro = (byte) 0x83;
                break;
            case R.id.v_phaseN_base_add:
                pro = (byte) 0x74;
                break;
            case R.id.v_phaseN_base_sub:
                pro = (byte) 0x84;
                break;
            case R.id.peakv_phaseN_slope_add:
                pro = (byte) 0x75;
                break;
            case R.id.peakv_phaseN_slope_sub:
                pro = (byte) 0x85;
                break;


        }
        Toast.makeText(getActivity(),""+ Utils_Byte.bytesToHexString(pro),Toast.LENGTH_SHORT).show();
        //       CEMThreeLineLib.getInstance().writeByte(pro);
        SerialPortHelp.getInstance().sendData(new byte[]{(byte) 0x00,pro});

    }
}
