package com.cem.powerqualityanalyser.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.databean.FileBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FilesListAdapter extends RecyclerView.Adapter {
    private List<FileBean> fileList;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private ItemClickListener itemClickListener;

    public FilesListAdapter(List<FileBean> fileList) {
        this.fileList = fileList;
    }

    public List<FileBean> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileBean> fileList) {
        this.fileList = fileList;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_fileslist, parent, false);
            return new HeadViewHolder(layout);
        } else {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filelsit, parent, false);
            return new FileViewHolder(layout);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        int index = position;
        if (mHeaderView != null) {
            index = position - 1;
        }
        if (holder instanceof FileViewHolder) {
            final FileBean fileBean = fileList.get(index);
            ((FileViewHolder) holder).tv_start.setText(formatTime(fileBean.getStartTime()));
            ((FileViewHolder) holder).tv_statt.setText(formatTime(fileBean.getEndTime()));
            ((FileViewHolder) holder).tv_name.setText(fileBean.getFileName());
            ((FileViewHolder) holder).tv_type.setText(fileBean.getType());
            ((FileViewHolder) holder).checkBox.setChecked(fileBean.getChecked());
            final int finalIndex = index;
            ((FileViewHolder) holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    fileList.get(finalIndex).setChecked(isChecked);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(position - 1);
                }
            });
        }
    }

    private String formatTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        return format.format(date);
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? fileList.size() : fileList.size() + 1;
    }

    class HeadViewHolder extends ViewHolder {
        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    class FileViewHolder extends ViewHolder {
        TextView tv_start, tv_statt, tv_name, tv_type;
        CheckBox checkBox;

        public FileViewHolder(View itemView) {
            super(itemView);
            tv_start = itemView.findViewById(R.id.tv_start);
            tv_statt = itemView.findViewById(R.id.tv_statt);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            checkBox = itemView.findViewById(R.id.item_check);
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

}
