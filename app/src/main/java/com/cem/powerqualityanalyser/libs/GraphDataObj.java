package com.cem.powerqualityanalyser.libs;

import java.util.ArrayList;
import java.util.List;

public class GraphDataObj {
    private List<Float> valueA;
    private List<Float> valueB;
    private List<Float> valueC;
    private PhaseType type;
    private float coefficient = 1.0F;
    private int dataCount;
    private int dataValuePoint = 0;

    public List<Float> getValueA() {
        return this.valueA;
    }

    public List<Float> getValueB() {
        return this.valueB;
    }

    public List<Float> getValueC() {
        return this.valueC;
    }

    public PhaseType getType() {
        return this.type;
    }

    public GraphDataObj(PhaseType type, int startIndex, byte[] bytes, int dataByteLen, int dataCount) {
        this.possGraph(type, startIndex, bytes, dataByteLen, dataCount, this.coefficient, this.dataValuePoint, this.dataValuePoint, this.dataValuePoint);
    }

    public GraphDataObj(PhaseType type, int startIndex, byte[] bytes, int dataByteLen, int dataCount, float coefficient) {
        this.possGraph(type, startIndex, bytes, dataByteLen, dataCount, coefficient, this.dataValuePoint, this.dataValuePoint, this.dataValuePoint);
    }

    public GraphDataObj(PhaseType type, int startIndex, byte[] bytes, int dataByteLen, int dataCount, float coefficient, int point) {
        this.possGraph(type, startIndex, bytes, dataByteLen, dataCount, coefficient, point, point, point);
    }

    public GraphDataObj(PhaseType type, int startIndex, byte[] bytes, int dataByteLen, int dataCount, int pointA, int pointB, int pointC) {
        this.possGraph(type, startIndex, bytes, dataByteLen, dataCount, this.coefficient, pointA, pointB, pointC);
    }

    public GraphDataObj(PhaseType type, int startIndex, byte[] bytes, int dataByteLen, int dataCount, float coefficient, int pointA, int pointB, int pointC) {
        this.possGraph(type, startIndex, bytes, dataByteLen, dataCount, coefficient, pointA, pointB, pointC);
    }

    public void possGraph(PhaseType type, int startIndex, byte[] bytes, int dataByteLen, int dataCount, float coefficient, int pointA, int pointB, int pointC) {
        this.coefficient = coefficient;
        this.valueA = new ArrayList();
        this.valueB = new ArrayList();
        this.valueC = new ArrayList();
        this.dataCount = dataCount;
        this.type = type;
        int aEndIndex = dataByteLen * dataCount + startIndex;
        int bEndIndex = dataByteLen * dataCount + aEndIndex;
        int cEndIndex = dataByteLen * dataCount + bEndIndex;
        this.data2Value(this.valueA, bytes, startIndex, aEndIndex, dataByteLen, pointA);
        this.data2Value(this.valueB, bytes, aEndIndex, bEndIndex, dataByteLen, pointB);
        this.data2Value(this.valueC, bytes, bEndIndex, cEndIndex, dataByteLen, pointC);
    }

    private void data2Value(List<Float> svalue, byte[] bytes, int start, int end, int dataByteLen, int point) {
        for(int i = start; i < end; i += dataByteLen) {
            float value;
            if (dataByteLen == 1) {
                value = (float)(bytes[i] & 255);
            } else {
                value = (float)(bytes[i] << 8 & '\uffff' | bytes[i + 1] & 255);
            }

            value = (float)((double)value / Math.pow(10.0D, (double)point));
            svalue.add(value * this.coefficient);
        }

    }

    public int getDataCount() {
        return this.dataCount;
    }

}
