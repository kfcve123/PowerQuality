package com.cem.powerqualityanalyser.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.libs.MeterCommand;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.view.RightModeView;

import java.util.ArrayList;
import java.util.List;

public class ParameterSetFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {
    private List<CheckBox> VoltAmps,Powers,Inrushs,Unbalances,Harmonic,DipsAndSweels,Shipboards,Energys;
    private CheckBox power_kw,power_kva,power_kvar,power_kva_harm,power_cos,power_kva_unb,power_kw_fund,power_kva_fund,power_w_fund,
            energy_kvah,energy_kwh_forw,energy_kwh,energy_kvarh,energy_kwh_rev,
            unbalance_vfound,unbalance_afound,unbalance_freq,unbalance_fV,unbalance_fA,
            dips_urms,dips_urms2,dips_freq,
            voltamp_rms,voltamp_peak,voltamp_thdf,voltamp_max,voltamp_dc,voltamp_peak2,voltamp_cf,voltamp_thdr,voltamp_min,voltamp_freq,
            harmonic_urms,harmonic_irms,harmonic_freq,harmonic_vfound,harmonic_afound,harmonic_uthd2_50,harmonic_ithd2_50,harmonic_uthd,harmonic_ithd,
            shipboard_urms,shipboard_irms,shipboard_freq,shipboard_uabc,
            inrush_irms,inrush_irms2,inrush_freq;


    private ViewGroup background_power,background_inrush,background_unbalance,background_harmonic,background_dips,background_shipboard,background_voltamp,background_energy;
    private ArrayList<String> checkName = new ArrayList<>();
    private RightModeView rightModeView;
    private List<RightListViewItemObj> strList;


    @Override
    public int setContentView() {
        return R.layout.fragment_parameterset;
    }

    @Override
    public void onInitView() {

        rightModeView = (RightModeView) findViewById(R.id.parameter_rightview);
        rightModeView.setUpDownClick(false);
        rightModeView.hideUpDownView();
        rightModeView.setTextSize(20);
        rightModeView.setTypeContentTvBg(R.mipmap.parameter_right_choose_off,R.mipmap.parameter_right_choose_on);
        strList =  new ArrayList();

        strList.add(new RightListViewItemObj(getString(R.string.parameter_VoltAmp), -1,20));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_Inrush), -1,20));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_Shipboard), -1,20));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_Harmonic), -1,20));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_DipsSweels), -1,20));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_Unbalance), -1,20));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_Power), -1,20));
        strList.add(new RightListViewItemObj(getString(R.string.parameter_Energy), -1,20));


        rightModeView.setModeList(strList);
        rightModeView.setOnItemCheckCallBack(new RightModeView.OnItemCheckCallBack() {
            @Override
            public void onItemCheck(int item) {
                switch (item){
                    case 0:
                        background_voltamp.setVisibility(View.VISIBLE);
                        background_inrush.setVisibility(View.GONE);
                        background_shipboard.setVisibility(View.GONE);
                        background_harmonic.setVisibility(View.GONE);
                        background_dips.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.GONE);
                        break;
                    case 1:
                        background_voltamp.setVisibility(View.GONE);
                        background_inrush.setVisibility(View.VISIBLE);
                        background_shipboard.setVisibility(View.GONE);
                        background_harmonic.setVisibility(View.GONE);
                        background_dips.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.GONE);

                        break;
                    case 2:
                        background_voltamp.setVisibility(View.GONE);
                        background_inrush.setVisibility(View.GONE);
                        background_shipboard.setVisibility(View.VISIBLE);
                        background_harmonic.setVisibility(View.GONE);
                        background_dips.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.GONE);

                        break;
                    case 3:
                        background_voltamp.setVisibility(View.GONE);
                        background_inrush.setVisibility(View.GONE);
                        background_shipboard.setVisibility(View.GONE);
                        background_harmonic.setVisibility(View.VISIBLE);
                        background_dips.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.GONE);

                        break;
                    case 4:
                        background_voltamp.setVisibility(View.GONE);
                        background_inrush.setVisibility(View.GONE);
                        background_shipboard.setVisibility(View.GONE);
                        background_harmonic.setVisibility(View.GONE);
                        background_dips.setVisibility(View.VISIBLE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.GONE);

                        break;
                    case 5:
                        background_voltamp.setVisibility(View.GONE);
                        background_inrush.setVisibility(View.GONE);
                        background_shipboard.setVisibility(View.GONE);
                        background_harmonic.setVisibility(View.GONE);
                        background_dips.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.VISIBLE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.GONE);

                        break;
                    case 6:
                        background_voltamp.setVisibility(View.GONE);
                        background_inrush.setVisibility(View.GONE);
                        background_shipboard.setVisibility(View.GONE);
                        background_harmonic.setVisibility(View.GONE);
                        background_dips.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.VISIBLE);
                        background_energy.setVisibility(View.GONE);

                        break;
                    case 7:
                        background_voltamp.setVisibility(View.GONE);
                        background_inrush.setVisibility(View.GONE);
                        background_shipboard.setVisibility(View.GONE);
                        background_harmonic.setVisibility(View.GONE);
                        background_dips.setVisibility(View.GONE);
                        background_unbalance.setVisibility(View.GONE);
                        background_power.setVisibility(View.GONE);
                        background_energy.setVisibility(View.VISIBLE);

                        break;



                }
            }
        });

        initVoltAmpCheckBoxs();
        initInrushCheckBoxs();
        initShipboardCheckBoxs();
        initHarmonicCheckBoxs();
        initDipsCheckBoxs();
        initUnbalanceCheckBoxs();
        initPowerCheckBoxs();
        initEnergyCheckBoxs();


        background_inrush = (ViewGroup) this.findViewById(R.id.layout_inrush);
        background_harmonic = (ViewGroup) this.findViewById(R.id.layout_harmonic);
        background_dips = (ViewGroup) this.findViewById(R.id.layout_dips);
        background_shipboard = (ViewGroup) this.findViewById(R.id.layout_shipboard);


        background_voltamp = (ViewGroup) findViewById(R.id.layout_voltamp);


        background_unbalance = (ViewGroup) this.findViewById(R.id.layout_unbalance);
        background_power = (ViewGroup) this.findViewById(R.id.layout_power);
        background_energy = (ViewGroup) findViewById(R.id.layout_energy);

    }

    private void initVoltAmpCheckBoxs() {
        VoltAmps = new ArrayList<>();

        voltamp_rms =  (CheckBox) findViewById(R.id.voltamp_rms);
        voltamp_rms.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_rms);

        voltamp_peak =  (CheckBox) findViewById(R.id.voltamp_peak);
        voltamp_peak.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_peak);

        voltamp_thdf =  (CheckBox) findViewById(R.id.voltamp_thdf);
        voltamp_thdf.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_thdf);

        voltamp_max =  (CheckBox) findViewById(R.id.voltamp_max);
        voltamp_max.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_max);

        voltamp_dc =  (CheckBox) findViewById(R.id.voltamp_dc);
        voltamp_dc.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_dc);

        voltamp_peak2 =  (CheckBox) findViewById(R.id.voltamp_peak2);
        voltamp_peak2.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_peak2);

        voltamp_cf =  (CheckBox) findViewById(R.id.voltamp_cf);
        voltamp_cf.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_cf);

        voltamp_thdr =  (CheckBox) findViewById(R.id.voltamp_thdr);
        voltamp_thdr.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_thdr);

        voltamp_min =  (CheckBox) findViewById(R.id.voltamp_min);
        voltamp_min.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_min);

        voltamp_freq =  (CheckBox) findViewById(R.id.voltamp_freq);
        voltamp_freq.setOnCheckedChangeListener(this);
        VoltAmps.add(voltamp_freq);

    }

    private void initEnergyCheckBoxs(){

        Energys = new ArrayList<>();
        energy_kvah = (CheckBox) findViewById(R.id.energy_kvah);
        energy_kvah.setOnCheckedChangeListener(this);
        Energys.add(energy_kvah);

        energy_kwh_forw = (CheckBox) findViewById(R.id.energy_kwh_forw);
        energy_kwh_forw.setOnCheckedChangeListener(this);
        Energys.add(energy_kwh_forw);

        energy_kwh = (CheckBox) findViewById(R.id.energy_kwh);
        energy_kwh.setOnCheckedChangeListener(this);
        Energys.add(energy_kwh);

        energy_kvarh = (CheckBox) findViewById(R.id.energy_kvarh);
        energy_kvarh.setOnCheckedChangeListener(this);
        Energys.add(energy_kvarh);

        energy_kwh_rev = (CheckBox) findViewById(R.id.energy_kwh_rev);
        energy_kwh_rev.setOnCheckedChangeListener(this);
        Energys.add(energy_kwh_rev);

    }


    private void initInrushCheckBoxs(){
        Inrushs = new ArrayList<>();
        inrush_irms = (CheckBox) findViewById(R.id.inrush_irms);
        inrush_irms.setOnCheckedChangeListener(this);
        Inrushs.add(inrush_irms);
        inrush_irms2 = (CheckBox) findViewById(R.id.inrush_irms2);
        inrush_irms2.setOnCheckedChangeListener(this);
        Inrushs.add(inrush_irms2);
        inrush_freq = (CheckBox) findViewById(R.id.inrush_freq);
        inrush_freq.setOnCheckedChangeListener(this);
        Inrushs.add(inrush_freq);
    }


    private void initShipboardCheckBoxs(){
        Shipboards = new ArrayList<>();
        shipboard_urms = (CheckBox) findViewById(R.id.shipboard_urms);
        shipboard_urms.setOnCheckedChangeListener(this);
        Shipboards.add(shipboard_urms);
        shipboard_irms = (CheckBox) findViewById(R.id.shipboard_irms);
        shipboard_irms.setOnCheckedChangeListener(this);
        Shipboards.add(shipboard_irms);
        shipboard_freq = (CheckBox) findViewById(R.id.shipboard_freq);
        shipboard_freq.setOnCheckedChangeListener(this);
        Shipboards.add(shipboard_freq);
        shipboard_uabc = (CheckBox) findViewById(R.id.shipboard_uabc);
        shipboard_uabc.setOnCheckedChangeListener(this);
        Shipboards.add(shipboard_uabc);
    }

    private void initHarmonicCheckBoxs(){
        Harmonic = new ArrayList<>();
        harmonic_urms = (CheckBox) findViewById(R.id.harmonic_urms);
        harmonic_urms.setOnCheckedChangeListener(this);
        Harmonic.add(harmonic_urms);
        harmonic_irms = (CheckBox) findViewById(R.id.harmonic_irms);
        harmonic_irms.setOnCheckedChangeListener(this);
        Harmonic.add(harmonic_irms);
        harmonic_freq = (CheckBox) findViewById(R.id.harmonic_freq);
        harmonic_freq.setOnCheckedChangeListener(this);
        Harmonic.add(harmonic_freq);
        harmonic_vfound = (CheckBox) findViewById(R.id.harmonic_vfound);
        harmonic_vfound.setOnCheckedChangeListener(this);
        Harmonic.add(harmonic_vfound);
        harmonic_afound = (CheckBox) findViewById(R.id.harmonic_afound);
        harmonic_afound.setOnCheckedChangeListener(this);
        Harmonic.add(harmonic_afound);
        harmonic_uthd2_50 = (CheckBox) findViewById(R.id.harmonic_uthd2_50);
        harmonic_uthd2_50.setOnCheckedChangeListener(this);
        Harmonic.add(harmonic_uthd2_50);
        harmonic_ithd2_50 = (CheckBox) findViewById(R.id.harmonic_ithd2_50);
        harmonic_ithd2_50.setOnCheckedChangeListener(this);
        Harmonic.add(harmonic_ithd2_50);
        harmonic_uthd = (CheckBox) findViewById(R.id.harmonic_uthd);
        harmonic_uthd.setOnCheckedChangeListener(this);
        Harmonic.add(harmonic_uthd);
        harmonic_ithd = (CheckBox) findViewById(R.id.harmonic_ithd);
        harmonic_ithd.setOnCheckedChangeListener(this);
        Harmonic.add(harmonic_ithd);
    }


    private void initDipsCheckBoxs(){
        DipsAndSweels = new ArrayList<>();
        dips_urms = (CheckBox) findViewById(R.id.dips_urms);
        dips_urms.setOnCheckedChangeListener(this);
        DipsAndSweels.add(dips_urms);
        dips_urms2 = (CheckBox) findViewById(R.id.dips_urms2);
        dips_urms2.setOnCheckedChangeListener(this);
        DipsAndSweels.add(dips_urms2);
        dips_freq = (CheckBox) findViewById(R.id.dips_freq);
        dips_freq.setOnCheckedChangeListener(this);
        DipsAndSweels.add(dips_freq);
    }

    private void initUnbalanceCheckBoxs(){
        Unbalances = new ArrayList<>();
        unbalance_vfound = (CheckBox) findViewById(R.id.unbalance_vfound);
        unbalance_vfound.setOnCheckedChangeListener(this);
        Unbalances.add(unbalance_vfound);
        unbalance_afound = (CheckBox) findViewById(R.id.unbalance_afound);
        unbalance_afound.setOnCheckedChangeListener(this);
        Unbalances.add(unbalance_afound);
        unbalance_freq = (CheckBox) findViewById(R.id.unbalance_freq);
        unbalance_freq.setOnCheckedChangeListener(this);
        Unbalances.add(unbalance_freq);
        unbalance_fA = (CheckBox) findViewById(R.id.unbalance_fA);
        unbalance_fA.setOnCheckedChangeListener(this);
        Unbalances.add(unbalance_fA);
        unbalance_fV = (CheckBox) findViewById(R.id.unbalance_fV);
        unbalance_fV.setOnCheckedChangeListener(this);
        Unbalances.add(unbalance_fV);
    }
    private void initPowerCheckBoxs(){

        Powers = new ArrayList<>();
        power_kw = (CheckBox) findViewById(R.id.power_kw);
        power_kw.setOnCheckedChangeListener(this);
        Powers.add(power_kw);

        power_kva = (CheckBox) findViewById(R.id.power_kva);
        power_kva.setOnCheckedChangeListener(this);
        Powers.add(power_kva);

        power_kvar = (CheckBox) findViewById(R.id.power_kvar);
        power_kvar.setOnCheckedChangeListener(this);
        Powers.add(power_kvar);

        power_kva_harm = (CheckBox) findViewById(R.id.power_kva_harm);
        power_kva_harm.setOnCheckedChangeListener(this);
        Powers.add(power_kva_harm);

        power_cos = (CheckBox) findViewById(R.id.power_cos);
        power_cos.setOnCheckedChangeListener(this);
        Powers.add(power_cos);

        power_kva_unb = (CheckBox) findViewById(R.id.power_kva_unb);
        power_kva_unb.setOnCheckedChangeListener(this);
        Powers.add(power_kva_unb);

        power_kw_fund = (CheckBox) findViewById(R.id.power_kw_fund);
        power_kw_fund.setOnCheckedChangeListener(this);
        Powers.add(power_kw_fund);

        power_kva_fund = (CheckBox) findViewById(R.id.power_kva_fund);
        power_kva_fund.setOnCheckedChangeListener(this);
        Powers.add(power_kva_fund);

        power_w_fund = (CheckBox) findViewById(R.id.power_w_fund);
        power_w_fund.setOnCheckedChangeListener(this);
        Powers.add(power_w_fund);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setCheckBoxStatu((CheckBox)buttonView,isChecked);
    }
    private MeterCommand meterCommand;
    public MeterCommand getMeterCommand(){
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
        for (int i = 0; i <Inrushs.size() ; i++) {
            if (Inrushs.get(i).isChecked()){
                return false;
            }
        }
        for (int i = 0; i <Unbalances.size() ; i++) {
            if (Unbalances.get(i).isChecked()){
                return false;
            }
        }
        for (int i = 0; i <Harmonic.size() ; i++) {
            if (Harmonic.get(i).isChecked()){
                return false;
            }
        }
        for (int i = 0; i <DipsAndSweels.size() ; i++) {
            if (DipsAndSweels.get(i).isChecked()){
                return false;
            }
        }
        for (int i = 0; i <Shipboards.size() ; i++) {
            if (Shipboards.get(i).isChecked()){
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
            setCheckBoxsToUn(Inrushs);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(Harmonic);
            setCheckBoxsToUn(DipsAndSweels);
            setCheckBoxsToUn(Shipboards);
            setCheckBoxsToUn(Energys);
            setCheckBoxsToUn(VoltAmps);
            meterCommand = MeterCommand.AllParameter;

        }else if (Energys.contains(checkBox)){
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(Harmonic);
            setCheckBoxsToUn(DipsAndSweels);
            setCheckBoxsToUn(Shipboards);
            setCheckBoxsToUn(Inrushs);
            setCheckBoxsToUn(VoltAmps);

            meterCommand = MeterCommand.AllParameter;

        }else if (VoltAmps.contains(checkBox)){
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(Harmonic);
            setCheckBoxsToUn(DipsAndSweels);
            setCheckBoxsToUn(Shipboards);
            setCheckBoxsToUn(Inrushs);
            setCheckBoxsToUn(Energys);

            meterCommand = MeterCommand.AllParameter;
        }else if (Inrushs.contains(checkBox)){
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(Harmonic);
            setCheckBoxsToUn(DipsAndSweels);
            setCheckBoxsToUn(Shipboards);
            setCheckBoxsToUn(Energys);
            setCheckBoxsToUn(VoltAmps);

            meterCommand = MeterCommand.Surge_A;
        }else if (Unbalances.contains(checkBox)){
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(Inrushs);
            setCheckBoxsToUn(Harmonic);
            setCheckBoxsToUn(DipsAndSweels);
            setCheckBoxsToUn(Shipboards);
            setCheckBoxsToUn(Energys);
            setCheckBoxsToUn(VoltAmps);
            meterCommand = MeterCommand.ThreeUnbalanced;
        }else if (Harmonic.contains(checkBox)){
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(Inrushs);
            setCheckBoxsToUn(DipsAndSweels);
            setCheckBoxsToUn(Shipboards);
            setCheckBoxsToUn(Energys);
            setCheckBoxsToUn(VoltAmps);
            meterCommand = MeterCommand.ThreeHarmonic;
        }else if (DipsAndSweels.contains(checkBox)){
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(Harmonic);
            setCheckBoxsToUn(Inrushs);
            setCheckBoxsToUn(Shipboards);
            setCheckBoxsToUn(Energys);
            setCheckBoxsToUn(VoltAmps);
            meterCommand = MeterCommand.Sudden_UP_Down;
        }else if (Shipboards.contains(checkBox)){
            setCheckBoxsToUn(Powers);
            setCheckBoxsToUn(Unbalances);
            setCheckBoxsToUn(Harmonic);
            setCheckBoxsToUn(Inrushs);
            setCheckBoxsToUn(DipsAndSweels);
            setCheckBoxsToUn(Energys);
            setCheckBoxsToUn(VoltAmps);
            meterCommand = MeterCommand.Hz_400;
        }
    }

    private void setCheckBoxsToUn(List<CheckBox> checkBoxes){
        for (int i = 0; i < checkBoxes.size(); i++) {
            checkBoxes.get(i).setChecked(false);
        }
    }

    public ArrayList<String> getCheckName(){
        checkName.clear();
        for (int i = 0; i < Shipboards.size(); i++) {
            CheckBox checkBox = Shipboards.get(i);
            if (checkBox.isChecked())
                checkName.add(checkBox.getText().toString());
        }

        for (int i = 0; i < Powers.size(); i++) {
            CheckBox checkBox = Powers.get(i);
            if (checkBox.isChecked())
                checkName.add(checkBox.getText().toString());
        }
        for (int i = 0; i < Inrushs.size(); i++) {
            CheckBox checkBox = Inrushs.get(i);
            if (checkBox.isChecked())
                checkName.add(checkBox.getText().toString());
        }
        for (int i = 0; i < Unbalances.size(); i++) {
            CheckBox checkBox = Unbalances.get(i);
            if (checkBox.isChecked())
                checkName.add(checkBox.getText().toString());
        }
        for (int i = 0; i < Harmonic.size(); i++) {
            CheckBox checkBox = Harmonic.get(i);
            if (checkBox.isChecked())
                checkName.add(checkBox.getText().toString());
        }
        for (int i = 0; i < DipsAndSweels.size(); i++) {
            CheckBox checkBox = DipsAndSweels.get(i);
            if (checkBox.isChecked())
                checkName.add(checkBox.getText().toString());
        }

        for (int i = 0; i < Energys.size(); i++) {
            CheckBox checkBox = Energys.get(i);
            if (checkBox.isChecked())
                checkName.add(checkBox.getText().toString());
        }
        for (int i = 0; i < VoltAmps.size(); i++) {
            CheckBox checkBox = VoltAmps.get(i);
            if (checkBox.isChecked())
                checkName.add(checkBox.getText().toString());
        }
        return checkName;
    }
}
