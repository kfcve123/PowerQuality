package com.cem.powerqualityanalyser.libs;

public class MeterTools {
    public MeterTools() {
    }

    public static String FormatData(float data, int pointCount) {
        return String.format("%." + pointCount + "f", data);
    }
}
