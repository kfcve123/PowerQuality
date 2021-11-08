package com.cem.powerqualityanalyser.databean;

import android.content.Context;


import com.cem.powerqualityanalyser.sqlBean.DipsSwellsDataBean;
import com.cem.powerqualityanalyser.sqlBean.InrushDataBean;
import com.cem.powerqualityanalyser.sqlBean.SqlDataBean;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * 历史记录 和 图库 文件管理
 */
public class FileImageManager {
    public static final String IMAGE_CHILD_PATH = "pic";
    public static void formatSD(Context context) {
        DBManager.getInstance().clearDB();
        File file = new File(context.getExternalCacheDir(),IMAGE_CHILD_PATH);
        if (file.exists()){
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
        }
    }

    public static void findDBAndFiles(final Context context, boolean isEager,final FindListener findListener){
        final List<FileBean> fileBeans = new ArrayList<>();
        DBManager.getInstance().findALL(isEager,new DBManager.DBQueryAllParameterListener() {
            @Override
            public void onFail() {
                if (findListener != null)
                    LitePal.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            Collections.sort(fileBeans);
                            findListener.findFinish(fileBeans);
                        }
                    });
            }

            @Override
            public void onSuccess(List<SqlDataBean> dataMeterAllParameterList) {
                for (int i = 0; i < dataMeterAllParameterList.size(); i++) {
                    FileBean fileBean = new FileBean();
                    fileBean.setType(FileBean.TYPE_CSV);
                    SqlDataBean sqlDataBean = dataMeterAllParameterList.get(i);
                    fileBean.setFileName(sqlDataBean.getFileName());
                    fileBean.setStartTime(sqlDataBean.getStartDate());
                    fileBean.setEndTime(sqlDataBean.getEndDate());
                    fileBeans.add(fileBean);
//                    List<BaseData> baseDataList = SqlDataTool.SqlDataBean2BaseData(context, dataMeterAllParameterList.get(i));
//                    log.e("find:  " + baseDataList.size());
                }

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(context.getExternalCacheDir(),IMAGE_CHILD_PATH);
                        if (file.exists()){
                            File[] files = file.listFiles();
                            for (int i = 0; i < files.length; i++) {
                                if (files[i].getName().endsWith(".png")){
                                    FileBean fileBean = new FileBean();
                                    fileBean.setType(FileBean.TYPE_PIC);
                                    fileBean.setFileName(files[i].getName());
                                    String name = files[i].getName();
                                    Long aLong = Long.valueOf(name.substring(0, name.length() - 4));
                                    fileBean.setStartTime(new Date(aLong));
                                    fileBean.setEndTime(fileBean.getStartTime());
                                    fileBean.setImagePath(files[i].getPath());
                                    fileBeans.add(fileBean);
                                }
                            }

                        }
                        if (findListener != null)
                            LitePal.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Collections.sort(fileBeans);
                                    findListener.findFinish(fileBeans);
                                }
                            });
                    }
                });
            }


        });

    }

    /**
     * 只显示CSV 文件
     * @param context
     * @param isEager
     * @param findListener
     */
    public static void findCsvFiles(final Context context, boolean isEager,final FindListener findListener){
        final List<FileBean> fileBeans = new ArrayList<>();
        DBManager.getInstance().findALL(isEager,new DBManager.DBQueryAllParameterListener() {
            @Override
            public void onFail() {
                if (findListener != null)
                    LitePal.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            Collections.sort(fileBeans);
                            findListener.findFinish(fileBeans);
                        }
                    });
            }

            @Override
            public void onSuccess(List<SqlDataBean> dataMeterAllParameterList) {
                for (int i = 0; i < dataMeterAllParameterList.size(); i++) {
                    FileBean fileBean = new FileBean();
                    fileBean.setType(FileBean.TYPE_CSV);
                    SqlDataBean sqlDataBean = dataMeterAllParameterList.get(i);
                    fileBean.setFileName(sqlDataBean.getFileName());
                    fileBean.setStartTime(sqlDataBean.getStartDate());
                    fileBean.setEndTime(sqlDataBean.getEndDate());
                    fileBeans.add(fileBean);
                }

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        /*File file = new File(context.getExternalCacheDir(),IMAGE_CHILD_PATH);
                        if (file.exists()){
                            File[] files = file.listFiles();
                            for (int i = 0; i < files.length; i++) {
                                if (files[i].getName().endsWith(".png")){
                                    FileBean fileBean = new FileBean();
                                    fileBean.setType(FileBean.TYPE_PIC);
                                    fileBean.setFileName(files[i].getName());
                                    String name = files[i].getName();
                                    Long aLong = Long.valueOf(name.substring(0, name.length() - 4));
                                    fileBean.setStartTime(new Date(aLong));
                                    fileBean.setEndTime(fileBean.getStartTime());
                                    fileBean.setImagePath(files[i].getPath());
                                    fileBeans.add(fileBean);
                                }
                            }

                        }*/
                        if (findListener != null)
                            LitePal.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Collections.sort(fileBeans);
                                    findListener.findFinish(fileBeans);
                                }
                            });
                    }
                });
            }


        });

    }


    /**
     * 只显示图片文件
     * @param context
     * @param isEager
     * @param findListener
     */
    public static void findImageFiles(final Context context, boolean isEager,final FindListener findListener){
        final List<FileBean> fileBeans = new ArrayList<>();
        DBManager.getInstance().findALL(isEager,new DBManager.DBQueryAllParameterListener() {
            @Override
            public void onFail() {
                if (findListener != null)
                    LitePal.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            Collections.sort(fileBeans);
                            findListener.findFinish(fileBeans);
                        }
                    });
            }

            @Override
            public void onSuccess(List<SqlDataBean> dataMeterAllParameterList) {
                /*for (int i = 0; i < dataMeterAllParameterList.size(); i++) {
                    FileBean fileBean = new FileBean();
                    fileBean.setType(FileBean.TYPE_CSV);
                    SqlDataBean sqlDataBean = dataMeterAllParameterList.get(i);
                    fileBean.setFileName(sqlDataBean.getFileName());
                    fileBean.setStartTime(sqlDataBean.getStartDate());
                    fileBean.setEndTime(sqlDataBean.getEndDate());
                    fileBeans.add(fileBean);
                }*/

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(context.getExternalCacheDir(),IMAGE_CHILD_PATH);
                        if (file.exists()){
                            File[] files = file.listFiles();
                            for (int i = 0; i < files.length; i++) {
                                if (files[i].getName().endsWith(".png")){
                                    FileBean fileBean = new FileBean();
                                    fileBean.setType(FileBean.TYPE_PIC);
                                    fileBean.setFileName(files[i].getName());
                                    String name = files[i].getName();
                                    Long aLong = Long.valueOf(name.substring(0, name.length() - 4));
                                    fileBean.setStartTime(new Date(aLong));
                                    fileBean.setEndTime(fileBean.getStartTime());
                                    fileBean.setImagePath(files[i].getPath());
                                    fileBeans.add(fileBean);
                                }
                            }

                        }
                        if (findListener != null)
                            LitePal.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Collections.sort(fileBeans);
                                    findListener.findFinish(fileBeans);
                                }
                            });
                    }
                });
            }


        });

    }


    public static void findInrushCsvFiles(final Context context, boolean isEager,final FindListener findListener){
        final List<FileBean> fileBeans = new ArrayList<>();
        DBManager.getInstance().findInrushALL(isEager,new DBManager.DBQueryInrushParameterListener() {
            @Override
            public void onFail() {
                if (findListener != null)
                    LitePal.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            Collections.sort(fileBeans);
                            findListener.findFinish(fileBeans);
                        }
                    });
            }

            @Override
            public void onSuccess(List<InrushDataBean> dataMeterAllParameterList) {
                for (int i = 0; i < dataMeterAllParameterList.size(); i++) {
                    FileBean fileBean = new FileBean();
                    fileBean.setType(FileBean.TYPE_CSV);
                    InrushDataBean sqlDataBean = dataMeterAllParameterList.get(i);
                    fileBean.setFileName(sqlDataBean.getFileName());
                    fileBean.setStartTime(sqlDataBean.getStartDate());
                    fileBean.setEndTime(sqlDataBean.getEndDate());
                    fileBeans.add(fileBean);
                }

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (findListener != null)
                            LitePal.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Collections.sort(fileBeans);
                                    findListener.findFinish(fileBeans);
                                }
                            });
                    }
                });
            }


        });

    }

    public static void findDipsSwellsCsvFiles(final Context context, boolean isEager,final FindListener findListener){
        final List<FileBean> fileBeans = new ArrayList<>();
        DBManager.getInstance().findDipsSwellsALL(isEager,new DBManager.DBQueryDipsSwellsParameterListener() {
            @Override
            public void onFail() {
                if (findListener != null)
                    LitePal.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            Collections.sort(fileBeans);
                            findListener.findFinish(fileBeans);
                        }
                    });
            }

            @Override
            public void onSuccess(List<DipsSwellsDataBean> dataMeterAllParameterList) {
                for (int i = 0; i < dataMeterAllParameterList.size(); i++) {
                    FileBean fileBean = new FileBean();
                    fileBean.setType(FileBean.TYPE_CSV);
                    DipsSwellsDataBean sqlDataBean = dataMeterAllParameterList.get(i);
                    fileBean.setFileName(sqlDataBean.getFileName());
                    fileBean.setStartTime(sqlDataBean.getStartDate());
                    fileBean.setEndTime(sqlDataBean.getEndDate());
                    fileBeans.add(fileBean);
                }

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (findListener != null)
                            LitePal.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Collections.sort(fileBeans);
                                    findListener.findFinish(fileBeans);
                                }
                            });
                    }
                });
            }


        });

    }

    public static void deleteFile(String filePath){
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
        }
    }

    public interface FindListener{

        void findFinish(List<FileBean> fileBeanList);

    }

}
