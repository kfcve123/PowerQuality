package com.cem.powerqualityanalyser.databean;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cem.powerqualityanalyser.sqlBean.DipsSwellsDataBean;
import com.cem.powerqualityanalyser.sqlBean.DipsSwellsMeterData;
import com.cem.powerqualityanalyser.sqlBean.InrushDataBean;
import com.cem.powerqualityanalyser.sqlBean.InrushMeterData;
import com.cem.powerqualityanalyser.sqlBean.SqlDataBean;
import com.cem.powerqualityanalyser.sqlBean.SqlMeterData;
import com.cem.powerqualityanalyser.tool.MeterDataPool;
import com.cem.powerqualityanalyser.tool.log;


import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DBManager {

    private static ExecutorService singleThreadExecutor;
    private static DBManager dbManager = new DBManager();
    private static SQLiteDatabase database;
    public static DBManager getInstance(){
        return dbManager;
    }
    private DBManager() {
        database = Connector.getDatabase();
        singleThreadExecutor = Executors.newSingleThreadExecutor();
    }

    public void insetMeterData(final String fileName,final List<SqlMeterData> meterDataList, final DBSaveListener dbListener){
                database.beginTransaction();
                Cursor cursor = LitePal.findBySQL("select id from " + SqlDataBean.class.getSimpleName().toLowerCase() + " where filename = " + "'" + fileName + "'");
                if (cursor != null && cursor.moveToFirst()){
                    int id1 = cursor.getColumnIndex("id");
                    int id = cursor.getInt(id1);
                    cursor.close();
                    for (int i = 0; i < meterDataList.size(); i++) {
                        SqlMeterData sqlMeterData = meterDataList.get(i);
                        database.execSQL("insert into sqlmeterdata(modetype,parametertype,phasetype,value,time,sqldatabean_id,datacount,gelectricity,relectricity) values (?,?,?,?,?,?,?,?,?)", new Object[] { sqlMeterData.getModeType(), sqlMeterData.getParameterType(),sqlMeterData.getPhaseType(),sqlMeterData.getValue(),sqlMeterData.getTime().getTime(),id,sqlMeterData.getDataCount(),sqlMeterData.isgElectricity(),sqlMeterData.isrElectricity() });
                    }
                }
                database.setTransactionSuccessful();
                database.endTransaction();
                LitePal.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (dbListener != null)
                            dbListener.onSuccess();
                    }
                });
    }
    public void updateBeanEndDateSync(final int id,final Date date, final DBSaveListener dbListener){
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.beginTransaction();
                database.execSQL("update sqldatabean set enddate=? where id=?", new Object[]{date.getTime(),id});
                database.setTransactionSuccessful();
                database.endTransaction();
                LitePal.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (dbListener != null)
                            dbListener.onSuccess();
                    }
                });
            }
        });
    }
    public void updateBeanEndDate(final int id,final Date date){
                database.beginTransaction();
                database.execSQL("update sqldatabean set enddate=? where id=?", new Object[]{date.getTime(),id});
                database.setTransactionSuccessful();
                database.endTransaction();
    }
    private void findDataCoreTable(final boolean isEager, final String whereString, final DBQueryAllParameterListener dbQueryListener){
//        find(LitePal.where("data_name = ?",name),dbQueryListener);
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.beginTransaction();
                String tableName = SqlDataBean.class.getSimpleName().toLowerCase();
                String tableName2 = SqlMeterData.class.getSimpleName().toLowerCase();

                final List<SqlDataBean> list = new ArrayList<>();
                Cursor cursor = LitePal.findBySQL("select * from " + tableName + whereString);
                if (cursor != null && cursor.moveToFirst()){
                    int id1 = cursor.getColumnIndex("id");
                    int checkparameters = cursor.getColumnIndex("checkparameters");
                    int enddate = cursor.getColumnIndex("enddate");
                    int startdate = cursor.getColumnIndex("startdate");
                    int filename = cursor.getColumnIndex("filename");
                    int modetype = cursor.getColumnIndex("modetype");
                    int devicename = cursor.getColumnIndex("devicename");

                    do {
                        int id = cursor.getInt(id1);
                        String checkParameters = cursor.getString(checkparameters);
                        Date startDate = new Date(cursor.getLong(startdate));
                        Date endDate = new Date(cursor.getLong(enddate));
                        String fileName = cursor.getString(filename);
                        String deviceName = cursor.getString(devicename);
                        int modeType = cursor.getInt(modetype);

                        SqlDataBean sqlDataBean = MeterDataPool.obtainBean();
                        sqlDataBean.setId(id);
                        sqlDataBean.setEndDate(endDate);
                        sqlDataBean.setStartDate(startDate);
                        sqlDataBean.setDeviceName(deviceName);
                        sqlDataBean.setCheckParameters(checkParameters);
                        sqlDataBean.setFileName(fileName);
                        sqlDataBean.setModeType(modeType);
                        list.add(sqlDataBean);
                    }while (cursor.moveToNext());
                    cursor.close();
                }

                if (isEager)
                    for (SqlDataBean bean: list) {
                        List<SqlMeterData> dataList = new ArrayList<>();
                        Cursor cursor2 = LitePal.findBySQL("select * from " + tableName2 + " where " + tableName+"_id = " + bean.getId()   + " order by id asc");
                        long ot = 0;
                        long ct = 0;

                        if (cursor2 != null && cursor2.moveToFirst()){
                            int id1 = cursor2.getColumnIndex("id");
                            int modetype = cursor2.getColumnIndex("modetype");
                            int parametertype = cursor2.getColumnIndex("parametertype");
                            int phasetype = cursor2.getColumnIndex("phasetype");
                            int datacount = cursor2.getColumnIndex("datacount");
                            int value = cursor2.getColumnIndex("value");
                            int time = cursor2.getColumnIndex("time");
                            int gelectricity = cursor2.getColumnIndex("gelectricity");
                            int relectricity = cursor2.getColumnIndex("relectricity");
                            long startTime = System.currentTimeMillis();
                            long l3 = System.currentTimeMillis();
                            long mt = 0;
                            long posstime=System.currentTimeMillis();
                            do {
                               mt += System.currentTimeMillis() - l3;
                                long l2 = System.currentTimeMillis();
                                int id = cursor2.getInt(id1);
                                int modeType = cursor2.getInt(modetype);
                                int parameterType = cursor2.getInt(parametertype);
                                int phaseType = cursor2.getInt(phasetype);
                                int dataCount = cursor2.getInt(datacount);
                                float valueFloat = cursor2.getFloat(value);

                                Date Time = new Date(cursor2.getLong(time));
                                boolean rElectricity = cursor2.getInt(relectricity) == 1;
                                boolean gElectricity = cursor2.getInt(gelectricity) == 1;
                                ct += System.currentTimeMillis() - l2;
                                long l1 = System.currentTimeMillis();

                                SqlMeterData sqlMeterData = MeterDataPool.obtainMeter();
                                sqlMeterData.setId(id);
                                sqlMeterData.setValue(valueFloat);
                                sqlMeterData.setDataCount(dataCount);
                                sqlMeterData.setPhaseType(phaseType);
                                sqlMeterData.setModeType(modeType);
                                sqlMeterData.setParameterType(parameterType);
                                sqlMeterData.setTime(Time);
                                sqlMeterData.setgElectricity(gElectricity);
                                sqlMeterData.setrElectricity(rElectricity);
                                dataList.add(sqlMeterData);
                                ot += System.currentTimeMillis() - l1;
                                l3 = System.currentTimeMillis();

                            }while (cursor2.moveToNext());
                                log.e("查询读取时间"+(System.currentTimeMillis()-posstime));
                                log.e("创建对象耗时："+(ot));
                                log.e("cursor耗时："+(ct));
                                log.e("move耗时"+(mt));
                                log.e("查询2耗时："+(System.currentTimeMillis()-startTime));
                                cursor2.close();
                        }
                        bean.addMeterList(dataList);
                    }

                database.setTransactionSuccessful();
                database.endTransaction();
                LitePal.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        dbQueryListener.onSuccess(list);
                    }
                });
            }
        });
    }


    public void findFromName(final boolean isEager,final String name , final DBQueryAllParameterListener dbQueryListener){
//        find(LitePal.where("data_name = ?",name),dbQueryListener);
        String where = " where filename = '" + name +"'";
    this.findDataCoreTable(isEager,where,dbQueryListener);
}


    public void findFromDate(final boolean isEager,final DBQueryAllParameterListener dbQueryListener, final Date startDate, final Date endDate){
        String where = " where startdate >= " + startDate.getTime() + " and enddate <= " + endDate.getTime();
        this.findDataCoreTable(isEager,where,dbQueryListener);

    }
    /**
     *
     * @param isEager 是否激进查询，true 查询所有关联表以及参数，false 只查询最外层表
     * @param dbQueryListener
     */
    public void findALL(final boolean isEager, final DBQueryAllParameterListener dbQueryListener){
       findDataCoreTable(isEager,"",dbQueryListener);
    }

    public void clearDB(){
        DataSupport.deleteAll(SqlDataBean.class);
        DataSupport.deleteAll(SqlMeterData.class);
    }


    public void deleteFromName(final String name, final DBUpdateOrDeleteListener dbUpdateOrDeleteListener){
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.beginTransaction();

                String tableName = SqlDataBean.class.getSimpleName().toLowerCase();
                final List<SqlDataBean> list = new ArrayList<>();
                Cursor cursor = LitePal.findBySQL("select id from " + tableName + " where filename = '" + name + "'");
                if (cursor != null && cursor.moveToFirst()){
                    int ID = cursor.getColumnIndex("id");
                    do {
                        int anInt = cursor.getInt(ID);
                        LitePal.getDatabase().execSQL("delete from " + SqlMeterData.class.getSimpleName().toLowerCase() + " where " + SqlDataBean.class.getSimpleName().toLowerCase() + "_id = " + anInt);
                        LitePal.getDatabase().execSQL("delete from " + SqlDataBean.class.getSimpleName().toLowerCase() + " where " + " id = " + anInt);
                    }while (cursor.moveToNext());
                    cursor.close();
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                LitePal.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        dbUpdateOrDeleteListener.onFinish();
                    }
                });
            }
        });
    }


    public void delete(final SqlDataBean sqlDataBean, final DBUpdateOrDeleteListener dbUpdateOrDeleteListener){
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (sqlDataBean.isSaved()){
                    database.beginTransaction();
                    int id = sqlDataBean.getId();
                    LitePal.getDatabase().execSQL("delete from " + SqlMeterData.class.getSimpleName().toLowerCase() + " where " + sqlDataBean.getClass().getSimpleName().toLowerCase() + "_id = " + id);
                    sqlDataBean.delete();
                    database.setTransactionSuccessful();
                    database.endTransaction();
                    LitePal.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            dbUpdateOrDeleteListener.onFinish();
                        }
                    });
                }

            }
        });
    }


    public interface DBUpdateOrDeleteListener{
        void onFinish();
    }


    public interface DBQueryAllParameterListener{
        void onFail();
        void onSuccess(List<SqlDataBean> dataMeterAllParameterList);
    }

    public interface DBSaveListener{
        void onSuccess();
        void onFail();
    }

    /*--------------Inrush 的数据库--------------*/

    public void insetInrushMeterData(final String fileName, final List<InrushMeterData> meterDataList, final DBSaveListener dbListener){
        database.beginTransaction();
        Cursor cursor = LitePal.findBySQL("select id from " + InrushDataBean.class.getSimpleName().toLowerCase() + " where filename = " + "'" + fileName + "'");
        if (cursor != null && cursor.moveToFirst()){
            int id1 = cursor.getColumnIndex("id");
            int id = cursor.getInt(id1);
            cursor.close();
            for (int i = 0; i < meterDataList.size(); i++) {
                InrushMeterData inrushMeterData = meterDataList.get(i);
                database.execSQL("insert into inrushmeterdata(modetype,parametertype,phasetype,value,time,inrushdatabean_id,datacount,gelectricity,relectricity) values (?,?,?,?,?,?,?,?,?)", new Object[] { inrushMeterData.getModeType(), inrushMeterData.getParameterType(),inrushMeterData.getPhaseType(),inrushMeterData.getValue(),inrushMeterData.getTime().getTime(),id,inrushMeterData.getDataCount(),inrushMeterData.isgElectricity(),inrushMeterData.isrElectricity() });
            }
        }

        database.setTransactionSuccessful();
        database.endTransaction();
        LitePal.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (dbListener != null)
                    dbListener.onSuccess();
            }
        });
    }

    public void updateInrushBeanEndDate(final int id,final Date date){
        database.beginTransaction();
        database.execSQL("update inrushdatabean set enddate=? where id=?", new Object[]{date.getTime(),id});
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void updateInrushBeanEndDateSync(final int id,final Date date, final DBSaveListener dbListener){
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.beginTransaction();
                database.execSQL("update inrushdatabean set enddate=? where id=?", new Object[]{date.getTime(),id});
                database.setTransactionSuccessful();
                database.endTransaction();
                LitePal.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (dbListener != null)
                            dbListener.onSuccess();
                    }
                });
            }
        });
    }

    private void findInrushDataCoreTable(final boolean isEager, final String whereString, final DBQueryInrushParameterListener dbQueryListener){
//        find(LitePal.where("data_name = ?",name),dbQueryListener);
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.beginTransaction();
                String tableName = InrushDataBean.class.getSimpleName().toLowerCase();
                String tableName2 = InrushMeterData.class.getSimpleName().toLowerCase();

                final List<InrushDataBean> list = new ArrayList<>();
                Cursor cursor = LitePal.findBySQL("select * from " + tableName + whereString);
                if (cursor != null && cursor.moveToFirst()){
                    int id1 = cursor.getColumnIndex("id");
                    int checkparameters = cursor.getColumnIndex("checkparameters");
                    int enddate = cursor.getColumnIndex("enddate");
                    int startdate = cursor.getColumnIndex("startdate");
                    int filename = cursor.getColumnIndex("filename");
                    int modetype = cursor.getColumnIndex("modetype");
                    int devicename = cursor.getColumnIndex("devicename");


                    do {
                        int id = cursor.getInt(id1);
                        String checkParameters = cursor.getString(checkparameters);
                        Date startDate = new Date(cursor.getLong(startdate));
                        Date endDate = new Date(cursor.getLong(enddate));
                        String fileName = cursor.getString(filename);
                        String deviceName = cursor.getString(devicename);
                        int modeType = cursor.getInt(modetype);

                        InrushDataBean sqlDataBean = MeterDataPool.obtainInrushBean();
                        sqlDataBean.setId(id);
                        sqlDataBean.setEndDate(endDate);
                        sqlDataBean.setStartDate(startDate);
                        sqlDataBean.setDeviceName(deviceName);
                        sqlDataBean.setCheckParameters(checkParameters);
                        sqlDataBean.setFileName(fileName);
                        sqlDataBean.setModeType(modeType);
                        list.add(sqlDataBean);
                    }while (cursor.moveToNext());
                    cursor.close();
                }

                if (isEager)
                    for (InrushDataBean bean: list) {
                        List<InrushMeterData> dataList = new ArrayList<>();
                        Cursor cursor2 = LitePal.findBySQL("select * from " + tableName2 + " where " + tableName+"_id = " + bean.getId()   + " order by id asc");
                        long ot = 0;
                        long ct = 0;

                        if (cursor2 != null && cursor2.moveToFirst()){
                            int id1 = cursor2.getColumnIndex("id");
                            int modetype = cursor2.getColumnIndex("modetype");
                            int parametertype = cursor2.getColumnIndex("parametertype");
                            int phasetype = cursor2.getColumnIndex("phasetype");
                            int datacount = cursor2.getColumnIndex("datacount");
                            int value = cursor2.getColumnIndex("value");
                            int time = cursor2.getColumnIndex("time");
                            int gelectricity = cursor2.getColumnIndex("gelectricity");
                            int relectricity = cursor2.getColumnIndex("relectricity");
                            long startTime = System.currentTimeMillis();
                            long l3 = System.currentTimeMillis();
                            long mt = 0;
                            long posstime=System.currentTimeMillis();
                            do {
                                mt += System.currentTimeMillis() - l3;
                                long l2 = System.currentTimeMillis();
                                int id = cursor2.getInt(id1);
                                int modeType = cursor2.getInt(modetype);
                                int parameterType = cursor2.getInt(parametertype);
                                int phaseType = cursor2.getInt(phasetype);
                                int dataCount = cursor2.getInt(datacount);
                                float valueFloat = cursor2.getFloat(value);

                                Date Time = new Date(cursor2.getLong(time));
                                boolean rElectricity = cursor2.getInt(relectricity) == 1;
                                boolean gElectricity = cursor2.getInt(gelectricity) == 1;
                                ct += System.currentTimeMillis() - l2;
                                long l1 = System.currentTimeMillis();

                                InrushMeterData inrushMeterData = MeterDataPool.obtainInrushMeter();
                                inrushMeterData.setId(id);
                                inrushMeterData.setValue(valueFloat);
                                inrushMeterData.setDataCount(dataCount);
                                inrushMeterData.setPhaseType(phaseType);
                                inrushMeterData.setModeType(modeType);
                                inrushMeterData.setParameterType(parameterType);
                                inrushMeterData.setTime(Time);
                                inrushMeterData.setgElectricity(gElectricity);
                                inrushMeterData.setrElectricity(rElectricity);
                                dataList.add(inrushMeterData);
                                ot += System.currentTimeMillis() - l1;
                                l3 = System.currentTimeMillis();

                            }while (cursor2.moveToNext());
                            log.e("查询读取时间"+(System.currentTimeMillis()-posstime));
                            log.e("创建对象耗时："+(ot));
                            log.e("cursor耗时："+(ct));
                            log.e("move耗时"+(mt));
                            log.e("查询2耗时："+(System.currentTimeMillis()-startTime));
                            cursor2.close();
                        }
                        bean.addMeterList(dataList);
                    }

                database.setTransactionSuccessful();
                database.endTransaction();
                LitePal.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        dbQueryListener.onSuccess(list);
                    }
                });
            }
        });
    }

    /**
     *
     * @param isEager 是否激进查询，true 查询所有关联表以及参数，false 只查询最外层表
     * @param dbQueryListener
     */
    public void findInrushALL(final boolean isEager, final DBQueryInrushParameterListener dbQueryListener){
        findInrushDataCoreTable(isEager,"",dbQueryListener);
    }

    public void findInrushFromName(final boolean isEager,final String name , final DBQueryInrushParameterListener dbQueryListener){
//        find(LitePal.where("data_name = ?",name),dbQueryListener);
        String where = " where filename = '" + name +"'";
        findInrushDataCoreTable(isEager,where,dbQueryListener);
    }

    public void deleteInrushFromName(final String name, final DBUpdateOrDeleteListener dbUpdateOrDeleteListener){
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.beginTransaction();

                String tableName = InrushDataBean.class.getSimpleName().toLowerCase();
                final List<InrushDataBean> list = new ArrayList<>();
                Cursor cursor = LitePal.findBySQL("select id from " + tableName + " where filename = '" + name + "'");
                if (cursor != null && cursor.moveToFirst()){
                    int ID = cursor.getColumnIndex("id");
                    do {
                        int anInt = cursor.getInt(ID);
                        LitePal.getDatabase().execSQL("delete from " + InrushMeterData.class.getSimpleName().toLowerCase() + " where " + InrushDataBean.class.getSimpleName().toLowerCase() + "_id = " + anInt);
                        LitePal.getDatabase().execSQL("delete from " + InrushDataBean.class.getSimpleName().toLowerCase() + " where " + " id = " + anInt);
                    }while (cursor.moveToNext());
                    cursor.close();
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                LitePal.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        dbUpdateOrDeleteListener.onFinish();
                    }
                });
            }
        });
    }

    public interface DBQueryInrushParameterListener{
        void onFail();
        void onSuccess(List<InrushDataBean> dataMeterAllParameterList);
    }

    /***********************DipsSwells************************/

    public void insetDipsSwellsMeterData(final String fileName, final List<DipsSwellsMeterData> meterDataList, final DBSaveListener dbListener){
        database.beginTransaction();
        Cursor cursor = LitePal.findBySQL("select id from " + DipsSwellsDataBean.class.getSimpleName().toLowerCase() + " where filename = " + "'" + fileName + "'");
        if (cursor != null && cursor.moveToFirst()){
            int id1 = cursor.getColumnIndex("id");
            int id = cursor.getInt(id1);
            cursor.close();
            for (int i = 0; i < meterDataList.size(); i++) {
                DipsSwellsMeterData dipsSwellsMeterData = meterDataList.get(i);
                database.execSQL("insert into dipsswellsmeterdata(modetype,parametertype,phasetype,value,time,dipsswellsdatabean_id,datacount,gelectricity,relectricity) values (?,?,?,?,?,?,?,?,?)", new Object[] { dipsSwellsMeterData.getModeType(), dipsSwellsMeterData.getParameterType(),dipsSwellsMeterData.getPhaseType(),dipsSwellsMeterData.getValue(),dipsSwellsMeterData.getTime().getTime(),id,dipsSwellsMeterData.getDataCount(),dipsSwellsMeterData.isgElectricity(),dipsSwellsMeterData.isrElectricity() });
            }
        }

        database.setTransactionSuccessful();
        database.endTransaction();
        LitePal.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (dbListener != null)
                    dbListener.onSuccess();
            }
        });
    }

    public void updateDipsSwellsBeanEndDate(final int id,final Date date){
        database.beginTransaction();
        database.execSQL("update dipsswellsdatabean set enddate=? where id=?", new Object[]{date.getTime(),id});
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void updateDipsSwellsBeanEndDateSync(final int id,final Date date, final DBSaveListener dbListener){
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.beginTransaction();
                database.execSQL("update dipsswellsdatabean set enddate=? where id=?", new Object[]{date.getTime(),id});
                database.setTransactionSuccessful();
                database.endTransaction();
                LitePal.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (dbListener != null)
                            dbListener.onSuccess();
                    }
                });
            }
        });
    }

    private void findDipsSwellsDataCoreTable(final boolean isEager, final String whereString, final DBQueryDipsSwellsParameterListener dbQueryListener){
//        find(LitePal.where("data_name = ?",name),dbQueryListener);
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.beginTransaction();
                String tableName = DipsSwellsDataBean.class.getSimpleName().toLowerCase();
                String tableName2 = DipsSwellsMeterData.class.getSimpleName().toLowerCase();

                final List<DipsSwellsDataBean> list = new ArrayList<>();
                Cursor cursor = LitePal.findBySQL("select * from " + tableName + whereString);
                if (cursor != null && cursor.moveToFirst()){
                    int id1 = cursor.getColumnIndex("id");
                    int checkparameters = cursor.getColumnIndex("checkparameters");
                    int enddate = cursor.getColumnIndex("enddate");
                    int startdate = cursor.getColumnIndex("startdate");
                    int filename = cursor.getColumnIndex("filename");
                    int modetype = cursor.getColumnIndex("modetype");
                    int devicename = cursor.getColumnIndex("devicename");

                    do {
                        int id = cursor.getInt(id1);
                        String checkParameters = cursor.getString(checkparameters);
                        Date startDate = new Date(cursor.getLong(startdate));
                        Date endDate = new Date(cursor.getLong(enddate));
                        String fileName = cursor.getString(filename);
                        String deviceName = cursor.getString(devicename);
                        int modeType = cursor.getInt(modetype);

                        DipsSwellsDataBean sqlDataBean = MeterDataPool.obtainDipsSwellsBean();
                        sqlDataBean.setId(id);
                        sqlDataBean.setEndDate(endDate);
                        sqlDataBean.setStartDate(startDate);
                        sqlDataBean.setDeviceName(deviceName);
                        sqlDataBean.setCheckParameters(checkParameters);
                        sqlDataBean.setFileName(fileName);
                        sqlDataBean.setModeType(modeType);
                        list.add(sqlDataBean);
                    }while (cursor.moveToNext());
                    cursor.close();
                }

                if (isEager)
                    for (DipsSwellsDataBean bean: list) {
                        List<DipsSwellsMeterData> dataList = new ArrayList<>();
                        Cursor cursor2 = LitePal.findBySQL("select * from " + tableName2 + " where " + tableName+"_id = " + bean.getId()   + " order by id asc");
                        long ot = 0;
                        long ct = 0;

                        if (cursor2 != null && cursor2.moveToFirst()){
                            int id1 = cursor2.getColumnIndex("id");
                            int modetype = cursor2.getColumnIndex("modetype");
                            int parametertype = cursor2.getColumnIndex("parametertype");
                            int phasetype = cursor2.getColumnIndex("phasetype");
                            int datacount = cursor2.getColumnIndex("datacount");
                            int value = cursor2.getColumnIndex("value");
                            int time = cursor2.getColumnIndex("time");
                            int gelectricity = cursor2.getColumnIndex("gelectricity");
                            int relectricity = cursor2.getColumnIndex("relectricity");
                            long startTime = System.currentTimeMillis();
                            long l3 = System.currentTimeMillis();
                            long mt = 0;
                            long posstime=System.currentTimeMillis();
                            do {
                                mt += System.currentTimeMillis() - l3;
                                long l2 = System.currentTimeMillis();
                                int id = cursor2.getInt(id1);
                                int modeType = cursor2.getInt(modetype);
                                int parameterType = cursor2.getInt(parametertype);
                                int phaseType = cursor2.getInt(phasetype);
                                int dataCount = cursor2.getInt(datacount);
                                float valueFloat = cursor2.getFloat(value);

                                Date Time = new Date(cursor2.getLong(time));
                                boolean rElectricity = cursor2.getInt(relectricity) == 1;
                                boolean gElectricity = cursor2.getInt(gelectricity) == 1;
                                ct += System.currentTimeMillis() - l2;
                                long l1 = System.currentTimeMillis();

                                DipsSwellsMeterData dipsSwellsMeterData = MeterDataPool.obtainDipsSwellsMeter();
                                dipsSwellsMeterData.setId(id);
                                dipsSwellsMeterData.setValue(valueFloat);
                                dipsSwellsMeterData.setDataCount(dataCount);
                                dipsSwellsMeterData.setPhaseType(phaseType);
                                dipsSwellsMeterData.setModeType(modeType);
                                dipsSwellsMeterData.setParameterType(parameterType);
                                dipsSwellsMeterData.setTime(Time);
                                dipsSwellsMeterData.setgElectricity(gElectricity);
                                dipsSwellsMeterData.setrElectricity(rElectricity);
                                dataList.add(dipsSwellsMeterData);
                                ot += System.currentTimeMillis() - l1;
                                l3 = System.currentTimeMillis();

                            }while (cursor2.moveToNext());
                            log.e("查询读取时间"+(System.currentTimeMillis()-posstime));
                            log.e("创建对象耗时："+(ot));
                            log.e("cursor耗时："+(ct));
                            log.e("move耗时"+(mt));
                            log.e("查询2耗时："+(System.currentTimeMillis()-startTime));
                            cursor2.close();
                        }
                        bean.addMeterList(dataList);
                    }

                database.setTransactionSuccessful();
                database.endTransaction();
                LitePal.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        dbQueryListener.onSuccess(list);
                    }
                });
            }
        });
    }

    public void findDipsSwellsALL(final boolean isEager, final DBQueryDipsSwellsParameterListener dbQueryListener){
        findDipsSwellsDataCoreTable(isEager,"",dbQueryListener);
    }

    public void findDipsSwellsFromName(final boolean isEager,final String name , final DBQueryDipsSwellsParameterListener dbQueryListener){
//        find(LitePal.where("data_name = ?",name),dbQueryListener);
        String where = " where filename = '" + name +"'";
        findDipsSwellsDataCoreTable(isEager,where,dbQueryListener);
    }

    public void deleteDipsSwellsFromName(final String name, final DBUpdateOrDeleteListener dbUpdateOrDeleteListener){
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.beginTransaction();

                String tableName = DipsSwellsDataBean.class.getSimpleName().toLowerCase();
                final List<DipsSwellsDataBean> list = new ArrayList<>();
                Cursor cursor = LitePal.findBySQL("select id from " + tableName + " where filename = '" + name + "'");
                if (cursor != null && cursor.moveToFirst()){
                    int ID = cursor.getColumnIndex("id");
                    do {
                        int anInt = cursor.getInt(ID);
                        LitePal.getDatabase().execSQL("delete from " + DipsSwellsMeterData.class.getSimpleName().toLowerCase() + " where " + DipsSwellsDataBean.class.getSimpleName().toLowerCase() + "_id = " + anInt);
                        LitePal.getDatabase().execSQL("delete from " + DipsSwellsDataBean.class.getSimpleName().toLowerCase() + " where " + " id = " + anInt);
                    }while (cursor.moveToNext());
                    cursor.close();
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                LitePal.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        dbUpdateOrDeleteListener.onFinish();
                    }
                });
            }
        });
    }

    public interface DBQueryDipsSwellsParameterListener{
        void onFail();
        void onSuccess(List<DipsSwellsDataBean> dataMeterAllParameterList);
    }

}
