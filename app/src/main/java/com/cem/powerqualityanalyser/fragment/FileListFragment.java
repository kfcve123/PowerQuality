package com.cem.powerqualityanalyser.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.activity.BaseActivity;
import com.cem.powerqualityanalyser.activity.DipsSwellHistoryLoggerActivity;
import com.cem.powerqualityanalyser.activity.HistoryLoggerActivity;
import com.cem.powerqualityanalyser.activity.InrushHistoryActivity;
import com.cem.powerqualityanalyser.activity.NewHistoryLoggerActivity;
import com.cem.powerqualityanalyser.activity.PreviewImageActivity;
import com.cem.powerqualityanalyser.adapter.FilesListAdapter;
import com.cem.powerqualityanalyser.databean.DBManager;
import com.cem.powerqualityanalyser.databean.FileBean;
import com.cem.powerqualityanalyser.databean.FileImageManager;
import com.cem.powerqualityanalyser.tool.CSVTool;
import com.cem.powerqualityanalyser.tool.WifiBlueTool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListFragment extends BaseFragment implements FileImageManager.FindListener, FilesListAdapter.ItemClickListener, CSVTool.WriteListener {
    private RecyclerView recyclerView;
    private FilesListAdapter adapter;
    private List<FileBean> list = new ArrayList<>();
    private CSVTool csvTool;
    private WifiBlueTool wifiBlueTool;
    @Override
    public int setContentView() {
        return R.layout.fragment_filelist;
    }

    @Override
    public void onInitView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_files);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FilesListAdapter(new ArrayList<FileBean>());
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
        adapter.setHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.head_fileslist,null));
        wifiBlueTool = new WifiBlueTool(this.getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void shareFileEmail(File... files){
        if (wifiBlueTool.isWifiOpened() && wifiBlueTool.isNetworkConnected() && wifiBlueTool.isNetworkOnline()){
            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            String[] tos = { "" };
            String[] ccs = { "" };
            String[] bccs = {""};
            intent.putExtra(Intent.EXTRA_EMAIL, tos);
            intent.putExtra(Intent.EXTRA_CC, ccs);
            intent.putExtra(Intent.EXTRA_BCC, bccs);
            intent.putExtra(Intent.EXTRA_TEXT, "body");
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
            ArrayList fileUris = new ArrayList();
            for (int i = 0; i < files.length; i++) {
                fileUris.add(Uri.fromFile(files[i]));
            }
            intent.setPackage("com.android.email");
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, fileUris);
//            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//        intent.setType("application/octet-stream");
//            intent.setType("image/*");
//            intent.setType("text/plain");
            intent.setType("*/*");
            Intent.createChooser(intent, "Choose Email Client");
            startActivity(intent);
        }else{
            wifiBlueTool.toWifiSeting();
        }

    }
    private void shareFileBT(File file){
        if (wifiBlueTool.isOpenBlue() && wifiBlueTool.isBTConnected()){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.setPackage("com.android.bluetooth");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));//path为文件的路径
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent chooser = Intent.createChooser(intent, "Share BT");
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(chooser);
        }else{
            wifiBlueTool.toBTSeting();
        }

    }
    private int shareType = -1;

    public void setShareType(int type) {
        this.shareType = type;
    }

    private void initData(){
        ((BaseActivity) getActivity()).showLoading();
 //       FileImageManager.findImageFiles(this.getActivity(),false,this);
        if(shareType == 1) {
            FileImageManager.findCsvFiles(this.getActivity(), false, this);
        }else if(shareType == 0) {
            FileImageManager.findImageFiles(this.getActivity(), false, this);
        }else if(shareType == 2) {
            FileImageManager.findInrushCsvFiles(this.getActivity(), false, this);
        }else if(shareType == 4){//findDipsSwellsCsvFiles
            FileImageManager.findDipsSwellsCsvFiles(this.getActivity(), false, this);
        }else{
            FileImageManager.findDBAndFiles(this.getActivity(), false, this);
        }

    }
    public void deletefile(){
        final List<FileBean> fileList = adapter.getFileList();
        ArrayList<FileBean> fileBeans = new ArrayList<>();
        fileBeans.addAll(fileList);
        for (int i = 0; i < fileBeans.size(); i++) {
            final FileBean fileBean = fileBeans.get(i);
            if (fileBean.getChecked()){
                if (fileBean.getType().equals(FileBean.TYPE_CSV)){
                    if(shareType == 2) {
                        DBManager.getInstance().deleteInrushFromName(fileBean.getFileName(), new DBManager.DBUpdateOrDeleteListener() {
                            @Override
                            public void onFinish() {
                                fileList.remove(fileBean);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }else if(shareType == 4){
                        DBManager.getInstance().deleteDipsSwellsFromName(fileBean.getFileName(), new DBManager.DBUpdateOrDeleteListener() {
                            @Override
                            public void onFinish() {
                                fileList.remove(fileBean);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }else {
                        DBManager.getInstance().deleteFromName(fileBean.getFileName(), new DBManager.DBUpdateOrDeleteListener() {
                            @Override
                            public void onFinish() {
                                fileList.remove(fileBean);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }else{
                    FileImageManager.deleteFile(fileBean.getImagePath());
                    fileList.remove(fileBean);
                    adapter.notifyDataSetChanged();
                }

            }
        }
    }

    public void selectAll() {
        List<FileBean> fileList = adapter.getFileList();
        if (fileList != null){
            if (isSelectAll()){
                for (int i = 0; i < fileList.size(); i++) {
                    fileList.get(i).setChecked(false);
                }
            }else{
                for (int i = 0; i < fileList.size(); i++) {
                    fileList.get(i).setChecked(true);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    private boolean isSelectAll(){
        List<FileBean> fileList = adapter.getFileList();
        if (fileList != null){
            for (int i = 0; i < fileList.size(); i++) {
                if (!fileList.get(i).getChecked()){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void findFinish(List<FileBean> fileBeanList) {
        ((BaseActivity) getActivity()).dissLoading();
        if (fileBeanList.size() == 0){
            Toast.makeText(getActivity(),"no data",Toast.LENGTH_SHORT).show();
        }
        list = fileBeanList;
        adapter.setFileList(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {


    }

    public void OpenFile(){
        int selectCount = getSelectCount();
        if (selectCount == 1){
            List<FileBean> fileList = adapter.getFileList();
            FileBean fileBean = null;
            for (int i = 0; i < fileList.size(); i++) {
                if (fileList.get(i).getChecked())
                    fileBean = fileList.get(i);
            }
            if (fileBean.getType().equals(FileBean.TYPE_CSV)){
                if(shareType == 2){
                    Intent intent = new Intent(this.getActivity(), InrushHistoryActivity.class);
                    intent.putExtra("fileName", fileBean.getFileName());
                    intent.putExtra(AppConfig.MainPutActivityNameKey, "History Data");
                    startActivity(intent);

                }else if(shareType == 4){
                    Intent intent = new Intent(this.getActivity(), DipsSwellHistoryLoggerActivity.class);
                    intent.putExtra("fileName", fileBean.getFileName());
                    intent.putExtra(AppConfig.MainPutActivityNameKey, "History Data");
                    startActivity(intent);
                }else if(shareType == 1){
                    Intent intent = new Intent(this.getActivity(), NewHistoryLoggerActivity.class);
                    intent.putExtra("fileName", fileBean.getFileName());
                    intent.putExtra(AppConfig.MainPutActivityNameKey, "History Data");
                    startActivity(intent);
                }
            }else{
                //打开图片
                Intent intent = new Intent(this.getActivity(), PreviewImageActivity.class);
                intent.putExtra("imagePath",fileBean.getImagePath());
                intent.putExtra(AppConfig.MainPutActivityNameKey, "Image");
                startActivity(intent);

            }
        }else if (selectCount > 1){
            Toast.makeText(this.getActivity(),"Only one file can be selected",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this.getActivity(),"Please select a file",Toast.LENGTH_SHORT).show();
        }



    }
    public int getSelectCount(){
        List<FileBean> fileList = adapter.getFileList();
        int count = 0;
        if (fileList != null){
            for (int i = 0; i < fileList.size(); i++) {
                if (fileList.get(i).getChecked())
                    count ++;
            }
        }
        return count;
    }

    public void shareFile(int positon) {
        int selectCount = getSelectCount();
        if (selectCount == 1){
            List<FileBean> fileList = adapter.getFileList();
            FileBean fileBean = null;
            for (int i = 0; i < fileList.size(); i++) {
                if (fileList.get(i).getChecked())
                    fileBean = fileList.get(i);
            }
            if (fileBean.getType().equals(FileBean.TYPE_CSV)){
                File file = new File(CSVTool.filePath,fileBean.getFileName()+".csv");
                if (positon == 0){
                    shareFileBT(file);
                }else if (positon == 1){
                    shareFileEmail(file);
                }
            }else{
                File file = new File(fileBean.getImagePath());
                if (positon == 0){
                    shareFileBT(file);
                }else if (positon == 1){
                    shareFileEmail(file);
                }
            }

        }else if (selectCount > 1){
            Toast.makeText(this.getActivity(),"Only one file can be selected",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this.getActivity(),"Please select a file",Toast.LENGTH_SHORT).show();
        }


    }

    public void OutCsv() {

        int selectCount = getSelectCount();
        if (selectCount == 1){
            List<FileBean> fileList = adapter.getFileList();
            FileBean fileBean = null;
            for (int i = 0; i < fileList.size(); i++) {
                if (fileList.get(i).getChecked())
                    fileBean = fileList.get(i);
            }
            if (fileBean.getType().equals(FileBean.TYPE_CSV)){
                if (csvTool == null)
                    csvTool = new CSVTool(this.getActivity(),this);
                csvTool.writeCsvFile(fileBean.getFileName());
                ((BaseActivity) this.getActivity()).showLoading();
            }

        }else if (selectCount > 1){
            Toast.makeText(this.getActivity(),"Only one file can be selected",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this.getActivity(),"Please select a file",Toast.LENGTH_SHORT).show();
        }
    }

    public void newOutCsv() {

        int selectCount = getSelectCount();
        if (selectCount == 1){
            List<FileBean> fileList = adapter.getFileList();
            FileBean fileBean = null;
            for (int i = 0; i < fileList.size(); i++) {
                if (fileList.get(i).getChecked())
                    fileBean = fileList.get(i);
            }
            if (fileBean.getType().equals(FileBean.TYPE_CSV)){
                if (csvTool == null)
                    csvTool = new CSVTool(this.getActivity(),this);
                csvTool.newwriteCsvFile(fileBean.getFileName());
                ((BaseActivity) this.getActivity()).showLoading();
            }

        }else if (selectCount > 1){
            Toast.makeText(this.getActivity(),"Only one file can be selected",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this.getActivity(),"Please select a file",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void writeFinish() {
        ((BaseActivity) this.getActivity()).dissLoading();
    }

    @Override
    public void writeFail() {
        ((BaseActivity) this.getActivity()).dissLoading();
    }



}
