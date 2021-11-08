package com.cem.powerqualityanalyser.tool;

import android.support.v4.util.Pools;


import com.cem.powerqualityanalyser.databeannew.DataMeterData;
import com.cem.powerqualityanalyser.databean.DataPhaseObj;
import com.cem.powerqualityanalyser.databeannew.DataPhaseObjN;
import com.cem.powerqualityanalyser.sqlBean.DipsSwellsDataBean;
import com.cem.powerqualityanalyser.sqlBean.DipsSwellsMeterData;
import com.cem.powerqualityanalyser.sqlBean.InrushDataBean;
import com.cem.powerqualityanalyser.sqlBean.InrushMeterData;
import com.cem.powerqualityanalyser.sqlBean.SqlDataBean;
import com.cem.powerqualityanalyser.sqlBean.SqlMeterData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeterDataPool {
    private static final  int CTR_REQUEST_BEANS_SPOOL_SIZE = 100;

    private static final  int CTR_REQUEST_BEANS_SPOOL_SIZE2 = 500000;
    private static final  int CTR_REQUEST_BEANS_SPOOL_SIZE3 = 100000;
    private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    //初始化线程池
    private static  Pools.SimplePool<SqlMeterData> sPool =
            new Pools.SimplePool<SqlMeterData>(CTR_REQUEST_BEANS_SPOOL_SIZE2);
    //初始化线程池
    private static  Pools.SimplePool<SqlDataBean> allsPool =
            new Pools.SimplePool<SqlDataBean>(CTR_REQUEST_BEANS_SPOOL_SIZE);
    //初始化线程池
    private static  Pools.SimplePool<DataMeterData> dataPool = new Pools.SimplePool<DataMeterData>(CTR_REQUEST_BEANS_SPOOL_SIZE2);

    private static  Pools.SimplePool<DataPhaseObjN> objNPool = new Pools.SimplePool<DataPhaseObjN>(CTR_REQUEST_BEANS_SPOOL_SIZE3);
    private static  Pools.SimplePool<DataPhaseObj> objPool = new Pools.SimplePool<DataPhaseObj>(CTR_REQUEST_BEANS_SPOOL_SIZE3);


    private static  Pools.SimplePool<InrushMeterData> inrushPool = new Pools.SimplePool<InrushMeterData>(CTR_REQUEST_BEANS_SPOOL_SIZE2);
    private static  Pools.SimplePool<InrushDataBean> allinrushPool = new Pools.SimplePool<InrushDataBean>(CTR_REQUEST_BEANS_SPOOL_SIZE);

    private static  Pools.SimplePool<DipsSwellsMeterData> dipsSwellPool = new Pools.SimplePool<DipsSwellsMeterData>(CTR_REQUEST_BEANS_SPOOL_SIZE2);
    private static  Pools.SimplePool<DipsSwellsDataBean> alldipsSwellPool = new Pools.SimplePool<DipsSwellsDataBean>(CTR_REQUEST_BEANS_SPOOL_SIZE);


    public static void recycle(final DipsSwellsMeterData dipsSwellsMeterData) {
        // Clear state if needed.
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dipsSwellPool.release(dipsSwellsMeterData);
            }
        });
    }

    public static void recycle(final DipsSwellsDataBean dipsSwellsDataBean) {
        // Clear state if needed.
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<DipsSwellsMeterData> dataList = dipsSwellsDataBean.getDataList();
                if (dataList != null){
                    for (DipsSwellsMeterData data: dataList) {
                        try {
                            dipsSwellPool.release(data);
                        }catch (Exception e){
                            log.e(e.getLocalizedMessage());
                        }
                    }
                }

                try {
                    dipsSwellsDataBean.dataList = null;
                    alldipsSwellPool.release(dipsSwellsDataBean);
                }catch (Exception e){
                    log.e(e.getLocalizedMessage());
                }
            }
        });

    }




    public static void recycle(final InrushMeterData inrushMeterData) {
        // Clear state if needed.
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                inrushPool.release(inrushMeterData);
            }
        });
    }

    public static void recycle(final InrushDataBean inrushDataBean) {
        // Clear state if needed.
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<InrushMeterData> dataList = inrushDataBean.getDataList();
                if (dataList != null){
                    for (InrushMeterData data: dataList) {
                        try {
                            inrushPool.release(data);
                        }catch (Exception e){
                            log.e(e.getLocalizedMessage());
                        }
                    }
                }

                try {
                    inrushDataBean.dataList = null;
                    allinrushPool.release(inrushDataBean);
                }catch (Exception e){
                    log.e(e.getLocalizedMessage());
                }
            }
        });

    }

    public static void recycle(final SqlMeterData sqlMeterData) {
        // Clear state if needed.
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                sPool.release(sqlMeterData);
            }
        });
    }
    public static void recycle(final SqlDataBean sqlDataBean) {
        // Clear state if needed.
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<SqlMeterData> dataList = sqlDataBean.getDataList();
                if (dataList != null){
                    for (SqlMeterData data: dataList) {
                        try {
                            sPool.release(data);
                        }catch (Exception e){
                            log.e(e.getLocalizedMessage());
                        }
                    }
                }

                try {
                    sqlDataBean.dataList = null;
                    allsPool.release(sqlDataBean);
                }catch (Exception e){
                    log.e(e.getLocalizedMessage());
                }
            }
        });

    }
    public static void recycle(final DataPhaseObj obj) {
        // Clear state if needed.
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    dataPool.release(obj.getPhaseA());
                    dataPool.release(obj.getPhaseB());
                    dataPool.release(obj.getPhaseC());

                    obj.setPhaseA(null);
                    obj.setPhaseB(null);
                    obj.setPhaseC(null);
                    objPool.release(obj);
                }catch (Exception e){
                    log.e(e.getLocalizedMessage());
                }
            }
        });

    }

    public static void recycle(final DataPhaseObjN objN) {
        // Clear state if needed.
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    dataPool.release(objN.getPhaseA());
                    dataPool.release(objN.getPhaseB());
                    dataPool.release(objN.getPhaseC());
                    dataPool.release(objN.getPhaseN());

                    objN.setPhaseA(null);
                    objN.setPhaseB(null);
                    objN.setPhaseC(null);
                    objN.setPhaseN(null);
                    objNPool.release(objN);
                }catch (Exception e){
                    log.e(e.getLocalizedMessage());
                }
            }
        });

    }

    /**
     * 获取（创建对象）
     * 默认从对象池中获取，拿不到就new
     *
     * @return
     */
    public static SqlMeterData obtainMeter() {
        SqlMeterData instance = sPool.acquire();
        return (instance != null) ? instance : new SqlMeterData();
    }
    public static DataMeterData obtainData() {
        DataMeterData instance = dataPool.acquire();
        return (instance != null) ? instance : new DataMeterData(0);
    }

    public static DataPhaseObjN obtainObjN() {
        DataPhaseObjN instance = objNPool.acquire();
        return (instance != null) ? instance : new DataPhaseObjN();
    }
    public static DataPhaseObj obtainObj() {
        DataPhaseObj instance = objPool.acquire();
        return (instance != null) ? instance : new DataPhaseObj();
    }

    public static InrushMeterData obtainInrushMeter() {
        InrushMeterData instance = inrushPool.acquire();
        return (instance != null) ? instance : new InrushMeterData();
    }

    public static InrushDataBean obtainInrushBean() {
        InrushDataBean instance = allinrushPool.acquire();
        return (instance != null) ? instance : new InrushDataBean();
    }

    public static DipsSwellsDataBean obtainDipsSwellsBean() {
        DipsSwellsDataBean instance = alldipsSwellPool.acquire();
        return (instance != null) ? instance : new DipsSwellsDataBean();
    }
    public static DipsSwellsMeterData obtainDipsSwellsMeter() {
        DipsSwellsMeterData instance = dipsSwellPool.acquire();
        return (instance != null) ? instance : new DipsSwellsMeterData();
    }

    /**
     * 获取（创建对象）
     * 默认从对象池中获取，拿不到就new
     *
     * @return
     */
    public static SqlDataBean obtainBean() {
        SqlDataBean instance = allsPool.acquire();
        return (instance != null) ? instance : new SqlDataBean();
    }




    public void destoryPool() {
        if (sPool != null) {
            sPool = null;
        }
        if (allsPool != null) {
            allsPool = null;
        }
        if (dataPool != null) {
            dataPool = null;
        }
    }
}
