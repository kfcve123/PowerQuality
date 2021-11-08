package com.cem.powerqualityanalyser.libs;

public class MeterData {
    private float value;
    private String showValue;
    private byte[] sData;
    private int dataPoint;
    private int olValue = 15658734;
    private boolean gElectricity;
    private boolean rElectricity;
    private int rawLen = 4;

    public MeterData(float value) {
        this.setData(value);
    }

    public MeterData(float value, int pointCount, boolean g, boolean r) {
        this.setData(value, pointCount, g, r);
    }

    public MeterData(byte[] bytes) {
        this.sData = bytes;
        float data = (float)Utils_Byte.bytes3ToShort(bytes);
        byte point = (byte)(bytes[this.rawLen - 1] & 0x07);
        int g = bytes[this.rawLen - 1] & 0x10;
        int r = bytes[this.rawLen - 1] & 0x20;
        if (g > 0) {
            this.gElectricity = true;
        }

        if (r > 0) {
            this.rElectricity = true;
        }

        int symbol = bytes[this.rawLen - 1] & 0x80;
        this.dataPoint = point;
        float value = (float)((double)data / Math.pow(10.0D, (double)point));
        if (symbol > 0) {
            value = -value;
        }

        if (data >= (float)this.olValue) {
            value = (float)this.olValue;
        }

        this.setData(value);
    }

    public boolean isGElectricit() {
        return this.gElectricity;
    }

    public boolean isElectricity() {
        return this.rElectricity;
    }

    private void setData(float value) {
        this.value = value;
        this.showValue = this.dataProcess(value);
    }

    private void setData(float value, int pointCount, boolean g, boolean r) {
        this.value = value;
        this.gElectricity = g;
        this.rElectricity = r;
        this.dataPoint = pointCount;
        this.showValue = this.dataProcess(value);
    }

    private String dataProcess(float data) {
        String showData = MeterTools.FormatData(data, this.dataPoint);
        if (data >= (float)this.olValue) {
            showData = "----";
        }

        return showData;
    }

    private int xiaoshudian(float num) {
        num -= (float)((int)num);

        for(int i = 0; i < 10; ++i) {
            num *= 10.0F;
            if (num - (float)((int)num) == 0.0F) {
                return i + 1;
            }
        }

        return 0;
    }

    public float getValue() {
        return this.value;
    }

    public String getShowValue() {
        return this.showValue;
    }

    public byte[] getsData() {
        return this.sData;
    }

    public int getDataPoint() {
        return this.dataPoint;
    }

}
