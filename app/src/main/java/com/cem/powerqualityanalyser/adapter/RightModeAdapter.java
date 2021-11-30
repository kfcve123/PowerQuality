package com.cem.powerqualityanalyser.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.view.TextImageView;

import java.util.List;


public class RightModeAdapter extends BaseAdapter {

    private static final int MAX_ITEM_COUNT = 20;

    private Context context;
    private LayoutInflater layoutInflater;
    private List<RightListViewItemObj> list;
    private MyClickListener mListener;
    private int showLeftIndex = 0;
    private int setTextSize = 20;
    private boolean changgeSize = false;
    private int typeContentTvBg = R.mipmap.textview_bg2;
    private int typeContentTvBgSelct = R.mipmap.textview_select_bg2;

    public void setTypeContentTvBg(int normal,int select){
        this.typeContentTvBg = normal;
        this.typeContentTvBgSelct = select;
    }


    public RightModeAdapter(Context context, List sousuo, MyClickListener mListener){
        this.context = context;
        this.layoutInflater=LayoutInflater.from(context);
        this.list = sousuo;
        this.mListener = mListener;
    }

    public void updateModeList(List<RightListViewItemObj> strList) {
        this.list = strList;
    }

    public void setSelectIndex(int i) {
        this.showLeftIndex = i;
        if(showLeftIndex<0)
            showLeftIndex = 0;
        if(showLeftIndex>=list.size())
            showLeftIndex = list.size()-1;
        notifyDataSetChanged();
    }

    private boolean lostFocus;
    public void lostFocus(boolean lost) {
        this.lostFocus = lost;
        notifyDataSetChanged();
    }


    /**
     * 组件集合，对应xml中的控件
     * @author Administrator
     */
    public class RightViewHolder{
        public TextImageView typeContentTv;
        public ImageView typeLeftIcon;
        public RelativeLayout right_view;

        public RightViewHolder(View convertView){
            typeLeftIcon = (ImageView) convertView.findViewById(R.id.icon_right);
            typeContentTv = (TextImageView) convertView.findViewById(R.id.right_item_tv);
            if(changgeSize)
                typeContentTv.setTextSize(setTextSize);
            right_view = convertView.findViewById(R.id.right_view);
        }
    }

    public void setTextSize(boolean changeSize,int textSize){
        this.changgeSize = changeSize;
        this.setTextSize = textSize;
    }


    /**
     * 设置listview显示的条数，为4条
     * @return
     */
    @Override
    public int getCount() {
        if (list == null)
            return 0;
        return Math.min(MAX_ITEM_COUNT,list.size());
    }


    @Override
    public Object getItem(int position) {
        if(list.get(position)!=null&&!list.isEmpty()){
            return list.get(position);
        }
        return  null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        RightViewHolder viewHolder=null;
        if(convertView==null) {
            //获得组件，实例化组件
            convertView =layoutInflater.inflate(R.layout.rightmode_listview_item, null);
            viewHolder=new RightViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (RightViewHolder) convertView.getTag();
        }
        viewHolder.typeContentTv.setText(list.get(position).getContent());
        if(list.get(position).getTextSize()!= -1){
            viewHolder.typeContentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,list.get(position).getTextSize());
        }else{
            viewHolder.typeContentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
        }
        if(position == showLeftIndex){
            if(!lostFocus)
                viewHolder.typeLeftIcon.setVisibility(View.VISIBLE);
            else
                viewHolder.typeLeftIcon.setVisibility(View.INVISIBLE);
            viewHolder.typeContentTv.setBackgroundResource(typeContentTvBgSelct);
        }else{
            viewHolder.typeLeftIcon.setVisibility(View.INVISIBLE);
            viewHolder.typeContentTv.setBackgroundResource(typeContentTvBg);
        }
        viewHolder.typeLeftIcon.setOnClickListener(mListener);
        viewHolder.typeLeftIcon.setTag(position);

        /*final RightViewHolder finalViewHolder = viewHolder;
        convertView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    log.e("====hasFocus==1111==" + position);
                    finalViewHolder.right_view.setBackground(context.getDrawable(R.mipmap.set_check_bg));
                } else {

                    log.e("====hasFocus==2222==" + position);
                    finalViewHolder.right_view.setBackground(null);

                }
            }
        });*/

        return convertView;

    }


    /**
     * 用于回调的抽象类
     */
    public static abstract class MyClickListener implements View.OnClickListener {
        /**
         * 基类的onClick方法
         */
        @Override
        public void onClick(View v) {
            myShanChuOnClick((Integer) v.getTag(), v);
        }
        public abstract void myShanChuOnClick(int position, View v);
    }




}
