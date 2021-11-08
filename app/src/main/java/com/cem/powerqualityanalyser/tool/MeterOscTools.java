                               package com.cem.powerqualityanalyser.tool;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelLineData;

public class MeterOscTools {

    private float scopeZeroValue = 327.8f;

   /* public static List<List<Float>> getMeterData(int wir_index,int typeIndex, ModelLineData peakup,ModelLineData peakdown,List<ModelLineData> waveform) {
        List<List<Float>> listout = null;
        switch (typeIndex) {
            case 0:
                listout = possVolt4V(peakup,peakdown,waveform);
                break;
            case 1:
                listout = possVolt3U(peakup,peakdown,waveform);
                break;
            case 2:
                listout = possAmp4A(peakup,peakdown,waveform);
                break;
            case 3:
                listout = possScopeL1(peakup,peakdown,waveform);
                break;
            case 4:
                listout = possScopeL2(peakup,peakdown,waveform);
                break;
            case 5:
                listout = possScopeL3(peakup,peakdown,waveform);
                break;
            case 6:
                listout = possScopeLN(peakup,peakdown,waveform);
                break;
        }
        return listout;
    }

*/
    public static List<List<Float>> getMeterData(int wir_index,int typeIndex, List<ModelLineData> waveform) {
        List<List<Float>> listout = null;
        switch (wir_index) {
            case 0:
                switch (typeIndex) {
                    case 0:
                        listout = possVolt4V(waveform.get(14),waveform.get(15),waveform.subList(16,waveform.size()));
                        break;
                    case 1:
                        listout = possVolt3U(waveform.get(14),waveform.get(15),waveform.subList(16,waveform.size()));
                        break;
                    case 2:
                        listout = possAmp4A(waveform.get(14),waveform.get(15),waveform.subList(16,waveform.size()));
                        break;
                    case 3:
                        listout = possScopeL1(waveform.get(14),waveform.get(15),waveform.get(16),waveform.get(17),waveform.subList(18,waveform.size()));
                        break;
                    case 4:
                        listout = possScopeL2(waveform.get(14),waveform.get(15),waveform.get(16),waveform.get(17),waveform.subList(18,waveform.size()));
                        break;
                    case 5:
                        listout = possScopeL3(waveform.get(14),waveform.get(15),waveform.get(16),waveform.get(17),waveform.subList(18,waveform.size()));
                        break;
                    case 6:
                        listout = possScopeLN(waveform.get(14),waveform.get(15),waveform.get(16),waveform.get(17),waveform.subList(18,waveform.size()));
                        break;
                }
            break;
            case 1:


                break;
        }
        return listout;
    }

    private static float maxValueL1,minValueL1,maxValueL2,minValueL2,maxValueL3,minValueL3,maxValueLN,minValueLN;

    private static void getMaxAndMinValue(List<ModelLineData> waveform){
        maxValueL1 = 0;
        minValueL1 = waveform.get(0).getaValue().getValueFl();

        maxValueL2 = 0;
        minValueL2 = waveform.get(0).getbValue().getValueFl();

        maxValueL3 = 0;
        minValueL3 = waveform.get(0).getcValue().getValueFl();

        maxValueLN = 0;
        minValueLN = waveform.get(0).getnValue().getValueFl();

        for (int j = 0; j < waveform.size(); j++) {
            if(waveform.get(j).getaValue()!=null) {
                if (waveform.get(j).getaValue().getValueFl() > maxValueL1)
                    maxValueL1 = waveform.get(j).getaValue().getValueFl();
                if (waveform.get(j).getaValue().getValueFl() < minValueL1)
                    minValueL1 = waveform.get(j).getaValue().getValueFl();
            }
            if(waveform.get(j).getbValue()!=null) {
                if (waveform.get(j).getbValue().getValueFl() > maxValueL2)
                    maxValueL2 = waveform.get(j).getbValue().getValueFl();
                if (waveform.get(j).getbValue().getValueFl() < minValueL2)
                    minValueL2 = waveform.get(j).getbValue().getValueFl();
            }
            if (waveform.get(j).getcValue() != null) {
                if (waveform.get(j).getcValue().getValueFl() > maxValueL3)
                    maxValueL3 = waveform.get(j).getcValue().getValueFl();
                if (waveform.get(j).getcValue().getValueFl() < minValueL3)
                    minValueL3 = waveform.get(j).getcValue().getValueFl();
            }
            if (waveform.get(j).getnValue() != null) {
                if (waveform.get(j).getnValue().getValueFl() > maxValueLN)
                    maxValueLN = waveform.get(j).getnValue().getValueFl();
                if (waveform.get(j).getnValue().getValueFl() < minValueLN)
                    minValueLN = waveform.get(j).getnValue().getValueFl();
            }
        }
    }

    private static float getRelScopeValue(float maxValue,float minValue,float peakMax,float peakMin,float scopeValue){
        float zeroValue = 1093.0f;
        float relScopeValue = 0f;
        if(scopeValue>zeroValue){
            if(maxValue != zeroValue)
                relScopeValue = peakMax * (scopeValue - zeroValue) / (maxValue - zeroValue);
        }else{
            if(minValue != zeroValue)
                relScopeValue = peakMin * (zeroValue - scopeValue)/(zeroValue-minValue);
        }
 //       log.e(scopeValue + "----------==" + relScopeValue);
        return relScopeValue;
    }

    private static float getRelAScopeValue(float maxValue,float minValue,float peakMax,float peakMin,float scopeValue){
        float zeroValue = 3278.0f;
        float relScopeValue = 0f;
        if(scopeValue>zeroValue){
            if(maxValue != zeroValue)
                relScopeValue = peakMax * (scopeValue - zeroValue) / (maxValue - zeroValue);
        }else{
            if(minValue != zeroValue)
                relScopeValue = peakMin * (zeroValue - scopeValue)/(zeroValue-minValue);
        }

//        log.e("--Peak+--" +maxValue + "--Peak-==" + minValue + "--转换前--"+ scopeValue + "---转换后==" + relScopeValue);
        return relScopeValue;
    }

    private static float getRelL1L2L3NVScopeValue(float maxValue,float minValue,float peakMax,float peakMin,float scopeValue){
        float zeroValue = 1093.0f;
        float relScopeValue = 0f;
        if(scopeValue>zeroValue){
            if(maxValue != zeroValue)
                relScopeValue = peakMax * (scopeValue - zeroValue) / (maxValue - zeroValue);
        }else{
            if(minValue != zeroValue)
                relScopeValue = peakMin * (zeroValue - scopeValue)/(zeroValue-minValue);
        }

//        log.e("--max--" +maxValue + "--min--" + minValue + "--scopeValue--"+ scopeValue + "---==" + relScopeValue);
        return relScopeValue;
    }

    private static float getRelL1L2L3NAScopeValue(float maxValue,float minValue,float peakMax,float peakMin,float scopeValue){
        float zeroValue = 1093.0f;
        float relScopeValue = 0f;
        if(scopeValue>zeroValue){
            if(maxValue != zeroValue)
                relScopeValue = peakMax * (scopeValue - zeroValue) / (maxValue - zeroValue);
        }else{
            if(minValue != zeroValue)
                relScopeValue = peakMin * (zeroValue - scopeValue)/(zeroValue-minValue);
        }

 //       log.e("--max--" +maxValue + "--peakMax--" + peakMax + "--scopeValue--"+ scopeValue + "---==" + relScopeValue);
        return relScopeValue;
    }



    private static List<List<Float>> possVolt4V(ModelLineData peakup,ModelLineData peakdown,List<ModelLineData> waveform) {
        List<List<Float>> outList = new ArrayList<>();
        List<Float> floats = new ArrayList<>();
        getMaxAndMinValue(waveform);
        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelScopeValue(maxValueL1,minValueL1,peakup.getaValue().getValueFl(),peakdown.getaValue().getValueFl(),waveform.get(i).getaValue().getValueFl()));
        }
        outList.add(floats);
        floats = new ArrayList<>();

        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelScopeValue(maxValueL2,minValueL2,peakup.getbValue().getValueFl(),peakdown.getbValue().getValueFl(),waveform.get(i).getbValue().getValueFl()));
        }
        outList.add(floats);
        floats = new ArrayList<>();

        for(int i = 0;i< waveform.size();i++){
            if(waveform.get(i).getcValue()!=null)
                floats.add(getRelScopeValue(maxValueL3,minValueL3,peakup.getcValue().getValueFl(),peakdown.getcValue().getValueFl(),waveform.get(i).getcValue().getValueFl()));
            else
                floats.add(0f);
        }
        outList.add(floats);
        floats = new ArrayList<>();

        for(int i = 0;i< waveform.size();i++){
            if(waveform.get(i).getnValue()!=null)
                floats.add(getRelScopeValue(maxValueLN,minValueLN,peakup.getnValue().getValueFl(),peakdown.getnValue().getValueFl(),waveform.get(i).getnValue().getValueFl()));
            else
                floats.add(0f);
        }
        outList.add(floats);
        return outList;
    }

    private static List<List<Float>> possVolt3U(ModelLineData peakup,ModelLineData peakdown,List<ModelLineData> waveform) {
        List<List<Float>> outList = new ArrayList<>();
        List<Float> floats = new ArrayList<>();
        getMaxAndMinValue(waveform);

        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelScopeValue(maxValueL1,minValueL1,peakup.getaValue().getValueFl(),peakdown.getaValue().getValueFl(),waveform.get(i).getaValue().getValueFl()));
        }
        outList.add(floats);
        floats = new ArrayList<>();

        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelScopeValue(maxValueL2,minValueL2,peakup.getbValue().getValueFl(),peakdown.getbValue().getValueFl(),waveform.get(i).getbValue().getValueFl()));
        }
        outList.add(floats);
        floats = new ArrayList<>();

        for(int i = 0;i< waveform.size();i++){
            if(waveform.get(i).getcValue()!=null)
                floats.add(getRelScopeValue(maxValueL3,minValueL3,peakup.getcValue().getValueFl(),peakdown.getcValue().getValueFl(),waveform.get(i).getcValue().getValueFl()));
            else
                floats.add(0f);
        }
        outList.add(floats);;

        return outList;
    }

    private static List<List<Float>> possAmp4A(ModelLineData peakup,ModelLineData peakdown,List<ModelLineData> waveform) {
        List<List<Float>> outList = new ArrayList<>();
        List<Float> floats = new ArrayList<>();
        getMaxAndMinValue(waveform);
        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelAScopeValue(maxValueL1,minValueL1,peakup.getaValue().getValueFl(),peakdown.getaValue().getValueFl(),waveform.get(i).getaValue().getValueFl()));
        }
        outList.add(floats);
        floats = new ArrayList<>();

        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelAScopeValue(maxValueL2,minValueL2,peakup.getbValue().getValueFl(),peakdown.getbValue().getValueFl(),waveform.get(i).getbValue().getValueFl()));
        }
        outList.add(floats);
        floats = new ArrayList<>();

        for(int i = 0;i< waveform.size();i++){
            if(waveform.get(i).getcValue()!=null)
                floats.add(getRelAScopeValue(maxValueL3,minValueL3,peakup.getcValue().getValueFl(),peakdown.getcValue().getValueFl(),waveform.get(i).getcValue().getValueFl()));
            else
                floats.add(0f);
        }
        outList.add(floats);
        floats = new ArrayList<>();

        for(int i = 0;i< waveform.size();i++){
            if(waveform.get(i).getnValue()!=null)
                floats.add(getRelAScopeValue(maxValueLN,minValueLN,peakup.getnValue().getValueFl(),peakdown.getnValue().getValueFl(),waveform.get(i).getnValue().getValueFl()));
            else
                floats.add(0f);
        }
        outList.add(floats);
        return outList;
    }


    private static void getVAMaxAndMinValue(List<ModelLineData> waveform){
        maxValueL1 = 0;
        minValueL1 = waveform.get(0).getaValue().getValueFl();

        maxValueL2 = 0;
        minValueL2 = waveform.get(0).getbValue().getValueFl();

        for (int j = 0; j < waveform.size(); j++) {
            if (waveform.get(j).getaValue().getValueFl() > maxValueL1)
                maxValueL1 = waveform.get(j).getaValue().getValueFl();
            if (waveform.get(j).getaValue().getValueFl() < minValueL1)
                minValueL1 = waveform.get(j).getaValue().getValueFl();

            if (waveform.get(j).getbValue().getValueFl() > maxValueL2)
                maxValueL2 = waveform.get(j).getbValue().getValueFl();
            if (waveform.get(j).getbValue().getValueFl() < minValueL2)
                minValueL2 = waveform.get(j).getbValue().getValueFl();

        }
    }



    private static List<List<Float>> possScopeL1(ModelLineData peakupV,ModelLineData peakdownV,ModelLineData peakupA,ModelLineData peakdownA,List<ModelLineData> waveform) {
        List<List<Float>> outList = new ArrayList<>();
        List<Float> floats = new ArrayList<>();
        getVAMaxAndMinValue(waveform);
        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelL1L2L3NVScopeValue(maxValueL1,minValueL1,peakupV.getaValue().getValueFl(),peakdownV.getaValue().getValueFl(),waveform.get(i).getaValue().getValueFl()));
        }
        outList.add(floats);
        floats = new ArrayList<>();

        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelL1L2L3NAScopeValue(maxValueL2,minValueL2,peakupA.getaValue().getValueFl(),peakdownA.getaValue().getValueFl(),waveform.get(i).getbValue().getValueFl()));
        }
        outList.add(floats);
        return outList;
    }

    private static List<List<Float>> possScopeL2(ModelLineData peakupV,ModelLineData peakdownV,ModelLineData peakupA,ModelLineData peakdownA,List<ModelLineData> waveform) {

        List<List<Float>> outList = new ArrayList<>();
        List<Float> floats = new ArrayList<>();
        getVAMaxAndMinValue(waveform);
        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelL1L2L3NVScopeValue(maxValueL1,minValueL1,peakupV.getbValue().getValueFl(),peakdownV.getbValue().getValueFl(),waveform.get(i).getaValue().getValueFl()));
        }
        outList.add(floats);
        floats = new ArrayList<>();

        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelL1L2L3NAScopeValue(maxValueL2,minValueL2,peakupA.getbValue().getValueFl(),peakdownA.getbValue().getValueFl(),waveform.get(i).getbValue().getValueFl()));
        }
        outList.add(floats);
        return outList;
    }

    private static List<List<Float>> possScopeL3(ModelLineData peakupV,ModelLineData peakdownV,ModelLineData peakupA,ModelLineData peakdownA,List<ModelLineData> waveform) {

        List<List<Float>> outList = new ArrayList<>();
        List<Float> floats = new ArrayList<>();
        getVAMaxAndMinValue(waveform);
        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelL1L2L3NVScopeValue(maxValueL1,minValueL1,peakupV.getcValue().getValueFl(),peakdownV.getcValue().getValueFl(),waveform.get(i).getaValue().getValueFl()));
        }
        outList.add(floats);
        floats = new ArrayList<>();

        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelL1L2L3NAScopeValue(maxValueL2,minValueL2,peakupA.getcValue().getValueFl(),peakdownA.getcValue().getValueFl(),waveform.get(i).getbValue().getValueFl()));
        }
        outList.add(floats);
        return outList;
    }

    private static List<List<Float>> possScopeLN(ModelLineData peakupV,ModelLineData peakdownV,ModelLineData peakupA,ModelLineData peakdownA,List<ModelLineData> waveform) {
        List<List<Float>> outList = new ArrayList<>();
        List<Float> floats = new ArrayList<>();
        getVAMaxAndMinValue(waveform);
        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelL1L2L3NVScopeValue(maxValueL1,minValueL1,peakupV.getnValue().getValueFl(),peakdownV.getnValue().getValueFl(),waveform.get(i).getaValue().getValueFl()));
        }
        outList.add(floats);
        floats = new ArrayList<>();

        for(int i = 0;i< waveform.size();i++){
            floats.add(getRelL1L2L3NAScopeValue(maxValueL2,minValueL2,peakupA.getnValue().getValueFl(),peakdownA.getnValue().getValueFl(),waveform.get(i).getbValue().getValueFl()));
        }
        outList.add(floats);
        return outList;
    }

}
