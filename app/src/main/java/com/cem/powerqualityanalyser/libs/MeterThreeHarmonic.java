package com.cem.powerqualityanalyser.libs;

import com.cem.powerqualityanalyser.libsnew.PhaseObj;
import com.cem.powerqualityanalyser.libsnew.PhaseObjN;

public class MeterThreeHarmonic extends BaseMeterData {
    private PhaseObj v_PhaseValue;
    private PhaseObj a_PhaseValue;
    private PhaseObjN v_THD_PhaseValue;
    private PhaseObjN a_THD_PhaseValue;
    private MeterData frequency;
    private GraphDataObj listA_Phase;
    private GraphDataObj listV_Phase;
    private GraphDataObj listW_Phase;
    private PhaseObj v_ValueDC;
    private int byteCount = 2;
    private int dataCount = 50;

    public MeterThreeHarmonic(byte[] bytes) {
        super(bytes);
        this.v_PhaseValue = this.CreatePhaseObj(PhaseType.V_Value, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_PhaseValue = this.CreatePhaseObj(PhaseType.A_Value, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.v_THD_PhaseValue = this.CreatePhaseObj(PhaseType.V_THD, this.getMeterData(), this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_THD_PhaseValue = this.CreatePhaseObj(PhaseType.A_THD, this.getMeterData(), this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.frequency = this.getMeterData();
        this.v_ValueDC = this.CreatePhaseObj(PhaseType.V_DC, this.getMeterData(), this.getMeterData(), this.getMeterData());
        int vEndex = this.dataCount * 3 * 2;
        int aEndex;
        int wEndex;
        int pointB;
        if (this.waveformByteCount > vEndex) {
            aEndex = this.v_PhaseValue.getPhaseA().getDataPoint();
            wEndex = this.v_PhaseValue.getPhaseB().getDataPoint();
            pointB = this.v_PhaseValue.getPhaseC().getDataPoint();
            this.listV_Phase = new GraphDataObj(PhaseType.V_GraphHarmonic, this.waveformByteStart, bytes, this.byteCount, this.dataCount, aEndex, wEndex, pointB);
        }

        aEndex = this.dataCount * 3 * 2 + this.dataCount * 3 * 2;
        if (this.waveformByteCount > aEndex) {
            wEndex = this.a_PhaseValue.getPhaseA().getDataPoint();
            pointB = this.a_PhaseValue.getPhaseB().getDataPoint();
            int pointC = this.a_PhaseValue.getPhaseC().getDataPoint();
            this.listA_Phase = new GraphDataObj(PhaseType.A_GraphHarmonic, this.waveformByteStart + vEndex, bytes, this.byteCount, this.dataCount, wEndex, pointB, pointC);
        }

        wEndex = this.dataCount * 3 * 2 + this.dataCount * 3 * 2 + this.dataCount * 3 * 2;
        if (this.waveformByteCount >= wEndex) {
            this.listW_Phase = new GraphDataObj(PhaseType.W_GraphHarmonic, this.waveformByteStart + aEndex, bytes, this.byteCount, this.dataCount, 2.0F);
        }

    }

    public PhaseObj getV_PhaseValue() {
        return this.v_PhaseValue;
    }

    public PhaseObj getA_PhaseValue() {
        return this.a_PhaseValue;
    }

    public PhaseObjN getV_THD_PhaseValue() {
        return this.v_THD_PhaseValue;
    }

    public PhaseObjN getA_THD_PhaseValue() {
        return this.a_THD_PhaseValue;
    }

    public MeterData getFrequency() {
        return this.frequency;
    }

    public GraphDataObj getListA_Phase() {
        return this.listA_Phase;
    }

    public GraphDataObj getListV_Phase() {
        return this.listV_Phase;
    }

    public GraphDataObj getListW_Phase() {
        return this.listW_Phase;
    }

    public PhaseObj getV_ValueDC() {
        return this.v_ValueDC;
    }

    public int getDataCount() {
        return this.dataCount;
    }
}
