package com.cem.powerqualityanalyser.tool;

import com.cem.powerqualityanalyser.meterobject.MeterHarmonicObj;

import java.util.ArrayList;
import java.util.List;

/**
 * 谐波
 */
public class MeterHarmonicTool {
    /*public  static   MeterHarmonicObj   getMeterData(int typeIndex,int lineIndex, MeterThreeHarmonic harobj){
        MeterHarmonicObj hobj=null;
        switch (typeIndex){
            case 0:
                hobj=possVolt(lineIndex,harobj);
                hobj.setUnit("V");
                break;
            case 1:
                hobj=possAmp(lineIndex,harobj);
                hobj.setUnit("A");
                break;
            case 2:
                hobj=possWatt(lineIndex,harobj);
                break;
        }
        hobj.setPhaseIndex(lineIndex);
        return  hobj;
    }
    private static MeterHarmonicObj possVolt(int lineIndex, MeterThreeHarmonic harobj){
       return  possHarmonic (lineIndex,harobj.getV_ValueDC(), harobj.getV_THD_PhaseValue(),harobj.getListV_Phase(),harobj.getV_PhaseValue());
    }
    private static MeterHarmonicObj possAmp(int lineIndex, MeterThreeHarmonic harobj){
        return  possHarmonic (lineIndex,null, harobj.getA_THD_PhaseValue(),harobj.getListA_Phase(),harobj.getA_PhaseValue());
    }
    private static MeterHarmonicObj possWatt(int lineIndex, MeterThreeHarmonic harobj){
        return  possHarmonic (lineIndex,harobj.getV_ValueDC(),null,harobj);
    }

    private static List<Float> possGraph(List<Float> floatsV, List<Float> floatsA){
        List<Float> floatsW = new ArrayList<>();
        float max = floatsA.get(0) * floatsV.get(0);
        for (int i = 0; i < floatsV.size(); i++) {
            float v = floatsA.get(i) * floatsV.get(i);
            if (max == 0){
                floatsW.add(0f);
            }else{
                floatsW.add(v/max);
            }
        }
        return floatsW;
    }
    private  static  MeterHarmonicObj possHarmonic(int lineIndex, PhaseObj dcvalue, PhaseObjN obj, MeterThreeHarmonic harobj){
        MeterHarmonicObj hobj=null;
        float thd=-1;
        float dc=-1;
        List<Float> valueGraph =null;
        GraphDataObj graphobjV = harobj.getListV_Phase();
        GraphDataObj graphobjA = harobj.getListA_Phase();
        float rms = -1;
        switch (lineIndex){
            case  0:
                if (obj!=null) {
                    thd = thd2Int(obj.getPhaseA());
                }
                rms = harobj.getV_PhaseValue().getPhaseA().getValue() * harobj.getA_PhaseValue().getPhaseA().getValue();
                valueGraph=possGraph(graphobjV.getValueA(),graphobjA.getValueA());
                if (dcvalue != null)
                    dc=dcvalue.getPhaseA().getValue();
                break;
            case 1:
                if (obj!=null) {
                    thd = thd2Int(obj.getPhaseB());
                }
                rms = harobj.getV_PhaseValue().getPhaseB().getValue() * harobj.getA_PhaseValue().getPhaseB().getValue();
                if (dcvalue != null)
                    dc=dcvalue.getPhaseB().getValue();
                valueGraph=possGraph(graphobjV.getValueB(),graphobjA.getValueB());
                break;
            case 2:
                if (obj!=null) {
                    thd = thd2Int(obj.getPhaseC());
                }
                rms = harobj.getV_PhaseValue().getPhaseC().getValue() * harobj.getA_PhaseValue().getPhaseC().getValue();
                if (dcvalue != null)
                    dc=dcvalue.getPhaseC().getValue();
                valueGraph=possGraph(graphobjV.getValueC(),graphobjA.getValueC());
                break;
            case 3:
                valueGraph=new ArrayList<>();
                valueGraph.add(thd2Int(obj.getPhaseA()));
                valueGraph.add(thd2Int(obj.getPhaseB()));
                valueGraph.add(thd2Int(obj.getPhaseC()));
                valueGraph.add(thd2Int(obj.getPhaseN()));
                break;
        }
        if (valueGraph != null)
            hobj=new MeterHarmonicObj(thd,dc,valueGraph);
        if (rms > 1000){
            rms = rms/1000;
            hobj.setUnit("KW");
        }else{
            hobj.setUnit("W");
        }
        hobj.setRmsValue(rms);
        return  hobj;
    }


    private  static  MeterHarmonicObj possHarmonic(int lineIndex, PhaseObj dcvalue, PhaseObjN obj, GraphDataObj graphobj){
        MeterHarmonicObj hobj=null;
        float thd=-1;
        float dc=-1;
        List<Float> valueGraph =null;
        switch (lineIndex){
            case  0:
                if (obj!=null) {
                    thd = thd2Int(obj.getPhaseA());
                }
                valueGraph=possGraph( graphobj.getValueA());
                if (dcvalue != null)
                    dc=dcvalue.getPhaseA().getValue();
                break;
            case 1:
                if (obj!=null) {
                    thd = thd2Int(obj.getPhaseB());
                }
                if (dcvalue != null)
                    dc=dcvalue.getPhaseB().getValue();
                valueGraph=possGraph( graphobj.getValueB());
                break;
            case 2:
                if (obj!=null) {
                    thd = thd2Int(obj.getPhaseC());
                }
                if (dcvalue != null)
                    dc=dcvalue.getPhaseC().getValue();
                valueGraph=possGraph( graphobj.getValueC());
                break;
            case 3:
                valueGraph=new ArrayList<>();
                valueGraph.add(thd2Int(obj.getPhaseA()));
                valueGraph.add(thd2Int(obj.getPhaseB()));
                valueGraph.add(thd2Int(obj.getPhaseC()));
                valueGraph.add(thd2Int(obj.getPhaseN()));
                break;
        }
        if (valueGraph != null)
            hobj=new MeterHarmonicObj(thd,dc,valueGraph);
        return  hobj;
    }


    private  static  MeterHarmonicObj possHarmonic(int lineIndex, PhaseObj dcvalue, PhaseObjN obj, GraphDataObj graphobj,PhaseObj obj2){
        MeterHarmonicObj hobj=null;
        float thd=-1;
        float dc=-1;
        float rms = -1;
        List<Float> valueGraph =null;
        switch (lineIndex){
            case  0:
                if (obj!=null) {
                    thd = thd2Int(obj.getPhaseA());
                }
                valueGraph=possGraph( graphobj.getValueA());
//                dc=dcvalue.getPhaseA().getValue();
                if (dcvalue != null && valueGraph.size() == 50 && valueGraph.get(0) != 0)
                    dc=Math.abs(dcvalue.getPhaseA().getValue())/graphobj.getValueA().get(0);
                rms = obj2.getPhaseA().getValue();
                break;
            case 1:
                if (obj!=null) {
                    thd = thd2Int(obj.getPhaseB());
                }
                valueGraph=possGraph( graphobj.getValueB());
//                dc=dcvalue.getPhaseB().getValue();
                if (dcvalue != null && valueGraph.size() == 50 && valueGraph.get(0) != 0)
                    dc=Math.abs(dcvalue.getPhaseB().getValue())/graphobj.getValueB().get(0);
                rms = obj2.getPhaseB().getValue();
                break;
            case 2:
                if (obj!=null) {
                    thd = thd2Int(obj.getPhaseC());
                }
                valueGraph=possGraph( graphobj.getValueC());

//                dc=dcvalue.getPhaseC().getValue();
                if (dcvalue != null && valueGraph.size() == 50 && valueGraph.get(0) != 0)
                    dc=Math.abs(dcvalue.getPhaseC().getValue())/graphobj.getValueC().get(0);
                rms = obj2.getPhaseC().getValue();
                break;
            case 3:
                valueGraph=new ArrayList<>();
                valueGraph.add(thd2Int(obj.getPhaseA()));
                valueGraph.add(thd2Int(obj.getPhaseB()));
                valueGraph.add(thd2Int(obj.getPhaseC()));
                valueGraph.add(thd2Int(obj.getPhaseN()));
                break;
        }
        hobj=new MeterHarmonicObj(thd,dc,valueGraph,rms);
        return  hobj;
    }


    private static   float thd2Int(MeterData data){
        return (data.getValue()*100);
    }
    private  static   List<Float> possGraph(List<Float> svalue){
        List<Float> valueGraph =new ArrayList<>();
        float dcValue;
        if (svalue.size()!=0){//处理波形
            float maxvalueA=0;
            int startindex;
            if (svalue.size()==51){
                startindex=1;
                dcValue= svalue.get(0);
            }else
                startindex=0;

            for (int i =startindex;i<svalue.size();i++){
                float value =svalue.get(i);
                if (value>maxvalueA)
                    maxvalueA=value;
            }
            for (int i =startindex;i<svalue.size();i++){
                float value =svalue.get(i);
                if (maxvalueA==0)
                    valueGraph.add(0f);
                else
                    valueGraph.add(value/maxvalueA);
            }

        }
        return valueGraph;
    }*/
}
