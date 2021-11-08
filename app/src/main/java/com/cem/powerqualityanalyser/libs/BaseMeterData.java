package com.cem.powerqualityanalyser.libs;

import com.cem.powerqualityanalyser.libsnew.PhaseObj;
import com.cem.powerqualityanalyser.libsnew.PhaseObjN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseMeterData {
    protected int valueByteCount = 4;
    protected int dataValueCount;
    protected int waveformByteStart;
    protected int waveformByteCount;
    protected List<MeterData> dataValue = new ArrayList();
    protected int meterDataIndex = 0;

    public BaseMeterData(byte[] bytes) {
        dataValueCount = bytes[0];
        waveformByteStart = valueByteCount * dataValueCount + 1;
        waveformByteCount = bytes.length - waveformByteStart;
        if (dataValueCount > 0 && bytes.length > dataValueCount * valueByteCount) {
            for(int i = 0; i < dataValueCount; ++i) {
                int startIndex = i * valueByteCount + 1;
                int endIndex = startIndex + valueByteCount;
                byte[] databytes = Arrays.copyOfRange(bytes, startIndex, endIndex);
                MeterData datavalue = new MeterData(databytes);
                dataValue.add(datavalue);
            }
        }

    }

    protected PhaseObj CreatePhaseObj(PhaseType type, MeterData a, MeterData b, MeterData c) {
        PhaseObj obj = new PhaseObj(type, a, b, c);
        return obj;
    }

    protected PhaseObjN CreatePhaseObj(PhaseType type, MeterData a, MeterData b, MeterData c, MeterData n) {
        PhaseObjN obj = new PhaseObjN(type, a, b, c, n);
        return obj;
    }

    protected MeterData getMeterData() {
        MeterData data = null;
        if (dataValue.size() > meterDataIndex) {
            data = dataValue.get(meterDataIndex);
            ++meterDataIndex;
        }

        return data;
    }
}
