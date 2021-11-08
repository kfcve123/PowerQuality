package com.cem.powerqualityanalyser.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.adapter.RightModeAdapter;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.tool.log;

import java.util.ArrayList;
import java.util.List;


public class RightModeView extends LinearLayout implements View.OnClickListener{


    private RightModeListView rightmodelistview;
 //   private RecyclerView rightmodelistview;
    private RightModeAdapter rightModeAdapter;
    private Context mContext;
    private ImageView icon_up,icon_down;
    private TextView modelistindex;
    private int selectIndex = 0;
    private boolean initSelect  = true;

    public RightModeView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public RightModeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }
    private Handler myHandler = new Handler();
    private void init(Context context){
        this.mContext = context;
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.rightmode_view,this);

        modelistindex = linearLayout.findViewById(R.id.modelistindex);
        icon_down = linearLayout.findViewById(R.id.icon_down);
        icon_up = linearLayout.findViewById(R.id.icon_up);
        icon_down.setOnClickListener(this);
        icon_up.setOnClickListener(this);

        strList =  new ArrayList();
        rightModeAdapter = new RightModeAdapter(mContext, strList, new RightModeAdapter.MyClickListener() {
            @Override
            public void myShanChuOnClick(int position, View v) {

            }
        });

        rightmodelistview = linearLayout.findViewById(R.id.rightmodelistview);
        rightmodelistview.setDividerHeight(0);
        rightmodelistview.setAdapter(rightModeAdapter);
        setListViewHeightBasedOnChildren(rightmodelistview);
        rightmodelistview.setItemsCanFocus(true);
        /*rightmodelistview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    myHandler.postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            rightmodelistview.setSelection(0);
                        }
                    });
                }
            }
        });*/



        rightmodelistview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parenwt, View view, final int position, long id) {
   //             log.e("====hasFocus==1111==" + position);
                selectIndex = position;
                rightmodelistview.setSelection(selectIndex);
                rightModeAdapter.setSelectIndex(selectIndex);
                modelistindex.setText(selectIndex + 1 +"/" + strList.size());
                view.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                if(!initSelect) {
                    if (onItemCheckCallBack != null)
                        onItemCheckCallBack.onItemCheck(selectIndex);

                }else{
                    initSelect = false;
                }
                /*view.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        log.e("====hasFocus==1111==" + position);
                        if (hasFocus)
                        {
                            log.e("====hasFocus==1111==" + position);
                            v.setBackgroundColor(getResources().getColor(R.color.set_text_color_choose));
                        } else
                        {
                            log.e("====hasFocus==2222==" + position);
                            v.setBackgroundColor(getResources().getColor(R.color.set_text_color));
                        }
                    }
                });*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        rightmodelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectIndex = position;
                rightmodelistview.setSelection(selectIndex);
                rightModeAdapter.setSelectIndex(selectIndex);
                modelistindex.setText(selectIndex + 1 +"/" + strList.size());
                view.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                if(onItemCheckCallBack!=null)
                    onItemCheckCallBack.onItemCheck(selectIndex);
            }
        });

    }

    public void setSelection(int modeIndex){
        if(rightmodelistview!=null) {
            selectIndex = modeIndex;
            rightmodelistview.setSelection(modeIndex);
            rightModeAdapter.setSelectIndex(modeIndex);
            modelistindex.setText(modeIndex + 1 +"/" + strList.size());

        }
    }

    /**
     * 左侧数据列表和右边类型列表的 焦点切换
     * @param lost
     */
    public void lostFocus(boolean lost) {
        rightModeAdapter.lostFocus(lost);
    }

    public interface OnItemCheckCallBack{
        void onItemCheck(int item);
    }

    private OnItemCheckCallBack onItemCheckCallBack;

    public void setOnItemCheckCallBack(OnItemCheckCallBack checkCallBack){
        this.onItemCheckCallBack = checkCallBack;
    }


    private RightListViewItemObj getItemObj(String content,int src){
        return new RightListViewItemObj(content,src);
    }



    @Override
    protected void onAttachedToWindow() {
        // TODO Auto-generated method stub
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        super.onDetachedFromWindow();

    }

    private List<RightListViewItemObj> strList;
    public void setModeList(List<RightListViewItemObj> strList) {
        this.strList = strList;
        rightModeAdapter.updateModeList(strList);
        if(modelistindex!=null)
            modelistindex.setText(1 +"/" + strList.size());
        setListViewHeightBasedOnChildren(rightmodelistview);
    }

    public void notifyDataSetChanged(){
        rightModeAdapter.notifyDataSetChanged();
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        RightModeAdapter listAdapter = (RightModeAdapter) listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        ((MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除
        listView.setLayoutParams(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.icon_down:
                if(selectIndex<strList.size() -1)
                    selectIndex++;
                rightmodelistview.setSelection(selectIndex);
                rightModeAdapter.setSelectIndex(selectIndex);
                modelistindex.setText(selectIndex + 1 +"/" + strList.size());
                rightmodelistview.requestFocus();
                break;
            case R.id.icon_up:
                if(selectIndex>0)
                    selectIndex--;
                rightmodelistview.setSelection(selectIndex);
                rightModeAdapter.setSelectIndex(selectIndex);
                modelistindex.setText(selectIndex + 1 +"/" + strList.size());
                rightmodelistview.requestFocus();
                break;
        }

    }

    public void moveItem(int i) {
        if(i == 1){
            if(selectIndex<strList.size() -1)
                selectIndex++;
            rightmodelistview.setSelection(selectIndex);
            rightModeAdapter.setSelectIndex(selectIndex);
            modelistindex.setText(selectIndex + 1 +"/" + strList.size());
        }else{
            if(selectIndex>0)
                selectIndex--;
            rightmodelistview.setSelection(selectIndex);
            rightModeAdapter.setSelectIndex(selectIndex);
            modelistindex.setText(selectIndex + 1 +"/" + strList.size());

        }
    }
    private boolean updownClick = true;
    public void setUpDownClick(boolean b) {
        updownClick = b;
        icon_up.setClickable(b);
        icon_down.setClickable(b);
    }

    public void setListViewFocusable(boolean b) {
        if(rightmodelistview!=null){
            rightmodelistview.setFocusable(b);
        }
    }

    public void setListViewFocusableInTouchMode(boolean b) {
        if(rightmodelistview!=null){
            rightmodelistview.setFocusableInTouchMode(b);
        }
    }

    public void getViewFoucs(){
        setListViewFocusable(true);
        setListViewFocusableInTouchMode(false);
        requestListViewFocus();
    }


    public void requestListViewFocus() {
        if(rightmodelistview!=null){

//            rightmodelistview.setFocusable(true);
//            rightmodelistview.setFocusableInTouchMode(true);
//            rightmodelistview.requestFocus();
//            rightmodelistview.requestFocusFromTouch();

            rightmodelistview.requestFocus();
        }
    }

    public void hideUpDownView(){
        icon_up.setVisibility(GONE);
        icon_down.setVisibility(GONE);
        modelistindex.setVisibility(GONE);
    }
    public void showUpDownView(){
        icon_up.setVisibility(VISIBLE);
        icon_down.setVisibility(VISIBLE);
        modelistindex.setVisibility(VISIBLE);
    }

    public void setTextSize(int textSize){
        rightModeAdapter.setTextSize(true,textSize);
    }

    public void setTypeContentTvBg(int normal,int select){
        rightModeAdapter.setTypeContentTvBg(normal,select);
    }

}
