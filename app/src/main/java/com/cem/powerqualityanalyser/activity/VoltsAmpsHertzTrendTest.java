package com.cem.powerqualityanalyser.activity;


import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.libs.MeterAllParameter;
import com.cem.powerqualityanalyser.libsnew.PhaseObjN;
import com.cem.powerqualityanalyser.libs.PhaseType;
import com.cem.powerqualityanalyser.userobject.MeterGroupListObj;
import com.cem.powerqualityanalyser.view.datalist.MyTableListView;

import serialport.amos.cem.com.libamosserial.ModelAllData;


public class VoltsAmpsHertzTrendTest extends BaseFragmentTrend {
    private MyTableListView stickyLayout;
    private MeterGroupListObj meterTriangleTitle,meterStarTitle,meterAllTitle;



    @Override
    public void onInitViews() {
        stickyLayout = (MyTableListView) findViewById(R.id.sticky_layout);
        meterStarTitle=new MeterGroupListObj();
        meterStarTitle.addHeader(getResources().getStringArray(R.array.abcn_arrat));

        meterTriangleTitle=new MeterGroupListObj();
        meterTriangleTitle.addHeader(getResources().getStringArray(R.array.abc_arrat));

        meterAllTitle = new MeterGroupListObj();
        meterAllTitle.addHeader(getResources().getStringArray(R.array.all_arrat));
   /*     stickyLayout.addItem(meterStarTitle);
        stickyLayout.addItem(meterTriangleTitle);*/

    }


    @Override
    public void setShowMeterData(ModelAllData modelAllData) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int funTypeIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex, boolean showCursor) {

    }

    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {
        MeterAllParameter allParameter = (MeterAllParameter) baseMeterData;
        addMeterData("Vrms人(V)",0,meterStarTitle,allParameter.getV_StarValue()) ;
        addMeterData("Vpk(V)",1,meterStarTitle,allParameter.getV_StarMax()) ;
        addMeterData("CF V",2,meterStarTitle,allParameter.getV_StarCF()) ;
        addMeterData("Freq(Hz)",3,meterStarTitle,allParameter.getFrequency()) ;

        addMeterData("Udc(V)",4,meterStarTitle,allParameter.getV_triangleDCValue()) ;
        addMeterData("Arms(A)",5,meterStarTitle,allParameter.getA_Value()) ;
        addMeterData("Apk(A)",6,meterStarTitle,allParameter.getA_Max()) ;
        addMeterData("CF A",7,meterStarTitle,allParameter.getA_CF()) ;
        addMeterData("ΦV(°)",8,meterStarTitle,allParameter.getV_Angle()) ;
        addMeterData("ΦA(°)",9,meterStarTitle,allParameter.getA_Angle()) ;

        addMeterData("P(KW)",0,meterAllTitle,new PhaseObjN(PhaseType.None,allParameter.getPhase_kw().getPhaseA(),allParameter.getPhase_kw().getPhaseB(),allParameter.getPhase_kw().getPhaseC(),allParameter.getPhase_threekw().getPhaseA()));
        addMeterData("Q(Kvar)",1,meterAllTitle,new PhaseObjN(PhaseType.None,allParameter.getPhase_kvar().getPhaseA(),allParameter.getPhase_kvar().getPhaseB(),allParameter.getPhase_kvar().getPhaseC(),allParameter.getPhase_threekw().getPhaseB())) ;
        addMeterData("S(KVA)",2,meterAllTitle,new PhaseObjN(PhaseType.None,allParameter.getPhase_kva().getPhaseA(),allParameter.getPhase_kva().getPhaseB(),allParameter.getPhase_kva().getPhaseC(),allParameter.getPhase_threekw().getPhaseC())) ;
        addMeterData("Pf",3,meterAllTitle,allParameter.getPhase_Pf()) ;
        addMeterData("cosΦ(°)",4,meterAllTitle,new PhaseObjN(PhaseType.None,null,null,null,allParameter.getAngle_Cos())) ;
        if (allParameter.getV_threeUnbalanced() != null)
            addMeterData("V unbal. (%)",5,meterAllTitle,new PhaseObjN(PhaseType.None,null,null,null,allParameter.getV_threeUnbalanced())) ;
        if (allParameter.getA_threeUnbalanced() != null)
            addMeterData("A unbal. (%)",6,meterAllTitle,new PhaseObjN(PhaseType.None,null,null,null,allParameter.getA_threeUnbalanced())) ;

        addMeterData("Vrms△(V)",0,meterTriangleTitle,allParameter.getV_triangleValue()) ;
        addMeterData("Vpk(V)",1,meterTriangleTitle,allParameter.getV_triangleMax()) ;
        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
              if (stickyLayout.showItemsCount()<1) {
                    stickyLayout.addItem(meterStarTitle);
                  stickyLayout.addItem(meterAllTitle);
                  stickyLayout.addItem(meterTriangleTitle);
                }

                stickyLayout.notifyChildChanged();
            }
        });

    }



}
