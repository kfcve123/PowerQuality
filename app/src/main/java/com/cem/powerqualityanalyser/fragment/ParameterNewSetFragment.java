package com.cem.powerqualityanalyser.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.command.RecordCommand;
import com.cem.powerqualityanalyser.libs.MeterCommand;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.view.RightModeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterNewSetFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {
    private List<CheckBox> VoltAmps,Powers,Flickers,Unbalances,VoltHarmonics,AmpHarmonics,Frequencys,Energys;
    private CheckBox power_kw,power_kva,power_kvar,power_kva_harm,power_cos,power_kva_unb,power_kw_fund,power_kva_fund,power_w_fund,
            energy_kvah,energy_kwh_forw,energy_kwh,energy_kvarh,energy_kwh_rev,
            voltamp_urms,voltamp_vrms,voltamp_arms,voltamp_ucf,voltamp_udc,voltamp_vdc,voltamp_adc,voltamp_vcf,
            voltamp_upeak,voltamp_vpeak,voltamp_apeak,voltamp_acf,voltamp_upeak1,voltamp_vpeak1,voltamp_apeak1,
            freq_hz,
            flicker_pst1min,flicker_pst,flicker_plt,flicker_pinst,
            unbalance_unbal,unbalance_vneg,unbalance_vzero,unbalance_aneg,unbalance_azero,
            voltharmonic_vfund,voltharmonic_thdf,voltharmonic_thdr,voltharmonic_dc,
            voltharmonic_h1,voltharmonic_h2,voltharmonic_h3,voltharmonic_h4,voltharmonic_h5,
            voltharmonic_h6,voltharmonic_h7,voltharmonic_h8,voltharmonic_h9,voltharmonic_h10,
            voltharmonic_h11,voltharmonic_h12,voltharmonic_h13,voltharmonic_h14,voltharmonic_h15,
            voltharmonic_h16,voltharmonic_h17,voltharmonic_h18,voltharmonic_h19,voltharmonic_h20,
            voltharmonic_h21,voltharmonic_h22,voltharmonic_h23,voltharmonic_h24,voltharmonic_h25,
            voltharmonic_h26,voltharmonic_h27,voltharmonic_h28,voltharmonic_h29,voltharmonic_h30,
            voltharmonic_h31,voltharmonic_h32,voltharmonic_h33,voltharmonic_h34,voltharmonic_h35,
            voltharmonic_h36,voltharmonic_h37,voltharmonic_h38,voltharmonic_h39,voltharmonic_h40,
            voltharmonic_h41,voltharmonic_h42,voltharmonic_h43,voltharmonic_h44,voltharmonic_h45,
            voltharmonic_h46,voltharmonic_h47,voltharmonic_h48,voltharmonic_h49,voltharmonic_h50,
            ampharmonic_afund,ampharmonic_thdf,ampharmonic_thdr,ampharmonic_dc,
            ampharmonic_h1,ampharmonic_h2,ampharmonic_h3,ampharmonic_h4,ampharmonic_h5,
            ampharmonic_h6,ampharmonic_h7,ampharmonic_h8,ampharmonic_h9,ampharmonic_h10,
            ampharmonic_h11,ampharmonic_h12,ampharmonic_h13,ampharmonic_h14,ampharmonic_h15,
            ampharmonic_h16,ampharmonic_h17,ampharmonic_h18,ampharmonic_h19,ampharmonic_h20,
            ampharmonic_h21,ampharmonic_h22,ampharmonic_h23,ampharmonic_h24,ampharmonic_h25,
            ampharmonic_h26,ampharmonic_h27,ampharmonic_h28,ampharmonic_h29,ampharmonic_h30,
            ampharmonic_h31,ampharmonic_h32,ampharmonic_h33,ampharmonic_h34,ampharmonic_h35,
            ampharmonic_h36,ampharmonic_h37,ampharmonic_h38,ampharmonic_h39,ampharmonic_h40,
            ampharmonic_h41,ampharmonic_h42,ampharmonic_h43,ampharmonic_h44,ampharmonic_h45,
            ampharmonic_h46,ampharmonic_h47,ampharmonic_h48,ampharmonic_h49,ampharmonic_h50;


    private ViewGroup background_power,background_flicker,background_unbalance,background_vlotharmonic,background_ampharmonic,background_freq,background_voltamp,background_energy;
    private ArrayList<String> checkName = new ArrayList<>();

    private ArrayList<Integer> checkIndex = new ArrayList<>();



    private RightModeView rightModeView;
    private List<RightListViewItemObj> strList;
    private int rightIndex = 0;
    private int lastRightIndex = 0;
    private boolean isAllChecked;


    @Override
    public int setContentView() {
        return R.layout.fragment_parameternewset;
    }

    @Override
    public void onInitView() {

        rightModeView = (RightModeView) findViewById(R.id.parameter_rightview);
        rightModeView.setUpDownClick(false);
        rightModeView.hideUpDownView();
        rightModeView.setTextSize(20);
        rightModeView.setTypeContentTvBg(R.mipmap.parameter_right_choose_off,R.mipmap.parameter_right_choose_on);
        strList =  new ArrayList();

//        strList.add(new RightListViewItemObj(getString(R.string.parameter_VoltAmp), -1,20));
//        strList.add(new RightListViewItemObj(getString(R.string.parameter_Inrush), -1,20));
//        strList.add(new RightListViewItemObj(getString(R.string.parameter_Shipboard), -1,20));
//        strList.add(new RightListViewItemObj(getString(R.string.parameter_Harmonic), -1,20));
//        strList.add(new RightListViewItemObj(getString(R.string.parameter_DipsSweels), -1,20));
//        strList.add(new RightListViewItemObj(getString(R.string.parameter_Unbalance), -1,20));
//        strList.add(new RightListViewItemObj(getString(R.string.parameter_Power), -1,20));
//        strList.add(new RightListViewItemObj(getString(R.string.parameter_Energy), -1,20));

        strList.add(new RightListViewItemObj(getString(R.string.parameter_VoltAmp), -1,20));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_Flicker), -1,20));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_Frequency), -1,20));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_volt_harm), -1,18));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_amp_harm), -1,18));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_Unbalance), -1,20));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_Power), -1,20));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_Energy), -1,20));


        rightModeView.setModeList(strList);
        rightModeView.setOnItemCheckCallBack(new RightModeView.OnItemCheckCallBack() {
            @Override
            public void onItemCheck(int item) {
                rightIndex = item;
                if(lastRightIndex!=item) {
                    isAllChecked = false;
                    lastRightIndex = item;
                }
                switch (item){
                    case 0:
                        background_voltamp.setVisibility(View.VISIBLE);
                        background_flicker.setVisibility(View.GONE);
                        background_freq.setVisibility(View.GONE);
                        background_vlotharmonic.setVisibility(View.GONE);
                        background_ampharmonic.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.GONE);
                        break;
                    case 1:
                        background_voltamp.setVisibility(View.GONE);
                        background_flicker.setVisibility(View.VISIBLE);
                        background_freq.setVisibility(View.GONE);
                        background_vlotharmonic.setVisibility(View.GONE);
                        background_ampharmonic.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.GONE);

                        break;
                    case 2:
                        background_voltamp.setVisibility(View.GONE);
                        background_flicker.setVisibility(View.GONE);
                        background_freq.setVisibility(View.VISIBLE);
                        background_vlotharmonic.setVisibility(View.GONE);
                        background_ampharmonic.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.GONE);

                        break;
                    case 3:
                        background_voltamp.setVisibility(View.GONE);
                        background_flicker.setVisibility(View.GONE);
                        background_freq.setVisibility(View.GONE);
                        background_vlotharmonic.setVisibility(View.VISIBLE);
                        background_ampharmonic.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.GONE);

                        break;
                    case 4:
                        background_voltamp.setVisibility(View.GONE);
                        background_flicker.setVisibility(View.GONE);
                        background_freq.setVisibility(View.GONE);
                        background_vlotharmonic.setVisibility(View.GONE);
                        background_ampharmonic.setVisibility(View.VISIBLE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.GONE);

                        break;
                    case 5:
                        background_voltamp.setVisibility(View.GONE);
                        background_flicker.setVisibility(View.GONE);
                        background_freq.setVisibility(View.GONE);
                        background_vlotharmonic.setVisibility(View.GONE);
                        background_ampharmonic.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.VISIBLE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.GONE);

                        break;
                    case 6:
                        background_voltamp.setVisibility(View.GONE);
                        background_flicker.setVisibility(View.GONE);
                        background_freq.setVisibility(View.GONE);
                        background_vlotharmonic.setVisibility(View.GONE);
                        background_ampharmonic.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.VISIBLE);
                        background_energy.setVisibility(View.GONE);

                        break;
                    case 7:
                        background_voltamp.setVisibility(View.GONE);
                        background_flicker.setVisibility(View.GONE);
                        background_freq.setVisibility(View.GONE);
                        background_vlotharmonic.setVisibility(View.GONE);
                        background_ampharmonic.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });

        initVoltAmpCheckBoxs();
        initFlickerCheckBoxs();
        initFreqCheckBoxs();
        initVoltHarmonicCheckBoxs();
        initAmpHarmonicCheckBoxs();
        initUnbalanceCheckBoxs();
        initPowerCheckBoxs();
        initEnergyCheckBoxs();

        background_voltamp = (ViewGroup) findViewById(R.id.layout_voltamp);
        background_flicker = (ViewGroup) this.findViewById(R.id.layout_flicker);
        background_freq = (ViewGroup) this.findViewById(R.id.layout_freq);
        background_vlotharmonic = (ViewGroup) this.findViewById(R.id.layout_vlotharmonic);
        background_ampharmonic = (ViewGroup) this.findViewById(R.id.layout_ampharmonic);
        background_unbalance = (ViewGroup) this.findViewById(R.id.layout_unbalance);
        background_power = (ViewGroup) this.findViewById(R.id.layout_power);
        background_energy = (ViewGroup) findViewById(R.id.layout_energy);

    }

    private void initVoltAmpCheckBoxs() {
        VoltAmps = new ArrayList<>();

        voltamp_urms =  (CheckBox) findViewById(R.id.voltamp_urms);
        voltamp_urms.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_urms);

        voltamp_udc =  (CheckBox) findViewById(R.id.voltamp_udc);
        voltamp_udc.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_udc);

        voltamp_upeak =  (CheckBox) findViewById(R.id.voltamp_upeak);
        voltamp_upeak.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_upeak);

        voltamp_upeak1 =  (CheckBox) findViewById(R.id.voltamp_upeak1);
        voltamp_upeak1.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_upeak1);

        voltamp_vrms =  (CheckBox) findViewById(R.id.voltamp_vrms);
        voltamp_vrms.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_vrms);

        voltamp_vdc =  (CheckBox) findViewById(R.id.voltamp_vdc);
        voltamp_vdc.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_vdc);

        voltamp_vpeak =  (CheckBox) findViewById(R.id.voltamp_vpeak);
        voltamp_vpeak.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_vpeak);

        voltamp_vpeak1 =  (CheckBox) findViewById(R.id.voltamp_vpeak1);
        voltamp_vpeak1.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_vpeak1);

        voltamp_arms =  (CheckBox) findViewById(R.id.voltamp_arms);
        voltamp_arms.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_arms);

        voltamp_adc =  (CheckBox) findViewById(R.id.voltamp_adc);
        voltamp_adc.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_adc);

        voltamp_apeak =  (CheckBox) findViewById(R.id.voltamp_apeak);
        voltamp_apeak.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_apeak);

        voltamp_apeak1 =  (CheckBox) findViewById(R.id.voltamp_apeak1);
        voltamp_apeak1.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_apeak1);

        voltamp_ucf =  (CheckBox) findViewById(R.id.voltamp_ucf);
        voltamp_ucf.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_ucf);

        voltamp_vcf =  (CheckBox) findViewById(R.id.voltamp_vcf);
        voltamp_vcf.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_vcf);

        voltamp_acf =  (CheckBox) findViewById(R.id.voltamp_acf);
        voltamp_acf.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_acf);


    }
    private void initEnergyCheckBoxs(){
        Energys = new ArrayList<>();
        energy_kvah = (CheckBox) findViewById(R.id.energy_kvah);
        energy_kvah.setOnCheckedChangeListener(this);
        Energys.add(energy_kvah);

        energy_kvarh = (CheckBox) findViewById(R.id.energy_kvarh);
        energy_kvarh.setOnCheckedChangeListener(this);
        Energys.add(energy_kvarh);

        energy_kwh_forw = (CheckBox) findViewById(R.id.energy_kwh_forw);
        energy_kwh_forw.setOnCheckedChangeListener(this);
        Energys.add(energy_kwh_forw);

        energy_kwh_rev = (CheckBox) findViewById(R.id.energy_kwh_rev);
        energy_kwh_rev.setOnCheckedChangeListener(this);
        Energys.add(energy_kwh_rev);

        energy_kwh = (CheckBox) findViewById(R.id.energy_kwh);
        energy_kwh.setOnCheckedChangeListener(this);
        Energys.add(energy_kwh);

    }
    private void initFlickerCheckBoxs(){
        Flickers = new ArrayList<>();
        flicker_pst1min = (CheckBox) findViewById(R.id.flicker_pst1min);
        flicker_pst1min.setOnCheckedChangeListener(this);
        Flickers.add(flicker_pst1min);
        flicker_pst = (CheckBox) findViewById(R.id.flicker_pst);
        flicker_pst.setOnCheckedChangeListener(this);
        Flickers.add(flicker_pst);
        flicker_plt = (CheckBox) findViewById(R.id.flicker_plt);
        flicker_plt.setOnCheckedChangeListener(this);
        Flickers.add(flicker_plt);
        flicker_pinst = (CheckBox) findViewById(R.id.flicker_pinst);
        flicker_pinst.setOnCheckedChangeListener(this);
        Flickers.add(flicker_pinst);

    }
    private void initFreqCheckBoxs(){
        Frequencys = new ArrayList<>();
        freq_hz = (CheckBox) findViewById(R.id.freq_hz);
        freq_hz.setOnCheckedChangeListener(this);
        Frequencys.add(freq_hz);

    }
    private void initUnbalanceCheckBoxs(){
        Unbalances = new ArrayList<>();
        unbalance_unbal = (CheckBox) findViewById(R.id.unbalance_unbal);
        unbalance_unbal.setOnCheckedChangeListener(this);
        Unbalances.add(unbalance_unbal);
        unbalance_vneg = (CheckBox) findViewById(R.id.unbalance_vneg);
        unbalance_vneg.setOnCheckedChangeListener(this);
        Unbalances.add(unbalance_vneg);
        unbalance_vzero = (CheckBox) findViewById(R.id.unbalance_vzero);
        unbalance_vzero.setOnCheckedChangeListener(this);
        Unbalances.add(unbalance_vzero);
        unbalance_aneg = (CheckBox) findViewById(R.id.unbalance_aneg);
        unbalance_aneg.setOnCheckedChangeListener(this);
        Unbalances.add(unbalance_aneg);
        unbalance_azero = (CheckBox) findViewById(R.id.unbalance_azero);
        unbalance_azero.setOnCheckedChangeListener(this);
        Unbalances.add(unbalance_azero);
    }
    private void initPowerCheckBoxs(){

        Powers = new ArrayList<>();
        power_kw = (CheckBox) findViewById(R.id.power_kw);
        power_kw.setOnCheckedChangeListener(this);
        Powers.add(power_kw);

        power_kva_unb = (CheckBox) findViewById(R.id.power_kva_unb);
        power_kva_unb.setOnCheckedChangeListener(this);
        Powers.add(power_kva_unb);

        power_kva = (CheckBox) findViewById(R.id.power_kva);
        power_kva.setOnCheckedChangeListener(this);
        Powers.add(power_kva);


        power_kw_fund = (CheckBox) findViewById(R.id.power_kw_fund);
        power_kw_fund.setOnCheckedChangeListener(this);
        Powers.add(power_kw_fund);


        power_kvar = (CheckBox) findViewById(R.id.power_kvar);
        power_kvar.setOnCheckedChangeListener(this);
        Powers.add(power_kvar);

        power_kva_fund = (CheckBox) findViewById(R.id.power_kva_fund);
        power_kva_fund.setOnCheckedChangeListener(this);
        Powers.add(power_kva_fund);

        power_kva_harm = (CheckBox) findViewById(R.id.power_kva_harm);
        power_kva_harm.setOnCheckedChangeListener(this);
        Powers.add(power_kva_harm);

        power_w_fund = (CheckBox) findViewById(R.id.power_w_fund);
        power_w_fund.setOnCheckedChangeListener(this);
        Powers.add(power_w_fund);

        power_cos = (CheckBox) findViewById(R.id.power_cos);
        power_cos.setOnCheckedChangeListener(this);
        Powers.add(power_cos);
    }
    private void initVoltHarmonicCheckBoxs(){
        VoltHarmonics = new ArrayList<>();
        voltharmonic_vfund = (CheckBox) findViewById(R.id.voltharmonic_vfund);
        voltharmonic_vfund.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_vfund);
        voltharmonic_thdf = (CheckBox) findViewById(R.id.voltharmonic_thdf);
        voltharmonic_thdf.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_thdf);
        voltharmonic_thdr = (CheckBox) findViewById(R.id.voltharmonic_thdr);
        voltharmonic_thdr.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_thdr);
        voltharmonic_dc = (CheckBox) findViewById(R.id.voltharmonic_dc);
        voltharmonic_dc.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_dc);

        voltharmonic_h1 = (CheckBox) findViewById(R.id.voltharmonic_h1);
        voltharmonic_h1.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h1);
        voltharmonic_h2 = (CheckBox) findViewById(R.id.voltharmonic_h2);
        voltharmonic_h2.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h2);
        voltharmonic_h3 = (CheckBox) findViewById(R.id.voltharmonic_h3);
        voltharmonic_h3.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h3);
        voltharmonic_h4 = (CheckBox) findViewById(R.id.voltharmonic_h4);
        voltharmonic_h4.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h4);
        voltharmonic_h5 = (CheckBox) findViewById(R.id.voltharmonic_h5);
        voltharmonic_h5.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h5);

        voltharmonic_h6 = (CheckBox) findViewById(R.id.voltharmonic_h6);
        voltharmonic_h6.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h6);
        voltharmonic_h7 = (CheckBox) findViewById(R.id.voltharmonic_h7);
        voltharmonic_h7.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h7);
        voltharmonic_h8 = (CheckBox) findViewById(R.id.voltharmonic_h8);
        voltharmonic_h8.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h8);
        voltharmonic_h9 = (CheckBox) findViewById(R.id.voltharmonic_h9);
        voltharmonic_h9.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h9);
        voltharmonic_h10 = (CheckBox) findViewById(R.id.voltharmonic_h10);
        voltharmonic_h10.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h10);

        voltharmonic_h11 = (CheckBox) findViewById(R.id.voltharmonic_h11);
        voltharmonic_h11.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h11);
        voltharmonic_h12 = (CheckBox) findViewById(R.id.voltharmonic_h12);
        voltharmonic_h12.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h12);
        voltharmonic_h13 = (CheckBox) findViewById(R.id.voltharmonic_h13);
        voltharmonic_h13.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h13);
        voltharmonic_h14 = (CheckBox) findViewById(R.id.voltharmonic_h14);
        voltharmonic_h14.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h14);
        voltharmonic_h15 = (CheckBox) findViewById(R.id.voltharmonic_h15);
        voltharmonic_h15.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h15);

        voltharmonic_h16 = (CheckBox) findViewById(R.id.voltharmonic_h16);
        voltharmonic_h16.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h16);
        voltharmonic_h17 = (CheckBox) findViewById(R.id.voltharmonic_h17);
        voltharmonic_h17.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h17);
        voltharmonic_h18 = (CheckBox) findViewById(R.id.voltharmonic_h18);
        voltharmonic_h18.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h18);
        voltharmonic_h19 = (CheckBox) findViewById(R.id.voltharmonic_h19);
        voltharmonic_h19.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h19);
        voltharmonic_h20 = (CheckBox) findViewById(R.id.voltharmonic_h20);
        voltharmonic_h20.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h20);

        voltharmonic_h21 = (CheckBox) findViewById(R.id.voltharmonic_h21);
        voltharmonic_h21.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h21);
        voltharmonic_h22 = (CheckBox) findViewById(R.id.voltharmonic_h22);
        voltharmonic_h22.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h22);
        voltharmonic_h23 = (CheckBox) findViewById(R.id.voltharmonic_h23);
        voltharmonic_h23.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h23);
        voltharmonic_h24 = (CheckBox) findViewById(R.id.voltharmonic_h24);
        voltharmonic_h24.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h24);
        voltharmonic_h25 = (CheckBox) findViewById(R.id.voltharmonic_h25);
        voltharmonic_h25.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h25);

        voltharmonic_h26 = (CheckBox) findViewById(R.id.voltharmonic_h26);
        voltharmonic_h26.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h26);
        voltharmonic_h27 = (CheckBox) findViewById(R.id.voltharmonic_h27);
        voltharmonic_h27.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h27);
        voltharmonic_h28 = (CheckBox) findViewById(R.id.voltharmonic_h28);
        voltharmonic_h28.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h28);
        voltharmonic_h29 = (CheckBox) findViewById(R.id.voltharmonic_h29);
        voltharmonic_h29.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h29);
        voltharmonic_h30 = (CheckBox) findViewById(R.id.voltharmonic_h30);
        voltharmonic_h30.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h30);


        voltharmonic_h31 = (CheckBox) findViewById(R.id.voltharmonic_h31);
        voltharmonic_h31.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h31);
        voltharmonic_h32 = (CheckBox) findViewById(R.id.voltharmonic_h32);
        voltharmonic_h32.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h32);
        voltharmonic_h33 = (CheckBox) findViewById(R.id.voltharmonic_h33);
        voltharmonic_h33.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h33);
        voltharmonic_h34 = (CheckBox) findViewById(R.id.voltharmonic_h34);
        voltharmonic_h34.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h34);
        voltharmonic_h35 = (CheckBox) findViewById(R.id.voltharmonic_h35);
        voltharmonic_h35.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h35);

        voltharmonic_h36 = (CheckBox) findViewById(R.id.voltharmonic_h36);
        voltharmonic_h36.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h36);
        voltharmonic_h37 = (CheckBox) findViewById(R.id.voltharmonic_h37);
        voltharmonic_h37.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h37);
        voltharmonic_h38 = (CheckBox) findViewById(R.id.voltharmonic_h38);
        voltharmonic_h38.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h38);
        voltharmonic_h39 = (CheckBox) findViewById(R.id.voltharmonic_h39);
        voltharmonic_h39.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h39);
        voltharmonic_h40 = (CheckBox) findViewById(R.id.voltharmonic_h40);
        voltharmonic_h40.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h40);

        voltharmonic_h41 = (CheckBox) findViewById(R.id.voltharmonic_h41);
        voltharmonic_h41.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h41);
        voltharmonic_h42 = (CheckBox) findViewById(R.id.voltharmonic_h42);
        voltharmonic_h42.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h42);
        voltharmonic_h43 = (CheckBox) findViewById(R.id.voltharmonic_h43);
        voltharmonic_h43.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h43);
        voltharmonic_h44 = (CheckBox) findViewById(R.id.voltharmonic_h44);
        voltharmonic_h44.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h44);
        voltharmonic_h45 = (CheckBox) findViewById(R.id.voltharmonic_h45);
        voltharmonic_h45.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h45);

        voltharmonic_h46 = (CheckBox) findViewById(R.id.voltharmonic_h46);
        voltharmonic_h46.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h46);
        voltharmonic_h47 = (CheckBox) findViewById(R.id.voltharmonic_h47);
        voltharmonic_h47.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h47);
        voltharmonic_h48 = (CheckBox) findViewById(R.id.voltharmonic_h48);
        voltharmonic_h48.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h48);
        voltharmonic_h49 = (CheckBox) findViewById(R.id.voltharmonic_h49);
        voltharmonic_h49.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h49);
        voltharmonic_h50 = (CheckBox) findViewById(R.id.voltharmonic_h50);
        voltharmonic_h50.setOnCheckedChangeListener(this);
        VoltHarmonics.add(voltharmonic_h50);

    }
    private void initAmpHarmonicCheckBoxs(){
        AmpHarmonics = new ArrayList<>();
        ampharmonic_afund = (CheckBox) findViewById(R.id.ampharmonic_afund);
        ampharmonic_afund.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_afund);
        ampharmonic_thdf = (CheckBox) findViewById(R.id.ampharmonic_thdf);
        ampharmonic_thdf.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_thdf);
        ampharmonic_thdr = (CheckBox) findViewById(R.id.ampharmonic_thdr);
        ampharmonic_thdr.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_thdr);
        ampharmonic_dc = (CheckBox) findViewById(R.id.ampharmonic_dc);
        ampharmonic_dc.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_dc);

        ampharmonic_h1 = (CheckBox) findViewById(R.id.ampharmonic_h1);
        ampharmonic_h1.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h1);
        ampharmonic_h2 = (CheckBox) findViewById(R.id.ampharmonic_h2);
        ampharmonic_h2.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h2);
        ampharmonic_h3 = (CheckBox) findViewById(R.id.ampharmonic_h3);
        ampharmonic_h3.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h3);
        ampharmonic_h4 = (CheckBox) findViewById(R.id.ampharmonic_h4);
        ampharmonic_h4.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h4);
        ampharmonic_h5 = (CheckBox) findViewById(R.id.ampharmonic_h5);
        ampharmonic_h5.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h5);

        ampharmonic_h6 = (CheckBox) findViewById(R.id.ampharmonic_h6);
        ampharmonic_h6.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h6);
        ampharmonic_h7 = (CheckBox) findViewById(R.id.ampharmonic_h7);
        ampharmonic_h7.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h7);
        ampharmonic_h8 = (CheckBox) findViewById(R.id.ampharmonic_h8);
        ampharmonic_h8.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h8);
        ampharmonic_h9 = (CheckBox) findViewById(R.id.ampharmonic_h9);
        ampharmonic_h9.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h9);
        ampharmonic_h10 = (CheckBox) findViewById(R.id.ampharmonic_h10);
        ampharmonic_h10.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h10);

        ampharmonic_h11 = (CheckBox) findViewById(R.id.ampharmonic_h11);
        ampharmonic_h11.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h11);
        ampharmonic_h12 = (CheckBox) findViewById(R.id.ampharmonic_h12);
        ampharmonic_h12.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h12);
        ampharmonic_h13 = (CheckBox) findViewById(R.id.ampharmonic_h13);
        ampharmonic_h13.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h13);
        ampharmonic_h14 = (CheckBox) findViewById(R.id.ampharmonic_h14);
        ampharmonic_h14.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h14);
        ampharmonic_h15 = (CheckBox) findViewById(R.id.ampharmonic_h15);
        ampharmonic_h15.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h15);

        ampharmonic_h16 = (CheckBox) findViewById(R.id.ampharmonic_h16);
        ampharmonic_h16.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h16);
        ampharmonic_h17 = (CheckBox) findViewById(R.id.ampharmonic_h17);
        ampharmonic_h17.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h17);
        ampharmonic_h18 = (CheckBox) findViewById(R.id.ampharmonic_h18);
        ampharmonic_h18.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h18);
        ampharmonic_h19 = (CheckBox) findViewById(R.id.ampharmonic_h19);
        ampharmonic_h19.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h19);
        ampharmonic_h20 = (CheckBox) findViewById(R.id.ampharmonic_h20);
        ampharmonic_h20.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h20);

        ampharmonic_h21 = (CheckBox) findViewById(R.id.ampharmonic_h21);
        ampharmonic_h21.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h21);
        ampharmonic_h22 = (CheckBox) findViewById(R.id.ampharmonic_h22);
        ampharmonic_h22.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h22);
        ampharmonic_h23 = (CheckBox) findViewById(R.id.ampharmonic_h23);
        ampharmonic_h23.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h23);
        ampharmonic_h24 = (CheckBox) findViewById(R.id.ampharmonic_h24);
        ampharmonic_h24.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h24);
        ampharmonic_h25 = (CheckBox) findViewById(R.id.ampharmonic_h25);
        ampharmonic_h25.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h25);

        ampharmonic_h26 = (CheckBox) findViewById(R.id.ampharmonic_h26);
        ampharmonic_h26.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h26);
        ampharmonic_h27 = (CheckBox) findViewById(R.id.ampharmonic_h27);
        ampharmonic_h27.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h27);
        ampharmonic_h28 = (CheckBox) findViewById(R.id.ampharmonic_h28);
        ampharmonic_h28.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h28);
        ampharmonic_h29 = (CheckBox) findViewById(R.id.ampharmonic_h29);
        ampharmonic_h29.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h29);
        ampharmonic_h30 = (CheckBox) findViewById(R.id.ampharmonic_h30);
        ampharmonic_h30.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h30);

        ampharmonic_h31 = (CheckBox) findViewById(R.id.ampharmonic_h31);
        ampharmonic_h31.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h31);
        ampharmonic_h32 = (CheckBox) findViewById(R.id.ampharmonic_h32);
        ampharmonic_h32.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h32);
        ampharmonic_h33 = (CheckBox) findViewById(R.id.ampharmonic_h33);
        ampharmonic_h33.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h33);
        ampharmonic_h34 = (CheckBox) findViewById(R.id.ampharmonic_h34);
        ampharmonic_h34.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h34);
        ampharmonic_h35 = (CheckBox) findViewById(R.id.ampharmonic_h35);
        ampharmonic_h35.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h35);

        ampharmonic_h36 = (CheckBox) findViewById(R.id.ampharmonic_h36);
        ampharmonic_h36.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h36);
        ampharmonic_h37 = (CheckBox) findViewById(R.id.ampharmonic_h37);
        ampharmonic_h37.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h37);
        ampharmonic_h38 = (CheckBox) findViewById(R.id.ampharmonic_h38);
        ampharmonic_h38.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h38);
        ampharmonic_h39 = (CheckBox) findViewById(R.id.ampharmonic_h39);
        ampharmonic_h39.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h39);
        ampharmonic_h40 = (CheckBox) findViewById(R.id.ampharmonic_h40);
        ampharmonic_h40.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h40);

        ampharmonic_h41 = (CheckBox) findViewById(R.id.ampharmonic_h41);
        ampharmonic_h41.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h41);
        ampharmonic_h42 = (CheckBox) findViewById(R.id.ampharmonic_h42);
        ampharmonic_h42.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h42);
        ampharmonic_h43 = (CheckBox) findViewById(R.id.ampharmonic_h43);
        ampharmonic_h43.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h43);
        ampharmonic_h44 = (CheckBox) findViewById(R.id.ampharmonic_h44);
        ampharmonic_h44.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h44);
        ampharmonic_h45 = (CheckBox) findViewById(R.id.ampharmonic_h45);
        ampharmonic_h45.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h45);

        ampharmonic_h46 = (CheckBox) findViewById(R.id.ampharmonic_h46);
        ampharmonic_h46.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h46);
        ampharmonic_h47 = (CheckBox) findViewById(R.id.ampharmonic_h47);
        ampharmonic_h47.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h47);
        ampharmonic_h48 = (CheckBox) findViewById(R.id.ampharmonic_h48);
        ampharmonic_h48.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h48);
        ampharmonic_h49 = (CheckBox) findViewById(R.id.ampharmonic_h49);
        ampharmonic_h49.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h49);
        ampharmonic_h50 = (CheckBox) findViewById(R.id.ampharmonic_h50);
        ampharmonic_h50.setOnCheckedChangeListener(this);
        AmpHarmonics.add(ampharmonic_h50);



    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setCheckBoxStatu((CheckBox)buttonView,isChecked);
    }
    private byte[] meterCommand;
    public byte[] getRecordCommand(){
        return meterCommand;
    }

    private boolean checkIsNO(){

        for (int i = 0; i <Energys.size() ; i++) {
            if (Energys.get(i).isChecked()){
                return false;
            }
        }
        for (int i = 0; i <VoltAmps.size() ; i++) {
            if (VoltAmps.get(i).isChecked()){
                return false;
            }
        }

        for (int i = 0; i <Powers.size() ; i++) {
            if (Powers.get(i).isChecked()){
                return false;
            }
        }
        for (int i = 0; i <Flickers.size() ; i++) {
            if (Flickers.get(i).isChecked()){
                return false;
            }
        }
        for (int i = 0; i <Unbalances.size() ; i++) {
            if (Unbalances.get(i).isChecked()){
                return false;
            }
        }
        for (int i = 0; i <VoltHarmonics.size() ; i++) {
            if (VoltHarmonics.get(i).isChecked()){
                return false;
            }
        }
        for (int i = 0; i <AmpHarmonics.size() ; i++) {
            if (AmpHarmonics.get(i).isChecked()){
                return false;
            }
        }
        for (int i = 0; i <Frequencys.size() ; i++) {
            if (Frequencys.get(i).isChecked()){
                return false;
            }
        }
        return true;
    }

    private void setCheckBoxStatu(CheckBox checkBox,boolean isCheked){
        if (!isCheked){
            if (checkIsNO()){
                meterCommand = null;
            }
            return;
        }
        if (Powers.contains(checkBox)){
            setCheckBoxsToUn(Flickers);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(VoltHarmonics);
            setCheckBoxsToUn(AmpHarmonics);
            setCheckBoxsToUn(Frequencys);
            setCheckBoxsToUn(Energys);
            setCheckBoxsToUn(VoltAmps);
            meterCommand = RecordCommand.Record_Command_Power;

        }else if (Energys.contains(checkBox)){

            setCheckBoxsToUn(Flickers);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(VoltHarmonics);
            setCheckBoxsToUn(AmpHarmonics);
            setCheckBoxsToUn(Frequencys);
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(VoltAmps);
            meterCommand = RecordCommand.Record_Command_Energy;

        }else if (VoltAmps.contains(checkBox)){
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(VoltHarmonics);
            setCheckBoxsToUn(AmpHarmonics);
            setCheckBoxsToUn(Frequencys);
            setCheckBoxsToUn(Flickers);
            setCheckBoxsToUn(Energys);

            meterCommand = RecordCommand.Record_Command_VoltAmp;
        }else if (Flickers.contains(checkBox)){
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(VoltHarmonics);
            setCheckBoxsToUn(AmpHarmonics);
            setCheckBoxsToUn(Frequencys);
            setCheckBoxsToUn(Energys);
            setCheckBoxsToUn(VoltAmps);

            meterCommand = RecordCommand.Record_Command_Flicker;
        }else if (Unbalances.contains(checkBox)){
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(Flickers);
            setCheckBoxsToUn(VoltHarmonics);
            setCheckBoxsToUn(AmpHarmonics);
            setCheckBoxsToUn(Frequencys);
            setCheckBoxsToUn(Energys);
            setCheckBoxsToUn(VoltAmps);
            meterCommand = RecordCommand.Record_Command_Unbalance;
        }else if (VoltHarmonics.contains(checkBox)){
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(Flickers);
            setCheckBoxsToUn(AmpHarmonics);
            setCheckBoxsToUn(Frequencys);
            setCheckBoxsToUn(Energys);
            setCheckBoxsToUn(VoltAmps);
            meterCommand = RecordCommand.Record_Command_VoltHarmonic;
        }else if (AmpHarmonics.contains(checkBox)){
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(VoltHarmonics);
            setCheckBoxsToUn(Flickers);
            setCheckBoxsToUn(Frequencys);
            setCheckBoxsToUn(Energys);
            setCheckBoxsToUn(VoltAmps);
            meterCommand = RecordCommand.Record_Command_AmpHarmonic;
        }else if (Frequencys.contains(checkBox)){
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(VoltHarmonics);
            setCheckBoxsToUn(Flickers);
            setCheckBoxsToUn(AmpHarmonics);
            setCheckBoxsToUn(Energys);
            setCheckBoxsToUn(VoltAmps);
            meterCommand = RecordCommand.Record_Command_Frequency;
        }
    }

    private void setCheckBoxsToUn(List<CheckBox> checkBoxes){
        for (int i = 0; i < checkBoxes.size(); i++) {
            checkBoxes.get(i).setChecked(false);
        }
    }

    public ArrayList<Integer> getCheckIndex(){
        return checkIndex;
    }

    public ArrayList<String> getCheckName(){
        checkName.clear();
        checkIndex.clear();
        for (int i = 0; i < Frequencys.size(); i++) {
            CheckBox checkBox = Frequencys.get(i);
            if (checkBox.isChecked()) {
                checkName.add(checkBox.getText().toString());
                checkIndex.add(i);
            }
        }

        for (int i = 0; i < Powers.size(); i++) {
            CheckBox checkBox = Powers.get(i);
            if (checkBox.isChecked()) {
                checkName.add(checkBox.getText().toString());
                checkIndex.add(i);
            }
        }
        for (int i = 0; i < Flickers.size(); i++) {
            CheckBox checkBox = Flickers.get(i);
            if (checkBox.isChecked()) {
                checkName.add(checkBox.getText().toString());
                checkIndex.add(i);
            }
        }
        for (int i = 0; i < Unbalances.size(); i++) {
            CheckBox checkBox = Unbalances.get(i);
            if (checkBox.isChecked()){
                checkName.add(checkBox.getText().toString());
                checkIndex.add(i);
            }
        }
        for (int i = 0; i < VoltHarmonics.size(); i++) {
            CheckBox checkBox = VoltHarmonics.get(i);
            if (checkBox.isChecked()) {
                checkName.add(checkBox.getText().toString());
                checkIndex.add(i);
            }
        }
        for (int i = 0; i < AmpHarmonics.size(); i++) {
            CheckBox checkBox = AmpHarmonics.get(i);
            if (checkBox.isChecked()){
                checkName.add(checkBox.getText().toString());
                checkIndex.add(i);
            }
        }

        for (int i = 0; i < Energys.size(); i++) {
            CheckBox checkBox = Energys.get(i);
            if (checkBox.isChecked()){
                checkName.add(checkBox.getText().toString());
                checkIndex.add(i);
            }
        }
        for (int i = 0; i < VoltAmps.size(); i++) {
            CheckBox checkBox = VoltAmps.get(i);
            if (checkBox.isChecked()){
                checkName.add(checkBox.getText().toString());
                checkIndex.add(i);
            }
        }
        return checkName;
    }



    public void selectAllParaMeter() {
        switch (rightIndex){
            case 0:
                isAllChecked = !isAllChecked;
                voltamp_urms.setChecked(isAllChecked);
                voltamp_vrms.setChecked(isAllChecked);
                voltamp_arms.setChecked(isAllChecked);
                voltamp_ucf.setChecked(isAllChecked);
                voltamp_udc.setChecked(isAllChecked);
                voltamp_vdc.setChecked(isAllChecked);
                voltamp_adc.setChecked(isAllChecked);
                voltamp_vcf.setChecked(isAllChecked);
                voltamp_upeak.setChecked(isAllChecked);
                voltamp_vpeak.setChecked(isAllChecked);
                voltamp_apeak.setChecked(isAllChecked);
                voltamp_acf.setChecked(isAllChecked);
                voltamp_upeak1.setChecked(isAllChecked);
                voltamp_vpeak1.setChecked(isAllChecked);
                voltamp_apeak1.setChecked(isAllChecked);
                break;
            case 1:
                isAllChecked = !isAllChecked;
                flicker_pst1min.setChecked(isAllChecked);
                flicker_pst.setChecked(isAllChecked);
                flicker_plt.setChecked(isAllChecked);
                flicker_pinst.setChecked(isAllChecked);
                break;
            case 2:
                isAllChecked = !isAllChecked;
                freq_hz.setChecked(isAllChecked);
                break;
            case 3:
                isAllChecked = !isAllChecked;
                voltharmonic_vfund.setChecked(isAllChecked);
                voltharmonic_thdf.setChecked(isAllChecked);
                voltharmonic_thdr.setChecked(isAllChecked);
                voltharmonic_dc.setChecked(isAllChecked);
                voltharmonic_h1.setChecked(isAllChecked);
                voltharmonic_h2.setChecked(isAllChecked);
                voltharmonic_h3.setChecked(isAllChecked);
                voltharmonic_h4.setChecked(isAllChecked);
                voltharmonic_h5.setChecked(isAllChecked);
                voltharmonic_h6.setChecked(isAllChecked);
                voltharmonic_h7.setChecked(isAllChecked);
                voltharmonic_h8.setChecked(isAllChecked);
                voltharmonic_h9.setChecked(isAllChecked);
                voltharmonic_h10.setChecked(isAllChecked);
                voltharmonic_h11.setChecked(isAllChecked);
                voltharmonic_h12.setChecked(isAllChecked);
                voltharmonic_h13.setChecked(isAllChecked);
                voltharmonic_h14.setChecked(isAllChecked);
                voltharmonic_h15.setChecked(isAllChecked);
                voltharmonic_h16.setChecked(isAllChecked);
                voltharmonic_h17.setChecked(isAllChecked);
                voltharmonic_h18.setChecked(isAllChecked);
                voltharmonic_h19.setChecked(isAllChecked);
                voltharmonic_h20.setChecked(isAllChecked);
                voltharmonic_h21.setChecked(isAllChecked);
                voltharmonic_h22.setChecked(isAllChecked);
                voltharmonic_h23.setChecked(isAllChecked);
                voltharmonic_h24.setChecked(isAllChecked);
                voltharmonic_h25.setChecked(isAllChecked);
                voltharmonic_h26.setChecked(isAllChecked);
                voltharmonic_h27.setChecked(isAllChecked);
                voltharmonic_h28.setChecked(isAllChecked);
                voltharmonic_h29.setChecked(isAllChecked);
                voltharmonic_h30.setChecked(isAllChecked);
                voltharmonic_h31.setChecked(isAllChecked);
                voltharmonic_h32.setChecked(isAllChecked);
                voltharmonic_h33.setChecked(isAllChecked);
                voltharmonic_h34.setChecked(isAllChecked);
                voltharmonic_h35.setChecked(isAllChecked);
                voltharmonic_h36.setChecked(isAllChecked);
                voltharmonic_h37.setChecked(isAllChecked);
                voltharmonic_h38.setChecked(isAllChecked);
                voltharmonic_h39.setChecked(isAllChecked);
                voltharmonic_h40.setChecked(isAllChecked);
                voltharmonic_h41.setChecked(isAllChecked);
                voltharmonic_h42.setChecked(isAllChecked);
                voltharmonic_h43.setChecked(isAllChecked);
                voltharmonic_h44.setChecked(isAllChecked);
                voltharmonic_h45.setChecked(isAllChecked);
                voltharmonic_h46.setChecked(isAllChecked);
                voltharmonic_h47.setChecked(isAllChecked);
                voltharmonic_h48.setChecked(isAllChecked);
                voltharmonic_h49.setChecked(isAllChecked);
                voltharmonic_h50.setChecked(isAllChecked);

                break;
            case 4:
                isAllChecked = !isAllChecked;
                ampharmonic_afund.setChecked(isAllChecked);
                ampharmonic_thdf.setChecked(isAllChecked);
                ampharmonic_thdr.setChecked(isAllChecked);
                ampharmonic_dc.setChecked(isAllChecked);
                ampharmonic_h1.setChecked(isAllChecked);
                ampharmonic_h2.setChecked(isAllChecked);
                ampharmonic_h3.setChecked(isAllChecked);
                ampharmonic_h4.setChecked(isAllChecked);
                ampharmonic_h5.setChecked(isAllChecked);
                ampharmonic_h6.setChecked(isAllChecked);
                ampharmonic_h7.setChecked(isAllChecked);
                ampharmonic_h8.setChecked(isAllChecked);
                ampharmonic_h9.setChecked(isAllChecked);
                ampharmonic_h10.setChecked(isAllChecked);
                ampharmonic_h11.setChecked(isAllChecked);
                ampharmonic_h12.setChecked(isAllChecked);
                ampharmonic_h13.setChecked(isAllChecked);
                ampharmonic_h14.setChecked(isAllChecked);
                ampharmonic_h15.setChecked(isAllChecked);
                ampharmonic_h16.setChecked(isAllChecked);
                ampharmonic_h17.setChecked(isAllChecked);
                ampharmonic_h18.setChecked(isAllChecked);
                ampharmonic_h19.setChecked(isAllChecked);
                ampharmonic_h20.setChecked(isAllChecked);
                ampharmonic_h21.setChecked(isAllChecked);
                ampharmonic_h22.setChecked(isAllChecked);
                ampharmonic_h23.setChecked(isAllChecked);
                ampharmonic_h24.setChecked(isAllChecked);
                ampharmonic_h25.setChecked(isAllChecked);
                ampharmonic_h26.setChecked(isAllChecked);
                ampharmonic_h27.setChecked(isAllChecked);
                ampharmonic_h28.setChecked(isAllChecked);
                ampharmonic_h29.setChecked(isAllChecked);
                ampharmonic_h30.setChecked(isAllChecked);
                ampharmonic_h31.setChecked(isAllChecked);
                ampharmonic_h32.setChecked(isAllChecked);
                ampharmonic_h33.setChecked(isAllChecked);
                ampharmonic_h34.setChecked(isAllChecked);
                ampharmonic_h35.setChecked(isAllChecked);
                ampharmonic_h36.setChecked(isAllChecked);
                ampharmonic_h37.setChecked(isAllChecked);
                ampharmonic_h38.setChecked(isAllChecked);
                ampharmonic_h39.setChecked(isAllChecked);
                ampharmonic_h40.setChecked(isAllChecked);
                ampharmonic_h41.setChecked(isAllChecked);
                ampharmonic_h42.setChecked(isAllChecked);
                ampharmonic_h43.setChecked(isAllChecked);
                ampharmonic_h44.setChecked(isAllChecked);
                ampharmonic_h45.setChecked(isAllChecked);
                ampharmonic_h46.setChecked(isAllChecked);
                ampharmonic_h47.setChecked(isAllChecked);
                ampharmonic_h48.setChecked(isAllChecked);
                ampharmonic_h49.setChecked(isAllChecked);
                ampharmonic_h50.setChecked(isAllChecked);

                break;
            case 5:
                isAllChecked = !isAllChecked;
                unbalance_unbal.setChecked(isAllChecked);
                unbalance_vneg.setChecked(isAllChecked);
                unbalance_vzero.setChecked(isAllChecked);
                unbalance_aneg.setChecked(isAllChecked);
                unbalance_azero.setChecked(isAllChecked);
                break;
            case 6:
                isAllChecked = !isAllChecked;
                power_kw.setChecked(isAllChecked);
                power_kva.setChecked(isAllChecked);
                power_kvar.setChecked(isAllChecked);
                power_kva_harm.setChecked(isAllChecked);
                power_cos.setChecked(isAllChecked);
                power_kva_unb.setChecked(isAllChecked);
                power_kw_fund.setChecked(isAllChecked);
                power_kva_fund.setChecked(isAllChecked);
                power_w_fund.setChecked(isAllChecked);
                break;
            case 7:
                isAllChecked = !isAllChecked;
                energy_kvah.setChecked(isAllChecked);
                energy_kwh_forw.setChecked(isAllChecked);
                energy_kwh.setChecked(isAllChecked);
                energy_kvarh.setChecked(isAllChecked);
                energy_kwh_rev.setChecked(isAllChecked);
                break;
        }
    }


}
