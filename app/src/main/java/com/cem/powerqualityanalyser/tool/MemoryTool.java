package com.cem.powerqualityanalyser.tool;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class MemoryTool {

    public static float getFreeProportion(){
        File path=Environment.getExternalStorageDirectory();
        StatFs stat=new StatFs(path.getPath());
        long totalBlacks=stat.getBlockCount();
        long availableBlocks=stat.getAvailableBlocks();
        return availableBlocks  * 100 / totalBlacks;
    }
}
